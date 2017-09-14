package com.zbase.common;

import android.app.Application;

import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.cache.CacheMode;
import com.zbase.BuildConfig;
import com.zbase.manager.ActivityStackManager;

/**
 * 创建人：郑晓辉
 * 创建日期：2016/4/27
 * 描述：Application全局配置的父类，给子类MyApplication继承使用
 */
public abstract class BaseApplication extends Application {

    protected ActivityStackManager activityStack;

    public ActivityStackManager getActivityStack() {
        return activityStack;
    }

    public static boolean debugMode = BuildConfig.DEBUG;

    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler.getInstance().init(this);
        activityStack = ActivityStackManager.getInstance();
        activityStack.setOnAppDisplayListener(new ActivityStackManager.OnAppDisplayListener() {
            @Override
            public void onFront() {
                onAppFront();
            }

            @Override
            public void onBack() {
                onAppBack();
            }
        });

        initOkHttp();
    }

    private void initOkHttp() {
//        HttpHeaders headers = new HttpHeaders();
//        headers.put("commonHeaderKey1", "commonHeaderValue1");    //所有的 header 都 不支持 中文
//        headers.put("commonHeaderKey2", "commonHeaderValue2");
//        HttpParams params = new HttpParams();
//        params.put("commonParamsKey1", "commonParamsValue1");     //所有的 params 都 支持 中文
//        params.put("commonParamsKey2", "这里支持中文参数");

        //必须调用初始化
        OkHttpUtils.init(this);
        //以下都不是必须的，根据需要自行选择
        OkHttpUtils okHttpUtils = OkHttpUtils.getInstance()//
//                .debug("OkHttpUtils")                                              //是否打开调试
                .setCacheMode(CacheMode.NO_CACHE)                                  //不开启缓存
                .setConnectTimeout(OkHttpUtils.DEFAULT_MILLISECONDS)               //全局的连接超时时间
                .setReadTimeOut(OkHttpUtils.DEFAULT_MILLISECONDS)                  //全局的读取超时时间
                .setWriteTimeOut(OkHttpUtils.DEFAULT_MILLISECONDS);                 //全局的写入超时时间
        //.setCookieStore(new MemoryCookieStore())                           //cookie使用内存缓存（app退出后，cookie消失）
        //.setCookieStore(new PersistentCookieStore())                       //cookie持久化存储，如果cookie不过期，则一直有效
//                .addCommonHeaders(headers)                                         //设置全局公共头
//                .addCommonParams(params);                                          //设置全局公共参数
        if (BuildConfig.DEBUG) {
            okHttpUtils.debug("OkHttpUtils");
        }
    }

    /**
     * app运行在前台，子类覆盖实现
     */
    protected void onAppFront() {

    }

    /**
     * app运行在后台，子类覆盖实现
     */
    protected void onAppBack() {

    }

    /**
     * 获取登录activity的class
     *
     * @return
     */
    public abstract Class getLoginClass();

    /**
     * 获取统一跳转网页的class
     *
     * @return
     */
    public abstract Class getWebViewClass();

    /**
     * 判断是否已登录
     *
     * @return
     */
    public abstract boolean isLoggedIn();

    /**
     * 清空Application中保存的用户数据
     */
    public abstract void clearUser();

}
