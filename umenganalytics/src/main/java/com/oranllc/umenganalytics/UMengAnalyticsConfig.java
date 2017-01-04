package com.oranllc.umenganalytics;

import com.umeng.analytics.MobclickAgent;

/**
 * 创建人：郑晓辉
 * 创建日期：2016/5/18
 * 描述：
 */
public class UMengAnalyticsConfig {

    public static void init(boolean debugMode){
        MobclickAgent.setDebugMode(debugMode);
        MobclickAgent.openActivityDurationTrack(false); //禁止默认的页面统计方式，这样将不会再自动统计Activity。从而能统计Fragment
    }
}
