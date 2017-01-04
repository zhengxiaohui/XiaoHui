package com.zbase.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

/**
 * 通讯录等按拼音首字母分组排序快速地位搜索右侧贴边字母列表控件
 */
public class SideBar extends View {

    private OnTouchingLetterChangedListener onTouchingLetterChangedListener; // 触摸事件
    // 26个字母
    public static String[] b = {"A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z", "#"};
    private int choose = -1;// 选中
    private Paint paint = new Paint();

    private TextView floatTextView;//选中后屏幕中间弹出的文字控件

    public void setFloatTextView(TextView floatTextView) {
        this.floatTextView = floatTextView;
        floatTextView.setVisibility(View.INVISIBLE);
    }

    private int textColor = Color.BLACK;
    private int selectedTextColor = Color.BLUE;
    private int textSize = 25;
    private int backgroundDrawableId;
    private int selectedBackgroundDrawableId;
    private boolean keepPressColor;//是否保留选中颜色，默认false不保留

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public void setSelectedTextColor(int selectedTextColor) {
        this.selectedTextColor = selectedTextColor;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public void setBackgroundDrawableId(int backgroundDrawableId) {
        this.backgroundDrawableId = backgroundDrawableId;
    }

    public void setSelectedBackgroundDrawableId(int selectedBackgroundDrawableId) {
        this.selectedBackgroundDrawableId = selectedBackgroundDrawableId;
    }

    public void setKeepPressColor(boolean keepPressColor) {
        this.keepPressColor = keepPressColor;
    }

    public SideBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public SideBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SideBar(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int height = getHeight();// 获取对应高度
        int width = getWidth(); // 获取对应宽度
        int singleHeight = height / b.length;// 获取每一个字母的高度

        for (int i = 0; i < b.length; i++) {
            paint.setColor(textColor);
            paint.setTypeface(Typeface.DEFAULT_BOLD);
            paint.setAntiAlias(true);
            paint.setTextSize(textSize);
            // 选中的状态
            if (i == choose) {
                paint.setColor(selectedTextColor);
                paint.setFakeBoldText(true);
            }
            float xPos = width / 2 - paint.measureText(b[i]) / 2;
            float yPos = singleHeight * i + singleHeight;
            canvas.drawText(b[i], xPos, yPos, paint);
            paint.reset();// 重置画笔
        }

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        final float y = event.getY();// 点击y坐标
        final int oldChoose = choose;
        final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;
        final int c = (int) (y / getHeight() * b.length);// 点击y坐标所占总高度的比例*b数组的长度就等于点击b中的个数.

        switch (action) {
            case MotionEvent.ACTION_UP:
                setBackgroundResource(backgroundDrawableId);
                if (!keepPressColor) {
                    choose = -1;
                }
                invalidate();
                if (floatTextView != null) {
                    floatTextView.setVisibility(View.INVISIBLE);
                }
                break;

            default:
                setBackgroundResource(selectedBackgroundDrawableId);
                if (oldChoose != c) {
                    if (c >= 0 && c < b.length) {
                        if (listener != null) {
                            listener.onTouchingLetterChanged(b[c]);
                        }
                        if (floatTextView != null) {
                            floatTextView.setText(b[c]);
                            floatTextView.setVisibility(View.VISIBLE);
                        }

                        choose = c;
                        invalidate();
                    }
                }

                break;
        }
        return true;
    }

    public void setOnTouchingLetterChangedListener(
            OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
        this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
    }

    public interface OnTouchingLetterChangedListener {
        void onTouchingLetterChanged(String s);
    }

}