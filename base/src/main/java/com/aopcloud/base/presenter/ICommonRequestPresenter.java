/*
******************************* Copyright (c)*********************************\
**
**                 (c) Copyright 2018, 珠海现联瑜君岚运营管理有限公司, china, qd. sd
**                                All Rights Reserved
**
**                           By(珠海现联瑜君岚运营管理有限公司)
********************************End of Head************************************\
*/
package com.aopcloud.base.presenter;

import android.content.Context;

import java.util.Map;

/**
 * @PackageName : com.aopcloud.palmproject.presenter
 * @File : ICommonRequestPresenter.java
 * @Date : 2020/4/27 21:30
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：  
 */
public interface ICommonRequestPresenter {

    void request(int eventTag, Context context, String url, Map<String, String> params);

    void requestJson(int eventTag, Context context, String url, Map<String, String> params);

    void requestPost(int eventTag, Context context, String url, Map<String, String> params);

    void requestPostJson(int eventTag, Context context, String url, Map<String, String> params);

    void cancelRequest(Context context);

    void cancelRequest(Object o);


}
