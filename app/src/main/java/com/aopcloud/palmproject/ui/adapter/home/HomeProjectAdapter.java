package com.aopcloud.palmproject.ui.adapter.home;

import android.support.annotation.Nullable;

import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.model.LatLng;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.ui.activity.project.bean.ProjectListBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @PackageName : com.aopcloud.palmproject.ui.adapter.home
 * @File : HomeProjectAdapter.java
 * @Date : 2020/5/24 2020/5/24
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class HomeProjectAdapter extends BaseQuickAdapter<ProjectListBean, BaseViewHolder> {


    private double task_latitude;
    private double task_longitude;
    public HomeProjectAdapter(int layoutResId, @Nullable List<ProjectListBean> data) {
        super(layoutResId, data);
    }

    public void setTask_latitude(double task_latitude) {
        this.task_latitude = task_latitude;
    }

    public void setTask_longitude(double task_longitude) {
        this.task_longitude = task_longitude;
    }

    @Override
    protected void convert(BaseViewHolder helper, ProjectListBean item) {


        LatLng latLng1 = new LatLng(Double.valueOf(item.getLatitude()),Double.valueOf( item.getLongitue()));
        LatLng latLng2 = new LatLng(task_latitude, task_longitude);
        int distance = (int) AMapUtils.calculateLineDistance(latLng1, latLng2);

        helper.setText(R.id.tv_name, item.getName())
                .setText(R.id.tv_leader_name, item.getLeader_name())
                .setText(R.id.tv_time, item.getStart_date() + "-" + item.getEnd_date())
                .setText(R.id.tv_progress, item.getProgress() + "%")
                .setText(R.id.tv_state, "在建")
                .setText(R.id.tv_address, item.getAddress())
                .setText(R.id.tv_distance, distance>1000?"距你"+(distance/1000)+"km":"距你"+distance+"米")
        ;

    }


}
