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
     * 单选，点击之前选中的则不执行，点击不是之前选中的则取消其他选中，当前选中
     *
     * @param selectPosition
     */
    public void setSelectPositionSingle(int selectPosition);

    /**
     * 多选，点击之前选中的则取消选中，点击不是之前选中的则选中
     * @param selectPosition
     */
    public void setSelectPositionMulti(int selectPosition);

    /**
     * 反向选择全部，如果之前没有全选中，则变为全选中，反之则变成取消全选
     */
    public void reverseAllSelected();

    /**
     * 得到单选选中的索引，如果都没选则返回-1
     * @return
     */
    public int getSingleSelectedPosition();

    /**
     * 重置所有选中，即所有都没有选择
     */
    public void resetAllSelect();

    /**
     * 设置所有选中，即所有都选择
     */
    public void setAllSelect();

    public void setAllSelectedListener(AllSelectedListener allSelectedListener);

    public void setNotAllSelectedListener(NotAllSelectedListener notAllSelectedListener);
}
