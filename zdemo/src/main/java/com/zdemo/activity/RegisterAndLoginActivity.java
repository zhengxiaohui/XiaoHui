package com.zdemo.activity;

import android.app.Fragment;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.zbase.adapter.ZFragmentPagerAdapter;
import com.zdemo.R;
import com.zdemo.fragment.LoginFragment;
import com.zdemo.fragment.RegisterFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 创建人：郑晓辉
 * 创建日期：2016/12/26
 * 描述：自定义注册和登录控件
 */
public class RegisterAndLoginActivity extends BaseActivity {


    @Override
    protected int inflateMainLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView(View view) {
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        List<String> titleList = Arrays.asList(getResources().getStringArray(R.array.register_login));
        tabLayout.addTab(tabLayout.newTab().setText(titleList.get(0)));
        tabLayout.addTab(tabLayout.newTab().setText(titleList.get(1)));
        ArrayList<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new RegisterFragment());
        fragmentList.add(new LoginFragment());
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
