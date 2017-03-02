package com.zbase.builder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.zbase.util.ScreenUtil;

/**
 * 创建人：郑晓辉
 * 创建日期：2017/2/28
 * 描述：View构造器基类，对所有View的padding,margin等统一设置，泛型子类实现特定功能。
 */
public class BaseViewBuilder<T extends View> {

    protected Context context;
    protected T t;

    public BaseViewBuilder(Context context) {
        this.context = context;
    }

    public BaseViewBuilder setOnClickListener(View.OnClickListener onClickListener) {
        t.setOnClickListener(onClickListener);
        return this;
    }

    public BaseViewBuilder setPadding(int dp) {
        int px = ScreenUtil.dip2px(context, dp);
        t.setPadding(px, px, px, px);
        return this;
    }

    public BaseViewBuilder setLeftMargin(int dp) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.leftMargin = ScreenUtil.dip2px(context, dp);
        t.setLayoutParams(layoutParams);
        return this;
    }

    public BaseViewBuilder setRightMargin(int dp) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.rightMargin = ScreenUtil.dip2px(context, dp);
        t.setLayoutParams(layoutParams);
        return this;
    }

    public T build() {
        return t;
    }
}
