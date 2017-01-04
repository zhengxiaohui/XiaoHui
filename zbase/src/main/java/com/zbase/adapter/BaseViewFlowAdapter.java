package com.zbase.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 创建人：郑晓辉
 * 创建日期：2016/8/30
 * 描述：幻灯片循环播放ViewFlow适配器的抽象类
 */
public abstract class BaseViewFlowAdapter<T> extends ZBaseAdapterAdvance<T> {

    public BaseViewFlowAdapter(Context context) {
        super(context);
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
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
            final int circulatePosition = position % list.size();
            viewHolder.setListener(circulatePosition);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null) {
                        itemClickListener.onItemClick(v, circulatePosition);
                    }
                }
            });
            T t = list.get(circulatePosition);
            if (t != null) {
                viewHolder.initValue(circulatePosition, t);
            }
        }
        return convertView;
    }

}
