package com.zbase.interfaces;

import com.zbase.listener.AllSelectedListener;
import com.zbase.listener.NotAllSelectedListener;

import java.util.List;

/**
 * 创建人：郑晓辉
 * 创建日期：2017/3/8
 * 描述：列表单项选中和多项选中的接口
 */
public interface ISelectPosition {

    /**
     * 获取是否选中的集合
     * @return
     */
    public List<Boolean> getSelectList();

    /**
     * 反向选择，如果没选中则选中，如果选中则取消选中,单选如果选择的是之前选中的，则返回
     *
     * @param reverseSelectPosition
     * @param single                是否单选
     */
    public void setReverseSelectPosition(int reverseSelectPosition, boolean single);

    /**
     * 反向选择全部，如果之前没有全选中，则变为全选中，反之则变成取消全选
     */
    public void reverseAllSelected();

    /**
     * 得到单选选中的索引，如果都没选则返回-1
     * @return
     */
    public int getSingleSelectedPosition();

    public void setAllSelectedListener(AllSelectedListener allSelectedListener);

    public void setNotAllSelectedListener(NotAllSelectedListener notAllSelectedListener);
}
