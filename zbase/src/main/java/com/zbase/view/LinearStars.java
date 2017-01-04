package com.zbase.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zbase.R;

/**
 * 创建人：郑晓辉
 * 创建日期：2016/3/2
 * 描述：自己布局星星
 */
public class LinearStars extends LinearLayout {

    private int starMaxNum = 5;//星星最多数量，默认5颗
    private Drawable hollowStarDrawable;//空心星星图片资源id
    private Drawable solidStarDrawable;//实心星星图片资源id

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

        starMaxNum = a.getInt(R.styleable.LinearStars_starMaxNum, starMaxNum);

        if (a.hasValue(R.styleable.LinearStars_hollowStarDrawable)) {
            hollowStarDrawable = a.getDrawable(R.styleable.LinearStars_hollowStarDrawable);
            hollowStarDrawable.setCallback(this);
        }

        if (a.hasValue(R.styleable.LinearStars_solidStarDrawable)) {
            solidStarDrawable = a.getDrawable(
                    R.styleable.LinearStars_solidStarDrawable);
            solidStarDrawable.setCallback(this);
        }

        a.recycle();
    }

    /**
     * 设置当前星星数量
     *
     * @param currentStarNum
     */
    public void setCurrentStarNum(int currentStarNum) {
        if (currentStarNum > starMaxNum) {
            return;
        }
        removeAllViews();
        for (int i = 0; i < starMaxNum; i++) {
            if (i < currentStarNum) {
                ImageView starSolidImageView = new ImageView(getContext());
                starSolidImageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                starSolidImageView.setImageDrawable(solidStarDrawable);
                addView(starSolidImageView);
            } else {
                ImageView starEmptyImageView = new ImageView(getContext());
                starEmptyImageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                starEmptyImageView.setImageDrawable(hollowStarDrawable);
                addView(starEmptyImageView);
            }
        }
    }

    public int getStarMaxNum() {
        return starMaxNum;
    }

    public void setStarMaxNum(int starMaxNum) {
        this.starMaxNum = starMaxNum;
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
