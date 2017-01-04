package com.zdemo.view;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.zdemo.R;

/**
 * 创建人：郑晓辉
 * 创建日期：2016/12/29
 * 描述：MPAndroidChart使用的点击显示框
 */
public class CustomMarkerView extends MarkerView {

    private TextView tv_time;
    private TextView tv_content;
    private String[] xTexts;
    private String unitText;

    public CustomMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);
        // this markerview only displays a textview
        tv_time = (TextView) findViewById(R.id.tv_time);
        tv_content = (TextView) findViewById(R.id.tv_content);
    }

    public CustomMarkerView(Context context, int layoutResource, String[] xTexts, String unitText) {
        this(context, layoutResource);
        this.xTexts = xTexts;
        this.unitText=unitText;
    }

    // callbacks everytime the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        if (xTexts != null) {
            tv_time.setText(xTexts[e.getXIndex()]);
            tv_content.setText(unitText+" "+e.getVal()); // set the entry-value as the display text
        } else {
            tv_content.setText("" + e.getVal()); // set the entry-value as the display text
        }
    }

    @Override
    public int getXOffset(float xpos) {
        // this will center the marker-view horizontally
        return -(getWidth() / 2);
    }

    @Override
    public int getYOffset(float ypos) {
        // this will cause the marker-view to be above the selected value
        return -getHeight()*2;
    }
}
