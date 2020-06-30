package com.aopcloud.palmproject.ui.adapter.project;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;

import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.ui.activity.project.bean.ProjectTeamListBean;
import com.aopcloud.palmproject.ui.adapter.base.CommonAdapter;
import com.aopcloud.palmproject.ui.adapter.base.ViewHolder;

import java.util.List;

/**
 * @PackageName : com.aopcloud.palmproject.ui.adapter.project
 * @File : SpinnerTeamAdapter.java
 * @Date : 2020/5/18 13:53
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @VersionCode : V 1.0
 * @Describe ：
 */
public class SpinnerTeamAdapter extends CommonAdapter<ProjectTeamListBean> {


    public SpinnerTeamAdapter(Context context, int layoutId, List mBeanList) {
        super(context, layoutId, mBeanList);
    }

    @Override
    public void convert(ViewHolder holder, ProjectTeamListBean o, int position) {
        holder.setText(R.id.tv_name, o.getTeam_name());
    }
}