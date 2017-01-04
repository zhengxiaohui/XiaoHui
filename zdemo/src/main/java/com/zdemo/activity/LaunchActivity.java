package com.zdemo.activity;

import android.view.View;

import com.zbase.activity.BaseLaunchActivity;
import com.zdemo.R;
import com.zdemo.global.MyApplication;

/**
 * 创建人：郑晓辉
 * 创建日期：2016/11/3
 * 描述：
 */
public class LaunchActivity extends BaseLaunchActivity {

    @Override
    protected Class getGuideActivityClass() {
        return GuideActivity.class;
    }

    @Override
    protected Class getHomeActivityClass() {
        return MainActivity.class;
    }

    @Override
    protected boolean isTest() {
        return true;
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initValue() {
        iv_launch.setImageResource(R.mipmap.welcome_android);
        startHandler();
    }

    @Override
    public MyApplication getMyApplication() {
        return (MyApplication)getApplication();
    }

    @Override
    public void onClick(View v) {

    }
}

