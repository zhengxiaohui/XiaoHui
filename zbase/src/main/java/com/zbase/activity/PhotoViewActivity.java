package com.zbase.activity;


import android.graphics.Bitmap;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zbase.R;
import com.zbase.adapter.ZViewPagerAdapter;
import com.zbase.common.BaseApplication;
import com.zbase.util.ImageUtil;
import com.zbase.view.viewpager.HackyViewPager;

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * 创建人：LX
 * 创建日期：2016/8/25
 * 描述：
 */
public class PhotoViewActivity extends AbstractBaseActivity {

    public final static String IMAGE_PATH_LIST = "image_path_list";
    public final static String DEFAULT_DRAWABLE_ID = "default_drawable_id";
    public final static String BITMAP = "bitmap";
    public final static String CURRENT_POSITION = "current_position";
    private List<View> photoViewList = new ArrayList<>();
    private HackyViewPager hackyViewPager;
    private ZViewPagerAdapter zViewPagerAdapter;
    private TextView tv_tip_number;
    private PhotoView photoView;

    @Override
    protected int inflateBaseLayoutId() {
        return R.layout.zbase_activity_photoview;
    }

    @Override
    protected int inflateMainLayoutId() {
        return 0;
    }

    @Override
    protected void initBaseView(View view) {

    }

    @Override
    protected void initView(View view) {
        tv_tip_number = (TextView) findViewById(R.id.tv_tip_number);
        hackyViewPager = (HackyViewPager) findViewById(R.id.hackyViewPager);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initValue() {
        hideStatusBar();
        final ArrayList<String> imagePathList = getIntent().getStringArrayListExtra(IMAGE_PATH_LIST);
        int drawableId = getIntent().getIntExtra(DEFAULT_DRAWABLE_ID, 0);
        int currentPosition = getIntent().getIntExtra(CURRENT_POSITION, 0);
        Bitmap bitmap = getIntent().getParcelableExtra(BITMAP);

        if (imagePathList != null && imagePathList.size() > 0 && drawableId != 0) {//多张图片地址
            tv_tip_number.setText("1/" + imagePathList.size());
            for (String imagePath : imagePathList) {
                photoView = new PhotoView(this);
                photoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
                    @Override
                    public void onPhotoTap(View view, float x, float y) {
                        onBackPressed();
                    }
                });
                ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                photoView.setLayoutParams(layoutParams);
                ImageLoader.getInstance().displayImage(imagePath, photoView, ImageUtil.getDisplayImageOptions(drawableId));
                photoViewList.add(photoView);
            }
        } else {
            tv_tip_number.setVisibility(View.INVISIBLE);
            photoView = new PhotoView(this);
            photoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
                @Override
                public void onPhotoTap(View view, float x, float y) {
                    onBackPressed();
                }
            });
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            photoView.setLayoutParams(layoutParams);
            photoView.setImageBitmap(bitmap);
            photoViewList.add(photoView);
        }
        zViewPagerAdapter = new ZViewPagerAdapter(photoViewList);
        hackyViewPager.setAdapter(zViewPagerAdapter);
        hackyViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tv_tip_number.setText(position + 1 + "/" + photoViewList.size());
                PhotoView photoView = (PhotoView) photoViewList.get(position);
                photoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
                    @Override
                    public void onPhotoTap(View view, float x, float y) {
                        onBackPressed();
                    }
                });
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        hackyViewPager.setCurrentItem(currentPosition);
    }

    @Override
    public BaseApplication getMyApplication() {
        return (BaseApplication) getApplication();
    }

    @Override
    public void onClick(View v) {

    }
}




