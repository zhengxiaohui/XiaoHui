/**
 * @file XFooterView.java
 * @create Mar 31, 2012 9:33:43 PM
 * @author Maxwin
 * @description XListView's footer
 */
package com.zbase.view;


import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.zbase.template.TextWatcherAfter;

/**
 * 创建人：郑晓辉
 * 创建日期：2017/2/25
 * 描述：搜索输入框LinearLayout的基类，具体布局由子类实现
 * 效果：输入框，放大镜图标，清空按钮，软键盘出现搜索按钮并点击响应
 */
public abstract class BaseSearchLinearLayout extends FrameLayout {

    private EditText etContent;//搜索文本
    private ImageView ivClear;//清空文本按钮
    private OnSearchListener onSearchListener;

    public EditText getEtContent() {
        return etContent;
    }

    public void setOnSearchListener(OnSearchListener onSearchListener) {
        this.onSearchListener = onSearchListener;
    }

    public interface OnSearchListener {
        void onSearch();
    }

    public BaseSearchLinearLayout(Context context) {
        super(context);
        initView(context);
    }

    public BaseSearchLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public BaseSearchLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }


    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(inflateMainLayoutId(), null);
        addView(view);
        etContent = (EditText) view.findViewById(findEtContentId());
        ivClear = (ImageView) view.findViewById(findIvClearId());
        etContent.addTextChangedListener(new TextWatcherAfter() {
            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {//没有内容
                    ivClear.setVisibility(View.GONE);
                } else {
                    ivClear.setVisibility(View.VISIBLE);
                }
            }
        });
        ivClear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                etContent.setText("");
            }
        });
        etContent.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        etContent.setSingleLine(true);//不然回车【搜索】会换行
        etContent.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // 先隐藏键盘
                    ((InputMethodManager) etContent.getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(((Activity) getContext()).getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    // 搜索，进行自己的操作...
                    if (onSearchListener!=null) {
                        onSearchListener.onSearch();
                    }
                    return true;
                }
                return false;
            }
        });
    }

    protected abstract int inflateMainLayoutId();

    protected abstract int findEtContentId();

    protected abstract int findIvClearId();

}
