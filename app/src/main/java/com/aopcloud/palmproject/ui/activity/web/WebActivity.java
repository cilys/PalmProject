package com.aopcloud.palmproject.ui.activity.web;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aopcloud.base.annotation.Layout;
import com.aopcloud.base.base.BaseActivity;
import com.aopcloud.base.log.Logcat;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.utils.RegUtil;
import com.aopcloud.palmproject.utils.UrlUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @PackageName : com.aopcloud.palmproject.ui.activity.web
 * @File : WebActivity.java
 * @Date : 2020/6/13 2020/6/13
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */

@Layout(R.layout.activity_webview)
public class WebActivity extends BaseActivity {
    @BindView(R.id.ll_header_back)
    LinearLayout mLlHeaderBack;
    @BindView(R.id.tv_header_title)
    TextView mTvHeaderTitle;
    @BindView(R.id.tv_header_right)
    TextView mTvHeaderRight;
    @BindView(R.id.ll_header_right)
    LinearLayout mLlHeaderRight;
    @BindView(R.id.ll_common_layout)
    LinearLayout mLlCommonLayout;
    @BindView(R.id.web_view)
    WebView mWebView;

    private String title;
    private String url;



    @Override
    protected void initData() {
        super.initData();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            title = bundle.getString("title","");
            url = bundle.getString("url","");
        }
    }

    @Override
    protected void initView() {
        mTvHeaderTitle.setText(""+title);
        WebSettings webSettings = mWebView.getSettings();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webSettings.setJavaScriptEnabled(true);//启用js
        webSettings.setBlockNetworkImage(false);//解决图片不显示
        mWebView.setWebViewClient(mWebViewClient);
        mWebView.loadUrl(url);
    }


    @OnClick({R.id.ll_header_back, R.id.ll_header_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_header_back:
                finish();
                break;
            case R.id.ll_header_right:
                break;
        }
    }

    WebViewClient mWebViewClient = new WebViewClient(){
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Logcat.i("-----1-----" + url + "/" + RegUtil.checkURL(url));
            if (UrlUtil.isURL(url)) {
                view.loadUrl(url);
                return true;
            }
            return super.shouldOverrideUrlLoading(view, url);
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            Logcat.i("-----1-----" + url + "/" + RegUtil.checkURL(url));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                if (UrlUtil.isURL(request.getUrl().toString())) {
                    view.loadUrl(request.getUrl().toString());
                    return true;
                }
            }
            return super.shouldOverrideUrlLoading(view, request);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }
    };
}
