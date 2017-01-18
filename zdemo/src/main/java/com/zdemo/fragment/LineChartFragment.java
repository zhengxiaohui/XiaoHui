package com.zdemo.fragment;

import android.graphics.Color;
import android.view.View;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.zdemo.R;
import com.zdemo.view.CustomMarkerView;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * 创建人：郑晓辉
 * 创建日期：2016/7/20
 * 描述：
 */
public class LineChartFragment extends BaseFragment {

    private LineChart mLineChart;
    String[] times = {"11:40", "11:45", "11:50", "11:55", "12:00", "12:05", "12:10", "12:15", "12:20", "12:25", "12:30", "12:35"};//X轴的标注
    float[] amounts = {4f, 4.2f, 4.4f, 4.4f, 4.69f, 4.3f, 4.9f, 4.7f, 4.6f, 4.6f, 4.5f, 4.3f};//图表的数据点

    @Override
    protected int inflateMainLayoutId() {
        return R.layout.fragment_line_chart;
    }

    @Override
    protected void initView(View view) {
        mLineChart = (LineChart) view.findViewById(R.id.spread_line_chart);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initValue() {
        showChart(mLineChart);
    }

    private void showChart(LineChart lineChart) {

        ArrayList<String> xValues = new ArrayList<String>();
        for (int i = 0; i < times.length; i++) {
            // x轴显示的数据，这里默认使用数字下标显示
            xValues.add(times[i]);
        }
        // y轴的数据
        ArrayList<Entry> yValues = new ArrayList<Entry>();
        for (int i = 0; i < amounts.length; i++) {
            yValues.add(new Entry(amounts[i], i));
        }


        // build a dataset and give it a type
        // y轴的数据集合
        LineDataSet lineDataSet = new LineDataSet(yValues, "溶氧度(mg/L)");/*y轴数据名称，显示在比例图上*/
//        lineDataSet.setFillAlpha(110);
//        lineDataSet.setFillColor(Color.RED);
        //用y轴的集合来设置参数
        lineDataSet.setLineWidth(1.75f); // 线宽
        lineDataSet.setCircleSize(3f);// 显示的圆形大小
        lineDataSet.setColor(Color.RED);// 折线的颜色
        lineDataSet.setCircleColor(Color.RED);// 圆形的颜色
        lineDataSet.setHighLightColor(Color.GREEN); //显示点击对齐线的颜色
        ArrayList<LineDataSet> lineDataSets = new ArrayList<LineDataSet>();
        lineDataSets.add(lineDataSet); // add the datasets
        // build a data object with the datasets
        LineData lineData = new LineData(xValues, lineDataSets);
        lineData.setValueFormatter(new ValueFormatter() {//格式化显示的数据
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return new DecimalFormat("0.00").format(value);
            }
        });

        // get the legend (only possible after setting data)
        Legend mLegend = lineChart.getLegend(); // 设置比例图标示，就是那个一组y的value的
        // modify the legend ...
        // mLegend.setPosition(LegendPosition.LEFT_OF_CHART);
        mLegend.setForm(Legend.LegendForm.CIRCLE);// 左下角说明文字左边的图形（溶氧度(mg/L)）
        mLegend.setFormSize(10f);// 字体大小(左下角说明文字)
        mLegend.setTextColor(Color.RED);// y轴数据名称的颜色，显示在比例图上(左下角说明文字)
//      mLegend.setTypeface(mTf);// 字体


        lineChart.setDrawBorders(true);  //是否在折线图上添加边框
        // no description text
        lineChart.setDescription("");// 数据描述
        // 如果没有数据的时候，会显示这个，类似listview的emtpyview
        lineChart.setNoDataTextDescription("没有数据");
        // enable / disable grid background
        lineChart.setDrawGridBackground(false); // 是否显示表格颜色
        lineChart.setGridBackgroundColor(Color.BLUE & 0x70FFFFFF); // 表格的的颜色，在这里是是给颜色设置一个透明度
        // enable touch gestures
        lineChart.setTouchEnabled(true); // 设置是否可以触摸
        // enable scaling and dragging
        lineChart.setDragEnabled(true);// 是否可以拖拽
        lineChart.setScaleEnabled(true);// 是否可以缩放
        // if disabled, scaling can be done on x- and y-axis separately
        lineChart.setPinchZoom(false);//
        lineChart.setBackgroundColor(Color.WHITE);// 设置背景颜色

        lineChart.getAxisRight().setDrawGridLines(false);//隐藏右边坐标轴横网格线
        lineChart.getAxisRight().setEnabled(false); // 隐藏右边的坐标轴

        YAxis leftAxis = lineChart.getAxisLeft();
        lineChart.getAxisLeft().setDrawGridLines(false);//隐藏左边坐标轴横网格线
        leftAxis.setAxisMaxValue(12f);
//        leftAxis.setValueFormatter(new YAxisValueFormatter() {//y轴坐标显示格式
//            @Override
//            public String getFormattedValue(float value, YAxis yAxis) {
//                return new DecimalFormat("0.00").format(value);
//            }
//        });

//        YAxis leftAxis = lineChart.getAxisLeft();
//        LimitLine ll = new LimitLine(4.5f, "血压偏高");//限制警示线
//        ll.setLineColor(Color.RED);
//        ll.setLineWidth(4f);
//        ll.setTextColor(Color.BLACK);
//        ll.setTextSize(12f);
//        leftAxis.addLimitLine(ll);

        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM); // 让x轴在下面
        lineChart.getXAxis().setGridColor(getResources().getColor(R.color.transparent));//设置表格透明
        lineChart.getXAxis().setDrawGridLines(false);//隐藏X轴竖网格线
//        lineChart.getXAxis().setValueFormatter(new XAxisValueFormatter() {
//            @Override
//            public String getXValue(String original, int index, ViewPortHandler viewPortHandler) {
//                return null;
//            }
//        });
        CustomMarkerView mv = new CustomMarkerView(context, R.layout.custom_marker_view_layout, times,"溶氧mg/L");
        lineChart.setMarkerView(mv);
        lineChart.setData(lineData); // 设置数据
        lineChart.animateX(2500); // 立即执行的动画,x轴
    }

    @Override
    public void onClick(View v) {

    }
}
