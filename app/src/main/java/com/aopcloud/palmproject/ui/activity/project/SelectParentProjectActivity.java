package com.aopcloud.palmproject.ui.activity.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.aopcloud.base.annotation.Layout;
import com.aopcloud.base.util.ToastUtil;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.api.ApiConstants;
import com.aopcloud.palmproject.common.ResultBean;
import com.aopcloud.palmproject.ui.activity.BaseAc;
import com.aopcloud.palmproject.ui.activity.project.bean.ProjectListBean;
import com.aopcloud.palmproject.ui.adapter.project.SelectParentProjectAdapter;
import com.aopcloud.palmproject.utils.LoginUserUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @PackageName : com.aopcloud.palmproject.ui.activity.project
 * @File : SelectParentProjectActivity.java
 * @Date : 2020/5/21 14:13
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @VersionCode : V 1.0
 * @Describe ：
 */
@Layout(R.layout.activity_project_select_parent)
public class SelectParentProjectActivity extends BaseAc implements ExpandableListView.OnChildClickListener, SelectParentProjectAdapter.OnPickerListener {
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
    @BindView(R.id.elv_list)
    ExpandableListView mElvList;


    private SelectParentProjectAdapter mAdapter;
    private Map<String, List<ProjectListBean>> mProjectMap = new HashMap<>();

    @Override
    protected void initData() {
        super.initData();
        toRequest(ApiConstants.EventTags.project_all);
    }

    @Override
    protected void initView() {
        mTvHeaderTitle.setText("所属项目");

        mTvHeaderRight.setText("确定");
        mAdapter = new SelectParentProjectAdapter(this, R.layout.item_project_add_company, R.layout.item_project_add_company_project, mProjectMap);
        mAdapter.setOnPickerListener(this);
        mElvList.setOnChildClickListener(this);
        mElvList.setAdapter(mAdapter);
    }

    private void setViewData(List<ProjectListBean> beanList) {
        List<String> company_code = new ArrayList();
        for (int i = 0; i < beanList.size(); i++) {
            if (!company_code.contains(beanList.get(i).getCompany_code())) {
                company_code.add(beanList.get(i).getCompany_code());
            }
        }

        for (int i = 0; i < company_code.size(); i++) {
            List<ProjectListBean> list = new ArrayList();
            for (int j = 0; j < beanList.size(); j++) {
                if (beanList.get(j).getCompany_code() == company_code.get(i)) {
                    list.add(beanList.get(j));
                }
            }
            mProjectMap.put("" + company_code.get(i), list);
        }

        if (mAdapter.getGroupCount() > 0) {
            mElvList.expandGroup(0);
        }
        mAdapter.notifyDataSetChanged();
    }


    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

        CheckBox checkBox = v.findViewById(R.id.checkbox);
        if (checkBox.isChecked()) {
            mAdapter.setSelectedPosition(-1, -1);
        } else {
            mAdapter.setSelectedPosition(groupPosition, childPosition);
        }
        mAdapter.notifyDataSetChanged();
        return false;
    }

    ProjectListBean mProjectListBean;
    @Override
    public void onPickerItem(ProjectListBean projectListBean) {
        mProjectListBean = projectListBean;
    }

    @OnClick({R.id.ll_header_back, R.id.ll_header_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_header_back:
                finish();
                break;
            case R.id.ll_header_right:
                if (mProjectListBean == null) {
                    ToastUtil.showToast("请选择所属项目");
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString("project_id", "" + mProjectListBean.getProject_id());
                bundle.putString("project_name", "" + mProjectListBean.getName());
                Intent intent = new Intent();
                intent.putExtras(bundle);
                setResult(0, intent);
                finish();
                break;
        }
    }

    @Override
    public void toRequest(int eventTag) {
        super.toRequest(eventTag);
        Map map = new HashMap();
        map.put("token", "" + LoginUserUtil.getToken(this));
        map.put("code", "" + LoginUserUtil.getCurrentEnterpriseNo(this));
        if (eventTag == ApiConstants.EventTags.project_all) {
            map.put("type", "" + 1);//类型，1：我负责的，2：我参与的，3：企业全部
            iCommonRequestPresenter.requestPost(eventTag, this, ApiConstants.project_all, map);
        }
    }

    @Override
    public void getRequestData(int eventTag, String result) {
        super.getRequestData(eventTag, result);
        ResultBean bean = JSON.parseObject(result, ResultBean.class);
        if (bean != null && bean.getCode() == 0) {
            if (eventTag == ApiConstants.EventTags.project_all) {
                List<ProjectListBean> beanList = JSON.parseArray(bean.getData(), ProjectListBean.class);
                setViewData(beanList);
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