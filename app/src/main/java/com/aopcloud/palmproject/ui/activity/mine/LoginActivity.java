package com.aopcloud.palmproject.ui.activity.mine;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.aopcloud.base.annotation.Layout;
import com.aopcloud.base.common.BaseEvent;
import com.aopcloud.base.util.ToastUtil;
import com.aopcloud.palmproject.MainActivity;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.api.ApiConstants;
import com.aopcloud.palmproject.common.ResultBean;
import com.aopcloud.palmproject.ui.activity.BaseAc;
import com.aopcloud.palmproject.ui.activity.enterprise.CreateOrJoinActivity;
import com.aopcloud.palmproject.ui.activity.enterprise.bean.EnterpriseListBean;
import com.aopcloud.palmproject.utils.CountDownUtil;
import com.aopcloud.palmproject.utils.JsonUtil;
import com.aopcloud.palmproject.utils.LoginUserUtil;
import com.aopcloud.palmproject.utils.RegUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * @PackageName : com.aopcloud.basic.ui.activity.mine
 * @File : LoginActivity.java
 * @Date : 2020/4/10 2020/4/10
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
@Layout(R.layout.activity_login)
public class LoginActivity extends BaseAc {
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
    @BindView(R.id.et_password)
    EditText mEtPassword;
    @BindView(R.id.et_verify_code)
    EditText mEtVerifyCode;
    @BindView(R.id.tv_send_verify_code)
    TextView mTvSendVerifyCode;
    @BindView(R.id.tv_login_type)
    TextView mTvLoginType;
    @BindView(R.id.tv_retrieve_password)
    TextView mTvRetrievePassword;
    @BindView(R.id.tv_submit)
    TextView mTvSubmit;


    private String mobile;
    private String verifyCode;
    private String password;
    private CountDownUtil mCountDownUtil;
    private boolean isVerifyCodeLogin = true;

    @Override
    protected void initView() {
        mTvHeaderTitle.setText("登录");
        mTvHeaderRight.setText("注册");
        mCountDownUtil = new CountDownUtil(mTvSendVerifyCode);


        mTvLoginType.setText(isVerifyCodeLogin ? "验证码登录" : "手机密码登录");
        mTvRetrievePassword.setVisibility(isVerifyCodeLogin ? View.VISIBLE : View.GONE);
        mEtVerifyCode.setVisibility(isVerifyCodeLogin ? View.GONE : View.VISIBLE);
        mTvSendVerifyCode.setVisibility(isVerifyCodeLogin ? View.GONE : View.VISIBLE);
        mEtPassword.setVisibility(isVerifyCodeLogin ? View.VISIBLE : View.GONE);
        isVerifyCodeLogin = !isVerifyCodeLogin;

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityManager activityMgr = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
        activityMgr.restartPackage(this.getPackageName());
        System.exit(0);
    }

    @OnClick({R.id.ll_header_back, R.id.ll_header_right, R.id.tv_send_verify_code, R.id.tv_login_type,
            R.id.tv_retrieve_password, R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_header_back:
                ActivityManager activityMgr = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
                activityMgr.restartPackage(this.getPackageName());
                System.exit(0);
                break;
            case R.id.ll_header_right:
                gotoActivity(RegisterActivity.class);
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
                toRequest(ApiConstants.EventTags.sms_code_send);
                break;
            case R.id.tv_login_type:
                mTvLoginType.setText(isVerifyCodeLogin ? "验证码登录" : "手机密码登录");
                mTvRetrievePassword.setVisibility(isVerifyCodeLogin ? View.VISIBLE : View.GONE);
                mEtVerifyCode.setVisibility(isVerifyCodeLogin ? View.GONE : View.VISIBLE);
                mTvSendVerifyCode.setVisibility(isVerifyCodeLogin ? View.GONE : View.VISIBLE);
                mEtPassword.setVisibility(isVerifyCodeLogin ? View.VISIBLE : View.GONE);
                isVerifyCodeLogin = !isVerifyCodeLogin;
                break;
            case R.id.tv_retrieve_password:
                gotoActivity(RetrievePasswordActivity.class);
                break;
            case R.id.tv_submit:
                mobile = mEtMobile.getText().toString();
                password = mEtPassword.getText().toString();
                verifyCode = mEtVerifyCode.getText().toString();

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
                if (isVerifyCodeLogin) {
                    if (TextUtils.isEmpty(verifyCode)) {
                        ToastUtil.showToast("请输入验证码");
                        return;
                    }
                    toRequest(ApiConstants.EventTags.login_sms_code);
                } else {
                    if (TextUtils.isEmpty(password)) {
                        ToastUtil.showToast("请输入密码");
                        return;
                    }
                    toRequest(ApiConstants.EventTags.login_pwd);
                }
                break;
        }
    }

    @Override
    public void toRequest(int eventTag) {
        super.toRequest(eventTag);
        Map map = new HashMap();
        map.put("tel", "" + mobile);
        if (eventTag == ApiConstants.EventTags.login_pwd) {
            map.put("password", "" + password);
            iCommonRequestPresenter.requestPost(eventTag, this, ApiConstants.login_pwd, map);
        } else if (eventTag == ApiConstants.EventTags.login_sms_code) {
            map.put("code", "" + verifyCode);
            iCommonRequestPresenter.requestPost(eventTag, this, ApiConstants.login_sms_code, map);
        } else if (eventTag == ApiConstants.EventTags.sms_code_send) {
            iCommonRequestPresenter.requestPost(eventTag, this, ApiConstants.sms_code_send, map);
        } else if (eventTag == ApiConstants.EventTags.company_my) {
            map.put("token", "" + LoginUserUtil.getToken(this));
            iCommonRequestPresenter.requestPost(eventTag, this, ApiConstants.company_my, map);
        }
    }

    @Override
    public void getRequestData(int eventTag, String result) {
        super.getRequestData(eventTag, result);
        dismissPopupLoading();
        ResultBean bean = JSON.parseObject(result, ResultBean.class);
        if (bean != null && bean.getCode() == 0) {
            if (eventTag == ApiConstants.EventTags.login_pwd || eventTag == ApiConstants.EventTags.login_sms_code) {
                String token = JsonUtil.parserField(bean.getData(), "token");
                String is_in_company = JsonUtil.parserField(bean.getData(), "is_in_company");
                LoginUserUtil.setToken(this, token);
                EventBus.getDefault().post(new BaseEvent(BaseEvent.EVENT_LOGIN));
                if (is_in_company.equals("1")) {
                    toRequest(ApiConstants.EventTags.company_my);
                } else {
                    gotoActivity(CreateOrJoinActivity.class);
                    finish();
                }
            } else if (eventTag == ApiConstants.EventTags.sms_code_send) {
                ToastUtil.showToast(bean != null ? bean.getMsg() : "验证码发送成功");
                mCountDownUtil.runTimer(60);
            } else if (eventTag == ApiConstants.EventTags.company_my) {
                List<EnterpriseListBean> beanList = JSON.parseArray(bean.getData(), EnterpriseListBean.class);
                LoginUserUtil.setCurrentEnterpriseNo(LoginActivity.this, beanList.get(0).getCode());
                LoginUserUtil.setCurrentEnterpriseBean(LoginActivity.this, beanList.get(0));
                EventBus.getDefault().post(new BaseEvent(BaseEvent.EVENT_LOGIN));
                gotoActivity(MainActivity.class);
                finish();
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
    protected void onDestroy() {
        super.onDestroy();
        mCountDownUtil.stopTimer();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && LoginUserUtil.isLogin(this)) {
            gotoActivity(MainActivity.class);
        }
    }
}
