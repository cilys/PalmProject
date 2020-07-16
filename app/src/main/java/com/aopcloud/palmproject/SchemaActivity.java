package com.aopcloud.palmproject;

import android.webkit.WebView;

import com.aopcloud.palmproject.ui.activity.BaseAc;

/**
 * @PackageName : com.aopcloud.basic
 * @File : SchemaActivity.java
 * @Date : 2019/12/26 16:34
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @VersionCode : V 1.0
 * @Describe ：
 */
public class SchemaActivity extends BaseAc {
    private WebView mWebView;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_webview;
    }

    @Override
    protected void initView() {
        mWebView = (WebView) findViewById(R.id.web_view);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl("https://test.aopcloud.com/scheme/");
    }
}