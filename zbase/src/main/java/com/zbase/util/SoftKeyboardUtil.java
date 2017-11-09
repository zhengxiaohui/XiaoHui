package com.zbase.util;

import android.app.Activity;
import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * 创建人：郑晓辉
 * 创建日期：2017/3/13
 * 描述：软键盘控制工具
 */
public class SoftKeyboardUtil {

    private OnSoftEnterClickListener onSoftEnterClickListener;

    public interface OnSoftEnterClickListener{
        void OnSoftEnterClick();
    }

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

    /**
     * 设置软键盘回车(完成)点击事件
     * 注意：会导致密码不变星号,inputType没效果等一些问题，最好单独使用
     * @param editText
     * @param onSoftEnterClickListener
     */
    public static void setEnterActionComplete(EditText editText, OnSoftEnterClickListener onSoftEnterClickListener){
//        editText.setInputType(EditorInfo.TYPE_CLASS_TEXT);//增加这个属性后，回车键的文字自动变成“完成”，而且键盘也是自动隐藏，不用自己隐藏哦，神奇（这里是三星键盘测试结果）
        setEnterAction(editText,-1,onSoftEnterClickListener);
    }

    /**
     * 设置软键盘回车点击事件
     * 注意：会导致密码不变星号,inputType没效果等一些问题，最好单独使用
     * 注意1：EditorInfo.IME_ACTION_DONE只有将singleLine设置为true或者inputType设置为text的EditText有效。
     注意2：对于EditorInfo.IME_ACTION_DONE，有些输入法并不支持它，比如搜狐拼音输入法。
     * @param editText
     * @param editorInfo
     * @param onSoftEnterClickListener
     */
    public static void setEnterAction(EditText editText, int editorInfo, final OnSoftEnterClickListener onSoftEnterClickListener){
        if (editorInfo!=-1) {
            editText.setImeOptions(editorInfo);
        }
        editText.setSingleLine();
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {//ACTION_DOWN不要，不然会执行两次
                    //隐藏键盘
//                    hideSoftKeyboard(context);
                    if (onSoftEnterClickListener!=null) {
                        onSoftEnterClickListener.OnSoftEnterClick();
                    }
                }
                return false;
            }
        });
    }

//    （1）actionUnspecified未指定，对应常量EditorInfo.IME_ACTION_UNSPECIFIED效果：
//    （2）actionNone 没有动作,对应常量EditorInfo.IME_ACTION_NONE效果：
//    （3）actionGo去往，对应常量EditorInfo.IME_ACTION_GO 效果：
//    （4）actionSearch 搜索，对应常量EditorInfo.IME_ACTION_SEARCH效果：
//    （5）actionSend 发送，对应常量EditorInfo.IME_ACTION_SEND效果：
//    （6）actionNext 下一个，对应常量EditorInfo.IME_ACTION_NEXT效果：
//    （7）actionDone 完成，对应常量EditorInfo.IME_ACTION_DONE效果：
//
//    android:imeOptions=”flagNoExtractUi” //使软键盘不全屏显示，只占用一部分屏幕 同时,这个属性还能控件软键盘右下角按键的显示内容,默认情况下为回车键
//    android:imeOptions=”actionNone” //输入框右侧不带任何提示
//    android:imeOptions=”actionGo” //右下角按键内容为’开始’
//    android:imeOptions=”actionSearch” //右下角按键为放大镜图片，搜索
//    android:imeOptions=”actionSend” //右下角按键内容为’发送’
//    android:imeOptions=”actionNext” //右下角按键内容为’下一步’ 或者下一项
//    android:imeOptions=”actionDone” //右下角按键内容为’完成’
//
//    注意：如果设置了 键盘没有变化 那么需要单独加一些其他的属性 配合使用
//    xml中 属性设置：
//    1 将singleLine设置为true
//    2 将inputType设置为text
//    java代码设置：
//    editText.setInputType(EditorInfo.TYPE_CLASS_TEXT);
//    editText.setImeOptions(EditorInfo.IME_ACTION_SEARCH);

}
