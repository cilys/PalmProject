package com.aopcloud.palmproject.ui.activity.staff;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.aopcloud.base.annotation.Layout;
import com.aopcloud.base.util.ToastUtil;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.api.ApiConstants;
import com.aopcloud.palmproject.common.ResultBean;
import com.aopcloud.palmproject.ui.activity.BaseAc;
import com.aopcloud.palmproject.ui.activity.department.DepartmentSelectActivity;
import com.aopcloud.palmproject.ui.activity.tag.DepartmentTagSelectActivity;
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
 * @PackageName : com.aopcloud.palmproject.ui.activity.staff
 * @File : StaffInviteByAddActivity.java
 * @Date : 2020/4/23 9:18
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @VersionCode : V 1.0
 * @Describe ：
 */
@Layout(R.layout.activity_staff_invite_add)
public class StaffInviteByAddActivity extends BaseAc {
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
    @BindView(R.id.tv_mobile)
    TextView mTvMobile;
    @BindView(R.id.ll_mobile)
    LinearLayout mLlMobile;
    @BindView(R.id.et_name)
    EditText mEtName;
    @BindView(R.id.rb_boy)
    RadioButton mRbBoy;
    @BindView(R.id.rb_girl)
    RadioButton mRbGirl;
    @BindView(R.id.tv_department)
    TextView mTvDepartment;
    @BindView(R.id.ll_department)
    LinearLayout mLlDepartment;
    @BindView(R.id.tv_department_tag)
    TextView mTvDepartmentTag;
    @BindView(R.id.ll_post)
    LinearLayout mLlPost;
    @BindView(R.id.et_account)
    EditText mEtAccount;
    @BindView(R.id.et_pwd)
    EditText mEtPwd;
    @BindView(R.id.tv_submit)
    TextView mTvSubmit;
    private String department_id = "";
    private String department_name = "";
    private String department_tag = "";

    @Override
    protected void initView() {
        mTvHeaderTitle.setText("邀请新成员");
    }

    @OnClick({R.id.ll_header_back, R.id.ll_header_right, R.id.ll_mobile, R.id.rb_boy, R.id.rb_girl, R.id.ll_department, R.id.ll_post, R.id.tv_submit})
    public void onViewClicked(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.ll_header_back:
                finish();
                break;
            case R.id.ll_header_right:
                break;
            case R.id.ll_mobile:
                List<String> list = new ArrayList<>();
                list.add(Manifest.permission.READ_CONTACTS);
                XXPermissions.with(this).permission(list).request(new OnPermission() {
                    @Override
                    public void hasPermission(List<String> granted, boolean isAll) {
                        if (isAll) {
                            gotoActivity(SelectPhoneListActivity.class, 0);
                        } else {
                            ToastUtil.showToast("请先开启权限");
                        }
                    }

                    @Override
                    public void noPermission(List<String> denied, boolean quick) {

                    }
                });
                break;
            case R.id.ll_department:
                gotoActivity(DepartmentSelectActivity.class, 1);
                break;
            case R.id.ll_post:
                bundle.putString("department_id", "" + department_id);
                bundle.putString("department_name", "" + department_name);
                gotoActivity(DepartmentTagSelectActivity.class, bundle, 2);
                break;
            case R.id.tv_submit:
                name = mEtName.getText().toString();
                acconnt = mEtAccount.getText().toString();
                pwd = mEtPwd.getText().toString();
                sex = mRbBoy.isChecked() ? "1" : "2";
                if (TextUtils.isEmpty(mobile)) {
                    ToastUtil.showToast("请选择手机号");
                    return;
                }
                toRequest(ApiConstants.EventTags.company_invite);
                break;
        }
    }

    private String mobile;
    private String name;
    private String sex;
    private String acconnt;
    private String pwd;

    @Override
    public void toRequest(int eventTag) {
        super.toRequest(eventTag);
        Map map = new HashMap();
        map.put("token", "" + LoginUserUtil.getToken(this));
        map.put("code", "" + LoginUserUtil.getCurrentEnterpriseNo(this));
        if (eventTag == ApiConstants.EventTags.company_invite) {
            map.put("tel", "" + mobile);
//            map.put("user_id", "");
            iCommonRequestPresenter.requestPost(eventTag, this, ApiConstants.company_invite, map);
        }
    }

    @Override
    public void getRequestData(int eventTag, String result) {
        super.getRequestData(eventTag, result);
        dismissPopupLoading();
        ResultBean bean = JSON.parseObject(result, ResultBean.class);
        if (bean != null && bean.getCode() == 0) {
            if (eventTag == ApiConstants.EventTags.company_invite) {
                ToastUtil.showToast(bean != null ? bean.getMsg() : "提交成功");
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
        if (data == null) {
            return;
        }
        if (requestCode == 0) {
            Bundle bundle = data.getExtras();
            mobile = bundle.getString("phone", "");
            name = bundle.getString("name", "");
            mTvMobile.setText(mobile);
            mEtName.setText(name);

        } else if (requestCode == 1) {
            Bundle bundle = data.getExtras();
            department_id = bundle.getString("department_id", "");
            department_name = bundle.getString("department_name");
            mTvDepartment.setText("" + department_name);

        } else if (requestCode == 2) {
            Bundle bundle = data.getExtras();
            department_tag = bundle.getString("tagId", "");
            String tagName = bundle.getString("tagName", "");
            mTvDepartmentTag.setText("" + tagName);
        }
    }
}