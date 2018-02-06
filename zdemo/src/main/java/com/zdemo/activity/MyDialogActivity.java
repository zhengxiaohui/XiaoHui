package com.zdemo.activity;

import android.util.Log;
import android.view.View;

import com.zbase.listener.OnCancelClickListener;
import com.zbase.listener.OnConfirmClickListener;
import com.zbase.view.CustomDialog;
import com.zdemo.R;

public class MyDialogActivity extends BaseActivity {

    @Override
    protected int inflateMainLayoutId() {
        return R.layout.my_dialog_main;
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected void setListener() {
        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                alertDialog3();
            }
        });

        findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                alertDialog4();
            }
        });
    }

    @Override
    protected void initValue() {

    }

    private void alertDialog3() {
        CustomDialog customDialog = new CustomDialog.Builder(context, inflate(R.layout.custom_dialog_content_confirm)).setContentId(R.id.tv_content).setConfirmId(R.id.tv_confirm).build();
        customDialog.setContent(R.string.umeng_socialize_text_tencent_version_no_match);
        customDialog.setConfirm(R.string.umeng_socialize_ucenter_login_title_platform);
        customDialog.setOnConfirmClickListener(new OnConfirmClickListener() {
            @Override
            public void onConfirmClick(View v) {
                Log.d("zheng", "点击了确定按钮");
            }
        });
        customDialog.show();
    }

    private void alertDialog4() {
        CustomDialog customDialog = new CustomDialog.Builder(context, inflate(R.layout.custom_dialog_title_content_confirm_cancel)).setTitleId(R.id.tv_title).setContentId(R.id.tv_content).setConfirmId(R.id.tv_confirm).setCancelId(R.id.tv_cancel).build();
        customDialog.setTitle(R.string.umeng_socialize_text_friend_list);
        customDialog.setContent(R.string.umeng_socialize_text_tencent_version_no_match);
        customDialog.setConfirm(R.string.umeng_socialize_ucenter_login_title_platform);
        customDialog.setCancel(R.string.umeng_socialize_back);
        customDialog.setOnConfirmClickListener(new OnConfirmClickListener() {
            @Override
            public void onConfirmClick(View v) {
                Log.d("zheng", "点击了确定按钮");
            }
        });
        customDialog.setOnCancelClickListener(new OnCancelClickListener() {
            @Override
            public void onCancelClick(View v) {
                Log.d("zheng", "点击了取消按钮");
            }
        });
        customDialog.show();
    }

    @Override
    public void onClick(View v) {

    }
}