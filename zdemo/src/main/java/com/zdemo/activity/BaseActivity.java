package com.zdemo.activity;

import android.content.Intent;
import android.view.View;

import com.umeng.analytics.MobclickAgent;
import com.zbase.activity.AbstractBaseActivity;
import com.zdemo.global.MyApplication;

import cn.jpush.android.api.JPushInterface;

/**
 * 针对单个项目使用的功能放这里，所有通用的放父类AbstractBaseActivity里面
 */
public abstract class BaseActivity extends AbstractBaseActivity{

    @Override
    protected int inflateBaseLayoutId() {
        return inflateMainLayoutId();
    }

    @Override
    protected void initBaseView(View view) {

    }

    @Override
    public MyApplication getMyApplication() {
        return (MyApplication)getApplication();
    }


    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);//极光推送
    }
    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);//极光推送
    }

}
