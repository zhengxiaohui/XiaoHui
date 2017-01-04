package com.zbase.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.zbase.download.DownloadManagerKit;
import com.zbase.util.AppUtil;

/**
 * 创建人：CXQ
 * 创建日期：2016/10/19
 * 描述：
 */
public class AppUpgradeService extends Service {

    private DownloadManagerKit downloadManagerKit;
    public static final String DOWNLOAD_URL = "download_url";

    @Override
    public void onCreate() {
        super.onCreate();
        downloadManagerKit = new DownloadManagerKit(this, true);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            String url = intent.getStringExtra(DOWNLOAD_URL);
            downloadManagerKit.download(url, AppUtil.getApplicationName(this), new DownloadManagerKit.OnDownloadCompleteListener() {
                @Override
                public void onDownloadComplete() {
                    stopSelf();
                }
            });
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        downloadManagerKit.onDestroy();
    }

}
