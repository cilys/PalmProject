package com.aopcloud.palmproject.ui.fragment.project;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.aopcloud.base.base.BaseFragment;
import com.aopcloud.base.log.Logcat;
import com.aopcloud.base.util.ResourceUtil;
import com.aopcloud.base.util.ToastUtil;
import com.aopcloud.base.util.ViewUtil;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.api.ApiConstants;
import com.aopcloud.palmproject.common.ResultBean;
import com.aopcloud.palmproject.ui.activity.project.ProjectDetailActivity;
import com.aopcloud.palmproject.ui.activity.project.bean.ProjectListBean;
import com.aopcloud.palmproject.ui.adapter.project.ProjectListAdapter;
import com.aopcloud.palmproject.utils.LoginUserUtil;
import com.aopcloud.palmproject.view.decoration.HorizontalDividerItemDecoration;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @PackageName : com.aopcloud.palmproject.ui.fragment.project
 * @File : DashboardFragment.java
 * @Date : 2020/5/2 2020/5/2
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class ProjectListFragment extends BaseFragment implements ProjectListAdapter.OnItemClickListener, TextView.OnEditorActionListener,
        CheckBox.OnCheckedChangeListener {


    @BindView(R.id.rv_list)
    RecyclerView mRvList;
    @BindView(R.id.et_search)
    EditText mEtSearch;
    @BindView(R.id.checkbox)
    CheckBox mCheckbox;

    private ProjectListAdapter mAdapter;

    private List<ProjectListBean> mBeanList = new ArrayList();

    private String type;

    public static ProjectListFragment getInstance(String type) {
        ProjectListFragment fragment = new ProjectListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initData() {
        super.initData();
        Bundle bundle = getArguments();
        if (bundle != null) {
            type = bundle.getString("type");
        }
        toRequest(ApiConstants.EventTags.project_all);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_project_list;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mAdapter = new ProjectListAdapter(R.layout.item_project_list, mBeanList);
        mAdapter.setOnItemClickListener(this);
        HorizontalDividerItemDecoration decoration = new HorizontalDividerItemDecoration.Builder(mActivity)
                .margin(0)
                .size(ViewUtil.dp2px(mActivity, 10))
                .color(ResourceUtil.getColor(R.color.transparent))
                .build();
        mRvList.setBackgroundResource(R.color.theme_background_f5);
        mRvList.addItemDecoration(decoration);
        mRvList.setLayoutManager(new LinearLayoutManager(mActivity));
        mRvList.setAdapter(mAdapter);
        mCheckbox.setOnCheckedChangeListener(this);
        mEtSearch.setOnEditorActionListener(this);

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        ToastUtil.showToast("敬请期待");
        buttonView.setChecked(false);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Bundle bundle = new Bundle();
        bundle.putString("project_id", mBeanList.get(position).getProject_id() + "");
        bundle.putString("project_name", mBeanList.get(position).getName());
        bundle.putString("company_id", mBeanList.get(position).getCompany_code());
        bundle.putString("project_status", mBeanList.get(position).getStatus());
        gotoActivity(ProjectDetailActivity.class, 0, bundle);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        ToastUtil.showToast("敬请期待");
        return false;
    }

    private void setViewData(List<ProjectListBean> beanList) {
        mBeanList.clear();
        mBeanList.addAll(beanList);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void toRequest(int eventTag) {
        super.toRequest(eventTag);
        Map map = new HashMap();
        map.put("token", "" + LoginUserUtil.getToken(mActivity));
        map.put("code", "" + LoginUserUtil.getCurrentEnterpriseNo(mActivity));
        if (eventTag == ApiConstants.EventTags.project_all) {
            map.put("type", "" + type);//类型，1：我负责的，2：我参与的，3：企业全部
            iCommonRequestPresenter.requestPost(eventTag, mActivity, ApiConstants.project_all, map);
        }
    }

    @Override
    public void getRequestData(int eventTag, String result) {
        super.getRequestData(eventTag, result);
        Logcat.i("------------" + eventTag + "/" + result);
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
        Logcat.i("------------" + eventTag + "/" + msg);
    }
}
