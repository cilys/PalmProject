package com.aopcloud.palmproject.ui.adapter.project;

import android.support.annotation.Nullable;

import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.ui.activity.project.bean.ProjectMemberDetailBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @PackageName : com.aopcloud.palmproject.ui.adapter.project
 * @File : ProjectMemberProjectAdapter.java
 * @Date : 2020/5/14 15:34
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @VersionCode : V 1.0
 * @Describe ：
 */
public class ProjectMemberProjectAdapter  extends BaseQuickAdapter<ProjectMemberDetailBean.ProjectsBean, BaseViewHolder> {
    public ProjectMemberProjectAdapter(int layoutResId, @Nullable List<ProjectMemberDetailBean.ProjectsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProjectMemberDetailBean.ProjectsBean item) {
        helper.setText(R.id.tv_name,item.getProject_name()+"");

    }
}
