package com.zbase.request;

import com.lzy.okhttputils.request.GetRequest;

/**
 * 创建人：郑晓辉
 * 创建日期：2016/8/1
 * 描述：
 */
public abstract class BaseGetRequestPage extends GetRequest {

    public BaseGetRequestPage(String url) {
        super(url);
    }

    public abstract int getPageIndex();

    public abstract void setPageIndex(int pageIndex);

    public abstract void setLastId(String lastId);

    /**
     * 分页模式，从0开始，从1开始，使用lastId分页
     *
     * @return
     */
    public abstract PageType getPageType();

    protected enum PageType {
        START_PAGE0, START_PAGE1, LAST_ID
    }

}
