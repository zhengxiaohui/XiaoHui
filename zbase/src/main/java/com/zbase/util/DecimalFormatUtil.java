/**
 *
 */
package com.zbase.util;

import java.text.DecimalFormat;

/**
 * <ul>
 * <li>作者：郑晓辉 </li>
 * <li>邮箱：378180078@qq.com </li>
 * <li>时间：2014-12-18 上午11:28:35</li>
 * <li>版本：1.0</li>
 * </ul>
 */
public class DecimalFormatUtil {
    /**
     * 格式化保留n位小数或格式化n位整数
     * #表示没有则为空，0表示如果没有则该位补0.
     * <ul>
     * <li>作者：郑晓辉 </li>
     * <li>邮箱：378180078@qq.com </li>
     * <li>时间：2014-12-18 上午11:28:43</li>
     * <li>版本：1.0</li>
     * </ul>
     *
     * @param o       可以被格式化的类型，如float,double,int等
     * @param pattern 格式化的模式，0.00 表示两位小数(如果用#.00则会出现".xx"的情况，就是整数个位变没了) 0.0000四位小数  00两位整以此类推
     * @return
     */
    public static String formatDecimal(Object o, String pattern) {
        return new DecimalFormat(pattern).format(o);
    }

    public static String formatOneDecimal(Object o) {
        return formatDecimal(o, "0.0");
    }

    public static String formatTwoDecimal(Object o) {
        return formatDecimal(o, "0.00");
    }

    public static String formatThreeDecimal(Object o) {
        return formatDecimal(o, "0.000");
    }
}
