package com.zbase.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

import com.zbase.R;
import com.zbase.enums.DirectionEnum;
import com.zbase.enums.RunStateEnum;
import com.zbase.listener.OnTimeEndListener;

/**
 * 创建人：郑晓辉
 * 创建日期：2016/12/15
 * 描述：计时TextView控件，单位秒，可以正计时，也可以倒计时。拥有功能：开始，停止，暂停，继续，时间结束回调
 */
public class TimerTextView extends TextView {

    private int maxSecond;//最大秒数
    private int currentSecond;//当前秒数
    private int pauseSecond;//暂停时的秒数
    private OnTimeEndListener onTimeEndListener;
    private String originalText;//原始文本，就是android:text=""中设置的
    private String jointText;//倒计时的时候显示的拼接文本，必须包含%作为通配符例如：还剩%秒>还剩5秒
    private RunStateEnum runStateEnum = RunStateEnum.STOP;//状态STOP/START/PAUSE/CONTINUE>0/1/2/3>停止/开始/暂停/继续
    private DirectionEnum directionEnum = DirectionEnum.FORWARD;//正计时还是倒计时，默认是正计时

    public void setOnTimeEndListener(OnTimeEndListener onTimeEndListener) {
        this.onTimeEndListener = onTimeEndListener;
    }

    public int getMaxSecond() {
        return maxSecond;
    }

    public void setMaxSecond(int maxSecond) {
        this.maxSecond = maxSecond;
    }

    public String getJointText() {
        return jointText;
    }

    public void setJointText(String jointText) {
        this.jointText = jointText;
    }

    public TimerTextView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public TimerTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public TimerTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.TimerTextView, defStyle, 0);
        int direction = a.getInt(R.styleable.TimerTextView_directionEnum, 0);
        switch (direction) {
            case 0:
                directionEnum = DirectionEnum.FORWARD;
                break;
            case 1:
                directionEnum = DirectionEnum.REVERSE;
                break;
        }
        maxSecond = a.getInt(R.styleable.TimerTextView_maxSecond, 0);
        jointText = a.getString(R.styleable.TimerTextView_jointText);
        a.recycle();
        loadViews();
    }

    private void loadViews() {
        originalText = getText().toString().trim();
    }

    public void start() {
        if (runStateEnum != RunStateEnum.STOP) {
            return;
        }
        runStateEnum = RunStateEnum.START;
        switch (directionEnum) {
            case FORWARD:
                currentSecond = 0;
                break;
            case REVERSE:
                currentSecond = maxSecond;
                break;
        }
//        setFormatText();
        handler.sendEmptyMessageDelayed(0, 1000);
    }

    public void pause() {
        if (runStateEnum != RunStateEnum.START && runStateEnum != RunStateEnum.CONTINUE) {
            return;
        }
        if (handler.hasMessages(0)) {
            handler.removeMessages(0);
        }
        runStateEnum = RunStateEnum.PAUSE;
        pauseSecond = currentSecond;
    }

    public void continueTo() {
        if (runStateEnum != RunStateEnum.PAUSE) {
            return;
        }
        runStateEnum = RunStateEnum.CONTINUE;
        currentSecond = pauseSecond;
        handler.sendEmptyMessageDelayed(0, 1000);
    }

    /**
     * 跳转到第几秒，不能大于最大秒数，continueTo的变种，还没测试
     *
     * @param second
     */
    public void seekTo(int second) {
        if (runStateEnum != RunStateEnum.PAUSE) {
            return;
        }
        if (second > maxSecond) {
            return;
        }
        runStateEnum = RunStateEnum.CONTINUE;
        currentSecond = second;
        handler.sendEmptyMessageDelayed(0, 1000);
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    switch (directionEnum) {
                        case FORWARD://正计时
                            if (currentSecond < maxSecond) {
                                currentSecond++;
                                setFormatText();
                                handler.sendEmptyMessageDelayed(0, 1000);
                            } else {
                                onTimeEnd();
                            }
                            break;
                        case REVERSE://倒计时
                            if (currentSecond > 1) {
                                currentSecond--;
                                setFormatText();
                                handler.sendEmptyMessageDelayed(0, 1000);
                            } else {
                                onTimeEnd();
                            }
                            break;
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    };

    private void onTimeEnd() {
        stop();
        if (onTimeEndListener != null) {
            onTimeEndListener.onTimeEnd();
        }
    }

    /**
     * 把jointText中的"%"替换成需要当前秒数currentSecond
     */
    private void setFormatText() {
        if (!TextUtils.isEmpty(jointText) && jointText.contains("%")) {
            String formatText = jointText.replace("%", String.valueOf(currentSecond));
            setText(formatText);
        } else {
            setText(String.valueOf(currentSecond));
        }
    }

    public void stop() {
        if (runStateEnum == RunStateEnum.STOP) {
            return;
        }
        if (handler.hasMessages(0)) {
            handler.removeMessages(0);
        }
        runStateEnum = RunStateEnum.STOP;
        setText(originalText);
        switch (directionEnum) {
            case FORWARD:
                currentSecond = 0;
                break;
            case REVERSE:
                currentSecond = maxSecond;
                break;
        }
    }

    /**
     * Activity或Fragment销毁onDestroy()中调用
     */
    public void onDestroy() {
        if (handler.hasMessages(0)) {
            handler.removeMessages(0);
        }
    }
}
