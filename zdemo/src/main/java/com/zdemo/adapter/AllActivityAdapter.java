package com.zdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zbase.adapter.ZBaseRecyclerAdapter;
import com.zdemo.R;
import com.zdemo.bean.Activity;

/**
 * Created by Administrator on 2015/2/16.
 */
public class AllActivityAdapter extends ZBaseRecyclerAdapter<Activity> {

    public AllActivityAdapter(Context context) {
        super(context);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent) {
        return new MyViewHolder(inflate(R.layout.adapter_all_activity, parent));
    }

    public class MyViewHolder extends ItemViewHolder {
        RelativeLayout rl_main;
        TextView tv_category;
        TextView tv_content;
        TextView tv_site;
        TextView tv_time;

        public MyViewHolder(View itemView) {
            super(itemView);
            rl_main = (RelativeLayout) itemView.findViewById(R.id.rl_main);
            tv_category = (TextView) itemView.findViewById(R.id.tv_category);
            tv_content = (TextView) itemView.findViewById(R.id.tv_content);
            tv_site = (TextView) itemView.findViewById(R.id.tv_site);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
        }

        @Override
        protected void setListener(int position) {
            rl_main.setTag(R.id.rl_main, position);
            rl_main.setOnClickListener(onClickListener);
        }

        @Override
        protected void initValue(int position,Activity activity) {
            tv_category.setText(activity.getActivityCategoryName());
            tv_content.setText(activity.getActivityContent());
            tv_site.setText(activity.getActivitySite());
            tv_time.setText(activity.getActivityTime());
        }
    }

}
