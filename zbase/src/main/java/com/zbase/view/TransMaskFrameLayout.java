package com.zbase.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.zbase.R;


/**
 * 创建人：郑晓辉
 * 创建日期：2017/1/9
 * 描述：半透明遮罩FrameLayout，可用于遮罩TextView，ImageView等。
 * 效果：按下的时候文字或图片被半透明遮罩遮住
 */
public class TransMaskFrameLayout extends FrameLayout {

    private ImageView imageView;
    private int maskCorner;//圆角弧度

    public int getMaskCorner() {
        return maskCorner;
    }

    public void setMaskCorner(int maskCorner) {
        this.maskCorner = maskCorner;
    }

    public TransMaskFrameLayout(Context context) {
        super(context);
        init(null, 0);
    }

    public TransMaskFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public TransMaskFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyle) {
        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.TransMaskFrameLayout, defStyle, 0);
        maskCorner = a.getInt(R.styleable.TransMaskFrameLayout_maskCorner, 10);//默认10
        a.recycle();
        loadViews();
    }

    private void loadViews() {

    }

    /**
     * 此方法会在所有的控件都从xml文件中加载完成后调用
     */
    @Override
    protected void onFinishInflate() {
        View childView = getChildAt(0);
        childView.measure(0, 0);
        imageView = new ImageView(getContext());
        LayoutParams imageLayoutParams = new LayoutParams(childView.getMeasuredWidth(), childView.getMeasuredHeight());
        imageLayoutParams.gravity = Gravity.CENTER;
        imageView.setLayoutParams(imageLayoutParams);
        float[] outerR = new float[]{maskCorner, maskCorner, maskCorner, maskCorner, maskCorner, maskCorner, maskCorner, maskCorner}; // 外部矩形弧度
        RectF inset = new RectF(0, 0, 0, 0); // 内部矩形与外部矩形的距离
        final RoundRectShape roundRectShape = new RoundRectShape(outerR, inset, null);
        imageView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ShapeDrawable shapeDrawable;
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        shapeDrawable = new ShapeDrawable(roundRectShape);
                        shapeDrawable.getPaint().setStyle(Paint.Style.FILL); // 指定填充模式
                        shapeDrawable.getPaint().setColor(getContext().getResources().getColor(R.color.transparent30));//指定填充颜色
                        imageView.setBackground(shapeDrawable);
                        break;
                    case MotionEvent.ACTION_UP:
                        shapeDrawable = new ShapeDrawable(roundRectShape);
                        shapeDrawable.getPaint().setStyle(Paint.Style.FILL); // 指定填充模式
                        shapeDrawable.getPaint().setColor(getContext().getResources().getColor(R.color.transparent));//指定填充颜色
                        imageView.setBackground(shapeDrawable);
                        break;
                }
                return true;
            }
        });
        addView(imageView);
    }

}
