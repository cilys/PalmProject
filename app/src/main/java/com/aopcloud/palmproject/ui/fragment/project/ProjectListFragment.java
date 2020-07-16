package com.aopcloud.palmproject.ui.fragment.project;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.aopcloud.base.base.BaseFragment;
import com.aopcloud.base.util.ResourceUtil;
import com.aopcloud.base.util.ToastUtil;
import com.aopcloud.base.util.ViewUtil;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.api.ApiConstants;
import com.aopcloud.palmproject.common.ResultBean;
import com.aopcloud.palmproject.ui.activity.project.ProjectDetailActivity;
import com.aopcloud.palmproject.ui.activity.project.bean.ProjectListBean;
import com.aopcloud.palmproject.ui.adapter.project.ProjectListAdapter;
import com.aopcloud.palmproject.ui.fragment.home.HomeProjectFragment;
import com.aopcloud.palmproject.utils.LoginUserUtil;
import com.aopcloud.palmproject.view.decoration.HorizontalDividerItemDecoration;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cily.utils.base.StrUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * @PackageName : com.aopcloud.palmproject.ui.fragment.project
 * @File : DashboardFragment.java
 * @Date : 2020/5/2 2020/5/2
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class ProjectListFragment extends BaseFragment implements ProjectListAdapter.OnItemClickListener, TextView.OnEditorActionListener {

    @BindView(R.id.rv_list)
    RecyclerView mRvList;
    @BindView(R.id.et_search)
    EditText mEtSearch;

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

    private DrawerLayout drawer_layout;
    private String state = HomeProjectFragment.STATE_all;

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
        mEtSearch.setOnEditorActionListener(this);

        view.findViewById(R.id.tv_filter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawer_layout != null) {
                    drawer_layout.openDrawer(Gravity.RIGHT);
                }
            }
        });

        view.findViewById(R.id.tv_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawer_layout != null){
                    drawer_layout.closeDrawers();
                }


                toRequest(ApiConstants.EventTags.project_all);
            }
        });

        drawer_layout = (DrawerLayout)view.findViewById(R.id.drawer_layout);

        CheckBox checkbox_state_all = (CheckBox)view.findViewById(R.id.checkbox_state_all);
        CheckBox checkbox_state_design = (CheckBox)view.findViewById(R.id.checkbox_state_design);
        CheckBox checkbox_state_ready = (CheckBox)view.findViewById(R.id.checkbox_state_ready);
        CheckBox checkbox_state_build = (CheckBox)view.findViewById(R.id.checkbox_state_build);
        CheckBox checkbox_state_completed = (CheckBox)view.findViewById(R.id.checkbox_state_completed);
        CheckBox checkbox_state_finish = (CheckBox)view.findViewById(R.id.checkbox_state_finish);
        CheckBox checkbox_state_termination = (CheckBox)view.findViewById(R.id.checkbox_state_termination);
        CheckBox checkbox_state_stop = (CheckBox)view.findViewById(R.id.checkbox_state_stop);
        checkbox_state_all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    state = HomeProjectFragment.STATE_all;
                    checkbox_state_design.setChecked(false);
                    checkbox_state_ready.setChecked(false);
                    checkbox_state_build.setChecked(false);
                    checkbox_state_completed.setChecked(false);
                    checkbox_state_finish.setChecked(false);
                    checkbox_state_termination.setChecked(false);
                    checkbox_state_stop.setChecked(false);
                }
            }
        });
        checkbox_state_design.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    state = HomeProjectFragment.STATE_design;
                    checkbox_state_all.setChecked(false);
                    checkbox_state_ready.setChecked(false);
                    checkbox_state_build.setChecked(false);
                    checkbox_state_completed.setChecked(false);
                    checkbox_state_finish.setChecked(false);
                    checkbox_state_termination.setChecked(false);
                    checkbox_state_stop.setChecked(false);
                }
            }
        });
        checkbox_state_ready.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    state = HomeProjectFragment.STATE_ready;
                    checkbox_state_all.setChecked(false);
                    checkbox_state_design.setChecked(false);
                    checkbox_state_build.setChecked(false);
                    checkbox_state_completed.setChecked(false);
                    checkbox_state_finish.setChecked(false);
                    checkbox_state_termination.setChecked(false);
                    checkbox_state_stop.setChecked(false);
                }
            }
        });
        checkbox_state_build.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    state = HomeProjectFragment.STATE_build;
                    checkbox_state_all.setChecked(false);
                    checkbox_state_design.setChecked(false);
                    checkbox_state_ready.setChecked(false);
                    checkbox_state_completed.setChecked(false);
                    checkbox_state_finish.setChecked(false);
                    checkbox_state_termination.setChecked(false);
                    checkbox_state_stop.setChecked(false);
                }
            }
        });
        checkbox_state_completed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    state = HomeProjectFragment.STATE_completed;
                    checkbox_state_all.setChecked(false);
                    checkbox_state_design.setChecked(false);
                    checkbox_state_ready.setChecked(false);
                    checkbox_state_build.setChecked(false);
                    checkbox_state_finish.setChecked(false);
                    checkbox_state_termination.setChecked(false);
                    checkbox_state_stop.setChecked(false);
                }
            }
        });
        checkbox_state_finish.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    state = HomeProjectFragment.STATE_finish;
                    checkbox_state_all.setChecked(false);
                    checkbox_state_design.setChecked(false);
                    checkbox_state_ready.setChecked(false);
                    checkbox_state_build.setChecked(false);
                    checkbox_state_completed.setChecked(false);
                    checkbox_state_termination.setChecked(false);
                    checkbox_state_stop.setChecked(false);
                }
            }
        });
        checkbox_state_termination.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    state = HomeProjectFragment.STATE_termination;
                    checkbox_state_all.setChecked(false);
                    checkbox_state_design.setChecked(false);
                    checkbox_state_ready.setChecked(false);
                    checkbox_state_build.setChecked(false);
                    checkbox_state_completed.setChecked(false);
                    checkbox_state_finish.setChecked(false);
                    checkbox_state_stop.setChecked(false);
                }
            }
        });
        checkbox_state_stop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    state = HomeProjectFragment.STATE_stop;
                    checkbox_state_design.setChecked(false);
                    checkbox_state_ready.setChecked(false);
                    checkbox_state_build.setChecked(false);
                    checkbox_state_completed.setChecked(false);
                    checkbox_state_finish.setChecked(false);
                    checkbox_state_termination.setChecked(false);
                }
            }
        });


        CheckBox checkbox_all = (CheckBox)view.findViewById(R.id.checkbox_all);
        CheckBox checkbox_me = (CheckBox)view.findViewById(R.id.checkbox_me);
        CheckBox checkbox_other = (CheckBox)view.findViewById(R.id.checkbox_other);
        checkbox_all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    checkbox_me.setChecked(false);
                    checkbox_other.setChecked(false);
                }
            }
        });
        checkbox_me.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    checkbox_all.setChecked(false);
                    checkbox_other.setChecked(false);
                }
            }
        });
        checkbox_other.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    checkbox_all.setChecked(false);
                    checkbox_me.setChecked(false);
                }
            }
        });

        view.findViewById(R.id.tv_reset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawer_layout != null){
                    drawer_layout.closeDrawers();
                }

                checkbox_state_all.setChecked(true);
                checkbox_state_design.setChecked(false);
                checkbox_state_ready.setChecked(false);
                checkbox_state_build.setChecked(false);
                checkbox_state_completed.setChecked(false);
                checkbox_state_finish.setChecked(false);
                checkbox_state_termination.setChecked(false);
                checkbox_state_stop.setChecked(false);

                state = HomeProjectFragment.STATE_all;

                checkbox_all.setChecked(true);
                checkbox_me.setChecked(false);
                checkbox_other.setChecked(false);

                toRequest(ApiConstants.EventTags.project_all);
            }
        });
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Bundle bundle = new Bundle();
        bundle.putString("project_id", mBeanList.get(position).getProject_id() + "");
        bundle.putString("project_name", mBeanList.get(position).getName());
        bundle.putString("company_id", mBeanList.get(position).getCompany_code());
        bundle.putString("project_status", mBeanList.get(position).getStatus());
        bundle.putString("start_date", mBeanList.get(position).getStart_date());
        bundle.putString("end_date", mBeanList.get(position).getEnd_date());
        gotoActivity(ProjectDetailActivity.class, 0, bundle);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        ToastUtil.showToast("敬请期待");
        return false;
    }

    private void setViewData(List<ProjectListBean> beanList) {
        mBeanList.clear();
        if (StrUtils.isEmpty(state) || HomeProjectFragment.STATE_all.equals(state)) {
            mBeanList.addAll(beanList);
        } else {
            for (ProjectListBean b : beanList) {
                if (state.equals(b.getStatus())) {
                    mBeanList.add(b);
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
        if (eventTag == ApiConstants.EventTags.project_all) {
            map.put("type", "" + type);//类型，1：我负责的，2：我参与的，3：企业全部
            iCommonRequestPresenter.requestPost(eventTag, mActivity, ApiConstants.project_all, map);
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