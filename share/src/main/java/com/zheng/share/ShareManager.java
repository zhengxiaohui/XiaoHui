package com.zheng.share;

import android.content.Context;

import com.zheng.share.qq.QQShareManager;
import com.zheng.share.weibo.WeiBoShareManager;
import com.zheng.share.weixin.WeiXinShareManager;

/**
 * 示例
 *
 * @author zhengxiaohui
 * @since 2017/7/13
 */
public class ShareManager {

    private QQShareManager qqShareManager;
    private WeiXinShareManager weiXinShareManager;
    private WeiBoShareManager weiBoShareManager;

    private static ShareManager shareManager;

    private ShareManager(){

    }

    public static ShareManager getInstance(){
        if (shareManager==null) {
            shareManager=new ShareManager();
        }
        return shareManager;
    }

    public void setWeiBoShareManager(WeiBoShareManager weiBoShareManager) {
        this.weiBoShareManager = weiBoShareManager;
    }

    public void init(Context context){
        qqShareManager=new QQShareManager(context);
        weiXinShareManager=new WeiXinShareManager(context);
        weiBoShareManager=new WeiBoShareManager(context);
    }

    public QQShareManager getQQShareManager() {
        return qqShareManager;
    }

    public WeiXinShareManager getWeiXinShareManager() {
        return weiXinShareManager;
    }

    public WeiBoShareManager getWeiBoShareManager() {
        return weiBoShareManager;
    }

}
