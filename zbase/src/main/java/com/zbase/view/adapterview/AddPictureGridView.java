package com.zbase.view.adapterview;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;

import com.zbase.adapter.BaseAddPictureAdapter;
import com.zbase.listener.ItemClickListener;
import com.zbase.util.ImageUtil;

import java.util.ArrayList;
import java.util.List;

import me.nereo.multi_image_selector.MultiImageSelector;

/**
 * 创建人：郑晓辉
 * 创建日期：2016/7/8
 * 描述：添加图片的GridView
 */
public class AddPictureGridView extends FullGridView {

    private BaseAddPictureAdapter baseAddPictureAdapter;
    private boolean isCompress=true;//是否压缩，默认true压缩

    public AddPictureGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AddPictureGridView(Context context) {
        super(context);
    }

    public AddPictureGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void init(final Activity activity, final BaseAddPictureAdapter baseAddPictureAdapter) {
        init(activity,baseAddPictureAdapter,true);
    }

    public void init(final Activity activity, final BaseAddPictureAdapter baseAddPictureAdapter,boolean isCompress) {
        this.baseAddPictureAdapter = baseAddPictureAdapter;
        this.isCompress=isCompress;
        baseAddPictureAdapter.setDeleteItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                baseAddPictureAdapter.getList().remove(position);
                baseAddPictureAdapter.notifyDataSetChanged();
            }
        });
        baseAddPictureAdapter.setAddItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                MultiImageSelector.create().multi().count(baseAddPictureAdapter.getMaxCount()).origin((ArrayList<String>) baseAddPictureAdapter.getList()).start(activity, MultiImageSelector.REQUEST_IMAGE_GRID);
            }
        });
        setAdapter(baseAddPictureAdapter);
    }

    public void init(final Fragment fragment, final BaseAddPictureAdapter baseAddPictureAdapter) {
        init(fragment,baseAddPictureAdapter);
    }

    public void init(final Fragment fragment, final BaseAddPictureAdapter baseAddPictureAdapter,boolean isCompress) {
        this.baseAddPictureAdapter = baseAddPictureAdapter;
        this.isCompress=isCompress;
        baseAddPictureAdapter.setDeleteItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                baseAddPictureAdapter.getList().remove(position);
                baseAddPictureAdapter.notifyDataSetChanged();
            }
        });
        baseAddPictureAdapter.setAddItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                MultiImageSelector.create().multi().count(baseAddPictureAdapter.getMaxCount()).origin((ArrayList<String>) baseAddPictureAdapter.getList()).start(fragment, MultiImageSelector.REQUEST_IMAGE_GRID);
            }
        });
        setAdapter(baseAddPictureAdapter);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MultiImageSelector.REQUEST_IMAGE_GRID) {
            if (resultCode == Activity.RESULT_OK) {
                List<String> pathList = new ArrayList<>();
                if (isCompress) {
                    for (int i = 0; i < data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT).size(); i++) {
                        pathList.add(ImageUtil.compressBitmap(getContext(), data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT).get(i)));
                    }
                } else {
                    pathList.addAll(data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT));
                }
                baseAddPictureAdapter.setList(pathList);
            }
        }
    }

}
