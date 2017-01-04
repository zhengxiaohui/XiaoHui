package com.zbase.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.widget.EditText;

import com.zbase.R;
import com.zbase.listener.OnInputFinishListener;
import com.zbase.util.ScreenUtil;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;

/**
 * 创建人：CXQ
 * 创建日期：2016/8/8
 * 描述：多位密码输入框，一般设置6位
 * 使用：
 * <com.zbase.view.PasswordEditText
 android:id="@+id/passwordEditText"
 android:layout_width="247dp"
 android:layout_height="42dp"
 android:layout_marginLeft="26dp"
 android:layout_marginRight="26dp"
 android:layout_marginTop="19dp"
 android:inputType="number"
 app:borderRadius="0dp"
 app:borderStrokeWidth="1dp"
 app:passwordBackground="@color/white"
 app:textWordColor="@color/c6"
 app:textWordLength="6"
 app:textWordRadius="8dp" />

 passwordEditText.setOnInputFinishListener(new PasswordEditText.OnInputFinishListener() {
@Override
public void onInputFinish(String password) {
requestDealPwd();
passwordEditText.setText("");
inputPasswordAlphaPopupWindow.dismiss();
passwordEditText.clearFocus();
}
});
 */
public class PasswordEditText extends EditText {
    private int borderStrokeColor = 0xFFDCDCDC;
    private float borderStrokeWidth;
    private float borderRadius;
    private int passwordBackground = Color.WHITE;
    private int textWordLength;
    private int textWordColor = Color.BLACK;
    private float textWordRadius;
    private Paint passwordPaint = new Paint(ANTI_ALIAS_FLAG);
    private Paint borderPaint = new Paint(ANTI_ALIAS_FLAG);
    private int textLength;
    /**
     * 输入结束监听
     */
    private OnInputFinishListener mOnInputFinishListener;

    public PasswordEditText(Context context) {
        super(context);
        getAttrs(null, 0);
    }

    public PasswordEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        getAttrs(attrs, 0);
    }

    public PasswordEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getAttrs(attrs, defStyleAttr);
    }

    private void getAttrs(AttributeSet attrs, int defStyle) {
        borderStrokeWidth = ScreenUtil.dip2px(getContext(), 1);
        borderRadius = ScreenUtil.dip2px(getContext(), 0);
        textWordLength = 6;
        textWordRadius = ScreenUtil.dip2px(getContext(), 8);
        TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.PasswordEditText, 0, 0);
        borderStrokeColor = a.getColor(R.styleable.PasswordEditText_borderStrokeColor, borderStrokeColor);
        borderStrokeWidth = a.getDimension(R.styleable.PasswordEditText_borderStrokeWidth, borderStrokeWidth);
        passwordBackground = a.getColor(R.styleable.PasswordEditText_passwordBackground, passwordBackground);
        borderRadius = a.getDimension(R.styleable.PasswordEditText_borderRadius, borderRadius);
        textWordLength = a.getInt(R.styleable.PasswordEditText_textWordLength, textWordLength);
        textWordColor = a.getColor(R.styleable.PasswordEditText_textWordColor, textWordColor);
        textWordRadius = a.getDimension(R.styleable.PasswordEditText_textWordRadius, textWordRadius);
        a.recycle();
        initValue();
    }

    private void initValue() {
        borderPaint.setStrokeWidth(borderStrokeWidth);
        borderPaint.setColor(borderStrokeColor);
        passwordPaint.setStyle(Paint.Style.FILL);
        passwordPaint.setColor(textWordColor);
        setFilters(new InputFilter[]{new InputFilter.LengthFilter(textWordLength)});
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();
        // 外边框
        RectF rect = new RectF(0, 0, width, height);
        borderPaint.setColor(borderStrokeColor);
        canvas.drawRoundRect(rect, borderRadius, borderRadius, borderPaint);
        // 内容区,这边主要是设置背景色，默认是白色
        RectF rectIn = new RectF(rect.left + borderStrokeWidth, rect.top + borderStrokeWidth,
                rect.right - borderStrokeWidth, rect.bottom - borderStrokeWidth);
        borderPaint.setColor(passwordBackground);
        canvas.drawRoundRect(rectIn, borderRadius, borderRadius, borderPaint);
        // 分割线
        borderPaint.setColor(borderStrokeColor);
        for (int i = 1; i < textWordLength; i++) {
            float x = width * i / textWordLength;
            canvas.drawLine(x, 0, x, height, borderPaint);
        }
        // 密码
        float cx, cy = height / 2;
        float half = width / textWordLength / 2;
        for (int i = 0; i < textLength; i++) {
            cx = width * i / textWordLength + half;
            canvas.drawCircle(cx, cy, textWordRadius, passwordPaint);
        }
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        this.textLength = text.toString().length();
        invalidate();
        if (textLength == textWordLength && mOnInputFinishListener != null) {
            mOnInputFinishListener.onInputFinish(text.toString());
        }
    }

    /**
     * 设置输入完成监听
     *
     * @param onInputFinishListener
     */
    public void setOnInputFinishListener(
            OnInputFinishListener onInputFinishListener) {
        this.mOnInputFinishListener = onInputFinishListener;
    }

    public int getBorderColor() {
        return borderStrokeColor;
    }

    public void setBorderColor(int borderColor) {
        this.borderStrokeColor = borderColor;
        borderPaint.setColor(borderColor);
        invalidate();
    }

    public float getBorderWidth() {
        return borderStrokeWidth;
    }

    public void setBorderWidth(float borderStrokeWidth) {
        this.borderStrokeWidth = borderStrokeWidth;
        borderPaint.setStrokeWidth(borderStrokeWidth);
        invalidate();
    }

    public float getBorderRadius() {
        return borderRadius;
    }

    public void setBorderRadius(float borderRadius) {
        this.borderRadius = borderRadius;
        invalidate();
    }

    public int getPasswordLength() {
        return textWordLength;
    }

    public void setPasswordLength(int textWordLength) {
        this.textWordLength = textWordLength;
        invalidate();
    }

    public int getPasswordColor() {
        return textWordColor;
    }

    public void setPasswordColor(int textWordColor) {
        this.textWordColor = textWordColor;
        passwordPaint.setColor(textWordColor);
        invalidate();
    }

    public int getEditTextBackground() {
        return passwordBackground;
    }

    public void setEditTextBackground(int backgroundColor) {
        this.passwordBackground = backgroundColor;
        borderPaint.setColor(backgroundColor);
        invalidate();
    }

    public float getPasswordRadius() {
        return textWordRadius;
    }

    public void setPasswordRadius(float textWordRadius) {
        this.textWordRadius = textWordRadius;
        invalidate();
    }
}
