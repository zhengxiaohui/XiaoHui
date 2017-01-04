package com.zdemo.activity;

import android.view.View;
import android.widget.CompoundButton;

import com.kyleduo.switchbutton.SwitchButton;
import com.zdemo.R;

/**
 * 创建人：郑晓辉
 * 创建日期：2016/12/22
 * 描述：SwitchButton滑动开关
 */
public class SwitchButtonActivity extends BaseActivity {

    private SwitchButton mFlymeSb, mMiuiSb, mCustomSb;

    @Override
    protected int inflateMainLayoutId() {
        return R.layout.activity_switch_button;
    }

    @Override
    protected void initView(View view) {
        mFlymeSb = (SwitchButton) findViewById(R.id.sb_custom_flyme);
        mMiuiSb = (SwitchButton) findViewById(R.id.sb_custom_miui);
        mCustomSb = (SwitchButton) findViewById(R.id.sb_custom);
    }

    @Override
    protected void setListener() {
        mFlymeSb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });
        mMiuiSb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });
        mCustomSb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });
    }

    @Override
    protected void initValue() {

    }

    @Override
    public void onClick(View v) {

    }
}
