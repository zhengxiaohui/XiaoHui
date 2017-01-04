package com.zdemo.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.zbase.adapter.ZBaseAdapterAdvance;
import com.zdemo.R;

/**
 * 创建人：郑晓辉
 * 创建日期：2015/12/30
 * 描述：
 */


public class ListViewPopupWindowAdapter extends ZBaseAdapterAdvance<String> {

    public ListViewPopupWindowAdapter(Context context) {
        super(context);
    }

    @Override
    protected int inflateMainLayoutId() {
        return R.layout.adapter_listview_popupwindow;
    }

    @Override
    protected ViewHolder createViewHolder() {
        return new MyViewHolder();
    }

    private class MyViewHolder extends ViewHolder {

        TextView tv_content;

        @Override
        protected void findView(View view) {
            tv_content = (TextView) view.findViewById(R.id.tv_content);
        }

        @Override
        protected void setListener(int position) {

        }

        @Override
        protected void initValue(int position, String string) {
            tv_content.setText(string);
        }
    }

}


