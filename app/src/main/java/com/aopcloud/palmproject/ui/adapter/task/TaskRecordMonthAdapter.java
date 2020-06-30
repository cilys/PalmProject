package com.aopcloud.palmproject.ui.adapter.task;

import android.support.annotation.Nullable;

import com.aopcloud.base.util.ResourceUtil;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.ui.fragment.task.TaskRecordFragment;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @PackageName : com.aopcloud.palmproject.ui.adapter.task
 * @File : TaskRecordMonthAdapter.java
 * @Date : 2020/5/19 2020/5/19
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class TaskRecordMonthAdapter extends BaseQuickAdapter<TaskRecordFragment.MonthBean, BaseViewHolder> {
    public TaskRecordMonthAdapter(int layoutResId, @Nullable List<TaskRecordFragment.MonthBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TaskRecordFragment.MonthBean item) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(item.getTimestamp());

        Calendar today = Calendar.getInstance();
        boolean isToDay = false;
        if (calendar.get(Calendar.YEAR) == today.get(Calendar.YEAR)
                && calendar.get(Calendar.MONTH) == today.get(Calendar.MONTH)
                && calendar.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH)) {
            isToDay = true;
        }
        if (item.isPlaceholder()) {
            helper.setText(R.id.tv_day, "")
                    .setText(R.id.tv_time, "")
                    .setVisible(R.id.view_tag, false);
        } else {
            helper.setText(R.id.tv_day, "" + calendar.get(Calendar.DATE))
                    .setTextColor(R.id.tv_day, !isToDay ? ResourceUtil.getColor("#FF101010") : ResourceUtil.getColor(R.color.orange))
                    .setText(R.id.tv_time, item.getType() == 2 ? getMin(item.getMinute()) : "")
                    .setBackgroundRes(R.id.view_tag, item.getType() == 1 ? R.drawable.shape_dot_theme : R.drawable.shape_dot_theme_r)
                    .setBackgroundRes(R.id.rl_item, item.getType() == 2 ? R.drawable.shape_task_recording : R.drawable.shape_task_recording_n)
                    .setVisible(R.id.view_tag, item.getType() != 0);
        }
    }

    private String getMin(long second) {
        long hours = second / 3600;            //转换小时
        second = second % 3600;                //剩余秒数
        long minutes = second / 60;            //转换分钟
        return "" + hours + ":" + minutes;
    }
}
