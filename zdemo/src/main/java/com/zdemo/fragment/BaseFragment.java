package com.zdemo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.umeng.analytics.MobclickAgent;
import com.zbase.fragment.AbstractBaseFragment;
import com.zdemo.activity.BaseActivity;

/**
 * 创建人：郑晓辉
 * 创建日期：2016/2/16
 * 描述：
 */
public abstract class BaseFragment extends AbstractBaseFragment {

    protected BaseActivity baseActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        baseActivity = (BaseActivity) getActivity();
        return inflater.inflate(inflateBaseLayoutId(),null);
    }

    @Override
    protected int inflateBaseLayoutId() {
        return inflateMainLayoutId();
    }

    @Override
    protected void initBaseView(View view) {

    }

}
