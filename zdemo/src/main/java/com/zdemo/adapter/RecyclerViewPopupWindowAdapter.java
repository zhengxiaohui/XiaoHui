package com.zdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zbase.adapter.ZBaseRecyclerAdapter;
import com.zdemo.R;

/**
 * 创建人：郑晓辉
 * 创建日期：2015/12/30
 * 描述：
 */


public class RecyclerViewPopupWindowAdapter extends ZBaseRecyclerAdapter<String> {

    public RecyclerViewPopupWindowAdapter(Context context) {
        super(context);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent) {
        return new MyViewHolder(inflate(R.layout.adapter_listview_popupwindow, parent));
    }

    public class MyViewHolder extends ItemViewHolder {
        TextView tv_content;

        public MyViewHolder(View view) {
            super(view);
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


