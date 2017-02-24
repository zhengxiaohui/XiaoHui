package com.zbase.view.layoutmanager;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * RecyclerView四种宽高自适应的布局
 * 放在ScrollView或HorizontalScrollView中，高度或高度能够撑开自适应
 */
@Deprecated
public class WrapLinearLayoutManager extends LinearLayoutManager{

    private int mode;
    public static final int MODE_HORIZONTAL_WRAP_HEIGHT=0;//水平RecyclerView高度自适应，根据子项高度确定外面高度，放在ScrollView中，高度能够撑开自适应
    public static final int MODE_HORIZONTAL_WRAP_WIDTH=1;//水平RecyclerView宽度自适应，根据子项宽度计算外面宽度，不能滑动，放在HorizontalScrollView里面时候使用
    public static final int MODE_VERTICAL_WRAP_HEIGHT=2;//垂直RecyclerView高度自适应，根据子项高度计算外面高度，不能滑动，放在ScrollView里面时候使用
    public static final int MODE_VERTICAL_WRAP_WIDTH=3;//垂直RecyclerView宽度自适应，根据子项宽度计算外面宽度，放在HorizontalScrollView中，宽度能够撑开自适应

    public WrapLinearLayoutManager(Context context,int mode) {
        super(context);
        // TODO Auto-generated constructor stub
        this.mode=mode;
    }

    @Override
    public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec,int heightSpec) {
        View view = recycler.getViewForPosition(0);
        if(view != null){
            measureChild(view, widthSpec, heightSpec);
            int measuredWidth=0;
            int measuredHeight=0;
            switch (mode) {
                case MODE_HORIZONTAL_WRAP_HEIGHT:
                     measuredWidth = View.MeasureSpec.getSize(widthSpec);
                     measuredHeight = view.getMeasuredHeight();
                    break;
                case MODE_HORIZONTAL_WRAP_WIDTH:
                     measuredWidth = view.getMeasuredWidth()*state.getItemCount();//子项的宽度乘以多少项
                     measuredHeight =View.MeasureSpec.getSize(heightSpec);//高度还是原来设置的高度
                    break;
                case MODE_VERTICAL_WRAP_HEIGHT:
                     measuredWidth = View.MeasureSpec.getSize(widthSpec);//宽度还是原来设置的宽度
                     measuredHeight =view.getMeasuredHeight()*state.getItemCount();//子项的高度乘以多少项
                    break;
                case MODE_VERTICAL_WRAP_WIDTH:
                     measuredWidth = view.getMeasuredWidth();//根据子项宽度确定外面宽度
                     measuredHeight =View.MeasureSpec.getSize(heightSpec);//高度还是原来的设置
                    break;
            }

            setMeasuredDimension(measuredWidth, measuredHeight);
        }
    }

}
