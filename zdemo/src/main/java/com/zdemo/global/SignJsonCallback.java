package com.zdemo.global;

import android.content.Context;

import com.lzy.okgo.request.base.Request;
import com.zbase.request.BaseJsonCallback;

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


}
