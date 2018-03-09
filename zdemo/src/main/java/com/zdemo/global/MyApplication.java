package com.zdemo.global;


import android.content.Context;
import android.support.multidex.MultiDex;

import com.oranllc.baidumap.BaiduMapConfig;
import com.oranllc.jpush.JPushConfig;
import com.oranllc.juhe.JuheConfig;
import com.oranllc.umenganalytics.UMengAnalyticsConfig;
import com.oranllc.umengsocialshare.common.UMengShareConfig;
import com.twitter.sdk.android.core.Twitter;
import com.zbase.common.BaseApplication;
import com.zbase.strategy.PopOne;
import com.zbase.strategy.PopStrategy;

public class MyApplication extends BaseApplication {
	
	@Override
	public void onCreate() {
		super.onCreate();
		PopStrategy.setBasePop(new PopOne());//策略模式,设置全局转圈圈样式

		UMengShareConfig.init();//友盟分享
		UMengAnalyticsConfig.init(debugMode);//友盟统计
		JPushConfig.init(this, debugMode);//极光推送
		BaiduMapConfig.init(this);//百度地图
		JuheConfig.init(this);//聚合数据
		Twitter.initialize(this);
	}

	/**
	 * 解决5.0以下手机报dalvikvm: Could not find class的问题
	 * @param base
	 */
	@Override
	protected void attachBaseContext(Context base) {
		super.attachBaseContext(base);
		MultiDex.install(this);
	}

	@Override
	public Class getLoginClass() {
		return null;
	}

	@Override
	public Class getWebViewClass() {
		return null;
	}

	@Override
	public boolean isLoggedIn() {
		return false;
	}

	@Override
	public void clearUser() {

	}

}
