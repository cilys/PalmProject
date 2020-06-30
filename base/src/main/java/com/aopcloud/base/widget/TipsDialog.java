package com.aopcloud.base.widget;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import com.aopcloud.base.R;

/**
 * @PackageName : com.aopcloud.base.widget
 * @File : TipsDialog.java
 * @Date : 2020/5/6 9:20
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @VersionCode : V 1.0
 * @Describe ：
 */
public class TipsDialog extends BaseDialog implements View.OnClickListener {

    View mViewLine;
    TextView mTvTipsMsg;
    TextView mTvTipsCancel;
    TextView mTvTipsSure;
    private Builder mBuilder;

    /**
     * @param context
     */
    public TipsDialog(Context context, Builder builder) {
        super(context);
        this.mBuilder = builder;
        if (mBuilder != null) {
            if (!TextUtils.isEmpty(mBuilder.msg)) {
                mTvTipsMsg.setText(mBuilder.msg);
            }
            mTvTipsCancel.setVisibility(mBuilder.showCancel?View.VISIBLE:View.GONE);
            mViewLine.setVisibility(mBuilder.showCancel?View.VISIBLE:View.GONE);
            if (mDialog!=null){
                mDialog.setCancelable(mBuilder.cancelable);
            }
        }
    }


    @Override
    public int setLayoutId() {
        return R.layout.base_dialog_tips;
    }

    @Override
    public void initView(Context context, View view) {
        mDialog = new Dialog(context, R.style.base_dialog_loading);

        mDialog.setContentView(view);
        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(context, R.anim.base_loading_animation);
        hyperspaceJumpAnimation.setInterpolator(new LinearInterpolator());
        mTvTipsMsg = (TextView) view.findViewById(R.id.tv_tips_msg);
        mViewLine = (View) view.findViewById(R.id.view_line);
        mTvTipsCancel = (TextView) view.findViewById(R.id.tv_tips_cancel);
        mTvTipsSure = (TextView) view.findViewById(R.id.tv_tips_sure);
        mTvTipsCancel.setOnClickListener(this);
        mTvTipsSure.setOnClickListener(this);
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_tips_cancel) {
            if (mBuilder.mOnTipsClickListener != null) {
                mBuilder.mOnTipsClickListener.onCancel(mDialog);
            }
        } else if (id == R.id.tv_tips_sure) {
            if (mBuilder.mOnTipsClickListener != null) {
                mBuilder.mOnTipsClickListener.onSure(mDialog);
            }
        }
    }

    public static class Builder {

        private String msg;
        private boolean showCancel;
        private boolean cancelable;
        private Context mContext;
        private OnTipsClickListener mOnTipsClickListener;


        public Builder(Context context) {
            this.mContext = context;
        }

        public Builder setMsg(String msg) {
            this.msg = msg;
            return this;
        }

        public Builder setOnTipsClickListener(OnTipsClickListener onTipsClickListener) {
            this.mOnTipsClickListener = onTipsClickListener;
            return this;
        }

        public Builder setShowCancel(boolean showCancel) {
            this.showCancel = showCancel;
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            this.cancelable = cancelable;return this;
        }

        public TipsDialog build() {
            return new TipsDialog(mContext, this);
        }
    }

    public abstract static class OnTipsClickListener {
        public abstract void onSure(Dialog dialog);

        public void onCancel(Dialog dialog) {

        }
    }
}
