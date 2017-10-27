package com.zbase.view.adapterview;

/**
 * Created by Administrator on 2015/9/18.
 */

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

/**一定要添加属性orientation，不然只显示一行,setAdapter()必须在adapter.setList()之后调用才有效
 * 模仿ListView的LinearLayout，使之能被嵌套在ScrollView中，高度自适应撑开
 * 其实只是普通的LinearLayout循环添加子View进去，只是传入了标准BaseAdapter使用，垂直或水平都可以。
 * 这种方式就是硬编码的升级版，可以使用BaseAdapter
 */
public class LinearListView extends LinearLayout {

    public LinearListView(Context context) {
        super(context);
    }

    public LinearListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LinearListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 必须在adapter.setList()之后调用才有效
     * @param adapter
     */
    public void setAdapter(BaseAdapter adapter) {
        setAdapter(adapter, 0, 0, 0);
    }

    /**
     * 设置adapter和分割线的长，宽，颜色（长，宽和颜色必须同时都有值才有效）
     * 例子：linearListView.setAdapter(followCommunityAdapter, ViewGroup.LayoutParams.MATCH_PARENT,R.dimen.line_thickness,R.color.line_color);
     *
     * @param adapter
     * @param lineWidth       可以传具体的px值也可以传match,wrap
     * @param lineHeightResId R.dimen.demo形式，如 R.dimen.line_thickness
     * @param lingColorResId  R.color.demo形式， 如 R.color.line_color
     */
    public void setAdapter(BaseAdapter adapter, int lineWidth, int lineHeightResId, int lingColorResId) {
        if (adapter != null && adapter.getCount() > 0) {
            removeAllViews();
            for (int i = 0; i < adapter.getCount(); i++) {
                addView(adapter.getView(i, null, this));
                if (i != adapter.getCount() - 1) {//不是最后一条
                    if (lineWidth != 0 && lineHeightResId != 0 && lingColorResId != 0) {
                        View lineView = new View(getContext());
                        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        lineView.setLayoutParams(layoutParams);
                        addView(lineView);
                    }
                }
            }
        }
    }

    /**
     *
     * @param adapter
     * @param drawable 分隔线的drawble
     * @param leftPadding 左边padding
     * @param rightPadding 右边padding
     */
    public void setAdapter(BaseAdapter adapter, Drawable drawable, int leftPadding, int rightPadding) {
        if (adapter != null && adapter.getCount() > 0) {
            removeAllViews();
            for (int i = 0; i < adapter.getCount(); i++) {
                addView(adapter.getView(i, null, this));
                if (i != adapter.getCount() - 1) {//不是最后一条
                    if (drawable != null) {
                        View lineView = new View(getContext());
                        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        layoutParams.setMargins(leftPadding, 0, rightPadding, 0);
                        lineView.setLayoutParams(layoutParams);
                        lineView.setBackground(drawable);
                        addView(lineView);
                    }
                }
            }
        }
    }
}
