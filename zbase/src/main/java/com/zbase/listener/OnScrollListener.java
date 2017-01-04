package com.zbase.listener;

/**
 * 创建人：郑晓辉
 * 创建日期：2016/6/7
 * 描述：滚动监听,横竖ScrollView通用
 */
public interface OnScrollListener {
    void onBegin();
    void onEnd();
    void onScroll();
}
