package com.zheng.share.weixin;

import android.content.Context;

import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zheng.share.ShareConstant;

/**
 * 示例
 *
 * @author zhengxiaohui
 * @since 2017/7/13
 * 注意：分享微信必须使用正式签名，不然会出现闪一下没出来微信界面的情况
 debug {
minifyEnabled false
signingConfig signingConfigs.release
}
 */
public class WeiXinShareManager {

    private Context context;
    private IWXAPI wxApi;

    public WeiXinShareManager(Context context){
        this.context=context;
        wxApi = WXAPIFactory.createWXAPI(context, ShareConstant.WEIXIN_APP_ID);
        wxApi.registerApp(ShareConstant.WEIXIN_APP_ID);
    }

    public void shareToWeiXin(String title, String summary) {
        wechatShare(0,title,summary);
    }

    public void shareToWeiXinCircle(String title, String summary) {
        wechatShare(1,title,summary);
    }

    /**
     * 微信分享 （这里仅提供一个分享网页的示例，其它请参看官网示例代码）
     * @param flag(0:分享到微信好友，1：分享到微信朋友圈)
     */
    private void wechatShare(int flag,String title, String summary){
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = ShareConstant.SHARE_URL;
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = title;
        msg.description = summary;
//        //这里替换一张自己工程里的图片资源
//        Bitmap thumb = BitmapFactory.decodeResource(context.getResources(), mipmapId);
//        msg.setThumbImage(thumb);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        req.scene = flag==0?SendMessageToWX.Req.WXSceneSession:SendMessageToWX.Req.WXSceneTimeline;
        wxApi.sendReq(req);
    }
}
