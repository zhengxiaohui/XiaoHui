package com.oranllc.template.activity;

import android.view.View;

import com.bumptech.glide.Glide;
import com.oranllc.template.R;
import com.oranllc.template.global.MyApplication;
import com.zbase.activity.BaseGuideActivity;

/**
 * 创建人：郑晓辉
 * 创建日期：2017/2/17
 * 描述：引导页
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
        return HomeActivity.class;
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
        if (imageUrlList != null && imageUrlList.size() > 0) {
            for (int i = 0; i < imageUrlList.size(); i++) {
                Glide.with(context).load(imageUrlList.get(i)).into(imageViewList.get(i));
            }
        }
    }

    @Override
    public MyApplication getMyApplication() {
        return (MyApplication) getApplication();
    }

    @Override
    public void onClick(View v) {

    }


}
