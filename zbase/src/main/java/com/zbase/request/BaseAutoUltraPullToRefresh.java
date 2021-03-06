package com.zbase.request;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.zbase.adapter.ZBaseRecyclerAdapter;
import com.zbase.interfaces.IPullToRefreshResponse;
import com.zbase.util.ScreenUtil;
import com.zbase.view.LoadMoreFooter;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.MaterialHeader;




/**
 * Created by Administrator on 2015/2/19.
 */
public abstract class BaseAutoUltraPullToRefresh<T extends IPullToRefreshResponse> {

    protected Context context;
    private IRequestPage requestPage;
    private Class<T> clazz;
    private RecyclerView recyclerView;
    protected ZBaseRecyclerAdapter zBaseRecyclerAdapter;
    private long lastUpdatedTime; // 最后请求数据时间
    private long httpBackTime;//请求http的返回时间
    private static final long minRefreshTime = 1000;//最小刷新时间

    public IRequestPage getRequestPage() {
        return requestPage;
    }

    /**
     * 重新设置请求参数，用于参数变化的时候刷重新请求
     * 一般用法如下：
     PostRequestPage postRequestPage =buildPostRequestPage("all);
     autoUltraPullToRefresh.setRequestPage(postRequestPage);
     autoUltraPullToRefresh.refresh();
     * @param requestPage
     */
    public void setRequestPage(IRequestPage requestPage) {
        this.requestPage = requestPage;
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
     * RecyclerView下拉刷新和滚动到底自动加载更多的构造方法
     *
     * @param context
     * @param requestPage           请求分页的类型
     * @param clazz                 返回的Json类的clazz对象
     * @param ptrClassicFrameLayout 下拉刷新控件，如果传null则没有下拉刷新的功能，只有滚动到底自动加载更多的功能
     * @param recyclerView
     * @param zBaseRecyclerAdapter
     */
    public BaseAutoUltraPullToRefresh(Context context, IRequestPage requestPage, Class<T> clazz,
                                      PtrClassicFrameLayout ptrClassicFrameLayout, RecyclerView recyclerView, ZBaseRecyclerAdapter zBaseRecyclerAdapter) {
        this.context = context;
        this.requestPage = requestPage;
        this.clazz = clazz;
        this.ptrClassicFrameLayout = ptrClassicFrameLayout;
        this.recyclerView = recyclerView;
        this.zBaseRecyclerAdapter = zBaseRecyclerAdapter;
        setLoadMoreFooter();
        this.recyclerView.setAdapter(zBaseRecyclerAdapter);
        setZPullToRefreshWithHttp();
    }

    /**
     * 子类可以覆盖，设置不同的LoadMoreFooter自由设置布局和背景颜色等
     */
    protected void setLoadMoreFooter() {
        zBaseRecyclerAdapter.setLoadMoreFooter(new LoadMoreFooter(context));
    }

    private void setZPullToRefreshWithHttp() {
        if (ptrClassicFrameLayout != null) {
            ptrClassicFrameLayout.setLastUpdateTimeRelateObject(context);
            ptrClassicFrameLayout.disableWhenHorizontalMove(true);//解决和横向滑动控件的冲突，比如viewpager，SlidingMenu,ViewFlow等。
            ptrClassicFrameLayout.setPtrHandler(new PtrHandler() {
                @Override
                public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                    return PtrDefaultHandler.checkContentCanBePulledDown(frame, recyclerView, header);
                }

                @Override
                public void onRefreshBegin(PtrFrameLayout frame) {
                    refresh(false);
                }
            });
        }
        zBaseRecyclerAdapter.setLoadMoreListener(new ZBaseRecyclerAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                requestList(RefreshType.LOAD_MORE, false);
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
        refresh(true);

//        ptrClassicFrameLayout.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                ptrClassicFrameLayout.autoRefresh();//自动刷新
//            }
//        }, 300);
    }

    /**
     * 重新请求数据，刷新，有转圈圈
     */
    public void refresh() {
        refresh(true);
    }

    /**
     * 初始化或刷新
     *
     * @param showProgress 是否显示转圈圈
     */
    private void refresh(boolean showProgress) {
        switch (requestPage.getPageType()) {
            case START_PAGE0:
                requestPage.setPageIndex(0);
                break;
            case START_PAGE1:
                requestPage.setPageIndex(1);
                break;
        }
        requestList(RefreshType.INIT_REFRESH, showProgress);
        httpBackTime = System.currentTimeMillis();
    }

    private void requestList(final RefreshType refreshType, boolean showProgress) {
        requestPage.execute(new BaseJsonCallback<T>(context, clazz, showProgress) {

            @Override
            public void onStart(Request<T, ? extends Request> request) {
                super.onStart(request);
                sign(request);
            }

            @Override
            public void onSuccess(T t) {
                if (onObtainDataListener != null) {
                    onObtainDataListener.onObtainData(t);//放在t.getList() != null && t.getList().size() > 0的外面是防止有头部数据而没有列表数据的时候没返回
                }
                if (t != null && t.getList() != null && t.getList().size() > 0) {
                    requestPage.setPageIndex(requestPage.getPageIndex() + 1);
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
                } else {
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
            public void onError(Response<T> response) {
                super.onError(response);
                zBaseRecyclerAdapter.setFooterNomal();
            }

            @Override
            public void onFinish() {
                super.onFinish();
                onFinishRefreshComplete();
                if (onAfterListener != null) {
                    onAfterListener.onAfter();
                }
            }

        });
    }

    protected abstract void sign(Request request);

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
