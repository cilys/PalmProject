package com.aopcloud.palmproject.ui.activity.department;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.aopcloud.base.annotation.Layout;
import com.aopcloud.base.util.ResourceUtil;
import com.aopcloud.base.util.ToastUtil;
import com.aopcloud.base.util.ViewUtil;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.api.ApiConstants;
import com.aopcloud.palmproject.common.ResultBean;
import com.aopcloud.palmproject.ui.activity.BaseAc;
import com.aopcloud.palmproject.ui.activity.department.bean.DepartmentListBean;
import com.aopcloud.palmproject.ui.activity.staff.StaffApprovalActivity;
import com.aopcloud.palmproject.ui.activity.staff.StaffDetailActivity;
import com.aopcloud.palmproject.ui.activity.staff.StaffInviteActivity;
import com.aopcloud.palmproject.ui.activity.staff.StaffQuitActivity;
import com.aopcloud.palmproject.ui.activity.staff.bean.StaffListBean;
import com.aopcloud.palmproject.ui.adapter.department.DepartmentChildAdapter;
import com.aopcloud.palmproject.ui.adapter.staff.StaffListAdapter;
import com.aopcloud.palmproject.utils.JumpActionUtil;
import com.aopcloud.palmproject.utils.LoginUserUtil;
import com.aopcloud.palmproject.view.decoration.HorizontalDividerItemDecoration;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.mcxtzhang.indexlib.IndexBar.widget.IndexBar;
import com.mcxtzhang.indexlib.suspension.SuspensionDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @PackageName : com.aopcloud.palmproject.ui.activity.enterprise
 * @File : EnterpriseDepartmentAddActivity.java
 * @Date : 2020/4/21 11:07
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @VersionCode : V 1.0
 * @Describe ：
 */

@Layout(R.layout.activity_enterprise_staff_home)
public class EnterpriseDepartmentHomeActivity extends BaseAc {
    @BindView(R.id.ll_header_back)
    LinearLayout mLlHeaderBack;
    @BindView(R.id.tv_header_title)
    TextView mTvHeaderTitle;
    @BindView(R.id.iv_header_more)
    ImageView mIvHeaderMore;
    @BindView(R.id.ll_header_right)
    LinearLayout mLlHeaderRight;
    @BindView(R.id.ll_common_layout)
    LinearLayout mLlCommonLayout;
    @BindView(R.id.et_search)
    EditText mEtSearch;
    @BindView(R.id.rv_list)
    RecyclerView mRvList;
    @BindView(R.id.index_bar)
    IndexBar mIndexBar;
    @BindView(R.id.tv_index_bar_hint)
    TextView mTvIndexBarHint;
    @BindView(R.id.ll_relative)
    RelativeLayout mLlRelative;
    @BindView(R.id.ll_new_staff)
    LinearLayout mLlNewStaff;
    @BindView(R.id.ll_default_dept)
    LinearLayout mLlDefaultDept;
    @BindView(R.id.rv_department)
    RecyclerView mRvDepartment;
    @BindView(R.id.ll_quit_staff)
    LinearLayout mLlQuitStaff;
    @BindView(R.id.ll_invite_staff)
    LinearLayout mLlInviteStaff;
    @BindView(R.id.ll_total_dept)
    LinearLayout mLlTotleDept;

    private StaffListAdapter mAdapter;
    private List<StaffListBean> dataBeanList = new ArrayList<>();
    private SuspensionDecoration mDecoration;
    private static final String INDEX_STRING_STAR = "☆";
    private List<DepartmentListBean> mDepartmentList = new ArrayList<>();

    private DepartmentChildAdapter mChildAdapter;

    @Override
    protected void initData() {
        super.initData();
        toRequest(ApiConstants.EventTags.department_all);
        toRequest(ApiConstants.EventTags.company_usermange);
    }

    @Override
    protected void initView() {
        mTvHeaderTitle.setText("通讯录");

        mIvHeaderMore.setImageResource(R.mipmap.gongsibiao1);


        HorizontalDividerItemDecoration decoration = new HorizontalDividerItemDecoration.Builder(this)
                .margin(0)
                .size(ViewUtil.dp2px(this, 1))
                .color(ResourceUtil.getColor(R.color.transparent))
                .build();
        mRvDepartment.setBackgroundResource(R.color.theme_background_f5);
        mRvDepartment.addItemDecoration(decoration);
        mChildAdapter = new DepartmentChildAdapter(R.layout.item_department_child, mDepartmentList);
        mRvDepartment.setLayoutManager(new LinearLayoutManager(this));
        mRvDepartment.addItemDecoration(decoration);
        mRvDepartment.setAdapter(mChildAdapter);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mAdapter = new StaffListAdapter(R.layout.item_staff_home, dataBeanList);
        mRvList.addItemDecoration(mDecoration = new SuspensionDecoration(this, dataBeanList));
        mRvList.setLayoutManager(layoutManager);
        mRvList.setAdapter(mAdapter);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mIndexBar.setmPressedShowTextView(mTvIndexBarHint)
                    .setNeedRealIndex(true)
                    .setmLayoutManager(layoutManager)
                    .setScrollBarSize(5);
        }
        mDecoration.setColorTitleBg(ResourceUtil.getColor("#f2f2f2"));
        mDecoration.setColorTitleFont(ResourceUtil.getColor("#666666"));

    }

    private void setViewData(List<StaffListBean> beanList) {

        dataBeanList.clear();
        dataBeanList.addAll(beanList);
        mIndexBar.getDataHelper().sortSourceDatas(dataBeanList);
        mIndexBar.setmSourceDatas(dataBeanList);
        mIndexBar.requestLayout();
        mIndexBar.invalidate();
        mDecoration.setmDatas(dataBeanList);
        mAdapter.setNewData(dataBeanList);
    }


    @Override
    protected void setListener() {
        super.setListener();
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("user_id", dataBeanList.get(position).getUser_id() + "");
                bundle.putString("company_user_id", dataBeanList.get(position).getCompany_user_id() + "");
                gotoActivity(StaffDetailActivity.class, bundle, 0);
            }
        });
        mChildAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("department_id", "" + mDepartmentList.get(position).getDepartment_id());
                bundle.putString("department_name", "" + mDepartmentList.get(position).getName());
                gotoActivity(DepartmentHomeActivity.class, bundle, 0);
            }
        });
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.iv_call) {
                    JumpActionUtil.jump2CallPhone(EnterpriseDepartmentHomeActivity.this,dataBeanList.get(position).getTel()+"");
                } else if (view.getId() == R.id.iv_send_sms) {
                    JumpActionUtil.jump2SendSms(EnterpriseDepartmentHomeActivity.this,dataBeanList.get(position).getTel()+"");

                }
            }
        });
    }

    @OnClick({R.id.ll_header_back, R.id.ll_header_right, R.id.ll_new_staff, R.id.ll_default_dept, R.id.ll_total_dept, R.id.ll_quit_staff, R.id.ll_invite_staff, R.id.ll_relative})
    public void onViewClicked(View view) {
        Bundle bundle;
        switch (view.getId()) {
            case R.id.ll_header_back:
                finish();
                break;
            case R.id.ll_header_right:
                gotoActivity(EnterpriseDepartmentActivity.class, 0);
                break;
            case R.id.ll_new_staff:
                gotoActivity(StaffApprovalActivity.class, 0);
                break;
            case R.id.ll_default_dept:
                bundle = new Bundle();
                gotoActivity(DepartmentAbsentActivity.class, bundle, 0);
                break;
            case R.id.ll_quit_staff:
                gotoActivity(StaffQuitActivity.class, 0);
                break;
            case R.id.ll_invite_staff:
                gotoActivity(StaffInviteActivity.class, 0);
                break;
            case R.id.ll_relative:
                break;
            case R.id.ll_total_dept:
                bundle = new Bundle();
                bundle.putString("department_id", "" + 0);
                bundle.putString("department_name", "" + "总经办");
                gotoActivity(DepartmentHomeActivity.class, bundle, 0);
                break;
        }
    }


    @Override
    public void toRequest(int eventTag) {
        super.toRequest(eventTag);
        Map map = new HashMap();
        map.put("token", "" + LoginUserUtil.getToken(this));
        map.put("code", "" + LoginUserUtil.getCurrentEnterpriseNo(this));
        if (eventTag == ApiConstants.EventTags.company_usermange) {
            map.put("status", "" + 1);
            iCommonRequestPresenter.requestPost(eventTag, this, ApiConstants.company_usermange, map);
        } else if (eventTag == ApiConstants.EventTags.department_all) {
            map.put("loop", "" + 0);
            iCommonRequestPresenter.requestPost(eventTag, this, ApiConstants.department_all, map);
        }
    }

    @Override
    public void getRequestData(int eventTag, String result) {
        super.getRequestData(eventTag, result);
        dismissPopupLoading();
        ResultBean bean = JSON.parseObject(result, ResultBean.class);
        if (bean != null && bean.getCode() == 0) {
            if (eventTag == ApiConstants.EventTags.company_usermange) {
                List<StaffListBean> beanList = JSON.parseArray(bean.getData(), StaffListBean.class);
                setViewData(beanList);
            } else if (eventTag == ApiConstants.EventTags.department_all) {
                List jsonArray = JSONArray.parseArray(bean.getData());
                List<DepartmentListBean> list = JSON.parseArray(jsonArray.get(0).toString(), DepartmentListBean.class);
                mDepartmentList.clear();
                mDepartmentList.addAll(list);
                mChildAdapter.notifyDataSetChanged();
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
        toRequest(ApiConstants.EventTags.company_usermange);
        toRequest(ApiConstants.EventTags.department_all);
    }
}









