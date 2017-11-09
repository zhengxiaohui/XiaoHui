package com.zbase.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Paint;
import android.support.design.widget.TabLayout;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zbase.listener.AfterClickListener;
import com.zbase.listener.OnObtainViewWidthHeightListener;

import java.lang.reflect.Field;

/**
 * 创建人：郑晓辉
 * 创建日期：2017/10/12 0012
 * 描述：
 */
public class ViewUtil {

    /**
     * 获取View的实际宽高
     *
     * @param view
     * @param onObtainViewWidthHeightListener
     */
    public static void getViewWidthHeight(final View view, final OnObtainViewWidthHeightListener onObtainViewWidthHeightListener) {
        final ViewTreeObserver viewTreeObserver = view.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                viewTreeObserver.removeOnGlobalLayoutListener(this);
                if (onObtainViewWidthHeightListener != null) {
                    onObtainViewWidthHeightListener.onObtainViewWidthHeight(view.getWidth(), view.getHeight());
                }
            }
        });
    }

    /**
     * 给TextView设置下划线
     *
     * @param textView
     */
    public static void setTextViewUnderLine(TextView textView) {
        textView.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        textView.getPaint().setAntiAlias(true);//抗锯齿
    }

    /**
     * 文字超过设置的最大行数，则显示按钮（显示全部）。点击按钮放开最大行数，这里设置100行
     * 注意：一定要在设置完文本之后再调用。
     *
     * @param tv_content
     * @param button
     * @param maxLine
     */
    public static void setTextViewMaxLinesWithButtonHide(final TextView tv_content, final View button, final int maxLine) {
        tv_content.setMaxLines(maxLine);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_content.setMaxLines(100);
                button.setVisibility(View.GONE);
            }
        });
        tv_content.post(new Runnable() {
            @Override
            public void run() {
                if (judgeFull(tv_content, maxLine)) {
                    button.setVisibility(View.VISIBLE);
                } else {
                    button.setVisibility(View.GONE);
                }
            }
        });
    }

    /**
     * 判断文字总长度是否超过最大行数
     *
     * @param textView
     * @param maxLine  最大行数
     * @return
     */
    private static boolean judgeFull(TextView textView, int maxLine) {
        return textView.getPaint().measureText(textView.getText().toString()) > maxLine * (textView.getWidth() -
                textView.getPaddingRight() - textView.getPaddingLeft());
    }

    /**
     * 设置EditText的点击事件
     *
     * @param context
     * @param editText
     * @param afterClickListener
     */
    public static void setEditTextAfterClickListener(final Context context, final EditText editText, final AfterClickListener afterClickListener) {
        editText.setClickable(true);
        editText.setFocusableInTouchMode(false);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setFocusable(true);
                editText.setFocusableInTouchMode(true);
                editText.requestFocus();
                editText.requestFocusFromTouch();
                SoftKeyboardUtil.showSoftKeyboard(context, editText);
                if (afterClickListener != null) {
                    afterClickListener.afterClick(v);
                }
            }
        });
    }

    /**
     * 设置TabLayout横线的左右间距
     *
     * @param tabs     TabLayout
     * @param leftDip  左边距离
     * @param rightDip 右边距离
     */
    public static void setTabLayoutIndicatorWidth(final TabLayout tabs, final int leftDip, final int rightDip) {
        tabs.post(new Runnable() {
            @Override
            public void run() {//TabLayout渲染出来后调用
                Class<?> tabLayout = tabs.getClass();
                Field tabStrip = null;
                try {
                    tabStrip = tabLayout.getDeclaredField("mTabStrip");
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
                tabStrip.setAccessible(true);
                LinearLayout llTab = null;
                try {
                    llTab = (LinearLayout) tabStrip.get(tabs);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
                int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());
                for (int i = 0; i < llTab.getChildCount(); i++) {
                    View child = llTab.getChildAt(i);
                    child.setPadding(0, 0, 0, 0);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
                    params.leftMargin = left;
                    params.rightMargin = right;
                    child.setLayoutParams(params);
                    child.invalidate();
                }
            }
        });
    }

    /**
     * 设置TabLayout的下划线和文字宽度一样长，必须设置app:tabMode="scrollable" 才有效果
     *
     * @param tabLayout
     */
    public static void setTabLayoutIndicatorWidthEquilong(final TabLayout tabLayout) {
        //了解源码得知 线的宽度是根据 tabView的宽度来设置的
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                try {
                    //拿到tabLayout的mTabStrip属性
                    LinearLayout mTabStrip = (LinearLayout) tabLayout.getChildAt(0);
                    int px10 = ScreenUtil.dip2px(tabLayout.getContext(), 10);
                    for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                        View tabView = mTabStrip.getChildAt(i);
                        //拿到tabView的mTextView属性  tab的字数不固定一定用反射取mTextView
                        Field mTextViewField = tabView.getClass().getDeclaredField("mTextView");
                        mTextViewField.setAccessible(true);
                        TextView mTextView = (TextView) mTextViewField.get(tabView);
                        tabView.setPadding(0, 0, 0, 0);
                        //因为我想要的效果是   字多宽线就多宽，所以测量mTextView的宽度
                        int width = 0;
                        width = mTextView.getWidth();
                        if (width == 0) {
                            mTextView.measure(0, 0);
                            width = mTextView.getMeasuredWidth();
                        }
                        //设置tab左右间距为10dp  注意这里不能使用Padding 因为源码中线的宽度是根据 tabView的宽度来设置的
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tabView.getLayoutParams();
                        params.width = width;
                        params.leftMargin = px10;
                        params.rightMargin = px10;
                        tabView.setLayoutParams(params);
                        tabView.invalidate();
                    }
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
