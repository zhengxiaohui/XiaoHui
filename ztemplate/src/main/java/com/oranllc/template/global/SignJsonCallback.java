package com.oranllc.template.global;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.lzy.okgo.request.base.Request;
import com.zbase.request.BaseJsonCallback;

import org.json.JSONObject;

import okhttp3.Response;

/**
 * 创建人：郑晓辉
 * 创建日期：2016/8/3
 * 描述：
 */
public abstract class SignJsonCallback<T> extends BaseJsonCallback<T> {

    public SignJsonCallback(Context context, Class<T> clazz) {
        super(context, clazz);
    }

    public SignJsonCallback(Context context, Class<T> clazz, boolean showProgress) {
        super(context, clazz, showProgress);
    }

    @Override
    public void onStart(Request<T, ? extends Request> request) {
        super.onStart(request);
        //        String appKey = HttpConstant.APP_KEY;
//        String timeStamp = String.valueOf(System.currentTimeMillis());
//        String nonce = String.valueOf(UUID.randomUUID()).replaceAll("-", "");
//        String[] ArrTmp = {appKey, timeStamp, nonce};
//        Arrays.sort(ArrTmp);
//        String tmpStr = "";
//        for (int i = 0; i < ArrTmp.length; i++) {
//            tmpStr += ArrTmp[i];
//        }
//        tmpStr = tmpStr + HttpConstant.APP_SECRET;
//        String sign = MD5Encryption.getPassword32(tmpStr);
//        request.params("appKey", appKey);
//        request.params("timeStamp", timeStamp);
//        request.params("nonce", nonce);
//        request.params("sign", sign);
    }

    @Override
    public T convertResponse(Response response) throws Throwable {
        String responseData = response.body().string();
        if (TextUtils.isEmpty(responseData)) return null;
        if (clazz != null) {
            JSONObject jsonObject = new JSONObject(responseData);
            int codeState = jsonObject.getInt("codeState");
            if (codeState == -1) {//判断token是否过期，过期则退出登录，清空用户信息，跳转到个人中心
                abstractBaseActivity.logout();
            } else {
                return new Gson().fromJson(responseData, clazz);
            }
        }
        return null;
    }
}
