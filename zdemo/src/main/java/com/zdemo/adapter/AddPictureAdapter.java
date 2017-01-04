package com.zdemo.adapter;

import android.content.Context;

import com.zbase.adapter.BaseAddPictureAdapter;
import com.zdemo.R;

/**
 * 创建人：CXQ
 * 创建日期：2016/6/28
 * 描述：发布居民说说的添加图片
 */
public class AddPictureAdapter extends BaseAddPictureAdapter {

    public AddPictureAdapter(Context context) {
        super(context);
    }

    @Override
    protected int inflateMainLayoutId() {
        return R.layout.adapter_add_picture;
    }

    @Override
    public int findPictureImageViewId() {
        return R.id.iv_picture;
    }

    @Override
    public int findDeleteImageViewId() {
        return R.id.iv_delete;
    }

    @Override
    public int findAddImageViewId() {
        return R.id.iv_add;
    }
}
