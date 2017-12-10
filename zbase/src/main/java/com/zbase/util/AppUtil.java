package com.zbase.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.model.Progress;
import com.zbase.R;
import com.zbase.bean.AppInfo;
import com.zbase.common.ZSharedPreferences;
import com.zbase.listener.OnObtainAppInfoListListener;
import com.zbase.service.AppUpgradeService;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * 创建人：郑晓辉
 * 创建日期：2016/3/18
 * 描述：所有app有关的工具类
 */
public class AppUtil {

    /**
     * 获取应用程序名称
     *
     * @return
     */
    public static String getApplicationName(Context context) {
        PackageManager packageManager = null;
        ApplicationInfo applicationInfo = null;
        try {
            packageManager = context.getPackageManager();
            applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            applicationInfo = null;
        }
        String applicationName = (String) packageManager.getApplicationLabel(applicationInfo);
        return applicationName;
    }

    /**
     * 获得软件版号versionCode
     *
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {
        try {
            PackageInfo packInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return packInfo.versionCode;
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 获得软件版号versionName
     *
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        try {
            PackageInfo packInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return packInfo.versionName;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 获取apk包的信息
     *
     * @return
     */
    public static PackageInfo getPackageInfo(Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo packInfo = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
            return packInfo;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取application下的metaData值
     *
     * @param context
     * @param metaKey
     * @return
     */
    public static String getApplicationMetaValue(Context context, String metaKey) {
        Bundle metaData = null;
        if (context == null || metaKey == null) {
            return null;
        }
        try {
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            if (null != ai) {
                metaData = ai.metaData;
            }
            if (null != metaData) {
                Object apiKey = metaData.get(metaKey);
                if (null != apiKey) {
                    return apiKey.toString();
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
        }
        return null;
    }

    /**
     * 获取activity 中的的metaData值
     *
     * @param
     * @param metaKey
     * @return
     */
    public static String getActivityMetaValue(Activity activity, String metaKey) {
        Bundle metaData = null;
        if (activity == null || metaKey == null) {
            return null;
        }
        try {
            ActivityInfo ai = activity.getPackageManager().getActivityInfo(activity.getComponentName(), PackageManager.GET_META_DATA);
            if (null != ai) {
                metaData = ai.metaData;
            }
            if (null != metaData) {
                Object apiKey = metaData.get(metaKey);
                if (null != apiKey) {
                    return apiKey.toString();
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
        }
        return null;
    }

    /**
     * 判断第三方应用是否安装
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isAppInstalled(Context context, String packageName) {
        try {
            PackageManager pm = context.getPackageManager();
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
        return true;
    }

    /**
     * 判断软件是否已安装
     *
     * @param apkFilePath
     * @return true 已安装,false 未安装
     */
    public static boolean checkIsInstalled(Context context, String apkFilePath) {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(
                    getPackagePathOfApk(context, apkFilePath), 0);

        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
            // e.printStackTrace();
        }
        if (packageInfo == null) {
            System.out.println("没有安装");
            return false;
        } else {
            System.out.println("已经安装");
            return true;
        }
    }

    /**
     * 安装一个apk文件
     */
    public static void install(Context context, File uriFile) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(uriFile), "application/vnd.android.package-archive");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);   // 询问完成安装还是打开
        context.startActivity(intent);
    }

    /**
     * 安装APK
     *
     * @param context
     * @param apkFilePath
     */
    public static void install(Context context, String apkFilePath) {
        Intent intallIntent = new Intent(Intent.ACTION_VIEW);
        intallIntent.setDataAndType(Uri.parse("file://" + apkFilePath), "application/vnd.android.package-archive");
        context.startActivity(intallIntent);
    }

    /**
     * 卸载
     *
     * @param context
     */
    public static void uninstall(Context context) {
        Uri packageURI = Uri.parse("package:" + context.getPackageName());
        Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
        context.startActivity(uninstallIntent);
    }

    /**
     * 读取本地apk文件的包路径
     *
     * @param apkFilePath
     * @return 返回包路径, 如果文件不存在, 则返回空字符串
     */
    public static String getPackagePathOfApk(Context context, String apkFilePath) {
        String packagePath = "";
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo info = pm.getPackageArchiveInfo(apkFilePath,
                    PackageManager.GET_ACTIVITIES);
            if (info != null) {
                ApplicationInfo appInfo = info.applicationInfo;
                packagePath = appInfo.packageName; // 得到安装包名称
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return packagePath;
    }

    /**
     * 判断App是否在运行
     * packageName如：com.goood.casedesign
     *
     * @return
     */
    public static boolean isAppRunning(Context context, String packageName) {
        boolean isAppRunning = false;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(150);
        for (ActivityManager.RunningTaskInfo info : list) {
            if (info.topActivity.getPackageName().equals(packageName) && info.baseActivity.getPackageName().equals(packageName)) {
                isAppRunning = true;
                break;
            }
        }
        return isAppRunning;
    }

    /**
     * 判断服务已经启动运行
     *
     * @param context
     * @param serviceFullName serviceFullName service全名
     * @return
     */
    public static boolean isServiceLaungle(Context context, String serviceFullName) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> infos = am.getRunningServices(30); //30是最大值
        for (ActivityManager.RunningServiceInfo info : infos) {
            if (info.service.getClassName().equals(serviceFullName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 添加当前应用的桌面快捷方式
     *
     * @param context
     */
    public static void createShortcut(Context context, int resId, int appNameResId) {
        if (hasShortcut(context, appNameResId)) {//自己加的代码，还没测试是不是要加
            return;
        }
        final PackageManager pm = context.getPackageManager();
        final String packageName = context.getPackageName();
        Intent shortcut = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
        Intent shortcutIntent = pm.getLaunchIntentForPackage(packageName);
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
        // 获取当前应用名称
        String title = null;
        try {
            title = pm.getApplicationLabel(pm.getApplicationInfo(packageName, PackageManager.GET_META_DATA)).toString();
        } catch (Exception e) {
        }
        if (null == title) {
            title = context.getString(appNameResId);
        }
        // 快捷方式名称
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, title);
        // 不允许重复创建（不一定有效）
        shortcut.putExtra("duplicate", false);
        // 快捷方式的图标
        Intent.ShortcutIconResource iconResource = Intent.ShortcutIconResource.fromContext(context, resId);
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconResource);
        context.sendBroadcast(shortcut);
    }

    // 判断是否存在快捷方式
    public static boolean hasShortcut(Context context, int appNameResId) {
        final PackageManager pm = context.getPackageManager();
        final String packageName = context.getPackageName();
        // 获取当前应用名称
        String title = null;
        try {
            title = pm.getApplicationLabel(pm.getApplicationInfo(packageName, PackageManager.GET_META_DATA)).toString();
        } catch (Exception e) {
        }
        if (null == title) {
            title = context.getString(appNameResId);
        }
        String url = "";
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.FROYO) {
            url = "content://com.android.launcher.settings/favorites?notify=true";
        } else {
            url = "content://com.android.launcher2.settings/favorites?notify=true";
        }
        Cursor cursor = null;
        try {
            ContentResolver resolver = context.getContentResolver();
            cursor = resolver.query(Uri.parse(url), null, "title=?", new String[]{title}, null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.close();
                return true;
            }
        } catch (Exception e) {
        } finally {
            if (null != cursor) {
                cursor.close();
            }
        }
        return false;
    }

    /**
     * 删除当前应用的桌面快捷方式
     *
     * @param context
     */
    public static void delShortcut(Context context, int appNameResId) {
        Intent shortcut = new Intent("com.android.launcher.action.UNINSTALL_SHORTCUT");
        // 获取当前应用名称
        String title = null;
        final PackageManager pm = context.getPackageManager();
        final String packageName = context.getPackageName();
        try {
            title = pm.getApplicationLabel(pm.getApplicationInfo(packageName, PackageManager.GET_META_DATA)).toString();
        } catch (Exception e) {
        }
        if (null == title) {
            title = context.getString(appNameResId);
        }
        // 快捷方式名称
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, title);
        Intent shortcutIntent = pm.getLaunchIntentForPackage(packageName);
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
        context.sendBroadcast(shortcut);
    }

    /**
     * 打开其他的app
     *
     * @param context
     * @param packageName 包名
     */
    public static void openOtherApp(Context context, String packageName) {
        try {
            Intent intent = new Intent();
            PackageManager packageManager = context.getPackageManager();
            intent = packageManager.getLaunchIntentForPackage(packageName);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent);
        } catch (Exception e) {
            PopUtil.toast(context, R.string.you_do_not_install_the_app);
            e.printStackTrace();
        }
    }

    /**
     * 打开其他的app，指定打开界面，调用时加上try...catch，如果没有安装该应用则跳转到webview等
     *
     * @param activity
     * @param packageName  包名
     * @param activityName activity名
     * @param url 没有具体地址的可以传空
     */
    // 另：几个常用的Package命令：
    // 新浪微博（编辑界面）：com.sina.weibo //com.sina.weibo.EditActivity
    // 腾讯微博（编辑界面）：com.tencent.WBlog// com.tencent.WBlog.activity.MicroblogInput
    // 微信： com.tencent.mm //com.tencent.mm.ui.LauncherUI
    // QQ: com.tencent.mobileqq// com.tencent.mobileqq.activity.HomeActivity
    // 淘宝: com.taobao.taobao// com.taobao.tao.detail.activity.DetailActivity
    public static void openOtherApp(Activity activity, String packageName, String activityName,String url) {
        Intent intent = new Intent();
        ComponentName cmp = new ComponentName(packageName, activityName);
        intent.setAction(Intent.ACTION_MAIN);
        if (!TextUtils.isEmpty(url)) {
            intent.setData(Uri.parse(url));
        }
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setComponent(cmp);
        activity.startActivityForResult(intent, 0);
    }

    /**
     * 打开浏览器
     *
     * @param context
     * @param url
     */
    public static void openBrowser(Context context, String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(intent);
    }

    /**
     * 下载新版本apk并更新，文件名默认为应用程序名称ApplicationName
     *
     * @param context
     * @param apkUrl
     */
    public static void updateApk(final Context context, String apkUrl) {
        downloadApkAndInstall(context, apkUrl, getApplicationName(context));
    }

    /**
     * 下载apk并安装，文件名自定义
     *
     * @param context
     * @param apkUrl
     * @param apkName
     */
    public static void downloadApkAndInstall(final Context context, String apkUrl, String apkName) {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setIndeterminate(false);
        progressDialog.setTitle("下载");
        progressDialog.setMessage("正在下载,请稍后...");
        progressDialog.show();
        OkGo.<File>get(apkUrl).tag(context).execute(new FileCallback(new StringBuffer(apkName).append(".apk").toString()) {

            @Override
            public void onError(com.lzy.okgo.model.Response<File> response) {
                super.onError(response);
                PopUtil.toast(context, "下载失败");
                progressDialog.dismiss();
            }

            @Override
            public void onSuccess(com.lzy.okgo.model.Response<File> response) {
                progressDialog.dismiss();
                install(context, response.body());
            }

            @Override
            public void downloadProgress(Progress progress) {
                double currentSizes = FileUtil.formetFileSize(progress.currentSize, FileUtil.SIZETYPE_KB);
                double totalSizes = FileUtil.formetFileSize(progress.totalSize, FileUtil.SIZETYPE_KB);
                progressDialog.setMax((int) totalSizes);
                progressDialog.setProgressNumberFormat("%1d KB/%2d KB");
                progressDialog.setProgress((int) currentSizes);
            }
        });
    }

    /**
     * 下载文件，文件名自定义
     *
     * @param context
     * @param url
     * @param fileName
     */
    public static void downloadFileWithProgressDialog(final Context context, String url, String fileName) {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setIndeterminate(false);
        progressDialog.setTitle("下载");
        progressDialog.setMessage("正在下载,请稍后...");
        progressDialog.show();
        OkGo.<File>get(url).tag(context).execute(new FileCallback(fileName) {

            @Override
            public void onError(com.lzy.okgo.model.Response<File> response) {
                super.onError(response);
                PopUtil.toast(context, "下载失败");
                progressDialog.dismiss();
            }

            @Override
            public void onSuccess(com.lzy.okgo.model.Response<File> response) {
                PopUtil.toast(context, "下载完成");
                progressDialog.dismiss();
            }

            @Override
            public void downloadProgress(Progress progress) {
                double currentSizes = FileUtil.formetFileSize(progress.currentSize, FileUtil.SIZETYPE_KB);
                double totalSizes = FileUtil.formetFileSize(progress.totalSize, FileUtil.SIZETYPE_KB);
                progressDialog.setMax((int) totalSizes);
                progressDialog.setProgressNumberFormat("%1d KB/%2d KB");
                progressDialog.setProgress((int) currentSizes);
            }

        });
    }

    /**
     * 开启apk升级服务
     *
     * @param downloadUrl
     */
    public static void startAppUpgradeService(Context context, String downloadUrl) {
        Intent intent = new Intent(context, AppUpgradeService.class);
        intent.putExtra(AppUpgradeService.DOWNLOAD_URL, downloadUrl);
        context.startService(intent);
    }

    /**
     * 是不是第一次安装后执行，覆盖安装或版本升级不算（因为share数据会保留）
     *
     * @param key SharedPreferences的key,不同的key针对不同的事件，是1对多的关系，多个事件互不影响
     *            注意：每次调用该方法的地方key都应该不同，因为每次执行完都可能改变了share的值
     * @return
     */
    public static boolean isFirstTimeSetupExecute(Context context, String key) {
        if (ZSharedPreferences.getInstance(context).getBoolean(key, true)) {
            ZSharedPreferences.getInstance(context).putBoolean(key, false);
            return true;
        }
        return false;
    }

    /**
     * 是不是新版本升级或者第一次安装（第一次share没值，默认为0）
     *
     * @param key SharedPreferences的key,不同的key针对不同的事件，是1对多的关系，多个事件互不影响
     *            注意：每次调用该方法的地方key都应该不同，因为每次执行完都可能改变了share的值
     * @return
     */
    public static boolean isNewVersion(Context context, String key) {
        int oldVersionCode = ZSharedPreferences.getInstance(context).getInt(key, 0);
        int newVersionCode = AppUtil.getVersionCode(context);
        if (oldVersionCode != newVersionCode) {
            ZSharedPreferences.getInstance(context).putInt(key, newVersionCode);
            return true;
        }
        return false;
    }

    /**
     * 版本号比较
     * 0代表相等，1代表version1大于version2，-1代表version1小于version2
     *
     * @param version1
     * @param version2
     * @return
     */
    public static int compareVersion(String version1, String version2) {
        if (version1.equals(version2)) {
            return 0;
        }
        String[] version1Array = version1.split("\\.");
        String[] version2Array = version2.split("\\.");
        int index = 0;
        // 获取最小长度值
        int minLen = Math.min(version1Array.length, version2Array.length);
        int diff = 0;
        // 循环判断每位的大小
        while (index < minLen
                && (diff = Integer.parseInt(version1Array[index])
                - Integer.parseInt(version2Array[index])) == 0) {
            index++;
        }
        if (diff == 0) {
            // 如果位数不一致，比较多余位数
            for (int i = index; i < version1Array.length; i++) {
                if (Integer.parseInt(version1Array[i]) > 0) {
                    return 1;
                }
            }
            for (int i = index; i < version2Array.length; i++) {
                if (Integer.parseInt(version2Array[i]) > 0) {
                    return -1;
                }
            }
            return 0;
        } else {
            return diff > 0 ? 1 : -1;
        }
    }

    /**
     * 获取app签名md5值
     */
    public static String getSignMd5Str(Context context, String packageName) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
            Signature[] signs = packageInfo.signatures;
            Signature sign = signs[0];
            String signStr = encryptionMD5(sign.toByteArray());
            return signStr;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * MD5加密
     *
     * @param byteStr 需要加密的内容
     * @return 返回 byteStr的md5值
     */
    public static String encryptionMD5(byte[] byteStr) {
        MessageDigest messageDigest = null;
        StringBuffer md5StrBuff = new StringBuffer();
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(byteStr);
            byte[] byteArray = messageDigest.digest();
            for (int i = 0; i < byteArray.length; i++) {
                if (Integer.toHexString(0xFF & byteArray[i]).length() == 1) {
                    md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
                } else {
                    md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
                }
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return md5StrBuff.toString();
    }

    /**
     * 获取所有已安装非系统的app信息
     * @param context
     * @return
     */
    public static void getUserAppInfoList(final Context context, final OnObtainAppInfoListListener onObtainAppInfoListListener) {
        final Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        List<AppInfo> appList = (List<AppInfo>) msg.obj;
                        if (onObtainAppInfoListListener!=null) {
                            onObtainAppInfoListListener.onObtainAppInfoList(appList);
                        }
                        break;
                }
                super.handleMessage(msg);
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<AppInfo> appList = new ArrayList<>(); //用来存储获取的应用信息数据
                PackageManager packageManager=context.getPackageManager();
                List<PackageInfo> packages = packageManager.getInstalledPackages(0);
                for (int i = 0; i < packages.size(); i++) {
                    PackageInfo packageInfo = packages.get(i);
                    AppInfo appInfo = new AppInfo();
//                    appInfo.setAppName(packageInfo.applicationInfo.loadLabel(packageManager).toString());
                    appInfo.setPackageName(packageInfo.packageName);
                    appInfo.setVersionName(packageInfo.versionName);
//                    appInfo.setVersionCode(packageInfo.versionCode);
//                    appInfo.setAppIcon(packageInfo.applicationInfo.loadIcon(packageManager));//获取图片非常耗时，其他属性也都有点耗时，所以不需要的属性不要去获取
                    if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                        appList.add(appInfo);//如果非系统应用，则添加至appList
                    }
                }
                Message message=new Message();
                message.what=0;
                message.obj=appList;
                handler.sendMessage(message);
            }
        }).start();
    }


}
