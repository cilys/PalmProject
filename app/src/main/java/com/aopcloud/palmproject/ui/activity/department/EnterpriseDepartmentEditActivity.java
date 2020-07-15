package com.aopcloud.palmproject.ui.activity.department;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.aopcloud.base.annotation.Layout;
import com.aopcloud.base.util.ToastUtil;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.api.ApiConstants;
import com.aopcloud.palmproject.common.ResultBean;
import com.aopcloud.palmproject.ui.activity.BaseAc;
import com.aopcloud.palmproject.ui.activity.department.bean.DepartmentNodeBean;
import com.aopcloud.palmproject.utils.LoginUserUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @PackageName : com.aopcloud.palmproject.ui.activity.enterprise
 * @File : EnterpriseDepartmentEditActivity.java
 * @Date : 2020/4/21 11:07
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @VersionCode : V 1.0
 * @Describe ：
 */
@Layout(R.layout.activity_enterprise_department_edit)
public class EnterpriseDepartmentEditActivity extends BaseAc {
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
    @BindView(R.id.et_department_name)
    EditText mEtDepartmentName;
    @BindView(R.id.tv_department_manger)
    TextView mTvDepartmentManger;
    @BindView(R.id.tv_parent)
    TextView mTvParent;
    @BindView(R.id.tv_gone_department)
    TextView mTvGoneDepartment;
    @BindView(R.id.sw_gone_department)
    Switch mSwGoneDepartment;
    @BindView(R.id.rl_enterprise_department)
    RelativeLayout mRlEnterpriseDepartment;
    @BindView(R.id.spinner_project)
    Spinner mSpinnerProject;
    @BindView(R.id.switch_project)
    Switch mSwitchProject;
    @BindView(R.id.switch_notice)
    Switch mSwitchNotice;
    @BindView(R.id.spinner_budget)
    Spinner mSpinnerBudget;
    @BindView(R.id.switch_budget)
    Switch mSwitchBudget;
    @BindView(R.id.spinner_purchase)
    Spinner mSpinnerPurchase;
    @BindView(R.id.switch_purchase)
    Switch mSwitchPurchase;
    @BindView(R.id.switch_contract)
    Switch mSwitchContract;
    @BindView(R.id.switch_expense)
    Switch mSwitchExpense;
    @BindView(R.id.switch_bookkeeping)
    Switch mSwitchBookkeeping;
    @BindView(R.id.switch_invoice)
    Switch mSwitchInvoice;
    @BindView(R.id.switch_cooperate)
    Switch mSwitchCooperate;
    @BindView(R.id.switch_goods)
    Switch mSwitchGoods;
    @BindView(R.id.switch_record)
    Switch mSwitchRecord;
    @BindView(R.id.tv_submit)
    TextView mTvSubmit;


    private String parentId = "0";

    private String name = "";
    private String leader_id = "";
    private String status = "";
    private String department_id = "";
    private String department_name="";

    private String team_id = "";

    private DepartmentNodeBean mDepartmentNodeBean;

    @Override
    protected void initData() {
        super.initData();
        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            String json = bundle.getString("json");
            mDepartmentNodeBean= JSON.parseObject(json,DepartmentNodeBean.class);
            department_id =mDepartmentNodeBean.getDepartment_id()+"";
            department_name= mDepartmentNodeBean.getName();

        }
    }

    @Override
    protected void initView() {
        mTvHeaderTitle.setText("部门设置");
        mTvHeaderRight.setText("删除");
        mEtDepartmentName.setText(""+department_name);

        if (mDepartmentNodeBean!=null){
            mEtDepartmentName.setText(mDepartmentNodeBean.getName());
            mTvDepartmentManger.setText(mDepartmentNodeBean.getLeader_name());
        }
    }


    @OnClick({R.id.ll_header_back, R.id.ll_header_right, R.id.tv_department_manger, R.id.tv_parent, R.id.tv_submit})
    public void onViewClicked(View view) {
        Bundle bundle ;
        switch (view.getId()) {
            case R.id.ll_header_back:
                finish();
                break;
            case R.id.ll_header_right:
                toRequest(ApiConstants.EventTags.department_del);
                break;
            case R.id.tv_department_manger:
                bundle = new Bundle();
                bundle.putString("title","部门主管选择");
                gotoActivity(DepartmentLeaderSelectActivity.class, bundle,2);
                break;
            case R.id.tv_parent:
                gotoActivity(DepartmentSelectActivity.class, 0);
                break;
            case R.id.tv_submit:
                checkParams();
                break;
        }
    }

    private void checkParams() {
        name = mEtDepartmentName.getText().toString();
        leader_id = LoginUserUtil.getLoginUserBean(this).getId()+"";

        if (TextUtils.isEmpty(name)) {
            ToastUtil.showToast("请输入部门名字");
            return;
        }
        if (TextUtils.isEmpty(leader_id)) {
            ToastUtil.showToast("请选择负责人");
            return;
        }

        toRequest(ApiConstants.EventTags.department_update);
    }

    @Override
    public void toRequest(int eventTag) {
        super.toRequest(eventTag);
        Map map = new HashMap();
        map.put("token", "" + LoginUserUtil.getToken(this));
        map.put("code", "" + LoginUserUtil.getCurrentEnterpriseNo(this));
        map.put("department_id", "" + department_id);
        if (eventTag == ApiConstants.EventTags.department_update) {
            map.put("pid", "" + parentId);
            map.put("name", "" + name);
            map.put("leader_id", "" + LoginUserUtil.getLoginUserBean(this).getId());
            map.put("status", mSwGoneDepartment.isChecked() ? "2" : "1");
            iCommonRequestPresenter.requestPost(eventTag, this, ApiConstants.department_update, map);
        } else if (eventTag == ApiConstants.EventTags.department_del) {
            iCommonRequestPresenter.requestPost(eventTag, this, ApiConstants.department_del, map);
        }
    }

    @Override
    public void getRequestData(int eventTag, String result) {
        super.getRequestData(eventTag, result);
        ResultBean bean = JSON.parseObject(result, ResultBean.class);
        if (bean != null && bean.getCode() == 0) {
            if (eventTag == ApiConstants.EventTags.department_update) {
                ToastUtil.showToast(bean != null ? bean.getMsg() : "编辑成功");
                setResult(0);
                finish();
            } else if (eventTag == ApiConstants.EventTags.department_del) {
                ToastUtil.showToast(bean != null ? bean.getMsg() : "删除成功");
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
        if (requestCode == 0 && resultCode == 0) {
            Bundle bundle = data.getExtras();
            parentId = bundle.getString("department_id", "");
            String parent_department_name = bundle.getString("department_name");
            mTvParent.setText("" + parent_department_name);
        }else if (requestCode==1){
            gotoActivity(DepartmentLeaderSelectActivity.class,data.getExtras(),2);
        }else if (requestCode==2){
            Bundle bundle = data.getExtras();
            leader_id = bundle.getString("user_id", "");
            String leader_name = bundle.getString("user_name");
            mTvDepartmentManger.setText("" + leader_name);
        }
    }

}