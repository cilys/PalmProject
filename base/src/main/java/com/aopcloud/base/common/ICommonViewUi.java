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
 *@File: ICommonViewUi
 * Create Date: 2018/10/23 11:28
 * Describe   :
 * @Author     : By k
 * E-mail     : vip@devkit.vip
 * VersionName: 1
 * VersionCode: V 1.0
 * Code Update:（author - time）
 * Update Describe：
 */
public interface ICommonViewUi {
    /**
     *去请求数据
     */
    void toRequest(int eventTag);

    /**
     * 得到请求的结果
     * @param result
     */
    void getRequestData(int eventTag, String result);
    /**
     * 请求失败
     * @param eventTag
     * @param msg
     */
    void onRequestFailureException(int eventTag, String msg);
}
