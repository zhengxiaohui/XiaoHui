package com.zbase.strategy;

import android.content.Context;

import com.zbase.R;
import com.zbase.util.PopUtil;

/**
 * Created by Administrator on 2015/2/10.
 */
public class PopThree extends BasePop {

    /**
     * 自定义单张图片旋转对话框，中间橙色图片，背景灰色
     * @param context
     */
    @Override
    public void showProgressDialog(Context context) {
        PopUtil.showLoadingDialog(context, context.getString(R.string.please_wait));
    }
}
