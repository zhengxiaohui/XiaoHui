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
public class FormatUtil {
	/**
	 * 格式化保留n位小数或格式化n位整数
	 * #.00 表示两位小数 #.0000四位小数 00两位整数以此类推.
	 * <ul>
	 * <li>作者：郑晓辉 </li> 
	 * <li>邮箱：378180078@qq.com </li> 
	 * <li>时间：2014-12-18 上午11:28:43</li>
	 * <li>版本：1.0</li>
	 * </ul>
	 * @param o 可以被格式化的类型，如float,double,int等
	 * @param pattern 格式化的模式，#.00 表示两位小数 #.0000四位小数  00两位整以此类推
	 * @return
	 */
    public static String formatDeci(Object o,String pattern) {
        return new DecimalFormat(pattern).format(o);
    }
}
