package com.zbase.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class ZViewPagerAdapter extends PagerAdapter {

    private List<View> viewList = new ArrayList<>();
    private List<String> titleList = new ArrayList<>();

    public ZViewPagerAdapter(List<View> viewList) {
        this.viewList = viewList;
    }

    // 获取要滑动的控件的数量，在这里我们以滑动的广告栏为例，那么这里就应该是展示的广告图片的ImageView数量
    @Override
    public int getCount() { // 获得size
        return viewList.size();
    }

    // 判断显示的是否是同一个View，这里我们将两个参数相比较返回即可
    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    // PagerAdapter只缓存三张要显示的View，如果滑动的View超出了缓存的范围，就会调用这个方法，将View销毁
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(viewList.get(position));//删除页卡
    }

    // 当要显示的View可以进行缓存的时候，会调用这个方法进行显示View的初始化，我们将要显示的View加入到ViewGroup中，然后作为返回值返回即可
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(viewList.get(position));//添加页卡
        return viewList.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }

    /**
     * 设置头部切换标签标题
     *
     * @param titleList
     */
    public void setPageTitleList(List<String> titleList) {
        this.titleList = titleList;
    }
}
