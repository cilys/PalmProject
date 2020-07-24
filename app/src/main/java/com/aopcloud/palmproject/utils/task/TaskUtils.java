package com.aopcloud.palmproject.utils.task;

import com.aopcloud.palmproject.conf.TaskStatus;
import com.aopcloud.palmproject.ui.activity.project.bean.ProjectTaskBean;
import com.aopcloud.palmproject.ui.activity.project.bean.ProjectTaskDetailBean;
import com.aopcloud.palmproject.ui.fragment.home.HomeProjectFragment;
import com.aopcloud.palmproject.ui.fragment.home.HomeTaskFragment;
import com.aopcloud.palmproject.ui.fragment.project.DashboardFragment;
import com.cily.utils.base.StrUtils;
import com.cily.utils.base.time.TimeType;
import com.cily.utils.base.time.TimeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 任务相关计算的工具类
 */
public class TaskUtils {
    private final static String DATE_INIT = "1970-01-01";

    public static List<ProjectTaskBean> getTypeList(List<ProjectTaskBean> beanList, String status){
        List<ProjectTaskBean> result = new ArrayList<>();

        if (beanList == null){
            return result;
        }
        if (beanList.size() == 0){
            return result;
        }

        if (DashboardFragment.STATUS_ALL.equals(status)){
            result.addAll(beanList);
            return result;
        }

        for (ProjectTaskBean bean : beanList) {
//            String state = bean.getStart_date();
            String state = bean.getCalculateStatus();

            if (DashboardFragment.STATUS_UN_PLAN.equals(status)){
                //未安排，定义：没有开始时间、或者开始时间为1970-01-01
                if (StrUtils.isEmpty(state) || DATE_INIT.equals(state)){
                    result.add(bean);
                }
            } else if (DashboardFragment.STATUS_UN_START.equals(status)){
                //未开始
                if (TaskStatus.STATE_no_start.equals(state)) {
                    result.add(bean);
                }
            } else if (DashboardFragment.STATUS_IN_PROCESS.equals(status)) {
                //进行中，定义：进行中。如果当前时间已超过任务结束时间，则为超期状态
                if (TaskStatus.STATE_progress.equals(state) || TaskStatus.STATE_operation.equals(state)) {
                    result.add(bean);
                }
            } else if (DashboardFragment.STATUS_OUT_TIME.equals(status)) {
                if (state.contains("已超期") || state.contains("已逾期")){
                    result.add(bean);
                }
            } else if (DashboardFragment.STATUS_COMPLETE.equals(status)) {
                if (TaskStatus.STATE_complete.equals(state)) {
                    result.add(bean);
                }
            } else if (DashboardFragment.STATUS_PAUSE.equals(status)) {
                if (HomeProjectFragment.STATE_stop.equals(state)
                        || (!StrUtils.isEmpty(state) && state.contains("暂停"))) {
                    result.add(bean);
                }
            }
        }
        return result;
    }

    public static String getState(ProjectTaskBean bean) {
        if (bean == null) {
            return "";
        }

        return bean.getCalculateStatus();
    }

    public static String getTaskDetailState(ProjectTaskDetailBean bean){
        if (bean == null){
            return "";
        }
        return bean.getCalculateStatus();
    }

    /**
     * 首页、项目、统计任务数量
     * @param beanList
     * @param state
     * @return
     */
    public static List<ProjectTaskBean> getList(List<ProjectTaskBean> beanList, String state){
        List<ProjectTaskBean> result = new ArrayList<>();

        if (beanList == null){
            return result;
        }
        if (beanList.size() == 0){
            return result;
        }
        if (TaskStatus.STATE_all.equals(state)){
            result.addAll(beanList);

            return result;
        }

        for (ProjectTaskBean bean : beanList) {
            if (!StrUtils.isEmpty(bean.getCalculateStatus())){
                if (bean.getCalculateStatus().equals(state)){
                    result.add(bean);
                }
            }
        }
        return result;
    }
}