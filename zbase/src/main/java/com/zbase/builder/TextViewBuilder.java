package com.zbase.builder;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.StringRes;
import android.widget.TextView;

/**
 * 创建人：郑晓辉
 * 创建日期：2017/2/28
 * 描述：TextView构造器
 */
public class TextViewBuilder extends BaseViewBuilder<TextView> {


    public TextViewBuilder(Context context) {
        super(context);
        t=new TextView(context);
    }

    public TextViewBuilder setText(@StringRes int resid) {
        t.setText(resid);
        return this;
    }

    public TextViewBuilder setTextColor(@ColorRes int id) {
        t.setTextColor(context.getResources().getColor(id));
        return this;
    }

}
