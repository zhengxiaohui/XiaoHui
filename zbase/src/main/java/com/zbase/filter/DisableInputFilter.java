package com.zbase.filter;

import android.text.InputFilter;
import android.text.Spanned;

/**
 * 创建人：郑晓辉
 * 创建日期：2016/12/13
 * 描述：不论输入什么字符都返回""实现disable的效果，直接使用setEnabled(false)是无效的
 */
public class DisableInputFilter implements InputFilter {

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        return dest.subSequence(dstart, dend);
    }
}
