package com.aopcloud.palmproject.ui.adapter.home;

import android.support.annotation.Nullable;

import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.model.LatLng;
import com.aopcloud.base.log.Logcat;
import com.aopcloud.base.util.ResourceUtil;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.ui.activity.project.bean.ProjectTaskBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @PackageName : com.aopcloud.palmproject.ui.adapter.home
 * @File : HomeTaskAdapter.java
 * @Date : 2020/5/23 2020/5/23
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class HomeTaskAdapter extends BaseQuickAdapter<ProjectTaskBean, BaseViewHolder> {


    private double task_latitude;
    private double task_longitude;
    public HomeTaskAdapter(int layoutResId, @Nullable List<ProjectTaskBean> data) {
        super(layoutResId, data);
    }

    public void setTask_latitude(double task_latitude) {
        this.task_latitude = task_latitude;
    }

    public void setTask_longitude(double task_longitude) {
        this.task_longitude = task_longitude;
    }

    @Override
    protected void convert(BaseViewHolder helper, ProjectTaskBean item) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date current = new Date();
        Date eDate = null;
        try {
            eDate = dateFormat.parse(item.getEnd_date());
        } catch (ParseException e) {
            eDate = new Date();
        }

        long betweenDays = ((eDate.getTime() - current.getTime()) / (1000 * 60 * 60 * 24));

        Logcat.i("--------"+betweenDays);
        String days = "";

        if (betweenDays > 0) {
            days = "剩余" + betweenDays + "天";
        }else {
            days = "逾期" + (-betweenDays) + "天";
        }


        LatLng latLng1 = new LatLng(Double.valueOf(item.getLatitude()),Double.valueOf( item.getLongitue()));
        LatLng latLng2 = new LatLng(task_latitude, task_longitude);
        int distance = (int) AMapUtils.calculateLineDistance(latLng1, latLng2);

        helper.setText(R.id.tv_name, item.getName())
                .setText(R.id.tv_leader_name, item.getLeader_name())
                .setText(R.id.tv_time, item.getStart_date() + "-" + item.getEnd_date())
                .setText(R.id.tv_days, ""+days)
                .setText(R.id.tv_count_unit, item.getWork_value() + "/" + item.getWork_unit())
                .setText(R.id.tv_progress, item.getProgress() + "%")
                .setText(R.id.tv_state, item.getStatus_str())
                .setText(R.id.tv_address, item.getAddress())
                .setText(R.id.tv_distance, distance>1000?"距你"+(distance/1000)+"km":"距你"+distance+"米")
                .setTextColor(R.id.tv_days,item.getStatus_str().equals("已逾期")?ResourceUtil.getColor("#FFF90C0C"):ResourceUtil.getColor("#FF3291F8"))
                .setTextColor(R.id.tv_state, getStateColor(item.getStatus_str()))
                .setVisible(R.id.tv_time, !item.getStatus_str().equals("未安排"))
                .setVisible(R.id.tv_days, item.getStatus_str().equals("已逾期") || item.getStatus_str().equals("进行中") || item.getStatus_str().equals("作业中"))
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
