package com.zdemo.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zbase.decoration.LinearLayoutDecoration;
import com.zbase.util.PopUtil;
import com.zdemo.R;
import com.zdemo.adapter.AllActivityAdapter;
import com.zdemo.bean.ActivityListJson;
import com.zdemo.constant.HttpConstant;
import com.zdemo.global.AutoUltraPullToRefreshSign;
import com.zdemo.global.GetRequestPage;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;

/**
 * 创建人：郑晓辉
 * 创建日期：2016/5/25
 * 描述：通用下拉刷新控件
 */
public class UltraPullToRefreshActivity extends BaseActivity {

    @Override
    protected int inflateMainLayoutId() {
        return R.layout.activity_ultra_pull_to_refresh;
    }

    @Override
    protected void initView(View view) {
        PtrClassicFrameLayout ptrClassicFrameLayout = (PtrClassicFrameLayout) view.findViewById(R.id.ptrClassicFrameLayout);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new LinearLayoutDecoration(context));
        AllActivityAdapter adapter = new AllActivityAdapter(context);
        adapter.setOnClickListener(this);
        View emptyView = adapter.inflaterEmptyViewSubtractHeight(R.layout.empty_view, getResources().getDimensionPixelOffset(R.dimen.top_height));
        ((ImageView) emptyView.findViewById(R.id.iv_empty)).setImageResource(R.mipmap.add_picture);
        ((TextView) emptyView.findViewById(R.id.tv_empty)).setText(R.string.no_content);
        GetRequestPage getRequestPage = new GetRequestPage(HttpConstant.QUERY_LATEST_ACTIVITY_URL);
        getRequestPage.tag(this);
        AutoUltraPullToRefreshSign autoUltraPullToRefresh = new AutoUltraPullToRefreshSign(context, getRequestPage, ActivityListJson.class,
                ptrClassicFrameLayout, recyclerView, adapter);
        autoUltraPullToRefresh.initData();
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initValue() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_main:
                int position = (int) v.getTag(R.id.rl_main);
                PopUtil.toast(context, position + "");
                break;
        }
    }
}
