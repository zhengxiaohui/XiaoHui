package com.zdemo.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zbase.adapter.BaseSlidingMenuAdapter;
import com.zdemo.R;

/**
 * 创建人：郑晓辉
 * 创建日期：2015/12/14
 * 描述：
 */
public class DemoSlidingMenuAdapter extends BaseSlidingMenuAdapter<String> {


    public DemoSlidingMenuAdapter(Context context) {
        super(context);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent) {
        return new MyViewHolder(inflate(R.layout.adapter_sliding_menu, parent));
    }

    public class MyViewHolder extends SlidingMenuItemViewHolder {

        TextView tv_delete;

        public MyViewHolder(View view) {
            super(view);
            tv_delete = (TextView) view.findViewById(R.id.tv_delete);
        }

        @Override
        protected int findSlidingMenuId() {
            return R.id.slidingMenu;
        }

        @Override
        protected void setListener(int position) {
            tv_delete.setTag(R.id.tv_delete, position);
            tv_delete.setOnClickListener(onClickListener);
        }

        @Override
        protected void initValue(int position, String string) {

        }
    }

}
