package com.zdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zbase.adapter.ZBaseRecyclerAdapter;
import com.zdemo.R;

/**
 * 创建人：郑晓辉
 * 创建日期：2016/6/23
 * 描述：
 */
public class MultiImageSelectorAdapter extends ZBaseRecyclerAdapter<String> {

    private ImageView imageView;

    public MultiImageSelectorAdapter(Context context) {
        super(context);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent) {
        return new MyViewHolder(inflate(R.layout.adapter_multi_image_selector, parent));
    }

    public class MyViewHolder extends ItemViewHolder {


        public MyViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.imageView);
        }

        @Override
        protected void setListener(int position) {
            imageView.setTag(R.id.imageView,position);
            imageView.setOnClickListener(onClickListener);
        }

        @Override
        protected void initValue(int position, String uri) {
            Glide.with(context).load(uri).into(imageView);
        }
    }

}
