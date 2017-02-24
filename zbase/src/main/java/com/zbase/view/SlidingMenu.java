package com.zbase.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

/**
 * 创建人：郑晓辉
 * 创建日期：2016/6/14
 * 描述：列表左滑出现菜单，配合BaseSlidingMenuAdapter使用
 */
public class SlidingMenu extends LinearLayout {

    private MyHorizontalScrollView horizontalScrollView;
    // 屏幕宽度
    private int mScreenWidth;
    // 菜单区域宽度
    private int mMenuWidth;
    // 因为onMeasure方法可能被多次调用，所以设置该标记
    private boolean once = false;
    // 判断菜单栏是否开启
    private boolean isOpen = false;
    private boolean scrollable = true;
    private OnMenuOpenListener onMenuOpenListener;

    /**
     * 未使用自定义属性时调用
     *
     * @param context
     * @param attrs
     */
    public SlidingMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        // 获取屏幕宽度
        mScreenWidth = outMetrics.widthPixels;
    }

    /**
     * 设置子View的宽和高 设置自己的宽和高(因为子View在内部，所以当决定子View宽高后也就决定自己的宽和高了)
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 为了只执行一次
        if (!once) {
            if (getChildCount() != 2) {
                throw new IllegalStateException("SlidingMenu控件子布局必须有且只有2个！");
            }
            View contentView = getChildAt(0);
            View menuView = getChildAt(1);
            ViewGroup.LayoutParams contentViewLayoutParams = contentView.getLayoutParams();
            ViewGroup.LayoutParams menuViewLayoutParams = menuView.getLayoutParams();
            removeAllViews();
            horizontalScrollView = new MyHorizontalScrollView(getContext());
            horizontalScrollView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            horizontalScrollView.setHorizontalScrollBarEnabled(false);
            LinearLayout linearLayout = new LinearLayout(getContext());
            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout.addView(contentView, contentViewLayoutParams);
            linearLayout.addView(menuView, menuViewLayoutParams);
            horizontalScrollView.addView(linearLayout);
            addView(horizontalScrollView);
            // 获取到菜单宽度
            if (menuView.getLayoutParams().width <= 0) {
                throw new IllegalStateException("布局中滑出的菜单必须设置固定宽度DP！");
            } else {
                mMenuWidth = menuView.getLayoutParams().width;
            }
            contentView.getLayoutParams().width = mScreenWidth;
            once = true;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 打开菜单
     */
    public void openMenu() {
        if (isOpen) {
            return;
        }
        horizontalScrollView.smoothScrollTo(mMenuWidth, 0);
        isOpen = true;
    }

    /**
     * 关闭菜单
     */
    public void closeMenu() {
        if (!isOpen) {
            return;
        }
        horizontalScrollView.smoothScrollTo(0, 0);
        isOpen = false;
    }

    /**
     * 菜单开关
     */
    public void toggle() {
        if (isOpen) {
            closeMenu();
        } else {
            openMenu();
        }
    }

    public void setScrollable(boolean scrollable) {
        this.scrollable = scrollable;
    }

    public interface OnMenuOpenListener {
        void onMenuOpen();
    }

    public void setOnMenuOpenListener(OnMenuOpenListener onMenuOpenListener) {
        this.onMenuOpenListener = onMenuOpenListener;
    }

    private class MyHorizontalScrollView extends HorizontalScrollView {

        public MyHorizontalScrollView(Context context) {
            super(context);
        }

        public MyHorizontalScrollView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public MyHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }


        /**
         * 通过设置偏移量，将menu隐藏
         */
        @Override
        protected void onLayout(boolean changed, int l, int t, int r, int b) {
            super.onLayout(changed, l, t, r, b);
            // 如果布局发生变化才执行
            if (changed) {
                this.scrollTo(0, 0);
            }
        }

        @Override
        public boolean onTouchEvent(MotionEvent ev) {
            int action = ev.getAction();
            switch (action) {
                case MotionEvent.ACTION_UP:
                    // 屏幕偏移的宽度(即隐藏在左边的宽度)
                    int scrollX = getScrollX();
                    if (scrollX >= mMenuWidth / 2) {
                        smoothScrollTo(mMenuWidth, 0);
                        if (onMenuOpenListener != null) {
                            onMenuOpenListener.onMenuOpen();
                        }
                        isOpen = true;
                    } else {
                        smoothScrollTo(0, 0);
                        isOpen = false;
                    }
                    return true;
                case MotionEvent.ACTION_MOVE:
                    if (scrollable) {
                        return super.onTouchEvent(ev);
                    } else {
                        return true;
                    }
            }
            return super.onTouchEvent(ev);
        }
    }
}

