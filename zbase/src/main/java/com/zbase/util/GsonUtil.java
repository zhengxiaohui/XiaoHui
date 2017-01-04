package com.zbase.util;

import com.google.gson.Gson;

/**
 * 创建人：郑晓辉
 * 创建日期：2016/5/26
 * 描述：
 */
public class GsonUtil {

    private static Gson gson;

    public static <T> T fromJson(String json, Class<T> classOfT) {
        if (gson == null) {
            gson = new Gson();
        }
        T t = gson.fromJson(json, classOfT);
        return t;
    }

}
