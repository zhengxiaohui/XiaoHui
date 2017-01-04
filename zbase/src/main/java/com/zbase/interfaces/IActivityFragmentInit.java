package com.zbase.interfaces;

import android.view.View;

/**
 * 创建人：郑晓辉
 * 创建日期：2016/7/22
 * 描述：Activity和Fragment初始化接口
 */
public interface IActivityFragmentInit {
    int inflateBaseLayoutId();

    int inflateMainLayoutId();

    void initBaseView(View view);

    void initView(View view);

    void setListener();

    void initValue();
}
