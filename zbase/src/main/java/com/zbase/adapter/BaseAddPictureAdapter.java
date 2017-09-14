package com.zbase.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zbase.listener.ItemClickListener;

/**
 * 创建人：郑晓辉
 * 创建日期：2016/7/88
 * 描述：添加图片GridView的抽象适配器，子类继承实现具体的布局
 */
public abstract class BaseAddPictureAdapter extends ZBaseAdapterAdvance<String> {

    private int maxCount = 9;
    private ItemClickListener deleteItemClickListener;//点击删除图片按钮控件
    private ItemClickListener addItemClickListener;//点击添加图片按钮控件

    public int getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }

    public void setDeleteItemClickListener(ItemClickListener deleteItemClickListener) {
        this.deleteItemClickListener = deleteItemClickListener;
    }

    public void setAddItemClickListener(ItemClickListener addItemClickListener) {
        this.addItemClickListener = addItemClickListener;
    }

    public BaseAddPictureAdapter(Context context) {
        super(context);
    }

    @Override
    protected ViewHolder createViewHolder() {
        return new MyViewHolder();
    }

    @Override
    public int getCount() {
        if (list.size() >= maxCount) {
            return list.size();
        } else {
            return list.size() + 1;
        }
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (position == list.size()) {
            MyViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = (MyViewHolder) createViewHolder();
                convertView = LayoutInflater.from(context).inflate(inflateMainLayoutId(), parent, false);
                viewHolder.findView(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (MyViewHolder) convertView.getTag();
            }
            viewHolder.setListener(position);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null) {
                        itemClickListener.onItemClick(v, position);
                    }
                }
            });
            viewHolder.imageView.setVisibility(View.GONE);
            viewHolder.iv_delete.setVisibility(View.GONE);
            viewHolder.iv_add.setVisibility(View.VISIBLE);
            return convertView;
        } else {
            return super.getView(position, convertView, parent);
        }
    }

    private class MyViewHolder extends ViewHolder {
        ImageView imageView;
        ImageView iv_delete;
        ImageView iv_add;

        @Override
        protected void findView(View view) {
            imageView = (ImageView) view.findViewById(findPictureImageViewId());
            iv_delete = (ImageView) view.findViewById(findDeleteImageViewId());
            iv_add = (ImageView) view.findViewById(findAddImageViewId());
        }

        @Override
        protected void setListener(final int position) {
            iv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (deleteItemClickListener != null) {
                        deleteItemClickListener.onItemClick(v, position);
                    }
                }
            });
            iv_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (addItemClickListener != null) {
                        addItemClickListener.onItemClick(v, position);
                    }
                }
            });
        }

        @Override
        protected void initValue(int position, String uri) {
            imageView.setVisibility(View.VISIBLE);
            iv_delete.setVisibility(View.VISIBLE);
            iv_add.setVisibility(View.GONE);
            Glide.with(context).load(uri).into(imageView);
        }
    }

    public abstract int findPictureImageViewId();

    public abstract int findDeleteImageViewId();

    public abstract int findAddImageViewId();

}
