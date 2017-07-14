package com.zheng.share.weibo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.share.WbShareCallback;
import com.sina.weibo.sdk.share.WbShareHandler;
import com.zheng.share.R;
import com.zheng.share.ShareConstant;

/**
 * 示例
 *
 * @author zhengxiaohui
 * @since 2017/7/14
 */
public class WeiBoShareManager implements WbShareCallback{
    private Context context;
    private WbShareHandler shareHandler;

    public WeiBoShareManager(Context context) {
        this.context = context;
        WbSdk.install(context, new AuthInfo(context, ShareConstant.WEIBO_APP_KEY, "https://api.weibo.com/oauth2/default.html", ""));
    }

    public void shareToWeiBo(Activity activity, String title, String summary) {
        shareHandler = new WbShareHandler(activity);
        shareHandler.registerApp();

        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
        TextObject textObject = new TextObject();
        textObject.text = summary+" "+ShareConstant.SHARE_URL;
        textObject.title = title;
        textObject.actionUrl = ShareConstant.SHARE_URL;
        weiboMessage.textObject = textObject;
//        weiboMessage.imageObject = getImageObj();
//        weiboMessage.mediaObject = getWebpageObj();
        shareHandler.shareMessage(weiboMessage, true);
    }

    public void onNewIntent(Intent intent) {
        shareHandler.doResultIntent(intent,this);
    }

    @Override
    public void onWbShareSuccess() {
        Toast.makeText(context, context.getString(R.string.share_success), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onWbShareCancel() {
        Toast.makeText(context, context.getString(R.string.share_cancel), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onWbShareFail() {
        Toast.makeText(context, context.getString(R.string.share_fail), Toast.LENGTH_SHORT).show();
    }

//    /**
//     * 创建文本消息对象。
//     *
//     * @return 文本消息对象。
//     */
//    private TextObject getTextObj() {
//        TextObject textObject = new TextObject();
//        textObject.text = getSharedText();
//        textObject.title = "xxxx";
//        textObject.actionUrl = "http://www.baidu.com";
//        return textObject;
//    }

//    /**
//     * 创建图片消息对象。
//     * @return 图片消息对象。
//     */
//    private ImageObject getImageObj() {
//        ImageObject imageObject = new ImageObject();
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.test);
//        imageObject.setImageObject(bitmap);
//        return imageObject;
//    }
//
//    /**
//     * 创建多媒体（网页）消息对象。
//     *
//     * @return 多媒体（网页）消息对象。
//     */
//    private WebpageObject getWebpageObj() {
//        WebpageObject mediaObject = new WebpageObject();
//        mediaObject.identify = Utility.generateGUID();
//        mediaObject.title ="测试title";
//        mediaObject.description = "测试描述";
//        Bitmap  bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_logo);
//        // 设置 Bitmap 类型的图片到视频对象里         设置缩略图。 注意：最终压缩过的缩略图大小不得超过 32kb。
//        mediaObject.setThumbImage(bitmap);
//        mediaObject.actionUrl = "http://news.sina.com.cn/c/2013-10-22/021928494669.shtml";
//        mediaObject.defaultText = "Webpage 默认文案";
//        return mediaObject;
//    }
}
