package com.aopcloud.base.widget;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.aopcloud.base.R;

/**
 * @PackageName : com.aopcloud.base.widget
 * @File : LoadingDialog.java
 * @Date : 2019/12/27 14:52
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @VersionCode : V 1.0
 * @Describe ：
 */
public class LoadingDialog extends BaseDialog {

    private TextView mTvTips;
    private ImageView mImageView;

    /**
     * @param context
     */
    public LoadingDialog(Context context) {
        super(context);

    }

    @Override
    public int setLayoutId() {
        return R.layout.base_dialog_loading;
    }

    @Override
    public void initView(Context context, View view) {
        mDialog = new Dialog(context, R.style.base_dialog_loading);

        mDialog.setContentView(view);
        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(context, R.anim.base_loading_animation);
        hyperspaceJumpAnimation.setInterpolator(new LinearInterpolator());
        mImageView = mDialog.findViewById(R.id.iv_base_loading);
        mTvTips = mDialog.findViewById(R.id.tv_base_loading_tips);
        mImageView.startAnimation(hyperspaceJumpAnimation);
    }

    public void setTips(String tips) {
        if (mTvTips != null && !TextUtils.isEmpty(tips)) {
            mTvTips.setText(tips);
        }
    }

    @Override
    public void show() {
        super.show();
        if (mImageView!=null){
            Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(mDialog.getContext(), R.anim.base_loading_animation);
            hyperspaceJumpAnimation.setInterpolator(new LinearInterpolator());
            mImageView.startAnimation(hyperspaceJumpAnimation);
        }

    }
}
