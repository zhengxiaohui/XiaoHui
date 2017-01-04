package com.zbase.decoration;

/**
 * 创建人：郑晓辉
 * 创建日期：2016/6/15
 * 描述：
 */
/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * limitations under the License.
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.State;
import android.view.View;

/**
 * This class is from the v7 samples of the Android SDK. It's not by me!
 * <p/>
 * See the license above for details.
 */
public class LinearLayoutDecoration extends RecyclerView.ItemDecoration {

    private Context context;

    private static final int[] ATTRS = new int[]{
            android.R.attr.listDivider
    };

    private Drawable lineDrawable;
    private int drawableId;
    private int orientation;//水平0，垂直1
    private int leftPadding;//垂直列表有效
    private int rightPadding;//垂直列表有效
    private int topPadding;//水平列表有效
    private int bottomPadding;//水平列表有效

    /**
     * 水平0，垂直1
     *
     * @param orientation
     */
    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public void setDrawableId(@DrawableRes int drawableId) {
        this.drawableId = drawableId;
        lineDrawable = context.getResources().getDrawable(drawableId);
    }

    public void setLeftPadding(int leftPadding) {
        this.leftPadding = leftPadding;
    }

    public void setRightPadding(int rightPadding) {
        this.rightPadding = rightPadding;
    }

    public void setTopPadding(int topPadding) {
        this.topPadding = topPadding;
    }

    public void setBottomPadding(int bottomPadding) {
        this.bottomPadding = bottomPadding;
    }

    public LinearLayoutDecoration(Context context) {
        this.context = context;
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        lineDrawable = a.getDrawable(0);//默认系统分割线
        a.recycle();
        orientation = 1;//列表默认垂直方向
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, State state) {

        if (orientation == 1) {
            drawVertical(c, parent);
        } else {
            drawHorizontal(c, parent);
        }

    }

    public void drawVertical(Canvas c, RecyclerView parent) {
        final int left = parent.getPaddingLeft() + leftPadding;
        final int right = parent.getWidth() - parent.getPaddingRight() - rightPadding;
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount - 1; i++) {
            final View child = parent.getChildAt(i);
            long itemId = parent.getLayoutManager().getItemViewType(child);
            if (itemId == 0) {
                continue;
            }
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + lineDrawable.getIntrinsicHeight();
            lineDrawable.setBounds(left, top, right, bottom);
            lineDrawable.draw(c);
        }
    }

    public void drawHorizontal(Canvas c, RecyclerView parent) {
        final int top = parent.getPaddingTop() + topPadding;
        final int bottom = parent.getHeight() - parent.getPaddingBottom() - bottomPadding;
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount - 1; i++) {
            final View child = parent.getChildAt(i);
            long itemId = parent.getLayoutManager().getItemViewType(child);
            if (itemId == 0) {
                continue;
            }
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int left = child.getRight() + params.rightMargin;
            final int right = left + lineDrawable.getIntrinsicHeight();
            lineDrawable.setBounds(left, top, right, bottom);
            lineDrawable.draw(c);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, State state) {
        int position = parent.getChildAdapterPosition(view);
        if (position != 0 && position != state.getItemCount() - 1 && position != state.getItemCount() - 2) {//去掉ZBaseRecyclerAdapter中默认添加的头部和尾部，占位不画分割线
            if (orientation == 1) {
                outRect.set(0, 0, 0, lineDrawable.getIntrinsicHeight());
            } else {
                outRect.set(0, 0, lineDrawable.getIntrinsicWidth(), 0);
            }
        }
    }
}
