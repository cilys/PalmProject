package com.aopcloud.palmproject.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.aopcloud.palmproject.bean.UserBean;
import com.aopcloud.palmproject.ui.activity.enterprise.bean.EnterpriseListBean;

/**
 * @PackageName : com.aopcloud.palmproject.utils
 * @File : LoginUserUtil.java
 * @Date : 2020/4/27 2020/4/27
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class LoginUserUtil {
    protected static String TAG = LoginUserUtil.class.getSimpleName();

    /**
     * @param context
     * @param bean    设置、更新用户信息
     */
    public static void setLoginUserBean(Context context, UserBean bean) {
        if (bean == null) {
            Log.d(TAG, "set login bean is empty");
        }
        SharedPreferencesUtils.setSP(context, "loginUser", JSON.toJSONString(bean));
    }

    /**
     * @param context
     * @return 获取用户信息
     */
    public static UserBean getLoginUserBean(Context context) {
        String json = (String) SharedPreferencesUtils.getSP(context, "loginUser", "");
        if (!TextUtils.isEmpty(json)) {
            return JSON.parseObject(json, UserBean.class);
        }
        return null;
    }

    /**
     * @param context
     * @return 获取用户 Token
     */
    public static String getToken(Context context) {
        return (String) SharedPreferencesUtils.getSP(context, "userToken", "");
    }

    /**
     * @param context
     * @return 更新用户 Token
     */
    public static void setToken(Context context, String token) {
        SharedPreferencesUtils.setSP(context, "userToken", "" + token);

    }


    /**
     * @param context
     * @return 获取当前企业 code
     */
    public static String getCurrentEnterpriseNo(Context context) {
        return (String) SharedPreferencesUtils.getSP(context, "Enterprise", "");
    }

    /**
     * @param context
     * @return 设置当前企业 code
     */
    public static void setCurrentEnterpriseNo(Context context, String enterpriseCode) {
        SharedPreferencesUtils.setSP(context, "Enterprise", "" + enterpriseCode);

    }

    /**
     * @param context
     * @return 获取当前企业 code
     */
    public static EnterpriseListBean getCurrentEnterpriseBean(Context context) {
        String json = (String) SharedPreferencesUtils.getSP(context, "CurrentEnterpriseBean", "");
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        return JSON.parseObject(json, EnterpriseListBean.class);
    }

    /**
     * @param context
     * @return 设置当前企业 code
     */
    public static void setCurrentEnterpriseBean(Context context, EnterpriseListBean enterpriseBean) {
        SharedPreferencesUtils.setSP(context, "CurrentEnterpriseBean", "" + JSON.toJSONString(enterpriseBean));
    }

    /**
     * @Method
     * @Description 是否登录
     * @Date: 2020/4/27 23:09
     * @Author: k
     * @Param
     * @Return
     */

    public static boolean isLogin(Context context) {
        String token = (String) SharedPreferencesUtils.getSP(context, "userToken", "");
        return !TextUtils.isEmpty(token);

    }

    /**
     * 退出登录 清除缓存
     *
     * @param context
     */
    public static void exitLogin(Context context) {
        SharedPreferencesUtils.removeSP(context, "loginUser");
        SharedPreferencesUtils.removeSP(context, "userToken");
        SharedPreferencesUtils.removeSP(context, "CurrentEnterpriseBean");
        SharedPreferencesUtils.removeSP(context, "Enterprise");
    }

}
