package com.aopcloud.palmproject.net;

import com.cily.utils.base.StrUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Request;

public class HttpUtils {

    public static void post(String tag, String url, Map<String, String> headers,
                            Map<String, String> params, IRequestListener listener) {
        if (headers == null) {
            headers = new HashMap<>();
        }
        if (params == null) {
            params = new HashMap<>();
        }
        OkHttpUtils.post().url(url)
                .tag(StrUtils.isEmpty(tag) ? url : tag)
                .headers(headers).params(params).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                if (listener != null){
                    listener.onError(tag, url, e, id);
                }
            }

            @Override
            public void onResponse(String response, int id) {
                if (listener != null){
                    listener.onSuccess(tag, url, response, id);
                }
            }

            @Override
            public void onAfter(int id) {
                super.onAfter(id);
                if (listener != null){
                    listener.onAfter(tag, url, id);
                }
            }

            @Override
            public void onBefore(Request request, int id) {
                if (listener != null){
                    listener.onBefore(tag, url, request, id);
                }
            }
        });
    }

    public static void cancel(String tag){
        OkHttpUtils.getInstance().cancelTag(tag);
    }
}
