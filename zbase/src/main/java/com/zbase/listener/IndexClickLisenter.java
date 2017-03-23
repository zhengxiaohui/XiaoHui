package com.zbase.listener;

import android.view.View;

/**
 * 创建人：郑晓辉
 * 创建日期：2017/3/22
 * 描述：记住点击索引的点击事件抽象子类
 */
public abstract class IndexClickLisenter implements View.OnClickListener {

    protected int index;//点击索引,子类可访问

    public IndexClickLisenter(int index) {
        this.index = index;
    }
}
