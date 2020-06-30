package com.aopcloud.palmproject.ui.adapter.task;

import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.ui.activity.task.bean.TaskChildBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @PackageName : com.aopcloud.palmproject.ui.adapter.task
 * @File : TaskChildManageAdapter.java
 * @Date : 2020/5/20 10:49
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @VersionCode : V 1.0
 * @Describe ：
 */
public class TaskChildManageAdapter extends BaseQuickAdapter<TaskChildBean.SubordinatesBean, BaseViewHolder> {
    public TaskChildManageAdapter(int layoutResId, @Nullable List<TaskChildBean.SubordinatesBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TaskChildBean.SubordinatesBean item) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd");

        String startTime = item.getStart_date();
        String endTime = item.getEnd_date();
        long betweenDays = 0;
        Date startDate = null;
        Date endDate;
        String sTime="";
        String eTime="";
        if (!TextUtils.isEmpty(startTime) && !TextUtils.isEmpty(endTime)) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd HH:mm");
            try {
                startDate = new Date();
                endDate = format.parse(endTime);



                sTime = dateFormat.format(format.parse(startTime));
                eTime = dateFormat.format(endDate);
                betweenDays = ((endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24));
            } catch (ParseException e) {
                e.printStackTrace();
                startDate = new Date();
                endDate = new Date();
            }
        }
        helper.setText(R.id.tv_no, "" + (helper.getAdapterPosition() + 1))
                .setText(R.id.tv_name, "[" + item.getLeader_name() + "]" + item.getName())
                .setText(R.id.tv_time, "" + simpleDateFormat.format(startDate))
                .setText(R.id.tv_create_time, ""+sTime)
                .setText(R.id.tv_end_time, ""+eTime)
                .setChecked(R.id.checkbox, item.isComplete())
                .addOnClickListener(R.id.tv_name);

        helper.getView(R.id.ll_time_layout).setVisibility(item.isDevelop() ? View.VISIBLE : View.GONE);

        TextView textView = helper.getView(R.id.tv_name);
        if (item.isComplete()) {
            //添加删除线  
            textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);


        }
    }
}
