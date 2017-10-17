package com.nostra13.universalimageloader.utils;

import android.content.Context;
import android.graphics.Bitmap;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

/**
 * 创建人：郑晓辉
 * 创建日期：2017/10/17 0017
 * 描述：
 */
public class ImageLoaderUtil {
    public static DisplayImageOptions getDisplayImageOptions() {
        return getDisplayImageOptions(0, 0, null);
    }

    public static DisplayImageOptions getDisplayImageOptions(int defaultImageRes) {
        return getDisplayImageOptions(defaultImageRes, 0, null);
    }

    public static DisplayImageOptions getDisplayImageOptions(int defaultImageRes, float diameterDP, Context context) {
        DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder().bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.IN_SAMPLE_INT);
        if (defaultImageRes != 0) {
            builder.showImageOnLoading(defaultImageRes).showImageForEmptyUri(defaultImageRes).showImageOnFail(defaultImageRes);
        }
        if (context != null && diameterDP != 0) {
            builder.displayer(new RoundedBitmapDisplayer(dip2px(context, diameterDP)));
        }
        return builder.build();
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    private static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
