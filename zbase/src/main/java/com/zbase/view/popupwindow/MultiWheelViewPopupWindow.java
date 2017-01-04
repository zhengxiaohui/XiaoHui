package com.zbase.view.popupwindow;

import android.content.Context;
import android.view.View;

import com.zbase.view.MultiWheelView;

/**
 * 创建人：郑晓辉
 * 创建日期：2016/12/2
 * 描述：弹出MultiWheelViewPopupWindow
 * 注意:如果宽度不是充满屏幕，则ListView的宽度要设置成固定值多少dp,Adapter的TextView的宽度要设置成Match
 */
public class MultiWheelViewPopupWindow extends MainStreamPopupWindow {

    private MultiWheelViewPopupWindow(Context context, View view) {
        super(context, view);
    }

    private MultiWheelViewPopupWindow(Context context, View view, int width, int height) {
        super(context, view, width, height);
    }

    @Override
    public MultiWheelView getMainView() {
        return (MultiWheelView) super.getMainView();
    }

    public static class Builder extends MainStreamBuilder {

        public Builder(Context context, View view) {
            super(context, view);
            mainViewCancelPopupWindow = new MultiWheelViewPopupWindow(context, view);
        }

        public Builder(Context context, View view, int width, int height) {
            super(context, view, width, height);
            mainViewCancelPopupWindow = new MultiWheelViewPopupWindow(context, view, width, height);
        }
    }

}
