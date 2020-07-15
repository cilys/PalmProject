package com.aopcloud.palmproject.ui.activity.enterprise;

import android.Manifest;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.aopcloud.base.annotation.Layout;
import com.aopcloud.base.util.ToastUtil;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.api.ApiConstants;
import com.aopcloud.palmproject.common.ResultBean;
import com.aopcloud.palmproject.ui.activity.BaseAc;
import com.aopcloud.palmproject.ui.activity.QrCodeScanActivity;
import com.aopcloud.palmproject.utils.LoginUserUtil;
import com.hjq.permissions.OnPermission;
import com.hjq.permissions.XXPermissions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @PackageName : com.aopcloud.palmproject.ui.activity.enterprise
 * @File : JoinEnterpriseActivity.java
 * @Date : 2020/4/19 2020/4/19
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
@Layout(R.layout.activity_enterprise_join)
public class JoinEnterpriseActivity extends BaseAc {
    @BindView(R.id.tv_cancel)
    TextView mTvCancel;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.iv_scan)
    ImageView mIvScan;
    @BindView(R.id.et_number)
    EditText mEtNumber;
    @BindView(R.id.et_log)
    EditText mEtLog;
    @BindView(R.id.tv_submit)
    TextView mTvSubmit;

    private String enterpriseNo;
    private String reason;

    @Override
    protected void initView() {
        mTvTitle.setText("加入团队");
        String activityTo = getIntent().getStringExtra("activityTo");
        if ("QrCodeScanActivity".equals(activityTo)){
            toQrCodeActivity();
        } else {

        }
    }


    @OnClick({R.id.tv_cancel, R.id.iv_scan, R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                finish();
                break;
            case R.id.iv_scan:
                toQrCodeActivity();
                break;
            case R.id.tv_submit:
                enterpriseNo = mEtNumber.getText().toString();
                reason = mEtLog.getText().toString();
                if (TextUtils.isEmpty(enterpriseNo)) {
                    ToastUtil.showToast("请输入团队号");
                    return;
                }
                if (TextUtils.isEmpty(reason)) {
                    ToastUtil.showToast("请输入申请理由");
                    return;
                }
                showPopXupLoading("提交申请中...");
                toRequest(ApiConstants.EventTags.company_apply);
                break;
        }
    }

    @Override
    public void toRequest(int eventTag) {
        super.toRequest(eventTag);
        Map map = new HashMap();
        map.put("token", "" + LoginUserUtil.getToken(this));

        if (eventTag == ApiConstants.EventTags.company_apply) {
            map.put("code", "" + enterpriseNo);
            map.put("reason", "" + reason);
            iCommonRequestPresenter.requestPost(eventTag, this, ApiConstants.company_apply, map);
        }
    }

    @Override
    public void getRequestData(int eventTag, String result) {
        super.getRequestData(eventTag, result);
        dismissPopupLoading();
        ResultBean bean = JSON.parseObject(result, ResultBean.class);
        if (bean != null && bean.getCode() == 0) {
            if (eventTag == ApiConstants.EventTags.company_apply) {
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data==null){
            return;
        }
        if (requestCode==2){
            enterpriseNo = data.getStringExtra("code");
            mEtNumber.setText(""+enterpriseNo);
        }
    }

    private void toQrCodeActivity(){
        List<String> list = new ArrayList<>();
        list.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        list.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        list.add(Manifest.permission.CAMERA);
        XXPermissions.with(this).permission(list).request(new OnPermission() {
            @Override
            public void hasPermission(List<String> granted, boolean isAll) {
                if (isAll) {
                    gotoActivity(QrCodeScanActivity.class, 2);
                } else {
                    ToastUtil.showToast("请先开启权限");
                }
            }

            @Override
            public void noPermission(List<String> denied, boolean quick) {
                ToastUtil.showToast("请开启相机以及内存卡读写权限");
            }
        });
    }
}