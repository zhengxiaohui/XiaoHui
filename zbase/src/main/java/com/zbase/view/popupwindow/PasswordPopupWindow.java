package com.zbase.view.popupwindow;

import android.content.Context;
import android.view.KeyEvent;
import android.view.View;

import com.zbase.listener.OnInputFinishListener;
import com.zbase.view.PasswordEditText;

/**
 * 创建人：郑晓辉
 * 创建日期：2016/8/18
 * 描述：弹出密码输入框的PopupWindow
 */
public class PasswordPopupWindow extends MainStreamPopupWindow {

    private PasswordEditText passwordEditText;

    public PasswordEditText getPasswordEditText() {
        return passwordEditText;
    }

    public void setOnInputFinishListener(OnInputFinishListener onInputFinishListener) {
        if (onInputFinishListener != null && passwordEditText != null) {
            dismiss();
            passwordEditText.setOnInputFinishListener(onInputFinishListener);
        }
    }

    private PasswordPopupWindow(Context context, View view) {
        super(context, view);
    }

    private PasswordPopupWindow(Context context, View view, int width, int height) {
        super(context, view, width, height);
    }

    @Override
    protected void init(View view) {
        super.init(view);
        setOutsideTouchNotDismissExceptKeyBack();
        if (mainView instanceof PasswordEditText) {
            passwordEditText = (PasswordEditText) mainView;
            passwordEditText.setFocusable(true);// 这个很重要，如果没设置，onKey执行不到
            passwordEditText.setFocusableInTouchMode(true);
            passwordEditText.setOnKeyListener(new View.OnKeyListener() {//必须对passwordEditText进行监听，如果针对的是整个view，则如果焦点在输入框passwordEditText的时候，按回退键无法关闭弹窗。
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        dismiss();
                        return true;
                    }
                    return false;
                }
            });
        }
        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                setWindowBackgroundTranslucence(false);//背景恢复
                if (passwordEditText != null) {
                    passwordEditText.getText().clear();
                }
            }
        });
    }

    public static class Builder extends MainStreamBuilder {

        public Builder(Context context, View view) {
            super(context, view);
            mainViewCancelPopupWindow = new PasswordPopupWindow(context, view);
        }

        public Builder(Context context, View view, int width, int height) {
            super(context, view, width, height);
            mainViewCancelPopupWindow = new PasswordPopupWindow(context, view, width, height);
        }
    }

}
