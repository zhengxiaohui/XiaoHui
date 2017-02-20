package com.oranllc.template.activity;

import android.view.View;

import com.oranllc.template.R;
import com.oranllc.template.global.MyApplication;
import com.zbase.activity.BaseLaunchActivity;

/**
 * 创建人：郑晓辉
 * 创建日期：2017/2/17
 * 描述：启动页
 */
public class LaunchActivity extends BaseLaunchActivity {

    @Override
    protected Class getGuideActivityClass() {
        return GuideActivity.class;
    }

    @Override
    protected Class getHomeActivityClass() {
        return HomeActivity.class;
    }

    @Override
    protected boolean isTest() {
        return true;
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initValue() {
//        requestLaunchImage();
        iv_launch.setImageResource(R.mipmap.list_item_default);
        startHandler(3000);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public MyApplication getMyApplication() {
        return (MyApplication) getApplication();
    }

//    /**
//     * 请求启动图片
//     */
//    private void requestLaunchImage() {
//        OkHttpUtils.get(HttpConstant.START_PAGE)
//                .tag(this)
//                .execute(new SignJsonCallback<CommonStringBean>(context, CommonStringBean.class, false) {
//                    @Override
//                    public void onResponse(boolean isFromCache, CommonStringBean responseBean, Request request, @Nullable
//                            Response response) {
//                        if (responseBean.getCodeState() == 1) {
//                            ImageLoader.getInstance().displayImage(responseBean.getData(), iv_launch);
//                            requestGuideImages();
//                        } else {
//                            PopUtil.toast(context, responseBean.getMessage());
//                        }
//                    }
//                });
//    }
//
//    /**
//     * 请求引导页图片
//     */
//    private void requestGuideImages() {
//        OkHttpUtils.get(HttpConstant.GUIDE_PAGE)
//                .tag(this)
//                .execute(new SignJsonCallback<CommonListStringBean>(context, CommonListStringBean.class, false) {
//                    @Override
//                    public void onResponse(boolean isFromCache, CommonListStringBean responseBean, Request request, @Nullable Response response) {
//                        if (responseBean.getCodeState() == 1) {
//                            imageUrlList = (ArrayList<String>) responseBean.getData();
//                            startHandler(500);
//                        } else {
//                            PopUtil.toast(context, responseBean.getMessage());
//                        }
//                    }
//                });
//    }

}
