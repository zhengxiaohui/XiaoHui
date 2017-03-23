package com.zdemo.activity;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.zbase.download.DownloadManagerKit;
import com.zbase.util.AppUtil;
import com.zdemo.R;

/**
 * 创建人：郑晓辉
 * 创建日期：2016/6/3
 * 描述：
 */
public class DownloadManagerActivity extends BaseActivity {
    private Button button;
    private Button button1;
    private Button button2;
    private DownloadManagerKit downloadManagerKit;

    @Override
    protected int inflateMainLayoutId() {
        return R.layout.activity_download_manager;
    }

    @Override
    protected void initView(View view) {
        button = (Button) findViewById(R.id.button);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
    }

    @Override
    protected void setListener() {
        button.setOnClickListener(this);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
    }

    @Override
    protected void initValue() {
        downloadManagerKit = new DownloadManagerKit(this, true);
    }

    @Override
    public void onClick(View v) {
        String url = "http://app.yinglingzhe.com/Upload/Software/20161019113944.apk";
        switch (v.getId()) {
            case R.id.button://OKHttp下载，不能退出当前Activity，否则报错。
                AppUtil.updateApk(context, url);
                break;
            case R.id.button1://DownloadManager下载,不能退出当前Activity，否则不能下载完全或者不能执行安装步骤。
                downloadManagerKit.download(url, "今日头条", new DownloadManagerKit.OnDownloadCompleteListener() {
                    @Override
                    public void onDownloadComplete() {
                        Toast.makeText(context, "下载完成回调", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.button2://DownloadManager+Service下载,可以退出Activity甚至整个app，都可以正常下载安装。
                AppUtil.startAppUpgradeService(context,url);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        downloadManagerKit.onDestroy();
    }
}
