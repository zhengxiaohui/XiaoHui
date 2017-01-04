package com.zbase.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.zbase.R;

/**
 * 创建人：郑晓辉
 * 创建日期：2015/11/25
 * 描述：颜色矩阵图片控件
 */
public class ColorMatrixView extends ImageView {

    private Paint myPaint = null;
    private Bitmap bitmap = null;
    private ColorMatrix myColorMatrix = null;
    private float[] colorArray = {1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0};

    public ColorMatrixView(Context context, AttributeSet attrs) {
        super(context, attrs);
        bitmap = ((BitmapDrawable) getDrawable()).getBitmap();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //新建画笔对象
        myPaint = new Paint();
        //描画（原始图片）
        canvas.drawBitmap(bitmap, 0, 0, myPaint);
        //新建颜色矩阵对象
        myColorMatrix = new ColorMatrix();
        //设置颜色矩阵的值
        myColorMatrix.set(colorArray);
        //设置画笔颜色过滤器
        myPaint.setColorFilter(new ColorMatrixColorFilter(myColorMatrix));
        //描画（处理后的图片）
        canvas.drawBitmap(bitmap, 0, 0, myPaint);
        invalidate();
    }

    //设置颜色数值
    public void setColorArray(float[] colorArray) {
        this.colorArray = colorArray;
    }

    //设置图片
    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
