package com.zdemo.activity;

import android.util.Log;
import android.view.View;

import com.zbase.listener.OnScrollListener;
import com.zbase.view.ObservableHorizontalScrollView;
import com.zdemo.R;

/**
 * 创建人：郑晓辉
 * 创建日期：2016/6/7
 * 描述：
 */
public class ObservableHorizontalScrollViewActivity extends BaseActivity {

    private ObservableHorizontalScrollView observableHorizontalScrollView;

    @Override
    protected int inflateMainLayoutId() {
        return R.layout.activity_observable_horizontal_scrollview;
    }

    @Override
    protected void initView(View view) {
        observableHorizontalScrollView = (ObservableHorizontalScrollView) findViewById(R.id.observableHorizontalScrollView);
    }

    @Override
    protected void setListener() {
        observableHorizontalScrollView.setOnScrollListener(new OnScrollListener() {
            @Override
            public void onBegin() {
                Log.d("zheng","开始");
            }

            @Override
            public void onEnd() {
                Log.d("zheng","结束");
            }

            @Override
            public void onScroll() {
                Log.d("zheng","滚动");
            }
        });
    }

    @Override
    protected void initValue() {

    }

    @Override
    public void onClick(View v) {

    }
}
