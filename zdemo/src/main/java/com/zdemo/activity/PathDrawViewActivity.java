package com.zdemo.activity;

import android.graphics.Point;
import android.view.View;

import com.zbase.view.PathDrawView;
import com.zdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建人：郑晓辉
 * 创建日期：2017/1/12
 * 描述：正三角形和点路径集合的自定义图形
 */
public class PathDrawViewActivity extends BaseActivity{

    private PathDrawView pathDrawView;

    @Override
    protected int inflateMainLayoutId() {
        return R.layout.activity_path_draw_view;
    }

    @Override
    protected void initView(View view) {
        pathDrawView = (PathDrawView) view.findViewById(R.id.pathDrawView);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initValue() {
        List<Point> pointList=new ArrayList<>();
        pointList.add(new Point(10,10));
        pointList.add(new Point(20,20));
        pointList.add(new Point(30,30));
        pointList.add(new Point(40,40));
        pointList.add(new Point(50,50));
        pathDrawView.setPointList(pointList);
    }

    @Override
    public void onClick(View v) {

    }
}
