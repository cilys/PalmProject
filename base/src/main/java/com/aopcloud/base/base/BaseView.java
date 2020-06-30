package com.aopcloud.base.base;

/**
 * @PackageName : com.aopcloud.base.interfaces
 * @File : BaseView.java
 * @Date : 2019/12/26 10:46
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @VersionCode : V 1.0
 * @Describe ：
 */
public interface BaseView {

    /**
     * showLoading
     */
    void showLoading();

    /**
     * dismissLoading
     */
    void dismissLoading();

    /**
     * onFailure
     * @param error
     */
    void onFailure(String error);

    /**
     * onNoNetWork
     */
    void onNoNetWork();

    /**
     *
     */
    void onTokenError();


}
