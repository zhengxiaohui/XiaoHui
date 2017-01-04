package com.zbase.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.TextView;


/**
 * 创建人：郑晓辉
 * 创建日期：2016/7/13
 * 描述：验证码60秒倒计时TextView控件
 * 注意：宽度最好设置成固定dp，如果是wrap会因为文字变少而宽度减少
 */
public class VerifyCodeTextView extends TextView {

    private static final int TOTAL_TIME = 59;//总时间60秒,59-0
    private int total = TOTAL_TIME;
    private int intervalTime = 1000;//间隔时间1秒
    private String defaultText;//默认显示的文字

    public interface OnVerifyCodeStateListener {

        void onStart();

        void onStop();
    }

    private OnVerifyCodeStateListener onVerifyCodeStateListener;

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

    private void init() {
        defaultText = getText().toString();
    }

    public void start() {
        start(null);
    }

    public void start(OnVerifyCodeStateListener onVerifyCodeStateListener) {
        setEnabled(false);
        setText(total + "s");
        handler.sendEmptyMessageDelayed(0, intervalTime);
        if (onVerifyCodeStateListener != null) {
            onVerifyCodeStateListener.onStart();
        }
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    if (total == 0) {
                        reset();
                        return;
                    }
                    total--;
                    setText(total + "s");
                    handler.sendEmptyMessageDelayed(0, intervalTime);
                    break;
            }
            super.handleMessage(msg);
        }
    };

    /**
     * 重置控件状态
     */
    public void reset() {
        total = TOTAL_TIME;
        setText(defaultText);
        setEnabled(true);
        if (onVerifyCodeStateListener != null) {
            onVerifyCodeStateListener.onStop();
        }
        handler.removeMessages(0);
    }
}
