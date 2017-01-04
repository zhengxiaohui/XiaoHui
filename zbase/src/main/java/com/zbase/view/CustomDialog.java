package com.zbase.view;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.StringRes;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.zbase.listener.OnCancelClickListener;
import com.zbase.listener.OnConfirmClickListener;

import static android.content.Context.INPUT_METHOD_SERVICE;


/**
 * 创建人：郑晓辉
 * 创建日期：2016/6/7
 * 描述：自定义对话框类，建造模式传入布局，id等
 */
public class CustomDialog extends Dialog {
    private TextView tvTitle;
    private TextView tvContent;
    private TextView tvConfirm;
    private TextView tvCancel;
    private int titleId;
    private int contentId;
    private int confirmId;
    private int cancelId;
    private OnConfirmClickListener onConfirmClickListener;
    private OnCancelClickListener OnCancelClickListener;

    public void setOnConfirmClickListener(OnConfirmClickListener onConfirmClickListener) {
        this.onConfirmClickListener = onConfirmClickListener;
    }

    public void setOnCancelClickListener(OnCancelClickListener OnCancelClickListener) {
        this.OnCancelClickListener = OnCancelClickListener;
    }

    private CustomDialog(Context context) {
        super(context, android.R.style.Theme_Holo_Light_Dialog_MinWidth);//定义默认的最小宽度
    }

    private CustomDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    private CustomDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    private void init(View view) {
        setCanceledOnTouchOutside(false);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(view);

        if (titleId != 0) {
            tvTitle = (TextView) findViewById(titleId);
        }
        if (contentId != 0) {
            tvContent = (TextView) findViewById(contentId);
        }
        if (confirmId != 0) {
            tvConfirm = (TextView) findViewById(confirmId);
            tvConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    if (onConfirmClickListener != null) {
                        onConfirmClickListener.onConfirmClick(v);
                    }
                }
            });
        }
        if (cancelId != 0) {
            tvCancel = (TextView) findViewById(cancelId);
            tvCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    if (OnCancelClickListener != null) {
                        OnCancelClickListener.onCancelClick(v);
                    }
                }
            });
        }
    }

    @Override
    public void setTitle(@StringRes int resid) {
        if (tvTitle != null) {
            tvTitle.setText(resid);
        }
    }

    public void setTitle(String title) {
        if (tvTitle != null) {
            tvTitle.setText(title);
        }
    }

    public void setContent(@StringRes int resid) {
        if (tvContent != null) {
            tvContent.setText(resid);
        }
    }


    public void setContent(String content) {
        if (tvContent != null) {
            tvContent.setText(content);
        }
    }

    public TextView getTvContent() {
        return tvContent;
    }

    public void setContent(SpannableStringBuilder spannableStringBuilder) {
        if (tvContent != null) {
            tvContent.setText(spannableStringBuilder);
        }
    }

    public void setConfirm(@StringRes int resid) {
        if (tvConfirm != null) {
            tvConfirm.setText(resid);
        }
    }

    public void setCancel(@StringRes int resid) {
        if (tvCancel != null) {
            tvCancel.setText(resid);
        }
    }

    public static class Builder {

        private CustomDialog customDialog;
        private View view;

        public Builder(Context context, View view) {
            customDialog = new CustomDialog(context);
            this.view = view;
        }

        public Builder(Context context, int themeResId, View view) {
            customDialog = new CustomDialog(context, themeResId);
            this.view = view;
        }

        public Builder(Context context, boolean cancelable, View view) {
            customDialog = new CustomDialog(context, cancelable, null);//感觉cancelListener基本不会用到，有dismissListener。如果用到的话要改这里
            this.view = view;
        }

        public Builder setTitleId(int titleId) {
            customDialog.titleId = titleId;
            return this;
        }

        public Builder setContentId(int contentId) {
            customDialog.contentId = contentId;
            return this;
        }

        public Builder setConfirmId(int confirmId) {
            customDialog.confirmId = confirmId;
            return this;
        }

        public Builder setCancelId(int cancelId) {
            customDialog.cancelId = cancelId;
            return this;
        }

        public CustomDialog build() {
            customDialog.init(view);
            return customDialog;
        }
    }

    @Override
    public void dismiss() {
        if (getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null) {
            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        super.dismiss();
    }
}
