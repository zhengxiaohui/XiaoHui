package com.zbase.listener;

import android.support.v4.view.ViewPager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

/**
 * Created by 郑晓辉 on 2015/3/10
 * email:378180078@qq.com
 * 摘要：自定义标题跟随移动的ViewPager标题移动动画
 * 详细说明：如果需要自定义就继承此类使用
 * 使用示例：
 */
public class onTitleMovedViewPagerChangeListener implements ViewPager.OnPageChangeListener {

    private int currIndex = 0;// 当前页卡编号
    private int titleOffset;//偏移量
    private ImageView cursorImageView;// 动画图片

    public onTitleMovedViewPagerChangeListener(int titleOffset,ImageView cursorImageView){
        this.titleOffset =titleOffset;
        this.cursorImageView =cursorImageView;
    }

    public void onPageScrollStateChanged(int arg0) {


    }

    public void onPageScrolled(int arg0, float arg1, int arg2) {


    }

    public void onPageSelected(int arg0) {
        Animation animation = new TranslateAnimation(titleOffset *currIndex, titleOffset *arg0, 0, 0);
        currIndex = arg0;
        animation.setFillAfter(true);// True:图片停在动画结束位置
        animation.setDuration(300);
        cursorImageView.startAnimation(animation);
    }

}
