package com.zdemo.activity;

import android.app.Fragment;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.zbase.adapter.ZFragmentPagerAdapter;
import com.zdemo.R;
import com.zdemo.fragment.BarChartFragment;
import com.zdemo.fragment.HelloLineChartFragment;
import com.zdemo.fragment.LineChartFragment;
import com.zdemo.fragment.PieChartFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 创建人：郑晓辉
 * 创建日期：2015/12/26
 * 描述：
 */

public class ReportActivity extends BaseActivity {

    @Override
    protected int inflateMainLayoutId() {
        return R.layout.activity_report;
    }

    @Override
    protected void initView(View view) {

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        List<String> titleList = Arrays.asList(getResources().getStringArray(R.array.chart));
        tabLayout.addTab(tabLayout.newTab().setText(titleList.get(0)));
        tabLayout.addTab(tabLayout.newTab().setText(titleList.get(1)));
        tabLayout.addTab(tabLayout.newTab().setText(titleList.get(2)));
        tabLayout.addTab(tabLayout.newTab().setText(titleList.get(3)));
        ArrayList<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new LineChartFragment());
        fragmentList.add(new PieChartFragment());
        fragmentList.add(new BarChartFragment());
        fragmentList.add(new HelloLineChartFragment());
        ZFragmentPagerAdapter zFragmentPagerAdapter = new ZFragmentPagerAdapter(getFragmentManager(), fragmentList);
        zFragmentPagerAdapter.setPageTitleList(titleList);
        viewPager.setAdapter(zFragmentPagerAdapter);
        viewPager.setOffscreenPageLimit(fragmentList.size());
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initValue() {

    }

    @Override
    public void onClick(View v) {

    }
}
