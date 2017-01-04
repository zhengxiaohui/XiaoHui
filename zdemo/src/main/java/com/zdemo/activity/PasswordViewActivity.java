package com.zdemo.activity;

import android.view.View;

import com.zbase.common.ZLog;
import com.zbase.listener.OnInputFinishListener;
import com.zbase.view.PasswordView;
import com.zdemo.R;

/**
 * 创建人：郑晓辉
 * 创建日期：2016/7/20
 * 描述：支付界面输入6位密码控件
 */
public class PasswordViewActivity extends BaseActivity {

    private PasswordView passwordView;

    @Override
    protected int inflateMainLayoutId() {
        return R.layout.activity_password_view;
    }

    @Override
    protected void initView(View view) {
        passwordView = (PasswordView) findViewById(R.id.passwordView);
        passwordView.setOnInputFinishListener(new OnInputFinishListener() {
            @Override
            public void onInputFinish(String password) {
                ZLog.dZheng(password);
            }
        });
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initValue() {

    }

    @Override
    public void onClick(View v) {

    }
}
