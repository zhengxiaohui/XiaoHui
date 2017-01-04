package com.zbase.view.popupwindow;

import android.content.Context;
import android.view.View;

import com.zbase.R;
import com.zbase.enums.DateTimeLimitEnum;
import com.zbase.enums.DateTimeModelEnum;
import com.zbase.util.DateTimeUtil;
import com.zbase.util.PopUtil;
import com.zbase.util.WheelViewDataUtil;
import com.zbase.view.MultiWheelView;
import com.zbase.view.WheelView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 创建人：郑晓辉
 * 创建日期：2016/12/5
 * 描述：DateTimeWheelViewPopupWindow 日期时间控件
 * 可以自由设置年月日时分秒的多种组合
 */
public class DateTimeWheelViewPopupWindow extends MainStreamPopupWindow {

    private static final int MIN_YEAR = 1900;//最早年份，默认1900年
    private static final int MAX_YEAR = 2100;//最晚年份，默认2100年
    private Calendar selectCalendar;
    private Calendar currentCalendar;
    private int currentYear;
    private int currentMonth;
    private int currentDay;
    private int currentHour;
    private int currentMin;
    private DateTimeLimitEnum dateTimeLimitEnum;
    private DateTimeModelEnum dateTimeModelEnum;
    List<Integer> defaultIndexList = new ArrayList<>();//各个WheelView默认选中的索引,这里为当前日期时间作为默认值

    private DateTimeWheelViewPopupWindow(Context context, View view) {
        super(context, view);
    }

    private DateTimeWheelViewPopupWindow(Context context, View view, int width, int height) {
        super(context, view, width, height);
    }

    @Override
    public MultiWheelView getMainView() {
        return (MultiWheelView) super.getMainView();
    }

    /**
     * 初始化
     *
     * @param dateTimeLimit 时间限制
     * @param dateTimeModel 时间模式
     * @param hasUnit       是否显示单位
     */
    public void initDateTime(DateTimeLimitEnum dateTimeLimit, DateTimeModelEnum dateTimeModel, boolean hasUnit) {
        initDateTime(MIN_YEAR, MAX_YEAR, dateTimeLimit, dateTimeModel, hasUnit);
    }

    /**
     * @param minYear       最早年份
     * @param maxYear       最晚年份
     * @param dateTimeLimit 时间限制
     * @param dateTimeModel 时间模式
     * @param hasUnit       是否显示单位
     */
    public void initDateTime(int minYear, int maxYear, DateTimeLimitEnum dateTimeLimit, DateTimeModelEnum dateTimeModel, boolean hasUnit) {
        this.dateTimeLimitEnum = dateTimeLimit;
        this.dateTimeModelEnum = dateTimeModel;
        final MultiWheelView multiWheelView = getMainView();

        currentCalendar = Calendar.getInstance();
        currentYear = currentCalendar.get(Calendar.YEAR);
        currentMonth = currentCalendar.get(Calendar.MONTH) + 1;
        currentDay = currentCalendar.get(Calendar.DAY_OF_MONTH);
        currentHour = currentCalendar.get(Calendar.HOUR_OF_DAY);//注意不能用HOUR，那是12小时制
        currentMin = currentCalendar.get(Calendar.MINUTE);

        List<String> yearList = WheelViewDataUtil.getNMData(minYear, 1, maxYear, false, 4);
        List<String> monthList = WheelViewDataUtil.getNMData(0, 1, 12, true, 2);
        List<String> dayList = WheelViewDataUtil.getDayOfMonthData(currentYear, currentMonth);
        List<String> hourList = WheelViewDataUtil.getNMData(0, 1, 24, false, 2);
        List<String> minList = WheelViewDataUtil.getNMData(0, 1, 60, false, 2);
        List<List<String>> allDataLists = new ArrayList<>();//全部年月日时分的数据
        allDataLists.add(yearList);
        allDataLists.add(monthList);
        allDataLists.add(dayList);
        allDataLists.add(hourList);
        allDataLists.add(minList);
        List<String> allUnitList = new ArrayList<>();//全部年月日时分单位的数据
        allUnitList.add("年");
        allUnitList.add("月");
        allUnitList.add("日");
        allUnitList.add("时");
        allUnitList.add("分");
        List<Integer> allIndexList = new ArrayList<>();//所有WheelView默认选中的索引
        allIndexList.add(0);
        allIndexList.add(0);
        allIndexList.add(0);
        allIndexList.add(0);
        allIndexList.add(0);

        List<List<String>> dataLists = new ArrayList<>();
        List<String> unitList = new ArrayList<>();

        switch (dateTimeModel) {
            case YEAR:
                dataLists = allDataLists.subList(0, 1);
                unitList = allUnitList.subList(0, 1);
                defaultIndexList = allIndexList.subList(0, 1);
                defaultIndexList.set(0, yearList.indexOf(WheelViewDataUtil.formatZero(currentYear, 4)));
                break;
            case MONTH:
                dataLists = allDataLists.subList(1, 2);
                unitList = allUnitList.subList(1, 2);
                defaultIndexList = allIndexList.subList(1, 2);
                defaultIndexList.set(0, monthList.indexOf(WheelViewDataUtil.formatZero(currentMonth, 2)));
                break;
            case DAY:
                dataLists = allDataLists.subList(2, 3);
                unitList = allUnitList.subList(2, 3);
                defaultIndexList = allIndexList.subList(2, 3);
                defaultIndexList.set(0, dayList.indexOf(WheelViewDataUtil.formatZero(currentDay, 2)));
                break;
            case HOUR:
                dataLists = allDataLists.subList(3, 4);
                unitList = allUnitList.subList(3, 4);
                defaultIndexList = allIndexList.subList(3, 4);
                defaultIndexList.set(0, hourList.indexOf(WheelViewDataUtil.formatZero(currentHour, 2)));
                break;
            case MIN:
                dataLists = allDataLists.subList(4, 5);
                unitList = allUnitList.subList(4, 5);
                defaultIndexList = allIndexList.subList(4, 5);
                defaultIndexList.set(0, minList.indexOf(WheelViewDataUtil.formatZero(currentMin, 2)));
                break;
            case YEAR_MONTH:
                dataLists = allDataLists.subList(0, 2);
                unitList = allUnitList.subList(0, 2);
                defaultIndexList = allIndexList.subList(0, 2);
                defaultIndexList.set(0, yearList.indexOf(WheelViewDataUtil.formatZero(currentYear, 4)));
                defaultIndexList.set(1, monthList.indexOf(WheelViewDataUtil.formatZero(currentMonth, 2)));
                break;
            case MONTH_DAY:
                dataLists = allDataLists.subList(1, 3);
                unitList = allUnitList.subList(1, 3);
                defaultIndexList = allIndexList.subList(1, 3);
                defaultIndexList.set(0, monthList.indexOf(WheelViewDataUtil.formatZero(currentMonth, 2)));
                defaultIndexList.set(1, dayList.indexOf(WheelViewDataUtil.formatZero(currentDay, 2)));
                break;
            case HOUR_MIN:
                dataLists = allDataLists.subList(3, 5);
                unitList = allUnitList.subList(3, 5);
                defaultIndexList = allIndexList.subList(3, 5);
                defaultIndexList.set(0, hourList.indexOf(WheelViewDataUtil.formatZero(currentHour, 2)));
                defaultIndexList.set(1, minList.indexOf(WheelViewDataUtil.formatZero(currentMin, 2)));
                break;
            case YEAR_MONTH_DAY:
                dataLists = allDataLists.subList(0, 3);
                unitList = allUnitList.subList(0, 3);
                defaultIndexList = allIndexList.subList(0, 3);
                defaultIndexList.set(0, yearList.indexOf(WheelViewDataUtil.formatZero(currentYear, 4)));
                defaultIndexList.set(1, monthList.indexOf(WheelViewDataUtil.formatZero(currentMonth, 2)));
                defaultIndexList.set(2, dayList.indexOf(WheelViewDataUtil.formatZero(currentDay, 2)));
                break;
            case YEAR_MONTH_DAY_HOUR_MIN:
                dataLists = allDataLists.subList(0, 5);
                unitList = allUnitList.subList(0, 5);
                defaultIndexList = allIndexList.subList(0, 5);
                defaultIndexList.set(0, yearList.indexOf(WheelViewDataUtil.formatZero(currentYear, 4)));
                defaultIndexList.set(1, monthList.indexOf(WheelViewDataUtil.formatZero(currentMonth, 2)));
                defaultIndexList.set(2, dayList.indexOf(WheelViewDataUtil.formatZero(currentDay, 2)));
                defaultIndexList.set(3, hourList.indexOf(WheelViewDataUtil.formatZero(currentHour, 2)));
                defaultIndexList.set(4, minList.indexOf(WheelViewDataUtil.formatZero(currentMin, 2)));
                break;
        }
        multiWheelView.setDataLists(dataLists);

        switch (dateTimeModel) {//所有月日联动
            case MONTH_DAY:
                WheelView monthWheelView = multiWheelView.getWheelViewList().get(0);
                final WheelView dayWheelView = multiWheelView.getWheelViewList().get(1);
                monthWheelView.setOnSelectListener(new WheelView.OnSelectListener() {
                    @Override
                    public void endSelect(int id, final String text) {
                        dayWheelView.refreshData(WheelViewDataUtil.getDayOfMonthData(currentYear, Integer.parseInt(text)));
                    }

                    @Override
                    public void selecting(int id, String text) {

                    }
                });
                break;
            case YEAR_MONTH_DAY:
            case YEAR_MONTH_DAY_HOUR_MIN:
                final WheelView yWheelView = multiWheelView.getWheelViewList().get(0);
                final WheelView mWheelView = multiWheelView.getWheelViewList().get(1);
                final WheelView dWheelView = multiWheelView.getWheelViewList().get(2);
                yWheelView.setOnSelectListener(new WheelView.OnSelectListener() {
                    @Override
                    public void endSelect(int id, String text) {
                        dWheelView.refreshData(WheelViewDataUtil.getDayOfMonthData(Integer.parseInt(text), Integer.parseInt(mWheelView.getSelectedText())));
                    }

                    @Override
                    public void selecting(int id, String text) {

                    }
                });
                mWheelView.setOnSelectListener(new WheelView.OnSelectListener() {
                    @Override
                    public void endSelect(int id, String text) {
                        dWheelView.refreshData(WheelViewDataUtil.getDayOfMonthData(Integer.parseInt(yWheelView.getSelectedText()), Integer.parseInt(text)));
                    }

                    @Override
                    public void selecting(int id, String text) {

                    }
                });
                break;
        }

        if (hasUnit) {
            multiWheelView.setUnitList(unitList);
        }
        setDefault();
    }

    private void setDefault() {
        for (int i = 0; i < getMainView().getWheelViewList().size(); i++) {
            getMainView().getWheelViewList().get(i).setDefault(defaultIndexList.get(i));
        }
    }

    @Override
    protected void onConfirmClick(View v) {
        selectCalendar = Calendar.getInstance();//选中的日期时间
        switch (dateTimeModelEnum) {
            case YEAR:
                selectCalendar.set(Integer.parseInt(getMainView().getWheelViewList().get(0).getSelectedText()), currentMonth - 1, currentDay, currentHour, currentMin, 0);
                break;
            case MONTH:
                selectCalendar.set(currentYear, Integer.parseInt(getMainView().getWheelViewList().get(0).getSelectedText()) - 1, currentDay, currentHour, currentMin, 0);
                break;
            case DAY:
                selectCalendar.set(currentYear, currentMonth - 1, Integer.parseInt(getMainView().getWheelViewList().get(0).getSelectedText()), currentHour, currentMin, 0);
                break;
            case HOUR:
                selectCalendar.set(currentYear, currentMonth - 1, currentDay, Integer.parseInt(getMainView().getWheelViewList().get(0).getSelectedText()), currentMin, 0);
                break;
            case MIN:
                selectCalendar.set(currentYear, currentMonth - 1, currentDay, currentHour, Integer.parseInt(getMainView().getWheelViewList().get(0).getSelectedText()), 0);
                break;
            case YEAR_MONTH:
                selectCalendar.set(Integer.parseInt(getMainView().getWheelViewList().get(0).getSelectedText()), Integer.parseInt(getMainView().getWheelViewList().get(1).getSelectedText()) - 1, currentDay, currentHour, currentMin, 0);
                break;
            case MONTH_DAY:
                selectCalendar.set(currentYear, Integer.parseInt(getMainView().getWheelViewList().get(0).getSelectedText()) - 1, Integer.parseInt(getMainView().getWheelViewList().get(1).getSelectedText()), currentHour, currentMin, 0);
                break;
            case HOUR_MIN:
                selectCalendar.set(currentYear, currentMonth - 1, currentDay, Integer.parseInt(getMainView().getWheelViewList().get(0).getSelectedText()), Integer.parseInt(getMainView().getWheelViewList().get(1).getSelectedText()), 0);
                break;
            case YEAR_MONTH_DAY:
                selectCalendar.set(Integer.parseInt(getMainView().getWheelViewList().get(0).getSelectedText()), Integer.parseInt(getMainView().getWheelViewList().get(1).getSelectedText()) - 1, Integer.parseInt(getMainView().getWheelViewList().get(2).getSelectedText()), currentHour, currentMin, 0);
                break;
            case YEAR_MONTH_DAY_HOUR_MIN:
                selectCalendar.set(Integer.parseInt(getMainView().getWheelViewList().get(0).getSelectedText()), Integer.parseInt(getMainView().getWheelViewList().get(1).getSelectedText()) - 1, Integer.parseInt(getMainView().getWheelViewList().get(2).getSelectedText()), Integer.parseInt(getMainView().getWheelViewList().get(3).getSelectedText()), Integer.parseInt(getMainView().getWheelViewList().get(4).getSelectedText()), 0);
                break;
        }

        switch (dateTimeLimitEnum) {
            case ALL:
                super.onConfirmClick(v);
                break;
            case NO_BEFORE:
                Calendar amendCalendar = Calendar.getInstance();
                amendCalendar.setTime(currentCalendar.getTime());
                amendCalendar.set(Calendar.MINUTE, amendCalendar.get(Calendar.MINUTE) - 1);
                if (selectCalendar.before(amendCalendar)) {
                    PopUtil.toast(context, R.string.select_datetime_before);
                } else {
                    super.onConfirmClick(v);
                }
                break;
            case NO_AFTER:
                if (selectCalendar.after(currentCalendar)) {
                    PopUtil.toast(context, R.string.select_datetime_after);
                } else {
                    super.onConfirmClick(v);
                }
                break;
            case NO_BEFORE_DEFAULT:
                Calendar amendCalendarDefault = Calendar.getInstance();
                amendCalendarDefault.setTime(currentCalendar.getTime());
                amendCalendarDefault.set(Calendar.MINUTE, currentCalendar.get(Calendar.MINUTE) - 1);
                if (selectCalendar.before(amendCalendarDefault)) {
                    PopUtil.toast(context, R.string.select_datetime_before);
                    setDefault();
                } else {
                    super.onConfirmClick(v);
                }
                break;
            case NO_AFTER_DEFAULT:
                if (selectCalendar.after(currentCalendar)) {
                    PopUtil.toast(context, R.string.select_datetime_after);
                    setDefault();
                } else {
                    super.onConfirmClick(v);
                }
                break;
        }
    }

    /**
     * 这个返回格式一般是准备传给服务端的完整格式，显示一般不用这个，例如2016-12-06 11:49:00
     * 返回显示格式请调用MultiWheelView的getConnectorString或者getDataList自行拼接
     *
     * @return
     */
    public String getFormatDateTimeString() {
        return DateTimeUtil.dateToString(selectCalendar.getTime(), DateTimeUtil.YMDHMS);
    }

    public static class Builder extends MainStreamBuilder {

        public Builder(Context context, View view) {
            super(context, view);
            mainViewCancelPopupWindow = new DateTimeWheelViewPopupWindow(context, view);
        }

        public Builder(Context context, View view, int width, int height) {
            super(context, view, width, height);
            mainViewCancelPopupWindow = new DateTimeWheelViewPopupWindow(context, view, width, height);
        }
    }

}
