package com.oranllc.baidumap;

import android.content.Context;

import com.baidu.mapapi.SDKInitializer;

/**
 * 创建人：郑晓辉
 * 创建日期：2016/5/26
 * 描述：百度地图配置，在MyApplication的oncreate()初始化中调用 BaiduMapConfig.init(this);//百度地图
 */
public class BaiduMapConfig {

    public static void init(Context context){
        SDKInitializer.initialize(context);//百度地图
    }
}
