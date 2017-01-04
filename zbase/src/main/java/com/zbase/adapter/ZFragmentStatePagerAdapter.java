package com.zbase.adapter;


import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;

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
 * 描述 :FragmentStatePagerAdapter 和前面的 FragmentPagerAdapter 一样，
 * 是继承子 PagerAdapter。但是，和 FragmentPagerAdapter 不一样的是，
 * 正如其类名中的 'State' 所表明的含义一样，该 PagerAdapter 的实现将只保留当前页面，
 * 当页面离开视线后，就会被消除，释放其资源；而在页面需要显示时，生成新的页面(就像 ListView 的实现一样)。
 * 这么实现的好处就是当拥有大量的页面时，不必在内存中占用大量的内存。
 * 但是少量页面的时候用FragmentPagerAdapter加载比较快，因为都在内存中了
 * <p/>
 * **********************************************************
 */
public class ZFragmentStatePagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> titleList = new ArrayList<>();

    public ZFragmentStatePagerAdapter(FragmentManager fragmentManager, List<Fragment> fragmentList) {
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
