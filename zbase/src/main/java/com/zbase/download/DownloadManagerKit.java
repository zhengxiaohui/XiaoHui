package com.zbase.download;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建人：郑晓辉
 * 创建日期：2016/9/14
 * 描述：
 */
public class DownloadManagerKit {

    private Context context;
    private DownloadManager downloadManager;
    private DownLoadCompleteReceiver downLoadCompleteReceiver;
    private long downId;
    private boolean install;
    private OnDownloadCompleteListener onDownloadCompleteListener;

    public interface OnDownloadCompleteListener {
        void onDownloadComplete();
    }

    public DownloadManagerKit(Context context) {
        this(context, false);
    }

    /**
     * @param context
     * @param install 是否自动安装apk
     */
    public DownloadManagerKit(Context context, boolean install) {
        this.context = context;
        this.install = install;
        init();
    }

    private void init() {
        downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        IntentFilter filter = new IntentFilter();
        filter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        filter.addAction(DownloadManager.ACTION_NOTIFICATION_CLICKED);
        downLoadCompleteReceiver = new DownLoadCompleteReceiver();
        context.registerReceiver(downLoadCompleteReceiver, filter);
    }

    public void download(String url, String title) {
        download(url, title, null);
    }

    public void download(String url, String title, OnDownloadCompleteListener onDownloadCompleteListener) {
        this.onDownloadCompleteListener = onDownloadCompleteListener;
        Toast.makeText(context, "开始下载！", Toast.LENGTH_SHORT).show();
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI); //设置允许使用的网络类型，这里是移动网络和wifi都可以
        request.setAllowedOverRoaming(false);//手机在漫游状态下下载
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);//下载时时候在状态栏显示通知信息
        request.setTitle(title);
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        String mimeString = mimeTypeMap.getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(url));
        request.setMimeType(mimeString);//设置文件类型
        request.setVisibleInDownloadsUi(true);// 设置为可见和可管理
//        request.allowScanningByMediaScanner();//设置为可被媒体扫描器找到
        String filename = url.substring(url.lastIndexOf("/") + 1);
        request.setDestinationInExternalFilesDir(context, Environment.DIRECTORY_DOWNLOADS, filename); //设置文件存放目录
        //将下载请求添加至downManager，注意enqueue方法的编号为当前
        downId = downloadManager.enqueue(request);
    }


    private class DownLoadCompleteReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
                if (onDownloadCompleteListener != null) {
                    onDownloadCompleteListener.onDownloadComplete();
                }
                long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                if (install) {
                    install(id);
                }
                Toast.makeText(context, "下载完成！", Toast.LENGTH_SHORT).show();
            } else if (intent.getAction().equals(DownloadManager.ACTION_NOTIFICATION_CLICKED)) {//点击通知栏

            }
        }
    }

    private void install(long id) {
        Intent installIntent = new Intent(Intent.ACTION_VIEW);
        Uri downloadFileUri = downloadManager.getUriForDownloadedFile(id);
        installIntent.setDataAndType(downloadFileUri, "application/vnd.android.package-archive");
        installIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(installIntent);
    }

    public List<DownloadFileInfo> queryDownloadFileInfo(DownloadManager downManager, long... ids) {
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(ids);
        List<DownloadFileInfo> downloadFileInfoList = new ArrayList<>();
        Cursor cursor = downManager.query(query);
        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_ID));
            String title = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_TITLE));
            String localURI = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
            String status = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
            String bytesDownloadSoFar = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
            String totalSizeBytes = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
            DownloadFileInfo downloadFileInfo = new DownloadFileInfo();
            downloadFileInfo.setId(id);
            downloadFileInfo.setTitle(title);
            downloadFileInfo.setLocalURI(localURI);
            downloadFileInfo.setStatus(status);
            downloadFileInfo.setBytesDownloadSoFar(bytesDownloadSoFar);
            downloadFileInfo.setTotalSizeBytes(totalSizeBytes);
            downloadFileInfoList.add(downloadFileInfo);
        }
        cursor.close();
        return downloadFileInfoList;
    }

    public void onDestroy() {
        context.unregisterReceiver(downLoadCompleteReceiver);
    }

    public class DownloadFileInfo {
        private String id;
        private String title;
        private String localURI;
        private String status;
        private String bytesDownloadSoFar;
        private String totalSizeBytes;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getLocalURI() {
            return localURI;
        }

        public void setLocalURI(String localURI) {
            this.localURI = localURI;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getBytesDownloadSoFar() {
            return bytesDownloadSoFar;
        }

        public void setBytesDownloadSoFar(String bytesDownloadSoFar) {
            this.bytesDownloadSoFar = bytesDownloadSoFar;
        }

        public String getTotalSizeBytes() {
            return totalSizeBytes;
        }

        public void setTotalSizeBytes(String totalSizeBytes) {
            this.totalSizeBytes = totalSizeBytes;
        }
    }
}
