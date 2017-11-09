package com.zbase.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;

import com.lzy.okhttputils.OkHttpUtils;
import com.umeng.analytics.MobclickAgent;
import com.zbase.activity.AbstractBaseActivity;
import com.zbase.common.BaseApplication;
import com.zbase.common.Const;
import com.zbase.common.GlobalBroadcastReceiver;
import com.zbase.imagedispose.PhotoPicker;
import com.zbase.interfaces.IGlobalReceiver;
import com.zbase.util.ImageUtil;


/**
 * 公共基础父类Fragment，initView(View view)，setListener()，initValue()
 * 抽象方法
 *
 * @author z
 */
public abstract class AbstractBaseFragment extends Fragment implements View.OnClickListener, IGlobalReceiver {

    protected Context context;
    protected AbstractBaseActivity abstractBaseActivity;
    private GlobalBroadcastReceiver globalBroadcastReceiver;
    protected PhotoPicker photoPicker;//拍照和图库选择器

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    protected void init(View view) {
        context = getActivity();
        abstractBaseActivity = (AbstractBaseActivity) getActivity();
        registerGlobleReceiver();
        initBaseView(view);//初始化头部公共布局
        initView(view);//查找控件
        setListener();//设置监听
        initValue();//初始化数据
    }

    protected abstract int inflateBaseLayoutId();

    protected abstract int inflateMainLayoutId();

    protected abstract void initBaseView(View view);

    protected abstract void initView(View view);

    protected abstract void setListener();

    protected abstract void initValue();

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(getClass().getSimpleName()); //友盟统计
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(getClass().getSimpleName());//友盟统计
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

    public abstract BaseApplication getMyApplication();

    /**
     * 注册应用内广播接收器
     */
    public void registerGlobleReceiver() {
        globalBroadcastReceiver = new GlobalBroadcastReceiver(this);
        getActivity().registerReceiver(globalBroadcastReceiver, globalBroadcastReceiver.getIntentFilter());
    }

    public void unRegisterGlobleReceiver() {
        getActivity().unregisterReceiver(globalBroadcastReceiver);
    }

    /**
     * 简单判断登录或执行
     * 在登录情况下执行操作，如果未登录，则先登录(登录后没有继续执行之前的操作，这是和doWithLogin的区别)
     */
    protected void doOrLogin(Intent intent) {
        if (getMyApplication().isLoggedIn()) {
            startActivity(intent);
        } else {
            startActivity(new Intent(context, abstractBaseActivity.getMyApplication().getLoginClass()));
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
            startActivityForResult(new Intent(context, abstractBaseActivity.getMyApplication().getLoginClass()), Const.LOGIN_REQUEST_CODE);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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
    public void onDestroy() {
        super.onDestroy();
        unRegisterGlobleReceiver();
        //Activity销毁时，取消网络请求
        OkHttpUtils.getInstance().cancelTag(this);
        if (photoPicker != null) {
            photoPicker.onDestroy();
        }
    }

    /**
     * 发送通用广播
     */
    public void sendCommonBroadcast(String code) {
        abstractBaseActivity.sendCommonBroadcast(code);
    }

    /**
     * 发送通用广播带参数
     */
    public void sendCommonBroadcast(String code, Bundle bundle) {
        abstractBaseActivity.sendCommonBroadcast(code, bundle);
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
        abstractBaseActivity.sendLoginBroadcast();
    }

    /**
     * 发送注销广播
     */
    public void sendLogoutBroadcast() {
        abstractBaseActivity.sendLogoutBroadcast();
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
