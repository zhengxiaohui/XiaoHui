package com.oranllc.common;

/**
 * 创建人：郑晓辉
 * 创建日期：2016/5/27
 * 描述：公共广播接收常量，同时被ZBase和独立功能模块Module引用，如JPush
 * 通过公共广播解耦了第三方SDK等Module跟自己ZBase代码的联系。这样ZBase就不用直接引用JPush等第三方Module里面的任何类，方法，变量，常量等。
 * 这样如果不是用极光推送，ZBase中的Gradle文件就不用引用JPush了，缩小了包的体积。其他第三方SDK也是一样的，或者自己写的独立Module代码。
 * 以后有Module都可以考虑用公共广播做通讯，能最大限度的解耦，实现任意插拔式开发。
 * 多条广播之间ACTION不能重复，KEY可以重复
 */
public class CommonReceiveConstant {
//    极光推送用广播常量begin
    public static final String ACTION_MESSAGE_RECEIVED = "action_message_received"; // 极光推送消息到达
    public static final String ACTION_NOTIFICATION_RECEIVED = "action_notification_received"; // 极光推送通知到达
    public static final String ACTION_NOTIFICATION_OPENED = "action_notification_opened"; // 极光推送通知打开
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";
    public static final String KEY_CONTENT_TYPE = "content_type";
    public static final String KEY_CONTENT = "content";
//    极光推送用广播常量end




}
