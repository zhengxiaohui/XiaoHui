package com.zbase.listener;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * 创建人：郑晓辉
 * 创建日期：2017/12/11 0011
 * 描述：翻转或者摇一摇监听器
 */
public class ShakeListener implements SensorEventListener {
    public static ShakeListener sensor1;
    // 速度阈值，当摇晃速度达到这值后产生作用
    private static final int SPEED_SHRESHOLD = 400;
    // 两次检测的时间间隔
    private static final int UPTATE_INTERVAL_TIME = 70;
    // 两次翻转时间间隔
    private static final int OVER_INTERVAL_TIME = 1000;//1秒内的多次翻转只算一次
    // 传感器管理器
    private SensorManager sensorManager;
    // 传感器
    private Sensor sensor;
    // 重力感应监听器
    private OnShakeListener onShakeListener;
    // 上下文
    private static Context context;
    // 手机上一个位置时重力感应坐标
    private float lastX;
    private float lastY;
    private float lastZ;

    // 上次检测时间
    private long lastUpdateTime;
    // 上次翻转时间
    private long lastOverTime;
    public static ShakeListener newInstance(Context c) {
        if (sensor1 == null) {
            sensor1 = new ShakeListener();
            context = c;
            return sensor1;
        } else {
            return sensor1;
        }
    }

    // 开始
    public void start() {
        // 获得传感器管理器
        if(sensorManager==null){
            sensorManager = (SensorManager) context
                    .getSystemService(Context.SENSOR_SERVICE);
        }
        if (sensorManager != null&&sensor==null) {
            // 获得重力传感器
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }
        if (sensor != null) {
            sensorManager.registerListener(this, sensor,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    // 停止检测
    public void stop() {
        sensorManager.unregisterListener(this);
    }

    // 摇晃监听接口
    public interface OnShakeListener {
        public void onShake();
    }

    // 设置重力感应监听器
    public void setOnShakeListener(OnShakeListener listener) {
        onShakeListener = listener;
    }

    // 重力感应器感应获得变化数据
    @Override
    public void onSensorChanged(SensorEvent event) {

        long currentTime = System.currentTimeMillis();
        // 两次检测的时间间隔
        long timeInterval = currentTime - lastUpdateTime;
        // 判断是否达到了检测时间间隔
        if (timeInterval < UPTATE_INTERVAL_TIME) {
            return;
        }
        // 现在的时间变成last时间
        lastUpdateTime = currentTime;

        // 获得x,y,z坐标
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        // 获得x,y,z的变化值
        float deltaX = x - lastX;
        float deltaY = y - lastY;
        float deltaZ = z - lastZ;

        // 将现在的坐标变成last坐标
        lastX = x;
        lastY = y;
        lastZ = z;

        double speed = Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ
                * deltaZ)
                / timeInterval * 10000;
        // 达到速度阀值，发出提示
        if (speed >= SPEED_SHRESHOLD){
            // 两次检测的时间间隔
            long overTimeInterval = currentTime - lastOverTime;
            if (overTimeInterval < OVER_INTERVAL_TIME) {//1秒内的多次翻转只算一次
                return;
            }
            // 手机晃动
            onShakeListener.onShake();
            lastOverTime= currentTime;
        }
    }
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

}