package com.zbase.util;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;

import com.zbase.bean.AppInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 郑晓辉 on 2015/3/24
 * email:378180078@qq.com
 * 摘要：分享管理
 * 详细说明：包含调用系统分享和自定义分享
 * 使用示例：
 */
public class ShareUtil {

    /**
     * 分享文字
     * @param context
     * @param title
     * @param text
     */
    public static void shareText(Context context, String title, String text) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, title);
        intent.putExtra(Intent.EXTRA_TEXT, text);
        context.startActivity(Intent.createChooser(intent, title));
    }

    // 分享照片
    public static void SharePhoto(Context context,String photoUri,String title) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        File file = new File(photoUri);
        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
        shareIntent.setType("image/jpeg");
        context.startActivity(Intent.createChooser(shareIntent, title));
    }

    /**
     * 分享图片或者文字（文字的还没测试）
     * @param context       上下文
     * @param activityTitle Activity的名字
     * @param msgTitle      消息标题
     * @param msgText       消息内容 "I have successfully share my message through my app"
     * @param imgPath       图片路径，不分享图片则传null
     */
    public static void shareImageOrText(Context context, String activityTitle, String msgTitle, String msgText, String imgPath) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        if (imgPath == null || imgPath.equals("")) {
            intent.setType("text/plain"); // 纯文本
        } else {
            File f = new File(imgPath);
            if (f != null && f.exists() && f.isFile()) {
                intent.setType("image/png");
                Uri uri = Uri.fromFile(f);
                intent.putExtra(Intent.EXTRA_STREAM, uri);
            }
        }
        intent.putExtra(Intent.EXTRA_SUBJECT, msgTitle);
        intent.putExtra(Intent.EXTRA_TEXT, msgText);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(Intent.createChooser(intent, activityTitle));
    }


    /**
     * 分享语音文件
     * @param context
     * @param path
     */
    public static void shareAudio(Context context, String path,String Title) {
        Uri uri = Uri.parse("file:///" + path);///data/data/<package name>/files目录
        Intent it = new Intent(Intent.ACTION_SEND);
        it.putExtra(Intent.EXTRA_STREAM, uri);
        it.setType("audio/*");
        context.startActivity(Intent.createChooser(it,Title));
    }

    /**
     * 查询所有支持分享的应用信息
     *
     * @param context
     * @return
     */
    private static List<ResolveInfo> getShareApps(Context context) {
        List<ResolveInfo> mApps = new ArrayList<ResolveInfo>();
        Intent intent = new Intent(Intent.ACTION_SEND, null);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setType("text/plain");
        PackageManager pManager = context.getPackageManager();
        mApps = pManager.queryIntentActivities(intent, PackageManager.COMPONENT_ENABLED_STATE_DEFAULT);
        return mApps;
    }

    /**
     * 得到应用列表
     *
     * @return
     */
    public static List<AppInfo> getShareAppList(Context context) {
        List<AppInfo> shareAppInfos = new ArrayList<AppInfo>();
        PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> resolveInfos = getShareApps(context);
        if (null == resolveInfos) {
            return null;
        }
        else {
            for (ResolveInfo resolveInfo : resolveInfos) {
                AppInfo appInfo = new AppInfo();
                appInfo.setAppPkgName(resolveInfo.activityInfo.packageName);
                appInfo.setAppLauncherClassName(resolveInfo.activityInfo.name);
                appInfo.setAppName(resolveInfo.loadLabel(packageManager).toString());
                appInfo.setAppIcon(resolveInfo.loadIcon(packageManager));
                shareAppInfos.add(appInfo);
            }
        }
        return shareAppInfos;
    }

    /**
     * 分享到微信朋友圈，只能发图片
     * @param context
     * @param files 文件数组
     */
    private void shareMultiplePictureToTimeLine(Context context,File... files) {
        Intent intent = new Intent();
        ComponentName comp = new ComponentName("com.tencent.mm",
                "com.tencent.mm.ui.tools.ShareToTimeLineUI");
        intent.setComponent(comp);
        intent.setAction(Intent.ACTION_SEND_MULTIPLE);
        intent.setType("image/*");

        ArrayList<Uri> imageUris = new ArrayList<Uri>();
        for (File f : files) {
            imageUris.add(Uri.fromFile(f));
        }
        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);
        context.startActivity(intent);
    }
}
