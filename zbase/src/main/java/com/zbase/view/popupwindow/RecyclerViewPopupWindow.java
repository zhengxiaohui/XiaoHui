package com.zbase.view.popupwindow;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zbase.adapter.ZBaseRecyclerAdapter;

/**
 * 创建人：郑晓辉
 * 创建日期：2016/11/30
 * 描述：弹出RecyclerViewPopupWindow
 * 注意:如果宽度不是充满屏幕，则RecyclerView的宽度要设置成固定值多少dp,Adapter的TextView的宽度要设置成Match
 */
public class RecyclerViewPopupWindow extends MainStreamPopupWindow {

    private RecyclerViewPopupWindow(Context context, View view) {
        super(context, view);
    }

    private RecyclerViewPopupWindow(Context context, View view, int width, int height) {
        super(context, view, width, height);
    }

    public void setAdapter(ZBaseRecyclerAdapter zBaseRecyclerAdapter) {
        if (mainView instanceof RecyclerView) {
            RecyclerView recyclerView = (RecyclerView) mainView;
            if (recyclerView != null) {
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(zBaseRecyclerAdapter);
            }
        }
    }

    @Override
    public RecyclerView getMainView() {
        return (RecyclerView) super.getMainView();
    }

    public static class Builder extends MainStreamBuilder {

        public Builder(Context context, View view) {
            super(context, view);
            mainViewCancelPopupWindow = new RecyclerViewPopupWindow(context, view);
        }

        public Builder(Context context, View view, int width, int height) {
            super(context, view, width, height);
            mainViewCancelPopupWindow = new RecyclerViewPopupWindow(context, view, width, height);
        }
    }

}
