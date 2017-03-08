package com.zbase.common;

import android.view.View;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * 创建人：郑晓辉
 * 创建日期：2017/3/7
 * 描述：只有下拉刷新，没有自动加载更多的时候使用这个抽象类
 * 用法：
 ptrClassicFrameLayout.setPtrHandler(new PtrMyDefaultHandler(recyclerView) {
@Override
public void onRefreshBegin(PtrFrameLayout frame) {
requestMessage();
}
});
 */
public abstract class PtrMyDefaultHandler extends PtrDefaultHandler {

    private View view;

    public PtrMyDefaultHandler(View view) {
        this.view = view;
    }

    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        return super.checkCanDoRefresh(frame, view, header);
    }

}
