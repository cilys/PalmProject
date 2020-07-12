package com.aopcloud.palmproject.ui.activity.department;

import android.app.Dialog;
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
import com.alibaba.fastjson.JSONArray;
import com.aopcloud.base.annotation.Layout;
import com.aopcloud.base.base.BaseActivity;
import com.aopcloud.base.log.Logcat;
import com.aopcloud.base.util.ResourceUtil;
import com.aopcloud.base.util.ToastUtil;
import com.aopcloud.base.util.ViewUtil;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.api.ApiConstants;
import com.aopcloud.palmproject.common.ResultBean;
import com.aopcloud.palmproject.ui.activity.department.bean.DepartmentListBean;
import com.aopcloud.palmproject.ui.activity.department.bean.DepartmentMemberBean;
import com.aopcloud.palmproject.ui.activity.department.bean.DepartmentNodeBean;
import com.aopcloud.palmproject.ui.activity.department.bean.DepartmentStaffBean;
import com.aopcloud.palmproject.ui.activity.staff.StaffDetailActivity;
import com.aopcloud.palmproject.ui.activity.tag.DepartmentTagManagerActivity;
import com.aopcloud.palmproject.ui.activity.staff.StaffInviteActivity;
import com.aopcloud.palmproject.ui.adapter.department.DepartmentChildAdapter;
import com.aopcloud.palmproject.ui.adapter.department.DepartmentMemberAdapter;
import com.aopcloud.palmproject.ui.adapter.staff.StaffListAdapter;
import com.aopcloud.palmproject.utils.JumpActionUtil;
import com.aopcloud.palmproject.utils.LoginUserUtil;
import com.aopcloud.palmproject.view.TipsDialog;
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
@Layout(R.layout.activity_department_home)
public class DepartmentHomeActivity extends BaseActivity {
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
    @BindView(R.id.rv_department)
    RecyclerView mRvDepartment;
    @BindView(R.id.tv_department_staff)
    TextView mTvDepartmentStaff;


    private String department_id = "";
    private String department_name = "";

    private DepartmentMemberAdapter mAdapter;
    private List<DepartmentMemberBean> mDepartmentStaffBeans = new ArrayList<>();
    private List<DepartmentListBean> mDepartmentList = new ArrayList<>();

    private DepartmentChildAdapter mChildAdapter;

    private String del_user_id;
    private boolean manager=false;

    @Override
    protected void initData() {
        super.initData();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            department_id = bundle.getString("department_id");
            department_name = bundle.getString("department_name");
        }
        toRequest(ApiConstants.EventTags.department_all);
        toRequest(ApiConstants.EventTags.department_department);
        toRequest(ApiConstants.EventTags.department_alluser);
    }

    @Override
    protected void initView() {
        mTvHeaderTitle.setText("" + department_name);
        mTvHeaderRight.setText("职务");


        HorizontalDividerItemDecoration decoration = new HorizontalDividerItemDecoration.Builder(this)
                .margin(0)
                .size(ViewUtil.dp2px(this, 1))
                .color(ResourceUtil.getColor(R.color.transparent))
                .build();
        mRvList.setBackgroundResource(R.color.theme_background_f5);
        mRvList.addItemDecoration(decoration);
        mChildAdapter = new DepartmentChildAdapter(R.layout.item_department_child, mDepartmentList);
        mRvDepartment.setLayoutManager(new LinearLayoutManager(this));
        mRvDepartment.addItemDecoration(decoration);
        mRvDepartment.setAdapter(mChildAdapter);

        mAdapter = new DepartmentMemberAdapter(R.layout.item_department_staff, mDepartmentStaffBeans);
        mRvList.addItemDecoration(decoration);
        mRvList.setLayoutManager(new LinearLayoutManager(this));
        mRvList.setAdapter(mAdapter);


        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void setListener() {
        super.setListener();
        mChildAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("department_id", "" + mDepartmentList.get(position).getDepartment_id());
                bundle.putString("department_name", "" + mDepartmentList.get(position).getName());
                gotoActivity(DepartmentHomeActivity.class, bundle, 0);
            }
        });
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("user_id", mDepartmentStaffBeans.get(position).getUser_id() + "");
                bundle.putString("company_user_id", mDepartmentStaffBeans.get(position).getCompany_user_id() + "");
                gotoActivity(StaffDetailActivity.class, bundle, 0);
            }
        });

        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.iv_call) {
                    JumpActionUtil.jump2CallPhone(DepartmentHomeActivity.this, mDepartmentStaffBeans.get(position).getTel() + "");
                } else if (view.getId() == R.id.iv_send_sms) {
                    JumpActionUtil.jump2SendSms(DepartmentHomeActivity.this, mDepartmentStaffBeans.get(position).getTel() + "");
                } else if (view.getId() == R.id.item_content) {
                    Bundle bundle = new Bundle();
                    bundle.putString("user_id", mDepartmentStaffBeans.get(position).getUser_id() + "");
                    bundle.putString("company_user_id", mDepartmentStaffBeans.get(position).getCompany_user_id() + "");
                    gotoActivity(StaffDetailActivity.class, bundle, 0);
                } else if (view.getId() == R.id.tv_del) {
                    if (!manager){
                        ToastUtil.showToast("无权访问");
                        return;
                    }
                    TipsDialog.wrap(DepartmentHomeActivity.this).setMsg("确认移除部门？").setOnActionClickListener(new TipsDialog.onActionClickListener() {
                        @Override
                        public void onSure(Dialog dialog) {
                            del_user_id = mDepartmentStaffBeans.get(position).getUser_id() + "";
                            toRequest(ApiConstants.EventTags.department_deluser);
                            dialog.dismiss();
                        }

                        @Override
                        public void onCancel(Dialog dialog) {
                            super.onCancel(dialog);
                            dialog.dismiss();
                            mAdapter.notifyItemChanged(position);
                        }
                    }).show();
                }
            }
        });
    }

    @OnClick({R.id.ll_header_back, R.id.ll_header_right, R.id.ll_add})
    public void onViewClicked(View view) {
        Bundle bundle;
        switch (view.getId()) {
            case R.id.ll_header_back:
                setResult(0);
                finish();
                break;
            case R.id.ll_header_right:
                bundle = new Bundle();
                bundle.putString("department_id", "" + department_id);
                bundle.putString("department_name", "" + department_name);
                gotoActivity(DepartmentTagManagerActivity.class, bundle, 0);
                break;
            case R.id.ll_add:
                bundle = new Bundle();
                bundle.putString("department_id", "" + department_id);
                bundle.putString("department_name", "" + department_name);
                gotoActivity(DepartmentAddMemberActivity.class, bundle, 0);
                break;
        }
    }

    @Override
    public void toRequest(int eventTag) {
        super.toRequest(eventTag);
        Map map = new HashMap();
        map.put("token", "" + LoginUserUtil.getToken(this));
        map.put("code", "" + LoginUserUtil.getCurrentEnterpriseNo(this));
        map.put("department_id", "" + department_id);
        if (eventTag == ApiConstants.EventTags.department_department) {
            map.put("loop", "" + 0);
            iCommonRequestPresenter.requestPost(eventTag, this, ApiConstants.department_department, map);
        } else if (eventTag == ApiConstants.EventTags.department_alluser) {
            iCommonRequestPresenter.requestPost(eventTag, this, ApiConstants.department_alluser, map);
        } else if (eventTag == ApiConstants.EventTags.department_deluser) {
            map.put("department_id", "" + department_id);
            map.put("user_ids", "" + del_user_id);
            iCommonRequestPresenter.requestPost(eventTag, this, ApiConstants.department_deluser, map);
        }else if (eventTag == ApiConstants.EventTags.department_all) {
            map.put("loop", "1");
            iCommonRequestPresenter.requestPost(eventTag, this, ApiConstants.department_all, map);
        }
    }

    @Override
    public void getRequestData(int eventTag, String result) {
        super.getRequestData(eventTag, result);
        ResultBean bean = JSON.parseObject(result, ResultBean.class);
        if (bean != null && bean.getCode() == 0) {
            if (eventTag == ApiConstants.EventTags.department_department) {
                List jsonArray = JSONArray.parseArray(bean.getData());
                List<DepartmentListBean> list = JSON.parseArray(jsonArray.get(0).toString(), DepartmentListBean.class);
                mDepartmentList.clear();
                mDepartmentList.addAll(list);
                mChildAdapter.notifyDataSetChanged();
            } else if (eventTag == ApiConstants.EventTags.department_alluser) {
                List<DepartmentMemberBean> list = JSON.parseArray(bean.getData(), DepartmentMemberBean.class);
                mDepartmentStaffBeans.clear();
                mDepartmentStaffBeans.addAll(list);
                mAdapter.notifyDataSetChanged();
            } else if (eventTag == ApiConstants.EventTags.department_deluser) {
                toRequest(ApiConstants.EventTags.department_alluser);
            } if (eventTag == ApiConstants.EventTags.department_all) {
                String json = bean.getData().substring(1, bean.getData().length() - 1);
                List<DepartmentNodeBean> beanList = JSON.parseArray(json, DepartmentNodeBean.class);
                setViewData(beanList);
            }
        } else {
            ToastUtil.showToast(bean != null ? bean.getMsg() : "加载错误，请重试");
        }

    }

    private void setViewData(List<DepartmentNodeBean> beanList) {
        for (int i = 0; i <beanList.size() ; i++) {

            String id = beanList.get(i).getDepartment_id()+"";
            if (id.equals(department_id)&&beanList.get(i).getLeader_id()==LoginUserUtil.getLoginUserBean(this).getId()){
                manager=true;

            }
        }
    }

    @Override
    public void onRequestFailureException(int eventTag, String msg) {
        super.onRequestFailureException(eventTag, msg);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        toRequest(ApiConstants.EventTags.department_department);
        toRequest(ApiConstants.EventTags.department_alluser);
    }
}