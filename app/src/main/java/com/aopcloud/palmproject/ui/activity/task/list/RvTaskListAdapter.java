package com.aopcloud.palmproject.ui.activity.task.list;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aopcloud.base.util.ResourceUtil;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.ui.activity.project.bean.ProjectTaskBean;
import com.aopcloud.palmproject.utils.DateUtils;
import com.aopcloud.palmproject.utils.task.TaskUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class RvTaskListAdapter extends BaseQuickAdapter<ProjectTaskBean, BaseViewHolder> {
    public RvTaskListAdapter(int layoutResId, @Nullable List<ProjectTaskBean> data) {
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

        helper.setText(R.id.tv_title, item.getName())
                .setText(R.id.tv_count, "工作量：" + item.getWork_value() + "" + item.getWork_unit())
                .setText(R.id.tv_state, TaskUtils.getState(item))
                .setText(R.id.tv_manager, "发起:" + item.getLeader_name())
                .setText(R.id.tv_progress, item.getProgress() + "%")
                .setText(R.id.tv_project_time, "限期" + betweenDays + "天")
                .setText(R.id.tv_project_time_count, DateUtils.calculateBetweenDay(item.getEnd_date()))
                .setText(R.id.tv_time, "" + dateFormat1.format(sDate) + "-" + dateFormat1.format(eDate))
                .setTextColor(R.id.tv_project_time_count, item.getStatus_str().equals("已逾期") ? ResourceUtil.getColor("#FFF90C0C") : ResourceUtil.getColor("#FF3291F8"))
                .setTextColor(R.id.tv_state, getStateColor(item.getStatus_str()))
                .setVisible(R.id.tv_time, !item.getStatus_str().equals("未安排"))
                .setVisible(R.id.tv_project_time, !item.getStatus_str().equals("未安排"))
                .setVisible(R.id.tv_project_time_count, DateUtils.betweenDay(item.getEnd_date()) < 0)
                .setVisible(R.id.tv_progress, !item.getStatus_str().equals("未安排"))
        ;

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
