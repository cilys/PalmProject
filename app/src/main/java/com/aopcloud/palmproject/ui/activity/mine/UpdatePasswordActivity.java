package com.aopcloud.palmproject.ui.activity.mine;

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
import com.aopcloud.palmproject.utils.LoginUserUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @PackageName : com.aopcloud.basic.ui.activity.mine
 * @File : UpdatePasswordActivity.java
 * @Date : 2020/4/10 2020/4/10
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
@Layout(R.layout.activity_mine_update_password)
public class UpdatePasswordActivity extends BaseActivity {
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
    @BindView(R.id.et_old_password)
    EditText mEtOldPassword;
    @BindView(R.id.et_new_password)
    EditText mEtNewPassword;
    @BindView(R.id.et_confirm_new_password)
    EditText mEtConfirmNewPassword;
    @BindView(R.id.tv_submit)
    TextView mTvSubmit;

    private String oldPassword;
    private String newPassword;
    private String confirmNewPassword;

    @Override
    protected void initView() {

    }

    @OnClick({R.id.ll_header_back, R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_header_back:
                finish();
                break;
            case R.id.tv_submit:
                oldPassword = mEtOldPassword.getText().toString();
                newPassword = mEtNewPassword.getText().toString();
                confirmNewPassword = mEtConfirmNewPassword.getText().toString();
                checkParams();
                break;
        }
    }

    private void checkParams() {
        if (TextUtils.isEmpty(oldPassword)) {
            ToastUtil.showToast("请输入旧密码");
            mEtOldPassword.setError("请输入旧密码");
            return;
        }
        if (TextUtils.isEmpty(newPassword)) {
            ToastUtil.showToast("请输入新密码");
            mEtNewPassword.setError("请输入新密码");
            return;
        }
        if (TextUtils.isEmpty(confirmNewPassword)) {
            ToastUtil.showToast("请输入新密码");
            mEtConfirmNewPassword.setError("请输入新密码");
            return;
        }

        if (!confirmNewPassword.equals(newPassword)) {
            ToastUtil.showToast("两次输入的新密码不一致");
            mEtConfirmNewPassword.setError("两次输入的新密码不一致");
            return;
        }
        toRequest(ApiConstants.EventTags.reset_password);
    }



    @Override
    public void toRequest(int eventTag) {
        super.toRequest(eventTag);
        Map map = new HashMap();
        map.put("token", "" + LoginUserUtil.getToken(this));
        if (eventTag == ApiConstants.EventTags.reset_password) {
            map.put("old_password", "" + oldPassword);
            map.put("password", "" + newPassword);
            map.put("repassword", "" + newPassword);
            iCommonRequestPresenter.requestPost(eventTag, this, ApiConstants.reset_password, map);
        }
    }

    @Override
    public void getRequestData(int eventTag, String result) {
        super.getRequestData(eventTag, result);
        dismissPopupLoading();
        ResultBean bean = JSON.parseObject(result, ResultBean.class);
        if (bean != null && bean.getCode() == 0) {
            if (eventTag == ApiConstants.EventTags.reset_password) {
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
