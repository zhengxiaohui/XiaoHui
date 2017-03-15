package com.karics.library.zxing.my;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;

import com.karics.library.zxing.android.CaptureActivity;

/**
 * 创建人：郑晓辉
 * 创建日期：2016/9/12
 * 描述：二维码扫描
 * 用法：
 * QRCodeManager.scan(this);
 @Override
 public void onActivityResult(int requestCode, int resultCode, Intent data) {
super.onActivityResult(requestCode, resultCode, data);
QRCodeManager.onActivityResult(requestCode, resultCode, data, new QRCodeManager.OnQRCodeObtainListener() {
@Override
public void onQRCodeObtain(String content) {
textView.setText(content);
}
});
}
 */
public class QRCodeManager {

    private static final int REQUEST_CODE_SCAN = 0x0000;
    private static final String DECODED_CONTENT_KEY = "codedContent";
    private static final String DECODED_BITMAP_KEY = "codedBitmap";

    public interface OnQRCodeObtainListener {
        void onQRCodeObtain(String content);
    }

    public static void scan(Activity activity) {
        Intent intent = new Intent(activity, CaptureActivity.class);
        activity.startActivityForResult(intent, REQUEST_CODE_SCAN);
    }

    public static void scan(Fragment fragment) {
        Intent intent = new Intent(fragment.getActivity(), CaptureActivity.class);
        fragment.startActivityForResult(intent, REQUEST_CODE_SCAN);
    }

    public static void onActivityResult(int requestCode, int resultCode, Intent data, OnQRCodeObtainListener onQRCodeObtainListener) {
        // 扫描二维码/条码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                String content = data.getStringExtra(DECODED_CONTENT_KEY);
                Bitmap bitmap = data.getParcelableExtra(DECODED_BITMAP_KEY);
                if (onQRCodeObtainListener != null) {
                    onQRCodeObtainListener.onQRCodeObtain(content);
                }
            }
        }
    }
}
