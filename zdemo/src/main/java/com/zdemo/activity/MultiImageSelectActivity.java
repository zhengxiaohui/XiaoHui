package com.zdemo.activity;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.zbase.listener.ItemClickListener;
import com.zbase.view.adapterview.AddPictureGridView;
import com.zdemo.R;
import com.zdemo.adapter.AddPictureAdapter;
import com.zdemo.adapter.MultiImageSelectorAdapter;

import java.util.ArrayList;

import me.nereo.multi_image_selector.MultiImageSelector;


/**
 * 创建人：郑晓辉
 * 创建日期：2016/6/23
 * 描述：
 */
public class MultiImageSelectActivity extends BaseActivity {

    private Button button1;
    private Button button2;
    private MultiImageSelectorAdapter adapter;
    private ArrayList<String> uriList = new ArrayList<>();
    private AddPictureGridView addPictureGridView;

    @Override
    protected int inflateMainLayoutId() {
        return R.layout.activity_multi_image_selector;
    }

    @Override
    protected void initView(View view) {
        button1 = (Button) view.findViewById(R.id.button1);
        button2 = (Button) view.findViewById(R.id.button2);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter = new MultiImageSelectorAdapter(context);
        adapter.setOnClickListener(this);
        adapter.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }
        });
        recyclerView.setAdapter(adapter);

        addPictureGridView = (AddPictureGridView) view.findViewById(R.id.addPictureGridView);
        addPictureGridView.init(this, new AddPictureAdapter(context));
    }

    @Override
    protected void setListener() {
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
    }

    @Override
    protected void initValue() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                MultiImageSelector.create().single().start(this, MultiImageSelector.REQUEST_IMAGE);
                break;
            case R.id.button2:
                MultiImageSelector.create()
                        .showCamera(true) // show camera or not. true by default
                        .count(9) // max select image size, 9 by default. used width #.multi()
                        .multi() // multi mode, default mode;
                        .origin(uriList) // original select data set, used width #.multi()
                        .start(this, MultiImageSelector.REQUEST_IMAGE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MultiImageSelector.REQUEST_IMAGE) {
            if (resultCode == RESULT_OK) {
                uriList = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);
                adapter.setList(uriList);
            }
        }
        addPictureGridView.onActivityResult(requestCode, resultCode, data);
    }
}
