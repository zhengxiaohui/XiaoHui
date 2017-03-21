package com.zbase.view.adapterview;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;

import com.zbase.adapter.BaseAddPictureAdapter;
import com.zbase.listener.ItemClickListener;
import com.zbase.listener.OnItemDeleteListener;
import com.zbase.util.ImageUtil;

import java.io.File;
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

    public void setOnItemDeleteListener(OnItemDeleteListener onItemDeleteListener) {
        this.onItemDeleteListener = onItemDeleteListener;
    }

    private OnItemDeleteListener onItemDeleteListener;

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
        this.baseAddPictureAdapter = baseAddPictureAdapter;
        baseAddPictureAdapter.setDeleteItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                baseAddPictureAdapter.getList().remove(position);
                baseAddPictureAdapter.notifyDataSetChanged();
                if (onItemDeleteListener != null) {
                    onItemDeleteListener.onItemDelete(position);
                }
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
        this.baseAddPictureAdapter = baseAddPictureAdapter;
        baseAddPictureAdapter.setDeleteItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                baseAddPictureAdapter.getList().remove(position);
                baseAddPictureAdapter.notifyDataSetChanged();
                if (onItemDeleteListener != null) {
                    onItemDeleteListener.onItemDelete(position);
                }
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
                List<String> list = new ArrayList<>();
                list.addAll(data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT));//list存放原图地址,GridView显示的是原图，上传的时候一般用压缩图。
                baseAddPictureAdapter.setList(list);
            }
        }
    }

    /**
     * 获取压缩后的图片缓存文件列表
     * 注意：压缩之后地址改变了，取的是压缩后缓存目录的地址，不是原图的地址。
     * 压缩后的图片文件地址列表，和原图地址不同，保存在缓存目录中。/date/date/(包名)/cacheImage/...
     *
     * @return
     */
    public List<File> getCompressFileList() {
        List<String> pathList = baseAddPictureAdapter.getList();
        List<File> fileList = new ArrayList<>();
        if (pathList != null && pathList.size() > 0) {
            for (int i = 0; i < pathList.size(); i++) {
                if (!pathList.get(i).startsWith("http")) {//以http开头的网络图片是下载的，不是本地选择的，所以图片不压缩不上传。只压缩上传新选择的图片，符合编辑模式。（图片删除有独立接口删）
                    fileList.add(new File(ImageUtil.compressBitmap(getContext(), pathList.get(i))));
                }
            }
        }
        return fileList;
    }

}
