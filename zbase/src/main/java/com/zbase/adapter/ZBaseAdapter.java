package com.zbase.adapter;

import android.content.Context;
import android.view.View;
import android.widget.BaseAdapter;

import com.zbase.activity.AbstractBaseActivity;
import com.zbase.listener.AllSelectedListener;
import com.zbase.listener.ItemClickListener;
import com.zbase.listener.NotAllSelectedListener;

import java.util.ArrayList;
import java.util.List;

/**
 * **********************************************************
 * <p/>
 * 说明：通用列表适配器抽象基类(推荐使用ZBaseAdapterAdvance)
 * <p/>
 * 作者：郑晓辉
 * <p/>
 * 创建日期：2013-10-30
 * <p/>
 * 描述 :抽象类MyBaseAdapter继承了BaseAdapter，使用了泛型。 传入list,已经写了getCount()，
 * 子类不用再写。子类继承后只要重写getView()方法，并且增加ViewHolder就可以了。
 * <p/>
 * **********************************************************
 */
@Deprecated
public abstract class ZBaseAdapter<T> extends BaseAdapter {

    protected Context context;
    protected AbstractBaseActivity abstractBaseActivity;
    protected List<T> list = new ArrayList<T>();
    protected View.OnClickListener onClickListener;
    protected ItemClickListener itemClickListener;
    protected List<Boolean> selectList = new ArrayList<>();//多选每个位置的状态（false没选中，true选中）
    public List<Boolean> getSelectList() {
        return selectList;
    }

    public ZBaseAdapter(Context context) {
        this.context = context;
        abstractBaseActivity=(AbstractBaseActivity)context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public T getItem(int position) {
        if (position < list.size()) {
            return list.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    /**
     * 添加数据，一般用于上拉加载更多，分页数据添加或者初始化数据
     *
     * @param addList
     */
    public void addList(List<T> addList) {
        if (addList != null && addList.size() > 0) {
            list.addAll(addList);
        }
        notifyDataSetChanged();
    }

    /**
     * 设置数据，一般用于下拉刷新，清空旧数据并添加新数据
     *
     * @param setList
     */
    public void setList(List<T> setList) {
        list.clear();
        if (setList != null) {//这里不能加判断setList.size() > 0，否则有可能出现原来空数据的界面添加数据后显示不出来的情况。
            list = setList;
            for (int i = 0; i < setList.size(); i++) {
                selectList.add(false);//初始化每个项的选中状态，默认都是false
            }
        }
        notifyDataSetChanged();
    }

    /**
     * 获取List
     *
     * @return
     */
    public List<T> getList() {
        return list;
    }

    /**
     * 移除单个项
     *
     * @param position
     */
    public void removeItem(int position) {
        if (position < list.size()) {
            list.remove(position);
            selectList.remove(position);
            notifyDataSetChanged();
        }
    }

    /**
     * 清除数据并刷新
     */
    public void clear() {
        list.clear();
        selectList.clear();
        notifyDataSetChanged();
    }

    /**
     * 反向选择，如果没选中则选中，如果选中则取消选中
     *
     * @param reverseSelectPosition
     * @param single                是否单选
     */
    public void setReverseSelectPosition(int reverseSelectPosition, boolean single) {
        if (single) {//单选
            if (selectList.get(reverseSelectPosition)) {//如果选择的是之前选中的，则取消选中
                selectList.set(reverseSelectPosition, false);
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
