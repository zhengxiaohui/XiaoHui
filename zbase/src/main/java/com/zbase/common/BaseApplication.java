package com.zbase.common;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;

import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.cache.CacheMode;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.zbase.manager.ActivityStackManager;

/**
 * 创建人：郑晓辉
 * 创建日期：2016/4/27
 * 描述：Application全局配置的父类，给子类MyApplication继承使用
 */
public abstract class BaseApplication extends Application {

    protected ActivityStackManager activityStack;

    public ActivityStackManager getActivityStack() {
        return activityStack;
    }

    public static boolean debugMode = true;//是否是debug模式，默认true。控制打印日志，极光推送模式等。打包APK的时候要设置成false
    private RefWatcher refWatcher;

    public static RefWatcher getRefWatcher(Context context) {
        BaseApplication application = (BaseApplication) context.getApplicationContext();
        return application.refWatcher;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        refWatcher = LeakCanary.install(this);
//        refWatcher = RefWatcher.DISABLED;//正式发布的时候要把上面的那句换成这句，使LeakCanary失效
        CrashHandler.getInstance().init(this);
        activityStack = ActivityStackManager.getInstance();
        activityStack.setOnAppDisplayListener(new ActivityStackManager.OnAppDisplayListener() {
            @Override
            public void onFront() {
                onAppFront();
            }

            @Override
            public void onBack() {
                onAppBack();
            }
        });

        initImageLoader();

        initOkHttp();
    }

    private void initImageLoader() {
        DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder()
                .cloneFrom(DisplayImageOptions.createSimple())//先获取默认的设置,默认是不缓存的
                .bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .cacheOnDisk(true)//允许磁盘缓存
                .displayer(new SimpleBitmapDisplayer()).build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .threadPriority(Thread.NORM_PRIORITY - 2)// 设置线程的优先级
                .tasksProcessingOrder(QueueProcessingType.LIFO)// 设置图片下载和显示的工作队列排序
                .denyCacheImageMultipleSizesInMemory()// 当同一个Uri获取不同大小的图片，缓存到内存时，只缓存一个。默认会缓存多个不同的大小的相同图片
                .memoryCache(new LruMemoryCache(6 * 1024 * 1024))//如果设置了这个弱缓存，就不要设置memoryCacheSize，memoryCacheSizePercentage
//                .memoryCacheSize(6 * 1024 * 1024)//设置缓存的大小
//                .memoryCacheSizePercentage(13) // 设置缓存空间占应用所需缓存的百分比
                //如果配置了缓存路径和缓存方式的，就不要配置diskCacheFileNameGenerator，diskCacheSize，diskCacheFileCount
//                .diskCache(new UnlimitedDiskCache(StorageUtils.getOwnCacheDirectory(getApplicationContext(), SystemConst.IMAGE_CACHE)))
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())// 设置缓存文件的名字，如果指定了上述的缓存路径和缓存方式的，就不要配置这个信息
                .diskCacheSize(50 * 1024 * 1024)//设置缓存文件的大小，如果指定了上述的缓存路径和缓存方式的，就不要配置这个信息
                .diskCacheFileCount(100)// 缓存文件的最大个数，如果指定了上述的缓存路径和缓存方式的，就不要配置这个信息
                .defaultDisplayImageOptions(displayImageOptions)
                .writeDebugLogs()
                .build();
        ImageLoader.getInstance().init(config);
    }

    private void initOkHttp() {
//        HttpHeaders headers = new HttpHeaders();
//        headers.put("commonHeaderKey1", "commonHeaderValue1");    //所有的 header 都 不支持 中文
//        headers.put("commonHeaderKey2", "commonHeaderValue2");
//        HttpParams params = new HttpParams();
//        params.put("commonParamsKey1", "commonParamsValue1");     //所有的 params 都 支持 中文
//        params.put("commonParamsKey2", "这里支持中文参数");

        //必须调用初始化
        OkHttpUtils.init(this);
        //以下都不是必须的，根据需要自行选择
        OkHttpUtils okHttpUtils = OkHttpUtils.getInstance()//
//                .debug("OkHttpUtils")                                              //是否打开调试
                .setCacheMode(CacheMode.NO_CACHE)                                  //不开启缓存
                .setConnectTimeout(OkHttpUtils.DEFAULT_MILLISECONDS)               //全局的连接超时时间
                .setReadTimeOut(OkHttpUtils.DEFAULT_MILLISECONDS)                  //全局的读取超时时间
                .setWriteTimeOut(OkHttpUtils.DEFAULT_MILLISECONDS);                 //全局的写入超时时间
        //.setCookieStore(new MemoryCookieStore())                           //cookie使用内存缓存（app退出后，cookie消失）
        //.setCookieStore(new PersistentCookieStore())                       //cookie持久化存储，如果cookie不过期，则一直有效
//                .addCommonHeaders(headers)                                         //设置全局公共头
//                .addCommonParams(params);                                          //设置全局公共参数
        if (debugMode) {
            okHttpUtils.debug("OkHttpUtils");
        }
    }

    /**
     * app运行在前台，子类覆盖实现
     */
    protected void onAppFront() {

    }

    /**
     * app运行在后台，子类覆盖实现
     */
    protected void onAppBack() {

    }

    /**
     * 获取登录activity的class
     *
     * @return
     */
    public abstract Class getLoginClass();

    /**
     * 获取统一跳转网页的class
     *
     * @return
     */
    public abstract Class getWebViewClass();

    /**
     * 判断是否已登录
     *
     * @return
     */
    public abstract boolean isLoggedIn();

    /**
     * 清空Application中保存的用户数据
     */
    public abstract void clearUser();

}
