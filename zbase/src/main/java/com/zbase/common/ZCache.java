package com.zbase.common;

import android.content.Context;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/2/24.
 * 调用ACache类的方法
 */
public class ZCache {


    /**
     * 保存 Serializable数据,可以保存各种序列化的对象，各种List,Map等
     *
     * @param key
     *            保存的key
     * @param value
     *            保存的value
     */
    public static void putSerializable(Context context,String key, Serializable value){
        ACache.get(context).put(key,value);
    }

    public static Object getAsObject(Context context,String key){
        return ACache.get(context).getAsObject(key);
    }

    /**
     * 保存 Serializable数据�?缓存�?
     *
     * @param key
     *            保存的key
     * @param value
     *            保存的value
     * @param second
     *            保存的时间，单位：秒
     */
    public static void putSerializableSecond(Context context,String key, Serializable value, int second) {
        ACache.get(context).put(key,value,second);
    }

    /**
     * 保存 Serializable数据�?缓存�?
     *
     * @param key
     *            保存的key
     * @param value
     *            保存的value
     * @param minute
     *            保存的时间，单位：分钟
     */
    public static void putSerializableMinute(Context context,String key, Serializable value, int minute) {
        ACache.get(context).put(key,value,minute*60);
    }

    /**
     * 保存 Serializable数据�?缓存�?
     *
     * @param key
     *            保存的key
     * @param value
     *            保存的value
     * @param hour
     *            保存的时间，单位：小时
     */
    public static void putSerializableHour(Context context,String key, Serializable value, int hour) {
        ACache.get(context).put(key,value,hour*60*60);
    }

    /**
     * 保存 Serializable数据�?缓存�?
     *
     * @param key
     *            保存的key
     * @param value
     *            保存的value
     * @param day
     *            保存的时间，单位：天
     */
    public static void putSerializableDay(Context context,String key, Serializable value, int day) {
        ACache.get(context).put(key,value,day*60*60*24);
    }

    /**
     * 保存 Serializable数据�?缓存�?
     *
     * @param key
     *            保存的key
     * @param value
     *            保存的value
     * @param month
     *            保存的时间，单位：月
     */
    public static void putSerializableMonth(Context context,String key, Serializable value, int month) {
        ACache.get(context).put(key,value,month*60*60*24*30);
    }

    /**
     * 保存 Serializable数据�?缓存�?
     *
     * @param key
     *            保存的key
     * @param value
     *            保存的value
     * @param year
     *            保存的时间，单位：年
     */
    public static void putSerializableYear(Context context,String key, Serializable value, int year) {
        ACache.get(context).put(key,value,year*60*60*24*365);
    }


}
