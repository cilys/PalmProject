package com.aopcloud.base.common;

import android.provider.Settings;

/**
 * @PackageName : com.aopcloud.base.common
 * @File : BaseConfig.java
 * @Date : 2019/12/26 11:36
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @VersionCode : V 1.0
 * @Describe ：
 */
public class BaseConfig {

    public String getAndroidId() {
        String androidId = Settings.Secure.getString(AppHelper.getInstance().getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        return androidId;
    }
}
