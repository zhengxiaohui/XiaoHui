package com.zdemo.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.oranllc.kanwheel.custom.DateTimeWheelView;
import com.zdemo.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * 创建人：郑晓辉
 * 创建日期：2016/1/11
 * 描述：
 */
public class WheelDateTimeActivity extends Activity {
    EditText et_date_time;

    Button btnselecttime1, btnselecttime2, btnselecttime3;

    int year, month, day, hour, min;

    LayoutInflater inflater;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wheel_datetime);
        et_date_time = (EditText) findViewById(R.id.txttime);
        btnselecttime1 = (Button) findViewById(R.id.button1);
        btnselecttime2 = (Button) findViewById(R.id.button2);
        btnselecttime3 = (Button) findViewById(R.id.button3);

        String yyyy = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
        Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
        yyyy = formatter.format(curDate);

        Calendar calendar = Calendar.getInstance();

        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        min = calendar.get(Calendar.MINUTE);

        inflater = LayoutInflater.from(WheelDateTimeActivity.this);

        btnselecttime1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                final DateTimeWheelView wheelMain = new DateTimeWheelView.Builder(WheelDateTimeActivity.this, DateTimeWheelView.DisplayType.YEAR_MONTH_DAY_HOUR_MIN).setYear(year).setMonth(month)
                        .setDay(day).setHour(hour).setMin(min).build();
                new AlertDialog.Builder(WheelDateTimeActivity.this)
                        .setTitle("选择时间")
                        .setView(wheelMain.getTimePickerView())
                        .setPositiveButton("确定",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        year = wheelMain.getYear();
                                        month = wheelMain.getMonth()-1;
                                        day = wheelMain.getDay();
                                        hour=wheelMain.getHour();
                                        min=wheelMain.getMin();
                                        et_date_time.setText(wheelMain.getYear() + "年" + wheelMain.getMonth() + "月" + wheelMain.getDay() + "日" + wheelMain.getHour() + "时" + wheelMain.getMin() + "分");
                                    }
                                }).setNegativeButton("取消", null).show();

            }
        });

        btnselecttime2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                final DateTimeWheelView wheelMain = new DateTimeWheelView.Builder(WheelDateTimeActivity.this, DateTimeWheelView.DisplayType.YEAR_MONTH_DAY).setYear(year).setMonth(month)
                        .setDay(day).build();
                new AlertDialog.Builder(WheelDateTimeActivity.this)
                        .setTitle("选择日期")
                        .setView(wheelMain.getTimePickerView())
                        .setPositiveButton("确定",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        year = wheelMain.getYear();
                                        month = wheelMain.getMonth()-1;
                                        day = wheelMain.getDay();
                                        et_date_time.setText(wheelMain.getYear() + "年" + wheelMain.getMonth() + "月" + wheelMain.getDay() + "日");
                                    }
                                }).setNegativeButton("取消", null).show();

            }
        });

        btnselecttime3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                final DateTimeWheelView wheelMain = new DateTimeWheelView.Builder(WheelDateTimeActivity.this, DateTimeWheelView.DisplayType.YEAR).setYear(year).build();
                new AlertDialog.Builder(WheelDateTimeActivity.this)
                        .setTitle("选择年份")
                        .setView(wheelMain.getTimePickerView())
                        .setPositiveButton("确定",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        year = wheelMain.getYear();
                                        et_date_time.setText(wheelMain.getYear() + "年");
                                    }
                                }).setNegativeButton("取消", null).show();

            }
        });
    }
}
