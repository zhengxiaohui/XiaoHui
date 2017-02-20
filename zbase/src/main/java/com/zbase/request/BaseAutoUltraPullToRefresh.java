package com.zbase.request;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lzy.okhttputils.request.BaseRequest;
import com.zbase.adapter.ZBaseRecyclerAdapter;
import com.zbase.interfaces.IPullToRefreshResponse;
import com.zbase.util.ScreenUtil;
import com.zbase.view.LoadMoreFooter;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.MaterialHeader;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by Administrator on 2015/2/19.
 */
public abstract class BaseAutoUltraPullToRefresh<T extends IPullToRefreshResponse> {

    protected Context context;
    private BaseGetRequestPage baseGetRequestPage;


    private Class<T> clazz;
    private RecyclerView recyclerView;
    protected ZBaseRecyclerAdapter zBaseRecyclerAdapter;
    private long lastUpdatedTime; // 最后请求数据时间
    private long httpBackTime;//请求http的返回时间
    private static final long minRefreshTime = 1000;//最小刷新时间
    private boolean showProgress = true;//是否显示转圈圈

    public BaseGetRequestPage getBaseGetRequestPage() {
        return baseGetRequestPage;
    }

    public void setBaseGetRequestPage(BaseGetRequestPage baseGetRequestPage) {
        this.baseGetRequestPage = baseGetRequestPage;
    }

    /**
     * 描述：下拉刷新的枚举:初始化或刷新，加载更多
     */
    public enum RefreshType {
        INIT_REFRESH, LOAD_MORE
    }

    /**
     * 描述：UltraPullToRefresh的头部样式枚举：经典，材料设计
     */
    public enum UltraPullHeadType {
        CLASSIC, MATERIAL
    }

    private PtrClassicFrameLayout ptrClassicFrameLayout;
    private UltraPullHeadType ultraPullHeadType = UltraPullHeadType.CLASSIC;//头部样式枚举，默认经典下拉样式

    public void setUltraPullHeadType(UltraPullHeadType ultraPullHeadType) {
        this.ultraPullHeadType = ultraPullHeadType;
    }

    public interface OnObtainDataListener<T> {
        void onObtainData(T response);
    }

    private OnObtainDataListener onObtainDataListener;

    public interface OnAfterListener {
        void onAfter();
    }

    private OnAfterListener onAfterListener;

    public void setOnAfterListener(OnAfterListener onAfterListener) {
        this.onAfterListener = onAfterListener;
    }

    /**
     * ListView下拉刷新的构造方法
     *
     * @param context
     * @param baseGetRequestPage   下拉刷新的请求参数
     * @param clazz                返回的Json类的clazz对象
     * @param recyclerView         数据为空显示提示的RecyclerView
     * @param zBaseRecyclerAdapter 适配器
     */
    public BaseAutoUltraPullToRefresh(Context context, BaseGetRequestPage baseGetRequestPage, Class<T> clazz,
                                      PtrClassicFrameLayout ptrClassicFrameLayout, RecyclerView recyclerView, ZBaseRecyclerAdapter zBaseRecyclerAdapter) {
        this.context = context;
        this.baseGetRequestPage = baseGetRequestPage;
        this.clazz = clazz;
        this.ptrClassicFrameLayout = ptrClassicFrameLayout;
        this.recyclerView = recyclerView;
        this.zBaseRecyclerAdapter = zBaseRecyclerAdapter;
        setLoadMoreFooter();
        this.recyclerView.setAdapter(zBaseRecyclerAdapter);
        setZPullToRefreshWithHttp();
    }

    protected void setLoadMoreFooter(){
        zBaseRecyclerAdapter.setLoadMoreFooter(new LoadMoreFooter(context));
    }

    private void setZPullToRefreshWithHttp() {
        if (ptrClassicFrameLayout != null) {
            ptrClassicFrameLayout.setLastUpdateTimeRelateObject(context);
            ptrClassicFrameLayout.setPtrHandler(new PtrHandler() {
                @Override
                public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                    return PtrDefaultHandler.checkContentCanBePulledDown(frame, recyclerView, header);
                }

                @Override
                public void onRefreshBegin(PtrFrameLayout frame) {
                    refresh();
                }
            });
        }
        zBaseRecyclerAdapter.setLoadMoreListener(new ZBaseRecyclerAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                requestList(RefreshType.LOAD_MORE);
                zBaseRecyclerAdapter.setFooterLoading();
            }
        });

//        zPullToRefreshListView.setOnPullEventListener(new PullToRefreshBase.OnPullEventListener<ListView>() {
//            @Override
//            public void onPullEvent(PullToRefreshBase<ListView> refreshView, PullToRefreshBase.State state, PullToRefreshBase.Mode direction) {
//                zPullToRefreshListView.setLastUpdatedTime(lastUpdatedTime);
//            }
//        });

    }

    public void initData() {
        initData(UltraPullHeadType.CLASSIC);
    }

    public void initData(OnObtainDataListener onObtainDataListener) {
        this.onObtainDataListener = onObtainDataListener;
        initData(UltraPullHeadType.CLASSIC);
    }

    public void initData(UltraPullHeadType ultraPullHeadType) {
        switch (ultraPullHeadType) {
            case CLASSIC:

                break;
            case MATERIAL:
                if (ptrClassicFrameLayout != null) {
                    final MaterialHeader header = new MaterialHeader(context);
                    header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
                    header.setPadding(0, ScreenUtil.dip2px(context, 15), 0, ScreenUtil.dip2px(context, 10));
                    header.setPtrFrameLayout(ptrClassicFrameLayout);
                    ptrClassicFrameLayout.setLoadingMinTime(1000);
                    ptrClassicFrameLayout.setDurationToCloseHeader(500);//回退时间
                    ptrClassicFrameLayout.setHeaderView(header);
                    ptrClassicFrameLayout.addPtrUIHandler(header);
                }
                break;
        }
        refresh();
        showProgress = false;

//        ptrClassicFrameLayout.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                ptrClassicFrameLayout.autoRefresh();//自动刷新
//            }
//        }, 300);
    }

    /**
     * 初始化或刷新
     */
    public void refresh() {
        switch (baseGetRequestPage.getPageType()) {
            case START_PAGE0:
                baseGetRequestPage.setPageIndex(0);
                break;
            case START_PAGE1:
                baseGetRequestPage.setPageIndex(1);
                break;
            case LAST_ID:
                baseGetRequestPage.setLastId("");
                break;
        }
        requestList(RefreshType.INIT_REFRESH);
        httpBackTime = System.currentTimeMillis();
    }

    private void requestList(final RefreshType refreshType) {
        baseGetRequestPage.execute(new JsonCallback<T>(context, clazz, showProgress) {

            @Override
            public void onBefore(BaseRequest request) {
                super.onBefore(request);
                sign(request);
            }

            @Override
            public void onResponse(boolean isFromCache, T t, Request request, @Nullable
                    Response response) {
                if (t != null && t.isSuccess()) {//请求状态成功
                    if (t.getList() != null && t.getList().size() > 0) {
                        if(baseGetRequestPage.getPageType()== BaseGetRequestPage.PageType.LAST_ID){
                            baseGetRequestPage.setLastId(t.getLastId());
                        }else {
                            baseGetRequestPage.setPageIndex(baseGetRequestPage.getPageIndex() + 1);
                        }
                        if (onObtainDataListener != null) {
                            onObtainDataListener.onObtainData(t);
                        }
                        switch (refreshType) {
                            case INIT_REFRESH:
                                zBaseRecyclerAdapter.setEmpty(false);
                                zBaseRecyclerAdapter.setList(t.getList());
                                lastUpdatedTime = System.currentTimeMillis();
                                zBaseRecyclerAdapter.setFooterNomal();
                                break;
                            case LOAD_MORE:
                                zBaseRecyclerAdapter.addList(t.getList());
                                zBaseRecyclerAdapter.setFooterNomal();
                                break;
                        }
                    } else {//请求成功，但是没数据，size为0
                        switch (refreshType) {
                            case INIT_REFRESH:
                                zBaseRecyclerAdapter.setEmpty(true);
                                zBaseRecyclerAdapter.clear();
                                lastUpdatedTime = System.currentTimeMillis();
                                zBaseRecyclerAdapter.setFooterNomal();
                                break;
                            case LOAD_MORE:
                                zBaseRecyclerAdapter.setFooterShowAll();
                                break;
                        }
                    }
                } else {//请求状态失败
                    switch (refreshType) {
                        case INIT_REFRESH:
                            zBaseRecyclerAdapter.setEmpty(true);
                            zBaseRecyclerAdapter.clear();
                            lastUpdatedTime = System.currentTimeMillis();
                            zBaseRecyclerAdapter.setFooterNomal();
                            break;
                        case LOAD_MORE:
                            zBaseRecyclerAdapter.setFooterShowAll();
                            break;
                    }
                }
            }

            @Override
            public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                super.onError(isFromCache, call, response, e);
//                zBaseRecyclerAdapter.setEmpty(true); 有数据，但是再请求时候出现错误时，会上面显示数据，下面显示empty
                zBaseRecyclerAdapter.setFooterNomal();
            }

            @Override
            public void onAfter(boolean isFromCache, @Nullable T t, Call call, @Nullable Response response, @Nullable Exception e) {
                super.onAfter(isFromCache, t, call, response, e);
                onFinishRefreshComplete();
                if (onAfterListener != null) {
                    onAfterListener.onAfter();
                }
            }
        });
    }

    protected abstract void sign(BaseRequest request);

    /**
     * http请求结束后刷新旋转状态变成普通状态
     */
    private void onFinishRefreshComplete() {
        if (ptrClassicFrameLayout != null) {
            long refreshIntervalTime = System.currentTimeMillis() - httpBackTime;
            if (refreshIntervalTime > minRefreshTime) {
                ptrClassicFrameLayout.refreshComplete();
            } else {
                handler.sendEmptyMessageDelayed(0, minRefreshTime - refreshIntervalTime);
            }
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ptrClassicFrameLayout.refreshComplete();
        }
    };

}
