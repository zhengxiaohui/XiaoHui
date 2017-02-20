package com.oranllc.template.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.oranllc.template.R;
import com.oranllc.template.global.MyApplication;
import com.zbase.activity.AbstractBaseActivity;

//import cn.jpush.android.api.JPushInterface;


/**
 * 创建人：郑晓辉
 * 创建日期：2015/11/18
 * 描述：该项目activity界面通用基类
 */
public abstract class BaseActivity extends AbstractBaseActivity {
    private RelativeLayout base_rl_top;//所有Activity或Fragment的通用头部
    protected ImageView base_iv_back;//头部左边返回键
    protected TextView base_tv_title;//头部中间标题
    private LinearLayout base_ll_left;
    private LinearLayout base_ll_middle;
    private LinearLayout base_ll_right;
    private FrameLayout fl_content;//中间内容，由子类实现

    @Override
    protected int inflateBaseLayoutId() {
        return R.layout.activity_base;
    }

    @Override
    protected void initBaseView(View view) {
//        setStatusBarColor(R.color.c2);
        base_rl_top = (RelativeLayout) findViewById(R.id.base_rl_top);
        base_iv_back = (ImageView) findViewById(R.id.base_iv_back);
        base_iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        base_tv_title = (TextView) findViewById(R.id.base_tv_title);
        base_ll_left = (LinearLayout) findViewById(R.id.base_ll_left);
        base_ll_right = (LinearLayout) findViewById(R.id.base_ll_right);
        base_ll_middle = (LinearLayout) findViewById(R.id.base_ll_middle);
        fl_content = (FrameLayout) findViewById(R.id.fl_content);
        fl_content.addView(LayoutInflater.from(context).inflate(inflateMainLayoutId(), null));
    }

    @Override
    protected void onResume() {
        super.onResume();
//        JPushInterface.onResume(this);//极光推送
    }

    @Override
    protected void onPause() {
        super.onPause();
//        JPushInterface.onPause(this);//极光推送
    }

    /**
     * 公共布局头部，默认可见，设置不可见
     */
    protected void setTopGone() {
        base_rl_top.setVisibility(View.GONE);
    }

    /**
     * 隐藏头部左边返回键
     */
    protected void setTopLeftGone() {
        base_iv_back.setVisibility(View.GONE);
    }

    protected void setTopTitle(int topTitleResId) {
        base_tv_title.setText(getString(topTitleResId));
    }

    protected void setTopTitle(String topTitle) {
        base_tv_title.setText(topTitle);
    }

    /**
     * 设置顶部左边里面的控件,如果有多个控件，最好是inflate整个多控件的布局进去，当成一个整体
     *
     * @param view
     */
    protected void setTopLeftView(View view) {
        base_ll_left.removeAllViews();
        base_ll_left.addView(view);
    }

    /**
     * 设置顶部中间里面的控件,如果有多个控件，最好是inflate整个多控件的布局进去，当成一个整体
     *
     * @param view
     */
    protected void setTopMiddleView(View view) {
        base_ll_middle.removeAllViews();
        base_ll_middle.addView(view);
    }

    /**
     * 设置顶部右边里面的控件,如果有多个控件，最好是inflate整个多控件的布局进去，当成一个整体
     *
     * @param view
     */
    protected void setTopRightView(View view) {
        base_ll_right.removeAllViews();
        base_ll_right.addView(view);
    }


    /**
     * 隐藏头部右边
     */
    protected void setTopRightGone() {
        base_ll_right.setVisibility(View.GONE);

    }

    /**
     * 显示头部右边
     */
    protected void setTopRightVisible() {
        base_ll_right.setVisibility(View.VISIBLE);

    }

    protected View getTopRightView() {
        return base_ll_right.getChildAt(0);
    }

    protected View getTopLeftView() {
        return base_ll_left.getChildAt(0);
    }

    @Override
    public MyApplication getMyApplication() {
        return (MyApplication) getApplication();
    }

}
