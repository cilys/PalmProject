package com.aopcloud.palmproject.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.aopcloud.base.base.BaseFragment;
import com.aopcloud.base.common.BaseEvent;
import com.aopcloud.base.log.Logcat;
import com.aopcloud.base.util.ResourceUtil;
import com.aopcloud.base.util.ToastUtil;
import com.aopcloud.base.util.ViewUtil;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.api.ApiConstants;
import com.aopcloud.palmproject.common.MassageEvent;
import com.aopcloud.palmproject.common.ResultBean;
import com.aopcloud.palmproject.ui.activity.department.EnterpriseDepartmentHomeActivity;
import com.aopcloud.palmproject.ui.activity.enterprise.EnterpriseHomeActivity;
import com.aopcloud.palmproject.ui.activity.enterprise.SwitchEnterpriseActivity;
import com.aopcloud.palmproject.ui.activity.enterprise.bean.EnterpriseInfoBean;
import com.aopcloud.palmproject.ui.activity.log.bean.WorkLogStatisticsBean;
import com.aopcloud.palmproject.ui.activity.mine.LoginActivity;
import com.aopcloud.palmproject.ui.activity.project.ProjectDetailActivity;
import com.aopcloud.palmproject.ui.activity.project.ProjectManagerActivity;
import com.aopcloud.palmproject.ui.activity.project.bean.ProjectListBean;
import com.aopcloud.palmproject.ui.activity.log.WorkLogManagerActivity;
import com.aopcloud.palmproject.ui.activity.project.bean.ProjectTaskBean;
import com.aopcloud.palmproject.ui.activity.staff.bean.StaffListBean;
import com.aopcloud.palmproject.ui.activity.task.TaskDetailActivity;
import com.aopcloud.palmproject.ui.activity.task.TaskManageActivity;
import com.aopcloud.palmproject.ui.adapter.enterprise.EnterpriseProjectAdapter;
import com.aopcloud.palmproject.ui.adapter.enterprise.EnterpriseTaskAdapter;
import com.aopcloud.palmproject.ui.adapter.enterprise.EnterpriseTodoAdapter;
import com.aopcloud.palmproject.ui.adapter.feagment.AppFragmentPagerAdapter;
import com.aopcloud.palmproject.ui.fragment.home.HomeProjectFragment;
import com.aopcloud.palmproject.ui.fragment.task.TaskCountFragment;
import com.aopcloud.palmproject.utils.LoginUserUtil;
import com.aopcloud.palmproject.view.decoration.DividerItemDecoration;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cily.utils.base.time.TimeType;
import com.cily.utils.base.time.TimeUtils;
import com.cilys.app.view.NoScrollViewPager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @PackageName : com.aopcloud.basic.ui.fragment
 * @File : NewsFragment.java
 * @Date : 2020/4/10 2020/4/10
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class ProjectFragment extends BaseFragment {

    @BindView(R.id.ll_info)
    LinearLayout mLlInfo;
    @BindView(R.id.ll_list)
    LinearLayout mLlList;
    @BindView(R.id.ll_work)
    LinearLayout mLlWork;

    @BindView(R.id.tv_project_left)
    TextView mTvProjectLeft;
    @BindView(R.id.tv_project_count)
    TextView mTvProjectCount;
    @BindView(R.id.tv_project_right)
    TextView mTvProjectRight;
    @BindView(R.id.tv_project_more)
    TextView mTvProjectMore;
    @BindView(R.id.rv_project)
    RecyclerView mRvProject;
    @BindView(R.id.tv_task_left)
    TextView mTvTaskLeft;
    @BindView(R.id.tv_task_count)
    TextView mTvTaskCount;
    @BindView(R.id.tv_task_right)
    TextView mTvTaskRight;
    @BindView(R.id.tv_task_more)
    TextView mTvTaskMore;
    @BindView(R.id.rv_task)
    RecyclerView mRvTask;
    @BindView(R.id.tv_enterprise_name)
    TextView mTvEnterpriseName;

//    @BindView(R.id.tv_undo)
//    TextView mTvUndo;       //未完成数量
//    @BindView(R.id.tv_done)
//    TextView mTvDone;       //已完成数量
//    @BindView(R.id.tv_outTime)
//    TextView mTvOutTime;    //已逾期数量

    @BindView(R.id.tv_company_count)
    TextView mTvCompanyCount;       //企业数量
    @BindView(R.id.tv_tell_count)
    TextView mTvTellCount;          //通讯录数量
    @BindView(R.id.tv_work_count)
    TextView mTvWorkCount;          //工作汇报数量
    @BindView(R.id.tv_apply_count)
    TextView mTvApplyCount;         //申请审批数量

    @BindView(R.id.view_splite_line_0)
    View mViewSpliteLine0;
    @BindView(R.id.view_splite_line_1)
    View mViewSpliteLine1;
    @BindView(R.id.view_splite_line_2)
    View mViewSpliteLine2;

//    private EnterpriseTodoAdapter mTodoAdapter;
    private EnterpriseProjectAdapter mProjectAdapter;
    private EnterpriseTaskAdapter mTaskAdapter;
    private List todoList = new ArrayList();
    private List<ProjectListBean> projectList = new ArrayList();
    private List<ProjectTaskBean> mTaskBeans = new ArrayList();

    @Override
    protected void initBaseLayout() {
        super.initBaseLayout();
    }


    @Override
    protected void initData() {
        super.initData();
        if (LoginUserUtil.isLogin(mActivity)) {
            toRequest(ApiConstants.EventTags.company_get);
            toRequest(ApiConstants.EventTags.project_all);
            toRequest(ApiConstants.EventTags.task_all);
            toRequest(ApiConstants.EventTags.company_usermange);
            toRequest(ApiConstants.EventTags.reportjob_statistics);
        }
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.reset().fitsSystemWindows(true)
                .statusBarDarkFont(false)
                .navigationBarColor("#111111")
                .statusBarColor(R.color.theme_color)
                .init();
    }

    @Override
    protected void onFirstVisible() {
        super.onFirstVisible();
        initImmersionBar();
    }

    @Override
    protected void onVisible() {
        super.onVisible();
        initImmersionBar();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_enterprise;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        DividerItemDecoration decoration= new DividerItemDecoration.Builder()
                .color(ResourceUtil.getColor(R.color.theme_background_f5))
                .width(ViewUtil.dp2px(mActivity,10))
                .height(ViewUtil.dp2px(mActivity,10))
                .build();
//        mTodoAdapter = new EnterpriseTodoAdapter(R.layout.item_enterprise_todo, todoList);
        mProjectAdapter = new EnterpriseProjectAdapter(R.layout.item_enterprise_project, projectList);
        mTaskAdapter = new EnterpriseTaskAdapter(R.layout.item_enterprise_task, mTaskBeans);

        mRvProject.addItemDecoration(decoration);
        mRvProject.setLayoutManager(new LinearLayoutManager(mActivity));
        mRvProject.setAdapter(mProjectAdapter);
//        mRvTask.addItemDecoration(decoration);
        mRvTask.setLayoutManager(new LinearLayoutManager(mActivity));
        mRvTask.setAdapter(mTaskAdapter);


        if (!LoginUserUtil.isLogin(mActivity)) {
            mTvEnterpriseName.setText("未登录");
        }
        if (LoginUserUtil.isLogin(mActivity) && TextUtils.isEmpty(LoginUserUtil.getCurrentEnterpriseNo(mActivity))) {
            mTvEnterpriseName.setText("请先选择或加入企业");
        }

        NoScrollViewPager noVp = (NoScrollViewPager)view.findViewById(R.id.noVp_count);
        List<Fragment> mFragments = new ArrayList<>();
        TaskCountFragment fg0 = new TaskCountFragment();
        Bundle b0 = new Bundle();
        b0.putString("type", "2");
        fg0.setArguments(b0);
        mFragments.add(fg0);

        TaskCountFragment fg1 = new TaskCountFragment();
        Bundle b1 = new Bundle();
        b1.putString("type", "3");
        fg1.setArguments(b1);
        mFragments.add(fg1);

        TaskCountFragment fg2 = new TaskCountFragment();
        Bundle b2 = new Bundle();
        b2.putString("type", "1");
        fg2.setArguments(b2);
        mFragments.add(fg2);

        LinearLayout ll_0 = (LinearLayout)view.findViewById(R.id.ll_0);
        ll_0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noVp.setCurrentItem(0);
            }
        });
        LinearLayout ll_1 = (LinearLayout)view.findViewById(R.id.ll_1);
        ll_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noVp.setCurrentItem(1);
            }
        });
        LinearLayout ll_2 = (LinearLayout)view.findViewById(R.id.ll_2);
        ll_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noVp.setCurrentItem(2);
            }
        });

        noVp.setAdapter(new AppFragmentPagerAdapter(getChildFragmentManager(), mFragments, null));
        noVp.setCurrentItem(0);
        noVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if (i == 0){
                    if (mViewSpliteLine0 != null){
                        mViewSpliteLine0.setVisibility(View.VISIBLE);
                    }
                    if (mViewSpliteLine1 != null){
                        mViewSpliteLine1.setVisibility(View.INVISIBLE);
                    }
                    if (mViewSpliteLine2 != null){
                        mViewSpliteLine2.setVisibility(View.INVISIBLE);
                    }
                } else if (i == 1){
                    if (mViewSpliteLine0 != null){
                        mViewSpliteLine0.setVisibility(View.INVISIBLE);
                    }
                    if (mViewSpliteLine1 != null){
                        mViewSpliteLine1.setVisibility(View.VISIBLE);
                    }
                    if (mViewSpliteLine2 != null){
                        mViewSpliteLine2.setVisibility(View.INVISIBLE);
                    }
                } else if (i == 2){
                    if (mViewSpliteLine0 != null){
                        mViewSpliteLine0.setVisibility(View.INVISIBLE);
                    }
                    if (mViewSpliteLine1 != null){
                        mViewSpliteLine1.setVisibility(View.INVISIBLE);
                    }
                    if (mViewSpliteLine2 != null){
                        mViewSpliteLine2.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private void setViewTaskData(List<ProjectTaskBean> beanList) {
        mTaskBeans.clear();
        if (beanList != null){
            if (beanList.size() > 3){
                mTaskBeans.addAll(beanList.subList(0, 3));
            }else {
                mTaskBeans.addAll(beanList);
            }
        }
//        mTaskBeans.addAll(beanList);
        mTaskAdapter.notifyDataSetChanged();
    }

    @Override
    protected void setListener() {
        super.setListener();
        mTaskAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("task_id", mTaskBeans.get(position).getTask_id() + "");
                bundle.putString("task_name", mTaskBeans.get(position).getName() + "");
                bundle.getString("project_id",mTaskBeans.get(position).getProject_id()+"");
                bundle.putString("team_id",mTaskBeans.get(position).getTeam_id() + "");
                gotoActivity(TaskDetailActivity.class, 0, bundle);
            }
        });
        mProjectAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("project_id", projectList.get(position).getProject_id() + "");
                bundle.putString("project_name", projectList.get(position).getName());
                bundle.putString("company_id", projectList.get(position).getCompany_code());
                gotoActivity(ProjectDetailActivity.class, 0, bundle);
            }
        });

    }

    private void setViewData(EnterpriseInfoBean enterpriseInfoBean) {
        mTvEnterpriseName.setText(enterpriseInfoBean.getName());
    }

    private void setViewData(List<ProjectListBean> beanList) {
        projectList.clear();
        if (beanList != null){
            if (beanList.size() > 3) {
                projectList.addAll(beanList.subList(0, 3));
            }else {
                projectList.addAll(beanList);
            }
        }
//        projectList.addAll(beanList);
        mProjectAdapter.notifyDataSetChanged();
    }

    @OnClick({R.id.ll_info, R.id.ll_list, R.id.ll_work, R.id.tv_project_more, R.id.tv_task_more, R.id.tv_enterprise_name})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_info:
                if (!LoginUserUtil.isLogin(mActivity)) {
                    gotoActivity(LoginActivity.class, 0);
                } else if (LoginUserUtil.isLogin(mActivity) && TextUtils.isEmpty(LoginUserUtil.getCurrentEnterpriseNo(mActivity))) {
                    ToastUtil.showToast("请先选择或加入企业");
                } else {
                    gotoActivity(EnterpriseHomeActivity.class, 0);
                }
                break;
            case R.id.ll_list:
                if (!LoginUserUtil.isLogin(mActivity)) {
                    gotoActivity(LoginActivity.class, 0);
                } else if (LoginUserUtil.isLogin(mActivity) && TextUtils.isEmpty(LoginUserUtil.getCurrentEnterpriseNo(mActivity))) {
                    ToastUtil.showToast("请先选择或加入企业");
                    return;
                } else {
                    gotoActivity(EnterpriseDepartmentHomeActivity.class, 0);
                }
                break;
            case R.id.ll_work:
                if (!LoginUserUtil.isLogin(mActivity)) {
                    gotoActivity(LoginActivity.class, 0);
                } else if (LoginUserUtil.isLogin(mActivity) && TextUtils.isEmpty(LoginUserUtil.getCurrentEnterpriseNo(mActivity))) {
                    ToastUtil.showToast("请先选择或加入企业");
                } else {
                    gotoActivity(WorkLogManagerActivity.class, 0);
                }
                break;
            case R.id.tv_project_more:
                if (!LoginUserUtil.isLogin(mActivity)) {
                    gotoActivity(LoginActivity.class, 0);
                    ;
                } else if (LoginUserUtil.isLogin(mActivity) && TextUtils.isEmpty(LoginUserUtil.getCurrentEnterpriseNo(mActivity))) {
                    ToastUtil.showToast("请先选择或加入企业");
                } else {
                    gotoActivity(ProjectManagerActivity.class, 0);
                }
                break;
            case R.id.tv_task_more:
                if (!LoginUserUtil.isLogin(mActivity)) {
                    gotoActivity(LoginActivity.class, 0);
                } else if (LoginUserUtil.isLogin(mActivity) && TextUtils.isEmpty(LoginUserUtil.getCurrentEnterpriseNo(mActivity))) {
                    ToastUtil.showToast("请先选择或加入企业");
                } else {
                    gotoActivity(TaskManageActivity.class, 0);
                }
                break;
            case R.id.tv_enterprise_name:
                gotoActivity(SwitchEnterpriseActivity.class, 0);
                break;
        }
    }

    @Override
    public void toRequest(int eventTag) {
        super.toRequest(eventTag);
        Map map = new HashMap();
        map.put("token", "" + LoginUserUtil.getToken(mActivity));
        map.put("code", "" + LoginUserUtil.getCurrentEnterpriseNo(mActivity));
        if (eventTag == ApiConstants.EventTags.company_get) {
            iCommonRequestPresenter.requestPost(eventTag, mActivity, ApiConstants.company_get, map);
        } else if (eventTag == ApiConstants.EventTags.project_all) {
            map.put("type", "1");//类型，1：我负责的，2：我参与的，3：企业全部
            iCommonRequestPresenter.requestPost(eventTag, mActivity, ApiConstants.project_all, map);
        } else if (eventTag == ApiConstants.EventTags.task_all) {
            map.put("type",""+1);
            iCommonRequestPresenter.requestPost(eventTag, mActivity, ApiConstants.task_all, map);
        } else if (eventTag == ApiConstants.EventTags.company_usermange) {
            map.put("status", "" + 1);
            iCommonRequestPresenter.requestPost(eventTag, mActivity, ApiConstants.company_usermange, map);
        } else if (eventTag == ApiConstants.EventTags.reportjob_statistics) {
            map.put("code", "" + LoginUserUtil.getCurrentEnterpriseNo(mActivity));
            map.put("role","1");
            iCommonRequestPresenter.requestPost(eventTag, mActivity, ApiConstants.reportjob_statistics, map);
        }
    }

    private void parseProjectCount(List<ProjectListBean> beanList){
//        if (beanList == null || beanList.size() < 1){
//            mTvUndo.setText("0");
//            mTvDone.setText("0");
//            mTvOutTime.setText("0");
//        } else {
//            int undo = 0;
//            int done = 0;
//            int outTime = 0;
//            for (ProjectListBean b : beanList){
//                String status = b.getStatus();
//                if (HomeProjectFragment.STATE_completed.equals(status)
//                    || HomeProjectFragment.STATE_finish.equals(status)){
//                    done ++;
//                }else {
//                    String end_data = b.getEnd_date();
//                    long endDate = TimeUtils.strToMil(end_data, TimeType.DAY_LINE, System.currentTimeMillis());
//                    if (System.currentTimeMillis() > endDate){
//                        outTime ++;
//                    }else {
//                        undo ++;
//                    }
//                }
//            }
//            mTvUndo.setText(String.valueOf(undo));
//            mTvDone.setText(String.valueOf(done));
//            mTvOutTime.setText(String.valueOf(outTime));
//        }
    }

    @Override
    public void getRequestData(int eventTag, String result) {
        super.getRequestData(eventTag, result);
        ResultBean bean = JSON.parseObject(result, ResultBean.class);
        if (bean != null && bean.getCode() == 0) {
            if (eventTag == ApiConstants.EventTags.company_get) {
                EnterpriseInfoBean enterpriseInfoBean = JSON.parseObject(bean.getData(), EnterpriseInfoBean.class);
                setViewData(enterpriseInfoBean);
            } else if (eventTag == ApiConstants.EventTags.project_all) {
                List<ProjectListBean> beanList = JSON.parseArray(bean.getData(), ProjectListBean.class);
                setViewData(beanList);
                parseProjectCount(beanList);
            } else if (eventTag == ApiConstants.EventTags.task_all) {
                List<ProjectTaskBean> beanList = JSON.parseArray(bean.getData(), ProjectTaskBean.class);
                setViewTaskData(beanList);
                // TODO: 2020/7/9 先解决其它问题
//                if (beanList != null && beanList.size() > 0){
//                    if (beanList.size() > 99){
//                        mTvApplyCount.setText("99+");
//                    }else {
//                        mTvApplyCount.setText(String.valueOf(beanList.size()));
//                    }
//                    mTvApplyCount.setVisibility(View.VISIBLE);
//                }else {
//                    mTvApplyCount.setVisibility(View.GONE);
//                }
            } else if (eventTag == ApiConstants.EventTags.company_usermange){
                List<StaffListBean> beanList = JSON.parseArray(bean.getData(), StaffListBean.class);
                // TODO: 2020/7/9 先解决其它问题
//                if (beanList != null && beanList.size() > 0){
//                    if (beanList.size() > 99){
//                        mTvTellCount.setText("99+");
//                    }else {
//                        mTvTellCount.setText(String.valueOf(beanList.size()));
//                    }
//                    mTvTellCount.setVisibility(View.VISIBLE);
//                }else {
//                    mTvTellCount.setVisibility(View.GONE);
//                }
            } else if (eventTag == ApiConstants.EventTags.reportjob_statistics) {
                WorkLogStatisticsBean statisticsBean = JSON.parseObject(bean.getData(), WorkLogStatisticsBean.class);
                // TODO: 2020/7/9 先解决其它问题

                //                if (statisticsBean != null){
//                    int total = statisticsBean.getTotal();
//                    if (total < 1){
//                        mTvWorkCount.setVisibility(View.GONE);
//                    } else {
//                        mTvWorkCount.setVisibility(View.VISIBLE);
//                        if (total > 99){
//                            mTvWorkCount.setText("99+");
//                        }else {
//                            mTvWorkCount.setText(String.valueOf(total));
//                        }
//                    }
//                }
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


    @Override
    public void onEvent(BaseEvent postResult) {
        super.onEvent(postResult);
        if (postResult.type.equals(BaseEvent.EVENT_LOGIN)) {
            toRequest(ApiConstants.EventTags.company_get);
        } else if (postResult.type.equals(BaseEvent.EVENT_LOGOUT)) {
        } else if (postResult.type.equals(MassageEvent.SWITCH_COMPANY)) {
            toRequest(ApiConstants.EventTags.company_get);
            toRequest(ApiConstants.EventTags.project_all);
            toRequest(ApiConstants.EventTags.task_all);
        }
    }
}