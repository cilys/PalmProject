package com.aopcloud.base.util;

import android.graphics.Color;
import android.os.Build;

import com.aopcloud.base.common.AppHelper;

/**
 * @PackageName : com.aopcloud.base.util
 * @File : ResourceUtil.java
 * @Date : 2019/12/26 13:57
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @VersionCode : V 1.0
 * @Describe ：
 */
public class ResourceUtil {


    /**
     * @param resId
     * @return
     */
    public static String getString(int resId) {
        return AppHelper.getInstance().getContext().getString(resId);
    }
    /**
     * @param resId
     * @return
     */
    public static String[] getStringArray(int resId) {
        return AppHelper.getInstance().getContext().getResources().getStringArray(resId);
    }


    /**
     * @param resId
     * @param s
     * @return
     */
    public static String getString(int resId, String s) {
        String val = String.format(AppHelper.getInstance().getContext().getString(resId), s);
        return val;
    }

    /**
     * @param resId
     * @param i
     * @return
     */
    public static String getString(int resId, int i) {
        String val = String.format(AppHelper.getInstance().getContext().getString(resId), i);
        return val;
    }

    /**
     * @param resId
     * @param d
     * @return
     */
    public static String getString(int resId, double d) {
        String val = String.format(AppHelper.getInstance().getContext().getString(resId), d);
        return val;
    }

    /**
     * @param resId
     * @return
     */
    public static int getColor(int resId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return AppHelper.getInstance().getContext().getResources().getColor(resId, AppHelper.getInstance().getContext().getTheme());
        } else {
            return AppHelper.getInstance().getContext().getResources().getColor(resId);
        }
    }

    /**
     * @param color
     * @return
     */
    public static int getColor(String color) {
        return Color.parseColor(color);
    }
}
