package com.zdemo.activity;

import android.view.View;

import com.zbase.view.AutoCycleSlideView;
import com.zdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建人：郑晓辉
 * 创建日期：2016/6/13
 * 描述：
 */
public class AutoCycleSlideViewActivity extends BaseActivity{

    private AutoCycleSlideView autoCycleSlideView;

    @Override
    protected int inflateMainLayoutId() {
        return R.layout.activity_autocycleslideview;
    }

    @Override
    protected void initView(View view) {
        autoCycleSlideView=(AutoCycleSlideView)view.findViewById(R.id.autoCycleSlideView);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initValue() {
        List<View> viewList=new ArrayList<>();
        for (int i = 0; i <5 ; i++) {
            View view=inflate(R.layout.inflate_autocycleslideview);
            viewList.add(view);
        }
        autoCycleSlideView.loadViews(viewList);
        autoCycleSlideView.startAutoFlow();
    }

    @Override
    public void onClick(View v) {

    }
}
