package com.zbase.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zbase.R;
import com.zbase.listener.IndexClickLisenter;

/**
 * 创建人：郑晓辉
 * 创建日期：2016/3/2
 * 描述：自己布局星星
 */
public class LinearStars extends LinearLayout {

    private int currentStar;//当前星星数量
    private int maxStar = 5;//星星最多数量，默认5颗
    private Drawable hollowStarDrawable;//空心星星图片资源id
    private Drawable solidStarDrawable;//实心星星图片资源id
    private int intervalSpace;//星星之间的间隔
    private int starSize;//星星大小
    private boolean gradeAble = false;//是否可以评分，默认false

    public LinearStars(Context context) {
        super(context);
        init(null, 0);
    }

    public LinearStars(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public LinearStars(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.LinearStars, defStyle, 0);

        currentStar = a.getInt(R.styleable.LinearStars_currentStar, currentStar);
        maxStar = a.getInt(R.styleable.LinearStars_maxStar, maxStar);

        if (a.hasValue(R.styleable.LinearStars_hollowStarDrawable)) {
            hollowStarDrawable = a.getDrawable(R.styleable.LinearStars_hollowStarDrawable);
            hollowStarDrawable.setCallback(this);
        }

        if (a.hasValue(R.styleable.LinearStars_solidStarDrawable)) {
            solidStarDrawable = a.getDrawable(
                    R.styleable.LinearStars_solidStarDrawable);
            solidStarDrawable.setCallback(this);
        }

        intervalSpace = a.getDimensionPixelOffset(R.styleable.LinearStars_intervalSpace, 0);
        starSize = a.getDimensionPixelOffset(R.styleable.LinearStars_starSize, 0);
        gradeAble = a.getBoolean(R.styleable.LinearStars_gradeAble, gradeAble);

        a.recycle();
        setCurrentStar(currentStar);
    }

    /**
     * 设置当前星星数量
     *
     * @param currentStar
     */
    public void setCurrentStar(int currentStar) {
        if (currentStar > maxStar) {
            return;
        }
        this.currentStar = currentStar;
        removeAllViews();
        for (int i = 0; i < maxStar; i++) {
            LinearLayout.LayoutParams layoutParams;
            if (starSize == 0) {
                layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            } else {
                layoutParams = new LinearLayout.LayoutParams(starSize, starSize);
            }
            if (i != 0) {
                layoutParams.leftMargin = intervalSpace;
            }
            if (i < currentStar) {
                ImageView starSolidImageView = new ImageView(getContext());
                starSolidImageView.setLayoutParams(layoutParams);
                starSolidImageView.setImageDrawable(solidStarDrawable);
                if (gradeAble) {
                    starSolidImageView.setOnClickListener(new IndexClickLisenter(i) {
                        @Override
                        public void onClick(View v) {
                            setCurrentStar(index + 1);
                        }
                    });
                }
                addView(starSolidImageView);
            } else {
                ImageView starEmptyImageView = new ImageView(getContext());
                starEmptyImageView.setLayoutParams(layoutParams);
                starEmptyImageView.setImageDrawable(hollowStarDrawable);
                if (gradeAble) {
                    starEmptyImageView.setOnClickListener(new IndexClickLisenter(i) {
                        @Override
                        public void onClick(View v) {
                            setCurrentStar(index + 1);
                        }
                    });
                }
                addView(starEmptyImageView);
            }
        }
    }

    public int getCurrentStar() {
        return currentStar;
    }

    public int getMaxStar() {
        return maxStar;
    }

    public void setMaxStar(int maxStar) {
        this.maxStar = maxStar;
    }

    public Drawable getHollowStarDrawable() {
        return hollowStarDrawable;
    }

    public void setHollowStarDrawable(Drawable hollowStarDrawable) {
        this.hollowStarDrawable = hollowStarDrawable;
    }

    public Drawable getSolidStarDrawable() {
        return solidStarDrawable;
    }

    public void setSolidStarDrawable(Drawable solidStarDrawable) {
        this.solidStarDrawable = solidStarDrawable;
    }
}
