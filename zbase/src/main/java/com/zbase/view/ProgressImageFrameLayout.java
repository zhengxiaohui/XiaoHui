package com.zbase.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

/**
 * 创建人：郑晓辉
 * 创建日期：2017/1/3
 * 描述：带加载动画的图片控件（转圈圈）（未完待续，根据实际使用情况再改进）
 */
public class ProgressImageFrameLayout extends FrameLayout {

    private ImageView imageView;
    private ProgressBar progressBar;

    public ProgressImageFrameLayout(Context context) {
        super(context);
        init(null, 0);
    }

    public ProgressImageFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public ProgressImageFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyle) {
//        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CustomEditText, defStyle, 0);
//        minLength = a.getInt(R.styleable.CustomEditText_minLength, 0);
//        a.recycle();
        loadViews();
    }

    private void loadViews() {
        imageView = new ImageView(getContext());
        FrameLayout.LayoutParams imageLayoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        imageLayoutParams.gravity = Gravity.CENTER;
        imageView.setLayoutParams(imageLayoutParams);

        progressBar = new ProgressBar(getContext());
        FrameLayout.LayoutParams progressLayoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        progressLayoutParams.gravity = Gravity.CENTER;
        progressBar.setLayoutParams(progressLayoutParams);
        progressBar.setIndeterminate(true);
        addView(imageView);
        addView(progressBar);

        ImageLoader.getInstance().displayImage("https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png", imageView, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}
