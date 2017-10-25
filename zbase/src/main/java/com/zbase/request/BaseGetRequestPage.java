package com.zbase.request;

import com.lzy.okhttputils.request.GetRequest;

/**
 * 创建人：郑晓辉
 * 创建日期：2016/8/1
 * 描述：请求分页的类型
 */
public abstract class BaseGetRequestPage extends GetRequest implements IRequestPage{

    protected int pageIndex;

    public BaseGetRequestPage(String url) {
        super(url);
    }

    public int getPageIndex() {
        return pageIndex;
    }

    @Override
    public int getPageSize() {
        return 20;//默认20，子类可以继承重写
    }

    /**
     * 分页参数的Key值，比如index
     * @param pageIndex
     */
    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
        getParams().put(getIndexKey(), String.valueOf(pageIndex));
    }

}
