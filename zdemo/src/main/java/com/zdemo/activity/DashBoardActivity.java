package com.zdemo.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.zbase.view.dashboardview.DashboardView1;
import com.zbase.view.dashboardview.DashboardView2;
import com.zbase.view.dashboardview.DashboardView3;
import com.zbase.view.dashboardview.DashboardView4;
import com.zdemo.R;

import java.util.Random;

/**
 * 创建人：郑晓辉
 * 创建日期：2016/12/28
 * 描述：各种样式的表盘
 */
public class DashBoardActivity extends BaseActivity {

    private DashboardView1 mDashboardView1;
    private DashboardView2 mDashboardView2;
    private DashboardView3 mDashboardView3;
    private DashboardView4 mDashboardView4;

    private boolean isAnimFinished = true;

    @Override
    protected int inflateMainLayoutId() {
        return R.layout.activity_dash_board;
    }

    @Override
    protected void initView(View view) {
        mDashboardView1 = (DashboardView1) findViewById(R.id.dashboard_view_1);
        mDashboardView2 = (DashboardView2) findViewById(R.id.dashboard_view_2);
        mDashboardView3 = (DashboardView3) findViewById(R.id.dashboard_view_3);
        mDashboardView4 = (DashboardView4) findViewById(R.id.dashboard_view_4);
    }

    @Override
    protected void setListener() {
        mDashboardView1.setOnClickListener(this);
        mDashboardView2.setOnClickListener(this);
        mDashboardView3.setOnClickListener(this);
        mDashboardView4.setOnClickListener(this);
    }

    @Override
    protected void initValue() {
        mDashboardView2.setCreditValueWithAnim(new Random().nextInt(600) + 350);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dashboard_view_1:
                mDashboardView1.setRealTimeValue(new Random().nextInt(100));

                break;
            case R.id.dashboard_view_2:
                mDashboardView2.setCreditValueWithAnim(new Random().nextInt(950 - 350) + 350);

                break;
            case R.id.dashboard_view_3:
                mDashboardView3.setCreditValue(new Random().nextInt(950 - 350) + 350);

                break;
            case R.id.dashboard_view_4:
                if (isAnimFinished) {
                    ObjectAnimator animator = ObjectAnimator.ofInt(mDashboardView4, "mRealTimeValue",
                            mDashboardView4.getVelocity(), new Random().nextInt(180));
                    animator.setDuration(1500).setInterpolator(new LinearInterpolator());
                    animator.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            isAnimFinished = false;
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            isAnimFinished = true;
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {
                            isAnimFinished = true;
                        }
                    });
                    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            int value = (int) animation.getAnimatedValue();
                            mDashboardView4.setVelocity(value);
                        }
                    });
                    animator.start();
                }

                break;
        }
    }
}
