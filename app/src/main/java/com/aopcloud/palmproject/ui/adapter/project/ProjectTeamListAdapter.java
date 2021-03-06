package com.aopcloud.palmproject.ui.adapter.project;

import android.support.annotation.Nullable;

import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.ui.activity.project.bean.ProjectTeamListBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @PackageName : com.aopcloud.palmproject.ui.adapter.project
 * @File : ProjectTeamListAdapter.java
 * @Date : 2020/5/4 2020/5/4
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class ProjectTeamListAdapter extends BaseQuickAdapter<ProjectTeamListBean, BaseViewHolder> {
    public ProjectTeamListAdapter(int layoutResId, @Nullable List<ProjectTeamListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProjectTeamListBean item) {
        helper.setText(R.id.tv_name, item.getTeam_name())
                .setText(R.id.tv_type, "自营")
                .setText(R.id.tv_count, "("+item.getMember().size()+")")
                .setText(R.id.tv_project_type, item.getIndustry())
        ;

    }
}
