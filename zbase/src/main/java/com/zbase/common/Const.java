package com.zbase.common;

import android.graphics.Bitmap;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.zbase.R;

/**
 * 用来标记static的全局变量或常量
 *
 * @author Administrator
 */
public class Const {
    public static String LOGIN_DO_CODE = "login_do_code";
    public static final int LOGIN_REQUEST_CODE = 10000;//登录的请求码
    public static final String URI_PRE = "file://";//图片地址uri的前缀
    public static final String FIRST_LAUNCH = "first_launch"; //是否第一次启动APPprotected
    public static final String USER = "user";//用户信息

    /**
     * ImageLoader常用默认配置属性，没有内存缓存和磁盘缓存，没有加载失败图和默认图
     */
    public static final DisplayImageOptions DEFAULT_IMAGE_OPTIONS = new DisplayImageOptions.Builder()
            .cloneFrom(DisplayImageOptions.createSimple())
            .bitmapConfig(Bitmap.Config.RGB_565)//设置图片的解码类型//
            .imageScaleType(ImageScaleType.IN_SAMPLE_INT)// 是设置 图片的缩放方式, EXACTLY :图像将完全按比例缩小的目标大小
            //EXACTLY_STRETCHED:图片会缩放到目标大小完全,,IN_SAMPLE_INT:图像将被二次采样的整数倍,,  IN_SAMPLE_POWER_OF_2:图片将降低2倍，直到下一减少步骤，使图像更小的目标大小
            .cacheOnDisk(true)//设置下载的图片是否缓存在SD卡中
            .considerExifParams(true)//是否考虑JPEG图像EXIF参数（旋转，翻转）
            .showImageOnLoading(R.mipmap.zbase_circle)
            .displayer(new SimpleBitmapDisplayer()).build();
}
