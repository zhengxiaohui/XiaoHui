package com.zbase.request;

import com.lzy.okhttputils.request.GetRequest;

/**
 * 创建人：郑晓辉
 * 创建日期：2016/8/1
 * 描述：请求分页类型
 */
public abstract class BaseGetRequestPage extends GetRequest {

    protected int pageIndex;
    protected String lastId;

    public BaseGetRequestPage(String url) {
        super(url);
    }

    public int getPageIndex() {
        return pageIndex;
    }

    /**
     * 分页参数的Key值，比如index
     * @param pageIndex
     */
    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
        getParams().removeUrl(getIndexKey());
        getParams().put(getIndexKey(), String.valueOf(pageIndex));
    }

    /**
     * 分页参数的Key值，比如lastId
     * @param lastId
     */
    public void setLastId(String lastId) {
        this.lastId = lastId;
        getParams().removeUrl(getLastIdKey());
        getParams().put(getLastIdKey(), String.valueOf(lastId));
    }

    protected abstract String getIndexKey();

    protected abstract String getLastIdKey();

    /**
     * 分页模式，从0开始，从1开始，使用lastId分页
     *
     * @return
     */
    public abstract PageType getPageType();

    protected enum PageType {//请求分页类型：从0开始，从1开始，传最后一条记录的id
        START_PAGE0, START_PAGE1, LAST_ID
    }

}
