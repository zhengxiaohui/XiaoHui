package com.zbase.view.adapterview;


import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.zbase.R;
import com.zbase.util.ScreenUtil;

/**
 * 创建人：郑晓辉
 * 创建日期：2017/02/25
 * 描述：智能包裹LinearLayout，水平循环放置TextView，当前行放不下则放到下一行中。经常用于做搜索记录和热门标签等。
 * 注意：做搜索记录等和EditText有交互的时候，最好是在activity布局中include记录列表并通过设置Visibility控制隐藏显示，而不要使用PopupWindow控件做。
 * 如果使用PopupWindow控件做，则SmartWrapLinearLayout的宽度需要设置成固定dp而不能用match（获取不到），并且会出现弹出PopupWindow后EditText失去焦点的情况（不能输入文字）
 */
public class SmartWrapLinearLayout extends LinearLayout {

    private int totalWidth;//LinearLayout总长度
    private int remainingWidth;//LinearLayout剩余长度
    private LinearLayout currentLinearLayout;//当前子LinearLayout
    private BaseAdapter adapter;
    private int viewSpace;//View之间的间距
    private int lineSpace;//行间距

    public SmartWrapLinearLayout(Context context) {
        super(context);
        init(null, 0);
    }

    public SmartWrapLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public SmartWrapLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyle) {
        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.SmartWrapLinearLayout, defStyle, 0);
        viewSpace = a.getDimensionPixelOffset(R.styleable.SmartWrapLinearLayout_viewSpace, ScreenUtil.dip2px(getContext(), 10));//默认10dp
        lineSpace = a.getDimensionPixelOffset(R.styleable.SmartWrapLinearLayout_lineSpace, ScreenUtil.dip2px(getContext(), 10));//默认10dp
        a.recycle();
        setOrientation(VERTICAL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (totalWidth == 0) {
            totalWidth = getMeasuredWidth();
            if (totalWidth != 0) {
                remainingWidth = totalWidth;
            }
        }
    }

    /**
     * 必须在adapter.setList()之后调用才有效
     * @param adapter
     */
    public void setAdapter(BaseAdapter adapter) {
        this.adapter = adapter;
        initView();
    }

    private void initView() {
        if (adapter != null && adapter.getCount() > 0) {
            removeAllViews();
            currentLinearLayout = createLinearLayout(false);
            addView(currentLinearLayout);//默认添加第一个子LinearLayout
            for (int i = 0; i < adapter.getCount(); i++) {
                View view = adapter.getView(i, null, this);
                view.measure(0, 0);
                int viewMeasuredWidth = view.getMeasuredWidth();
                if (remainingWidth < viewMeasuredWidth) {//连没有间距的view都放不下
                    currentLinearLayout = createLinearLayout(true);
                    addView(currentLinearLayout);
                    remainingWidth = totalWidth;
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    layoutParams.rightMargin = viewSpace;
                    view.setLayoutParams(layoutParams);
                    remainingWidth -= viewSpace;
                } else if (remainingWidth >= viewMeasuredWidth + viewSpace) {//连有间距的view都放得下
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    layoutParams.rightMargin = viewSpace;
                    view.setLayoutParams(layoutParams);
                    remainingWidth -= viewSpace;
                }
                currentLinearLayout.addView(view);
                remainingWidth -= viewMeasuredWidth;
            }
        }
    }

    private LinearLayout createLinearLayout(boolean hasTopMargin) {
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(HORIZONTAL);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (hasTopMargin) {
            layoutParams.topMargin = lineSpace;
        }
        linearLayout.setLayoutParams(layoutParams);
        return linearLayout;
    }

}
