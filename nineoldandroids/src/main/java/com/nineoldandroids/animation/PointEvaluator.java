package com.nineoldandroids.animation;

/**
 * 创建人：郑晓辉
 * 创建日期：2016/3/1
 * 描述：x,y坐标差值计算，一般用于x,y同时以自己的速率增长的动画，比如从左上角平铺到右下角的动画
 */
public class PointEvaluator implements TypeEvaluator {

    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {
        PointXY startPoint = (PointXY) startValue;
        PointXY endPoint = (PointXY) endValue;
        float x = startPoint.getX() + fraction * (endPoint.getX() - startPoint.getX());
        float y = startPoint.getY() + fraction * (endPoint.getY() - startPoint.getY());
        PointXY point = new PointXY(x, y);
        return point;
    }

}
