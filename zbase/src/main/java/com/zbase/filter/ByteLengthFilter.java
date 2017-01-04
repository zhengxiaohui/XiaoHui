package com.zbase.filter;

import android.text.InputFilter;
import android.text.Spanned;

/**
 * 创建人：CXQ
 * 创建日期：2016/10/27
 * 描述：限制中英文输入总字节数，限制最大长度。例如：英文“abcd”占4位，中文“天天向上”占8位，总和12位字节。如果是系统的MaxLength属性则总和是8位字符
 * 用法:InputFilter[] inputFilters = {new EditLenthInputFilter(16)};//必须是数组，因为能传多个Filter
       et_nickname.setFilters(inputFilters);
  或者：et_nickname.setFilters(new InputFilter[]{new EditLenthInputFilter(16)});
  对应的限制最小长度的判断写法：nickName.getBytes("GBK").length > 3 ，必须是GBK如果是UTF则变成每个中文算3个字节而不是2个字节
 */
public class ByteLengthFilter implements InputFilter {
    private int maxInputLength;

    public ByteLengthFilter(int maxLength) {
        maxInputLength = maxLength;
    }

    /**
     * @param source 为即将输入的字符串
     * @param start  source的start，一般为0
     * @param end    end也可理解为source长度了，一般为source长度-1
     * @param dest   输入框中原来的内容
     * @param dstart 要替换或者添加的起始位置，即光标所在的位置
     * @param dend   要替换或者添加的终止始位置，若为选择一串字符串进行更改，则为选中字符串 最后一个字符在dest中的位置。
     * @return 返回你打算放入替换位置的CharSequence。包括空字符串表示不允许输入，或者null来表示将所有字符都输入
     */
    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        int leftLength = maxInputLength - calculateLength(dest.toString());//剩余可填写的长度
        if (leftLength > 0) {//如果可填写的长度>0
            int subLength = 0;//自增长长度
            int position = 0;//截取位置
            if (source.length() > leftLength) {
                source = source.subSequence(0, leftLength);
            }
            for (int i = 0; i < source.length(); i++) {
                if (isCh(source.charAt(i))) {//如果是中文，占用两个字符
                    subLength = subLength + 2;
                } else {//否则，占用一个字符
                    subLength++;
                }
                if (subLength <= leftLength) {//如果字符小于=可填写的长度
                    position = i + 1;
                } else {
                    break;
                }
            }
            return source.subSequence(0, position);
        } else {//如果可填写的长度<0,不可再填写新的
            return "";
        }
    }

    private boolean isCh(char c) {
        if ((c >= 0x2E80 && c <= 0xFE4F) || (c >= 0xA13F && c <= 0xAA40) || c >= 0x80) { // 中文字符范围0x4e00 0x9fbb
            return true;//是中文
        } else {
            return false;
        }
    }

    private int calculateLength(String etstring) {
        char[] ch = etstring.toCharArray();
        int varlength = 0;
        for (int i = 0; i < ch.length; i++) {
            // changed by zyf 0825 , bug 6918，加入中文标点范围 ， TODO 标点范围有待具体化
            if ((ch[i] >= 0x2E80 && ch[i] <= 0xFE4F) || (ch[i] >= 0xA13F && ch[i] <= 0xAA40) || ch[i] >= 0x80) { // 中文字符范围0x4e00 0x9fbb
                varlength = varlength + 2;
            } else {
                varlength++;
            }
        }
        // 这里也可以使用getBytes,更准确嘛
        // varlength = etstring.getBytes(CharSet.forName("GBK")).lenght;// 编码根据自己的需求，注意u8中文占3个字节...
        return varlength;
    }
}
