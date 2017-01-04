package com.zbase.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.zbase.view.viewpager.WrapViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建人：郑晓辉
 * 创建日期：2016/6/13
 * 描述：自定义自动循环滚动幻灯片
 * 实际上是继承RelativeLayout，所以RelativeLayout的所有属性都能用。WrapViewPager和LinearDot是其子布局
 * 使用WrapViewPager能够是高度自适应，如果使用普通的ViewPager则必须设置固定的高度。
 * 注意：LinearDot设置与底部的距离只能用paddingBottom不能用marginBottom，marginBottom不起作用，原因不明
 */
public class AutoCycleSlideView extends RelativeLayout {

    private Context context;
    private int intervalTime = 5000;//默认间隔时间5秒
    private Handler handler;
    private WrapViewPager viewPager;

    public AutoCycleSlideView(Context context) {
        super(context);
        init(context);
    }

    public AutoCycleSlideView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AutoCycleSlideView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
    }

    /**
     * 根据子控件高度计算自身高度,这样自己的高度才能用wrap自适应
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, viewPager.getMeasuredHeight());
    }

    public void loadViews(final List<View> viewList) {
        loadViews(viewList, intervalTime);
    }

    public void loadViews(final List<View> viewList, final int intervalTime) {
        this.intervalTime = intervalTime;
        final LinearDot linearDot = (LinearDot) getChildAt(0);
        linearDot.setDotCount(viewList.size());
        viewPager = new WrapViewPager(context);
        CyclePageAdapter cyclePageAdapter = new CyclePageAdapter(viewList);
        viewPager.setAdapter(cyclePageAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {
                linearDot.setSelectedPosition(position % viewList.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (null != handler) {
                    if (handler.hasMessages(0)) {
                        handler.removeMessages(0);
                    }
                    if (state == ViewPager.SCROLL_STATE_IDLE) {
                        handler.sendMessageDelayed(Message.obtain(handler, 0), intervalTime);
                    }
                }
            }
        });
        addView(viewPager);
    }

    public void startAutoFlow() {
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                handler.sendMessageDelayed(Message.obtain(handler, 0), intervalTime);//接收到自己的消息后再发送消息，变成循环发送
                return true;
            }
        });
        handler.sendMessageDelayed(Message.obtain(handler, 0), intervalTime);
    }

    public void onResume() {
        if (!handler.hasMessages(0)) {//如果没有消息，重新发送一条，就是为了activity在onResume的时候重新运行。判断是为了不移除start里面发的消息
            handler.sendMessageDelayed(Message.obtain(handler, 0), intervalTime);
        }
    }

    public void onPause() {
        handler.removeMessages(0);
    }

    private class CyclePageAdapter extends PagerAdapter {

        private List<View> viewList = new ArrayList<>();

        public CyclePageAdapter(List<View> viewList) {
            this.viewList = viewList;
        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        // PagerAdapter只缓存三张要显示的View，如果滑动的View超出了缓存的范围，就会调用这个方法，将View销毁
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(viewList.get(position % viewList.size()));//删除页卡
        }

        // 当要显示的View可以进行缓存的时候，会调用这个方法进行显示View的初始化，我们将要显示的View加入到ViewGroup中，然后作为返回值返回即可
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(viewList.get(position % viewList.size()));//添加页卡
            return viewList.get(position % viewList.size());
        }
    }
}
