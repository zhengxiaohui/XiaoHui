package com.oranllc.template.activity;

import android.app.Fragment;
import android.view.View;

import com.oranllc.template.R;
import com.oranllc.template.fragment.HomeFragment1;
import com.oranllc.template.fragment.HomeFragment2;
import com.oranllc.template.fragment.HomeFragment3;
import com.zbase.common.ZLog;
import com.zbase.view.MyTabWidget;

/**
 * 创建人：郑晓辉
 * 创建日期：2017/2/17
 * 描述：主页
 */
public class HomeActivity extends BaseActivity {

    private MyTabWidget myTabWidget;
    private HomeFragment1 homeFragment1;
    private HomeFragment2 homeFragment2;
    private HomeFragment3 homeFragment3;

    @Override
    protected int inflateMainLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void initView(View view) {
        myTabWidget = (MyTabWidget) view.findViewById(R.id.myTabWidget);
        myTabWidget.setCurrentTab(0);
    }

    @Override
    protected void setListener() {
        myTabWidget.setSelectionChangedListener(new MyTabWidget.OnMyTabSelectionChanged() {
            @Override
            public void onTabSelectionChanged(int tabIndex, boolean clicked) {
                switchFragment(tabIndex);
            }
        });
    }

//    @Override
//    protected void afterLogin(String loginDoCode) {
//        switch (loginDoCode) {
//            case LoginCodeConstant.MINE:
//                    switchFragment(2);
//                break;
//        }
//    }

    @Override
    protected void initValue() {
        setTopGone();
        homeFragment1 = new HomeFragment1();
        homeFragment2 = new HomeFragment2();
        homeFragment3 = new HomeFragment3();
        initMultiFragment(R.id.fl_fragment, new Fragment[]{homeFragment1, homeFragment2, homeFragment3});

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onNotificationReceived(String title, String content, String extras, String contentType) {
        super.onNotificationReceived(title, content, extras, contentType);
        ZLog.dZheng("onNotificationReceived" + extras);
    }

//    @Override
//    public void onNotificationOpened(String title, String content, String extras) {
//        PushBean pushBean = GsonUtil.fromJson(extras, PushBean.class);
//        Intent intent = new Intent();
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        switch (pushBean.getData().getType()) {
//            case 1:
//                intent.setClass(context, NoticeDetailActivity.class);
//                intent.putExtra(IntentConstant.HOME_VIEW_FLOW, pushBean.getData().getEntityId());
//                startActivity(intent);
//                break;
//            case 2:
//                intent.setClass(context, EventDetailActivity.class);
//                intent.putExtra(IntentConstant.HOME_VIEW_FLOW, pushBean.getData().getEntityId());
//                startActivity(intent);
//                break;
//        }
//    }


    @Override
    public void onBackPressed() {
        exitApp();
    }
}
