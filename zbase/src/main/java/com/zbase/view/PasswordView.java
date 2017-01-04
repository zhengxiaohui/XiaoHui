package com.zbase.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.InputFilter;
import android.text.InputType;
import android.util.AttributeSet;
import android.widget.EditText;

import com.zbase.R;
import com.zbase.listener.OnInputFinishListener;
import com.zbase.util.ScreenUtil;

/**
 * 支付界面输入6位密码控件
 *
 * @author zhangke
 */
public class PasswordView extends EditText {

    /**
     * 间隔，设置0就是连在一起的框，设置>0就是分开的框，间距
     */
    private int passwordSpaing;
    /**
     * 密码黑色圆圈半径大小
     */
    private int circleRadius;
    /**
     * 密码长度
     */
    private int passwordLength;
    /**
     * 单个格子的宽度
     */
    private int cellWidth;
    /**
     * 单个格子的高度
     */
    private int cellHeight;
    /**
     * 密码框
     */
    private Rect mRect;

    /**
     * 密码画笔
     */
    private Paint mPwdPaint;

    /**
     * 密码框画笔
     */
    private Paint mRectPaint;
    /**
     * 输入的密码长度
     */
    private int mInputLength;

    /**
     * 输入结束监听
     */
    private OnInputFinishListener mOnInputFinishListener;

    private int totalWidth;//控件总长度

    public PasswordView(Context context) {
        super(context);
        init(null, 0);
    }

    public PasswordView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public PasswordView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.PasswordView, defStyle, 0);

        cellWidth = a.getDimensionPixelOffset(R.styleable.PasswordView_cellWidth, ScreenUtil.dip2px(getContext(), 40));//默认40dp

        cellHeight = a.getDimensionPixelOffset(R.styleable.PasswordView_cellHeight, ScreenUtil.dip2px(getContext(), 40));//默认40dp

        circleRadius = a.getDimensionPixelOffset(R.styleable.PasswordView_circleRadius, ScreenUtil.dip2px(getContext(), 8));//默认8dp

        passwordSpaing = a.getDimensionPixelOffset(R.styleable.PasswordView_passwordSpaing, ScreenUtil.dip2px(getContext(), 0));//默认0dp

        passwordLength = a.getInt(R.styleable.PasswordView_passwordLength, 6);//默认密码长度6

        a.recycle();
        loadViews();
    }

    /**
     * 加载控件
     */
    private void loadViews() {
        setBackground(null);
        setFilters(new InputFilter[]{new InputFilter.LengthFilter(passwordLength)});
        setCursorVisible(false);
        setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL);
        totalWidth = this.cellWidth * passwordLength + passwordSpaing * (passwordLength);
        setWidth(totalWidth);
        setHeight(cellHeight);

        // 初始化密码画笔
        mPwdPaint = new Paint();
        mPwdPaint.setColor(Color.BLACK);
        mPwdPaint.setStyle(Paint.Style.FILL);
        mPwdPaint.setAntiAlias(true);
        // 初始化密码框
        mRectPaint = new Paint();
        mRectPaint.setStyle(Paint.Style.STROKE);
        mRectPaint.setColor(Color.LTGRAY);
        mRectPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 这三行代码非常关键，大家可以注释点在看看效果
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        canvas.drawRect(0, 0, totalWidth, cellHeight, paint);

        // 绘制密码框
        for (int i = 0; i < passwordLength; i++) {
            int left = (cellWidth + passwordSpaing) * i;
            int top = 0;
            int right = left + cellWidth;
            int bottom = cellHeight - top;
            mRect = new Rect(left, top, right, bottom);
            canvas.drawRect(mRect, mRectPaint);
        }

        // 绘制密码黑色圆圈
        for (int i = 0; i < mInputLength; i++) {
            int cx = cellWidth / 2 + (cellWidth + passwordSpaing) * i;
            int cy = cellHeight / 2;
            canvas.drawCircle(cx, cy, circleRadius, mPwdPaint);
        }
    }

    @Override
    protected void onTextChanged(CharSequence text, int start,
                                 int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        this.mInputLength = text.toString().length();
        invalidate();
        if (mInputLength == passwordLength && mOnInputFinishListener != null) {
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

    public int getPasswordSpaing() {
        return passwordSpaing;
    }

    public void setPasswordSpaing(int passwordSpaing) {
        this.passwordSpaing = passwordSpaing;
    }

    public int getCircleRadius() {
        return circleRadius;
    }

    public void setCircleRadius(int circleRadius) {
        this.circleRadius = circleRadius;
    }

    public int getPasswordLength() {
        return passwordLength;
    }

    public void setPasswordLength(int passwordLength) {
        this.passwordLength = passwordLength;
    }

    public int getCellWidth() {
        return cellWidth;
    }

    public void setCellWidth(int cellWidth) {
        this.cellWidth = cellWidth;
    }

    public int getCellHeight() {
        return cellHeight;
    }

    public void setCellHeight(int cellHeight) {
        this.cellHeight = cellHeight;
    }
}

