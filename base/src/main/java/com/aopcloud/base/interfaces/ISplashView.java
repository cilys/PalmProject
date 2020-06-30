package com.aopcloud.base.interfaces;

/**
 * @PackageName : com.aopcloud.base.interfaces
 * @File : ISplashView.java
 * @Date : 2019/12/26 10:37
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @VersionCode : V 1.0
 * @Describe ：
 */
public interface ISplashView {


    void onScreenChange(int currentScreen, int lastScreen);

    void gotoMainActivity();

    void gotoActivity(Class target);

}