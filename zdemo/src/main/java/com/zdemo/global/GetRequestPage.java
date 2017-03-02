package com.zdemo.global;


import com.zbase.request.BaseGetRequestPage;

/**
 * 创建人：郑晓辉
 * 创建日期：2016/7/29
 * 描述：分页请求参数名设置
 */
public class GetRequestPage extends BaseGetRequestPage {

    public GetRequestPage(String url) {
        super(url);
    }

    @Override
    protected String getIndexKey() {
        return "index";
    }

    @Override
    protected String getLastIdKey() {
        return "lastId";
    }

    @Override
    public PageType getPageType() {
        return PageType.START_PAGE1;
    }
}
