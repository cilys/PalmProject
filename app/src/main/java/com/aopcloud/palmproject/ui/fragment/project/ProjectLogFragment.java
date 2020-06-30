package com.aopcloud.palmproject.ui.fragment.project;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Spinner;
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
import com.aopcloud.palmproject.ui.activity.project.bean.ProjectLogBean;
import com.aopcloud.palmproject.ui.adapter.project.ProjectLogAdapter;
import com.aopcloud.palmproject.ui.adapter.project.SpinnerItemAdapter;
import com.aopcloud.palmproject.utils.LoginUserUtil;
import com.aopcloud.palmproject.view.decoration.HorizontalDividerItemDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @PackageName : com.aopcloud.palmproject.ui.fragment.project
 * @File : DashboardFragment.java
 * @Date : 2020/5/2 2020/5/2
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class ProjectLogFragment extends BaseFragment implements OnRefreshListener {


    @BindView(R.id.rv_list)
    RecyclerView mRvList;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.ll_time)
    LinearLayout mLlTime;
    @BindView(R.id.spinner_child)
    Spinner mSpinnerChild;
    @BindView(R.id.spinner_user)
    Spinner mSpinnerUser;
    @BindView(R.id.ll_content_layout)
    LinearLayout mLlContentLayout;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mRefreshLayout;

    private ProjectLogAdapter mAdapter;
    private List<ProjectLogBean> mBeanList = new ArrayList();
    private List<ProjectLogBean> mAllBeanList = new ArrayList();
    private SpinnerItemAdapter mUserAdapter;
    private SpinnerItemAdapter mTaskAdapter;

    public static ProjectLogFragment getInstance(String project_id) {
        ProjectLogFragment fragment = new ProjectLogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("project_id", "" + project_id);
        fragment.setArguments(bundle);
        return fragment;
    }

    private String project_id;

    @Override
    protected void initData() {
        super.initData();
        Bundle bundle = getArguments();
        if (bundle != null) {
            project_id = bundle.getString("project_id");
        }
        toRequest(ApiConstants.EventTags.trends_project);
    }


    @Override
    protected int setLayoutId() {
        return R.layout.fragment_project_log;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mRefreshLayout.setRefreshHeader(new ClassicsHeader(mActivity));
        mRefreshLayout.setEnableLoadMore(false);
        mRefreshLayout.setOnRefreshListener(this::onRefresh);
        mAdapter = new ProjectLogAdapter(R.layout.item_project_day_log, mBeanList);
        HorizontalDividerItemDecoration decoration = new HorizontalDividerItemDecoration.Builder(mActivity)
                .margin(0)
                .size(ViewUtil.dp2px(mActivity, 1))
                .color(ResourceUtil.getColor(R.color.transparent))
                .build();
        mRvList.setBackgroundResource(R.color.theme_background_f5);
        mRvList.addItemDecoration(decoration);
        mRvList.setLayoutManager(new LinearLayoutManager(mActivity));
        mRvList.setAdapter(mAdapter);
        mAdapter.setEmptyView(R.layout.layout_common_empty, mRvList);
        mAdapter.isUseEmpty(true);

        mUserAdapter = new SpinnerItemAdapter(mActivity, R.layout.item_spinner_team, mUserList);
        mSpinnerUser.setAdapter(mUserAdapter);
        mTaskAdapter = new SpinnerItemAdapter(mActivity, R.layout.item_spinner_team, mTaskList);
        mSpinnerChild.setAdapter(mTaskAdapter);
    }

    @Override
    protected void setListener() {
        super.setListener();
        mSpinnerChild.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinnerItemAdapter.SpinnerItemBean itemBean = (SpinnerItemAdapter.SpinnerItemBean) mSpinnerUser.getSelectedItem();
                setFilter(0, itemBean.getId(), mTaskList.get(position).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mSpinnerUser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinnerItemAdapter.SpinnerItemBean itemBean = (SpinnerItemAdapter.SpinnerItemBean) mSpinnerChild.getSelectedItem();
                setFilter(1, itemBean.getId(), mUserList.get(position).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        toRequest(ApiConstants.EventTags.trends_project);
    }


    private List<SpinnerItemAdapter.SpinnerItemBean> mTaskList = new ArrayList();
    private List<SpinnerItemAdapter.SpinnerItemBean> mUserList = new ArrayList();
    private Map mUserMap = new HashMap();
    private Map mTaskMap = new HashMap();

    private void setViewData(List<ProjectLogBean> beanList) {
        mTaskList.clear();
        mUserList.clear();
        mUserMap.clear();
        mTaskList.add(new SpinnerItemAdapter.SpinnerItemBean(-1, "全部工单"));
        mUserList.add(new SpinnerItemAdapter.SpinnerItemBean(-1, "全部班组"));
        for (int i = 0; i < beanList.size(); i++) {
            if (!mTaskMap.containsKey(beanList.get(i).getTask_id()) && !TextUtils.isEmpty(beanList.get(i).getTask_name())) {
                mTaskMap.put(beanList.get(i).getTask_id(), beanList.get(i));
                mTaskList.add(new SpinnerItemAdapter.SpinnerItemBean(beanList.get(i).getTask_id(), beanList.get(i).getTask_name()));
            }
            if (!mUserMap.containsKey(beanList.get(i).getUser_id()) && !TextUtils.isEmpty(beanList.get(i).getUser_name())) {
                mUserMap.put(beanList.get(i).getUser_id(), beanList.get(i));
                mUserList.add(new SpinnerItemAdapter.SpinnerItemBean(beanList.get(i).getUser_id(), beanList.get(i).getUser_name()));
            }
        }
        mRefreshLayout.finishLoadMore();
        mRefreshLayout.finishRefresh();
        mBeanList.clear();
        mAllBeanList.clear();
        mAllBeanList.addAll(beanList);
        mBeanList.addAll(mAllBeanList);
        mAdapter.notifyDataSetChanged();

        mUserAdapter.notifyDataSetChanged();
        mTaskAdapter.notifyDataSetChanged();
    }


    private void setFilter(int type, int currentTypeId, int id) {
        Logcat.e("---------" + type + "/" + currentTypeId + "/" + id);
        mBeanList.clear();
        if (id == -1) {
            mBeanList.addAll(mAllBeanList);
        } else {
            for (int i = 0; i < mAllBeanList.size(); i++) {
                if (type == 0 && mAllBeanList.get(i).getTask_id() == id) {
                    if (currentTypeId == -1) {
                        mBeanList.add(mAllBeanList.get(i));
                    } else if (mAllBeanList.get(i).getUser_id() == currentTypeId) {
                        mBeanList.add(mAllBeanList.get(i));
                    }
                } else if (type == 1 && mAllBeanList.get(i).getUser_id() == id) {
                    if (currentTypeId == -1) {
                        mBeanList.add(mAllBeanList.get(i));
                    } else if (mAllBeanList.get(i).getTask_id() == currentTypeId) {
                        mBeanList.add(mAllBeanList.get(i));
                    }
                }
            }
        }
        mAdapter.notifyDataSetChanged();
    }


    @Override
    public void toRequest(int eventTag) {
        super.toRequest(eventTag);
        Map map = new HashMap();
        map.put("token", "" + LoginUserUtil.getToken(mActivity));
        map.put("code", "" + LoginUserUtil.getCurrentEnterpriseNo(mActivity));
        if (eventTag == ApiConstants.EventTags.trends_project) {
            map.put("project_id", "" + project_id);//项目名称
            map.put("type", "-1");
            Logcat.i("------------" + eventTag + "/" + JSON.toJSONString(map));
            iCommonRequestPresenter.requestPost(eventTag, mActivity, ApiConstants.trends_project, map);
        } else if (eventTag == ApiConstants.EventTags.project_tasks) {
            Logcat.i("------------" + eventTag + "/" + JSON.toJSONString(map));
            iCommonRequestPresenter.requestPost(eventTag, mActivity, ApiConstants.project_tasks, map);
        }
    }

    @Override
    public void getRequestData(int eventTag, String result) {
        super.getRequestData(eventTag, result);
        Logcat.i("------------" + eventTag + "/" + result);
        ResultBean bean = JSON.parseObject(result, ResultBean.class);
        if (bean != null && bean.getCode() == 0) {
            if (eventTag == ApiConstants.EventTags.trends_project) {
                List<ProjectLogBean> beanList = JSON.parseArray(bean.getData(), ProjectLogBean.class);
                setViewData(beanList);
            } else if (eventTag == ApiConstants.EventTags.project_tasks) {
            }
        } else {
            mRefreshLayout.finishLoadMore();
            mRefreshLayout.finishRefresh();
            ToastUtil.showToast(bean != null ? bean.getMsg() : "加载错误，请重试");
        }

    }

    @Override
    public void onRequestFailureException(int eventTag, String msg) {
        super.onRequestFailureException(eventTag, msg);
        Logcat.i("------------" + eventTag + "/" + msg);
        mRefreshLayout.finishLoadMore();
        mRefreshLayout.finishRefresh();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        toRequest(ApiConstants.EventTags.trends_project);
    }


    @OnClick({R.id.tv_time, R.id.ll_time})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_time:
            case R.id.ll_time:
                ToastUtil.showToast("敬请期待");
                break;
        }
    }
}
