package com.oranllc.juhe;

import android.content.Context;

import com.thinkland.sdk.android.JuheSDKInitializer;

/**
 * 创建人：郑晓辉
 * 创建日期：2016/5/26
 * 描述：
 */
public class JuheConfig {

    public static void init(Context context){
        JuheSDKInitializer.initialize(context);//聚合数据
    }
}
