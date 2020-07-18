package com.aopcloud.palmproject.net;

import com.cily.utils.base.StrUtils;

import okhttp3.Request;

public abstract class BaseRequestListener implements IRequestListener{
    @Override
    public void onError(String tag, String url, Exception e, int id) {
        onError(StrUtils.isEmpty(tag) ? url : tag, e);
    }

    @Override
    public void onSuccess(String tag, String url, String response, int id) {
        onSuccess(StrUtils.isEmpty(tag) ? url : tag, response);
    }

    @Override
    public void onBefore(String tag, String url, Request request, int id) {
        onBefore(StrUtils.isEmpty(tag) ? url : tag, request);
    }

    @Override
    public void onAfter(String tag, String url, int id) {
        onAfter(StrUtils.isEmpty(tag) ? url : tag);
    }

    public void onError(String tag, Exception e){}
    public void onSuccess(String tag, String response){}
    public void onBefore(String tag, Request request){}
    public void onAfter(String tag){}
}