package com.zbase.manager;

import android.support.v7.widget.RecyclerView;
import android.widget.BaseAdapter;

import com.zbase.interfaces.ISelectPosition;
import com.zbase.listener.AllSelectedListener;
import com.zbase.listener.NotAllSelectedListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建人：郑晓辉
 * 创建日期：2017/3/8
 * 描述：列表单项选中和多项选中管理器
 */
public class AdapterSelectPositionManager implements ISelectPosition {

    private BaseAdapter baseAdapter;
    private RecyclerView.Adapter recyclerViewAdapter;
    private int type;//使用哪种Adapter,0为baseAdapter,1为recyclerViewAdapter

    private List<Boolean> selectList = new ArrayList<>();//多选每个位置的状态（false没选中，true选中）

    public List<Boolean> getSelectList() {
        return selectList;
    }

    public AdapterSelectPositionManager(BaseAdapter baseAdapter) {
        this.baseAdapter = baseAdapter;
        type = 0;
    }

    public AdapterSelectPositionManager(RecyclerView.Adapter recyclerViewAdapter) {
        this.recyclerViewAdapter = recyclerViewAdapter;
        type = 1;
    }

    private void notifyDataSetChanged() {
        switch (type) {
            case 0:
                baseAdapter.notifyDataSetChanged();
                break;
            case 1:
                recyclerViewAdapter.notifyDataSetChanged();
                break;
        }
    }

    /**
     * 反向选择，如果没选中则选中，如果选中则取消选中,单选如果选择的是之前选中的，则返回
     *
     * @param reverseSelectPosition
     * @param single                是否单选
     */
    public void setReverseSelectPosition(int reverseSelectPosition, boolean single) {
        if (single) {//单选
            if (selectList.get(reverseSelectPosition)) {//如果选择的是之前选中的，则返回
                return;
            } else {//取消其他选中，当前选中
                for (int i = 0; i < selectList.size(); i++) {
                    selectList.set(i, false);
                }
                selectList.set(reverseSelectPosition, true);
            }
            notifyDataSetChanged();
        } else {
            selectList.set(reverseSelectPosition, !selectList.get(reverseSelectPosition));
            notifyDataSetChanged();
            if (isAllSelect()) {//全部选中
                if (allSelectedListener != null) {
                    allSelectedListener.onAllSelected();
                }
            } else {//没有全部选中
                if (notAllSelectedListener != null) {
                    notAllSelectedListener.onNotAllSelected();
                }
            }
        }
    }

    /**
     * 反向选择全部，如果之前没有全选中，则变为全选中，反之则变成取消全选
     */
    public void reverseAllSelected() {
        if (isAllSelect()) {//如果之前是全部选中的则取消所有选中
            for (int i = 0; i < selectList.size(); i++) {
                selectList.set(i, false);
            }
            if (notAllSelectedListener != null) {
                notAllSelectedListener.onNotAllSelected();
            }
        } else {//全部选中
            for (int i = 0; i < selectList.size(); i++) {
                selectList.set(i, true);
            }
            if (allSelectedListener != null) {
                allSelectedListener.onAllSelected();
            }
        }
        notifyDataSetChanged();
    }

    /**
     * 是否全部选中
     *
     * @return
     */
    private boolean isAllSelect() {
        for (int i = 0; i < selectList.size(); i++) {
            if (!selectList.get(i)) {//如果有至少一个没有选中的，则状态为没有全部选中
                return false;
            }
        }
        return true;
    }

    /**
     * 得到单选选中的索引，如果都没选则返回-1
     *
     * @return
     */
    public int getSingleSelectedPosition() {
        for (int i = 0; i < selectList.size(); i++) {
            if (selectList.get(i)) {
                return i;
            }
        }
        return -1;
    }

    private AllSelectedListener allSelectedListener;
    private NotAllSelectedListener notAllSelectedListener;

    public void setAllSelectedListener(AllSelectedListener allSelectedListener) {
        this.allSelectedListener = allSelectedListener;
    }

    public void setNotAllSelectedListener(NotAllSelectedListener notAllSelectedListener) {
        this.notAllSelectedListener = notAllSelectedListener;
    }
}
