package com.oranllc.umengsocialshare.common;

import android.content.Context;
import android.widget.Toast;

import com.oranllc.umengsocialshare.R;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

/**
 * 创建人：郑晓辉
 * 创建日期：2016/9/20
 * 描述：
 */
public class MyShareListener implements UMShareListener {

    private Context context;

    public MyShareListener(Context context) {
        this.context = context;
    }

    @Override
    public void onResult(SHARE_MEDIA platform) {
        switch (platform) {
            case WEIXIN:
                Toast.makeText(context, context.getString(R.string.share_success, "微信"), Toast.LENGTH_SHORT).show();
                break;
            case WEIXIN_CIRCLE:
                Toast.makeText(context, context.getString(R.string.share_success, "微信朋友圈"), Toast.LENGTH_SHORT).show();
                break;
            case QQ:
                Toast.makeText(context, context.getString(R.string.share_success, "QQ"), Toast.LENGTH_SHORT).show();
                break;
            case SINA:
                Toast.makeText(context, context.getString(R.string.share_success, "新浪微博"), Toast.LENGTH_SHORT).show();
                break;
            case QZONE:
                Toast.makeText(context, context.getString(R.string.share_success, "QQ空间"), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onError(SHARE_MEDIA platform, Throwable t) {
        switch (platform) {
            case WEIXIN:
                Toast.makeText(context, context.getString(R.string.share_fail, "微信"), Toast.LENGTH_SHORT).show();
                break;
            case WEIXIN_CIRCLE:
                Toast.makeText(context, context.getString(R.string.share_fail, "微信朋友圈"), Toast.LENGTH_SHORT).show();
                break;
            case QQ:
                Toast.makeText(context, context.getString(R.string.share_fail, "QQ"), Toast.LENGTH_SHORT).show();
                break;
            case SINA:
                Toast.makeText(context, context.getString(R.string.share_fail, "新浪微博"), Toast.LENGTH_SHORT).show();
                break;
            case QZONE:
                Toast.makeText(context, context.getString(R.string.share_fail, "QQ空间"), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onCancel(SHARE_MEDIA platform) {
        switch (platform) {
            case WEIXIN:
                Toast.makeText(context, context.getString(R.string.share_cancel, "微信"), Toast.LENGTH_SHORT).show();
                break;
            case WEIXIN_CIRCLE:
                Toast.makeText(context, context.getString(R.string.share_cancel, "微信朋友圈"), Toast.LENGTH_SHORT).show();
                break;
            case QQ:
                Toast.makeText(context, context.getString(R.string.share_cancel, "QQ"), Toast.LENGTH_SHORT).show();
                break;
            case SINA:
                Toast.makeText(context, context.getString(R.string.share_cancel, "新浪微博"), Toast.LENGTH_SHORT).show();
                break;
            case QZONE:
                Toast.makeText(context, context.getString(R.string.share_cancel, "QQ空间"), Toast.LENGTH_SHORT).show();
                break;
        }
    }

}
