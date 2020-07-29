package com.aopcloud.palmproject.ui.activity.task.list;

import android.support.annotation.Nullable;

import com.aopcloud.base.util.ResourceUtil;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.conf.TaskStatus;
import com.aopcloud.palmproject.ui.activity.project.bean.ProjectTaskBean;
import com.aopcloud.palmproject.utils.DateUtils;
import com.aopcloud.palmproject.utils.task.TaskUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cily.utils.base.time.TimeType;
import com.cily.utils.base.time.TimeUtils;

import java.util.List;

public class RvTaskListAdapter extends BaseQuickAdapter<ProjectTaskBean, BaseViewHolder> {
    public RvTaskListAdapter(int layoutResId, @Nullable List<ProjectTaskBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProjectTaskBean item) {
        String state = TaskUtils.getState(item);
        helper.setText(R.id.tv_title, item.getName())
                .setText(R.id.tv_count, "工作量：" + item.getWork_value() + "" + item.getWork_unit())
                .setText(R.id.tv_state, TaskStatus.STATE_complete.equals(state) ? "待验收" : state)
                .setText(R.id.tv_manager, "发起:" + item.getLeader_name())
                .setText(R.id.tv_progress, item.getProgress() + "%")
                .setText(R.id.tv_project_time, "限期" + DateUtils.betweenDay(item.getStart_date(), item.getEnd_date()) + "天")
                .setText(R.id.tv_project_time_count, DateUtils.calculateBetweenDay(item.getEnd_date()))
                .setText(R.id.tv_time, changeDate(item.getStart_date()) + " - " + changeDate(item.getEnd_date()))
                .setTextColor(R.id.tv_project_time_count, state.equals("已逾期") ? ResourceUtil.getColor("#FFF90C0C") : ResourceUtil.getColor("#FF3291F8"))
                .setTextColor(R.id.tv_state, getStateColor(state))
                .setVisible(R.id.tv_time, !state.equals("未安排"))
                .setVisible(R.id.tv_project_time, !state.equals("未安排"))
                .setVisible(R.id.tv_project_time_count, TaskStatus.STATE_expect.equals(state))
                .setVisible(R.id.tv_progress, !state.equals("未安排"))
        ;

    }

    private String changeDate(String day){
        long t = TimeUtils.strToMil(day, TimeType.DAY_LINE, System.currentTimeMillis());
        return TimeUtils.milToStr(t, "MM.dd");
    }

    private int getStateColor(String status) {
        int color = ResourceUtil.getColor("#FFF4A304");
        if (status.equals("未开始")) {
            color = ResourceUtil.getColor("#FFF4A304");
        } else if (status.equals("进行中")) {
            color = ResourceUtil.getColor("#FF3291F8");
        } else if (status.equals("作业中")) {
            color = ResourceUtil.getColor("#FFB90BB9");
        } else if (status.equals("进行中")) {
            color = ResourceUtil.getColor("#FFF4A304");
        } else if (status.equals("已逾期")) {
            color = ResourceUtil.getColor("#FFF90C0C");
        } else if (status.equals("已完成")) {
            color = ResourceUtil.getColor("#FF6F6D6D");
        }
        return color;
    }
}