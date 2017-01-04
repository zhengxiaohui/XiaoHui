package com.zbase.view.popupwindow;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.PaintDrawable;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.zbase.R;

/**
 * 创建人：郑晓辉
 * 创建日期：2015/10/13
 * 主题：普通的PopupWindow，只设置了可点击等属性以及可以自适应的宽高
 * 描述：
 */
public class NormalPopupWindow extends PopupWindow {

    private int popViewWidth;
    private int popViewHeight;
    private View view;

    public NormalPopupWindow(View view) {
        super(view, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        init(view);
    }

    /**
     * @param view
     * @param width  可以使用LinearLayout.LayoutParams.WRAP_CONTENT||LinearLayout.LayoutParams.MATCH_CONTENT||pixl
     * @param height 可以使用LinearLayout.LayoutParams.WRAP_CONTENT||LinearLayout.LayoutParams.MATCH_CONTENT||pixl
     */
    public NormalPopupWindow(View view, int width, int height) {
        super(view, width, height);
        init(view);
    }

    private void init(View view) {
        this.view = view;
        setBackgroundDrawable(new PaintDrawable(Color.TRANSPARENT));
        setFocusable(true);
        setTouchable(true);
        setOutsideTouchable(true);
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        popViewWidth = view.getMeasuredWidth();
        popViewHeight = view.getMeasuredHeight();
    }

    /**
     * 设置点击背景不消失，点击回退键会消失
     */
    public void setOutsideTouchNotDismissExceptKeyBack() {
        setBackgroundDrawable(null);
        setOutsideTouchable(false);
        view.setFocusable(true);// 这个很重要，如果没设置，onKey执行不到
        view.setFocusableInTouchMode(true);
        view.setOnKeyListener(new View.OnKeyListener() {
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

    /**
     * 显示在参照物view的上面正中间
     * 注意：这里面的RecyclerView的宽度和高度必须是固定值dp才能计算出popViewWidth和popViewHeight的值
     *
     * @param anchor
     */
    public void showUpCenter(View anchor) {
        int[] location = new int[2];
        anchor.getLocationOnScreen(location);
        showAtLocation(anchor, Gravity.NO_GRAVITY,
                location[0] + (anchor.getWidth() - popViewWidth) / 2, location[1] - popViewHeight);
    }

    /**
     * 显示在参照物view的下面正中间，showAsDropDown默认方法是显示在下面的左边
     * 注意：这里面的RecyclerView的宽度必须是固定值dp才能计算出popViewWidth的值
     *
     * @param anchor
     */
    public void showDownCenter(View anchor) {
        int[] location = new int[2];
        anchor.getLocationOnScreen(location);
        showAsDropDown(anchor, (anchor.getWidth() - popViewWidth) / 2, 0);
    }

    /**
     * 显示在屏幕的正中间
     *
     * @param activity
     */
    public void showAtCenter(Activity activity) {
        showAtLocation(activity.getWindow().getDecorView(), Gravity.CENTER, 0, 0);
    }

    /**
     * 显示在屏幕的正下方
     *
     * @param activity
     */
    public void showAtBottom(Activity activity) {
        showAtLocation(activity.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    }

    /**
     * 带动画的popupwindow从屏幕下方移动进入，显示在屏幕底部，消失也是从下方移出
     *
     * @param activity
     */
    public void showAtBottomWithUpDownAnim(Activity activity) {
        setAnimationStyle(R.style.PopwinAnimBottomInOut);
        showAtLocation(activity.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    }

}
