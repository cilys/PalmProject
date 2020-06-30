package com.aopcloud.palmproject.view;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.aopcloud.base.util.ViewUtil;
import com.aopcloud.base.widget.BaseDialog;
import com.aopcloud.palmproject.R;

import butterknife.OnClick;

/**
 * @PackageName : com.aopcloud.basic.view
 * @File : TipsDialog.java
 * @Date : 2020/4/10 2020/4/10
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class TipsDialog extends BaseDialog {
    TextView mTvMsg;
    TextView mTvCancel;
    TextView mTvSure;
    TextView mTvTitle;
    View mViewLine;
    private boolean showCancel =true;
    private boolean showTitle;
    private String title;
    private String msg;
    private onActionClickListener mOnActionClickListener;
    private int titleColor=-1;
    private int msgColor=-1;

    public static TipsDialog wrap(Context context) {
        return new TipsDialog(context);
    }

    public TipsDialog setMsg(String msg) {
        this.msg = msg;
        return this;
    }
    public TipsDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public TipsDialog setShowCancel(boolean showCancel) {
        this.showCancel = showCancel;  return this;
    }

    public TipsDialog setOnActionClickListener(onActionClickListener onActionClickListener) {
        mOnActionClickListener = onActionClickListener;
        return this;
    }

    public TipsDialog setTitleColor(int titleColor) {
        this.titleColor = titleColor;return this;
    }

    public TipsDialog setMsgColor(int msgColor) {
        this.msgColor = msgColor;return this;
    }

    /**
     * @param context
     */
    public TipsDialog(Context context) {
        super(context);
    }

    @Override
    public int setLayoutId() {
        return R.layout.dialog_tips;
    }

    @Override
    public void initView(Context context, View view) {
        mDialog = new Dialog(context, com.aopcloud.base.R.style.base_dialog_loading);
        mDialog.setContentView(view);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(mDialog.getWindow().getAttributes());
        lp.width = ViewUtil.getScreenWidth(mContext) / 2 + ViewUtil.getScreenWidth(mContext) / 4;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mDialog.getWindow().setAttributes(lp);
        mTvMsg = mDialog.findViewById(R.id.tv_msg);
        mTvTitle = mDialog.findViewById(R.id.tv_title);
        mTvSure = mDialog.findViewById(R.id.tv_sure);
        mTvCancel = mDialog.findViewById(R.id.tv_cancel);
        mViewLine=mDialog.findViewById(R.id.view_line);
        mTvSure.setOnClickListener(this::onViewClicked);
        mTvCancel.setOnClickListener(this::onViewClicked);
        if (!TextUtils.isEmpty(msg)) {
            mTvMsg.setText(msg);
        }
    }

    @Override
    public void show() {

        if (!showCancel){
            mTvCancel.setVisibility(View.GONE);
            mViewLine.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(msg)) {
            mTvMsg.setText(msg);
        }
        if (!TextUtils.isEmpty(title)) {
            mTvTitle.setText(title);
            mTvTitle.setVisibility(View.VISIBLE);
        }else {
            mTvTitle.setVisibility(View.GONE);
        }
        if (titleColor!=-1){
            mTvTitle.setTextColor(titleColor);
        }if (msgColor!=-1){
            mTvMsg.setTextColor(msgColor);
        }
        super.show();
    }

    @OnClick({R.id.tv_msg, R.id.tv_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                if (mOnActionClickListener != null) {
                    mOnActionClickListener.onCancel(mDialog);
                    if (mDialog.isShowing()) {
                        mDialog.dismiss();
                    }
                }
                break;
            case R.id.tv_sure:
                if (mOnActionClickListener != null) {
                    mOnActionClickListener.onSure(mDialog);
                }
                break;
        }
    }

    public abstract static class onActionClickListener {
        public  void onCancel(Dialog dialog) {

        }

        public abstract void onSure(Dialog dialog);


    }

}
