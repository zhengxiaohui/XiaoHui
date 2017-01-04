package com.zbase.view.viewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/*********************
 * 禁止viewPager滑动 wg
 ******************/
public class CantScrollViewPager extends ViewPager {
	private boolean isCanScroll = true;

	public CantScrollViewPager(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public CantScrollViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public void setCanScroll(boolean isCanScroll) {
		this.isCanScroll = isCanScroll;
	}

	@Override
	public void scrollTo(int x, int y) {
		if (isCanScroll) {
			super.scrollTo(x, y);
		}
	}

}
