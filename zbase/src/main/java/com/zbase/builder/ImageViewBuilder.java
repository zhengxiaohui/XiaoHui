package com.zbase.builder;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

/**
 * 创建人：郑晓辉
 * 创建日期：2017/2/28
 * 描述：ImageView构造器
 */
public class ImageViewBuilder extends BaseViewBuilder<ImageView> {


    public ImageViewBuilder(Context context) {
        super(context);
        t = new ImageView(context);
    }

    public ImageViewBuilder setImageResource(@DrawableRes int resId) {
        t.setImageResource(resId);
        return this;
    }

}
