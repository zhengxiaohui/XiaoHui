package com.zbase.util;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * 创建人：郑晓辉
 * 创建日期：2016/12/5
 * 描述：WheelView数据构造工具
 */
public class WheelViewDataUtil {

    /**
     * 获取0到m-n的整数，间隔为n,并返回formatLength位整数，不足补0
     *
     * @param start        开始位置，开始附加值，比如1900年开始
     * @param interval     间隔，例如5分钟
     * @param max          最大值，例如60分钟
     * @param fromN        是否从n开始，如果true则60分钟,实际上得到的值为0,5,10...55。false的话得到5,10,15...60
     * @param formatLength 格式化位数，比如2位00
     * @return
     */
    public static ArrayList<String> getNMData(int start, int interval, int max, boolean fromN, int formatLength) {
        if (start < 0) {
            throw new IndexOutOfBoundsException("start必须大于0");
        }
        if (interval < 0) {
            throw new IndexOutOfBoundsException("interval必须大于0");
        }
        if (max < 0) {
            throw new IndexOutOfBoundsException("max必须大于0");
        }
        if (start > max) {
            throw new IndexOutOfBoundsException("start不能大于max");
        }
        int offset = 0;//偏移量
        if (fromN) {
            offset = 1;
        }
        int count = (max - start) / interval;
        ArrayList<String> list = new ArrayList<>();
        for (int i = offset; i < count + offset; i++) {
            String data = formatDeci(i * interval + start, getFormatString(formatLength));
            list.add(data);
        }
        return list;
    }

    /**
     * 根据年和月得到日
     *
     * @param year
     * @param month
     * @return
     */
    public static ArrayList<String> getDayOfMonthData(int year, int month) {
        if (year < 1900 || year > 2100) {
            throw new IndexOutOfBoundsException("年超出范围");
        }
        if (month < 1 || month > 12) {
            throw new IndexOutOfBoundsException("月超出范围");
        }
        int day = 0;
        if (month == 2) {
            if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {//是闰年
                day = 29;
            } else {
                day = 28;
            }
        } else {
            if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
                day = 31;
            } else {
                day = 30;
            }
        }
        return getNMData(0, 1, day, true, 2);
    }

    /**
     * 格式化保留n位小数或格式化n位整数
     * #.00 表示两位小数 #.0000四位小数 00两位整数以此类推.
     * <ul>
     * <li>作者：郑晓辉 </li>
     * <li>邮箱：378180078@qq.com </li>
     * <li>时间：2014-12-18 上午11:28:43</li>
     * <li>版本：1.0</li>
     * </ul>
     *
     * @param o       可以被格式化的类型，如float,double,int等,不能传String
     * @param pattern 格式化的模式，#.00 表示两位小数 #.0000四位小数  00两位整以此类推
     * @return
     */
    public static String formatDeci(Object o, String pattern) {
        return new DecimalFormat(pattern).format(o);
    }

    /**
     * 根据传入的对象和需要格式化的长度，获取格式化后的文本
     * @param o 可以被格式化的类型，如float,double,int等,不能传String
     * @param formatLength
     * @return
     */
    public static String formatZero(Object o, int formatLength) {
        return formatDeci(o,getFormatString(formatLength));
    }

    /**
     * 根据传入的位数返回需要的格式化字符串的格式，比如返回"00"
     * @param formatLength 格式化位数，比如2
     * @return
     */
    private static String getFormatString(int formatLength){
        if (formatLength < 1 || formatLength > 10) {
            throw new IndexOutOfBoundsException("格式化位数必须在1-10之间");
        }
        String formatString = "";
        for (int i = 0; i < formatLength; i++) {
            formatString += "0";
        }
        return formatString;
    }

}
