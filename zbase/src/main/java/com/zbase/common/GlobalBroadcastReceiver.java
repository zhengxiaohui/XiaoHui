package com.zbase.common;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.text.TextUtils;

import com.oranllc.common.CommonReceiveConstant;
import com.zbase.interfaces.IGlobalReceiver;

/**
 * 创建人：郑晓辉
 * 创建日期：2016/7/22
 * 描述：
 */
public class GlobalBroadcastReceiver extends BroadcastReceiver {

    public static final String ACTION_COMMON = "action_common"; // 除特殊用途广播外的通用广播
    public static final String ACTION_EXIT = "action_exit"; // 退出整个app,暂时没用，预留
    public static final String ACTION_LOGIN = "action_login"; // 登陆
    public static final String ACTION_LOGOUT = "action_logout"; // 注销
    public static String broadcastIdentifyCode;//广播识别码
    private IGlobalReceiver iGlobalReceiver;

    public GlobalBroadcastReceiver(IGlobalReceiver iGlobalReceiver) {
        this.iGlobalReceiver = iGlobalReceiver;
    }

    public IntentFilter getIntentFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(GlobalBroadcastReceiver.ACTION_COMMON);
        intentFilter.addAction(GlobalBroadcastReceiver.ACTION_EXIT);
        intentFilter.addAction(GlobalBroadcastReceiver.ACTION_LOGIN);
        intentFilter.addAction(GlobalBroadcastReceiver.ACTION_LOGOUT);
        intentFilter.addAction(CommonReceiveConstant.ACTION_MESSAGE_RECEIVED);
        intentFilter.addAction(CommonReceiveConstant.ACTION_NOTIFICATION_RECEIVED);
        intentFilter.addAction(CommonReceiveConstant.ACTION_NOTIFICATION_OPENED);
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);// 网络连接
        return intentFilter;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (!TextUtils.isEmpty(action)) {
            if (ACTION_EXIT.equals(action)) {

            } else if (ConnectivityManager.CONNECTIVITY_ACTION.equals(action)) {
//                    if (!NetWorkUtil.isNetworkConnected(AbstractBaseActivity.this)) {
//                        NetWorkUtil.alertNetWorkSettingDialog(AbstractBaseActivity.this);
//                    }
            } else if (ACTION_COMMON.equals(action)) {//通用广播，自己加识别码区分
                iGlobalReceiver.onCommonBroadcastReceive(broadcastIdentifyCode, intent.getExtras());
            } else if (ACTION_LOGIN.equals(action)) {
                iGlobalReceiver.onLogin(intent);
            } else if (ACTION_LOGOUT.equals(action)) {
                iGlobalReceiver.onLogout(intent);
            } else if (CommonReceiveConstant.ACTION_MESSAGE_RECEIVED.equals(action)) {//消息到达
                String title = intent.getStringExtra(CommonReceiveConstant.KEY_TITLE);
                String messge = intent.getStringExtra(CommonReceiveConstant.KEY_MESSAGE);
                String extras = intent.getStringExtra(CommonReceiveConstant.KEY_EXTRAS);
                String contentType = intent.getStringExtra(CommonReceiveConstant.KEY_CONTENT_TYPE);
                iGlobalReceiver.onMessageReceived(title, messge, extras, contentType);
            } else if (CommonReceiveConstant.ACTION_NOTIFICATION_RECEIVED.equals(action)) {//通知到达
                String title = intent.getStringExtra(CommonReceiveConstant.KEY_TITLE);
                String content = intent.getStringExtra(CommonReceiveConstant.KEY_CONTENT);
                String extras = intent.getStringExtra(CommonReceiveConstant.KEY_EXTRAS);
                String contentType = intent.getStringExtra(CommonReceiveConstant.KEY_CONTENT_TYPE);
                iGlobalReceiver.onNotificationReceived(title, content, extras, contentType);
            } else if (CommonReceiveConstant.ACTION_NOTIFICATION_OPENED.equals(action)) {
                String title = intent.getStringExtra(CommonReceiveConstant.KEY_TITLE);
                String content = intent.getStringExtra(CommonReceiveConstant.KEY_CONTENT);
                String extras = intent.getStringExtra(CommonReceiveConstant.KEY_EXTRAS);
                iGlobalReceiver.onNotificationOpened(title, content, extras);
            }//通知打开,如果已经存在activity则直接使用当前activity而不开启两个则Intent要加intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }
    }
}
