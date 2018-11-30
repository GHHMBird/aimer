package com.buyint.mergerbot.UIs;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.buyint.mergerbot.R;
import com.buyint.mergerbot.base.BaseActivity;
import com.buyint.mergerbot.base.LoadingFragment;
import com.buyint.mergerbot.database.DBUtilsKt;
import com.buyint.mergerbot.dto.LoginResponse;

import java.util.ArrayList;

/*
 * Created by Monkey on 2017/3/1.
 */

public class WebActivity extends BaseActivity {

    private static String cookies = "";
    private WebView wv;
    private String url;
    private ArrayList<String> urlList = new ArrayList();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        setMyTitleColor();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h5);
        initView();
        getUrl();
    }


    private void initView() {
        wv = findViewById(R.id.webview);

        TextView tvTitle = findViewById(R.id.toorbar_title);
        String title = getIntent().getStringExtra(getString(R.string.TITLE));
        tvTitle.setText(title);
        RelativeLayout ivBack = findViewById(R.id.toolbar_back);
        ivBack.setOnClickListener(v -> onBackPressed());

        wv.setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                if (keyCode == KeyEvent.KEYCODE_BACK && wv.canGoBack()) {
                    wv.goBack();
                    return true;
                }
            }
            return false;
        });
    }

    private void getUrl() {
        Intent intent = getIntent();
        url = intent.getStringExtra("params");
        if ("error".equals(url)) {
            showToast(getString(R.string.please_check_internet));
            return;
        }
        WebSettings webSettings = wv.getSettings();
        // 设置可以访问文件
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setBuiltInZoomControls(false);
//        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        //如果访问的页面中有Javascript，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        //不使用cache 全部从网络上获取
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        //无模式选择，通过setAppCacheEnabled(boolean flag)设置是否打开。默认关闭，即，H5的缓存无法使用。
        webSettings.setAppCacheEnabled(true);
        webSettings.setSupportZoom(true);
        //设置支持HTML5本地存储
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        LoginResponse model = DBUtilsKt.getLoginResponse(this);
        if (model != null) {
            if (!model.model.token.equals("")) {
                cookies = "token=" + model.model.token;
                if (!"".equals(url)) {
                    synCookies(WebActivity.this, url);
                }
            }
        }
        wv.addJavascriptInterface(new JavaScriptinterface(this), getString(R.string.android));
        //设置Web视图
        wv.setWebChromeClient(new webChromeClient());
        wv.setWebViewClient(new webViewClient());

        showLoadingFragment(R.id.activity_h5_fl);
        wv.loadUrl(url);
    }

    private class webViewClient extends WebViewClient {

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
        }

        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            LoadingFragment loadingFragment = findLoadingFragment();
            if (loadingFragment != null) {
                removeLoadingFragment();
            }
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();
        }

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.contains("mobile.nm121.com") || url.contains("jr.mmuza.com")) {
                return false;
            }
            if (url.startsWith("http:") || url.startsWith("https:") || url.startsWith("www")) {
                view.loadUrl(url);
                urlList.add(url);
                return false;
            } else {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                    return true;
                } catch (Exception e) {
                    return false;
                }
            }
        }

        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest resourceRequest) {
            String url = resourceRequest.getUrl().toString();
            if (url.contains("mobile.nm121.com") || url.contains("jr.mmuza.com")) {
                return false;
            }
            if (url.startsWith("http") || url.startsWith("https") || url.startsWith("www")) {
                view.loadUrl(resourceRequest.getUrl().toString());
                urlList.add(resourceRequest.getUrl().toString());
            }
            return true;
        }
    }

    //Web视图
    private class webChromeClient extends WebChromeClient {

        @Override
        public void onReceivedTitle(WebView view, String titleString) {
            super.onReceivedTitle(view, titleString);
        }
    }

    public static void synCookies(Context context, String url) {
        CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.removeSessionCookie();//移除
        cookieManager.setCookie(url, cookies);//cookies是在HttpClient中获得的cookie
        CookieSyncManager.getInstance().sync();
        String newCook = cookieManager.getCookie(url);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        if (urlList != null && urlList.size() > 0) {
            String s = urlList.remove(urlList.size() - 1);
            wv.loadUrl(s);
        } else {
            super.onBackPressed();
        }
    }
}
