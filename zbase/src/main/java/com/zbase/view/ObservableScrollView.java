package com.zbase.view;

/**
 * 创建人：郑晓辉
 * 创建日期：2016/6/7
 * 描述：可监听滚动的位置，开始，中间，结束。或者直接监听x,y,oldx,oldy
 */

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

import com.zbase.listener.OnScrollListener;
import com.zbase.listener.ScrollViewListener;

public class ObservableScrollView extends ScrollView {

    private OnScrollListener onScrollListener;//滚动监听最上，最下，其他
    private ScrollViewListener scrollViewListener;////更细控制的滚动监听，直接获取ScrollView本身，x,y,oldx,oldy

    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }

    public void setScrollViewListener(ScrollViewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }

    public ObservableScrollView(Context context) {
        super(context);
    }

    public ObservableScrollView(Context context, AttributeSet attrs,
                                int defStyle) {
        super(context, attrs, defStyle);
    }

    public ObservableScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        if (scrollViewListener != null) {//更细控制的滚动监听
            scrollViewListener.onScrollChanged(x, y, oldx, oldy);
        }

        if (onScrollListener != null) {
            if (y == 0) {
                onScrollListener.onBegin();
            } else if (getChildAt(getChildCount() - 1).getBottom() == getHeight() + y) {
                onScrollListener.onEnd();
            } else {
                onScrollListener.onScroll();
            }
        }

    }

}
