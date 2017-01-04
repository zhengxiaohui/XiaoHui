package com.zbase.util;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.zbase.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;

public final class FileUtil {

    private static String mSdRootPath = Environment.getExternalStorageDirectory().getPath();//sd卡的根目录
    public static final int SIZETYPE_B = 1;// 获取文件大小单位为B的double值
    public static final int SIZETYPE_KB = 2;// 获取文件大小单位为KB的double值
    public static final int SIZETYPE_MB = 3;// 获取文件大小单位为MB的double值
    public static final int SIZETYPE_GB = 4;// 获取文件大小单位为GB的double值

    // 打开文件
    public static void openFile(Context context, File file) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.content.Intent.ACTION_VIEW);
        String type = getMIMEType(file);
        intent.setDataAndType(Uri.fromFile(file), type);
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, context.getString(R.string.no_app_to_open), Toast.LENGTH_SHORT)
                    .show();
        }
    }

    // 获取文件格式
    public static String getMIMEType(File f) {

        String end = f
                .getName()
                .substring(f.getName().lastIndexOf(".") + 1,
                        f.getName().length()).toLowerCase();
        String type = "";
        if (end.equals("mp3") || end.equals("aac") || end.equals("amr")
                || end.equals("mpeg") || end.equals("mp4")) {
            type = "audio";
        } else if (end.equals("jpg") || end.equals("gif") || end.equals("png")
                || end.equals("jpeg")) {
            type = "image";
        } else if (end.equals("pdf")) {
            type = "application/pdf";
        } else if (end.equals("doc")) {
            type = "application/msword";
        } else if (end.equals("ppt")) {
            type = "application/vnd.ms-powerpoint";
        } else if (end.equals("xls")) {
            type = "application/vnd.ms-excel";
        } else if (end.equals("txt")) {
            // type = "application/msword";
            type = "text/plain";
        } else {
            type = "*";
        }
        type += "/";
        return type;
    }

    public static String getCacheDir(Context context) {
        String strHomeDir = "";// 主目录
        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED)) {// 有SD卡
            String str = android.os.Environment.getExternalStorageDirectory()
                    .toString();
            if (!str.endsWith("/") && !str.endsWith("\\")) {
                str += '/';
            }
            strHomeDir = str;
        } else {
            strHomeDir = context.getFilesDir().getAbsolutePath();
        }

        return strHomeDir;
    }


    /**
     * 判断文件是否存在
     *
     * @param path 完整路径
     * @return
     */
    public static boolean exists(String path) {
        try {
            File file = new File(path);
            if (!file.exists() || file.isFile() == false)// 不存在
                return false;
            else
                return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除单个文件
     *
     * @param filePath
     * @return
     */
    public static boolean delFile(String filePath) {
        boolean ret = false;
        try {
            File file = new File(filePath);
            if (file.exists()) {
                file.delete();
                ret = true;
            }
        } catch (Exception e) {
            return false;
        }
        return ret;
    }

    /**
     * 删除文件夹
     *
     * @param dirPath
     * @return
     */
    public static boolean delDir(String dirPath) {
        boolean ret = false;
        try {
            File file = new File(dirPath);
            if (file.exists()) {
                File[] files = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    if (files[i].isDirectory()) {
                        if (!delDir(files[i].getName())) {
                            return false;
                        }
                    } else {
                        files[i].delete();
                    }
                }
                file.delete(); // 删除空文件夹
                ret = true;
            } else
                return true;
        } catch (Exception e) {
            return false;
        }
        return ret;
    }

    /**
     * 创建任意深度的文件所在文件夹
     *
     * @param path
     * @return File对象
     */
    public static File createDir(String path) {
        File file = new File(path);
        // 寻找父目录是否存在
        File parent = new File(file.getAbsolutePath().substring(0,
                file.getAbsolutePath().lastIndexOf(File.separator)));
        // 如果父目录不存在，则递归寻找更上一层目录
        if (!parent.exists()) {
            createDir(parent.getPath());
            // 创建父目录
            file.mkdirs();
        } else {
            // 判断自己是否存在
            File self = new File(path);
            if (!self.exists())
                self.mkdirs();
        }

        return file;
    }


    /**
     * 删除单个文件
     *
     * @param sPath 被删除文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }

    /**
     * 删除目录（文件夹）以及目录下的文件
     *
     * @param sPath 被删除目录的文件路径
     * @return 目录删除成功返回true，否则返回false
     */
    public static boolean deleteDirectory(String sPath) {
        // 如果sPath不以文件分隔符结尾，自动添加文件分隔符
        if (!sPath.endsWith(File.separator)) {
            sPath = sPath + File.separator;
        }
        File dirFile = new File(sPath);
        // 如果dir对应的文件不存在，或者不是一个目录，则退出
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        boolean flag = true;
        // 删除文件夹下的所有文件(包括子目录)
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            // 删除子文件
            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag)
                    break;
            } // 删除子目录
            else {
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag)
                    break;
            }
        }
        if (!flag)
            return false;
        // 删除当前目录
        if (dirFile.delete()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 根据路径删除指定的目录或文件，无论存在与否
     *
     * @param sPath 要删除的目录或文件
     * @return 删除成功返回 true，否则返回 false。
     */
    public static boolean DeleteFolder(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        // 判断目录或文件是否存在
        if (!file.exists()) { // 不存在返回 false
            return flag;
        } else {
            // 判断是否为文件
            if (file.isFile()) { // 为文件时调用删除文件方法
                return deleteFile(sPath);
            } else { // 为目录时调用删除目录方法
                return deleteDirectory(sPath);
            }
        }
    }

    // 递归
    public static long getFileSize(File f) throws Exception// 取得文件夹大小
    {
        long size = 0;
        File flist[] = f.listFiles();
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size = size + getFileSize(flist[i]);
            } else {
                size = size + flist[i].length();
            }
        }
        return size;
    }

    public static long getFileCount(File f) {// 递归求取目录文件个数
        long size = 0;
        File flist[] = f.listFiles();
        size = flist.length;
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size = size + getFileCount(flist[i]);
                size--;
            }
        }
        return size;
    }

    public static String FormetFileSize(long fileS) {// 转换文件大小
        DecimalFormat df = new DecimalFormat("0.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "K";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }

    /**
     * 转换文件大小,指定转换的类型
     *
     * @param fileS
     * @param sizeType
     * @return
     */
    public static double FormetFileSize(long fileS, int sizeType) {
        DecimalFormat df = new DecimalFormat("#.00");
        double fileSizeLong = 0;
        switch (sizeType) {
            case SIZETYPE_B:
                fileSizeLong = Double.valueOf(df.format((double) fileS));
                break;
            case SIZETYPE_KB:
                fileSizeLong = Double.valueOf(df.format((double) fileS / 1024));
                break;
            case SIZETYPE_MB:
                fileSizeLong = Double.valueOf(df.format((double) fileS / 1048576));
                break;
            case SIZETYPE_GB:
                fileSizeLong = Double.valueOf(df
                        .format((double) fileS / 1073741824));
                break;
            default:
                break;
        }
        return fileSizeLong;
    }

    //获取网络下载文件的大小
    public static int getFileLength(String fileUrl) {
        URL url = null;
        try {
            url = new URL(fileUrl);
        } catch (MalformedURLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        HttpURLConnection httpConnection = null;
        try {
            httpConnection = (HttpURLConnection) url.openConnection();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        // 设置连接超时时间为10000ms
        httpConnection.setConnectTimeout(10000);
        // 设置读取数据超时时间为10000ms
        httpConnection.setReadTimeout(10000);
        try {
            httpConnection.connect();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return httpConnection.getContentLength();
    }

    /**
     * 读取Assert下面的文件
     *
     * @param context
     * @param strAssertFileName 文件全路径名，如"mytxt.txt"或"txt/mytxt.txt"
     * @return
     */
    public static String readAssertResource(Context context, String strAssertFileName) {
        AssetManager assetManager = context.getAssets();
        String strResponse = "";
        try {
            InputStream inputStream = assetManager.open(strAssertFileName);
            BufferedReader bufferedReader = null;
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            try {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
            } catch (IOException e) {
            } finally {
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                    }
                }
            }
            strResponse = stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return strResponse;
    }

    // 是否存在SD卡
    public static boolean isSDCardExist() {
        return android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
    }

    /**
     * 调用此方法自动计算指定文件或指定文件夹的大小
     *
     * @param filePath 文件路径
     * @return 计算好的带B、KB、MB、GB的字符串
     */
    public static String getAutoFileOrFilesSize(String filePath) {
        File file = new File(filePath);
        long blockSize = 0;
        try {
            if (file.isDirectory()) {
                blockSize = getFileSizes(file);
            } else {
                blockSize = getFileSize(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("获取文件大小", "获取失败!");
        }
        return FormetFileSize(blockSize);
    }

    /**
     * 获取指定文件夹
     *
     * @param f
     * @return
     * @throws Exception
     */
    private static long getFileSizes(File f) throws Exception {
        long size = 0;
        File flist[] = f.listFiles();
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size = size + getFileSizes(flist[i]);
            } else {
                size = size + getFileSize(flist[i]);
            }
        }
        return size;
    }

    /**
     * 获取本应用的缓存数据
     *
     * @return
     */
    public static String getTheAppCacheSize(Context context) {
        double filesSize = 0.00;

        if (context.getFilesDir().exists()) {
            filesSize = getFileOrFilesSize(context.getFilesDir()
                    .getAbsolutePath(), 3);
        }

        // if (context.getCacheDir().exists()) {
        // filesSize += getFileOrFilesSize(context.getCacheDir()
        // .getAbsolutePath(), 3);
        // }
        //
        // if (new File("/data/data/" + context.getPackageName() + "/databases")
        // .exists()) {
        // filesSize += getFileOrFilesSize(
        // new File("/data/data/" + context.getPackageName()
        // + "/databases").getAbsolutePath(), 3);
        // }
        //
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            filesSize += getFileOrFilesSize(
                    new File("/data/data/" + context.getPackageName()
                            + "/databases").getAbsolutePath(), 3);
        }

        String filesSizeStr = filesSize + "";
        if (filesSizeStr.length() >= 4) {
            filesSizeStr = filesSizeStr.substring(0, 4);
        }

        return filesSizeStr + "MB";
    }

    /**
     * 获取文件指定文件的指定单位的大小
     *
     * @param filePath 文件路径
     * @param sizeType 获取大小的类型1为B、2为KB、3为MB、4为GB
     * @return double值的大小
     */
    public static double getFileOrFilesSize(String filePath, int sizeType) {
        File file = new File(filePath);
        long blockSize = 0;
        try {
            if (file.isDirectory()) {
                blockSize = getFileSizes(file);
            } else {
                blockSize = getFileSize(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("获取文件大小", "获取失败!" + e.getMessage());
        }
        return FormetFileSize(blockSize, sizeType);
    }

}
