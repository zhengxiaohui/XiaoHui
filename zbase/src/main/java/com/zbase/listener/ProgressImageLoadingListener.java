package com.zbase.listener;

import android.graphics.Bitmap;
import android.view.View;

import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

/**
 * 创建人：郑晓辉
 * 创建日期：2016/2/25
 * 描述：带转圈圈的图片加载监听
 */
public class ProgressImageLoadingListener implements ImageLoadingListener {

    private View progressView;//转圈圈View，可以是系统的ProgressBar或者第三方或自定义的ProgressWheel等都可以

    public ProgressImageLoadingListener(View progressView){
        this.progressView=progressView;
    }

    @Override
    public void onLoadingStarted(String imageUri, View view) {
        progressView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
        progressView.setVisibility(View.GONE);
    }

    @Override
    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
        progressView.setVisibility(View.GONE);
    }

    @Override
    public void onLoadingCancelled(String imageUri, View view) {
        progressView.setVisibility(View.GONE);
    }
}
