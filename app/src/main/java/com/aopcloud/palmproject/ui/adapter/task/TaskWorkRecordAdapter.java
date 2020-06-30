package com.aopcloud.palmproject.ui.adapter.task;

import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.aopcloud.base.util.ResourceUtil;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.ui.activity.task.bean.SalaryBean;
import com.aopcloud.palmproject.view.decoration.DividerItemDecoration;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @PackageName : com.aopcloud.palmproject.ui.adapter.task
 * @File : TaskWorkRecordAdapter.java
 * @Date : 2020/5/18 2020/5/18
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class TaskWorkRecordAdapter extends BaseQuickAdapter<SalaryBean.UserListBean, BaseViewHolder> {
    public TaskWorkRecordAdapter(int layoutResId, @Nullable List<SalaryBean.UserListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SalaryBean.UserListBean item) {


        RecyclerView recyclerView = helper.getView(R.id.rv_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        DividerItemDecoration itemDecoration = new DividerItemDecoration.Builder()
                .color(ResourceUtil.getColor(R.color.theme_background_f5))
                .height(2)
                .build();
        recyclerView.addItemDecoration(itemDecoration);
        TaskWorkRecordChildAdapter adapter = new TaskWorkRecordChildAdapter(R.layout.item_task_statistics_record_child,item.getStatistic());
        recyclerView.setAdapter(adapter);


        helper.getView(R.id.rv_list).setVisibility(item.isExpand()? View.VISIBLE:View.GONE);

        helper.setText(R.id.tv_no, "" + (helper.getAdapterPosition() + 1))
                .setText(R.id.tv_name, item.getUser_name())
                .setText(R.id.tv_tag,"")
                .setText(R.id.tv_real_name, "" + item.getType())
                .setText(R.id.tv_real_name,item.getUser_status() == 1 ? "已实名" : "未实名")
                .setTextColor(R.id.tv_real_name,item.getUser_status() == 1 ? ResourceUtil.getColor("#FF108CF7") : ResourceUtil.getColor("#FFFFB342"))
                .setText(R.id.tv_majors, item.getMajors())
                .setText(R.id.tv_price, "￥" + item.getTotal_salary())
                .addOnClickListener(R.id.tv_price);

    }
}
