package com.aopcloud.base.interactor;

import android.content.Context;

import java.util.Map;

/**
 *@File: ICommonRequestInteractor
 * Create Date: 2018/10/23 11:45
 * Describe   :
 * @Author     : By k
 * E-mail     : vip@devkit.vip
 * VersionName: 1
 * VersionCode: V 1.0
 * Code Update:（author - time）
 * Update Describe：
 */
public interface ICommonRequestInteractor {

    void request(int eventTag, Context context, String url, Map<String, String> map);

    void requestJson(int eventTag, Context context, String url, Map<String, String> params);

    void requestPost(int eventTag, Context context, String url, Map<String, String> params);

    void requestPostJson(int eventTag, Context context, String url, Map<String, String> params);

    void cancelRequest(Context context);

    void cancelRequest(Object o);
}
