package com.zbase.view.adapterview;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 创建人：郑晓辉
 * 创建日期：2015/9/28
 * 主题：RecyclerView高度自适应的布局
 * 描述：还没测试，貌似不行
 */
@Deprecated
public class WrapLinearLayoutManager2 extends LinearLayoutManager {

    public WrapLinearLayoutManager2(Context context) {
        super(context);
    }

    public WrapLinearLayoutManager2(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    @Override
    public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec, int heightSpec) {
        int newHeightSpec = View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, View.MeasureSpec.AT_MOST);
        super.onMeasure(recycler, state, widthSpec, newHeightSpec);
    }
}
