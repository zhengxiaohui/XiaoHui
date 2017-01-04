package com.zdemo.activity;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lzy.okhttputils.OkHttpUtils;
import com.zbase.request.JsonCallback;
import com.zbase.util.PopUtil;
import com.zdemo.R;
import com.zdemo.bean.User;
import com.zdemo.bean.UserJson;
import com.zdemo.constant.HttpConstant;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by Administrator on 2015/2/27.
 */
public class ServerCRUDActivity extends BaseActivity {

    private EditText editText;
    private TextView textView;
    private Button btnAdd;
    private Button btnDelete;
    private Button btnEdit;
    private Button btnQuery;
    private String id;
    private List<User> userList = new ArrayList<User>();

    @Override
    protected int inflateMainLayoutId() {
        return R.layout.server_crud;
    }

    @Override
    public void initView(View view) {
        editText = (EditText) findViewById(R.id.editText);
        textView = (TextView) findViewById(R.id.textView);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnEdit = (Button) findViewById(R.id.btnEdit);
        btnQuery = (Button) findViewById(R.id.btnQuery);
    }

    @Override
    public void setListener() {
        btnAdd.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnEdit.setOnClickListener(this);
        btnQuery.setOnClickListener(this);
    }

    @Override
    public void initValue() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAdd:
                requestAdd();
                break;
            case R.id.btnDelete:
                requestDelete();
                break;
            case R.id.btnEdit:
                requestEdit();
                break;
            case R.id.btnQuery:
                requestQuery();
                break;
        }
    }

    private void requestAdd() {
        OkHttpUtils.get(HttpConstant.ADD_USER_URL)
                .tag(this)
                .params("name", editText.getText().toString().trim())
                .execute(new JsonCallback<UserJson>(context, UserJson.class) {
                    @Override
                    public void onResponse(boolean isFromCache, UserJson userJson, Request request, @Nullable Response response) {
                        if (userJson.isSuccess()) {
                            PopUtil.toast(ServerCRUDActivity.this, R.string.add_success);
                        } else {
                            PopUtil.toast(ServerCRUDActivity.this, userJson.getMessage());
                        }
                    }
                });
    }

    private void requestDelete() {
        OkHttpUtils.get(HttpConstant.DELETE_USER_URL)
                .tag(this)
                .params("id", id)
                .execute(new JsonCallback<UserJson>(context, UserJson.class) {
                    @Override
                    public void onResponse(boolean isFromCache, UserJson userJson, Request request, @Nullable Response response) {
                        if (userJson.isSuccess()) {
                            PopUtil.toast(ServerCRUDActivity.this, R.string.delete_success);
                        } else {
                            PopUtil.toast(ServerCRUDActivity.this, userJson.getMessage());
                        }
                    }
                });
    }

    private void requestEdit() {
        OkHttpUtils.get(HttpConstant.EDIT_USER_URL)
                .tag(this)
                .params("id", id)
                .params("name", editText.getText().toString().trim())
                .execute(new JsonCallback<UserJson>(context, UserJson.class) {
                    @Override
                    public void onResponse(boolean isFromCache, UserJson userJson, Request request, @Nullable Response response) {
                        if (userJson.isSuccess()) {
                            PopUtil.toast(ServerCRUDActivity.this, R.string.edit_success);
                        } else {
                            PopUtil.toast(ServerCRUDActivity.this, userJson.getMessage());
                        }
                    }
                });
    }

    private void requestQuery() {
        OkHttpUtils.get(HttpConstant.QUERY_USER_URL)
                .tag(this)
                .execute(new JsonCallback<UserJson>(context, UserJson.class) {
                    @Override
                    public void onResponse(boolean isFromCache, UserJson userJson, Request request, @Nullable Response response) {
                        if (userJson.isSuccess()) {
                            PopUtil.toast(ServerCRUDActivity.this, R.string.query_success);
                        } else {
                            PopUtil.toast(ServerCRUDActivity.this, userJson.getMessage());
                        }
                    }
                });
    }

}
