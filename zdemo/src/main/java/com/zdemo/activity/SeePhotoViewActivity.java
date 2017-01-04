package com.zdemo.activity;

import android.view.View;
import android.widget.ImageView;

import com.zdemo.R;

/**
 * 创建人：郑晓辉
 * 创建日期：2016/11/7
 * 描述：
 */
public class SeePhotoViewActivity extends BaseActivity{

    private ImageView imageView;

    @Override
    protected int inflateMainLayoutId() {
        return R.layout.activity_see_photoview;
    }

    @Override
    protected void initView(View view) {
        imageView=(ImageView)view.findViewById(R.id.imageView);
    }

    @Override
    protected void setListener() {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpToPhotoViewActivity(imageView);
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
