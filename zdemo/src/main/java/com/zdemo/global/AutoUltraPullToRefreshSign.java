package com.zdemo.global;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.lzy.okhttputils.request.BaseRequest;
import com.zbase.adapter.ZBaseRecyclerAdapter;
import com.zbase.request.BaseAutoUltraPullToRefresh;
import com.zbase.request.BaseGetRequestPage;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;

/**
 * 创建人：郑晓辉
 * 创建日期：2016/8/4
 * 描述：
 */
public class AutoUltraPullToRefreshSign extends BaseAutoUltraPullToRefresh {
    /**
     * ListView下拉刷新的构造方法
     *
     * @param context
     * @param baseGetRequestPage    下拉刷新的请求参数
     * @param clazz                 返回的Json类的clazz对象
     * @param ptrClassicFrameLayout
     * @param recyclerView          数据为空显示提示的RecyclerView
     * @param zBaseRecyclerAdapter  适配器
     */
    public AutoUltraPullToRefreshSign(Context context, BaseGetRequestPage baseGetRequestPage, Class clazz, PtrClassicFrameLayout ptrClassicFrameLayout, RecyclerView recyclerView, ZBaseRecyclerAdapter zBaseRecyclerAdapter) {
        super(context, baseGetRequestPage, clazz, ptrClassicFrameLayout, recyclerView, zBaseRecyclerAdapter);
    }

    @Override
    protected void sign(BaseRequest request) {

    }
}
