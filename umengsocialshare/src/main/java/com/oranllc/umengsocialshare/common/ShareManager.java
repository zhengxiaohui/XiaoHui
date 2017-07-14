package com.oranllc.umengsocialshare.common;

import android.app.Activity;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

/**
 * 创建人：郑晓辉
 * 创建日期：2016/12/7
 * 描述：友盟分享事件管理器
 */
public class ShareManager {

    private Activity activity;
    private String title;
    private String text;
    private UMImage uMImage;
    private String url;

    public ShareManager(Activity activity, String title, String text, UMImage uMImage, String url) {
        this.activity = activity;
        this.title = title;
        this.text = text;
        this.uMImage = uMImage;
        this.url = url;
    }

    public static void share(Activity activity, String title, String text, int drawableId, String url) {
        new ShareAction(activity).setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.QQ, SHARE_MEDIA.SINA)
                .withTitle(title)
                .withText(text)
                .withMedia(new UMImage(activity, drawableId))
                .withTargetUrl(url)
                .setCallback(new MyShareListener(activity))
                .open();
    }

    public void shareWeiXin() {
        new ShareAction(activity).setPlatform(SHARE_MEDIA.WEIXIN).setCallback(new MyShareListener(activity))
                .withTitle(title)
                .withText(text)
                .withMedia(uMImage)
                .withTargetUrl(url)
                .share();
    }

    public void shareWeiXinCircle() {
        new ShareAction(activity).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE).setCallback(new MyShareListener(activity))
                .withTitle(title)
                .withText(text)
                .withMedia(uMImage)
                .withTargetUrl(url)
                .share();
    }

    public void shareSina() {
        new ShareAction(activity).setPlatform(SHARE_MEDIA.SINA).setCallback(new MyShareListener(activity))
                .withTitle(title)
                .withText(text)
                .withMedia(uMImage)
                .withTargetUrl(url)
                .share();
    }

    public void shareQQ() {
        new ShareAction(activity).setPlatform(SHARE_MEDIA.QQ).setCallback(new MyShareListener(activity))
                .withTitle(title)
                .withText(text)
                .withMedia(uMImage)
                .withTargetUrl(url)
                .share();
    }

}
