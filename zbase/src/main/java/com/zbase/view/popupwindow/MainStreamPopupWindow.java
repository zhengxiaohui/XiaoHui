package com.zbase.view.popupwindow;

import android.content.Context;
import android.support.annotation.StringRes;
import android.view.View;
import android.widget.TextView;

import com.zbase.listener.OnCancelClickListener;
import com.zbase.listener.OnConfirmClickListener;

/**
 * 创建人：郑晓辉
 * 创建日期：2016/11/30
 * 描述：弹出主流的PopupWindow
 * 自由设置是否有：标题，主内容，确定，取消，这些控件。
 */
public class MainStreamPopupWindow extends AlphaPopupWindow {

    private TextView tvTitle;//标题控件
    protected View mainView;//主控件
    private TextView tvConfirm;//确定控件
    private TextView tvCancel;//取消控件

    private int titleTextViewId;
    private int mainViewId;
    private int confirmTextViewId;
    private int cancelTextViewId;

    private OnConfirmClickListener onConfirmClickListener;

    public void setOnConfirmClickListener(OnConfirmClickListener onConfirmClickListener) {
        this.onConfirmClickListener = onConfirmClickListener;
    }

    private OnCancelClickListener onCancelClickListener;

    public void setOnCancelClickListener(OnCancelClickListener OnCancelClickListener) {
        this.onCancelClickListener = OnCancelClickListener;
    }

    protected MainStreamPopupWindow(Context context, View view) {
        super(context, view);
    }

    protected MainStreamPopupWindow(Context context, View view, int width, int height) {
        super(context, view, width, height);
    }

    protected void init(View view) {
        setDark(true);//默认弹出后背景变暗，外面还可以设置为不变暗。
        if (titleTextViewId != 0) {
            tvTitle = (TextView) view.findViewById(titleTextViewId);
        }
        if (mainViewId != 0) {
            mainView = view.findViewById(mainViewId);
        }
        if (confirmTextViewId != 0) {
            tvConfirm = (TextView) view.findViewById(confirmTextViewId);
            if (tvConfirm != null) {
                tvConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onConfirmClick(v);
                    }
                });
            }
        }
        if (cancelTextViewId != 0) {
            tvCancel = (TextView) view.findViewById(cancelTextViewId);
            if (tvCancel != null) {
                tvCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onCancelClick(v);
                    }
                });
            }
        }
    }

    /**
     * 子类可以覆盖重写增加自己的判断条件等
     * @param v
     */
    protected void onConfirmClick(View v){
        dismiss();
        if (onConfirmClickListener != null) {
            onConfirmClickListener.onConfirmClick(v);
        }
    }

    /**
     * 子类可以覆盖重写增加自己的判断条件等
     * @param v
     */
    protected void onCancelClick(View v){
        dismiss();
        if (onCancelClickListener != null) {
            onCancelClickListener.onCancelClick(v);
        }
    }

    /**
     * 设置标题文字
     *
     * @param text
     */
    public void setTitle(CharSequence text) {
        if (tvTitle != null) {
            tvTitle.setText(text);
        }
    }

    /**
     * 设置标题文字资源Id
     *
     * @param resid
     */
    public void setTitle(@StringRes int resid) {
        if (tvTitle != null) {
            tvTitle.setText(resid);
        }
    }

    /**
     * 设置确定文字资源Id
     *
     * @param resid
     */
    public void setConfirmStringId(@StringRes int resid) {
        if (tvConfirm != null) {
            tvConfirm.setText(resid);
        }
    }

    /**
     * 设置取消文字资源Id
     *
     * @param resid
     */
    public void setCancelStringId(@StringRes int resid) {
        if (tvCancel != null) {
            tvCancel.setText(resid);
        }
    }

    /**
     * 返回MainView,由子类覆盖返回具体的View
     * @return
     */
    public View getMainView(){
        return mainView;
    }

    public static class MainStreamBuilder {

        protected MainStreamPopupWindow mainViewCancelPopupWindow;
        private View view;

        public MainStreamBuilder(Context context, View view) {
            this.view = view;
        }

        public MainStreamBuilder(Context context, View view, int width, int height) {
            this.view = view;
        }

        /**
         * 设置标题TextVieV控件的Id
         *
         * @param titleTextViewId
         * @return
         */
        public MainStreamBuilder setTitleTextViewId(int titleTextViewId) {
            mainViewCancelPopupWindow.titleTextViewId = titleTextViewId;
            return this;
        }

        /**
         * 设置主要内容的View控件的Id
         *
         * @param mainViewId
         * @return
         */
        public MainStreamBuilder setMainViewId(int mainViewId) {
            mainViewCancelPopupWindow.mainViewId = mainViewId;
            return this;
        }

        /**
         * 设置确定TextView控件的Id
         *
         * @param confirmTextViewId
         * @return
         */
        public MainStreamBuilder setConfirmTextViewId(int confirmTextViewId) {
            mainViewCancelPopupWindow.confirmTextViewId = confirmTextViewId;
            return this;
        }

        /**
         * 设置取消TextView控件的Id
         *
         * @param cancelTextViewId
         * @return
         */
        public MainStreamBuilder setCancelTextViewId(int cancelTextViewId) {
            mainViewCancelPopupWindow.cancelTextViewId = cancelTextViewId;
            return this;
        }

        /**
         * 初始化控件
         *
         * @return
         */
        public MainStreamPopupWindow build() {
            mainViewCancelPopupWindow.init(view);
            return mainViewCancelPopupWindow;
        }
    }

}
