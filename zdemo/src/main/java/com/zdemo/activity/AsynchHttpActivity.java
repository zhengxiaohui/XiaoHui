package com.zdemo.activity;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lzy.okhttputils.OkHttpUtils;
import com.zbase.request.JsonCallback;
import com.zbase.util.PopUtil;
import com.zdemo.R;
import com.zdemo.bean.Person;
import com.zdemo.bean.PersonJson;
import com.zdemo.constant.HttpConstant;

import java.util.List;

import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by Administrator on 2015/2/7.
 */
public class AsynchHttpActivity extends BaseActivity {

    private TextView textView3;
    private TextView textView5;
    private Button button;

    @Override
    protected int inflateMainLayoutId() {
        return R.layout.asynch_http_layout;
    }

    @Override
    public void initView(View view) {
        textView3 = (TextView) findViewById(R.id.textView3);
        textView5 = (TextView) findViewById(R.id.textView5);
        button = (Button) findViewById(R.id.button);
    }

    @Override
    public void setListener() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                personList = (List<Person>) ZCache.getAsObject(AsynchHttpActivity.this, Cons.personList);
//                if (personList != null) {
//                    initData(personList);
//                } else {
                requestVolley();
//                }

            }
        });
    }

    @Override
    public void initValue() {

    }

    private void requestVolley() {
        OkHttpUtils.get(HttpConstant.TEST_URL)
                .tag(this)
                .params("pageIndex", "0")
                .params("pageSize", "10")
                .execute(new JsonCallback<PersonJson>(context, PersonJson.class) {
                    @Override
                    public void onResponse(boolean isFromCache, PersonJson personJson, Request request, @Nullable Response response) {
                        if (response != null && personJson.isSuccess()) {
                            List<Person> personList = personJson.getPersonList();
                            if (personList != null && personList.size() > 0) {
                                textView5.setText(personList.get(3).getAge() + "");
                            }
                        } else {
                            PopUtil.toast(AsynchHttpActivity.this, R.string.query_fail);
                        }
                    }
                });
    }

//    private void request() {//测试发现AsynchHttp框架请求失败的时候没有回调到失败方法。（比如ip地址不对的时候）
//        RequestParams requestParams = new RequestParams();
//        requestParams.put("pageIndex", 0);
//        requestParams.put("pageSize", 10);
//        HttpClient.post(this, HttpConstans.TEST_URL, requestParams, true, new GsonHttpResponseHandler<PersonJson>(PersonJson.class) {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, PersonJson response) {
//                if (response != null && response.isSuccess()) {
//                    List<Person> personList = response.getPersonList();
//                    if (personList != null && personList.size() > 0) {
//                        textView5.setText(personList.get(3).getAge() + "");
//                        ZCache.putSerializableSecond(AsynchHttpActivity.this, Constans.personList, (Serializable) response.getPersonList(), 3);
//                    }
//                } else {
//                    PopUtil.toast(AsynchHttpActivity.this, R.string.query_fail);
//                }
//
//            }
//        });
//    }

    @Override
    public void onClick(View v) {

    }
}
