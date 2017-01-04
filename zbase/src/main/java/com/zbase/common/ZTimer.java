package com.zbase.common;

import android.os.Handler;
import android.os.Message;

import com.zbase.enums.DirectionEnum;
import com.zbase.enums.RunStateEnum;
import com.zbase.listener.OnProgressListener;
import com.zbase.listener.OnTimeEndListener;

/**
 * 创建人：郑晓辉
 * 创建日期：2016/12/19
 * 描述：计时ZTimer，解耦独立类，不和TextView等整合，可应用于更多的场景。单位秒，可以正计时，也可以倒计时。
 */
public class ZTimer {
    private int maxSecond;//最大秒数
    private int currentSecond;//当前秒数
    private int pauseSecond;//暂停时的秒数
    private OnProgressListener onProgressListener;
    private OnTimeEndListener onTimeEndListener;
    private RunStateEnum runStateEnum = RunStateEnum.STOP;//状态STOP/START/PAUSE/CONTINUE>0/1/2/3>停止/开始/暂停/继续
    private DirectionEnum directionEnum = DirectionEnum.FORWARD;//正计时还是倒计时，默认是正计时

    public void setOnProgressListener(OnProgressListener onProgressListener) {
        this.onProgressListener = onProgressListener;
    }

    public void setOnTimeEndListener(OnTimeEndListener onTimeEndListener) {
        this.onTimeEndListener = onTimeEndListener;
    }

    public ZTimer(DirectionEnum directionEnum,int maxSecond) {
        this.directionEnum = directionEnum;
        this.maxSecond=maxSecond;
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

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    switch (directionEnum) {
                        case FORWARD://正计时
                            if (currentSecond < maxSecond) {
                                currentSecond++;
                                onProgressListener.onProgress(currentSecond);
                                handler.sendEmptyMessageDelayed(0, 1000);
                            } else {
                                onTimeEnd();
                            }
                            break;
                        case REVERSE://倒计时
                            if (currentSecond > 1) {
                                currentSecond--;
                                onProgressListener.onProgress(currentSecond);
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

    public void stop() {
        if (runStateEnum == RunStateEnum.STOP) {
            return;
        }
        if (handler.hasMessages(0)) {
            handler.removeMessages(0);
        }
        runStateEnum = RunStateEnum.STOP;
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
