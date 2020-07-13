package com.aopcloud.palmproject.ui.activity.mine;

import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.aopcloud.base.annotation.Layout;
import com.aopcloud.base.base.BaseActivity;
import com.aopcloud.base.util.ToastUtil;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.api.ApiConstants;
import com.aopcloud.palmproject.common.ResultBean;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @PackageName : com.aopcloud.basic.ui.activity.mine
 * @File : SettingPasswordActivity.java
 * @Date : 2020/4/10 2020/4/10
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
@Layout(R.layout.activity_register_set_password)
public class SettingPasswordActivity extends BaseActivity {
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
    @BindView(R.id.et_password)
    EditText mEtPassword;
    @BindView(R.id.iv_password)
    ImageView mIvPassword;
    @BindView(R.id.et_confirm_password)
    EditText mEtConfirmPassword;
    @BindView(R.id.iv_confirm_password)
    ImageView mIvConfirmPassword;
    @BindView(R.id.tv_submit)
    TextView mTvSubmit;

    private String mobile;
    private String verifyCode;
    private String password;
    private String confirmPassword;

    private boolean passwordGone = true;
    private boolean confirmPasswordGone = true;

    @Override
    protected void initData() {
        super.initData();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mobile = bundle.getString("mobile");
            verifyCode = bundle.getString("verifyCode");
        }
    }

    @Override
    protected void initView() {
        mTvHeaderTitle.setText("注册");

    }

    @OnClick({R.id.ll_header_back, R.id.iv_password, R.id.iv_confirm_password, R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_header_back:
                finish();
                break;
            case R.id.iv_password:
                mEtPassword.setInputType(passwordGone ? InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT : InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                mIvPassword.setImageResource(passwordGone ? R.mipmap.icon_password_gone : R.mipmap.icon_password_visible);
                passwordGone = !passwordGone;
                break;
            case R.id.iv_confirm_password:
                mEtConfirmPassword.setInputType(confirmPasswordGone ? InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT : InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                mIvConfirmPassword.setImageResource(confirmPasswordGone ? R.mipmap.icon_password_gone : R.mipmap.icon_password_visible);
                confirmPasswordGone = !confirmPasswordGone;
                break;
            case R.id.tv_submit:
                password = mEtPassword.getText().toString();
                confirmPassword = mEtConfirmPassword.getText().toString();
                checkParams();
                break;
        }
    }

    private void checkParams() {
        if (TextUtils.isEmpty(password)) {
            ToastUtil.showToast("请输入新密码");
            mEtPassword.setError("请输入新密码");
            return;
        }
        if (TextUtils.isEmpty(confirmPassword)) {
            ToastUtil.showToast("请输入新密码");
            mEtConfirmPassword.setError("请输入新密码");
            return;
        }
        if (!password.equals(confirmPassword)) {
            ToastUtil.showToast("两次输入的新密码不一致");
            mEtConfirmPassword.setError("两次输入的新密码不一致");
            return;
        }
        showPopXupLoading("注册中");
        toRequest(ApiConstants.EventTags.register);
    }

    @Override
    public void toRequest(int eventTag) {
        super.toRequest(eventTag);
        Map map = new HashMap();
        map.put("tel", "" + mobile);
        if (eventTag == ApiConstants.EventTags.register) {
            map.put("code", "" + verifyCode);
            map.put("password", "" + password);
            map.put("repassword", "" + password);
            iCommonRequestPresenter.requestPost(eventTag, this, ApiConstants.register, map);
        }
    }

    @Override
    public void getRequestData(int eventTag, String result) {
        super.getRequestData(eventTag, result);
        dismissPopupLoading();
        ResultBean bean = JSON.parseObject(result, ResultBean.class);
        if (bean != null && bean.getCode() == 0) {
            if (eventTag == ApiConstants.EventTags.register) {
               setResult(0);
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
}
