package com.aopcloud.base.common;

import android.app.Application;
import android.content.Context;

/**
 * @PackageName : com.aopcloud.base.common
 * @File : AppHelper.java
 * @Date : 2019/12/26 11:35
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @VersionCode : V 1.0
 * @Describe ：
 */
public class AppHelper {
    private static AppHelper instance = null;

    private static Context mContext;

    public static AppHelper getInstance() {
        if (null == instance) {
            instance = new AppHelper();
        }
        return instance;
    }

    public static void init(Application application) {
        mContext = application;
    }

    public Context getContext() {
        return mContext;
    }
}

