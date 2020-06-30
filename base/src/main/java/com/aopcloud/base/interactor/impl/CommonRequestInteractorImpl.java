
package com.aopcloud.base.interactor.impl;


import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.util.Log;

import com.aopcloud.base.common.IRequestListener;
import com.aopcloud.base.interactor.ICommonRequestInteractor;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Request;

/**
 * @PackageName : com.aopcloud.palmproject.interactor.impl
 * @File : CommonRequestInteractorImpl.java
 * @Date : 2020/4/27 21:31
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class CommonRequestInteractorImpl implements ICommonRequestInteractor {

    public IRequestListener iRequestListener;

    public CommonRequestInteractorImpl(IRequestListener iRequestListener) {
        this.iRequestListener = iRequestListener;
    }

    @Override
    public void request(final int eventTag, final Context context, final String url, Map<String, String> map) {
        Map headers = new HashMap();
        OkHttpUtils.get().url(url).tag(getActivity(context)).headers(headers).params(map).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                if (!isActivityEnd(context)) {
                    iRequestListener.onError(eventTag, e.getMessage());
                }
            }

            @Override
            public void onResponse(String response, int id) {
                executeResponse(eventTag, context, response);
            }

            @Override
            public void onAfter(int id) {
                super.onAfter(id);
            }

            @Override
            public void onBefore(Request request, int id) {

            }
        });
    }

    @Override
    public void requestJson(final int eventTag, final Context context, String url, Map<String, String> params) {
        Map headers = new HashMap();
        OkHttpUtils.get().url(url).tag(getActivity(context)).headers(headers).params(params).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                if (!isActivityEnd(context)) {
                    iRequestListener.onError(eventTag, e.getMessage());
                }
            }

            @Override
            public void onResponse(String response, int id) {
                executeResponse(eventTag, context, response);
            }

            @Override
            public void onAfter(int id) {
                super.onAfter(id);
            }

            @Override
            public void onBefore(Request request, int id) {

            }
        });

    }

    @Override
    public void requestPost(final int eventTag, final Context context, String url, Map<String, String> params) {
        Map headers = new HashMap();
        OkHttpUtils.post().url(url).tag(getActivity(context)).headers(headers).params(params).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                if (!isActivityEnd(context)) {
                    iRequestListener.onError(eventTag, e.getMessage());
                }
            }

            @Override
            public void onResponse(String response, int id) {
                executeResponse(eventTag, context, response);
            }

            @Override
            public void onAfter(int id) {
                super.onAfter(id);
            }

            @Override
            public void onBefore(Request request, int id) {

            }
        });

    }

    @Override
    public void requestPostJson(final int eventTag, final Context context, String url, Map<String, String> params) {
        Map headers = new HashMap();
        OkHttpUtils.postString()
                .url(url)
                .tag(getActivity(context))
                .headers(headers)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                if (!isActivityEnd(context)) {
                    iRequestListener.onError(eventTag, e.getMessage());
                }
            }

            @Override
            public void onResponse(String response, int id) {
                executeResponse(eventTag, context, response);
            }

            @Override
            public void onAfter(int id) {
                super.onAfter(id);
            }

            @Override
            public void onBefore(Request request, int id) {

            }
        });
    }


    public void executeResponse(int eventTag, Context context, String response) {
        if (!isActivityEnd(context)) {
            if (iRequestListener != null) {
                iRequestListener.onSuccess(eventTag, response);
            }
        }
    }


    /**
     * 根据context判断activity是否已经结束
     *
     * @param context
     * @return
     */
    public boolean isActivityEnd(final Context context) {
        Activity activity;
        if (context != null) {
//            try {
//                activity = (Activity) context;
//                if (activity == null || activity.isFinishing()) {
//                    System.out.println("context为null了");
//                    return true;
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
            activity = getActivity(context);
            if (activity == null || activity.isFinishing()) {
                return true;
            }
        }

        return false;
    }

    private Activity getActivity(Context mContext) {
        Context context = mContext;
        while (!(context instanceof Activity) && context instanceof ContextWrapper) {
            context = ((ContextWrapper) context).getBaseContext();
        }
        if (context instanceof Activity) {
            return (Activity) context;
        }
        Log.e("Ok","Unable to get Activity.");
        return null;
    }


    /**
     * 取消请求
     *
     * @param context
     */
    @Override
    public void cancelRequest(Context context) {
       OkHttpUtils.getInstance().cancelTag(getActivity(context));
    }

    /**
     * 取消请求
     *
     * @param o
     */
    @Override
    public void cancelRequest(Object o) {
        OkHttpUtils.getInstance().cancelTag(o);
    }
}
