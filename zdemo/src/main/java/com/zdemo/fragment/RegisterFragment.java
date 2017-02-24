package com.zdemo.fragment;

import android.view.View;

import com.zbase.util.PopUtil;
import com.zbase.view.VerifyEditText;
import com.zdemo.R;

/**
 * 创建人：郑晓辉
 * 创建日期：2017/1/3
 * 描述：自定义注册控件
 */
public class RegisterFragment extends BaseFragment {

    private VerifyEditText phoneEditText;
    private VerifyEditText passwordEditText;
    private VerifyEditText affirmPasswordEditText;

    @Override
    protected int inflateMainLayoutId() {
        return R.layout.fragment_register;
    }

    @Override
    protected void initView(View view) {
        phoneEditText = (VerifyEditText) view.findViewById(R.id.phoneEditText);
        passwordEditText = (VerifyEditText) view.findViewById(R.id.passwordEditText);
        affirmPasswordEditText = (VerifyEditText) view.findViewById(R.id.affirmPasswordEditText);

        view.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (phoneEditText.verify() && passwordEditText.verify() && affirmPasswordEditText.verify()) {//验证格式正确
                    if (passwordEditText.getString().equals(affirmPasswordEditText.getString())) {
                        PopUtil.toast(context, "注册成功！");
                    } else {
                        PopUtil.toast(context, getString(R.string.two_input_password_is_not_consistent));
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
