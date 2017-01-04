package com.zdemo.activity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.editorpage.ShareActivity;
import com.umeng.socialize.media.UMImage;
import com.zdemo.R;

/**
 * 创建人：郑晓辉
 * 创建日期：2016/5/12
 * 描述：
 */
public class UMengShareActivity extends BaseActivity {

    private TextView tv_share;
    final SHARE_MEDIA[] displaylist = new SHARE_MEDIA[]
            {
                    SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.SINA,
                    SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE
            };

    @Override
    protected int inflateMainLayoutId() {
        return R.layout.activity_umeng_share;
    }

    @Override
    protected void initView(View view) {
        tv_share = (TextView) findViewById(R.id.tv_share);
    }

    @Override
    protected void setListener() {
        tv_share.setOnClickListener(this);
    }

    @Override
    protected void initValue() {


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_share:
                //                图片(本地资源引用)
//                UMImage image = new UMImage(ShareActivity.this,
//                        BitmapFactory.decodeResource(getResources(), R.drawable.image));
//                图片(本地绝对路径)
//                UMImage image = new UMImage(ShareActivity.this,
//                        BitmapFactory.decodeFile("/mnt/sdcard/icon.png")));
                UMImage image = new UMImage(UMengShareActivity.this, "http://www.umeng.com/images/pic/social/integrated_3.png");
                new ShareAction(this).setDisplayList(displaylist)
                        .withText("呵呵")
                        .withTitle("title")
                        .withTargetUrl("http://www.baidu.com")
                        .withMedia(image)
                        .setListenerList(umShareListener)
                        .open();
                break;
        }
    }

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(UMengShareActivity.this, platform + " 分享成功!", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(UMengShareActivity.this, platform + " 分享失败!", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(UMengShareActivity.this, platform + " 分享取消!", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
}
