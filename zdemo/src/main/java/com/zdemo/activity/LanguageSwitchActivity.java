package com.zdemo.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.RadioGroup;

import com.zdemo.R;

import java.util.Locale;

/**
 * 创建人：郑晓辉
 * 创建日期：2016/12/28
 * 描述：切换语言环境
 */
public class LanguageSwitchActivity extends BaseActivity {

    private RadioGroup radioGroup;

    @Override
    protected int inflateMainLayoutId() {
        return R.layout.activity_language_switch;
    }

    @Override
    protected void initView(View view) {
        radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
    }

    @Override
    protected void setListener() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_1:
                        switchLanguage(Locale.SIMPLIFIED_CHINESE);
                        break;
                    case R.id.rb_2:
                        switchLanguage(Locale.TRADITIONAL_CHINESE);
                        break;
                    case R.id.rb_3:
                        switchLanguage(Locale.ENGLISH);
                        break;
                }
            }
        });
    }

    @Override
    protected void initValue() {

    }

    private void switchLanguage(Locale locale){
        Resources resources = getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        // 应用用户选择语言
        config.locale = locale;
        resources.updateConfiguration(config, dm);
        Intent intent = new Intent(this, LanguageSwitchActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {

    }
}
