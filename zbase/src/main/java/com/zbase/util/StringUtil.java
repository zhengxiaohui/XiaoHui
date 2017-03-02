package com.zbase.util;

import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.widget.EditText;

import java.lang.reflect.Field;

/**
 * 所有常用字符串处理工具类
 *
 * @author z
 */
public class StringUtil {
    public static boolean isEmptyOrNull(String str) {
        return str == null || str.trim().length() <= 0 || str.trim().equals("null") || str.trim().equals("NULL")
                || str.trim().equals("");
    }

    public static boolean isEmptyOrZero(String str) {
        return isEmptyOrNull(str) || "0".equals(str);
    }

    public static boolean isNotEmpty(String str) {
        return !isEmptyOrNull(str);
    }

    // 是否小写
    public static boolean isAcronym(String word) {
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (!Character.isLowerCase(c)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isA2Z(char cha) {
        boolean bool = false;
        String string = cha + ""; // 把字符转换成字符串
        String reg = "[a-zA-Z]";
        if (string.matches(reg)) {
            bool = true;
        }
        return bool;
    }

    /**
     * 设置TextView或EditText最大显示长度，超过部分被截掉，并加上"..."省略号
     * 中英文字符数一样
     *
     * @param original  原始字符串
     * @param maxLength 最大长度
     * @return
     */
    private String limitTextViewMaxLength(String original, int maxLength) {
        if (original.length() > maxLength) {
            original = original.substring(0, maxLength) + "...";
        }
        return original;
    }

    /**
     * 替换11位电话号码中间4位为*号
     *
     * @param originalString
     * @return
     */
    public static String replaceSymbolMiddleAsterisk(String originalString) {
        return replaceSymbol(originalString, 3, 7, "*");
    }

    /**
     * 字符串中某个位置到某个位置之间替换成需要的符号，如*,#等
     *
     * @param originalString 原始传入字符串
     * @param start          开始位置，包含
     * @param end            结束位置，不包含
     * @param symbol         替换成的符号，如*,#等
     * @return
     */
    public static String replaceSymbol(String originalString, int start, int end, String symbol) {
        String result = "";
        int length = originalString.length();
        if (start <= end && isNotEmpty(symbol)) {
            String subString = originalString.substring(start, end);
            String symbolString = "";
            for (int i = 0; i < subString.length(); i++) {
                symbolString += symbol;
            }
            result = originalString.substring(0, start) + symbolString + originalString.substring(end, length);
        }
        return result;
    }

    /**
     * 获取设置的最大长度(还没测试过)
     *
     * @return
     */
    public int getMaxLength(EditText editText) {
        int length = 0;
        try {
            InputFilter[] inputFilters = editText.getFilters();
            for (InputFilter filter : inputFilters) {
                Class<?> c = filter.getClass();
                if (c.getName().equals("android.text.InputFilter$LengthFilter")) {
                    Field[] f = c.getDeclaredFields();
                    for (Field field : f) {
                        if (field.getName().equals("mMax")) {
                            field.setAccessible(true);
                            length = (Integer) field.get(filter);
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return length;
    }

    /**
     * 设置EditText的hint文字大小（还没测试）
     * @param editText
     * @param hintTextSize
     */
    public static void setHintTextSize(final EditText editText, final float hintTextSize) {
        final float inputTextSize = editText.getTextSize();
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable arg0) {

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

            }

            @Override
            public void onTextChanged(CharSequence arg0, int start, int before, int count) {
                if (arg0.length() == 0) {
                    editText.setTextSize(TypedValue.COMPLEX_UNIT_SP, hintTextSize);
                } else {
                    editText.setTextSize(TypedValue.COMPLEX_UNIT_SP, inputTextSize);
                }
            }
        });
    }

}
