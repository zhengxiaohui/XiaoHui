package com.zheng.share.qq;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.zheng.share.R;
import com.zheng.share.ShareConstant;

/**
 * 示例
 *
 * @author zhengxiaohui
 * @since 2017/7/13
 */
public class QQShareManager {

    private Context context;
    private Tencent mTencent;

    public QQShareManager(Context context){
        this.context=context;
        mTencent = Tencent.createInstance(ShareConstant.QQ_APP_ID, context);
    }

    /**
     * （1） 分享图文消息
     */
    public void shareToQQAsURL(Activity activity) {
        final Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, "要分享的标题");
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY,  "要分享的摘要");
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,  ShareConstant.SHARE_URL);
//        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,"http://imgcache.qq.com/qzone/space_item/pre/0/66768.gif");
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME,  "测试应用222222");
//        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT,  "其他附加功能");
        mTencent.shareToQQ(activity, params, new BaseUiListener(activity));
    }

    /**
     * （4） 分享应用
     */
    public void shareToQQAsAPP(Activity activity,String title,String summary,String appName) {
        final Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_APP);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, title);
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY,  summary);
//        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, "http://imgcache.qq.com/qzone/space_item/pre/0/66768.gif");
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME,  appName);
        mTencent.shareToQQ(activity, params, new BaseUiListener(activity));
    }

    /**
     * 分享到QQ空间,目前只支持图文分享。由于必须要图片地址，所以暂时不用
     */
    public void shareToQzone (final Activity activity) {
        //分享类型
        final Bundle params = new Bundle();
        params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE,QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
        params.putString(QzoneShare.SHARE_TO_QQ_TITLE, "标题");//必填
        params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, "摘要");//选填
        params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, "http://www.qq.com/news/1.html");//必填
//        params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, "图片链接ArrayList");
        mTencent.shareToQzone(activity, params, new BaseUiListener(activity));
    }

    /**
     * 应用调用Andriod_SDK接口时，如果要成功接收到回调，需要在调用接口的Activity的onActivityResult方法中增加如下代码：
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public  void onActivityResult(int requestCode, int resultCode, Intent data) {
        Tencent.onActivityResultData(requestCode,resultCode,data,new BaseUiListener(context));
    }

    private class BaseUiListener implements IUiListener {

        private Context context;

        public BaseUiListener(Context context){
            this.context=context;
        }

        @Override
        public void onComplete(Object response) {
            Toast.makeText(context, context.getString(R.string.share_success), Toast.LENGTH_SHORT).show();
        }
        protected void doComplete(Object values) {

        }
        @Override
        public void onError(UiError e) {
            Toast.makeText(context, context.getString(R.string.share_fail), Toast.LENGTH_SHORT).show();
        }
        @Override
        public void onCancel() {
            Toast.makeText(context, context.getString(R.string.share_cancel), Toast.LENGTH_SHORT).show();
        }
    }
}
