package com.zbase.strategy;

import android.content.Context;

import com.zbase.util.PopUtil;

/**
 * Created by Administrator on 2015/2/10.
 */
public class PopTwo extends BasePop {

    /**
     * 自定义单张图片选择对话框，灰色背景，中间小图片
     * @param context
     */
    @Override
    public void showProgressDialog(Context context) {
        PopUtil.showMyProgressDialog(context, "");
    }
}
