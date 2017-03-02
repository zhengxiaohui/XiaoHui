package com.oranllc.template.global;

import com.oranllc.template.activity.LoginActivity;
import com.oranllc.template.activity.WebViewActivity;
import com.oranllc.template.bean.LoginBean;
import com.oranllc.umenganalytics.UMengAnalyticsConfig;
import com.zbase.common.BaseApplication;
import com.zbase.common.Const;
import com.zbase.common.ZSharedPreferences;
import com.zbase.strategy.PopOne;
import com.zbase.strategy.PopStrategy;

/**
 * 保存全局单例对象和应用初始化配置
 */
public class MyApplication extends BaseApplication {

    private LoginBean.Data user;

    public LoginBean.Data getUser() {
        return user;
    }

    public void setUser(LoginBean.Data user) {
        this.user = user;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        debugMode = true;//是否是debug模式，默认true。控制打印日志，极光推送模式等。打包APK的时候要设置成false
        PopStrategy.setBasePop(new PopOne());//策略模式,设置全局转圈圈样式
        UMengAnalyticsConfig.init(debugMode);//友盟统计,默认引用，需要统计的话只要配置appkey就可以，如果有引入友盟分享等，则使用同一个key不用另外配置
        LoginBean.Data data = ZSharedPreferences.getInstance(this).getJsonBean(Const.USER, LoginBean.Data.class);
        setUser(data);
    }

    @Override
    public Class getLoginClass() {
        return LoginActivity.class;
    }

    @Override
    public Class getWebViewClass() {
        return WebViewActivity.class;
    }

    @Override
    public boolean isLoggedIn() {
        return getUser() != null;
    }

    @Override
    public void clearUser() {
        setUser(null);
    }

}
