package com.zbase.listener;


/**
 * 创建人：郑晓辉
 * 创建日期：2016/6/7
 * 描述：更细控制的滚动监听
 */
public interface ScrollViewListener {
    void onScrollChanged(int x, int y, int oldx, int oldy);
}
