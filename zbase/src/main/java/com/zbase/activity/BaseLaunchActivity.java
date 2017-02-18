package com.zbase.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;

import com.zbase.R;
import com.zbase.common.Const;
import com.zbase.common.ZSharedPreferences;

import java.util.ArrayList;

/**
 * 创建人：郑晓辉
 * 创建日期：2016/11/2
 * 描述：
 */
public abstract class BaseLaunchActivity extends AbstractBaseActivity {

    protected ImageView iv_launch;
    protected ArrayList<String> imageUrlList = new ArrayList<>();

    @Override
    protected int inflateBaseLayoutId() {
        return R.layout.zbase_activity_launch;
    }

    @Override
    protected int inflateMainLayoutId() {
        return 0;
    }

    @Override
    protected void initBaseView(View view) {
        iv_launch = (ImageView) view.findViewById(R.id.iv_launch);
    }

    protected void startHandler() {
        startHandler(3000);
    }

    /**
     * 开始倒计时millisecond秒后跳转
     *
     * @param millisecond 倒计时时间，毫秒
     */
    protected void startHandler(int millisecond) {
        handler.sendEmptyMessageDelayed(0, millisecond);
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    if (isTest()) {//如果是测试状态，每次启动后都跳转到引导页
                        Intent intent = new Intent(BaseLaunchActivity.this, getGuideActivityClass());
                        if (imageUrlList != null && imageUrlList.size() > 0) {
                            intent.putStringArrayListExtra(BaseGuideActivity.IMAGE_URL_LIST, imageUrlList);
                        }
                        startActivity(intent);
                    } else {
                        boolean isAppFirstLaunch = ZSharedPreferences.getInstance(BaseLaunchActivity.this).getBoolean(Const.FIRST_LAUNCH, true);//是否第一次启动，默认true
                        if (isAppFirstLaunch) {
                            Intent intent = new Intent(BaseLaunchActivity.this, getGuideActivityClass());
                            if (imageUrlList != null && imageUrlList.size() > 0) {
                                intent.putStringArrayListExtra(BaseGuideActivity.IMAGE_URL_LIST, imageUrlList);
                            }
                            startActivity(intent);
                        } else {
                            startActivity(new Intent(BaseLaunchActivity.this, getHomeActivityClass()));
                        }
                    }
                    finish();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    /**
     * 获取引导页activity的class
     *
     * @return
     */
    protected abstract Class getGuideActivityClass();

    /**
     * 获取主页面activity的class
     *
     * @return
     */
    protected abstract Class getHomeActivityClass();

    /**
     * 是否测试状态，测试的话每次启动都有引导页
     *
     * @return
     */
    protected abstract boolean isTest();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler.hasMessages(0)) {
            handler.removeMessages(0);
        }
    }

}
