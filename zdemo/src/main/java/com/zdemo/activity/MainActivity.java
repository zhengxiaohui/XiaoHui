package com.zdemo.activity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.zdemo.R;

import net.lucode.hackware.magicindicatordemo.example.ExampleMainActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends BaseActivity implements OnItemClickListener {

    private GridView gridview;
    private SimpleAdapter simpleAdapter;
    private Class[] classes = {AnimationActivity.class, HtmlTextActivity.class, PopupWinowActivity.class,
            MyDialogActivity.class, ViewPagerActivity.class,
            LaunchActivity.class, CustomProgressDialogActivity.class,
            AreaCNWheelActivity.class, CustomShapeImageViewActivity.class, ColorMatrixActivity.class,
            SlidingMenuActivity.class, ReportActivity.class, CategoryListViewActivity.class, WheelDateTimeActivity.class,
            MaskableFrameLayoutActivity.class, UMengShareActivity.class, BaiduMapActivity.class, JuheActivity.class,
            UltraPullToRefreshActivity.class, DownloadManagerActivity.class, ObservableHorizontalScrollViewActivity.class, AutoCycleSlideViewActivity.class,
            MultiImageSelectActivity.class, PasswordViewActivity.class,QRCodeActivity.class,SeePhotoViewActivity.class,MultiWheelViewActivity.class,
            AdapterSelectActivity.class,LongClickLinearLayoutActivity.class,SwitchButtonActivity.class,FewUseWidgetActivity.class,RegisterAndLoginActivity.class,
            LanguageSwitchActivity.class,DashBoardActivity.class,TransMaskActivity.class,PathDrawViewActivity.class,CustTabViewpagerActivity.class,ExampleMainActivity.class,
            OverseasThirdLogin.class
    };
    private String[] demoNames = {"列表和图片的3D左右旋转", "HTML文本显示",
            "各种自定义popupWindow", " 自定义对话框，布局样式都自定义",
            "普通ViewPager和仿微博ViewPager", "启动页和引导页", "自定义转圈对话框",
             "中国地区滚轮选择对话框", "自定义形状", "颜色矩阵变幻滤镜",
            "列表向左滑出菜单", "报表各种形状图", "带分类标题的ListView", "滚轮日期时间选择器", "蒙版效果MaskableFrameLayout", "友盟分享", "百度地图"
            , "聚合数据", "通用下拉刷新UltraPullToRefresh", "DownloadManager下载", "监控HorizontalScrollView的滑动状态", "AutoCycleSlideView自定义自动滚动幻灯片",
            "图库单选多选拍摄图片", "密码输入框控件", "扫一扫","PhotoViewActivity","多个WheelView滚轮控件","列表单选，多选，全选，取消全选","长按布局控件结合录音播放计时等"
            ,"SwitchButton滑动开关","很少用到的系统控件使用示例","自定义注册和登录控件","切换语言环境","各种样式的表盘","按下灰色遮罩和验证码倒计时"
            ,"正三角形和点路径集合的自定义图形","cust tab viewpager","MagicIndicator","facebook,twitter,google"

    };

    @Override
    protected int inflateMainLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(View view) {
        gridview = (GridView) findViewById(R.id.gridview);
    }

    @Override
    protected void setListener() {
        gridview.setOnItemClickListener(this);
    }

    @Override
    protected void initValue() {

        ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < demoNames.length; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("ItemText", demoNames[i]);
            list.add(map);
        }
        simpleAdapter = new SimpleAdapter(this, list, R.layout.gridview_item,
                new String[]{"ItemText"}, new int[]{R.id.ItemText});
        gridview.setAdapter(simpleAdapter);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        startActivityByClass(classes[position]);
    }

    private void startActivityByClass(Class cls) {
        startActivity(new Intent(MainActivity.this, cls));
    }

    @Override
    public void onClick(View v) {

    }
}