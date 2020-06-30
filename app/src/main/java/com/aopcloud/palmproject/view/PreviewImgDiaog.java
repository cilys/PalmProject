package com.aopcloud.palmproject.view;

import android.content.Context;
import android.view.View;

import com.aopcloud.base.widget.BaseDialog;

/**
 * @PackageName : com.aopcloud.palmproject.view
 * @File : PreviewImgDiaog.java
 * @Date : 2020/5/31 2020/5/31
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class PreviewImgDiaog extends BaseDialog
{

    /**
     * @param context
     */
    public PreviewImgDiaog(Context context) {
        super(context);
    }

    @Override
    public int setLayoutId() {
        return 0;
    }

    @Override
    public void initView(Context context, View view) {

    }

    @Override
    public void show() {
        super.show();

    }
}
