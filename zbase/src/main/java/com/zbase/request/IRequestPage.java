package com.zbase.request;

import com.lzy.okhttputils.callback.AbsCallback;
import com.zbase.enums.PageType;

/**
 * 创建人：郑晓辉
 * 创建日期：2017/10/9 0009
 * 描述：
 */
public interface IRequestPage {

    int getPageIndex();

    void setPageIndex(int pageIndex);

    /**
     * 分页参数的Key值，比如index
     */
    String getIndexKey();

    /**
     * 分页模式，从0开始，从1开始
     *
     * @return
     */
    PageType getPageType();

    <T> void execute(AbsCallback<T> callback);
}
