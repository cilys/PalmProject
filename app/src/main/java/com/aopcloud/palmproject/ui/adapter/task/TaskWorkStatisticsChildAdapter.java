package com.aopcloud.palmproject.ui.adapter.task;

import android.support.annotation.Nullable;

import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.ui.activity.task.bean.SalaryBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.math.BigDecimal;
import java.util.List;

/**
 * @PackageName : com.aopcloud.palmproject.ui.adapter.task
 * @File : TaskWorkStatisticsChildAdapter.java
 * @Date : 2020/6/14 2020/6/14
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class TaskWorkStatisticsChildAdapter extends BaseQuickAdapter<SalaryBean.UserListBean.StatisticBean, BaseViewHolder> {
    private int type;

    public TaskWorkStatisticsChildAdapter(int layoutResId, @Nullable List<SalaryBean.UserListBean.StatisticBean> data, int type) {
        super(layoutResId, data);
        this.type = type;
    }

    @Override
    protected void convert(BaseViewHolder helper, SalaryBean.UserListBean.StatisticBean item) {

        String tag="";
        if (type==1){
            tag= "￥" + item.getSalary();
        }else if (type==2){
            tag= "" +String.format("%.1f", (item.getHours()/8))+"工日";
        }else {
            tag= "" + item.getHours()+"工时";
        }
        helper.setText(R.id.tv_day, "" + item.getDay())
                .setText(R.id.tv_tag, ""+tag);


    }
}
