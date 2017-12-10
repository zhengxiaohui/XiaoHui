package com.zbase.request;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.request.base.Request;
import com.zbase.R;
import com.zbase.activity.AbstractBaseActivity;
import com.zbase.common.ZLog;
import com.zbase.strategy.PopStrategy;
import com.zbase.util.NetWorkUtil;
import com.zbase.util.PopUtil;

import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * ================================================
 * 作    者：廖子尧
 * 版    本：1.0
 * 创建日期：2016/1/14
 * 描    述：默认将返回的数据解析成需要的Bean,可以是 BaseBean，String，List，Map
 * 修订历史：
 * ================================================
 */
public abstract class BaseJsonCallback<T> extends AbsCallback<T> {

    protected Context context;
    protected AbstractBaseActivity abstractBaseActivity;
    protected Class<T> clazz;
    protected boolean showProgress = true;//是否显示转圈圈，默认为显示，传false是不显示

    public BaseJsonCallback(Context context, Class<T> clazz) {
        this.context = context;
        this.abstractBaseActivity = (AbstractBaseActivity) context;
        this.clazz = clazz;
    }

    public BaseJsonCallback(Context context, Class<T> clazz, boolean showProgress) {
        this.context = context;
        this.abstractBaseActivity = (AbstractBaseActivity) context;
        this.clazz = clazz;
        this.showProgress = showProgress;
    }

    @Override
    public void onStart(Request<T, ? extends Request> request) {
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
    public T convertResponse(Response response) throws Throwable {
        //直接获取Reader流，构造JsonReader，免去服务端获取流转成字符串再转成json的过程，直接从流转对象
        ResponseBody body = response.body();
        if (body == null) return null;
        JsonReader jsonReader = new JsonReader(body.charStream());
        if (clazz != null) return new Gson().fromJson(jsonReader, clazz);
        return null;
    }

    @Override
    public void onSuccess(com.lzy.okgo.model.Response<T> response) {
        onSuccess(response.body());
    }

    public abstract void onSuccess(T t);

    @Override
    public void onError(com.lzy.okgo.model.Response<T> response) {
        super.onError(response);
        PopUtil.toast(context, R.string.cant_connect_to_server);
        ZLog.dZheng("onError:" + response.message());
    }

    @Override
    public void onFinish() {
        if (showProgress) {
            PopStrategy.closeProgressDialog();
        }
    }
}
