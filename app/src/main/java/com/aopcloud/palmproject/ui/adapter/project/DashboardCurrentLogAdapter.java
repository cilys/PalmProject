package com.aopcloud.palmproject.ui.adapter.project;

import android.support.annotation.Nullable;

import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.ui.activity.project.bean.DashboardAttendanceBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @PackageName : com.aopcloud.palmproject.ui.adapter.project
 * @File : DashboardCurrentLogAdapter.java
 * @Date : 2020/5/3 2020/5/3
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class DashboardCurrentLogAdapter extends BaseQuickAdapter<DashboardAttendanceBean, BaseViewHolder> {
    public DashboardCurrentLogAdapter(int layoutResId, @Nullable List<DashboardAttendanceBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DashboardAttendanceBean item) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");

        Date date = new Date();
        date.setTime(item.getMake_time()*1000);

        helper.setText(R.id.tv_time_team, ""+dateFormat.format(date)+ " "+ item.getTask_name())
                .setText(R.id.tv_name, ""+item.getUser_name())
                .setText(R.id.tv_tag, "")
                .setText(R.id.tv_state, item.getType()==0?"入场":"出场")
        ;

    }
}
