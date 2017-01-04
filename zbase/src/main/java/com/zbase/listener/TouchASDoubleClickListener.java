package com.zbase.listener;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

/**
 * 自定义双击事件
 */
public class TouchASDoubleClickListener implements OnTouchListener {
	// 计算点击的次数
	private int count = 0;
	// 第一次点击的时间 long型
	private long firstClick = 0;
	// 最后一次点击的时间
	private long lastClick = 0;
	private DoubleClickListener doubleClickListener;

    public void setDoubleClickListener(DoubleClickListener doubleClickListener) {
        this.doubleClickListener = doubleClickListener;
    }

    @Override
	public boolean onTouch(View v, MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			// 如果第二次点击 距离第一次点击时间过长 那么将第二次点击看为第一次点击
			if (firstClick != 0
					&& System.currentTimeMillis() - firstClick > 500) {
				count = 0;
			}
			count++;
			if (count == 1) {
				firstClick = System.currentTimeMillis();
			} else if (count == 2) {
				lastClick = System.currentTimeMillis();
				// 两次点击小于500ms 也就是连续点击
				if (lastClick - firstClick < 500) {
                    doubleClickListener.onDoubleClick(v);
				}
				clear();
			}
		}

		return false;
	}

	// 清空状态
	private void clear() {
		count = 0;
		firstClick = 0;
		lastClick = 0;
	}
}
