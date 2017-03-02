package com.zbase.template;

import android.text.TextWatcher;

/**
 * Created by Administrator on 2015/11/12.
 */
public abstract class TextWatcherAfter implements TextWatcher {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }
}




