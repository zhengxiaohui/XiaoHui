package com.zdemo.activity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.thinkland.sdk.android.DataCallBack;
import com.thinkland.sdk.android.JuheData;
import com.thinkland.sdk.android.Parameters;
import com.zbase.strategy.PopStrategy;
import com.zbase.util.GsonUtil;
import com.zbase.util.PopUtil;
import com.zdemo.R;
import com.zdemo.bean.IpArea;

/**
 * 创建人：郑晓辉
 * 创建日期：2016/5/26
 * 描述：
 */
public class JuheActivity extends BaseActivity {

    private TextView textView;
    private Button button;

    @Override
    protected int inflateMainLayoutId() {
        return R.layout.activity_juhe;
    }

    @Override
    protected void initView(View view) {
        textView = (TextView) findViewById(R.id.textView);
        button = (Button) findViewById(R.id.button);
    }

    @Override
    protected void setListener() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestData();
            }
        });
    }

    @Override
    protected void initValue() {

    }

    private void requestData() {
        PopStrategy.showProgressDialog(context);
        /**
         * 请不要添加key参数.
         */
        Parameters params = new Parameters();
        params.add("ip", "www.juhe.cn");
        /**
         * 请求的方法 参数: 第一个参数 当前请求的context;
         * 				  第二个参数 接口id;
         * 				  第三个参数 接口请求的url;
         * 				  第四个参数 接口请求的方式;
         * 				  第五个参数 接口请求的参数,键值对com.thinkland.sdk.android.Parameters类型;
         * 				  第六个参数请求的回调方法,com.thinkland.sdk.android.DataCallBack;
         *
         */
        JuheData.executeWithAPI(context, 1, "http://apis.juhe.cn/ip/ip2addr", JuheData.GET, params, new DataCallBack() {
            /**
             * 请求成功时调用的方法 statusCode为http状态码,responseString    *为请求返回数据.
             */
            @Override
            public void onSuccess(int statusCode, String responseString) {
                IpArea ipArea= GsonUtil.fromJson(responseString, IpArea.class);
                textView.setText(ipArea.getResult().getArea());
            }

            /**
             * 请求完成时调用的方法,无论成功或者失败都会调用.
             */
            @Override
            public void onFinish() {
                PopUtil.dismissProgressDialog();
            }

            /**
             * 请求失败时调用的方法,statusCode为http状态码,throwable为捕获到的异常
             * statusCode:30002 没有检测到当前网络. 30003 没有进行初始化. 0
             * 未明异常,具体查看Throwable信息. 其他异常请参照http状态码.
             */
            @Override
            public void onFailure(int statusCode, String responseString, Throwable throwable) {
                switch (statusCode) {
                    case 30002:
                        PopUtil.toast(context,"没有检测到当前网络!");
                        break;
                    case 30003:
                        PopUtil.toast(context, "没有进行初始化!");
                        break;
                    default:
                        PopUtil.toast(context, "未明异常,具体查看Throwable信息:"+throwable.getMessage());
                        break;
                }
            }
        });
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        JuheData.cancelRequests(context);//关闭当前页面正在进行中的请求.
    }
}
