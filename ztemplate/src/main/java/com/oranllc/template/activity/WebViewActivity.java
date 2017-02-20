package com.oranllc.template.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.oranllc.template.R;
import com.oranllc.template.global.MyApplication;
import com.zbase.activity.BaseWebViewActivity;
import com.zbase.common.BaseApplication;

/**
 * 创建人：郑晓辉
 * 创建日期：2017/02/17
 * 描述：内嵌浏览器页面
 */
public class WebViewActivity extends BaseWebViewActivity {
    private ImageView iv_back;
    private TextView tv_title;
    private String title;

    @Override
    protected int inflateMainLayoutId() {
        return R.layout.activity_web_view_top;
    }

    @Override
    protected void initView(View view) {
        iv_back = (ImageView) view.findViewById(R.id.iv_back);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
    }

    @Override
    protected void setListener() {
        iv_back.setOnClickListener(this);
    }

    @Override
    protected void initValue() {
        title = getString(R.string.app_name);
        if (getIntent().getExtras() != null) {
            Bundle bundle = getIntent().getExtras();
            title = bundle.getString(BaseWebViewActivity.WEB_VIEW_TITLE);
        }
        tv_title.setText(title);
    }

    @Override
    public BaseApplication getMyApplication() {
        return (MyApplication) getApplication();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
        }
    }
}
