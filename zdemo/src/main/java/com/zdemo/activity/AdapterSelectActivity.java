package com.zdemo.activity;

import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.zbase.listener.AllSelectedListener;
import com.zbase.listener.ItemClickListener;
import com.zbase.listener.NotAllSelectedListener;
import com.zdemo.R;
import com.zdemo.adapter.ListViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建人：郑晓辉
 * 创建日期：2016/12/7
 * 描述：
 */
public class AdapterSelectActivity extends BaseActivity {

    private ListView listView;
    private ListViewAdapter adapter;
    private Button button;

    @Override
    protected int inflateMainLayoutId() {
        return R.layout.activity_adapter_select;
    }

    @Override
    protected void initView(View view) {
        button = (Button) view.findViewById(R.id.button);
        listView = (ListView) view.findViewById(R.id.listView);
        adapter = new ListViewAdapter(context);
        adapter.setOnClickListener(this);
        adapter.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                adapter.setReverseSelectPosition(position,false);
            }
        });
        listView.setAdapter(adapter);

        adapter.setAllSelectedListener(new AllSelectedListener() {
            @Override
            public void onAllSelected() {
                button.setText("取消全选");
            }
        });
        adapter.setNotAllSelectedListener(new NotAllSelectedListener() {
            @Override
            public void onNotAllSelected() {
                button.setText("全选");
            }
        });
    }

    @Override
    protected void setListener() {
        button.setOnClickListener(this);
    }

    @Override
    protected void initValue() {
        List<String> demoList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            demoList.add("选项" + i);
        }
        adapter.setList(demoList);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                adapter.reverseAllSelected();
                break;
        }
    }
}
