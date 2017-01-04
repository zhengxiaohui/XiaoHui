package com.zbase.request;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.lzy.okhttputils.callback.AbsCallback;
import com.lzy.okhttputils.request.BaseRequest;
import com.zbase.R;
import com.zbase.strategy.PopStrategy;
import com.zbase.util.NetWorkUtil;
import com.zbase.util.PopUtil;

import okhttp3.Call;
import okhttp3.Response;

/**
 * ================================================
 * 作    者：廖子尧
 * 版    本：1.0
 * 创建日期：2016/1/14
 * 描    述：默认将返回的数据解析成需要的Bean,可以是 BaseBean，String，List，Map
 * 修订历史：
 * ================================================
 */
public abstract class JsonCallback<T> extends AbsCallback<T> {

    private Context context;
    private Class<T> clazz;
    private boolean showProgress = true;//是否显示转圈圈，默认为显示，传false是不显示

    public JsonCallback(Context context, Class<T> clazz) {
        this.context = context;
        this.clazz = clazz;
    }

    public JsonCallback(Context context, Class<T> clazz, boolean showProgress) {
        this.context = context;
        this.clazz = clazz;
        this.showProgress = showProgress;
    }

    @Override
    public void onBefore(BaseRequest request) {
        if (!NetWorkUtil.isNetworkConnected(context)) {
            PopUtil.toast(context, R.string.there_is_no_network);
            return;
        }
        if (showProgress) {
            try {
                PopStrategy.showProgressDialog(context);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {

            }
        }
    }

    //该方法是子线程处理，不能做ui相关的工作
    @Override
    public T parseNetworkResponse(Response response) throws Exception {
        String responseData = response.body().string();
        if (TextUtils.isEmpty(responseData)) return null;
        if (clazz != null) return new Gson().fromJson(responseData, clazz);
        return null;
    }

    @Override
    public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
        super.onError(isFromCache, call, response, e);
        PopUtil.toast(context, R.string.cant_connect_to_server);
    }

    @Override
    public void onAfter(boolean isFromCache, @Nullable T t, Call call, @Nullable Response response, @Nullable Exception e) {
        if (showProgress) {
            PopStrategy.closeProgressDialog();
        }
    }
}
