package com.zdemo.global;

import android.content.Context;

import com.lzy.okhttputils.request.BaseRequest;
import com.zbase.request.JsonCallback;

/**
 * 创建人：郑晓辉
 * 创建日期：2016/8/3
 * 描述：
 */
public abstract class SignJsonCallback<T> extends JsonCallback<T> {

    public SignJsonCallback(Context context, Class clazz) {
        super(context, clazz);
    }

    public SignJsonCallback(Context context, Class clazz, boolean showProgress) {
        super(context, clazz, showProgress);
    }

    @Override
    public void onBefore(BaseRequest request) {
        super.onBefore(request);
    }

}
