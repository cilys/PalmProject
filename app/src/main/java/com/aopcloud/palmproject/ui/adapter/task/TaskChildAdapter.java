package com.aopcloud.palmproject.ui.adapter.task;

import android.support.annotation.Nullable;

import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.ui.activity.task.bean.TaskChildBean;
import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @PackageName : com.aopcloud.palmproject.ui.adapter.task
 * @File : TaskChildAdapter.java
 * @Date : 2020/5/20 10:49
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @VersionCode : V 1.0
 * @Describe ：
 */
public class TaskChildAdapter extends BaseItemDraggableAdapter<TaskChildBean.SubordinatesBean, BaseViewHolder> {
    public TaskChildAdapter(int layoutResId, @Nullable List<TaskChildBean.SubordinatesBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TaskChildBean.SubordinatesBean item) {

        helper.setText(R.id.tv_name,"["+item.getLeader_name()+"]"+item.getName());
        helper.addOnClickListener(R.id.iv_del);

    }
}
