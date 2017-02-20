package com.oranllc.template.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.oranllc.template.R;
import com.oranllc.template.activity.BaseActivity;
import com.oranllc.template.global.MyApplication;
import com.zbase.fragment.AbstractBaseFragment;

/**
 * 创建人：郑晓辉
 * 创建日期：2016/2/16
 * 描述：
 */
public abstract class BaseFragment extends AbstractBaseFragment {

    protected BaseActivity baseActivity;
    private RelativeLayout base_rl_top;//所有Activity或Fragment的通用头部
    protected ImageView base_iv_back;//头部左边返回键
    protected TextView base_tv_title;//头部中间标题
    private LinearLayout base_ll_left;
    private LinearLayout base_ll_middle;
    private LinearLayout base_ll_right;
    private FrameLayout fl_content;//中间内容，由子类实现

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        baseActivity = (BaseActivity) getActivity();
        return inflater.inflate(inflateBaseLayoutId(), null);
    }

    @Override
    protected int inflateBaseLayoutId() {
        return R.layout.fragment_base;
    }

    @Override
    protected void initBaseView(View view) {
        base_rl_top = (RelativeLayout) view.findViewById(R.id.base_rl_top);
        base_rl_top.setVisibility(View.GONE);
        base_iv_back = (ImageView) view.findViewById(R.id.base_iv_back);
        base_tv_title = (TextView) view.findViewById(R.id.base_tv_title);
        base_ll_left = (LinearLayout) view.findViewById(R.id.base_ll_left);
        base_ll_right = (LinearLayout) view.findViewById(R.id.base_ll_right);
        base_ll_middle = (LinearLayout) view.findViewById(R.id.base_ll_middle);
        fl_content = (FrameLayout) view.findViewById(R.id.fl_content);
        fl_content.addView(LayoutInflater.from(context).inflate(inflateMainLayoutId(), null));

    }

    /**
     * 公共布局头部，默认不可见，设置可见
     */
    protected void setTopVisible() {
        base_rl_top.setVisibility(View.VISIBLE);
    }

    /**
     * 隐藏头部左边返回键
     */
    protected void setTopLeftGone() {
        base_iv_back.setVisibility(View.GONE);
    }

    /**
     * 设置标题可见和文字
     *
     * @param topTitleResId
     */
    protected void setTopTitle(int topTitleResId) {
        base_rl_top.setVisibility(View.VISIBLE);
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

    public MyApplication getMyApplication() {
        return (MyApplication) baseActivity.getApplication();
    }

}

