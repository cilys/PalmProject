package com.aopcloud.base.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;


/**
 * @PackageName : com.aopcloud.base.widget
 * @File : BaseDialog.java
 * @Date : 2019/12/26 10:59
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @VersionCode : V 1.0
 * @Describe ：
 */
public abstract class BaseDialog {

    protected Dialog mDialog;
    protected Context mContext;


    /**
     * in layoutId
     *
     * @return layoutId
     */
    public abstract int setLayoutId();

    /**
     * @param context
     */
    public BaseDialog(Context context) {
        mContext = context;
        View view = LayoutInflater.from(context).inflate(setLayoutId(), null);
        initView(context, view);
    }

    /**
     * init view
     *
     * @param context
     * @param view
     */
    public abstract void initView(Context context, View view);


    public void show() {
        if (mDialog.isShowing()) {
            return;
        }
        mDialog.show();
    }

    public void dismiss() {
        if (mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    public boolean isShowing() {
        return mDialog.isShowing();
    }

}
