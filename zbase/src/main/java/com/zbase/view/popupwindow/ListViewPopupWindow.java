package com.zbase.view.popupwindow;

import android.content.Context;
import android.view.View;
import android.widget.ListView;

import com.zbase.adapter.ZBaseAdapterAdvance;
import com.zbase.enums.Alignment;
import com.zbase.manager.ListViewRememberPositionManager;

/**
 * 创建人：郑晓辉
 * 创建日期：2016/11/30
 * 描述：弹出ListViewPopupWindow
 * 注意:如果宽度不是充满屏幕，则ListView的宽度要设置成固定值多少dp,Adapter的TextView的宽度要设置成Match
 */
public class ListViewPopupWindow extends MainStreamPopupWindow {

    private ListViewRememberPositionManager listViewRememberPositionManager;

    private ListViewPopupWindow(Context context, View view) {
        super(context, view);
    }

    private ListViewPopupWindow(Context context, View view, int width, int height) {
        super(context, view, width, height);
    }

    public void setAdapter(ZBaseAdapterAdvance zBaseAdapterAdvance) {
        if (mainView instanceof ListView) {
            ListView listView = (ListView) mainView;
            if (listView != null) {
                listView.setVerticalScrollBarEnabled(false);
                listView.setAdapter(zBaseAdapterAdvance);
                listViewRememberPositionManager = new ListViewRememberPositionManager(listView, Alignment.EXACT);
            }
        }
    }

    /**
     * ListView恢复滚动的位置
     */
    public void recoverPosition() {
        listViewRememberPositionManager.recoverPosition();
    }

    @Override
    public ListView getMainView() {
        return (ListView) super.getMainView();
    }

    public static class Builder extends MainStreamBuilder {

        public Builder(Context context, View view) {
            super(context, view);
            mainViewCancelPopupWindow = new ListViewPopupWindow(context, view);
        }

        public Builder(Context context, View view, int width, int height) {
            super(context, view, width, height);
            mainViewCancelPopupWindow = new ListViewPopupWindow(context, view, width, height);
        }
    }

}
