package com.aopcloud.palmproject.ui.fragment.project;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.aopcloud.base.base.BaseFragment;
import com.aopcloud.base.util.ToastUtil;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.api.ApiConstants;
import com.aopcloud.palmproject.common.ResultBean;
import com.aopcloud.palmproject.ui.activity.project.ProjectDetailActivity;
import com.aopcloud.palmproject.ui.activity.project.bean.DashboardAttendanceBean;
import com.aopcloud.palmproject.ui.activity.project.bean.ProjectChildDashboardBean;
import com.aopcloud.palmproject.ui.activity.project.bean.ProjectDetailBean;
import com.aopcloud.palmproject.ui.activity.project.bean.ProjectTaskBean;
import com.aopcloud.palmproject.ui.adapter.project.DashboardCurrentLogAdapter;
import com.aopcloud.palmproject.ui.adapter.project.ProjectChildDashboardAdapter;
import com.aopcloud.palmproject.utils.LoginUserUtil;
import com.aopcloud.palmproject.view.CircularProgressView;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
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
public class DashboardFragment extends BaseFragment {

    @BindView(R.id.tv_count)
    TextView mTvCount;
    @BindView(R.id.progress_bar)
    CircularProgressView mProgressBar;
    @BindView(R.id.tv_progress)
    TextView mTvProgress;
    @BindView(R.id.rl_progress_bar)
    RelativeLayout mRlProgressBar;
    @BindView(R.id.view_line)
    View mViewLine;
    @BindView(R.id.tv_no_plan)
    TextView mTvNoPlan;
    @BindView(R.id.tv_no_start)
    TextView mTvNoStart;
    @BindView(R.id.tv_in_progress)
    TextView mTvInProgress;
    @BindView(R.id.tv_time_out)
    TextView mTvTimeOut;
    @BindView(R.id.tv_complete)
    TextView mTvComplete;
    @BindView(R.id.rl_statistics)
    RelativeLayout mRlStatistics;
    @BindView(R.id.rl_drawer_menu)
    RelativeLayout mRlDrawerMenu;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.tv_before)
    TextView mTvBefore;
    @BindView(R.id.tv_current_into)
    TextView mTvCurrentInto;
    @BindView(R.id.tv_current_leave)
    TextView mTvCurrentLeave;
    @BindView(R.id.progress_current)
    CircularProgressView mProgressCurrent;
    @BindView(R.id.tv_progress_current)
    TextView mTvProgressCurrent;
    @BindView(R.id.rl_current_progress_bar)
    RelativeLayout mRlCurrentProgressBar;
    @BindView(R.id.view_c_line)
    View mViewCLine;
    @BindView(R.id.tv_current_more)
    TextView mTvCurrentMore;
    @BindView(R.id.rv_log)
    RecyclerView mRvLog;
    @BindView(R.id.et_search)
    EditText mEtSearch;
    @BindView(R.id.iv_clean)
    ImageView mIvClean;
    @BindView(R.id.checkbox_mine)
    CheckBox mCheckboxMine;
    @BindView(R.id.iv_close)
    ImageView mIvClose;
    @BindView(R.id.checkbox_all)
    CheckBox mCheckboxAll;
    @BindView(R.id.tv_sure)
    TextView mTvSure;
    @BindView(R.id.rv_list)
    RecyclerView mRvList;
    @BindView(R.id.tv_cancel)
    TextView mTvCancel;
    @BindView(R.id.tv_days)
    TextView mTvDays;
    @BindView(R.id.tv_after)
    TextView mTvAfter;
    Unbinder unbinder;

    private long end_time;
    private long start_time;

    private DashboardCurrentLogAdapter mLogAdapter;
    private ProjectChildDashboardAdapter mDashboardTaskAdapter;

    private List<DashboardAttendanceBean> mLogList = new ArrayList();
    private List<ProjectChildDashboardBean.SubordinatesBean> mBeanList = new ArrayList();

    public static DashboardFragment getInstance(String project_id) {
        DashboardFragment fragment = new DashboardFragment();
        Bundle bundle = new Bundle();
        bundle.putString("project_id", "" + project_id);
        fragment.setArguments(bundle);
        return fragment;
    }

    private String project_id;
private   Calendar calendar;
    @Override
    protected void initData() {
        super.initData();
        Bundle bundle = getArguments();
        if (bundle != null) {
            project_id = bundle.getString("project_id");
        }

        calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd");
        String today = isToday(calendar.getTime())?"(今天)":"";
        mTvDays.setText(dateFormat.format(calendar.getTime())+today);

        toRequest(ApiConstants.EventTags.project_get);
        toRequest(ApiConstants.EventTags.project_projects);
        toRequest(ApiConstants.EventTags.attendance_project);
        toRequest(ApiConstants.EventTags.project_tasks);


    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_project_dashboard;
    }


    @Override
    protected void initView(View view) {
        super.initView(view);

        mTvCurrentInto.setText("0");
        mTvCurrentLeave.setText("0");
        mLogAdapter = new DashboardCurrentLogAdapter(R.layout.item_dashboard_log, mLogList);
        mRvLog.setLayoutManager(new LinearLayoutManager(mActivity));
        mRvLog.setAdapter(mLogAdapter);


        mDashboardTaskAdapter = new ProjectChildDashboardAdapter(R.layout.item_dashboard_task, mBeanList);
        mRvList.setLayoutManager(new LinearLayoutManager(mActivity));
        mRvList.setAdapter(mDashboardTaskAdapter);
        mRlDrawerMenu.setVisibility(mBeanList.size() > 0 ? View.VISIBLE : View.GONE);

    }

    private void setViewTaskData(List<ProjectTaskBean> beanList) {
        List list2 = new ArrayList();
        List list3 = new ArrayList();
        List list4 = new ArrayList();
        List list5 = new ArrayList();
        List list6 = new ArrayList();
        for (int i = 0; i < beanList.size(); i++) {
            if (beanList.get(i).getStatus_str().contains("未安排")) {
                list2.add(beanList.get(i));
            } else if (beanList.get(i).getStatus_str().contains("未开始")) {
                list3.add(beanList.get(i));
            } else if (beanList.get(i).getStatus_str().contains("进行中")) {
                list4.add(beanList.get(i));
            } else if (beanList.get(i).getStatus_str().contains("已超期") || beanList.get(i).getStatus_str().contains("已逾期")) {
                list5.add(beanList.get(i));
            } else if (beanList.get(i).getStatus_str().contains("已完成")) {
                list6.add(beanList.get(i));
            }

        }


        mTvCount.setText("" + beanList.size());
        mTvNoPlan.setText("" + list2.size());
        mTvNoStart.setText("" + list3.size());
        mTvInProgress.setText("" + list4.size());
        mTvTimeOut.setText("" + list5.size());
        mTvComplete.setText("" + list6.size());
    }

    private void setViewData(ProjectDetailBean detailBean) {
        mTvProgress.setText(detailBean.getProgress() + "%");
        mProgressBar.setProgress(detailBean.getProgress());


        mTvCount.setText("0");
        mTvNoPlan.setText("0");
        mTvNoStart.setText("0");
        mTvInProgress.setText("0");
        mTvTimeOut.setText("0");
        mTvComplete.setText("0");

        mProgressCurrent.setProgress(detailBean.getProgress());
        mTvCurrentLeave.setText("0");
        mTvCurrentInto.setText("0");


    }

    @Override
    protected void setListener() {
        super.setListener();
        mDashboardTaskAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("project_id", mBeanList.get(position).getProject_id() + "");
                bundle.putString("project_name", mBeanList.get(position).getName());
                bundle.putString("company_id", mBeanList.get(position).getCompany_code());
                bundle.putString("project_status", mBeanList.get(position).getStatus());
                gotoActivity(ProjectDetailActivity.class, bundle, 0);
            }
        });
    }

    @OnClick({R.id.tv_before, R.id.tv_current_more, R.id.rl_drawer_menu, R.id.iv_clean, R.id.iv_close,
            R.id.tv_sure, R.id.tv_days, R.id.tv_after})
    public void onViewClicked(View view) {
        Bundle bundle;
        switch (view.getId()) {
            case R.id.iv_clean:
                break;
            case R.id.iv_close:
                mDrawerLayout.closeDrawers();
                break;
            case R.id.tv_sure:
                toRequest(0);
                mDrawerLayout.closeDrawers();
                break;
            case R.id.tv_current_more:

                break;
            case R.id.rl_drawer_menu:
                mDrawerLayout.openDrawer(Gravity.START);
                break;
            case R.id.tv_before:
                calendar.add(Calendar.DATE,-1);
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd");
                String today = isToday(calendar.getTime())?"(今天)":"";
                mTvDays.setText(dateFormat.format(calendar.getTime())+today);
                toRequest(ApiConstants.EventTags.attendance_project);
                break;
            case R.id.tv_after:
                if (isToday(calendar.getTime())){
                    ToastUtil.showToast("时间未到");
                    return;
                }
                calendar.add(Calendar.DATE,+1);
                SimpleDateFormat dateFormats = new SimpleDateFormat("MM/dd");
                String today2 = isToday(calendar.getTime())?"(今天)":"";
                mTvDays.setText(dateFormats.format(calendar.getTime())+today2);
                toRequest(ApiConstants.EventTags.attendance_project);
                break;
        }
    }

    public static boolean isToday(Date date) {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(date);
        int year1 = c1.get(Calendar.YEAR);
        int month1 = c1.get(Calendar.MONTH)+1;
        int day1 = c1.get(Calendar.DAY_OF_MONTH);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(new Date());
        int year2 = c2.get(Calendar.YEAR);
        int month2 = c2.get(Calendar.MONTH)+1;
        int day2 = c2.get(Calendar.DAY_OF_MONTH);
        if(year1 == year2 && month1 == month2 && day1 == day2){
            return true;
        }
        return false;
    }
    @Override
    public void toRequest(int eventTag) {
        super.toRequest(eventTag);
        Map map = new HashMap();
        map.put("token", "" + LoginUserUtil.getToken(mActivity));
        map.put("code", "" + LoginUserUtil.getCurrentEnterpriseNo(mActivity));
        map.put("project_id", "" + project_id);//项目名称
        if (eventTag == ApiConstants.EventTags.project_get) {
            iCommonRequestPresenter.requestPost(eventTag, mActivity, ApiConstants.project_get, map);
        } else if (eventTag == ApiConstants.EventTags.project_projects) {
            iCommonRequestPresenter.requestPost(eventTag, mActivity, ApiConstants.project_projects, map);
        } else if (eventTag == ApiConstants.EventTags.attendance_project) {
            map.put("start_time", "" + getStartTime(calendar));
            map.put("end_time", "" + getEndTime(calendar));
            map.put("page", "" + 1);
            map.put("page_size", "" + 1000);
            iCommonRequestPresenter.requestPost(eventTag, mActivity, ApiConstants.attendance_project, map);
        } else if (eventTag == ApiConstants.EventTags.project_tasks) {
            iCommonRequestPresenter.requestPost(eventTag, mActivity, ApiConstants.project_tasks, map);
        }
    }

    @Override
    public void getRequestData(int eventTag, String result) {
        super.getRequestData(eventTag, result);
        ResultBean bean = JSON.parseObject(result, ResultBean.class);
        if (bean != null && bean.getCode() == 0) {
            if (eventTag == ApiConstants.EventTags.project_get) {
                ProjectDetailBean detailBean = JSON.parseObject(bean.getData(), ProjectDetailBean.class);
                setViewData(detailBean);
            } else if (eventTag == ApiConstants.EventTags.project_projects) {
                ProjectChildDashboardBean childDashboardBean = JSON.parseObject(bean.getData(), ProjectChildDashboardBean.class);
                mBeanList.clear();
                mBeanList.addAll(childDashboardBean.getSubordinates());
                mDashboardTaskAdapter.notifyDataSetChanged();
                mRlDrawerMenu.setVisibility(mBeanList.size() > 0 ? View.VISIBLE : View.GONE);
            } else if (eventTag == ApiConstants.EventTags.attendance_project) {
                List<DashboardAttendanceBean> beanList = JSON.parseArray(bean.getData(), DashboardAttendanceBean.class);
                setAttendanceView(beanList);
            } else if (eventTag == ApiConstants.EventTags.project_tasks) {
                List<ProjectTaskBean> beanList = JSON.parseArray(bean.getData(), ProjectTaskBean.class);
                setViewTaskData(beanList);
            }
        } else {
            ToastUtil.showToast(bean != null ? bean.getMsg() : "加载错误，请重试");
        }

    }

    private void setAttendanceView(List<DashboardAttendanceBean> beanList) {

        List leaveList = new ArrayList();
        List intoList = new ArrayList();
        for (int i = 0; i < beanList.size(); i++) {

            if (beanList.get(i).getType() == 0) {
                intoList.add(beanList.get(i));
            } else {
                leaveList.add(beanList.get(i));
            }
        }

        mTvProgressCurrent.setText(""+intoList.size());
        mTvCurrentInto.setText("" + intoList.size());
        mTvCurrentLeave.setText("" + leaveList.size());
        mLogList.clear();
        mLogList.addAll(beanList);
        mLogAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRequestFailureException(int eventTag, String msg) {
        super.onRequestFailureException(eventTag, msg);
    }

    public static Long getStartTime(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis() / 1000;
    }

    public static Long getEndTime(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTimeInMillis() / 1000;
    }

}
