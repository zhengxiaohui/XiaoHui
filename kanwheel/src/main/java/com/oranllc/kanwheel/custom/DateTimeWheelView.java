package com.oranllc.kanwheel.custom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.oranllc.kanwheel.R;
import com.oranllc.kanwheel.adapters.NumericWheelAdapter;
import com.oranllc.kanwheel.listener.OnWheelChangedListener;
import com.oranllc.kanwheel.WheelView;

import java.util.Arrays;
import java.util.List;

@Deprecated
public class DateTimeWheelView {

    private Context context;
    private View timePickerView;
    private WheelView wv_year;
    private WheelView wv_month;
    private WheelView wv_day;
    private WheelView wv_hours;
    private WheelView wv_mins;
    private int screenHeight;

    public View getTimePickerView() {
        return timePickerView;
    }

    public enum DisplayType {YEAR, MONTH, DAY, HOUR, MIN, YEAR_MONTH, MONTH_DAY, HOUR_MIN, YEAR_MONTH_DAY, YEAR_MONTH_DAY_HOUR_MIN}

    private DisplayType displayType;

    private static final int START_YEAR = 1990, END_YEAR = 2100;
    private int year, month, day, hour, min;//month是直接传进来endMonthOfYear = calendar.get(Calendar.MONTH);是0-11的美国月

    private DateTimeWheelView(Context context, DisplayType displayType) {
        this.context=context;
        timePickerView = LayoutInflater.from(context).inflate(
                R.layout.wheel_timepicker, null);
        screenHeight = context.getResources().getDisplayMetrics().heightPixels;
        this.displayType = displayType;
    }

    public static class Builder {

        private DateTimeWheelView wheelMain;

        public Builder(Context context, DisplayType displayType) {
            wheelMain = new DateTimeWheelView(context, displayType);
        }

        public Builder setYear(int year) {
            wheelMain.year = year;
            return this;
        }

        public Builder setMonth(int month) {
            wheelMain.month = month;
            return this;
        }

        public Builder setDay(int day) {
            wheelMain.day = day;
            return this;
        }

        public Builder setHour(int hour) {
            wheelMain.hour = hour;
            return this;
        }

        public Builder setMin(int min) {
            wheelMain.min = min;
            return this;
        }

        /**
         * 最后一步把产品装配好给客户
         *
         * @return
         */
        public DateTimeWheelView build() {
            wheelMain.initDateTimePicker();
            return wheelMain;
        }
    }

    private void initDateTimePicker() {
        // 添加大小月月份并将其转换为list,方便之后的判断
        String[] months_big = {"1", "3", "5", "7", "8", "10", "12"};
        String[] months_little = {"4", "6", "9", "11"};

        final List<String> list_big = Arrays.asList(months_big);
        final List<String> list_little = Arrays.asList(months_little);

        wv_year = (WheelView) timePickerView.findViewById(R.id.year);
        wv_month = (WheelView) timePickerView.findViewById(R.id.month);
        wv_day = (WheelView) timePickerView.findViewById(R.id.day);
        wv_hours = (WheelView) timePickerView.findViewById(R.id.hour);
        wv_mins = (WheelView) timePickerView.findViewById(R.id.min);

        // 添加"年"监听
        OnWheelChangedListener wheelListener_year = new OnWheelChangedListener() {
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                int year_num = newValue + START_YEAR;
                // 判断大小月及是否闰年,用来确定"日"的数据
                if (list_big
                        .contains(String.valueOf(wv_month.getCurrentItem() + 1))) {
                    wv_day.setViewAdapter(new NumericWheelAdapter(context,1, 31));
                } else if (list_little.contains(String.valueOf(wv_month
                        .getCurrentItem() + 1))) {
                    wv_day.setViewAdapter(new NumericWheelAdapter(context,1, 30));
                } else {
                    if ((year_num % 4 == 0 && year_num % 100 != 0)
                            || year_num % 400 == 0)
                        wv_day.setViewAdapter(new NumericWheelAdapter(context,1, 29));
                    else
                        wv_day.setViewAdapter(new NumericWheelAdapter(context,1, 28));
                }
            }
        };

        // 添加"月"监听
        OnWheelChangedListener wheelListener_month = new OnWheelChangedListener() {
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                int month_num = newValue + 1;
                // 判断大小月及是否闰年,用来确定"日"的数据
                if (list_big.contains(String.valueOf(month_num))) {
                    wv_day.setViewAdapter(new NumericWheelAdapter(context,1, 31));
                } else if (list_little.contains(String.valueOf(month_num))) {
                    wv_day.setViewAdapter(new NumericWheelAdapter(context,1, 30));
                } else {
                    if (((wv_year.getCurrentItem() + START_YEAR) % 4 == 0 && (wv_year
                            .getCurrentItem() + START_YEAR) % 100 != 0)
                            || (wv_year.getCurrentItem() + START_YEAR) % 400 == 0)
                        wv_day.setViewAdapter(new NumericWheelAdapter(context,1, 29));
                    else
                        wv_day.setViewAdapter(new NumericWheelAdapter(context,1, 28));
                }
            }
        };
        // 年
        wv_year.setViewAdapter(new NumericWheelAdapter(context,START_YEAR, END_YEAR));// 设置"年"的显示数据
        wv_year.setCyclic(true);// 可循环滚动
//        wv_year.setLabel("年");// 添加文字
        wv_year.setCurrentItem(year - START_YEAR);// 初始化时显示的数据

        // 月
        wv_month.setViewAdapter(new NumericWheelAdapter(context,1, 12));
        wv_month.setCyclic(true);
//        wv_month.setLabel("月");
        wv_month.setCurrentItem(month);

        // 日
        wv_day.setCyclic(true);
        // 判断大小月及是否闰年,用来确定"日"的数据
        if (list_big.contains(String.valueOf(month + 1))) {
            wv_day.setViewAdapter(new NumericWheelAdapter(context,1, 31));
        } else if (list_little.contains(String.valueOf(month + 1))) {
            wv_day.setViewAdapter(new NumericWheelAdapter(context,1, 30));
        } else {//2月单独处理
            if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)// 闰年
                wv_day.setViewAdapter(new NumericWheelAdapter(context,1, 29));
            else
                wv_day.setViewAdapter(new NumericWheelAdapter(context,1, 28));
        }
//        wv_day.setLabel("日");
        wv_day.setCurrentItem(day - 1);

        wv_hours.setViewAdapter(new NumericWheelAdapter(context,0, 23));
        wv_hours.setCyclic(true);// 可循环滚动
//        wv_hours.setLabel("时");// 添加文字
        wv_hours.setCurrentItem(hour);

        wv_mins.setViewAdapter(new NumericWheelAdapter(context,0, 59));
        wv_mins.setCyclic(true);// 可循环滚动
//        wv_mins.setLabel("分");// 添加文字
        wv_mins.setCurrentItem(min);

        wv_year.addChangingListener(wheelListener_year);
        wv_month.addChangingListener(wheelListener_month);
        switch (displayType) {
            case YEAR:
                wv_month.setVisibility(View.GONE);
                wv_day.setVisibility(View.GONE);
                wv_hours.setVisibility(View.GONE);
                wv_mins.setVisibility(View.GONE);
                break;
            case MONTH:
                wv_year.setVisibility(View.GONE);
                wv_day.setVisibility(View.GONE);
                wv_hours.setVisibility(View.GONE);
                wv_mins.setVisibility(View.GONE);
                break;
            case DAY:
                wv_year.setVisibility(View.GONE);
                wv_month.setVisibility(View.GONE);
                wv_hours.setVisibility(View.GONE);
                wv_mins.setVisibility(View.GONE);
                break;
            case HOUR:
                wv_year.setVisibility(View.GONE);
                wv_month.setVisibility(View.GONE);
                wv_day.setVisibility(View.GONE);
                wv_mins.setVisibility(View.GONE);
                break;
            case MIN:
                wv_year.setVisibility(View.GONE);
                wv_month.setVisibility(View.GONE);
                wv_day.setVisibility(View.GONE);
                wv_hours.setVisibility(View.GONE);
                break;
            case YEAR_MONTH:
                wv_day.setVisibility(View.GONE);
                wv_hours.setVisibility(View.GONE);
                wv_mins.setVisibility(View.GONE);
                break;
            case HOUR_MIN:
                wv_year.setVisibility(View.GONE);
                wv_month.setVisibility(View.GONE);
                wv_day.setVisibility(View.GONE);
                break;
            case YEAR_MONTH_DAY:
                wv_hours.setVisibility(View.GONE);
                wv_mins.setVisibility(View.GONE);
                break;
            case YEAR_MONTH_DAY_HOUR_MIN:

                break;
        }
        int textSize = (screenHeight / 100) * 3;
//        wv_day.TEXT_SIZE = textSize;
//        wv_month.TEXT_SIZE = textSize;
//        wv_year.TEXT_SIZE = textSize;
//        wv_hours.TEXT_SIZE = textSize;
//        wv_mins.TEXT_SIZE = textSize;
    }

    public int getYear() {
        return wv_year.getCurrentItem() + START_YEAR;
    }

    /**
     * 是1-12的中国月
     * @return
     */
    public int getMonth() {
        return wv_month.getCurrentItem() + 1;
    }

    public int getDay() {
        return wv_day.getCurrentItem() + 1;
    }

    public int getHour() {
        return wv_hours.getCurrentItem();
    }

    public int getMin() {
        return wv_mins.getCurrentItem();
    }
}
