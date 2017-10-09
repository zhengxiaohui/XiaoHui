package com.zdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.zbase.adapter.ZBaseRecyclerAdapter;
import com.zdemo.R;

/**
 * 创建人：郑晓辉
 * 创建日期：2017/9/15 0015
 * 描述：
 */
public class GameLittleAdapter extends ZBaseRecyclerAdapter<String> {

    public GameLittleAdapter(Context context) {
        super(context);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent) {
        return new MyViewHolder(inflate(R.layout.adapter_game_little, parent));
    }

    public class MyViewHolder extends ItemViewHolder {

        private ImageView iv_pic;


        public MyViewHolder(View view) {
            super(view);
            iv_pic = (ImageView) view.findViewById(R.id.iv_pic);
        }

        @Override
        protected void setListener(int position) {

        }

        @Override
        protected void initValue(int position, String string) {

        }
    }

}
