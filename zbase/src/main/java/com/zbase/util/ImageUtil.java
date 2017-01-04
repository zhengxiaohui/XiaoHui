package com.zbase.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.zbase.assist.FastBlur;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImageUtil {

    public static Drawable bitmapToDrawable(Context context, Bitmap bitmap) {
        return new BitmapDrawable(context.getResources(), bitmap);
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        return ((BitmapDrawable) drawable).getBitmap();
    }

    /**
     * 转成圆图片
     *
     * @param bitmap
     * @return
     */
    public static Bitmap toOval(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        //        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(0xFF000000);
        canvas.drawOval(rectF, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    /**
     * 转成圆角图片
     *
     * @param bitmap
     * @param pixels
     * @return
     */
    public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        //        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(0xFF000000);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    /**
     * 根据URI获取Bitmap
     *
     * @param context
     * @param uri
     * @return
     */
    public static Bitmap decodeUriAsBitmap(Context context, Uri uri) {
        if (context == null || uri == null) return null;

        Bitmap bitmap;
        try {
            bitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }

    /**
     * 从URI中获得照片
     *
     * @param
     * @throws IOException
     */
    public static Bitmap getBitmapFormUri(Context context, Uri uri, int outWidth, int outHeight) throws IOException {
        // 取得图片
        InputStream temp = context.getContentResolver().openInputStream(uri);
        BitmapFactory.Options options = new BitmapFactory.Options();
        // 这个参数代表，不为bitmap分配内存空间，只记录一些该图片的信息（例如图片大小），说白了就是为了内存优化
        options.inJustDecodeBounds = true;
        // 通过创建图片的方式，取得options的内容（这里就是利用了java的地址传递来赋值）
        BitmapFactory.decodeStream(temp, null, options);
        // 关闭流
        temp.close();
        // 生成压缩的图片
        int i = 0;
        Bitmap tempBmp = null;
        while (true) {
            // 这一步是根据要设置的大小，使宽和高都能满足
            if ((options.outWidth >> i <= outWidth) && (options.outHeight >> i <= outHeight)) {
                // 重新取得流，注意：这里一定要再次加载，不能二次使用之前的流！
                temp = context.getContentResolver().openInputStream(uri);
                // 这个参数表示 新生成的图片为原始图片的几分之一。
                int xScale = options.outWidth / outWidth;
                int yScale = options.outHeight / outHeight;
                options.inSampleSize = xScale > yScale ? xScale : yScale;
                // 这里之前设置为了true，所以要改为false，否则就创建不出图片
                options.inJustDecodeBounds = false;
                tempBmp = BitmapFactory.decodeStream(temp, null, options);
                break;
            }
            i += 1;
        }
        return tempBmp;
    }

    /**
     * 得到固定宽的比列图片
     *
     * @param context
     * @return
     * @throws IOException
     */
    public static Bitmap getBitmapFormPath(Context context, String path, int width, int height) throws IOException {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Config.ARGB_4444;
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        int xScale = options.outWidth / width;
        int yScale = options.outHeight / height;
        options.inSampleSize = xScale > yScale ? xScale : yScale;
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }

    /**
     * 裁切图片
     *
     * @param bitmap
     * @param corpWith
     * @param corpHeight
     * @return
     */
    public static Bitmap corpBitmap(Bitmap bitmap, int corpWith, int corpHeight) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        if (null == bitmap || bitmap.isRecycled() || width <= 0 || height <= 0) {
            return null;
        }
        int srcLeft = 0;
        int srcTop = 0;
        //      int dstLeft = 0;
        //      int dstTop = 0;
        if (corpWith >= width) {
            corpWith = width;
        } else {
            srcLeft = (width - corpWith) / 2;
        }
        if (corpHeight >= height) {
            corpHeight = height;
        } else {
            srcTop = (height - corpHeight) / 2;
        }
        //        // Crop the subset from the original Bitmap.
        //        final Bitmap croppedBitmap = Bitmap.createBitmap(bitmap, srcLeft, srcTop, corpWith, corpHeight);
        return Bitmap.createBitmap(bitmap, srcLeft, srcTop, corpWith, corpHeight);
    }

    /**
     * 对图片进行裁切
     *
     * @param uri     内容路径
     * @param outFile 输出路径
     * @return
     */
    public static Intent getImageCropIntent(Uri uri, File outFile) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("scale", true);// 防止资源太小产生的黑边
        intent.putExtra("scaleUpIfNeeded", true);
        intent.putExtra("return-data", false);
        intent.putExtra("noFaceDetection", true);
        intent.putExtra("output", Uri.fromFile(outFile));
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        return intent;
    }

    /**
     * 绘制实心圆弧
     *
     * @param width
     * @param height
     * @return
     */
    public static ShapeDrawable createOvalShape(int width, int height) {
        OvalShape ovalShape = new OvalShape();
        ovalShape.resize(width, height);
        ShapeDrawable shapeDrawable = new ShapeDrawable(ovalShape);
        shapeDrawable.getPaint().setStyle(Paint.Style.FILL);
        shapeDrawable.setIntrinsicWidth(width);//设置固定宽高，否则不显示
        shapeDrawable.setIntrinsicHeight(width);
        return shapeDrawable;
    }

    public static Bitmap convertViewToBitmap(View view) {

        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),
                Config.ARGB_8888);
        //利用bitmap生成画布
        Canvas canvas = new Canvas(bitmap);
        //把view中的内容绘制在画布上
        view.draw(canvas);
        return bitmap;
    }

    public static Bitmap convertViewToBitmap2(View view) {
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        return bitmap;
    }

    /**
     * 图片做高斯模糊并设置为view的背景
     *
     * @param context
     * @param bitmap  做高斯模糊的图片
     * @param view    设置背景的view
     */
    private void blur(final Context context, final Bitmap bitmap, final View view) {
        final float scaleFactor = 8;//图片缩放比例；
        final float radius = 2;//模糊程度
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
                Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, (int) (view.getMeasuredWidth() / scaleFactor), (int) (view.getMeasuredHeight() / scaleFactor), true);
                Bitmap blurBitmap = FastBlur.doBlur(scaledBitmap, (int) radius, true);
                if (scaledBitmap != blurBitmap) {
                    scaledBitmap.recycle();
                    scaledBitmap = null;
                }
                if (blurBitmap != null) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        view.setBackground(new BitmapDrawable(context.getResources(), blurBitmap));
                    } else {
                        view.setBackgroundDrawable(new BitmapDrawable(context.getResources(), blurBitmap));
                    }
                }
            }
        });
    }

    public static DisplayImageOptions getDisplayImageOptions() {
        return getDisplayImageOptions(0, 0, null);
    }

    public static DisplayImageOptions getDisplayImageOptions(int defaultImageRes) {
        return getDisplayImageOptions(defaultImageRes, 0, null);
    }

    public static DisplayImageOptions getDisplayImageOptions(int defaultImageRes, float diameterDP, Context context) {
        DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder().cloneFrom(DisplayImageOptions.createSimple())
                .bitmapConfig(Config.RGB_565).imageScaleType(ImageScaleType.IN_SAMPLE_INT).cacheOnDisk(true).considerExifParams(true);
        if (defaultImageRes != 0) {
            builder.showImageOnLoading(defaultImageRes).showImageForEmptyUri(defaultImageRes).showImageOnFail(defaultImageRes);
        }
        if (context != null && diameterDP != 0) {
            builder.displayer(new RoundedBitmapDisplayer(ScreenUtil.dip2px(context, diameterDP)));
        }
        return builder.build();
    }

    /**
     * 设置图片的饱和度，可将图片设置成灰态
     *
     * @param bitmap       设置的图片
     * @param saturability 图片的饱和度 传入一个大于1的数字将增加饱和度，而传入一个0～1之间的数字会减少饱和度。0值将产生一幅灰度图像。
     * @return
     */
    public static Bitmap createSaturationBitmap(Bitmap bitmap, float saturability) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap faceIconGreyBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(faceIconGreyBitmap);
        Paint paint = new Paint();
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(saturability);
        ColorMatrixColorFilter colorMatrixFilter = new ColorMatrixColorFilter(colorMatrix);
        paint.setColorFilter(colorMatrixFilter);
        canvas.drawBitmap(bitmap, 0, 0, paint);
        return faceIconGreyBitmap;
    }

    /**
     * 设置图片成灰色
     *
     * @param bitmap
     * @return
     */
    public static Bitmap createGreyBitmap(Bitmap bitmap) {
        return createSaturationBitmap(bitmap, 0);
    }

    /**
     * 得到指定路径图片的options，不加载内存
     *
     * @param srcPath 源图片路径
     * @return Options {@link BitmapFactory.Options}
     */
    public static BitmapFactory.Options getBitmapOptions(String srcPath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(srcPath, options);
        return options;
    }

    /**
     * 压缩指定路径图片，并将其保存在缓存目录中;<br>
     * 通过isDelSrc判定是否删除源文件，并获取到缓存后的图片路径;<br>
     * 图片过大可能OOM
     *
     * @param context
     * @param srcPath
     * @param rqsW
     * @param rqsH
     * @param isDelSrc
     * @return
     */
    public static String compressBitmap(Context context, String srcPath, Bitmap.CompressFormat format, int rqsW, int rqsH, boolean isDelSrc) {
        Bitmap bitmap = compressBitmap(srcPath, rqsW, rqsH);
        File srcFile = new File(srcPath);
        String desPath = getImageCacheDir(context) + srcFile.getName();
        clearCropFile(desPath);
        int degree = getDegrees(srcPath);
        try {
            if (degree != 0) bitmap = rotateBitmap(bitmap, degree);
            File file = new File(desPath);
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(format, 70, fos);
            fos.close();
            if (isDelSrc) srcFile.deleteOnExit();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return desPath;
    }

    /**
     * 根据Uri删除文件
     *
     * @param uri 文件Uri
     * @return 成功与否
     */
    public static boolean clearCropFile(Uri uri) {
        if (uri == null) {
            return false;
        }
        return clearCropFile(uri.getPath());
    }

    /**
     * 删除文件
     *
     * @param path 文件路径
     * @return 成功与否
     */
    public static boolean clearCropFile(String path) {
        if (TextUtils.isEmpty(path)) {
            return false;
        }
        //有时文件明明存在  但 file.exists为false

        File file = new File(path);

        //System.out.println("工具判断:"+FileUtils.exists(file)+" 原始判断:"+file.exists()+" \npath:"+file.getPath());

        if (file.exists()) {
            boolean result = file.delete();
            if (result) {
                System.out.println("Cached crop file cleared.");
            } else {
                System.out.println("Failed to clear cached crop file.");
            }
            return result;
        } else {
            System.out.println("Trying to clear cached crop file but it does not exist.");
        }

        return false;
    }

    /**
     * 根据屏幕宽高和图片宽高对比的比例压缩图片
     *
     * @param context
     * @param imagePath
     * @return
     */
    public static String compressBitmap(Context context, String imagePath) {
        int screenMax = Math.max(ScreenUtil.getScreenWidth(context), ScreenUtil.getScreenHeight(context));
        return compressBitmap(context, imagePath, screenMax);
    }

    /**
     * 根据希望得到图片大小的比例压缩图片
     *
     * @param context
     * @param imagePath
     * @param maxWidthHeight 希望压缩后图片的宽和高的较大值
     * @return
     */
    public static String compressBitmap(Context context, String imagePath, int maxWidthHeight) {
        BitmapFactory.Options options = getBitmapOptions(imagePath);
        int imgMax = Math.max(options.outWidth, options.outHeight);
        int inSimpleSize = 1;
        if (maxWidthHeight <= imgMax) {
            inSimpleSize = Math.max(maxWidthHeight, imgMax) / Math.min(maxWidthHeight, imgMax);
        }
        return compressBitmap(context, imagePath, Bitmap.CompressFormat.JPEG, options.outWidth / inSimpleSize, options.outHeight / inSimpleSize, false);
    }

    /**
     * 压缩指定路径的图片，并得到图片对象
     *
     * @param path bitmap source path
     * @return Bitmap {@link Bitmap}
     */
    public static Bitmap compressBitmap(String path, int rqsW, int rqsH) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        options.inSampleSize = calculateInSampleSize(options, rqsW, rqsH);
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }

    /**
     * 基于质量的压缩算法， 此方法未解决压缩后图像失真问题
     * <br> 可先调用比例压缩适当压缩图片后，再调用此方法可解决上述问题
     * <br> 压缩格式为JPEG
     *
     * @param bitmap
     * @param maxBytes 压缩后的图像最大大小 单位为byte
     * @return
     */
    public static Bitmap compressBitmap(Bitmap bitmap, long maxBytes) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int options = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
            while (baos.toByteArray().length > maxBytes) {
                if (options == 0) {//如果图片的质量已降到最低则，不再进行压缩
                    break;
                }
                options -= 10;
                if (options < 0) {//如果图片质量小于10，则将图片的质量压缩到最小值
                    options = 0;
                }
                baos.reset();
                bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);//将压缩后的图片保存到baos中
            }
            byte[] bts = baos.toByteArray();
            Bitmap bmp = BitmapFactory.decodeByteArray(bts, 0, bts.length);
            baos.close();
            return bmp;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    /**
     * calculate the bitmap sampleSize
     *
     * @param options
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options, int rqsW, int rqsH) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (rqsW == 0 || rqsH == 0) return 1;
        if (height > rqsH || width > rqsW) {
            final int heightRatio = Math.round((float) height / (float) rqsH);
            final int widthRatio = Math.round((float) width / (float) rqsW);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    /**
     * 获取图片缓存路径
     *
     * @param context
     * @return
     */
    private static String getImageCacheDir(Context context) {
        String dir = context.getCacheDir() + "Image" + File.separator;
        File file = new File(dir);
        if (!file.exists()) file.mkdirs();
        return dir;
    }

    /**
     * get the orientation of the bitmap {@link ExifInterface}
     *
     * @param path 图片路径
     * @return
     */
    public static int getDegrees(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * rotate the bitmap
     *
     * @param bitmap
     * @param degrees
     * @return
     */
    public static Bitmap rotateBitmap(Bitmap bitmap, int degrees) {
        if (bitmap != null) {
            Matrix m = new Matrix();
            m.postRotate(degrees);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
            return bitmap;
        }
        return null;
    }

    /**
     * 保存图片
     *
     * @param bitmap
     * @param path
     * @param quality quality must be 0..100
     * @return
     */
    public static boolean saveBitmap(Bitmap bitmap, String path, int quality) {
        try {
            File file = new File(path);
            File parent = file.getParentFile();
            if (!parent.exists()) {
                parent.mkdirs();
            }
            FileOutputStream fos = new FileOutputStream(file);
            boolean b = bitmap.compress(CompressFormat.JPEG, quality, fos);
            fos.flush();
            fos.close();
            return b;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 设置网络图片控件的高度
     * 根据网络图片的宽高和设置图片控件的宽度自动设置图片控件的高度，一般用于图片宽度固定，高度不固定的场景，如自定义消息界面等
     *
     * @param imageView
     * @param ImageViewWidth    自己计算ImageView的宽度
     * @param actualImageWidht  图片实际宽度
     * @param actualImageHeight 图片实际高度
     */
    public static void setNetImageViewHeight(ImageView imageView, int ImageViewWidth, int actualImageWidht, int actualImageHeight) {
        imageView.getLayoutParams().width = ImageViewWidth;
        imageView.getLayoutParams().height = imageView.getLayoutParams().width * actualImageHeight / actualImageWidht;
    }

}
