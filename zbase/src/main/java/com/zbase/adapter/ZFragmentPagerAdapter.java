package com.zbase.adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * **********************************************************
 * <p/>
 * 说明：通用列表适配器抽象基类
 * <p/>
 * 作者：郑晓辉
 * <p/>
 * 创建日期：2013-05-19
 * <p/>
 * 描述 :FragmentPagerAdapter 和前面的 FragmentStatePagerAdapter 一样，
 * 是继承子 PagerAdapter。但是，和 FragmentStatePagerAdapter 不一样的是
 * 少量页面的时候用FragmentPagerAdapter加载比较快，因为都在内存中了
 * <p/>
 * **********************************************************
 */
public class ZFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> titleList = new ArrayList<>();

    public ZFragmentPagerAdapter(FragmentManager fragmentManager, List<Fragment> fragmentList) {
        super(fragmentManager);
        this.fragmentList = fragmentList;
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public Fragment getItem(int position) {
        if (position < fragmentList.size()) {
            return fragmentList.get(position);
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position < titleList.size()) {
            return titleList.get(position);
        }
        return null;
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
