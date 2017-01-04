package com.zdemo.activity;

import android.view.View;

import com.zbase.activity.BaseGuideActivity;
import com.zdemo.R;
import com.zdemo.global.MyApplication;

/**
 * 创建人：郑晓辉
 * 创建日期：2016/11/3
 * 描述：
 */
public class GuideActivity extends BaseGuideActivity {

    @Override
    protected int findTvSkipById() {
        return R.id.tv_skip;
    }

    @Override
    protected int findViewPagerById() {
        return R.id.viewPager;
    }

    @Override
    protected int findLinearDotById() {
        return R.id.linearDot;
    }

    @Override
    protected int getPageCount() {
        return 4;
    }

    @Override
    protected Class getHomeActivityClass() {
        return MainActivity.class;
    }

    @Override
    protected int inflateMainLayoutId() {
        return R.layout.activity_guide;
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initValue() {
        imageViewList.get(0).setImageResource(R.mipmap.guide_350_01);
        imageViewList.get(1).setImageResource(R.mipmap.guide_350_02);
        imageViewList.get(2).setImageResource(R.mipmap.guide_350_03);
        imageViewList.get(3).setImageResource(R.mipmap.guide_350_04);
    }

    @Override
    public MyApplication getMyApplication() {
        return (MyApplication) getApplication();
    }

    @Override
    public void onClick(View v) {

    }
}
