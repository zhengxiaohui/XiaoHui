package com.oranllc.template.adapter;

import android.content.Context;

import com.oranllc.template.R;
import com.zbase.adapter.BaseAddPictureAdapter;

/**
 * 创建人：CXQ
 * 创建日期：2016/11/8
 * 描述：
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
        return R.id.imageView;
    }

    @Override
    public int findDeleteImageViewId() {
        return R.id.iv_close;
    }

    @Override
    public int findAddImageViewId() {
        return R.id.iv_add;
    }
}
