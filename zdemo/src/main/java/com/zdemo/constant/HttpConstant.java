package com.zdemo.constant;

/**
 * 创建人：郑晓辉
 * 创建日期：2015/11/3
 * 描述：
 */
public class HttpConstant {
//    public static final String BASE_URL="http://192.168.2.7:8080/";
//    public static final String BASE_URL="http://10.0.2.2:8080/HttpTest/";
    public static final String BASE_URL="http://192.168.30.152:8080/HttpTest/";

    public static final String TEST_URL=BASE_URL+"test";
    public static final String QUERY_HOTTEST_ACTIVITY_URL=BASE_URL+"queryHottestActivity";//最热
    public static final String QUERY_LATEST_ACTIVITY_URL=BASE_URL+"queryLatestActivity";//最新

    public static final String ADD_USER_URL=BASE_URL+"addUser";
    public static final String DELETE_USER_URL=BASE_URL+"deleteUser";
    public static final String EDIT_USER_URL=BASE_URL+"editUser";
    public static final String QUERY_USER_URL=BASE_URL+"queryUser";
}
