package com.zbase.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

import com.zbase.R;

import java.util.ArrayList;
import java.util.List;


/**
 * 创建人：郑晓辉
 * 创建日期：2016/6/22
 * 描述：根据点的集合绘制任意图形
 */
public class PathDrawView extends View {

    private Paint paint;
    private int pathDrawFillColor = Color.RED;
    private List<Point> pointList = new ArrayList<>();

    public PathDrawView(Context context) {
        super(context);
        init(null, 0);
    }

    public PathDrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public PathDrawView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyle) {
        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.PathDrawView, defStyle, 0);
        pathDrawFillColor = a.getColor(R.styleable.PathDrawView_pathDrawFillColor,pathDrawFillColor);
        a.recycle();
        loadViews();
    }

    private void loadViews() {
        paint = new Paint();
        paint.setColor(pathDrawFillColor);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (pointList.size() == 0) {
            return;
        }
        Path path = new Path();
        path.moveTo(pointList.get(0).x, pointList.get(0).y);
        for (int i = 1; i < pointList.size(); i++) {
            path.lineTo(pointList.get(i).x, pointList.get(i).y);
        }
        path.close();
        canvas.drawPath(path, paint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (pointList.size() == 0) {
            return;
        }
        int width = 0;
        int height = 0;
        for (int i = 0; i < pointList.size(); i++) {
            width = Math.max(width, pointList.get(i).x);
            height = Math.max(height, pointList.get(i).y);
        }
        setMeasuredDimension(width, height);
    }

    public int getPathDrawFillColor() {
        return pathDrawFillColor;
    }

    public void setPathDrawFillColor(int pathDrawFillColor) {
        this.pathDrawFillColor = pathDrawFillColor;
    }

    public void setPointList(List<Point> pointList) {
        this.pointList = pointList;
    }
}
