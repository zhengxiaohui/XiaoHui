package com.zdemo.activity;

import android.view.View;
import android.widget.TextView;

import com.zbase.enums.DateTimeLimitEnum;
import com.zbase.enums.DateTimeModelEnum;
import com.zbase.listener.OnConfirmClickListener;
import com.zbase.view.MultiWheelView;
import com.zbase.view.popupwindow.DateTimeWheelViewPopupWindow;
import com.zbase.view.popupwindow.MultiWheelViewPopupWindow;
import com.zdemo.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 创建人：郑晓辉
 * 创建日期：2016/12/1
 * 描述：多个WheelView滚轮控件
 */
public class MultiWheelViewActivity extends BaseActivity {

    private MultiWheelView multiWheelView;
    private List<List<String>> dataLists = new ArrayList<>();//多个WheelView的数据
    private List<String> unitList = new ArrayList<>();
    private TextView textView;
    private MultiWheelViewPopupWindow multiWheelViewPopupWindow;
    private DateTimeWheelViewPopupWindow dateTimeWheelViewPopupWindow;

    @Override
    protected int inflateMainLayoutId() {
        return R.layout.activity_multi_wheelview;
    }

    @Override
    protected void initView(View view) {
        multiWheelView = (MultiWheelView) view.findViewById(R.id.multiWheelView);
        textView = (TextView) view.findViewById(R.id.textView);
    }

    @Override
    protected void setListener() {
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMultiWheelViewPopupWindow();
            }
        });
        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimeWheelViewPopupWindow();
            }
        });
    }

    @Override
    protected void initValue() {
        for (int i = 0; i < 3; i++) {
            dataLists.add(Arrays.asList(getResources().getStringArray(R.array.month)));
        }
        unitList.add("时");
        unitList.add("分");
        unitList.add("秒");
        multiWheelView.setDataLists(dataLists);
    }

    private void showMultiWheelViewPopupWindow() {
        if (multiWheelViewPopupWindow==null) {//设置全局变量才能记住上次的选择
            View view = inflate(R.layout.pw_multi_wheelview);
            multiWheelViewPopupWindow = (MultiWheelViewPopupWindow) new MultiWheelViewPopupWindow
                    .Builder(context, view).setMainViewId(R.id.multiWheelView).setConfirmTextViewId(R.id.tv_complete).setCancelTextViewId(R.id.tv_cancel).build();
            multiWheelViewPopupWindow.setOnConfirmClickListener(new OnConfirmClickListener() {
                @Override
                public void onConfirmClick(View v) {
                    textView.setText(multiWheelViewPopupWindow.getMainView().getConnectorString("-"));
                }

            });
            multiWheelViewPopupWindow.getMainView().setDataLists(dataLists);
            multiWheelViewPopupWindow.getMainView().setUnitList(unitList);
        }
        multiWheelViewPopupWindow.showAtBottom(this);
    }

    private void showDateTimeWheelViewPopupWindow() {
        if (dateTimeWheelViewPopupWindow==null) {//设置全局变量才能记住上次的选择
            View view = inflate(R.layout.pw_multi_wheelview);
            dateTimeWheelViewPopupWindow = (DateTimeWheelViewPopupWindow) new DateTimeWheelViewPopupWindow
                    .Builder(context, view).setMainViewId(R.id.multiWheelView).setConfirmTextViewId(R.id.tv_complete).setCancelTextViewId(R.id.tv_cancel).build();
            dateTimeWheelViewPopupWindow.setOnConfirmClickListener(new OnConfirmClickListener() {
                @Override
                public void onConfirmClick(View v) {
                    textView.setText(dateTimeWheelViewPopupWindow.getFormatDateTimeString());
                }

            });
            dateTimeWheelViewPopupWindow.initDateTime(DateTimeLimitEnum.ALL, DateTimeModelEnum.YEAR_MONTH_DAY_HOUR_MIN,false);
        }
        dateTimeWheelViewPopupWindow.showAtBottom(this);
    }

    @Override
    public void onClick(View v) {

    }
}
