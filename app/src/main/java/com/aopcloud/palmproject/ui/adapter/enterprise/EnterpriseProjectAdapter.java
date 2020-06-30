package com.aopcloud.palmproject.ui.adapter.enterprise;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.ui.activity.project.bean.ProjectListBean;
import com.aopcloud.palmproject.view.CircleBarView;
import com.aopcloud.palmproject.view.CircularProgressView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @PackageName : com.aopcloud.palmproject.ui.adapter.enterprise
 * @File : EnterpriseProjectAdapter.java
 * @Date : 2020/4/19 2020/4/19
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class EnterpriseProjectAdapter extends BaseQuickAdapter<ProjectListBean, BaseViewHolder> {
    public EnterpriseProjectAdapter(int layoutResId, @Nullable List<ProjectListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProjectListBean item) {

        String startTime = item.getStart_date().toString();
        String endTime = item.getEnd_date().toString();
        long betweenDays = 0;
        if (!TextUtils.isEmpty(startTime) && !TextUtils.isEmpty(endTime)) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date startDate = format.parse(startTime);
                Date endDate = format.parse(endTime);
                betweenDays = ((endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        String d;
        if (betweenDays < 0) {
            d = "逾期" +betweenDays+ "天";
        } else {
            d = "剩余" + betweenDays + "天";
        }

        CircularProgressView progressBar = helper.getView(R.id.progress_bar);
        progressBar.setProgress(item.getProgress());
        helper.setText(R.id.tv_title, item.getName())
                .setText(R.id.tv_progress, "" + item.getProgress() + "%")
                .setText(R.id.tv_manager_time, "负责 " + item.getLeader_name() + "| "+d);


    }
}
