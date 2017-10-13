package com.zbase.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Paint;
import android.support.design.widget.TabLayout;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zbase.listener.AfterClickListener;

import java.lang.reflect.Field;

/**
 * 创建人：郑晓辉
 * 创建日期：2017/10/12 0012
 * 描述：
 */
public class ViewUtil {

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
     * @param tabs TabLayout
     * @param leftDip 左边距离
     * @param rightDip 右边距离
     */
    public static void setTabLayoutIndicatorWidth(final TabLayout tabs, final int leftDip, final int rightDip) {
        tabs.post(new Runnable() {//TabLayout渲染出来后调用
            @Override
            public void run() {
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
     * @param tabLayout
     */
    public static void setTabLayoutIndicatorWidthEquilong(final TabLayout tabLayout){
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
                        params.width = width ;
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
