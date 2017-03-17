package com.zbase.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 创建人：郑晓辉
 * 创建日期：2016/4/13
 * 描述：ZBaseAdapter的改进型
 */
public abstract class ZBaseAdapterAdvance<T> extends ZBaseAdapter<T> {

    public ZBaseAdapterAdvance(Context context) {
        super(context);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = createViewHolder();
            convertView = LayoutInflater.from(context).inflate(inflateMainLayoutId(), parent, false);
            viewHolder.findView(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (list.size() > 0) {
            viewHolder.setListener(position);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null) {
                        itemClickListener.onItemClick(v, position);
                    }
                }
            });
            T t = list.get(position);
            if (t != null) {
                viewHolder.initValue(position, t);
            }
        }
        return convertView;
    }

    protected abstract int inflateMainLayoutId();

    protected abstract ViewHolder createViewHolder();


    public abstract class ViewHolder {
        protected abstract void findView(View view);

        protected abstract void setListener(int position);

        protected abstract void initValue(int position, T t);
    }
}
