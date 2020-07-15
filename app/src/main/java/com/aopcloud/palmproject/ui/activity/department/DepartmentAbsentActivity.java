package com.aopcloud.palmproject.ui.activity.department;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.aopcloud.base.annotation.Layout;
import com.aopcloud.base.util.ResourceUtil;
import com.aopcloud.base.util.ToastUtil;
import com.aopcloud.base.util.ViewUtil;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.api.ApiConstants;
import com.aopcloud.palmproject.common.ResultBean;
import com.aopcloud.palmproject.ui.activity.BaseAc;
import com.aopcloud.palmproject.ui.activity.department.bean.DepartmentMemberBean;
import com.aopcloud.palmproject.ui.activity.staff.StaffDetailActivity;
import com.aopcloud.palmproject.ui.activity.staff.StaffInviteActivity;
import com.aopcloud.palmproject.ui.adapter.department.DepartmentAbsentMemberAdapter;
import com.aopcloud.palmproject.utils.JumpActionUtil;
import com.aopcloud.palmproject.utils.LoginUserUtil;
import com.aopcloud.palmproject.view.decoration.HorizontalDividerItemDecoration;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @PackageName : com.aopcloud.palmproject.ui.activity.department
 * @File : DepartmentAddMemberActivity.java
 * @Date : 2020/4/22 2020/4/22
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
@Layout(R.layout.activity_department_absent)
public class DepartmentAbsentActivity extends BaseAc {

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
    @BindView(R.id.et_search)
    EditText mEtSearch;
    @BindView(R.id.ll_add)
    LinearLayout mLlAdd;
    @BindView(R.id.rv_list)
    RecyclerView mRvList;
    private DepartmentAbsentMemberAdapter mAdapter;
    private List<DepartmentMemberBean> mBeanList = new ArrayList<>();


    @Override
    protected void initData() {
        super.initData();
        toRequest(ApiConstants.EventTags.department_nduser);
    }

    @Override
    protected void initView() {
        mTvHeaderTitle.setText("未分配部门" );
        mTvHeaderRight.setText("");


        HorizontalDividerItemDecoration decoration = new HorizontalDividerItemDecoration.Builder(this)
                .margin(0)
                .size(ViewUtil.dp2px(this, 1))
                .color(ResourceUtil.getColor(R.color.transparent))
                .build();
        mAdapter = new DepartmentAbsentMemberAdapter(R.layout.item_staff_home, mBeanList);
        mRvList.addItemDecoration(decoration);
        mRvList.setLayoutManager(new LinearLayoutManager(this));
        mRvList.setAdapter(mAdapter);


        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void setListener() {
        super.setListener();
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("user_id", mBeanList.get(position).getUser_id() + "");
                bundle.putString("company_user_id",mBeanList.get(position).getCompany_user_id()+"");
                gotoActivity(StaffDetailActivity.class, bundle, 0);
            }
        });
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.iv_call) {
                    JumpActionUtil.jump2CallPhone(DepartmentAbsentActivity.this,mBeanList.get(position).getTel()+"");
                } else if (view.getId() == R.id.iv_send_sms) {
                    JumpActionUtil.jump2SendSms(DepartmentAbsentActivity.this,mBeanList.get(position).getTel()+"");

                }
            }
        });

    }

    @OnClick({R.id.ll_header_back, R.id.ll_header_right, R.id.ll_add})
    public void onViewClicked(View view) {
        Bundle bundle;
        switch (view.getId()) {
            case R.id.ll_header_back:
                finish();
                break;
            case R.id.ll_header_right:
                break;
            case R.id.ll_add:
                gotoActivity(StaffInviteActivity.class,  0);
                break;
        }
    }

    @Override
    public void toRequest(int eventTag) {
        super.toRequest(eventTag);
        Map map = new HashMap();
        map.put("token", "" + LoginUserUtil.getToken(this));
        map.put("code", "" + LoginUserUtil.getCurrentEnterpriseNo(this));
        if (eventTag == ApiConstants.EventTags.department_nduser) {
            iCommonRequestPresenter.requestPost(eventTag, this, ApiConstants.department_nduser, map);
        }
    }

    @Override
    public void getRequestData(int eventTag, String result) {
        super.getRequestData(eventTag, result);
        ResultBean bean = JSON.parseObject(result, ResultBean.class);
        if (bean != null && bean.getCode() == 0) {
            if (eventTag == ApiConstants.EventTags.department_nduser) {
                List<DepartmentMemberBean> list = JSON.parseArray(bean.getData(), DepartmentMemberBean.class);
                mBeanList.clear();
                mBeanList.addAll(list);
                mAdapter.notifyDataSetChanged();
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
        toRequest(ApiConstants.EventTags.department_nduser);
    }

}