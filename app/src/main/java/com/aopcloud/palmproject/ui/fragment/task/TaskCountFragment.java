package com.aopcloud.palmproject.ui.fragment.task;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.aopcloud.base.base.BaseFragment;
import com.aopcloud.base.util.ToastUtil;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.api.ApiConstants;
import com.aopcloud.palmproject.common.ResultBean;
import com.aopcloud.palmproject.conf.TaskStatus;
import com.aopcloud.palmproject.ui.activity.project.bean.ProjectTaskBean;
import com.aopcloud.palmproject.ui.activity.task.list.TaskListAc;
import com.aopcloud.palmproject.ui.fragment.home.HomeTaskFragment;
import com.aopcloud.palmproject.utils.LoginUserUtil;
import com.aopcloud.palmproject.utils.task.TaskUtils;
import com.cily.utils.base.time.TimeType;
import com.cily.utils.base.time.TimeUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskCountFragment extends BaseFragment {
    private TextView mTvUndo;
    private TextView mTvDoing;
    private TextView mTvOutTime;
    private TextView mTvDone;

    private String type = "2";

    @Override
    protected int setLayoutId() {
        return R.layout.fg_task_count;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);

        Bundle bundle = getArguments();
        if (bundle != null){
            type = bundle.getString("type", type);
        }

        mTvUndo = (TextView)view.findViewById(R.id.tv_undo);
        mTvDoing = (TextView)view.findViewById(R.id.tv_doing);
        mTvOutTime = (TextView)view.findViewById(R.id.tv_outTime);
        mTvDone = (TextView)view.findViewById(R.id.tv_done);

        mTvUndo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b = new Bundle();
                b.putString("type", type);
                b.putString("state", HomeTaskFragment.STATE_UNDO);
                gotoActivity(TaskListAc.class, b);
            }
        });
        mTvDoing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b = new Bundle();
                b.putString("type", type);
                b.putString("state", HomeTaskFragment.STATE_DOING);
                gotoActivity(TaskListAc.class, b);
            }
        });
        mTvOutTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b = new Bundle();
                b.putString("type", type);
                b.putString("state", HomeTaskFragment.STATE_OUT_OF_TIME);
                gotoActivity(TaskListAc.class, b);
            }
        });
        mTvDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b = new Bundle();
                b.putString("type", type);
                b.putString("state", HomeTaskFragment.STATE_DONE);
                gotoActivity(TaskListAc.class, b);
            }
        });
        toRequest(ApiConstants.EventTags.task_all);
    }

    @Override
    protected void onVisible() {
        super.onVisible();

        if (LoginUserUtil.isLogin(mActivity)) {
            toRequest(ApiConstants.EventTags.task_all);
        }
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    public void toRequest(int eventTag) {
        super.toRequest(eventTag);
        Map map = new HashMap();
        map.put("token", LoginUserUtil.getToken(mActivity));
        map.put("code", LoginUserUtil.getCurrentEnterpriseNo(mActivity));
        if (eventTag == ApiConstants.EventTags.task_all) {
            map.put("type", type);
            iCommonRequestPresenter.requestPost(eventTag, mActivity, ApiConstants.task_all, map);
        }
    }

    @Override
    public void getRequestData(int eventTag, String result) {
        super.getRequestData(eventTag, result);
        ResultBean bean = JSON.parseObject(result, ResultBean.class);
        if (bean != null && bean.getCode() == 0) {
            if (eventTag == ApiConstants.EventTags.task_all) {
                List<ProjectTaskBean> beanList = JSON.parseArray(bean.getData(), ProjectTaskBean.class);
                parseProjectCount(beanList);
            }
        } else {
            ToastUtil.showToast(bean != null ? bean.getMsg() : "加载错误，请重试");
        }
    }

    @Override
    public void onRequestFailureException(int eventTag, String msg) {
        super.onRequestFailureException(eventTag, msg);
    }

    private void parseProjectCount(List<ProjectTaskBean> beanList){
        if (beanList == null || beanList.size() < 1){
            mTvUndo.setText("0");
            mTvDone.setText("0");
            mTvOutTime.setText("0");
            mTvDoing.setText("0");
        } else {
            int undo = 0;
            int done = 0;
            int outTime = 0;
            int doing = 0;
            for (ProjectTaskBean b : beanList){
                String status = TaskUtils.getState(b);
                if (TaskStatus.STATE_complete.equals(status)){
                    done ++;
                } else {
                    if (TaskStatus.STATE_no_start.equals(status)){
                        String endDate = b.getEnd_date();
                        String realEndDate = b.getEnd_date_real();

                        long ed = TimeUtils.strToMil(endDate, TimeType.DAY_LINE, 0L);
                        long cl = System.currentTimeMillis();
                        if (cl > ed){
                            outTime ++;
                        }else {
                            undo ++;
                        }

                    } else if (TaskStatus.STATE_progress.equals(status) || TaskStatus.STATE_operation.equals(status)) {
                        doing ++;
                    } else if (TaskStatus.STATE_expect.equals(status)) {
                        outTime ++;
                    }
                }
            }
            mTvUndo.setText(String.valueOf(undo));
            mTvDone.setText(String.valueOf(done));
            mTvOutTime.setText(String.valueOf(outTime));
            mTvDoing.setText(String.valueOf(doing));
        }
    }
}