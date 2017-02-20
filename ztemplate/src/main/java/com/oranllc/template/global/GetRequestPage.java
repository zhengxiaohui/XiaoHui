package com.oranllc.template.global;


import com.zbase.request.BaseGetRequestPage;

/**
 * 创建人：郑晓辉
 * 创建日期：2016/7/29
 * 描述：分类请求子类
 */
public class GetRequestPage extends BaseGetRequestPage {

    private int pageIndex;

    public GetRequestPage(String url) {
        super(url);

    }

    @Override
    public int getPageIndex() {
        return pageIndex;
    }

    @Override
    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
        getParams().removeUrl("pageindex");
        getParams().put("pageindex", String.valueOf(pageIndex));
    }

    @Override
    public void setLastId(String lastId) {
        getParams().removeUrl("lastId");
        getParams().put("lastId", String.valueOf(lastId));
    }

    @Override
    public PageType getPageType() {
        return PageType.START_PAGE1;
    }
}
