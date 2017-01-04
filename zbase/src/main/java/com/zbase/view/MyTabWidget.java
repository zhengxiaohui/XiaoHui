/**
 *
 */
package com.zbase.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.widget.LinearLayout;

/**
 * 这里必须预先定义好里面的条目，条目内容不限
 * 底部控件
 */
public class MyTabWidget extends LinearLayout implements OnFocusChangeListener {


    /**
     * Let {@link } know that the user clicked on a tab indicator.
     */
    public static interface OnMyTabSelectionChanged {
        /**
         * Informs the TabHost which tab was selected. It also indicates
         * if the tab was clicked/pressed or just focused into.
         *
         * @param tabIndex index of the tab that was selected
         * @param clicked  whether the selection changed due to a touch/click
         *                 or due to focus entering the tab through navigation. Pass true
         *                 if it was due to a press/click and false otherwise.
         */
        void onTabSelectionChanged(int tabIndex, boolean clicked);
    }


    // This value will be set to 0 as soon as the first tab is added to TabHost.
    private int mSelectedTab = -1;
    private OnMyTabSelectionChanged mSelectionChangedListener = null;

    public MyTabWidget(Context context) {
        super(context);
        initTabWidget();
    }


    /**
     * @param context
     * @param attrs
     */
    public MyTabWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        initTabWidget();
    }


    /**
     * Returns the tab indicator view at the given index.
     *
     * @param index the zero-based index of the tab indicator view to return
     * @return the tab indicator view at the given index
     */
    public View getChildTabViewAt(int index) {
        return getChildAt(index);
    }

    /**
     * Returns the number of tab indicator views.
     *
     * @return the number of tab indicator views.
     */
    public int getTabCount() {
        return getChildCount();
    }

    /**
     * Sets the current tab.
     * This method is used to bring a tab to the front of the Widget,
     * and is used to post to the rest of the UI that a different tab
     * has been brought to the foreground.
     * <p/>
     * Note, this is separate from the traditional "focus" that is
     * employed from the view logic.
     * <p/>
     * For instance, if we have a list in a tabbed view, a user may be
     * navigating up and down the list, moving the UI focus (orange
     * highlighting) through the list items.  The cursor movement does
     * not effect the "selected" tab though, because what is being
     * scrolled through is all on the same tab.  The selected tab only
     * changes when we navigate between tabs (moving from the list view
     * to the next tabbed view, in this example).
     * <p/>
     * To move both the focus AND the selected tab at once, please use
     * {@link #setCurrentTab}. Normally, the view logic takes care of
     * adjusting the focus, so unless you're circumventing the UI,
     * you'll probably just focus your interest here.
     *
     * @param index The tab that you want to indicate as the selected
     *              tab (tab brought to the front of the widget)
     * @see #
     */
    public void setCurrentTab(int index) {
        if (index < 0 || index >= getTabCount() || index == mSelectedTab) {
            return;
        }

        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child.getOnFocusChangeListener() == null) {
                child.setFocusable(true);
                child.setClickable(true);
                child.setOnClickListener(new TabClickListener(i));
                child.setOnFocusChangeListener(this);
            }
        }


        if (mSelectedTab != -1) {
            getChildTabViewAt(mSelectedTab).setSelected(false);
        }
        mSelectedTab = index;
        getChildTabViewAt(mSelectedTab).setSelected(true);

        if (isShown()) {
            sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_SELECTED);
        }

        if (mSelectionChangedListener != null)
            mSelectionChangedListener.onTabSelectionChanged(mSelectedTab, true);
    }

    public int getCurrentTab() {
        return mSelectedTab;
    }


    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (v == this && hasFocus && getTabCount() > 0) {
            getChildTabViewAt(mSelectedTab).requestFocus();
            return;
        }

        if (hasFocus) {
            int i = 0;
            int numTabs = getTabCount();
            while (i < numTabs) {
                if (getChildTabViewAt(i) == v) {
                    setCurrentTab(i);
                    if (isShown()) {
                        // a tab is focused so send an event to announce the tab widget state
                        sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_FOCUSED);
                    }
                    break;
                }
                i++;
            }
        }
    }


    // registered with each tab indicator so we can notify tab host
    private class TabClickListener implements OnClickListener {

        private final int mTabIndex;

        private TabClickListener(int tabIndex) {
            mTabIndex = tabIndex;
        }

        public void onClick(View v) {
            setCurrentTab(mTabIndex);
        }
    }


    private void initTabWidget() {
        setChildrenDrawingOrderEnabled(true);

        // Deal with focus, as we don't want the focus to go by default
        // to a tab other than the current tab
        setFocusable(true);
        setOnFocusChangeListener(this);


    }

    @Override
    public void addView(View child) {
        if (child.getLayoutParams() == null) {
            final LayoutParams lp = new LayoutParams(
                    0,
                    ViewGroup.LayoutParams.MATCH_PARENT, 1.0f);
            lp.setMargins(0, 0, 0, 0);
            child.setLayoutParams(lp);
        }

        // Ensure you can navigate to the tab with the keyboard, and you can touch it
        child.setFocusable(true);
        child.setClickable(true);

        super.addView(child);

        // than potentially interfere with the view's listener
        child.setOnClickListener(new TabClickListener(getTabCount() - 1));
        child.setOnFocusChangeListener(this);
    }

    @Override
    public void removeAllViews() {
        super.removeAllViews();
        mSelectedTab = -1;
    }


    public void setSelectionChangedListener(OnMyTabSelectionChanged selectionChangedListener) {
        mSelectionChangedListener = selectionChangedListener;
    }


}
