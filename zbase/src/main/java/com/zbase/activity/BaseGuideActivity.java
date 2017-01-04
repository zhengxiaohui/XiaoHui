package com.zbase.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zbase.adapter.ZViewPagerAdapter;
import com.zbase.common.Const;
import com.zbase.common.ZSharedPreferences;
import com.zbase.view.LinearDot;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建人：郑晓辉
 * 创建日期：2016/11/2
 * 描述：
 */
public abstract class BaseGuideActivity extends AbstractBaseActivity {

    private TextView tvSkip;
    private ViewPager viewPager;
    private LinearDot linearDot;
    private int pageCount;//ViewPager的页数，一般也就是图片有几张
    private List<View> viewList = new ArrayList<>();
    protected List<ImageView> imageViewList = new ArrayList<>();
    private int viewPagerState;
    public static final String IMAGE_URL_LIST = "image_url_list";
    protected ArrayList<String> imageUrlList = new ArrayList<>();

    /**
     * 子类可以覆盖重新设置时间间隔
     *
     * @return
     */
    protected int getIntervalTime() {
        return 3000;
    }

    /**
     * 是否自动播放，默认true,子类可以覆盖设置为不自动播放
     *
     * @return
     */
    protected boolean isAutoPlay() {
        return true;
    }

    /**
     * 最后一页是否自动跳转到首页，默认false,子类可以覆盖设置自动跳转
     *
     * @return
     */
    protected boolean isLastPageAutoJump() {
        return false;
    }

    @Override
    protected int inflateBaseLayoutId() {
        return inflateMainLayoutId();
    }

    @Override
    protected void initBaseView(View view) {
        tvSkip = (TextView) view.findViewById(findTvSkipById());
        viewPager = (ViewPager) view.findViewById(findViewPagerById());
        linearDot = (LinearDot) view.findViewById(findLinearDotById());
        tvSkip.setVisibility(View.GONE);
        imageUrlList = getIntent().getStringArrayListExtra(IMAGE_URL_LIST);
        if (imageUrlList != null && imageUrlList.size() > 0) {//如果有网络图片则页数等于网络图片，如果不是网络图片，则自己设定页数
            pageCount = imageUrlList.size();
        } else {
            pageCount = getPageCount();
        }
        if (pageCount == 0) {
            return;
        }
        linearDot.setDotCount(pageCount);
        for (int i = 0; i < pageCount; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(new ViewPager.LayoutParams());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            viewList.add(imageView);
            imageViewList.add(imageView);
        }
        ZViewPagerAdapter zViewPagerAdapter = new ZViewPagerAdapter(viewList);
        viewPager.setAdapter(zViewPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {
                linearDot.setSelectedPosition(position % viewList.size());
                if (position == pageCount - 1) {
                    tvSkip.setVisibility(View.VISIBLE);
                } else {
                    tvSkip.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (handler.hasMessages(0)) {
                    handler.removeMessages(0);
                }
                if (state == ViewPager.SCROLL_STATE_IDLE && isAutoPlay()) {
                    handler.sendEmptyMessageDelayed(0, getIntervalTime());
                }
                viewPagerState = state;
                if (state == ViewPager.SCROLL_STATE_DRAGGING && viewPager.getCurrentItem() == pageCount - 1) {//最后一页再滑动
                    handler.sendEmptyMessageDelayed(1, 100);
                }
            }
        });
        tvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ZSharedPreferences.getInstance(BaseGuideActivity.this).putBoolean(Const.FIRST_LAUNCH, false);
                onTVSkipClick();
            }
        });
        if (isAutoPlay()) {
            handler.sendEmptyMessageDelayed(0, getIntervalTime());
        }
    }

    protected abstract int findTvSkipById();

    protected abstract int findViewPagerById();

    protected abstract int findLinearDotById();

    protected abstract int getPageCount();

    protected abstract Class getHomeActivityClass();

    /**
     * 子类可以覆盖执行不同跳转等
     */
    protected void onTVSkipClick() {
        startActivity(new Intent(BaseGuideActivity.this, getHomeActivityClass()));
        finish();
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    int currentItem = viewPager.getCurrentItem();
                    if (currentItem < pageCount) {
                        viewPager.setCurrentItem(currentItem + 1);
                        handler.sendEmptyMessageDelayed(0, getIntervalTime());
                    }
                    if (currentItem == pageCount - 1 && isLastPageAutoJump()) {
                        tvSkip.callOnClick();
                    }
                    break;
                case 1:
                    if (viewPager.getCurrentItem() < pageCount - 1 || viewPagerState == ViewPager.SCROLL_STATE_DRAGGING) {
                        handler.removeMessages(1);
                    } else {
                        tvSkip.callOnClick();
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler.hasMessages(0)) {
            handler.removeMessages(0);
        }
        if (handler.hasMessages(1)) {
            handler.removeMessages(1);
        }
    }
}
