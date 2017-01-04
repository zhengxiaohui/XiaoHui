package com.zdemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.oranllc.kanwheel.custom.ChineseAreaPicker;
import com.zdemo.R;

/**
 * Created by 郑晓辉 on 2015/3/23
 * email:378180078@qq.com
 * 摘要：
 * 详细说明：
 * 使用示例：
 */
public class AreaCNWheelActivity extends Activity {

    private TextView tvContent;
    private ChineseAreaPicker mPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_areacn_wheel);
        init();
    }

    private void init(){
        tvContent = (TextView) findViewById(R.id.tvContent);
        mPicker=new ChineseAreaPicker(this,tvContent);
        findViewById(R.id.linearLayout1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPicker.onClick();
            }
        });
    }
}
