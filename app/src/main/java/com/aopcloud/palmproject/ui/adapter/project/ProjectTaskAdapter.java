package com.aopcloud.palmproject.ui.adapter.project;

import android.support.annotation.Nullable;

import com.aopcloud.base.util.ResourceUtil;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.ui.activity.project.bean.ProjectTaskBean;
import com.aopcloud.palmproject.utils.task.TaskUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @PackageName : com.aopcloud.palmproject.ui.fragment.project
 * @File : ProjectTaskAdapter.java
 * @Date : 2020/5/15 12:26
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @VersionCode : V 1.0
 * @Describe ：
 */
public class ProjectTaskAdapter extends BaseQuickAdapter<ProjectTaskBean, BaseViewHolder> {

    public ProjectTaskAdapter(int layoutResId, @Nullable List<ProjectTaskBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProjectTaskBean item) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date sDate = null;
        Date eDate = null;
        try {
            sDate = dateFormat.parse(item.getStart_date());
            eDate = dateFormat.parse(item.getEnd_date());
        } catch (ParseException e) {
            sDate = new Date();
            eDate = new Date();
        }
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("MM-dd");

        long betweenDays = ((eDate.getTime() - sDate.getTime()) / (1000 * 60 * 60 * 24));

        String state = TaskUtils.getState(item);

        helper.setText(R.id.tv_title, item.getName())
                .setText(R.id.tv_count, "工作量：" + item.getWork_value() + "" + item.getWork_unit())
                .setText(R.id.tv_state, state)
                .setText(R.id.tv_manager, "发起:" + item.getLeader_name())
                .setText(R.id.tv_progress, item.getProgress() + "%")
                .setText(R.id.tv_project_time, "限期" + betweenDays + "天")
                .setText(R.id.tv_project_time_count, "")
                .setText(R.id.tv_time, "" + dateFormat1.format(sDate) + "-" + dateFormat1.format(eDate))
                .setTextColor(R.id.tv_project_time_count, state.equals("已逾期") ? ResourceUtil.getColor("#FFF90C0C") : ResourceUtil.getColor("#FF3291F8"))
                .setTextColor(R.id.tv_state, getStateColor(state))
                .setVisible(R.id.tv_time, !state.equals("未安排"))
                .setVisible(R.id.tv_project_time, !state.equals("未安排"))
                .setVisible(R.id.tv_project_time_count, state.equals("已逾期") || state.equals("进行中") || state.equals("作业中"))
                .setVisible(R.id.tv_progress, !state.equals("未安排"));
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