package com.zheng.facebook;

import android.content.Context;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

/**
 * 创建人：郑晓辉
 * 创建日期：2017/12/26 0026
 * 描述：可以不用调用了
 * UPDATE
 In SDK 4.22 the title, description, caption and image field of FBSDKShareLinkContent are deprecated. Consider removing them from usage.
 The Facebook SDK is now auto initialized on Application start. If you are using the Facebook SDK in the main process and don't need a callback
 on SDK initialization completion you can now remove calls to FacebookSDK.sdkInitialize. If you do need a callback, you should manually
 invoke the callback in your code.
 */
@Deprecated
public class FacebookConfig {

    /**
     * 可以不用调用了
     * 现在，Facebook SDK在应用程序启动时自动初始化。如果您正在使用Facebook SDK的主要过程和不需要一个回调SDK初始化完成你现在可以移除FacebookSDK.sdkInitialize调用。
     * @param context
     */
    @Deprecated
    public static void init(Context context) {
        FacebookSdk.sdkInitialize(context.getApplicationContext());
        AppEventsLogger.activateApp(context);
    }
}
