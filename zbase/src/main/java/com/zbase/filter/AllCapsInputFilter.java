package com.zbase.filter;

import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;

/**
 * 创建人：郑晓辉
 * 创建日期：2016/12/13
 * 描述：输入的字母全部变成大写字母
 */
public class AllCapsInputFilter implements InputFilter {
    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        for (int i = start; i < end; i++) {
            if (Character.isLowerCase(source.charAt(i))) {
                char[] v = new char[end - start];
                TextUtils.getChars(source, start, end, v, 0);
                String s = new String(v).toUpperCase();

                if (source instanceof Spanned) {
                    SpannableString sp = new SpannableString(s);
                    TextUtils.copySpansFrom((Spanned) source,
                            start, end, null, sp, 0);
                    return sp;
                } else {
                    return s;
                }
            }
        }
        return null; // keep original
    }
}
