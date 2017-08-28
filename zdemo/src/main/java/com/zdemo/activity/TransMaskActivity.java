package com.zdemo.activity;

import android.view.View;

import com.zbase.view.VerifyCodeTextView;
import com.zdemo.R;

/**
 * 创建人：郑晓辉
 * 创建日期：2017/1/9
 * 描述：按下显示半透明遮罩控件
 */
public class TransMaskActivity extends BaseActivity {

    private VerifyCodeTextView verifyCodeTextView;

    @Override
    protected int inflateMainLayoutId() {
        return R.layout.activity_trans_mask;
    }

    @Override
    protected void initView(View view) {
        verifyCodeTextView=(VerifyCodeTextView)view.findViewById(R.id.verifyCodeTextView);
    }

    @Override
    protected void setListener() {
        verifyCodeTextView.setOnClickListener(this);
    }

    @Override
    protected void initValue() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.verifyCodeTextView:
                verifyCodeTextView.start();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        verifyCodeTextView.onDestroy();
    }
}
