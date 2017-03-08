package com.zbase.view.popupwindow;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.zbase.R;

/**
 * 创建人：郑晓辉
 * 创建日期：2015/10/13
 * 主题：可控制窗体背景透明度的PopupWindow类
 * 描述：
 */
public class AlphaPopupWindow extends NormalPopupWindow {

    protected Context context;
    private float alpha = 1.0f;//默认背景不透明,透明度介于0.1-1.0之间，数值越低，透明度越低，就越暗
    private AfterDismissListener afterDismissListener;

    public void setAfterDismissListener(AfterDismissListener afterDismissListener) {
        this.afterDismissListener = afterDismissListener;
    }

    public interface AfterDismissListener {
        void afterDismiss();
    }

    public AlphaPopupWindow(Context context, View view) {
        super(view);
        init(context);
    }

    /**
     * @param view
     * @param width  可以使用LinearLayout.LayoutParams.WRAP_CONTENT||LinearLayout.LayoutParams.MATCH_CONTENT||pixl
     * @param height 可以使用LinearLayout.LayoutParams.WRAP_CONTENT||LinearLayout.LayoutParams.MATCH_CONTENT||pixl
     */
    public AlphaPopupWindow(Context context, View view, int width, int height) {
        super(view, width, height);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                setWindowBackgroundTranslucence(false);
                if (afterDismissListener != null) {
                    afterDismissListener.afterDismiss();
                }
            }
        });
    }

    /**
     * 设置窗体背景头透明度
     *
     * @param alpha
     */
    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    /**
     * 设置窗体背景变暗，默认透明度0.5
     *
     * @param isDark
     */
    public void setDark(boolean isDark) {
        if (isDark) {
            alpha = 0.7f;//透明度介于0.1-1.0之间，数值越低，透明度越低，就越暗
        }
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);
        setWindowBackgroundTranslucence(true);
    }

    @Override
    public void showAsDropDown(View view) {
        super.showAsDropDown(view);
        setWindowBackgroundTranslucence(true);
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff) {
        super.showAsDropDown(anchor, xoff, yoff);
        setWindowBackgroundTranslucence(true);
    }

    @Override
    public void showUpCenter(View anchor) {
        super.showUpCenter(anchor);
        setWindowBackgroundTranslucence(true);
    }

    @Override
    public void showDownCenter(View anchor) {
        super.showDownCenter(anchor);
        setWindowBackgroundTranslucence(true);
    }

    @Override
    public void showAtCenter(Activity activity) {
        super.showAtCenter(activity);
        setWindowBackgroundTranslucence(true);
    }

    @Override
    public void showAtBottom(Activity activity) {
        super.showAtBottom(activity);
        setWindowBackgroundTranslucence(true);
    }

    @Override
    public void showAtBottomWithUpDownAnim(Activity activity) {
        setAnimationStyle(R.style.PopwinAnimBottomInOut);
        showAtLocation(activity.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    }

    /**
     * 设置窗体背景是否半透明
     *
     * @param isTranslucence 是否半透明
     */
    public void setWindowBackgroundTranslucence(boolean isTranslucence) {
        Window window = ((Activity) context).getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        if (isTranslucence) {
            layoutParams.alpha = alpha;
        } else {
            layoutParams.alpha = 1.0f;
        }
        window.setAttributes(layoutParams);
    }

}
