package com.zbase.manager;

import android.support.annotation.ColorInt;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;

/**
 * 创建人：郑晓辉
 * 创建日期：2016/8/19
 * 描述：SpannableString复杂文本管理器
 */
public class SpannableStringManager {

    private SpannableStringBuilder spannableStringBuilder;

    public SpannableStringManager(String text) {
        this.spannableStringBuilder = new SpannableStringBuilder(text);
    }

    public SpannableStringBuilder getSpannableStringBuilder() {
        return spannableStringBuilder;
    }

    /**
     * @param start 开始位置
     * @param end   结束位置
     * @param color 字体颜色 必须传getResources().getColor(R.color.red)的形式
     * @return
     */
    public SpannableStringManager setTextColor(int start, int end, @ColorInt int color) {
        if (color != 0) {
            ForegroundColorSpan colorSpan = new ForegroundColorSpan(color);
            spannableStringBuilder.setSpan(colorSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return this;
    }

    /**
     *
     * @param start
     * @param end
     * @param textSize 文字大小，单位为dp
     * @return
     */
    public SpannableStringManager setTextSize(int start, int end, int textSize) {
        if (textSize != 0) {
            AbsoluteSizeSpan sizeSpan = new AbsoluteSizeSpan(textSize, true);//有true才是dp单位，不然是px
            spannableStringBuilder.setSpan(sizeSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return this;
    }

    /**
     * 设置复杂文本点击事件，textView.setText()之后必须调用textView.setMovementMethod(LinkMovementMethod.getInstance());//响应点击事件
     *
     * @param start
     * @param end
     * @param hasUnderLine 是否有下划线
     * @param onClickListener
     */
    public SpannableStringManager setTextClick(int start, int end, boolean hasUnderLine, View.OnClickListener onClickListener) {
        MyClickableSpan myClickableSpan = new MyClickableSpan(hasUnderLine, onClickListener);
        spannableStringBuilder.setSpan(myClickableSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    /**
     * 点击事件
     */
    private class MyClickableSpan extends ClickableSpan {
        private boolean hasUnderLine;
        private View.OnClickListener onClickListener;

        public MyClickableSpan(boolean hasUnderLine, View.OnClickListener onClickListener) {
            this.hasUnderLine = hasUnderLine;
            this.onClickListener = onClickListener;
        }

        @Override
        public void onClick(View widget) {
            if (onClickListener != null) {
                onClickListener.onClick(widget);
            }
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setUnderlineText(hasUnderLine);
        }

    }
}
