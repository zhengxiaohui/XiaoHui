package com.meg7.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;

/**
 * Created by Mostafa Gazar on 11/2/13.
 * 圆角矩形
 */
public class RoundRectangleImageView extends BaseImageView {

    private static float rx;//圆角矩形x方向圆半径
    private static float ry;//圆角矩形y方向圆半径

    public RoundRectangleImageView(Context context) {
        super(context);
    }

    public RoundRectangleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        sharedConstructor(context, attrs);
    }

    public RoundRectangleImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        sharedConstructor(context, attrs);
    }

    private void sharedConstructor(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RoundRectangleImageView);
        rx = a.getDimension(R.styleable.RoundRectangleImageView_rx, 20.0f);//固定写法，样式名称_属性名称
        ry = a.getDimension(R.styleable.RoundRectangleImageView_ry,20.0f);//默认20
        a.recycle();
    }

    public static Bitmap getBitmap(int width, int height) {
        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        canvas.drawRoundRect(new RectF(0.0f, 0.0f, width, height),rx,ry, paint);
        return bitmap;
    }

    @Override
    public Bitmap getBitmap() {
        return getBitmap(getWidth(), getHeight());
    }
}
