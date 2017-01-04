package com.zbase.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.DigitsKeyListener;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.widget.EditText;

import com.zbase.R;
import com.zbase.util.PopUtil;
import com.zbase.util.RegexUtil;

/**
 * 创建人：郑晓辉
 * 创建日期：2016/12/26
 * 描述：注册，登录使用的账号输入框
 */
public class CustomEditText extends EditText {

    public enum InputEnum {ACCOUNT, PHONE, EMAIL, PASSWORD}//普通账号，手机号，邮箱账号，密码

    private InputEnum inputEnum = InputEnum.ACCOUNT;//默认是普通账号
    private int minLength;//最小长度

    public CustomEditText(Context context) {
        super(context);
        init(null, 0);
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyle) {
        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CustomEditText, defStyle, 0);
        int direction = a.getInt(R.styleable.CustomEditText_inputEnum, 0);
        switch (direction) {
            case 0:
                inputEnum = InputEnum.ACCOUNT;
                break;
            case 1:
                inputEnum = InputEnum.PHONE;
                break;
            case 2:
                inputEnum = InputEnum.EMAIL;
                break;
            case 3:
                inputEnum = InputEnum.PASSWORD;
                break;
        }
        minLength = a.getInt(R.styleable.CustomEditText_minLength, 0);
        a.recycle();
        loadViews();
    }

    private void loadViews() {
        setMaxLines(1);
        switch (inputEnum) {
            case ACCOUNT:
                if (TextUtils.isEmpty(getHint())) {
                    setHint(getContext().getString(R.string.please_enter_the_account));
                }
                String accountDigits = getContext().getString(R.string.account_digits);
                setKeyListener(DigitsKeyListener.getInstance(accountDigits));
                if (getFilters().length == 0) {//如果布局中没有设置最大长度，则默认最大长度20
                    setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
                }
                break;
            case PHONE:
                if (TextUtils.isEmpty(getHint())) {
                    setHint(getContext().getString(R.string.please_enter_the_phone_number));
                }
                setInputType(InputType.TYPE_CLASS_NUMBER);//只能输入数字
                if (getFilters().length == 0) {//如果布局中没有设置最大长度，则默认最大长度11
                    setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
                }
                break;
            case EMAIL:
                if (TextUtils.isEmpty(getHint())) {
                    setHint(getContext().getString(R.string.please_enter_the_email_address));
                }
                String emailDigits = getContext().getString(R.string.email_digits);
                setKeyListener(DigitsKeyListener.getInstance(emailDigits));
                if (getFilters().length == 0) {//如果布局中没有设置最大长度，则默认最大长度20
                    setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
                }
                break;
            case PASSWORD:
                String passwordDigits = getContext().getString(R.string.password_digits);
                setKeyListener(DigitsKeyListener.getInstance(passwordDigits));
                setTransformationMethod(PasswordTransformationMethod.getInstance()); //设置为密码输入框
                if (getFilters().length == 0) {//如果布局中没有设置最大长度，则默认最大长度20
                    setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
                }
                break;
        }
    }

    /**
     * 验证账号，包括非空判断，最短字符串判断，格式验证
     *
     * @return
     */
    public boolean verify() {
        String text = getString();
        switch (inputEnum) {
            case ACCOUNT:
                if (minLength == 0) {//如果没有设置最小长度，则默认最小长度3
                    minLength = 3;
                }
                if (TextUtils.isEmpty(text)) {
                    PopUtil.toast(getContext(), getContext().getString(R.string.account_cannot_be_empty));
                    return false;
                } else if (text.length() < minLength) {
                    PopUtil.toast(getContext(), getContext().getString(R.string.account_length_is_too_short));
                    return false;
                }
                break;
            case PHONE:
                if (minLength == 0) {
                    minLength = 11;
                }
                if (TextUtils.isEmpty(text)) {
                    PopUtil.toast(getContext(), getContext().getString(R.string.phone_cannot_be_empty));
                    return false;
                } else if (text.length() < minLength) {
                    PopUtil.toast(getContext(), getContext().getString(R.string.phone_length_is_too_short));
                    return false;
                } else if (!RegexUtil.checkMobile(text)) {
                    PopUtil.toast(getContext(), getContext().getString(R.string.please_enter_a_correct_phone));
                    return false;
                }
                break;
            case EMAIL:
                if (minLength == 0) {
                    minLength = 10;
                }
                if (TextUtils.isEmpty(text)) {
                    PopUtil.toast(getContext(), getContext().getString(R.string.email_cannot_be_empty));
                    return false;
                } else if (text.length() < minLength) {
                    PopUtil.toast(getContext(), getContext().getString(R.string.email_length_is_too_short));
                    return false;
                } else if (!RegexUtil.checkEmail(text)) {
                    PopUtil.toast(getContext(), getContext().getString(R.string.please_enter_a_correct_email));
                    return false;
                }
                break;
            case PASSWORD:
                if (minLength == 0) {
                    minLength = 3;
                }
                if (TextUtils.isEmpty(text)) {
                    PopUtil.toast(getContext(), getContext().getString(R.string.password_cannot_be_empty));
                    return false;
                } else if (text.length() < minLength) {
                    PopUtil.toast(getContext(), getContext().getString(R.string.password_length_is_too_short));
                    return false;
                }
                break;
        }
        return true;
    }

    /**
     * 获取没有空格的文本
     *
     * @return
     */
    public String getString() {
        return getText().toString().trim();
    }

}
