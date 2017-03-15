package com.zbase.util;

import android.content.Context;
import android.view.View;
import android.widget.EditText;

import com.zbase.listener.AfterClickListener;

/**
 * 创建人：郑晓辉
 * 创建日期：2017/3/13
 * 描述：EditText工具类
 */
public class EditTextUtil {

    /**
     * 设置EditText的点击事件
     *
     * @param context
     * @param editText
     * @param afterClickListener
     */
    public static void setAfterClickListener(final Context context, final EditText editText, final AfterClickListener afterClickListener) {
        editText.setClickable(true);
        editText.setFocusableInTouchMode(false);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setFocusable(true);
                editText.setFocusableInTouchMode(true);
                editText.requestFocus();
                editText.requestFocusFromTouch();
                SoftKeyboardUtil.showSoftKeyboard(context, editText);
                if (afterClickListener != null) {
                    afterClickListener.afterClick(v);
                }
            }
        });
    }
}
