package com.zbase.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 日期和时间工具类
 *
 * @author z
 */
public class DateTimeUtil {

    public static final String YM = "yyyy-MM";
    public static final String MD = "MM-dd";
    public static final String YMD = "yyyy-MM-dd";
    public static final String HM = "HH:mm";
    public static final String HMS = "HH:mm:ss";
    public static final String YMDHM = "yyyy-MM-dd HH:mm";// 年月日时分
    public static final String YMDHMS = "yyyy-MM-dd HH:mm:ss";// 年月日时分秒
    public static final String YMDHMSS = "yyyy-MM-dd HH:mm:ss:SSS";// 日期时间精确到毫秒
    public static final String CYMD = "yyyy年MM月dd日";// 中国日期
    public static final String CYMDHM = "yyyy年MM月dd日 HH时mm分";// 中国日期时间
    public static final String CYMDHMS = "yyyy年MM月dd日 HH时mm分ss秒";// 中国日期时间

    /**
     * 获取到时间组成文件名的一部分，一般是生成图片，照片，音乐，视频，文件等时使用，格式中不能有
     * 空格，英文的冒号:，只能用中文的冒号：
     *
     * @return
     */
    public static String getNowDateTimeAsFileName() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH：mm：ss");
        Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
        String time = formatter.format(curDate);
        return time;
    }

    /**
     * 获取当前的时间
     *
     * @param format 传入需要返回的时间格式
     * @return
     */
    public static String getNowDateTimeString(String format) {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    public static String dateToString(Date date, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String dateString = formatter.format(date);
        return dateString;
    }

    public static Date stringToDate(String strDate, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = sdf.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 自定义日期时间格式
     *
     * @param date       日期时间字符串，如2014-8-1 13:11:33.0
     * @param dateFormat 格式化字符串，如HH:mm:ss
     * @return
     */
    public static String formatDate(String date, String dateFormat) {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf2 = new SimpleDateFormat(dateFormat);
        try {
            Date d = sdf1.parse(date);
            return sdf2.format(d);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 判断是否润年
     *
     * @param ddate
     * @return
     */
    public static boolean isLeapYear(String ddate) {

        /**
         * 详细设计： 1.被400整除是闰年，否则： 2.不能被4整除则不是闰年 3.能被4整除同时不能被100整除则是闰年
         * 3.能被4整除同时能被100整除则不是闰年
         */
        Date d = stringToDate(ddate,YMDHMS);
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(d);
        int year = gc.get(Calendar.YEAR);
        if ((year % 400) == 0)
            return true;
        else if ((year % 4) == 0) {
            if ((year % 100) == 0)
                return false;
            else
                return true;
        } else
            return false;
    }

    /**
     * 传入年月日，返回2016-01-15格式的字符串，常用于提交到接口给数据库使用
     *
     * @param year
     * @param monthOfYear
     * @param dayOfMonth
     * @return
     */
    public static String getFormattedDate(int year, int monthOfYear, int dayOfMonth) {
        String month = monthOfYear + "";
        if (month.length() == 1) {
            month = "0" + month;
        }
        String day = dayOfMonth + "";
        if (day.length() == 1) {
            day = "0" + day;
        }
        return year + "-" + month + "-" + day;
    }

    /**
     * 传入年月日时分，返回2016-01-15 06:30格式的字符串，常用于提交到接口给数据库使用
     *
     * @param year
     * @param monthOfYear
     * @param dayOfMonth
     * @param hourOfDay
     * @param min
     * @return
     */
    public static String getFormattedDateTime(int year, int monthOfYear, int dayOfMonth, int hourOfDay, int min) {
        String month = monthOfYear + "";
        if (month.length() == 1) {
            month = "0" + month;
        }
        String day = dayOfMonth + "";
        if (day.length() == 1) {
            day = "0" + day;
        }
        String hour = hourOfDay + "";
        if (hour.length() == 1) {
            hour = "0" + hour;
        }
        return year + "-" + month + "-" + day + " " + hour + ":" + min + ":00";
    }

}
