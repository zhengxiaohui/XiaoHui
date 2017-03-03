package com.zbase.manager;

import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.zbase.template.TextWatcherAfter;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建人：郑晓辉
 * 创建日期：2017/3/2
 * 描述：监听多个EditText的文本变化，当都不为空的时候按钮可点击
 */
public class EditTextListNoticeViewManager {

    private List<EditText> editTextList = new ArrayList<>();//多个监听的EditText
    private View view;//按钮

    public EditTextListNoticeViewManager(List<EditText> editTextList, View view) {
        this.editTextList = editTextList;
        this.view = view;
        addTextChangedListener();
    }

    public void addTextChangedListener() {
        if (editTextList != null && editTextList.size() > 0 && view != null) {
            for (int i = 0; i < editTextList.size(); i++) {
                editTextList.get(i).addTextChangedListener(new TextWatcherAfter() {
                    @Override
                    public void afterTextChanged(Editable s) {
                        if (isEditTextListHasEmpty(editTextList)) {
                            view.setEnabled(false);
                        } else {
                            view.setEnabled(true);
                        }
                    }
                });
            }
        }
    }

    /**
     * 是否有一个是空的，只要有一个是空的就返回空，按钮就不能点击
     *
     * @param editTextList
     * @return
     */
    private boolean isEditTextListHasEmpty(List<EditText> editTextList) {
        for (int i = 0; i < editTextList.size(); i++) {
            if (TextUtils.isEmpty(editTextList.get(i).getText().toString())) {
                return true;
            }
        }
        return false;
    }

}
