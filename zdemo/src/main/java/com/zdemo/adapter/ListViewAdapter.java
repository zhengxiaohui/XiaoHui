package com.zdemo.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.zbase.adapter.ZBaseAdapterAdvance;
import com.zdemo.R;

/**
 * 创建人：郑晓辉
 * 创建日期：2015/12/30
 * 描述：
 */


public class ListViewAdapter extends ZBaseAdapterAdvance<String> {

    public ListViewAdapter(Context context) {
        super(context);
    }

    @Override
    protected int inflateMainLayoutId() {
        return R.layout.adapter_listview;
    }

    @Override
    protected ViewHolder createViewHolder() {
        return new MyViewHolder();
    }

    private class MyViewHolder extends ViewHolder {

        private ImageView imageView;

        @Override
        protected void findView(View view) {
            imageView = (ImageView) view.findViewById(R.id.imageView);
        }

        @Override
        protected void setListener(int position) {

        }

        @Override
        protected void initValue(int position, String string) {
            boolean select=selectList.get(position);
            if (select) {
                imageView.setImageResource(R.mipmap.ic_joker);
            } else {
                imageView.setImageBitmap(null);
            }
        }
    }

}


