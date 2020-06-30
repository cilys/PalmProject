package com.aopcloud.palmproject.ui.adapter.project;

import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.ui.activity.project.bean.ProjectListBean;
import com.aopcloud.palmproject.view.CircularProgressView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @PackageName : com.aopcloud.palmproject.ui.adapter.project
 * @File : ProjectListAdapter.java
 * @Date : 2020/5/4 2020/5/4
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class ProjectListAdapter extends BaseQuickAdapter<ProjectListBean, BaseViewHolder> {
    public ProjectListAdapter(int layoutResId, @Nullable List<ProjectListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProjectListBean item) {
        RecyclerView recyclerView = helper.getView(R.id.rv_list);
        helper.setText(R.id.tv_title, item.getName())
                .setText(R.id.tv_leader, item.getLeader_name() + "")
                .setText(R.id.tv_day, item.getStart_date() + "")
                .setText(R.id.tv_progress, item.getProgress() + "%")
                .setText(R.id.tv_expand, recyclerView.getVisibility() == View.VISIBLE ? "折叠↑" : "展开↓");

        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        helper.getView(R.id.tv_expand).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.setVisibility(recyclerView.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                helper.setText(R.id.tv_expand, recyclerView.getVisibility() == View.VISIBLE ? "折叠↑" : "展开↓");
            }
        });

    }
}
