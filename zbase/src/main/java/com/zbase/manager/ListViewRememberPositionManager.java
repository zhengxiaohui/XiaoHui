package com.zbase.manager;

import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.zbase.enums.Alignment;

/**
 * 创建人：郑晓辉
 * 创建日期：2017/3/8
 * 描述：ListView记住滚动位置管理器
 */
public class ListViewRememberPositionManager {

    private ListView listView;
    private Alignment alignment;//校准方式
    private int position;
    private int top;

    /**
     *
     * @param listView
     * @param alignment 校准方式：Alignment.EXACT 精确，Alignment.ALIGNING 对齐
     */
    public ListViewRememberPositionManager(ListView listView, Alignment alignment) {
        this.listView = listView;
        this.alignment = alignment;
        init();
    }

    private void init() {
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                // 不滚动时保存当前滚动到的位置
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    switch (alignment) {
                        case EXACT:
                            position = listView.getFirstVisiblePosition();
                            View v = listView.getChildAt(0);
                            top = (v == null) ? 0 : v.getTop();
                            break;
                        case ALIGNING:
                            position = listView.getFirstVisiblePosition();
                            break;
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    /**
     * 恢复滚动的位置
     */
    public void recoverPosition() {
        switch (alignment) {
            case EXACT:
                listView.setSelectionFromTop(position, top);
                break;
            case ALIGNING:
                listView.setSelection(position);
                break;
        }
    }
}
