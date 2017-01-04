package com.oranllc.jpush;

import android.content.Context;

import cn.jpush.android.api.JPushInterface;

/**
 * 创建人：郑晓辉
 * 创建日期：2016/5/26
 * 描述：
 */
public class JPushConfig {

    public static void init(Context context, boolean debugMode) {
        JPushInterface.setDebugMode(debugMode);// 设置开启日志,发布时请关闭日志
        JPushInterface.init(context);//极光推送
    }

    //以下为设置通知栏样式的实例代码：
//    ok，来看setStyleBasic,这里我设置了自己的图标：R.drawable.t_2
//
//    /
//            *设置通知提示方式 - 基础属性
//    */
//    private void setStyleBasic(){
//        BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(PushSetActivity.this);
//        builder.statusBarDrawable = R.drawable.t_2;
//        builder.notificationFlags = Notification.FLAG_AUTO_CANCEL;  //设置为点击后自动消失
//        builder.notificationDefaults = Notification.DEFAULT_SOUND;  //设置为铃声（ Notification.DEFAULT_SOUND）或者震动（ Notification.DEFAULT_VIBRATE）
//        JPushInterface.setPushNotificationBuilder(1, builder);
//        Toast.makeText(PushSetActivity.this, "Basic Builder - 1", Toast.LENGTH_SHORT).show();
//    }
//    除了极光推送sdk自定义的通知栏属性外。我们可以自定义通知栏的显示界面。来看点击第二个通知栏选项的代码实现
//
//    /
//            *设置通知栏样式 - 定义通知栏Layout
//    */
//    private void setStyleCustom(){
//        CustomPushNotificationBuilder builder = new CustomPushNotificationBuilder(PushSetActivity.this,R.layout.customer_notitfication_layout,R.id.icon, R.id.title, R.id.text);
//        builder.layoutIconDrawable = R.drawable.ic_launcher;
//        builder.developerArg0 = "developerArg2";
//        JPushInterface.setPushNotificationBuilder(2, builder);
//        Toast.makeText(PushSetActivity.this,"Custom Builder - 2", Toast.LENGTH_SHORT).show();
//    }
//    这里的R.layout.customer_notitfication_layout就是自定义一个layout。源码也很简单。个人认为极光sdk里的样式就可以满足大部分项目需求了。要自定义也可以，但是不需要太复杂。
}
