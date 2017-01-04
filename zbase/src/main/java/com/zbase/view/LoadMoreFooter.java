package com.zbase.view;

import android.content.Context;
import android.util.AttributeSet;

import com.zbase.R;
import com.zbase.view.adapterview.BaseLoadMoreFooter;

/**
 * 创建人：郑晓辉
 * 创建日期：2016/7/5
 * 描述：自定义列表底部加载更多实现类，不同的实现类加载不同的布局。这里定义一个默认的
 */
public class LoadMoreFooter extends BaseLoadMoreFooter {

    public LoadMoreFooter(Context context) {
        super(context);
    }

    public LoadMoreFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LoadMoreFooter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int inflateMainLayoutId() {
        return R.layout.zbase_xlistview_footer;
    }

    @Override
    protected int findProgressBarId() {
        return R.id.xlistview_footer_progressbar;
    }

    @Override
    protected int findTextViewId() {
        return R.id.xlistview_footer_hint_textview;
    }

    @Override
    protected int findLoadingStringId() {
        return R.string.pull_to_refresh_footer_loading;
    }

    @Override
    protected int findShowAllStringId() {
        return R.string.pull_to_refresh_footer_show_all;
    }
}
