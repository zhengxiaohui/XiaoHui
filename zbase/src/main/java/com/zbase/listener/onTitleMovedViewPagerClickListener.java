package com.zbase.listener;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by 郑晓辉 on 2015/3/10
 * email:378180078@qq.com
 * 摘要：自定义标题跟随移动的ViewPager标题点击事件
 * 详细说明：如果需要自定义就继承此类使用
 * 使用示例：
 */
public class onTitleMovedViewPagerClickListener implements View.OnClickListener {

    private int index=0;
    private ViewPager viewPager;

    public onTitleMovedViewPagerClickListener(ViewPager viewPager,int index){
        this.index=index;
        this.viewPager=viewPager;
    }

    public void onClick(View v) {
        viewPager.setCurrentItem(index);
    }

}
