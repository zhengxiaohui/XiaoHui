package com.zbase.request;

import com.lzy.okhttputils.callback.AbsCallback;
import com.lzy.okhttputils.request.PostRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 创建人：郑晓辉
 * 创建日期：2016/8/1
 * 描述：请求分页的类型
 */
public abstract class BasePostRequestPage extends PostRequest implements IRequestPage {

    private JSONObject jsonObject;
    protected int pageIndex;

    public BasePostRequestPage(String url) {
        super(url);
    }

    public JSONObject getJsonObject() {
        jsonObject=new JSONObject();
        return jsonObject;
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
        try {
            jsonObject.put(getIndexKey(), String.valueOf(pageIndex));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public <T> void execute(AbsCallback<T> callback) {
        postJson(jsonObject.toString());
        super.execute(callback);
    }
}
