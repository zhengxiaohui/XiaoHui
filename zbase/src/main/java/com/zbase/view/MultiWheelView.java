package com.zbase.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.zbase.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建人：郑晓辉
 * 创建日期：2016/11/30
 * 描述：有多个WheelView滚轮的自定义布局LinearLayout
 */
public class MultiWheelView extends LinearLayout {

    private int wheelViewLayout;//单个WheelView的布局资源Id
    private List<WheelView> wheelViewList = new ArrayList<>();//定义多个WheelView

    public List<WheelView> getWheelViewList() {
        return wheelViewList;
    }

    public MultiWheelView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public MultiWheelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public MultiWheelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        setOrientation(HORIZONTAL);
        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.MultiWheelView, defStyle, 0);
        wheelViewLayout = a.getResourceId(R.styleable.MultiWheelView_wheelViewLayout, 0);
        a.recycle();
    }

    /**
     * 设置多个滚轮的数据集
     *
     * @param dataLists
     */
    public void setDataLists(List<List<String>> dataLists) {
        if (dataLists != null && dataLists.size() > 0) {
            for (int i = 0; i < dataLists.size(); i++) {
                WheelView wheelView = (WheelView) LayoutInflater.from(getContext()).inflate(wheelViewLayout, this, false);
                wheelView.setData(dataLists.get(i));
                wheelViewList.add(wheelView);
                addView(wheelView);
            }
        }
    }

    /**
     * 设置多个滚轮的单位名称
     *
     * @param unitList
     */
    public void setUnitList(List<String> unitList) {
        if (unitList != null && unitList.size() > 0) {
            if (unitList.size() != wheelViewList.size()) {
                throw new IndexOutOfBoundsException("单位个数和滚轮数据不匹配");
            }
            for (int i = 0; i < wheelViewList.size(); i++) {
                wheelViewList.get(i).setUnitText(unitList.get(i));
            }
        }
    }

    //得到多个滚轮的选中数据集合
    public List<String> getDataList() {
        List<String> textList = new ArrayList<>();
        for (int i = 0; i < wheelViewList.size(); i++) {
            textList.add(wheelViewList.get(i).getSelectedText());
        }
        return textList;
    }

    /**
     * 获取带连接符拼接的多个滚轮选中的总字符串
     *
     * @param symbol
     * @return
     */
    public String getConnectorString(String symbol) {
        String connectorString = "";
        for (int i = 0; i < wheelViewList.size(); i++) {
            if (i != wheelViewList.size() - 1) {
                connectorString = connectorString + wheelViewList.get(i).getSelectedText() + symbol;
            } else {
                connectorString = connectorString + wheelViewList.get(i).getSelectedText();
            }
        }
        return connectorString;
    }

}
