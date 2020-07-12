package com.aopcloud.palmproject.ui.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.aopcloud.base.annotation.Layout;
import com.aopcloud.base.base.BaseActivity;
import com.aopcloud.base.log.Logcat;
import com.aopcloud.base.util.ToastUtil;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.api.ApiConstants;
import com.aopcloud.palmproject.common.ResultBean;
import com.aopcloud.palmproject.ui.activity.enterprise.CreateOrJoinActivity;
import com.aopcloud.palmproject.utils.CountDownUtil;
import com.aopcloud.palmproject.utils.JsonUtil;
import com.aopcloud.palmproject.utils.LoginUserUtil;
import com.aopcloud.palmproject.utils.RegUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * @PackageName : com.aopcloud.basic.ui.activity.mine
 * @File : RegisterActivity.java
 * @Date : 2020/4/10 2020/4/10
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
@Layout(R.layout.activity_register)
public class RegisterActivity extends BaseActivity {
    @BindView(R.id.ll_header_back)
    LinearLayout mLlHeaderBack;
    @BindView(R.id.tv_header_title)
    TextView mTvHeaderTitle;
    @BindView(R.id.tv_header_right)
    TextView mTvHeaderRight;
    @BindView(R.id.ll_header_right)
    LinearLayout mLlHeaderRight;
    @BindView(R.id.ll_common_layout)
    LinearLayout mLlCommonLayout;
    @BindView(R.id.et_mobile)
    EditText mEtMobile;
    @BindView(R.id.et_verify_code)
    EditText mEtVerifyCode;
    @BindView(R.id.tv_send_verify_code)
    TextView mTvSendVerifyCode;
    @BindView(R.id.tv_submit)
    TextView mTvSubmit;
    @BindView(R.id.tv_clause)
    TextView mTvClause;

    private String mobile;
    private String verifyCode;
    private CountDownUtil mCountDownUtil;

    @Override
    protected void initView() {
        mTvHeaderTitle.setText("注册");
        mCountDownUtil = new CountDownUtil(mTvSendVerifyCode);
    }


    @OnClick({R.id.ll_header_back, R.id.tv_send_verify_code, R.id.tv_submit, R.id.tv_clause})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_header_back:
                finish();
                break;
            case R.id.tv_send_verify_code:
                mobile = mEtMobile.getText().toString();
                if (TextUtils.isEmpty(mobile)) {
                    ToastUtil.showToast("请输入手机号码");
                    mEtMobile.setError("请输入手机号码");
                    return;
                }
                if (!RegUtil.isMobile(mobile)) {
                    ToastUtil.showToast("请输入正确的手机号码");
                    mEtMobile.setError("请输入正确的手机号码");
                    return;
                }
                showPopXupLoading("发送中");
                toRequest(ApiConstants.EventTags.sms_code_send);
                break;
            case R.id.tv_submit:
                mobile = mEtMobile.getText().toString();
                if (TextUtils.isEmpty(mobile)) {
                    ToastUtil.showToast("请输入手机号码");
                    mEtMobile.setError("请输入手机号码");
                    return;
                }
                if (!RegUtil.isMobile(mobile)) {
                    ToastUtil.showToast("请输入正确的手机号码");
                    mEtMobile.setError("请输入正确的手机号码");
                    return;
                }
                verifyCode = mEtVerifyCode.getText().toString();
                mCountDownUtil.stopTimer();
                Bundle bundle = new Bundle();
                bundle.putString("mobile", "" + mobile);
                bundle.putString("verifyCode", "" + verifyCode);
                gotoActivity(SettingPasswordActivity.class, bundle,0);
                break;
            case R.id.tv_clause:
                break;
        }
    }


    @Override
    protected void onDestroy() {
        mCountDownUtil.stopTimer();
        super.onDestroy();
    }

    @Override
    public void toRequest(int eventTag) {
        super.toRequest(eventTag);
        Map map = new HashMap();
        map.put("tel", "" + mobile);
        if (eventTag == ApiConstants.EventTags.register) {
            map.put("code", "" + verifyCode);
            iCommonRequestPresenter.requestPost(eventTag, this, ApiConstants.login_sms_code, map);
        } else if (eventTag == ApiConstants.EventTags.sms_code_send) {
            iCommonRequestPresenter.requestPost(eventTag, this, ApiConstants.sms_code_send, map);
        }
    }

    @Override
    public void getRequestData(int eventTag, String result) {
        super.getRequestData(eventTag, result);
        dismissPopupLoading();
        ResultBean bean = JSON.parseObject(result, ResultBean.class);
        if (bean != null && bean.getCode() == 0) {
            if (eventTag == ApiConstants.EventTags.sms_code_send) {
                ToastUtil.showToast(bean != null ? bean.getMsg() : "验证码发送成功");
                mCountDownUtil.runTimer(60);
            }
        } else {
            ToastUtil.showToast(bean != null ? bean.getMsg() : "加载错误，请重试");
        }

    }

    @Override
    public void onRequestFailureException(int eventTag, String msg) {
        super.onRequestFailureException(eventTag, msg);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==0&&resultCode==0){
            finish();
        }
    }
}