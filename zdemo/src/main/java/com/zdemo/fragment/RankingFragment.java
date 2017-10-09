package com.zdemo.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zbase.decoration.LinearLayoutDecoration;
import com.zbase.listener.ItemClickListener;
import com.zdemo.R;
import com.zdemo.adapter.GameLittleAdapter;

import java.util.Arrays;

/**
 * 创建人：郑晓辉
 * 创建日期：2017/09/25
 * 描述：排行
*/
public class RankingFragment extends BaseFragment {
    private GameLittleAdapter adapter;
    @Override
    protected int inflateMainLayoutId() {
        return R.layout.fragment_ranking;
    }
    @Override
    protected void initView(View view) {


        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new LinearLayoutDecoration(context));
        adapter = new GameLittleAdapter(context);
        adapter.setOnClickListener(this);
        adapter.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }
        });
        adapter.inflaterEmptyView(R.layout.empty_view);
        recyclerView.setAdapter(adapter);
        adapter.setList(Arrays.asList(getResources().getStringArray(R.array.tests)));
    }
    @Override
    protected void setListener() {

    }
    @Override
    protected void initValue() {

    }
    @Override
    public void onClick(View v) {

    }
}