/*
******************************* Copyright (c)*********************************\
**
**                 (c) Copyright 2018, 珠海现联瑜君岚运营管理有限公司, china, qd. sd
**                                All Rights Reserved
**
**                           By(珠海现联瑜君岚运营管理有限公司)
********************************End of Head************************************\
*/
package com.aopcloud.base.common;

/**
 * @PackageName : com.aopcloud.palmproject.base
 * @File : IRequestListener.java
 * @Date : 2020/4/27 21:30
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public interface IRequestListener {

    /**
     * when data call back success
     *
     * @param data
     */
    void onSuccess(int eventTag, String data);

    /**
     * when data call back error
     *
     * @param msg
     */
    void onError(int eventTag, String msg);

}
