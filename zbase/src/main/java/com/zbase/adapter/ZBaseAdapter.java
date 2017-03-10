package com.zbase.adapter;

import android.content.Context;
import android.view.View;
import android.widget.BaseAdapter;

import com.zbase.activity.AbstractBaseActivity;
import com.zbase.interfaces.ISelectPosition;
import com.zbase.listener.AllSelectedListener;
import com.zbase.listener.ItemClickListener;
import com.zbase.listener.NotAllSelectedListener;
import com.zbase.manager.AdapterSelectPositionManager;

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
public abstract class ZBaseAdapter<T> extends BaseAdapter implements ISelectPosition {

    protected Context context;
    protected AbstractBaseActivity abstractBaseActivity;
    protected List<T> list = new ArrayList<T>();
    protected View.OnClickListener onClickListener;
    protected ItemClickListener itemClickListener;
    private AdapterSelectPositionManager adapterSelectPositionManager;

    public ZBaseAdapter(Context context) {
        this.context = context;
        abstractBaseActivity = (AbstractBaseActivity) context;
        adapterSelectPositionManager = new AdapterSelectPositionManager(this);
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
            for (int i = 0; i < addList.size(); i++) {
                adapterSelectPositionManager.getSelectList().add(false);//增加的List每个都默认false没选中
            }
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
            list.addAll(setList);//这里必须用addAll，不然list嵌套的情况会出现第二次点击子list的时候数据为空的问题，也就是引用了原来的list对象地址
            for (int i = 0; i < setList.size(); i++) {
                adapterSelectPositionManager.getSelectList().add(false);//初始化每个项的选中状态，默认都是false
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
            adapterSelectPositionManager.getSelectList().remove(position);
            notifyDataSetChanged();
        }
    }

    /**
     * 清除数据并刷新
     */
    public void clear() {
        list.clear();
        adapterSelectPositionManager.getSelectList().clear();
        notifyDataSetChanged();
    }

    /**
     * 反向选择，如果没选中则选中，如果选中则取消选中,单选如果选择的是之前选中的，则返回
     *
     * @param reverseSelectPosition
     * @param single                是否单选
     */
    public void setReverseSelectPosition(int reverseSelectPosition, boolean single) {
        adapterSelectPositionManager.setReverseSelectPosition(reverseSelectPosition, single);
    }

    /**
     * 反向选择全部，如果之前没有全选中，则变为全选中，反之则变成取消全选
     */
    public void reverseAllSelected() {
        adapterSelectPositionManager.reverseAllSelected();
    }

    /**
     * 得到单选选中的索引，如果都没选则返回-1
     *
     * @return
     */
    public int getSingleSelectedPosition() {
        return adapterSelectPositionManager.getSingleSelectedPosition();
    }

    public void setAllSelectedListener(AllSelectedListener allSelectedListener) {
        adapterSelectPositionManager.setAllSelectedListener(allSelectedListener);
    }

    public void setNotAllSelectedListener(NotAllSelectedListener notAllSelectedListener) {
        adapterSelectPositionManager.setNotAllSelectedListener(notAllSelectedListener);
    }

    @Override
    public List<Boolean> getSelectList() {
        return adapterSelectPositionManager.getSelectList();
    }
}
