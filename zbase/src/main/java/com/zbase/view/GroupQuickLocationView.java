package com.zbase.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.zbase.R;
import com.zbase.adapter.BaseGroupQuickLocationAdapter;

/**
 * 创建人：郑晓辉
 * 创建日期：2016/7/12
 * 描述：分组快速定位控件
 * 包含ListView,TextView,SideBar
 */
public class GroupQuickLocationView extends FrameLayout {

    private ListView listView;
    private TextView textView;
    private SideBar sideBar;

    public ListView getListView() {
        return listView;
    }

    public TextView getTextView() {
        return textView;
    }

    public SideBar getSideBar() {
        return sideBar;
    }

    public GroupQuickLocationView(Context context) {
        super(context);
    }

    public GroupQuickLocationView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GroupQuickLocationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void init(final BaseGroupQuickLocationAdapter baseGroupQuickLocationAdapter) {
        View view = inflate(getContext(), R.layout.zbase_inflate_group_quick_location_view, this);
        listView = (ListView) view.findViewById(R.id.listView);
        textView = (TextView) view.findViewById(R.id.textView);
        sideBar = (SideBar) view.findViewById(R.id.sideBar);
        sideBar.setFloatTextView(textView);
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                int position = baseGroupQuickLocationAdapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    listView.setSelection(position);
                }
            }
        });
        listView.setAdapter(baseGroupQuickLocationAdapter);
    }
}

