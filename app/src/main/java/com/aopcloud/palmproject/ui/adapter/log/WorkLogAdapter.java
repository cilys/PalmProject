package com.aopcloud.palmproject.ui.adapter.log;

import android.content.Context;
import android.support.annotation.Nullable;

import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.ui.activity.log.bean.WorkLogListBean;
import com.aopcloud.palmproject.ui.adapter.base.CommonAdapter;
import com.aopcloud.palmproject.ui.adapter.base.ViewHolder;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @PackageName : com.aopcloud.palmproject.ui.adapter.log
 * @File : WorkLogAdapter.java
 * @Date : 2020/4/21 2020/4/21
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class WorkLogAdapter extends BaseQuickAdapter<WorkLogListBean, BaseViewHolder> {


    public WorkLogAdapter(int layoutResId, @Nullable List<WorkLogListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WorkLogListBean item) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 ");
        Date date = new Date();
        date.setTime(item.getMake_time() * 1000);
        String type = "";
        if (item.getType() == 1) {
            type="日报";
        } else if (item.getType() == 2) {
            type="周报";
        } else if (item.getType() == 3) {
            type="月报";
        }
        helper.setText(R.id.tv_name, "" + item.getName())
                .setText(R.id.tv_type, "" + type)
                .setText(R.id.tv_date, "" + dateFormat.format(date))
                .setText(R.id.tv_complete, "本周完成工作：完成任务" + item.getTasks_finished() + " 过期任务" + item.getTasks_overtime() + " 进行中任" + item.getTasks_doing())
                .setText(R.id.tv_summary, "总结与障碍：" + item.getSummary())
                .setText(R.id.tv_plan, "工作计划：" + item.getPlan())
        ;

    }
}
