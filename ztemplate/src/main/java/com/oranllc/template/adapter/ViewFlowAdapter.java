package com.oranllc.template.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.oranllc.template.R;
import com.zbase.adapter.BaseViewFlowAdapter;
import com.zbase.util.ImageUtil;

import java.util.ArrayList;

/**
 * 创建人：郑晓辉
 * 创建日期：2017/2/17
 * 描述：图片轮播通用适配器，可以根据需求复制并修改
 */
public class ViewFlowAdapter extends BaseViewFlowAdapter<String> {

    public ViewFlowAdapter(Context context) {
        super(context);
    }

    @Override
    protected int inflateMainLayoutId() {
        return R.layout.adapter_viewflow;
    }

    @Override
    protected ViewHolder createViewHolder() {
        return new MyViewHolder();
    }

    private class MyViewHolder extends ViewHolder {

        private ImageView iv_banner;

        @Override
        protected void findView(View view) {
            iv_banner = (ImageView) view.findViewById(R.id.iv_banner);
        }

        @Override
        protected void setListener(final int position) {
            iv_banner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    abstractBaseActivity.jumpToPhotoViewActivity((ArrayList<String>) list, R.mipmap.list_item_default, position);
                }
            });
        }

        @Override
        protected void initValue(int position, String banners) {
            ImageLoader.getInstance().displayImage(banners, iv_banner, ImageUtil.getDisplayImageOptions(R.mipmap.list_item_default));
        }
    }

}
