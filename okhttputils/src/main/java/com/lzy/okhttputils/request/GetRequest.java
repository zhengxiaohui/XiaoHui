package com.lzy.okhttputils.request;

import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * ================================================
 * 作    者：廖子尧
 * 版    本：1.0
 * 创建日期：2016/1/12
 * 描    述：Get请求的实现类，注意需要传入本类的泛型
 * 修订历史：
 * ================================================
 */
public class GetRequest extends BaseRequest<GetRequest> {

    private String tempURL;//防止下拉刷新重复拼接url

    public GetRequest(String url) {
        super(url);
        tempURL = url;
    }

    @Override
    protected RequestBody generateRequestBody() {
        return null;
    }

    @Override
    protected Request generateRequest(RequestBody requestBody) {
        Request.Builder requestBuilder = new Request.Builder();
        appendHeaders(requestBuilder);
        url = createUrlFromParams(tempURL, params.urlParamsMap);
        return requestBuilder.get().url(url).tag(tag).build();
    }
}