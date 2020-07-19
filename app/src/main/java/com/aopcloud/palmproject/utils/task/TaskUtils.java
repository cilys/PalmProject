package com.aopcloud.palmproject.utils.task;

import com.aopcloud.palmproject.ui.activity.project.bean.ProjectTaskBean;
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

        for (ProjectTaskBean bean : beanList) {
            /*if (DashboardFragment.STATUS_UN_PLAN.equals(status)){
                //未安排，定义：没有开始时间、或者开始时间为1970-01-01
                if (StrUtils.isEmpty(bean.getStart_date()) || DATE_INIT.equals(bean.getStart_date())){
                    result.add(bean);
                }
            } else if (DashboardFragment.STATUS_UN_START.equals(status)){
                //未开始，定义：任务开始时间在当前时间之后。不管任务是否已经真实完成了
                String startDate = bean.getStart_date();
                if (StrUtils.isEmpty(startDate) || DATE_INIT.equals(startDate)){

                }else {
                    long sd = TimeUtils.strToMil(startDate, TimeType.DAY_LINE, System.currentTimeMillis());
                    if (sd > System.currentTimeMillis()){
                        result.add(bean);
                    }
                }
            } else if (DashboardFragment.STATUS_IN_PROCESS.equals(status)) {
                //进行中，定义：进行中。如果当前时间已超过任务结束时间，则为超期状态
                if (HomeTaskFragment.STATE_progress.equals(bean.getStatus_str()) || HomeTaskFragment.STATE_operation.equals(bean.getStatus_str())) {
                    String endDate = bean.getEnd_date();
                    long ed = TimeUtils.strToMil(endDate, TimeType.DAY_LINE, System.currentTimeMillis());
                    if (ed < System.currentTimeMillis()){

                    }else {
                        result.add(bean);
                    }
                }
            } else if (DashboardFragment.STATUS_OUT_TIME.equals(status)) {
                if (HomeTaskFragment.STATE_progress.equals(bean.getStatus_str())) {
                    String endDate = bean.getEnd_date();
                    long ed = TimeUtils.strToMil(endDate, TimeType.DAY_LINE, System.currentTimeMillis());
                    if (ed < System.currentTimeMillis()){
                        result.add(bean);
                    }
                } else if (bean.getStatus_str().contains("已超期") || bean.getStatus_str().contains("已逾期")){
                    result.add(bean);
                }
            } else if (DashboardFragment.STATUS_COMPLETE.equals(status)) {
                if (HomeTaskFragment.STATE_complete.equals(bean.getStatus_str())) {
                    String startDate = bean.getStart_date();
                    if (StrUtils.isEmpty(startDate) || DATE_INIT.equals(startDate)){

                    }else {
                        long sd = TimeUtils.strToMil(startDate, TimeType.DAY_LINE, System.currentTimeMillis());
                        if (sd > System.currentTimeMillis()){

                        } else {
                            result.add(bean);
                        }
                    }
                }
            } else if (DashboardFragment.STATUS_PAUSE.equals(status)) {
                if (HomeProjectFragment.STATE_stop.equals(bean.getStatus_str())) {
                    result.add(bean);
                }
            }*/
            if (DashboardFragment.STATUS_UN_PLAN.equals(status)){
                //未安排，定义：没有开始时间、或者开始时间为1970-01-01
                if (StrUtils.isEmpty(bean.getStart_date()) || DATE_INIT.equals(bean.getStart_date())){
                    result.add(bean);
                }
            } else if (DashboardFragment.STATUS_UN_START.equals(status)){
                //未开始
                if (HomeTaskFragment.STATE_no_start.equals(bean.getStatus_str())) {
                    result.add(bean);
                }
            } else if (DashboardFragment.STATUS_IN_PROCESS.equals(status)) {
                //进行中，定义：进行中。如果当前时间已超过任务结束时间，则为超期状态
                if (HomeTaskFragment.STATE_progress.equals(bean.getStatus_str()) || HomeTaskFragment.STATE_operation.equals(bean.getStatus_str())) {
                    result.add(bean);
                }
            } else if (DashboardFragment.STATUS_OUT_TIME.equals(status)) {
                if (bean.getStatus_str().contains("已超期") || bean.getStatus_str().contains("已逾期")){
                    result.add(bean);
                }
            } else if (DashboardFragment.STATUS_COMPLETE.equals(status)) {
                if (HomeTaskFragment.STATE_complete.equals(bean.getStatus_str())) {
                    result.add(bean);
                }
            } else if (DashboardFragment.STATUS_PAUSE.equals(status)) {
                if (HomeProjectFragment.STATE_stop.equals(bean.getStatus_str())
                        || (!StrUtils.isEmpty(bean.getStatus_str()) && bean.getStatus_str().contains("暂停"))) {
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

        String startDate = bean.getStart_date();
        if (StrUtils.isEmpty(startDate) || DATE_INIT.equals(startDate)){
            return "未安排";
        } else {
            long sd = TimeUtils.strToMil(startDate, TimeType.DAY_LINE, System.currentTimeMillis());
            if (sd > System.currentTimeMillis()){
                return "未开始";
            }

            if (HomeTaskFragment.STATE_progress.equals(bean.getStatus_str())) {
                String endDate = bean.getEnd_date();
                long ed = TimeUtils.strToMil(endDate, TimeType.DAY_LINE, System.currentTimeMillis());
                if (ed < System.currentTimeMillis()){

                } else {
                    return "进行中";
                }
            }

            if (HomeTaskFragment.STATE_progress.equals(bean.getStatus_str())) {
                String endDate = bean.getEnd_date();
                long ed = TimeUtils.strToMil(endDate, TimeType.DAY_LINE, System.currentTimeMillis());
                if (ed < System.currentTimeMillis()){
                    return "已逾期";
                }
            } else if (bean.getStatus_str().contains("已超期") || bean.getStatus_str().contains("已逾期")){
                return "已超期";
            }

            if (HomeTaskFragment.STATE_complete.equals(bean.getStatus_str())) {
                if (StrUtils.isEmpty(startDate) || DATE_INIT.equals(startDate)){

                }else {
                    if (sd > System.currentTimeMillis()){

                    } else {
                        return "已完成";
                    }
                }
            }
        }

        return bean.getStatus_str();
    }

}