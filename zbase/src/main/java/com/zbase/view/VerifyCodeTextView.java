package com.zbase.view;

import android.content.Context;
import android.util.AttributeSet;

import com.zbase.enums.DirectionEnum;
import com.zbase.listener.OnTimeEndListener;

/**
 * 创建人：郑晓辉
 * 创建日期：2017/3/3
 * 描述：60秒倒计时验证码控件，继承TimerTextView
 * 用法：
 private VerifyCodeTextView verifyCodeTextView;

 verifyCodeTextView=(VerifyCodeTextView)view.findViewById(R.id.verifyCodeTextView);

 verifyCodeTextView.setOnClickListener(this);

 switch (v.getId()) {
 case R.id.verifyCodeTextView:
 requestDemo();
 break;
 }

 网络请求返回成功中调用verifyCodeTextView.start();
 Activity销毁生命周期onDestroy()中调用verifyCodeTextView.onDestroy();
 */
public class VerifyCodeTextView extends TimerTextView {

    public VerifyCodeTextView(Context context) {
        super(context);
        init();
    }

    public VerifyCodeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VerifyCodeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        setMaxSecond(60);
        setDirectionEnum(DirectionEnum.REVERSE);
        setJointText("%s");
        setOnTimeEndListener(new OnTimeEndListener() {
            @Override
            public void onTimeEnd() {
                setText(getOriginalText());
                setEnabled(true);
            }
        });
    }

    @Override
    public void start() {
        super.start();
        setEnabled(false);
    }
}
