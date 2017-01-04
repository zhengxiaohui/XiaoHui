package com.zdemo.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.karics.library.zxing.my.QRCodeManager;
import com.zdemo.R;

/**
 * 创建人：郑晓辉
 * 创建日期：2016/9/13
 * 描述：
 */
public class QRCodeActivity extends BaseActivity {

    private TextView textView;
    private Button button;

    @Override
    protected int inflateMainLayoutId() {
        return R.layout.activity_qrcode;
    }

    @Override
    protected void initView(View view) {
        textView = (TextView) view.findViewById(R.id.textView);
        button = (Button) view.findViewById(R.id.button);
    }

    @Override
    protected void setListener() {
        button.setOnClickListener(this);
    }

    @Override
    protected void initValue() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                QRCodeManager.scan(this);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        QRCodeManager.onActivityResult(requestCode, resultCode, data, new QRCodeManager.OnQRCodeObtainListener() {
            @Override
            public void onQRCodeObtain(String content) {
                textView.setText(content);
            }
        });
    }
}
