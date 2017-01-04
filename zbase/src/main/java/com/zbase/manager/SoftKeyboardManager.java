package com.zbase.manager;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * 创建人：郑晓辉
 * 创建日期：2016/11/30
 * 描述：软键盘显示隐藏管理器
 * 注意：该类2016/11/30未做测试
 */
public class SoftKeyboardManager {

    private Context context;
    private InputMethodManager inputMethodManager;

    public SoftKeyboardManager(Context context) {
        this.context = context;
        inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    /**
     * 方法一(如果输入法在窗口上已经显示，则隐藏，反之则显示)
     */
    public void toggleSoftInput() {
        inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 强制显示软键盘
     *
     * @param view
     */
    public void showForce(View view) {
        inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    /**
     * 强制隐藏软键盘
     *
     * @param view
     */
    public void hideForce(View view) {
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
    }

    /**
     * 获取软键盘状态，是否打开，true打开，false关闭
     */
    public boolean obtainState() {
        return inputMethodManager.isActive();//若返回true，则表示输入法打开
    }

}
