package com.zdemo.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.zdemo.R;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerActivity extends Activity {

	private View view1, view2, view3;
	private ViewPager viewPager;
	private PagerTitleStrip pagerTitleStrip;//viewpager的标题
	private PagerTabStrip pagerTabStrip;//一个viewpager的指示器，效果就是一个横的粗的下划线
	private List<View> viewList;
	private List<String> titleList;
	private Button weibo_button;
   private Intent intent;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_pager_demo);
		initView();
	}

	private void initView() {
		viewPager = (ViewPager) findViewById(R.id.viewpager);
		pagerTabStrip=(PagerTabStrip) findViewById(R.id.pagertab);
		pagerTabStrip.setTabIndicatorColor(getResources().getColor(R.color.yellow));
		pagerTabStrip.setDrawFullUnderline(false);
		pagerTabStrip.setBackgroundColor(getResources().getColor(R.color.red));
		pagerTabStrip.setTextSpacing(50);
		view1 = LayoutInflater.from(this).inflate(R.layout.viewpager_layout1,null);
		view2 = LayoutInflater.from(this).inflate(R.layout.viewpager_layout2,null);
		view3 = LayoutInflater.from(this).inflate(R.layout.viewpager_layout3,null);

		viewList = new ArrayList<View>();// 将要分页显示的View装入数组中
		viewList.add(view1);
		viewList.add(view2);
		viewList.add(view3);

		titleList = new ArrayList<String>();// 每个页面的Title数据
		titleList.add("王鹏");
		titleList.add("姜语");
		titleList.add("结婚");

		PagerAdapter pagerAdapter = new PagerAdapter() {
			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				return arg0 == arg1;
			}
			@Override
			public int getCount() {
				return viewList.size();
			}

			@Override
			public void destroyItem(ViewGroup container, int position,
					Object object) {
				container.removeView(viewList.get(position));

			}

			@Override
			public int getItemPosition(Object object) {

				return super.getItemPosition(object);
			}

			@Override
			public CharSequence getPageTitle(int position) {

				return titleList.get(position);
			}

			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				container.addView(viewList.get(position));
				weibo_button=(Button) findViewById(R.id.button1);
				weibo_button.setOnClickListener(new OnClickListener() {
					
					public void onClick(View v) {
						intent=new Intent(ViewPagerActivity.this,ViewPagerWeiBoActivity.class);
						startActivity(intent);
					}
				});
				return viewList.get(position);
			}

		};
		viewPager.setAdapter(pagerAdapter);
	}



}