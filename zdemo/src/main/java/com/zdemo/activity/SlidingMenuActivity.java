package com.zdemo.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zbase.common.ZLog;
import com.zbase.decoration.LinearLayoutDecoration;
import com.zdemo.R;
import com.zdemo.adapter.DemoSlidingMenuAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建人：郑晓辉
 * 创建日期：2015/12/15
 * 描述：
 */
public class SlidingMenuActivity extends BaseActivity {

    private DemoSlidingMenuAdapter adapter;

    @Override
    protected int inflateMainLayoutId() {
        return R.layout.activity_sliding_menu;
    }

    @Override
    protected void initView(View view) {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new LinearLayoutDecoration(context));
        adapter = new DemoSlidingMenuAdapter(context);
        adapter.setOnClickListener(this);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                adapter.closeMenu();
            }
        });
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initValue() {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("a");
        list.add("a");
        list.add("a");
        list.add("a");
        list.add("a");
        list.add("a");
        list.add("a");
        list.add("a");
        adapter.addList(list);
    }

    @Override
    public void onClick(View v) {
        int position;
        switch (v.getId()) {
            case R.id.tv_delete:
                position = (int) v.getTag(R.id.tv_delete);
                ZLog.dZheng(position + "");
                break;
        }
    }
}
