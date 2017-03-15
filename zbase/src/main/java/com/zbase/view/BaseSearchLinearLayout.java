/**
 * @file XFooterView.java
 * @create Mar 31, 2012 9:33:43 PM
 * @author Maxwin
 * @description XListView's footer
 */
package com.zbase.view;


import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.zbase.R;
import com.zbase.common.ZCache;
import com.zbase.template.TextWatcherAfter;
import com.zbase.util.ListUtil;
import com.zbase.util.PopUtil;
import com.zbase.util.SoftKeyboardUtil;

import java.util.ArrayList;

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
    private ArrayList<String> historyList = new ArrayList<>();
    public static final String SEARCH_RECORDS = "search_records";

    public ArrayList<String> getHistoryList() {
        return historyList;
    }

    public EditText getEtContent() {
        return etContent;
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


    private void initView(final Context context) {
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
    }

    /**
     *
     * @param onSearchListener 点击软键盘搜索按钮的响应事件
     */
    public void init(OnSearchListener onSearchListener) {
        init(0, onSearchListener);
    }

    /**
     * 初始化
     * @param maxSize 历史记录最大条数，传0就不记录
     * @param onSearchListener 点击软键盘搜索按钮的响应事件
     */
    public void init(final int maxSize, final OnSearchListener onSearchListener) {
        etContent.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // 先隐藏键盘
                    SoftKeyboardUtil.hideSoftKeyboard(getContext());
                    // 搜索，进行自己的操作...
                    if (onSearchListener != null) {
                        if (!TextUtils.isEmpty(etContent.getText().toString().trim())) {
                            onSearchListener.onSearch();
                            if (maxSize != 0) {
                                ListUtil.pushOutFirst(maxSize, historyList, getString());
                                ZCache.putSerializable(getContext(), SEARCH_RECORDS, historyList);
                            }
                        } else {
                            PopUtil.toast(getContext(), R.string.please_enter_a_search_keyword);
                        }
                    }
                    return true;
                }
                return false;
            }
        });
        if (maxSize != 0) {
            if (ZCache.getAsObject(getContext(), SEARCH_RECORDS) != null) {
                historyList = (ArrayList<String>) ZCache.getAsObject(getContext(), SEARCH_RECORDS);
            }
        }
    }

    protected abstract int inflateMainLayoutId();

    protected abstract int findEtContentId();

    protected abstract int findIvClearId();

    /**
     * 获取没有空格的文本
     *
     * @return
     */
    public String getString() {
        return etContent.getText().toString().trim();
    }

    public void clearHistoryList(){
        historyList.clear();
        ZCache.putSerializable(getContext(), SEARCH_RECORDS, historyList);
    }

}
