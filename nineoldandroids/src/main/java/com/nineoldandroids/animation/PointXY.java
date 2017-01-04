package com.nineoldandroids.animation;

/**
 * 创建人：郑晓辉
 * 创建日期：2016/2/29
 * 描述：属性动画中使用，因为系统自带的Point类并没有getX(),getY()方法，所以自己定义一个
 */
public class PointXY {

    private float x;

    private float y;

    public PointXY(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

}
