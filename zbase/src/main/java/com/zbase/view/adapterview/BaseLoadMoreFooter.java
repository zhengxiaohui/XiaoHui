/**
 * @file XFooterView.java
 * @create Mar 31, 2012 9:33:43 PM
 * @author Maxwin
 * @description XListView's footer
 */
package com.zbase.view.adapterview;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * 创建人：郑晓辉
 * 创建日期：2016/7/5
 * 描述：自定义列表底部加载更多抽象类，子类不同的实现加载不同的布局
 */
public abstract class BaseLoadMoreFooter extends FrameLayout {

    public final static int STATE_NORMAL = 0;//普通状态
    public final static int STATE_LOADING = 1;//正在加载
    public final static int STATE_SHOW_ALL = 2;//全部显示完

    private ProgressBar progressBar;
    private TextView textView;

    public BaseLoadMoreFooter(Context context) {
        super(context);
        initView(context);
    }

    public BaseLoadMoreFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public BaseLoadMoreFooter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }


    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(inflateMainLayoutId(), this, false);
        progressBar = (ProgressBar) view.findViewById(findProgressBarId());
        textView = (TextView) view.findViewById(findTextViewId());
        addView(view);
        setScrollToButtomState(BaseLoadMoreFooter.STATE_NORMAL);
    }

    protected abstract int inflateMainLayoutId();

    protected abstract int findProgressBarId();

    protected abstract int findTextViewId();

    protected abstract int findLoadingStringId();

    protected abstract int findShowAllStringId();


    /**
     * 设置滑动到底部的状态
     *
     * @param state
     */
    public void setScrollToButtomState(int state) {
        switch (state) {
            case STATE_NORMAL://普通状态,什么都不显示,整个隐藏
                setVisibility(View.GONE);
                break;
            case STATE_LOADING://正在加载
                setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                textView.setText(findLoadingStringId());
                break;
            case STATE_SHOW_ALL://全部显示完
                setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                textView.setText(findShowAllStringId());
                break;
        }

    }

}
