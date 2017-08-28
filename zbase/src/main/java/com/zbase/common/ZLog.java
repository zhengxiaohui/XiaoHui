package com.zbase.common;


import com.zbase.BuildConfig;

import java.util.List;
import java.util.Map;

/**
 * 自定义日志打印，能够在发布app时关闭所有日志打印，防止信息泄露，还可以只打印某个工程师的日志
 *
 * @author z
 */
public class ZLog {

    /**
     * Send a DEBUG log message.
     *
     * @param tag Used to identify the source of a log message. It usually
     *            identifies the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static void d(String tag, String msg) {
        if (BaseApplication.debugMode)
            android.util.Log.d(tag, msg);
    }

    /**
     * Send an ERROR log message.
     *
     * @param tag Used to identify the source of a log message. It usually
     *            identifies the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static void e(String tag, String msg) {
        if (BaseApplication.debugMode)
            android.util.Log.e(tag, msg);
    }

    /**
     * Send a ERROR log message and log the exception.
     *
     * @param tag Used to identify the source of a log message. It usually
     *            identifies the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr  An exception to log
     */
    public static void e(String tag, String msg, Throwable tr) {
        if (BaseApplication.debugMode)
            android.util.Log.e(tag, msg, tr);
    }

    /**
     * Send a VERBOSE log message.
     *
     * @param tag Used to identify the source of a log message. It usually
     *            identifies the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static void v(String tag, String msg) {
        if (BaseApplication.debugMode)
            android.util.Log.v(tag, msg);
    }

    /**
     * Send a WARN log message.
     *
     * @param tag Used to identify the source of a log message. It usually
     *            identifies the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static void w(String tag, String msg) {
        if (BaseApplication.debugMode)
            android.util.Log.w(tag, msg);
    }

    /**
     * Send a INFO log message.
     *
     * @param tag Used to identify the source of a log message. It usually
     *            identifies the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static void i(String tag, String msg) {
        if (BaseApplication.debugMode)
            android.util.Log.i(tag, msg);
    }

    /**
     * 打印Map<String, String>类型
     *
     * @param tag
     * @param parames
     */
    public static void d(String tag, Map<String, String> parames) {
        if (BaseApplication.debugMode)
            for (Map.Entry entry : parames.entrySet()) {
                String strKey = (String) entry.getKey();
                String strValue = entry.getValue().toString();
                android.util.Log.d(tag, strKey);
                android.util.Log.d(tag, strValue);
            }
    }

    /**
     * 打印List<String>类型
     *
     * @param tag
     * @param parames
     */
    public static void d(String tag, List<String> parames) {
        if (BaseApplication.debugMode)
            for (String strContent : parames) {
                android.util.Log.d(tag, strContent);
            }
    }

    /**
     * zheng工程师打出来的dlog
     *
     * @param msg
     */
    public static void dZheng(String msg) {
        if (BaseApplication.debugMode)
            android.util.Log.d("zheng", msg);
    }

    /**
     * chen工程师打出来的dlog
     *
     * @param msg
     */
    public static void dChen(String msg) {
        if (BaseApplication.debugMode)
            android.util.Log.d("chen", msg);
    }

    /**
     * li工程师打出来的dlog
     *
     * @param msg
     */
    public static void dLi(String msg) {
        if (BaseApplication.debugMode)
            android.util.Log.d("li", msg);
    }

}
