package com.zbase.activity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.zbase.R;

public abstract class BaseWebViewActivity extends AbstractBaseActivity {
    protected WebView webView;
    protected ProgressBar processBar;
    private long exitTime = 0;
    public static final String URL = "url";
    private FrameLayout fl_top;//头部内容，由子类实现
    protected FrameLayout fl_main;//包含webview的中间布局,子类可以直接在里面添加View
    public static final String WEB_VIEW_TITLE = "web_view_title";

    @Override
    protected int inflateBaseLayoutId() {
        return R.layout.zbase_activity_webview;
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void initBaseView(View view) {
        fl_top = (FrameLayout) findViewById(R.id.fl_top);
        LayoutInflater.from(this).inflate(inflateMainLayoutId(), fl_top);
        fl_main = (FrameLayout) findViewById(R.id.fl_main);
        //进度条
        processBar = (ProgressBar) findViewById(R.id.processBar);
        processBar.setMax(100);
        webView = (WebView) findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);//webview的http和https的混合请求时，在API>=21的版本上面默认是关闭的，在21以下就是默认开启的
        }
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                processBar.setProgress(newProgress);
                processBar.setVisibility(newProgress == 100 ? View.GONE : View.VISIBLE);
                super.onProgressChanged(view, newProgress);
            }
        });

        webView.setWebViewClient(new WebViewClient() {
            // 重写此方法返回true表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            // 重写此方法可以让webview处理https请求
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });

        String url = getIntent().getStringExtra(URL);
        if (TextUtils.isEmpty(url)) {
            url="https://www.baidu.com";
        }
        webView.loadUrl(url);
    }

//    // 处理返回键
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
//            webView.goBack();
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }


    /**
     * 捕捉返回事件按钮
     * <p/>
     * 因为此 Activity 继承 TabActivity 用 onKeyDown 无响应，所以改用 dispatchKeyEvent
     * 一般的 Activity 用 onKeyDown 就可以了
     */
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount() == 0) {
                if (webView.canGoBack()) {
                    webView.goBack();
                } else {
                    this.onBackPressed();
                }
            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    /**
     * 退出程序
     */
//    private void exitApp() {
//        // 判断2次点击事件时间
//        if ((System.currentTimeMillis() - exitTime) > 2000) {
//            Toast.makeText(BaseWebViewActivity.this, getResources().getString(R.string.exit_app_toast), Toast.LENGTH_SHORT).show();
//            exitTime = System.currentTimeMillis();
//        } else {
//            onBackPressed();
//        }
//    }
    @Override
    public void onBackPressed() {
        // 清除缓存
        if (null != webView) {
            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webView.clearCache(false);//如果设置true会偶尔奔溃
            webView.clearHistory();
            webView.destroyDrawingCache();
        }
        super.onBackPressed();
    }


}
