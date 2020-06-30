package com.aopcloud.palmproject.ui.fragment.task;

import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.aopcloud.base.base.BaseFragment;
import com.aopcloud.base.log.Logcat;
import com.aopcloud.base.util.ToastUtil;
import com.aopcloud.base.util.ViewUtil;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.api.ApiConstants;
import com.aopcloud.palmproject.common.ResultBean;
import com.aopcloud.palmproject.listener.OnTaskFilterListener;
import com.aopcloud.palmproject.ui.activity.project.ProjectTaskDetailActivity;
import com.aopcloud.palmproject.ui.activity.project.bean.ProjectTaskBean;
import com.aopcloud.palmproject.ui.activity.project.bean.ProjectTeamListBean;
import com.aopcloud.palmproject.ui.activity.task.TaskDetailActivity;
import com.aopcloud.palmproject.ui.adapter.task.TaskManagerAdapter;
import com.aopcloud.palmproject.utils.LoginUserUtil;
import com.aopcloud.palmproject.view.BgDarkPopupWindow;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.aopcloud.palmproject.ui.fragment.task.TaskFragment.*;
import static com.aopcloud.palmproject.ui.fragment.task.TaskFragment.ViewHolder.*;

/**
 * @PackageName : com.aopcloud.palmproject.ui.fragment.task
 * @File : TaskFragment.java
 * @Date : 2020/5/7 2020/5/7
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class TaskFragment extends BaseFragment implements OnTaskFilterListener {
    @BindView(R.id.tv_time_out)
    TextView mTvTimeOut;
    @BindView(R.id.tv_current_count)
    TextView mTvCurrentCount;
    @BindView(R.id.tv_filter)
    TextView mTvFilter;
    @BindView(R.id.rv_list)
    RecyclerView mRvList;

    private TaskManagerAdapter mAdapter;
    private List<ProjectTaskBean> mBeanList = new ArrayList();
    private List<ProjectTaskBean> mAllBeanList = new ArrayList();
    private String type;


    public static TaskFragment getInstance(String type) {
        TaskFragment fragment = new TaskFragment();
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
        toRequest(ApiConstants.EventTags.task_all);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_task;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mAdapter = new TaskManagerAdapter(R.layout.item_project_work_sheet, mBeanList);
        mRvList.setLayoutManager(new LinearLayoutManager(mActivity));
        mRvList.setAdapter(mAdapter);

        mTvTimeOut.setText("超期任务" + 0);
        mTvCurrentCount.setText("当前任务" + 0);
    }

    private List<ProjectTaskBean> timeOutList = new ArrayList<>();
    private List<ProjectTaskBean> currentTaskList = new ArrayList<>();

    private void setViewData(List<ProjectTaskBean> beanList) {

        mAllBeanList.clear();
        mAllBeanList.addAll(beanList);
        mBeanList.clear();
        mBeanList.addAll(mAllBeanList);
        mAdapter.notifyDataSetChanged();

        timeOutList.clear();
        currentTaskList.clear();
        for (int i = 0; i < mAllBeanList.size(); i++) {
            if (mAllBeanList.get(i).getStatus_str().equals("已逾期") || mAllBeanList.get(i).getStatus_str().equals("已超期")) {
                timeOutList.add(mAllBeanList.get(i));
            } else if (mAllBeanList.get(i).getStatus_str().equals("进行中")) {
                currentTaskList.add(mAllBeanList.get(i));
            }
        }

        mTvTimeOut.setText("超期任务" + timeOutList.size());
        mTvCurrentCount.setText("当前任务" + currentTaskList.size());

    }


    public void setFilter(String k, List<String> state, List<String> level) {
        Logcat.d("" + k + "/" + state.toString() + "/" + level.toString());
        List list = new ArrayList();
        for (int i = 0; i < mAllBeanList.size(); i++) {
            for (int j = 0; j < state.size(); j++) {
                if (mAllBeanList.get(i).getStatus_str().contains(state.get(j))) {
                    list.add(mAllBeanList.get(i));
                }
            }
        }
        for (int i = 0; i < mAllBeanList.size(); i++) {
            for (int j = 0; j < level.size(); j++) {
                if (mAllBeanList.get(i).getLevel().contains(level.get(j))) {
                    list.add(mAllBeanList.get(i));
                }
            }
        }
        for (int i = 0; i < mAllBeanList.size(); i++) {
            if (!TextUtils.isEmpty(k) && mAllBeanList.get(i).getName().contains(k)) {
                list.add(mAllBeanList.get(i));
            }
        }
        mBeanList.clear();
        mBeanList.addAll(list);
        mAdapter.notifyDataSetChanged();
    }


    @Override
    protected void setListener() {
        super.setListener();
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (type.equals("1")) {
                    Bundle bundle = new Bundle();
                    bundle.putString("task_id", mBeanList.get(position).getTask_id() + "");
                    bundle.putString("project_id", mBeanList.get(position).getProject_id() + "");
                    bundle.putString("task_name", "" + mBeanList.get(position).getName());
                    gotoActivity(ProjectTaskDetailActivity.class, 0, bundle);
                } else if (type.equals("2") || type.equals("3")) {
                    Bundle bundle = new Bundle();
                    bundle.putString("task_id", mBeanList.get(position).getTask_id() + "");
                    bundle.putString("project_id", mBeanList.get(position).getProject_id() + "");
                    bundle.putString("task_name", "" + mBeanList.get(position).getName());
                    bundle.putString("team_id",mBeanList.get(position).getTeam_id() + "");
                    gotoActivity(TaskDetailActivity.class, 0, bundle);
                }
            }
        });
    }

    @OnClick({R.id.tv_time_out, R.id.tv_current_count, R.id.tv_filter})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_time_out:
                mBeanList.clear();
                mBeanList.addAll(timeOutList);
                mAdapter.notifyDataSetChanged();
                break;
            case R.id.tv_current_count:
                mBeanList.clear();
                mBeanList.addAll(currentTaskList);
                mAdapter.notifyDataSetChanged();
                break;
            case R.id.tv_filter:
                showFilter();
                break;
        }
    }


    @Override
    public void onReset() {
        mBeanList.clear();
        mBeanList.addAll(mAllBeanList);
        mAdapter.notifyDataSetChanged();

    }

    @Override
    public void onFilter(String search, List<String> state, List<String> level) {
        setFilter(search, state, level);
    }

    @Override
    public void toRequest(int eventTag) {
        super.toRequest(eventTag);
        Map map = new HashMap();
        map.put("token", "" + LoginUserUtil.getToken(mActivity));
        map.put("code", "" + LoginUserUtil.getCurrentEnterpriseNo(mActivity));
        if (eventTag == ApiConstants.EventTags.task_all) {
            map.put("type", "" + type);
            Logcat.i("------------" + eventTag + "/" + JSON.toJSONString(map));
            iCommonRequestPresenter.requestPost(eventTag, mActivity, ApiConstants.task_all, map);
        }
    }

    @Override
    public void getRequestData(int eventTag, String result) {
        super.getRequestData(eventTag, result);
        Logcat.i("------------" + eventTag + "/" + result);
        ResultBean bean = JSON.parseObject(result, ResultBean.class);
        if (bean != null && bean.getCode() == 0) {
            if (eventTag == ApiConstants.EventTags.task_all) {
                List<ProjectTaskBean> beanList = JSON.parseArray(bean.getData(), ProjectTaskBean.class);
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


    private BgDarkPopupWindow mPopupWindow;


    private void showFilter() {
        View mPopView = View.inflate(mActivity, R.layout.dialog_pop_task_filter, null);
        ViewHolder viewHolder = new ViewHolder(mPopView);
        viewHolder.setOnFilterListener(this);
        mPopupWindow = new BgDarkPopupWindow(mActivity, mPopView);
        viewHolder.setDarkPopupWindow(mPopupWindow);
        mPopupWindow.setHeight(ViewUtil.getViewHeight(mPopView));
        mPopupWindow.setFocusable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
        mPopupWindow.setDarkStyle(-1);
        mPopupWindow.setDarkColor(Color.parseColor("#a0000000"));
        mPopupWindow.resetDarkPosition();
        mPopupWindow.darkBelow(mTvFilter);
        mPopupWindow.showAsDropDown(mTvFilter, 0, 0);
    }

    public static class ViewHolder {
        @BindView(R.id.checkbox_no_started)
        CheckBox mCheckboxNoStarted;
        @BindView(R.id.checkbox_progress)
        CheckBox mCheckboxProgress;
        @BindView(R.id.checkbox_overtime)
        CheckBox mCheckboxOvertime;
        @BindView(R.id.checkbox_complete)
        CheckBox mCheckboxComplete;
        @BindView(R.id.checkbox_cancel)
        CheckBox mCheckboxCancel;
        @BindView(R.id.checkbox_pause)
        CheckBox mCheckboxPause;
        @BindView(R.id.checkbox_in)
        CheckBox mCheckboxIn;
        @BindView(R.id.checkbox_no_assign)
        CheckBox mCheckboxNoAssign;
        @BindView(R.id.checkbox_level_1)
        CheckBox mCheckboxLevel1;
        @BindView(R.id.checkbox_level_2)
        CheckBox mCheckboxLevel2;
        @BindView(R.id.checkbox_level_3)
        CheckBox mCheckboxLevel3;
        @BindView(R.id.et_search)
        EditText mEtSearch;
        @BindView(R.id.tv_reset)
        TextView mTvReset;
        @BindView(R.id.tv_sure)
        TextView mTvSure;

        OnTaskFilterListener mOnFilterListener;
        BgDarkPopupWindow mDarkPopupWindow;

        public void setOnFilterListener(OnTaskFilterListener onFilterListener) {
            mOnFilterListener = onFilterListener;
        }

        public void setDarkPopupWindow(BgDarkPopupWindow darkPopupWindow) {
            mDarkPopupWindow = darkPopupWindow;
        }

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        @OnClick({R.id.checkbox_no_started, R.id.checkbox_progress, R.id.checkbox_overtime, R.id.checkbox_complete,
                R.id.checkbox_cancel, R.id.checkbox_pause, R.id.checkbox_in, R.id.checkbox_no_assign,
                R.id.checkbox_level_1, R.id.checkbox_level_2, R.id.checkbox_level_3, R.id.tv_reset, R.id.tv_sure})
        public void onViewClicked(View view) {
            switch (view.getId()) {
                case R.id.checkbox_no_started:
                    break;
                case R.id.checkbox_progress:
                    break;
                case R.id.checkbox_overtime:
                    break;
                case R.id.checkbox_complete:
                    break;
                case R.id.checkbox_cancel:
                    break;
                case R.id.checkbox_pause:
                    break;
                case R.id.checkbox_in:
                    break;
                case R.id.checkbox_no_assign:
                    break;
                case R.id.checkbox_level_1:
                    break;
                case R.id.checkbox_level_2:
                    break;
                case R.id.checkbox_level_3:
                    break;
                case R.id.tv_reset:
                    if (mOnFilterListener != null) {
                        mOnFilterListener.onReset();
                    }
                    if (mDarkPopupWindow != null) {
                        mDarkPopupWindow.dismiss();
                    }
                    break;
                case R.id.tv_sure:
                    String s = mEtSearch.getText().toString();
                    if (mOnFilterListener != null) {
                        List state = new ArrayList();
                        List<String> level = new ArrayList();

                        if (mCheckboxNoStarted.isChecked()) {
                            state.add("未开始");
                        }
                        if (mCheckboxProgress.isChecked()) {
                            state.add("进行中");
                        }
                        if (mCheckboxOvertime.isChecked()) {
                            state.add("已超期");
                            state.add("已逾期");
                        }
                        if (mCheckboxComplete.isChecked()) {
                            state.add("已完成");
                        }
                        if (mCheckboxCancel.isChecked()) {
                            state.add("已取消");
                        }
                        if (mCheckboxPause.isChecked()) {
                            state.add("暂停中");
                        }
                        if (mCheckboxIn.isChecked()) {
                            state.add("作业中");
                        }
                        if (mCheckboxNoAssign.isChecked()) {
                            state.add("未安排");
                        }


                        if (mCheckboxLevel1.isChecked()) {
                            level.add("普通");
                        }
                        if (mCheckboxLevel2.isChecked()) {
                            level.add("重要");
                        }
                        if (mCheckboxLevel3.isChecked()) {
                            level.add("紧急");
                        }
                        mOnFilterListener.onFilter(TextUtils.isEmpty(s) ? "" : s, state, level);
                    }

                    if (mDarkPopupWindow != null) {
                        mDarkPopupWindow.dismiss();
                    }
                    break;
            }
        }

    }
}



