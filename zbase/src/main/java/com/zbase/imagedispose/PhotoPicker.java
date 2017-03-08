package com.zbase.imagedispose;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;

import com.yalantis.ucrop.UCrop;
import com.zbase.common.ZLog;

import static android.app.Activity.RESULT_OK;

/**
 * 创建人：郑晓辉
 * 创建日期：2016/9/8
 * 描述：拍照或者选择图库
 * 结合框架的用法：
 initPhotoPicker();

 switch (position) {
 case 0:
 photoPicker.openCameraAndCrop();
 break;
 case 1:
 photoPicker.openPhotoAndCrop();
 break;
 }

 protected void onPhotoPickerReturn(String imagePath) {
 ImageLoader.getInstance().displayImage(Const.URI_PRE + imagePath, iv_photo, ImageUtil.getDisplayImageOptions(R.mipmap.list_item_default));
 }

 */
public class PhotoPicker implements CropHandler {

    private Activity activity;
    private Fragment fragment;
    private CropParams cropParams;
    private boolean isFragment;
    private boolean hasCrop;//是否裁切
    private float wScale, hScale;//宽高比例

    public interface OnPhotoPickedListener {
        void onPhotoPicked(String imagePath);
    }

    private OnPhotoPickedListener onPhotoPickedListener;

    public PhotoPicker(Activity activity, OnPhotoPickedListener onPhotoPickedListener) {
        this.activity = activity;
        cropParams = new CropParams(activity);
        this.onPhotoPickedListener = onPhotoPickedListener;
        isFragment = false;
    }

    public PhotoPicker(Fragment fragment, OnPhotoPickedListener onPhotoPickedListener) {
        this.fragment = fragment;
        cropParams = new CropParams(fragment.getActivity());
        this.onPhotoPickedListener = onPhotoPickedListener;
        isFragment = true;
    }

    /**
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        CropHelper.handleResult(this, requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            Uri resultUri = UCrop.getOutput(data);
            if (onPhotoPickedListener != null) {
                onPhotoPickedListener.onPhotoPicked(resultUri.getPath());
            }
        } else if (resultCode == UCrop.RESULT_ERROR) {
            Throwable cropError = UCrop.getError(data);
            ZLog.dZheng(cropError.getMessage());
        }
    }

    /**
     * 清理图片缓存，必须在activity的onDestroy调用
     * 示例：@Override
     * protected void onDestroy() {
     * photoCropper.onDestroy();
     * super.onDestroy();
     * }
     */
    public void onDestroy() {
        CropHelper.clearCacheDir();
    }

    @Override
    public CropParams getCropParams() {
        return cropParams;
    }

    @Override
    public void onPhotoCropped(Uri uri) {
        if (!cropParams.compress) {
//            if (onPhotoPickedListener != null) {
//                onPhotoPickedListener.onPhotoPicked(uri.getPath());
//            }
        }
    }

    @Override
    public void onCompressed(Uri uri) {
        if (hasCrop) {
            if (isFragment) {
                UCrop.of(uri, uri).withAspectRatio(wScale, hScale).start(fragment.getActivity(), fragment);
            } else {
                UCrop.of(uri, uri).withAspectRatio(wScale, hScale).start(activity);
            }
        } else {
            if (onPhotoPickedListener != null) {
                onPhotoPickedListener.onPhotoPicked(uri.getPath());
            }
        }
    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onFailed(String message) {

    }

    @Override
    public void handleIntent(Intent intent, int requestCode) {
        if (isFragment) {
            fragment.startActivityForResult(intent, requestCode);
        } else {
            activity.startActivityForResult(intent, requestCode);
        }
    }

    public void openCameraAndCrop() {
        openCameraAndCrop(1, 1);
    }

    public void openPhotoAndCrop() {
        openPhotoAndCrop(1, 1);
    }

    /**
     * 拍照并裁切
     *
     * @param x 裁切比例宽
     * @param y 裁切比例高
     */
    public void openCameraAndCrop(float x, float y) {
        wScale = x;
        hScale = y;
        hasCrop = true;
        openCamera();
    }

    /**
     * 选择图库照片并裁切
     *
     * @param x 裁切比例宽
     * @param y 裁切比例高
     */
    public void openPhotoAndCrop(float x, float y) {
        wScale = x;
        hScale = y;
        hasCrop = true;
        openPhoto();
    }

    /**
     * 拍照
     */
    public void openCamera() {
        cropParams.refreshUri();
        cropParams.enable = false;
        cropParams.compress = true;
        Intent intent = CropHelper.buildCameraIntent(cropParams);
        if (isFragment) {
            fragment.startActivityForResult(intent, CropHelper.REQUEST_CAMERA);
        } else {
            activity.startActivityForResult(intent, CropHelper.REQUEST_CAMERA);
        }
    }

    /**
     * 选择图库照片
     */
    public void openPhoto() {
        cropParams.refreshUri();
        cropParams.enable = false;
        cropParams.compress = true;
        Intent intent = CropHelper.buildGalleryIntent(cropParams);
        if (isFragment) {
            fragment.startActivityForResult(intent, CropHelper.REQUEST_PICK);
        } else {
            activity.startActivityForResult(intent, CropHelper.REQUEST_PICK);
        }
    }
}
