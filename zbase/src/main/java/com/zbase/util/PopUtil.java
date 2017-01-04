package com.zbase.util;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zbase.R;

/**
 * 所有弹出信息控件工具集合
 *
 * @author z
 */
public class PopUtil {

    private static Dialog loadingDialog;

    /**
     * 弹出短Toast提示
     *
     * @param message 定义在资源文件string中的提示内容，如R.String.hello
     */
    public static void toast(Context context, int message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 弹出短Toast提示
     *
     * @param message 字符串内容
     */
    public static void toast(Context context, String message) {
        if (!TextUtils.isEmpty(message)) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 弹出长Toast提示
     *
     * @param message 定义在资源文件string中的提示内容，如R.String.hello
     */
    public static void toastLong(Context context, int message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    /**
     * 弹出长Toast提示
     *
     * @param message 字符串内容
     */
    public static void toastLong(Context context, String message) {
        if (!TextUtils.isEmpty(message)) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 弹出短Toast提示,显示在屏幕中间
     *
     * @param message 字符串内容
     */
    public static void toastOnCenter(Context context, String message) {
        if (!TextUtils.isEmpty(message)) {
            Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    /**
     * 弹出短Toast提示,显示在屏幕中间,带自定义图片
     *
     * @param message    字符串内容
     * @param drawableID 图片ID
     */
    public static void toastOnCenterWithImage(Context context, String message, int drawableID) {
        if (!TextUtils.isEmpty(message)) {
            Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            LinearLayout toastView = (LinearLayout) toast.getView();
            ImageView imageView = new ImageView(context);
            imageView.setImageResource(drawableID);
            toastView.addView(imageView, 0);
            toast.show();
        }
    }

    /**
     * 弹出系统默认转圈对话框，无标题和文字
     *
     * @param context
     */
    public static void showProgressDialog(Context context) {
        if (loadingDialog == null) {
            loadingDialog = ProgressDialog.show(context, "", "");
            loadingDialog.setCancelable(true);
            loadingDialog.setCanceledOnTouchOutside(false);
        }
        loadingDialog.show();
    }

    /**
     * 弹出系统默认转圈对话框，有标题和文字
     * 不要直接调用这个方法，请调用 PopStrategy.showProgressDialog(context);策略模式，方便任意替换
     *
     * @param context
     * @param title   标题
     * @param message 内容
     */
    public static void showProgressDialog(Context context, String title,
                                          String message) {
        if (loadingDialog == null) {
            loadingDialog = ProgressDialog.show(context, title, message);
            loadingDialog.setCancelable(true);//返回键可以取消圈圈
            loadingDialog.setCanceledOnTouchOutside(false);//点击圈圈周围的屏幕不可以取消圈圈
        }
        loadingDialog.show();
    }

    /**
     * 弹出自定义转圈对话框
     *
     * @param context
     * @param message 显示的文字，传""默认显示“请稍候”
     */
    public static void showMyProgressDialog(Context context, String message) {
        loadingDialog = ProgressDialog.show(context, "", "");
        loadingDialog.setContentView(R.layout.zbase_process_dialog_loading);
        TextView msgText = (TextView) loadingDialog.getWindow().findViewById(R.id.tvContent);
        if (message != null && !message.equals("")) {
            msgText.setText(message);
        }
        loadingDialog.setCancelable(true);
        loadingDialog.setCanceledOnTouchOutside(false);
        loadingDialog.show();
    }


    public static void showProgress(Context context) {
        loadingDialog = new Dialog(context, R.style.DialogStyle);
        loadingDialog.setContentView(R.layout.zbase_progress_bar);
        loadingDialog.setCancelable(true);
        loadingDialog.setCanceledOnTouchOutside(false);
        loadingDialog.show();
    }

    /**
     * 自定义单张图片旋转的Dialog
     *
     * @param context
     * @param msg     显示的文字
     * @return
     */
    public static void showLoadingDialog(Context context,
                                         String msg) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.zbase_loading_dialog, null);// 得到加载view
        LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);// 加载布局
        // main.xml中的ImageView
        ImageView spaceshipImage = (ImageView) v.findViewById(R.id.img);
        TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);// 提示文字
        // 加载动画
        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
                context, R.anim.zbase_loading_animation);
        // 使用ImageView显示动画
        spaceshipImage.startAnimation(hyperspaceJumpAnimation);
        tipTextView.setText(msg);// 设置加载信息
        loadingDialog = new Dialog(context, R.style.LoadingDialog);// 创建自定义样式dialog
        loadingDialog.setCancelable(true);
        loadingDialog.setCanceledOnTouchOutside(false);// true可以取消，false不能取消
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
        loadingDialog.show();
    }

    /**
     * 取消系统默认转圈对话框
     */
    public static void dismissProgressDialog() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }
    }

}
