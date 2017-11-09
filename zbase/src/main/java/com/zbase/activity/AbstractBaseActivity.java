package com.zbase.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.githang.statusbar.StatusBarCompat;
import com.lzy.okhttputils.OkHttpUtils;
import com.umeng.analytics.MobclickAgent;
import com.zbase.R;
import com.zbase.common.BaseApplication;
import com.zbase.common.Const;
import com.zbase.common.GlobalBroadcastReceiver;
import com.zbase.common.ZSharedPreferences;
import com.zbase.imagedispose.PhotoPicker;
import com.zbase.interfaces.IGlobalReceiver;
import com.zbase.manager.ActivityStackManager;
import com.zbase.util.ImageUtil;
import com.zbase.util.PopUtil;
import com.zbase.util.ScreenUtil;

import java.util.ArrayList;


/**
 * 公共基础父类activity，包含了HandleMessage消息队列处理，initView()，setListener()，initView()等抽象方法
 * 所有项目通用的功能放这里面，针对单个项目的功能放子类BaseActivity里。
 */
public abstract class AbstractBaseActivity extends AppCompatActivity implements View.OnClickListener, IGlobalReceiver {

    protected Context context;
    private GlobalBroadcastReceiver globalBroadcastReceiver;
    private long exitTime = 0;
    private int frameLayoutResId;//装载替换Fragment的FrameLayout布局Id
    private Fragment[] fragments;//多个Fragment，用于隐藏显示切换
    private int currentTab;//MyTabWidget登录之前的索引
    protected PhotoPicker photoPicker;//拍照和图库选择器

    public int getCurrentTab() {
        return currentTab;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(inflateBaseLayoutId());
        init();
    }

    protected void init() {
        context = this;
        getMyApplication().getActivityStack().addActivity(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//全部竖屏，防止横竖屏切换
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);//不自动弹出软键盘
        registerGlobleReceiver();//注册全局广播
        initBaseView(getWindow().getDecorView());//初始化头部公共布局
        initView(getWindow().getDecorView());//查找控件
        setListener();//设置监听
        initValue();//初始化数据
    }

    protected abstract int inflateBaseLayoutId();

    protected abstract int inflateMainLayoutId();

    protected abstract void initBaseView(View view);

    protected abstract void initView(View view);

    protected abstract void setListener();

    protected abstract void initValue();

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(getClass().getSimpleName());//友盟统计
        MobclickAgent.onResume(this);//友盟统计
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(getClass().getSimpleName()); //友盟统计//保证 onPageEnd 在onPause 之前调用,因为 onPause 中会保存信息
        MobclickAgent.onPause(this);//友盟统计
    }

    protected void initPhotoPicker() {
        initPhotoPicker(true);
    }

    protected void initPhotoPicker(final boolean isCompress) {
        photoPicker = new PhotoPicker(this, new PhotoPicker.OnPhotoPickedListener() {
            @Override
            public void onPhotoPicked(String imagePath) {
                if (isCompress) {
                    onPhotoPickerReturn(ImageUtil.compressBitmap(context,imagePath));
                } else {
                    onPhotoPickerReturn(imagePath);
                }
            }
        });
    }

    protected View inflate(@LayoutRes int resource) {
        return LayoutInflater.from(context).inflate(resource, null);
    }

    /**
     * 设置状态栏的颜色，xml布局中必须有
     * android:fitsSystemWindows="true"
     * android:clipToPadding="true"
     * 这样两个属性才行，不然布局会跑到状态栏里。
     * 如果是整个app都改变状态栏颜色，那就在BaseActivity中调用，
     * 在activity_base公共布局中设置就可以了。
     *
     * @param id
     */
    protected void setStatusBarColor(@ColorRes int id) {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        ViewGroup decorViewGroup = (ViewGroup) window.getDecorView();
        View statusBarView = new View(window.getContext());
        int statusBarHeight = ScreenUtil.getStatusBarHeight(window.getContext());
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, statusBarHeight);
        params.gravity = Gravity.TOP;
        statusBarView.setLayoutParams(params);
        statusBarView.setBackgroundColor(getResources().getColor(id));
        decorViewGroup.addView(statusBarView);
    }

    /**
     * 调用第三方库
     * @param id
     */
    protected void setStatusBarColorCompat(@ColorRes int id) {
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(id));
    }

    /**
     * 隐藏状态栏，注意，可能会导致布局错位
     */
    protected void hideStatusBar() {
        WindowManager.LayoutParams attrs = getWindow().getAttributes();
        attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setAttributes(attrs);
    }

    protected void showStatusBar() {
        WindowManager.LayoutParams attrs = getWindow().getAttributes();
        attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setAttributes(attrs);
    }

    /**
     * 点击输入框之外的地方隐藏软键盘
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    private boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    public void jumpToLogin() {
        startActivity(new Intent(context, getMyApplication().getLoginClass()));
    }

    /**
     * 跳转到BaseWebViewActivity的子类
     *
     * @param url 网页地址
     */
    public void jumpToWebViewActivity(String url) {
        jumpToWebViewActivity(url, null);
    }

    /**
     * 跳转到BaseWebViewActivity的子类
     *
     * @param url    网页地址
     * @param bundle 可以传任意数据类型
     */
    public void jumpToWebViewActivity(String url, Bundle bundle) {
        if (getMyApplication().getWebViewClass() != null) {
            Intent intent = new Intent(this, getMyApplication().getWebViewClass());
            intent.putExtra(BaseWebViewActivity.URL, url);
            if (bundle != null) {
                intent.putExtras(bundle);
            }
            startActivity(intent);
        }
    }

    /**
     * 跳转PhotoViewActivity，加载多张网络或本地图片
     *
     * @param imagePath         图片地址集合,网络和本地的都可以
     * @param defaultDrawableId 默认显示的本地图片id
     */
    public void jumpToPhotoViewActivity(String imagePath, int defaultDrawableId) {
        ArrayList<String> list = new ArrayList<>();
        list.add(imagePath);
        jumpToPhotoViewActivity(list, defaultDrawableId, 0);
    }

    /**
     * 跳转PhotoViewActivity，加载多张网络或本地图片
     *
     * @param imagePathList     图片地址集合,网络和本地的都可以
     * @param defaultDrawableId 默认显示的本地图片id
     * @param currentPosition   默认显示第几张图
     */
    public void jumpToPhotoViewActivity(ArrayList<String> imagePathList, int defaultDrawableId, int currentPosition) {
        Intent intent = new Intent(this, PhotoViewActivity.class);
        intent.putStringArrayListExtra(PhotoViewActivity.IMAGE_PATH_LIST, imagePathList);
        intent.putExtra(PhotoViewActivity.DEFAULT_DRAWABLE_ID, defaultDrawableId);
        intent.putExtra(PhotoViewActivity.CURRENT_POSITION, currentPosition);
        startActivity(intent);
    }

    /**
     * 跳转到PhotoViewActivity，加载单张本地资源图片
     *
     * @param anchorImageView
     */
    public void jumpToPhotoViewActivity(ImageView anchorImageView) {
        Intent intent = new Intent(this, PhotoViewActivity.class);
        intent.putExtra(PhotoViewActivity.BITMAP, ImageUtil.drawableToBitmap(anchorImageView.getDrawable()));
        startActivity(intent);
    }

    /**
     * 初始化多个Fragment，用于隐藏显示切换,并add第一个Fragment到Activity
     *
     * @param frameLayoutResId 装载替换Fragment的FrameLayout布局Id
     * @param fragments        主页等页面多个Fragment切换的Fragment数组
     */
    protected void initMultiFragment(int frameLayoutResId, Fragment[] fragments) {
        this.frameLayoutResId = frameLayoutResId;
        this.fragments = fragments;
        getFragmentManager().beginTransaction().add(frameLayoutResId, fragments[0]).commitAllowingStateLoss();
    }

    /**
     * 多个Fragment切换显示的索引
     *
     * @param position
     */
    public void switchFragment(int position) {
        if (fragments == null) {
            return;
        }
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        for (int i = 0; i < fragments.length; i++) {
            if (i != position && fragments[i] != null && fragments[i].isAdded()) {
                transaction.hide(fragments[i]);
            }
        }
        if (!fragments[position].isAdded()) {
            transaction.add(frameLayoutResId, fragments[position]).commitAllowingStateLoss();//用commit有时候会报错
        } else {
            transaction.show(fragments[position]).commitAllowingStateLoss();
        }
        currentTab = position;
    }

    public abstract BaseApplication getMyApplication();

    protected ActivityStackManager getActivityStackManager() {
        return getMyApplication().getActivityStack();
    }

    public ZSharedPreferences getZSharedPreferences() {
        return ZSharedPreferences.getInstance(this);
    }

    /**
     * 注册应用内广播接收器
     */
    public void registerGlobleReceiver() {
        globalBroadcastReceiver = new GlobalBroadcastReceiver(this);
        registerReceiver(globalBroadcastReceiver, globalBroadcastReceiver.getIntentFilter());
    }

    /**
     * 取消注册应用内广播接收器
     */
    public void unRegisterGlobleReceiver() {
        unregisterReceiver(globalBroadcastReceiver);
    }

    /**
     * 快速点击两次退出程序
     */
    protected void exitApp() {
        // 判断2次点击事件时间间隔
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            PopUtil.toast(this, R.string.exit_app_toast);
            exitTime = System.currentTimeMillis();
        } else {
            getMyApplication().getActivityStack().exitApp();
        }
    }

    /**
     * 程序退到桌面，模拟Home键事件
     */
    protected void fallBackHome() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);// 注意
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        int count = getMyApplication().getActivityStack().getActivityStartedCount();
        if (count == 0 && getMyApplication().getActivityStack().getOnAppDisplayListener() != null) {
            getMyApplication().getActivityStack().getOnAppDisplayListener().onFront();
        }
        count++;
        getMyApplication().getActivityStack().setActivityStartedCount(count);
    }

    @Override
    protected void onStop() {
        super.onStop();
        int count = getMyApplication().getActivityStack().getActivityStartedCount();
        if (count == 0 && getMyApplication().getActivityStack().getOnAppDisplayListener() != null) {
            getMyApplication().getActivityStack().getOnAppDisplayListener().onBack();
        }
        count--;
        getMyApplication().getActivityStack().setActivityStartedCount(count);
    }

    /**
     * 简单判断登录或跳转目标Activity
     * 在登录情况下跳转目标Activity，如果未登录，则先登录(登录后没有继续执行之前的操作，这是和doWithLogin的区别)
     */
    protected void doOrLogin(Intent intent) {
        if (getMyApplication().isLoggedIn()) {
            startActivity(intent);
        } else {
            startActivity(new Intent(context, getMyApplication().getLoginClass()));
        }
    }

    /**
     * 在登录情况下执行操作，如果未登录，则先登录再执行操作
     *
     * @param loginDoCode 登录操作码
     */
    protected void doWithLogin(String loginDoCode) {
        Const.LOGIN_DO_CODE = loginDoCode;
        if (getMyApplication().isLoggedIn()) {
            afterLogin(loginDoCode);
        } else {
            startActivityForResult(new Intent(context, getMyApplication().getLoginClass()), Const.LOGIN_REQUEST_CODE);
        }
    }

    /**
     * 登录后执行的回调方法，留给子类覆盖实现
     *
     * @param loginDoCode 登录操作码
     */
    protected void afterLogin(String loginDoCode) {

    }

    /**
     * 取消登录
     *
     * @param loginDoCode
     */
    protected void cancelLogin(String loginDoCode) {

    }

    /**
     * 登出，注销
     */
    public void logout(){
        getMyApplication().clearUser();
        getZSharedPreferences().putJsonBean(Const.USER, null);
        sendLogoutBroadcast();
        PopUtil.toast(context, R.string.logout_success);
        jumpToLogin();
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == Const.LOGIN_REQUEST_CODE) {
            afterLogin(Const.LOGIN_DO_CODE);
        }
        if (resultCode == Activity.RESULT_CANCELED && requestCode == Const.LOGIN_REQUEST_CODE) {
            cancelLogin(Const.LOGIN_DO_CODE);
        }
        if (photoPicker != null) {
            photoPicker.onActivityResult(requestCode, resultCode, data);
        }
    }

    /**
     * 获取到PhotoPicker返回的图片
     *
     * @param imagePath
     */
    protected void onPhotoPickerReturn(String imagePath) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getMyApplication().getActivityStack().removeActivity(this);
        unRegisterGlobleReceiver();
        OkHttpUtils.getInstance().cancelTag(this);//Activity销毁时，取消网络请求
        if (photoPicker != null) {
            photoPicker.onDestroy();
        }
    }

    /**
     * 发送通用广播
     */
    public void sendCommonBroadcast(String code) {
        globalBroadcastReceiver.broadcastIdentifyCode = code;
        sendBroadcast(new Intent(GlobalBroadcastReceiver.ACTION_COMMON));
    }

    /**
     * 发送通用广播带参数
     */
    public void sendCommonBroadcast(String code, Bundle bundle) {
        globalBroadcastReceiver.broadcastIdentifyCode = code;
        Intent intent = new Intent(GlobalBroadcastReceiver.ACTION_COMMON);
        intent.putExtras(bundle);
        sendBroadcast(intent);
    }

    /**
     * 接收到通用广播
     * @param code
     * @param bundle
     */
    public void onCommonBroadcastReceive(String code, Bundle bundle) {

    }

    /**
     * 发送登录广播
     */
    public void sendLoginBroadcast() {
        sendBroadcast(new Intent(GlobalBroadcastReceiver.ACTION_LOGIN));
    }

    /**
     * 发送注销广播
     */
    public void sendLogoutBroadcast() {
        sendBroadcast(new Intent(GlobalBroadcastReceiver.ACTION_LOGOUT));
    }

    /**
     * 接收到登录成功的广播
     * @param intent
     */
    public void onLogin(Intent intent) {

    }

    /**
     * 接收到注销成功的广播
     * @param intent
     */
    public void onLogout(Intent intent) {

    }

    /**
     * 接收到透传数据的广播
     */
    public void onMessageReceived(String title, String message, String extras, String contentType) {

    }

    /**
     * 接收到通知数据的广播
     */
    public void onNotificationReceived(String title, String content, String extras, String contentType) {

    }

    /**
     * 接收到打开通知的广播
     */
    public void onNotificationOpened(String title, String content, String extras) {

    }

}
