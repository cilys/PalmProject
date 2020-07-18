package com.aopcloud.palmproject.net;

import okhttp3.Request;

public interface IRequestListener {

    void onError(String tag, String url, Exception e, int id);
    void onSuccess(String tag, String url, String response, int id);
    void onBefore(String tag, String url, Request request, int id);
    void onAfter(String tag, String url, int id);
}
