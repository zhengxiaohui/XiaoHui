package com.zdemo.activity;

import android.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.zbase.adapter.ZFragmentPagerAdapter;
import com.zbase.view.MyTabWidget;
import com.zdemo.R;
import com.zdemo.fragment.RankingFragment;

import java.util.ArrayList;

/**
 * 创建人：郑晓辉
 * 创建日期：2017/10/09
 * 描述：cust tab viewpager
*/
public class CustTabViewpagerActivity extends BaseActivity {
    private MyTabWidget myTabWidget;
    private ViewPager viewPager;
    @Override
    protected int inflateMainLayoutId() {
        return R.layout.activity_cust_tab_viewpager;
    }
    @Override
    protected void initView(View view) {
        myTabWidget = (MyTabWidget) view.findViewById(R.id.myTabWidget);
        myTabWidget.setCurrentTab(0);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        ArrayList<Fragment> fragmentList = new ArrayList<>();
        RankingFragment rankingFragment0 = new RankingFragment();
        RankingFragment rankingFragment1 = new RankingFragment();
        RankingFragment rankingFragment2 = new RankingFragment();
        fragmentList.add(rankingFragment0);
        fragmentList.add(rankingFragment1);
        fragmentList.add(rankingFragment2);
        ZFragmentPagerAdapter zFragmentPagerAdapter = new ZFragmentPagerAdapter(getFragmentManager(), fragmentList);
        viewPager.setAdapter(zFragmentPagerAdapter);
        viewPager.setOffscreenPageLimit(fragmentList.size());
    }
    @Override
    protected void setListener() {
        myTabWidget.setSelectionChangedListener(new MyTabWidget.OnMyTabSelectionChanged() {
            @Override
            public void onTabSelectionChanged(int tabIndex, boolean clicked) {
                viewPager.setCurrentItem(tabIndex);
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                myTabWidget.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    @Override
    protected void initValue() {

    }
    @Override
    public void onClick(View v) {

    }
}