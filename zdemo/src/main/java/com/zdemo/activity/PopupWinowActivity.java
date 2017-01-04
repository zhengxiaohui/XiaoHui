package com.zdemo.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

import com.zbase.decoration.LinearLayoutDecoration;
import com.zbase.listener.ItemClickListener;
import com.zbase.listener.OnCancelClickListener;
import com.zbase.listener.OnConfirmClickListener;
import com.zbase.listener.OnInputFinishListener;
import com.zbase.util.PopUtil;
import com.zbase.view.popupwindow.ListViewPopupWindow;
import com.zbase.view.popupwindow.PasswordPopupWindow;
import com.zbase.view.popupwindow.RecyclerViewPopupWindow;
import com.zdemo.R;
import com.zdemo.adapter.HRecyclerViewPopupWindowAdapter;
import com.zdemo.adapter.ListViewTitleCancelPopupWindowAdapter;
import com.zdemo.adapter.RecyclerViewPopupWindowAdapter;

import java.util.Arrays;

public class PopupWinowActivity extends BaseActivity {

    private Button button0;
    private Button button1;
    private PasswordPopupWindow passwordPopupWindow;

    @Override
    protected int inflateMainLayoutId() {
        return R.layout.activity_popwindow;
    }

    @Override
    protected void initView(View view) {
        button0 = (Button) findViewById(R.id.button0);
        button1 = (Button) findViewById(R.id.button1);
    }

    @Override
    protected void setListener() {
        button0.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showHRecyclerViewPopupWindow();
            }
        });

        button1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showVRecyclerViewPopupWindow();
            }
        });

        findViewById(R.id.button2).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showPasswordPopWindow();
            }
        });

        findViewById(R.id.button).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomListViewPopupWindow();
            }
        });
    }

    @Override
    protected void initValue() {

    }

    private void showHRecyclerViewPopupWindow() {
        final RecyclerViewPopupWindow recyclerViewPopupWindow = (RecyclerViewPopupWindow) new RecyclerViewPopupWindow.Builder(context, inflate(R.layout.pw_recyclerview_h)
                ).setMainViewId(R.id.recyclerView).build();

        RecyclerView recyclerView=recyclerViewPopupWindow.getMainView();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        LinearLayoutDecoration linearLayoutDecoration=new LinearLayoutDecoration(context);
        linearLayoutDecoration.setDrawableId(R.mipmap.add_picture);
        linearLayoutDecoration.setTopPadding(10);
        linearLayoutDecoration.setBottomPadding(30);
        linearLayoutDecoration.setOrientation(0);
        recyclerView.addItemDecoration(linearLayoutDecoration);

        HRecyclerViewPopupWindowAdapter hRecyclerViewPopupWindowAdapter = new HRecyclerViewPopupWindowAdapter(context,true);
        hRecyclerViewPopupWindowAdapter.inflateHeaderView(R.layout.inflate_header);
        hRecyclerViewPopupWindowAdapter.inflateFooterView(R.layout.inflate_footer);
        hRecyclerViewPopupWindowAdapter.setList(Arrays.asList(getResources().getStringArray(R.array.pop_menu)));
        hRecyclerViewPopupWindowAdapter.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                recyclerViewPopupWindow.dismiss();
                PopUtil.toast(context, "点击第" + position);
            }
        });
        recyclerViewPopupWindow.setAdapter(hRecyclerViewPopupWindowAdapter);
        recyclerViewPopupWindow.showAtCenter(this);
    }

    private void showVRecyclerViewPopupWindow() {
        final RecyclerViewPopupWindow recyclerViewPopupWindow = (RecyclerViewPopupWindow) new RecyclerViewPopupWindow.Builder(context, inflate(R.layout.pw_recyclerview_v)
                ).setMainViewId(R.id.recyclerView).build();

        RecyclerView recyclerView=recyclerViewPopupWindow.getMainView();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        LinearLayoutDecoration linearLayoutDecoration=new LinearLayoutDecoration(context);
        linearLayoutDecoration.setLeftPadding(10);
        linearLayoutDecoration.setRightPadding(20);
        linearLayoutDecoration.setOrientation(1);
        recyclerView.addItemDecoration(linearLayoutDecoration);

        RecyclerViewPopupWindowAdapter recyclerViewPopupWindowAdapter = new RecyclerViewPopupWindowAdapter(context);
        recyclerViewPopupWindowAdapter.inflateHeaderView(R.layout.inflate_header);
        recyclerViewPopupWindowAdapter.inflateFooterView(R.layout.inflate_footer);
        recyclerViewPopupWindowAdapter.setList(Arrays.asList(getResources().getStringArray(R.array.pop_menu)));
        recyclerViewPopupWindowAdapter.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                recyclerViewPopupWindow.dismiss();
                PopUtil.toast(context, "点击第" + position);
            }
        });
        recyclerViewPopupWindow.setAdapter(recyclerViewPopupWindowAdapter);
        recyclerViewPopupWindow.showAtCenter(this);
    }

    private void showPasswordPopWindow() {
        View view = inflate(R.layout.pw_input_business_pwd);
        passwordPopupWindow = (PasswordPopupWindow) new PasswordPopupWindow.Builder(context, view).setMainViewId(R.id.passwordEditText).setConfirmTextViewId(R.id.tv_forget_pwd).build();
        passwordPopupWindow.setOnConfirmClickListener(new OnConfirmClickListener() {
            @Override
            public void onConfirmClick(View v) {
                PopUtil.toast(context, "点击了忘记密码");
            }

        });
        passwordPopupWindow.setOnInputFinishListener(new OnInputFinishListener() {
            @Override
            public void onInputFinish(String text) {
                PopUtil.toast(context, "密码输入完成");
            }
        });
        view.findViewById(R.id.iv_close).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                passwordPopupWindow.dismiss();
            }
        });
        passwordPopupWindow.showAtCenter(this);
    }

    private void showBottomListViewPopupWindow() {
        final ListViewPopupWindow listViewPopupWindow = (ListViewPopupWindow) new ListViewPopupWindow.Builder(context, inflate(R.layout.pw_listview_title_cancel_match),
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT).setMainViewId(R.id.listView).setTitleTextViewId(R.id.tv_title).setCancelTextViewId(R.id.tv_cancel).build();
        ListViewTitleCancelPopupWindowAdapter listViewTitleCancelPopupWindowAdapter = new ListViewTitleCancelPopupWindowAdapter(context);
        listViewTitleCancelPopupWindowAdapter.setList(Arrays.asList(getResources().getStringArray(R.array.pop_menu)));
        listViewTitleCancelPopupWindowAdapter.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                listViewPopupWindow.dismiss();
                PopUtil.toast(context, "点击第" + position);
            }
        });
        listViewPopupWindow.setAdapter(listViewTitleCancelPopupWindowAdapter);
        listViewPopupWindow.setOnCancelClickListener(new OnCancelClickListener() {
            @Override
            public void onCancelClick(View v) {
                PopUtil.toast(context, "点击了取消按钮");
            }
        });
        listViewPopupWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void onClick(View v) {

    }
}
