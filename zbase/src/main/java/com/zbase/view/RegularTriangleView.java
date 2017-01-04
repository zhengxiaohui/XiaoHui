package com.zbase.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import com.zbase.R;


/**
 * 创建人：郑晓辉
 * 创建日期：2016/6/22
 * 描述：正三角形
 */
public class RegularTriangleView extends View {

    private Paint paint;
    private int triangleRadius =30;//正三角形外包园的半径，即底边的一半
    private int triangleFillColor = Color.RED;

    public RegularTriangleView(Context context) {
        super(context);
        init(null, 0);
    }

    public RegularTriangleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public RegularTriangleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyle) {

        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.RegularTriangleView, defStyle, 0);

        triangleRadius = a.getDimensionPixelOffset(
                R.styleable.RegularTriangleView_triangleRadius,
                triangleRadius);

        triangleFillColor = a.getColor(
                R.styleable.RegularTriangleView_triangleFillColor,
                triangleFillColor);

        a.recycle();
        loadViews();
    }

    private void loadViews() {
        paint = new Paint();
        paint.setColor(triangleFillColor);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Path path = new Path();
        path.moveTo(triangleRadius, 0);
        path.lineTo(0, triangleRadius);
        path.lineTo(triangleRadius * 2, triangleRadius);
        path.close();
        canvas.drawPath(path, paint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(triangleRadius * 2, triangleRadius);
    }

    public int getTriangleRadius() {
        return triangleRadius;
    }

    public void setTriangleRadius(int triangleRadius) {
        this.triangleRadius = triangleRadius;
    }

    public int getTriangleFillColor() {
        return triangleFillColor;
    }

    public void setTriangleFillColor(int triangleFillColor) {
        this.triangleFillColor = triangleFillColor;
    }
}
