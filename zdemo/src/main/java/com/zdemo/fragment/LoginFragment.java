package com.zdemo.fragment;

import android.view.View;

import com.zbase.util.PopUtil;
import com.zbase.view.VerifyEditText;
import com.zdemo.R;

/**
 * 创建人：郑晓辉
 * 创建日期：2017/1/3
 * 描述：自定义登录控件
 */
public class LoginFragment extends BaseFragment {

    private VerifyEditText phoneEditText;
    private VerifyEditText passwordEditText;

    @Override
    protected int inflateMainLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    protected void initView(View view) {
        phoneEditText = (VerifyEditText) view.findViewById(R.id.phoneEditText);
        passwordEditText = (VerifyEditText) view.findViewById(R.id.passwordEditText);

        view.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (phoneEditText.verify() && passwordEditText.verify()) {//验证格式正确
                    if (phoneEditText.getString().equals("15859130726") && passwordEditText.getString().equals("123456")) {
                        PopUtil.toast(context, "登录成功！");
                    } else {
                        PopUtil.toast(context, getString(R.string.account_or_password_error));
                    }

                }
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
