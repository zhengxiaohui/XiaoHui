package com.zbase.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.zbase.R;
import com.zbase.listener.OnActionDownLongListener;
import com.zbase.listener.OnActionUpListener;
import com.zbase.listener.OnTimeEndListener;

/**
 * 创建人：郑晓辉
 * 创建日期：2016/12/6
 * 描述：监听长按事件和抬起事件的LinearLayout布局
 */
public class LongClickLinearLayout extends LinearLayout {

    private boolean isLongClick;//是否触发长按状态
    private int maxPressSecond;//最大长按秒数
    private int currentPressSecond;//当前长按秒数
    private OnActionDownLongListener onActionDownLongListener;
    private OnActionUpListener onActionUpListener;
    private OnTimeEndListener onTimeEndListener;

    public void setOnActionDownLongListener(OnActionDownLongListener onActionDownLongListener) {
        this.onActionDownLongListener = onActionDownLongListener;
    }

    public void setOnActionUpListener(OnActionUpListener onActionUpListener) {
        this.onActionUpListener = onActionUpListener;
    }

    public void setOnTimeEndListener(OnTimeEndListener onTimeEndListener) {
        this.onTimeEndListener = onTimeEndListener;
    }

    public LongClickLinearLayout(Context context) {
        super(context);
        init(context, null, 0);
    }

    public LongClickLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public LongClickLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        setOrientation(HORIZONTAL);
        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.LongClickLinearLayout, defStyle, 0);
        maxPressSecond = a.getInt(R.styleable.LongClickLinearLayout_maxPressSecond, 0);
        a.recycle();
        loadViews();
    }

    private void loadViews() {
        setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                isLongClick = true;//长按按钮触发成功，执行相应代码
                setSelected(true);//不用setPress()的原因是当弹出popupwindow的时候会失去焦点导致失效（自动变成false）
                if (maxPressSecond != 0) {
                    currentPressSecond = maxPressSecond;
                    handler.sendEmptyMessageDelayed(0, 1000);
                }
                if (onActionDownLongListener != null) {
                    onActionDownLongListener.onActionDownLong(v);
                }
                return true;
            }
        });

        setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (isLongClick)//判断是长按后的抬起，排除短按后的抬起
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_UP://松开按钮，执行相应代码
                            stopLongClick();
                            if (onActionUpListener != null) {
                                onActionUpListener.onActionUp(v);
                            }
                            break;
                        default:
                            break;
                    }
                return false;
            }
        });
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    if (currentPressSecond > 1) {
                        currentPressSecond--;
                        handler.sendEmptyMessageDelayed(0, 1000);
                    } else {
                        stopLongClick();
                        if (onTimeEndListener != null) {
                            onTimeEndListener.onTimeEnd();
                        }
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    };

    private void stopLongClick() {
        isLongClick = false;
        setSelected(false);
        currentPressSecond = maxPressSecond;
        if (handler.hasMessages(0)) {
            handler.removeMessages(0);
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

    public int getMaxPressSecond() {
        return maxPressSecond;
    }

    public void setMaxPressSecond(int maxPressSecond) {
        this.maxPressSecond = maxPressSecond;
    }
}
