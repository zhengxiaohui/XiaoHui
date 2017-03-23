package com.zbase.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zbase.R;

/**
 * 自己画一排多个小点，小点分为选中态和普通态，一般用于ViewPager切换的时候，小点跟着改变显示。
 * 代码中主要调用setDotCount()和setSelectedPosition()
 */
public class LinearDot extends LinearLayout {

    private Drawable normalDrawable;//正常小点的Drawable可以是图片，也可以是xml画的shap（更方便）
    private int normalDimension = 5;//正常小点的直径大小
    private Drawable selectedDrawable;//选中小点的Drawable可以是图片，也可以是xml画的shap（更方便）
    private int selectedDimension = 6;//选中小点的直径大小
    private int intervalDimension = 8;//小点之间的间距
    private int dotCount = 4;//总共有几个小点
    private int selectedPosition = 0;//选中的小点索引

    public LinearDot(Context context) {
        super(context);
        init(null, 0);
    }

    public LinearDot(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public LinearDot(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.LinearDot, defStyle, 0);

        if (a.hasValue(R.styleable.LinearDot_normalDrawable)) {
            normalDrawable = a.getDrawable(R.styleable.LinearDot_normalDrawable);
            normalDrawable.setCallback(this);
        }
        normalDimension = a.getDimensionPixelOffset(R.styleable.LinearDot_normalDimension, normalDimension);

        if (a.hasValue(R.styleable.LinearDot_selectedDrawable)) {
            selectedDrawable = a.getDrawable(R.styleable.LinearDot_selectedDrawable);
            selectedDrawable.setCallback(this);
        }
        selectedDimension = a.getDimensionPixelOffset(R.styleable.LinearDot_selectedDimension, selectedDimension);

        intervalDimension = a.getDimensionPixelOffset(R.styleable.LinearDot_intervalDimension, intervalDimension);

        dotCount = a.getInt(R.styleable.LinearDot_dotCount, dotCount);

        a.recycle();
        loadViews();
    }

    /**
     * 加载控件
     */
    private void loadViews() {
        if (dotCount == 0) {
            return;
        }
        removeAllViews();
        int intervalPxValue = 0;
        setGravity(Gravity.CENTER);
        for (int i = 0; i < dotCount; i++) {
            if (i != 0) {
                intervalPxValue = intervalDimension;
            }
            if (selectedPosition == i) {
                if (selectedDrawable != null) {
                    ImageView imageView = new ImageView(getContext());
                    imageView.setImageDrawable(selectedDrawable);
                    LayoutParams layoutParams = new LayoutParams(selectedDimension, selectedDimension);
                    layoutParams.leftMargin = intervalPxValue;
                    addView(imageView, layoutParams);
                }
            } else {
                if (normalDrawable != null) {
                    ImageView imageView = new ImageView(getContext());
                    imageView.setImageDrawable(normalDrawable);
                    LayoutParams layoutParams = new LayoutParams(normalDimension, normalDimension);
                    layoutParams.leftMargin = intervalPxValue;
                    addView(imageView, layoutParams);
                }
            }
        }
    }

    public Drawable getNormalDrawable() {
        return normalDrawable;
    }

    public void setNormalDrawable(Drawable normalDrawable) {
        this.normalDrawable = normalDrawable;
    }

    public float getNormalDimension() {
        return normalDimension;
    }

    public void setNormalDimension(int normalDimension) {
        this.normalDimension = normalDimension;
    }

    public Drawable getSelectedDrawable() {
        return selectedDrawable;
    }

    public void setSelectedDrawable(Drawable selectedDrawable) {
        this.selectedDrawable = selectedDrawable;
    }

    public float getSelectedDimension() {
        return selectedDimension;
    }

    public void setSelectedDimension(int selectedDimension) {
        this.selectedDimension = selectedDimension;
    }

    public float getIntervalDimension() {
        return intervalDimension;
    }

    public void setIntervalDimension(int intervalDimension) {
        this.intervalDimension = intervalDimension;
    }

    public int getDotCount() {
        return dotCount;
    }

    /**
     * 设置点的总个数，并从新加载控件
     *
     * @param dotCount
     */
    public void setDotCount(int dotCount) {
        this.dotCount = dotCount;
        loadViews();
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    /**
     * 设置选中点的位置，并从新加载控件
     *
     * @param selectedPosition
     */
    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
        loadViews();
    }

    /**
     * 设置ViewPager对个数和选中位置进行关联
     *
     * @param viewPager
     */
    public void setupWithViewPager(final ViewPager viewPager) {
        setDotCount(viewPager.getAdapter().getCount());
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setSelectedPosition(position % viewPager.getAdapter().getCount());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

}
