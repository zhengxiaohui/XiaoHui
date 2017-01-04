package com.zbase.view.popupwindow;

import android.content.Context;
import android.view.View;
import android.widget.GridView;

import com.zbase.adapter.ZBaseAdapterAdvance;

/**
 * 创建人：郑晓辉
 * 创建日期：2016/11/30
 * 描述：弹出GridViewPopupWindow
 * 注意:如果宽度不是充满屏幕，则GridView的宽度要设置成固定值多少dp,Adapter的TextView的宽度要设置成Match
 */
public class GridViewPopupWindow extends MainStreamPopupWindow {

    private GridViewPopupWindow(Context context, View view) {
        super(context, view);
    }

    private GridViewPopupWindow(Context context, View view, int width, int height) {
        super(context, view, width, height);
    }

    public void setAdapter(ZBaseAdapterAdvance zBaseAdapterAdvance) {
        if (mainView instanceof GridView) {
            GridView gridView = (GridView) mainView;
            if (gridView != null) {
                gridView.setAdapter(zBaseAdapterAdvance);
            }
        }
    }

    @Override
    public GridView getMainView() {
        return (GridView) super.getMainView();
    }

    public static class Builder extends MainStreamBuilder {

        public Builder(Context context, View view) {
            super(context, view);
            mainViewCancelPopupWindow = new GridViewPopupWindow(context, view);
        }

        public Builder(Context context, View view, int width, int height) {
            super(context, view, width, height);
            mainViewCancelPopupWindow = new GridViewPopupWindow(context, view, width, height);
        }
    }

}
