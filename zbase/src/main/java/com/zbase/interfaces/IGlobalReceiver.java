package com.zbase.interfaces;

import android.content.Intent;
import android.os.Bundle;

/**
 * 创建人：郑晓辉
 * 创建日期：2016/7/22
 * 描述：
 */
public interface IGlobalReceiver {

    void registerGlobleReceiver();

    void unRegisterGlobleReceiver();

    void sendCommonBroadcast(String code);

    void onCommonBroadcastReceive(String code, Bundle bundle);

    void sendLoginBroadcast();

    void sendLogoutBroadcast();

    void onLogin(Intent intent);

    void onLogout(Intent intent);

    void onMessageReceived(String title, String message, String extras, String contentType);

    void onNotificationReceived(String title, String content, String extras, String contentType);

    void onNotificationOpened(String title, String content, String extras);

}
