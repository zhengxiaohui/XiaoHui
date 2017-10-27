package com.zbase.view.adapterview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 放在ScrollView中，高度能够撑开自适应，并且自己不能滑动，让外面的ScrollView滑动
 * 注意：嵌套在RecyclerView中不能显示，还是优先使用LinearListView
 */
public class FullListView extends ListView {

    public FullListView(Context context) {
        super(context);
    }

    public FullListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FullListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

}
