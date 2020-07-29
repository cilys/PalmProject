package com.aopcloud.palmproject.ui.fragment.project;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.aopcloud.base.base.BaseFragment;
import com.aopcloud.base.util.ResourceUtil;
import com.aopcloud.base.util.ToastUtil;
import com.aopcloud.base.util.ViewUtil;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.api.ApiConstants;
import com.aopcloud.palmproject.common.ResultBean;
import com.aopcloud.palmproject.dialog.BottomListMulDialog;
import com.aopcloud.palmproject.dialog.bean.MulBean;
import com.aopcloud.palmproject.ui.activity.project.bean.ProjectLogBean;
import com.aopcloud.palmproject.ui.adapter.project.ProjectLogAdapter;
import com.aopcloud.palmproject.ui.adapter.project.SpinnerItemAdapter;
import com.aopcloud.palmproject.utils.LoginUserUtil;
import com.aopcloud.palmproject.view.decoration.HorizontalDividerItemDecoration;
import com.cily.utils.base.StrUtils;
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

    @BindView(R.id.ll_content_layout)
    LinearLayout mLlContentLayout;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mRefreshLayout;

    @BindView(R.id.tv_child)
    TextView tv_child;
    @BindView(R.id.tv_user)
    TextView tv_user;

    private ProjectLogAdapter mAdapter;
    private List<ProjectLogBean> mBeanList = new ArrayList();
    private List<ProjectLogBean> mAllBeanList = new ArrayList();

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

        Drawable df = getResources().getDrawable(R.mipmap.icon_sort_down);
        df.setBounds(0, 0, 46, 26);
        tv_child.setText("全部工单");
        tv_child.setCompoundDrawables(null, null, df, null);
        tv_child.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showChildDialog();
            }
        });

        tv_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTypeDialog();
            }
        });
        tv_user.setText("全部班组");
        tv_user.setCompoundDrawables(null, null, df, null);
    }
    private List<MulBean> datas_child = new ArrayList<>();
    private List<MulBean> datas_user = new ArrayList<>();
    private BottomListMulDialog dialog_user;
    private int selectedUserId = -1;
    private void showTypeDialog(){
        if (datas_user == null){
            datas_user = new ArrayList<>();
        }
        if (datas_user.size() < 1) {
            MulBean mulBean = new MulBean();
            mulBean.setId("-1");
            mulBean.setName("全部工单");
            datas_user.add(mulBean);
        }
        for (MulBean b : datas_user) {
            if (tv_user.getText().toString().trim().equals(b.getName())) {
                b.setSelected(true);
            }
        }
        dialog_user = new BottomListMulDialog(getActivity(), false, datas_user);
        dialog_user.setOnClickListener(new BottomListMulDialog.OnClickListener() {
            @Override
            public void onCommit(View view) {
                int position = -1;
                for (int i = 0; i < datas_user.size(); i++) {
                    if (datas_user.get(i).isSelected()) {
                        position = i;
                        break;
                    }
                }
                if (position > -1) {
                    if (!StrUtils.isEmpty(datas_user.get(position).getName())) {
                        tv_user.setText(datas_user.get(position).getName());
                        selectedUserId = Integer.valueOf(datas_user.get(position).getId());
                        setFilter(1, selectedChildId, selectedUserId);
                    }
                }
            }

            @Override
            public void onCancel(View view) {

            }

            @Override
            public void onItemClick(View view, int position, boolean selected) {
                if (selected) {
                    String name = datas_user.get(position).getName();
                    if (!StrUtils.isEmpty(name)) {
                        tv_user.setText(name);
                        selectedUserId = Integer.valueOf(datas_user.get(position).getId());
                        setFilter(1, selectedChildId, selectedUserId);
                    }
                }
            }
        });
    }
    private BottomListMulDialog dialog_child;
    private int selectedChildId = -1;
    private void showChildDialog(){
        if (datas_child == null){
            datas_child = new ArrayList<>();
        }
        if (datas_child.size() < 1) {
            MulBean mulBean = new MulBean();
            mulBean.setId("-1");
            mulBean.setName("全部工单");
            datas_child.add(mulBean);
        }
        for (MulBean b : datas_child) {
            if (tv_child.getText().toString().trim().equals(b.getName())) {
                b.setSelected(true);
            }
        }
        dialog_child = new BottomListMulDialog(getActivity(), false, datas_child);
        dialog_child.setOnClickListener(new BottomListMulDialog.OnClickListener() {
            @Override
            public void onCommit(View view) {
                int position = -1;
                for (int i = 0; i < datas_child.size(); i++) {
                    if (datas_child.get(i).isSelected()) {
                        position = i;
                        break;
                    }
                }
                if (position > -1) {
                    if (!StrUtils.isEmpty(datas_child.get(position).getName())) {
                        tv_child.setText(datas_child.get(position).getName());
                        selectedChildId = Integer.valueOf(datas_child.get(position).getId());
                        setFilter(0, selectedUserId, selectedChildId);
                    }
                }
            }

            @Override
            public void onCancel(View view) {

            }

            @Override
            public void onItemClick(View view, int position, boolean selected) {
                if (selected) {
                    String name = datas_child.get(position).getName();
                    if (!StrUtils.isEmpty(name)) {
                        tv_child.setText(name);
                        selectedChildId = Integer.valueOf(datas_child.get(position).getId());
                        setFilter(0, selectedUserId, selectedChildId);
                    }
                }
            }
        });
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        toRequest(ApiConstants.EventTags.trends_project);
    }

    private Map mUserMap = new HashMap();
    private Map mTaskMap = new HashMap();

    private void setViewData(List<ProjectLogBean> beanList) {
        if (datas_user == null) {
            datas_user = new ArrayList<>();
        }
        datas_user.clear();
        datas_user.add(new MulBean("-1", "全部班组"));

        if (datas_child == null){
            datas_child = new ArrayList<>();
        }
        datas_child.clear();
        datas_child.add(new MulBean("-1", "全部工单"));

        mUserMap.clear();
        for (int i = 0; i < beanList.size(); i++) {
            if (!mTaskMap.containsKey(beanList.get(i).getTask_id()) && !TextUtils.isEmpty(beanList.get(i).getTask_name())) {
                mTaskMap.put(beanList.get(i).getTask_id(), beanList.get(i));
                datas_child.add(new MulBean(String.valueOf(beanList.get(i).getTask_id()), beanList.get(i).getTask_name()));
            }
            if (!mUserMap.containsKey(beanList.get(i).getUser_id()) && !TextUtils.isEmpty(beanList.get(i).getUser_name())) {
                mUserMap.put(beanList.get(i).getUser_id(), beanList.get(i));
                datas_user.add(new MulBean(String.valueOf(beanList.get(i).getUser_id()), beanList.get(i).getUser_name()));
            }
        }
        mRefreshLayout.finishLoadMore();
        mRefreshLayout.finishRefresh();
        mBeanList.clear();
        mAllBeanList.clear();
        mAllBeanList.addAll(beanList);
        mBeanList.addAll(mAllBeanList);
        mAdapter.notifyDataSetChanged();

        if (dialog_child != null) {
            dialog_child.notifyDateChanged();
        }
        if (dialog_user != null) {
            dialog_user.notifyDateChanged();
        }
    }


    private void setFilter(int type, int currentTypeId, int id) {
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
            iCommonRequestPresenter.requestPost(eventTag, mActivity, ApiConstants.trends_project, map);
        } else if (eventTag == ApiConstants.EventTags.project_tasks) {
            iCommonRequestPresenter.requestPost(eventTag, mActivity, ApiConstants.project_tasks, map);
        }
    }

    @Override
    public void getRequestData(int eventTag, String result) {
        super.getRequestData(eventTag, result);
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
