package com.aopcloud.palmproject.ui.adapter.task;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.ui.activity.task.bean.SalaryBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @PackageName : com.aopcloud.palmproject.ui.adapter.task
 * @File : TaskWorkRecordChildAdapter.java
 * @Date : 2020/6/14 2020/6/14
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class TaskWorkRecordChildAdapter extends BaseQuickAdapter<SalaryBean.UserListBean.StatisticBean, BaseViewHolder> {

    public TaskWorkRecordChildAdapter(int layoutResId, @Nullable List<SalaryBean.UserListBean.StatisticBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SalaryBean.UserListBean.StatisticBean item) {
        long s =0;
        long e=0;
        String time;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        if (!TextUtils.isEmpty(item.getWorktimes())&&item.getWorktimes().contains("-")) {
            String ss = item.getWorktimes().substring(0,item.getWorktimes().indexOf("-"));
            String ee = item.getWorktimes().substring(item.getWorktimes().lastIndexOf("-")+1,item.getWorktimes().length());
             s = Long.valueOf(ss) * 1000;
             e = Long.valueOf(ee) * 1000;
            time=   "工时(" + simpleDateFormat.format(new Date(s)) + "-" + simpleDateFormat.format(new Date(e)) + ")";
        }else {
            time=  "工时";
        }
        helper.setText(R.id.tv_hours_time, time)
                .setText(R.id.tv_hours, "" + item.getHours())
                .setText(R.id.tv_day, String.format("%.1f", (item.getHours() / 8)) + "")
                .setText(R.id.tv_salary, "￥" + item.getSalary() + "元");


    }
}
