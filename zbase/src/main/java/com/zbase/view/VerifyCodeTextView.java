package com.zbase.view;

import android.content.Context;
import android.util.AttributeSet;

import com.zbase.enums.DirectionEnum;
import com.zbase.listener.OnTimeEndListener;

/**
 * 创建人：郑晓辉
 * 创建日期：2017/3/3
 * 描述：60秒倒计时验证码控件，继承TimerTextView
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
