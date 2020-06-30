package com.aopcloud.palmproject.ui.adapter.project;

import android.support.annotation.Nullable;

import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.ui.activity.project.bean.ProjectChildDashboardBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @PackageName : com.aopcloud.palmproject.ui.adapter.project
 * @File : ProjectChildDashboardAdapter.java
 * @Date : 2020/5/17 2020/5/17
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class ProjectChildDashboardAdapter extends BaseQuickAdapter<ProjectChildDashboardBean.SubordinatesBean, BaseViewHolder> {
    public ProjectChildDashboardAdapter(int layoutResId, @Nullable List<ProjectChildDashboardBean.SubordinatesBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProjectChildDashboardBean.SubordinatesBean item) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date sDate = null;
        Date eDate = null;
        try {
            sDate= dateFormat.parse(item.getStart_date());
            eDate = dateFormat.parse(item.getEnd_date());
        } catch (ParseException e) {
            sDate = new Date();
            eDate = new Date();
        }

        SimpleDateFormat dateFormat1 = new SimpleDateFormat("MM-dd");
        long  betweenDays = ((eDate.getTime() - sDate.getTime()) / (1000 * 60 * 60 * 24));

        helper.setText(R.id.tv_name,item.getName())
                .setText(R.id.tv_leader_name,item.getLeader_name())
                .setText(R.id.tv_leader_name,item.getName())
                .setText(R.id.tv_time_tag,betweenDays>0?"| 剩余":"")
                .setText(R.id.tv_time,betweenDays>0?""+betweenDays:"")
                .setText(R.id.tv_progress,item.getProgress()+"%");
    }
}
