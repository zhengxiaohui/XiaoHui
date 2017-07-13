package com.oranllc.umengsocialshare.common;

import com.umeng.socialize.PlatformConfig;

/**
 * 创建人：郑晓辉
 * 创建日期：2016/5/16
 * 描述：
 */
public class UMengShareConfig {

    public static void init(){
        PlatformConfig.setWeixin("wx7706f375dc58472a", "0bdbdd917bdcb19a54c42d3098772b2c");//微信 appid appsecret
        PlatformConfig.setSinaWeibo("2196713665", "d14b1d2247224882fdcb3c1fc5c40874");//新浪微博 appkey appsecret
        PlatformConfig.setQQZone("1106202535", "IDyTJg8P9PNw1uQf");// QQ和Qzone appid appkey


//        PlatformConfig.setAlipay("2015111700822536");
//        //支付宝 appid
//        PlatformConfig.setYixin("yxc0614e80c9304c11b0391514d09f13bf");
//        //易信 appkey
//        PlatformConfig.setTwitter("3aIN7fuF685MuZ7jtXkQxalyi", "MK6FEYG63eWcpDFgRYw4w9puJhzDl0tyuqWjZ3M7XJuuG7mMbO");
//        //Twitter appid appkey
//        PlatformConfig.setPinterest("1439206");
//        //Pinterest appid
//        PlatformConfig.setLaiwang("laiwangd497e70d4", "d497e70d4c3e4efeab1381476bac4c5e");
//        //来往 appid
    }

    //用法如下：


//    /**
//     * 分享
//     */
//    public void requestShare(final Activity activity) {
//        OkHttpUtils.get(HttpConstant.GET_SHARE)
//                .tag(this)
//                .execute(new SignJsonCallback<ShareBean>(context, ShareBean.class) {
//                    @Override
//                    public void onResponse(boolean isFromCache, ShareBean shareBean, Request request, @Nullable Response response) {
//                        if (shareBean.getCodeState() == 1) {
//                            ShareBean.Data data = shareBean.getData();
//                            String shareTitle = data.getsharetitle();
//                            String imgUrl = data.getImgUrl();
//                            String shareText = data.getShareText();
//                            String goUrl = data.getGoUrl();
//                            share(activity, shareTitle, shareText, imgUrl, goUrl);
//
//                        } else {
//                            PopUtil.toast(context, shareBean.getMessage());
//                        }
//                    }
//                });
//    }
//    //使用友盟默认显示面板分享
//    public void share(Activity activity, String title, String text, String imagePath, String url) {
//        new ShareAction(activity).setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.QQ, SHARE_MEDIA.SINA)
//                .withTitle(title)
//                .withText(text)
//                .withMedia(new UMImage(activity, imagePath))
//                .withTargetUrl(url)
//                .setCallback(new MyShareListener(activity))
//                .open();
//    }

    /**
     * 使用自定义界面加ShareManager管理友盟分享事件
     */
//    private void showPopupWindow() {
//        if (alphaPopupWindow == null) {
//            View view = inflate(R.layout.pw_share);
//            alphaPopupWindow = new AlphaPopupWindow(context, view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            alphaPopupWindow.setDark(true);
//            String url = "www.baidu.com";
//            final ShareManager shareManager = new ShareManager(this, getString(R.string.app_name), getString(R.string.app_name), new UMImage(this, R.mipmap.ic_launcher), url);
//            view.findViewById(R.id.tv_weixin_circle).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    shareManager.shareWeiXinCircle();
//                }
//            });
//            view.findViewById(R.id.tv_weixin).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    shareManager.shareWeiXin();
//                }
//            });
//            view.findViewById(R.id.tv_weibo).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    shareManager.shareSina();
//                }
//            });
//            view.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    alphaPopupWindow.dismiss();
//                }
//            });
//        }
//        alphaPopupWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
//    }
}
