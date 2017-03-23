package com.zbase.util;

import android.graphics.Paint;
import android.widget.TextView;

/**
 * 创建人：郑晓辉
 * 创建日期：2017/3/21
 * 描述：
 */
public class TextViewUtil {
    /**
     * 给TextView设置下划线
     *
     * @param textView
     */
    public void setTextViewUnderLine(TextView textView) {
        textView.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        textView.getPaint().setAntiAlias(true);//抗锯齿
    }
}
