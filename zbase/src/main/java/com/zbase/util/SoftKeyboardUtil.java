package com.zbase.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * 创建人：郑晓辉
 * 创建日期：2017/3/13
 * 描述：软键盘控制工具
 */
public class SoftKeyboardUtil {

    /**
     * 弹出软键盘
     *
     * @param context
     * @param view    当前焦点view，接受软键盘输入。
     */
    public static void showSoftKeyboard(Context context, View view) {
        getInputMethodManager(context).showSoftInput(view, 0);
    }

    /**
     * 隐藏软键盘
     *
     * @param context
     */
    public static void hideSoftKeyboard(Context context) {
        getInputMethodManager(context).hideSoftInputFromWindow(((Activity) context).getCurrentFocus().getWindowToken(), 0);
    }

    /**
     * 切换软键盘显示，如果当前显示则隐藏，反之则显示。
     *
     * @param context
     */
    public static void toggleSoftKeyboard(Context context) {
        getInputMethodManager(context).toggleSoftInput(0, 0);
    }

    private static InputMethodManager getInputMethodManager(Context context) {
        return (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
    }

}
