package com.aopcloud.base.util;

import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aopcloud.base.R;
import com.aopcloud.base.common.AppHelper;

/**
 * @PackageName : com.aopcloud.base.util
 * @File : ToastUtil.java
 * @Date : 2019/12/26 13:55
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @VersionCode : V 1.0
 * @Describe ：
 */
public class ToastUtil {


    /**
     * @param id
     */
    public static void showToast(int id) {
        Toast.makeText(AppHelper.getInstance().getContext(), id, Toast.LENGTH_SHORT).show();
    }

    /**
     * @param msg
     */
    public static void showToast(String msg) {
        Toast.makeText(AppHelper.getInstance().getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * @param resId
     * @param strId
     */
    public static void showToast(int resId, int strId) {
        showToast(resId, ResourceUtil.getString(strId));
    }


    /**
     * @param resId
     * @param str
     */
    public static void showToast(int resId, String str) {
        Toast toast = new Toast(AppHelper.getInstance().getContext());
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        View view = LayoutInflater.from(AppHelper.getInstance().getContext()).inflate(R.layout.base_layout_icon_toast, null);
        ImageView iv = view.findViewById(R.id.iv_tips);
        if (resId != 0 || resId != -1) {
            iv.setImageResource(resId);
        }
        TextView tv = view.findViewById(R.id.tv_tips);
        if (!TextUtils.isEmpty(str)) {
            tv.setText(str);
        }
        toast.setView(view);
        toast.show();
    }

}
