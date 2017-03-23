package com.zbase.manager;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

/**
 * 创建人：郑晓辉
 * 创建日期：2017/3/22
 * 描述：控制RecyclerView是否可以滑动
 */
public class CanScrollLinearLayoutManager extends LinearLayoutManager {

    private boolean isScrollEnabled = true;

    public CanScrollLinearLayoutManager(Context context) {
        super(context);
    }

    public void setScrollEnabled(boolean flag) {
        this.isScrollEnabled = flag;
    }

    @Override
    public boolean canScrollVertically() {
        //Similarly you can customize "canScrollHorizontally()" for managing horizontal scroll
        return isScrollEnabled && super.canScrollVertically();
    }
}
