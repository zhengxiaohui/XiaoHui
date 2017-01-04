package com.zbase.view.adapterview;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * 可在ScrollView中完全显示的GridView
 */
public class FullGridView extends GridView {

    public FullGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FullGridView(Context context) {
        super(context);
    }

    public FullGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //重写测量高度以适应ScrollView
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}    
