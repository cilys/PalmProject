package com.aopcloud.palmproject.ui.adapter.department;

import android.support.annotation.Nullable;

import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.ui.activity.department.bean.DepartmentListBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @PackageName : com.aopcloud.palmproject.ui.adapter.department
 * @File : DepartmentChildAdapter.java
 * @Date : 2020/4/23 2020/4/23
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class DepartmentChildAdapter extends BaseQuickAdapter<DepartmentListBean, BaseViewHolder> {
    public DepartmentChildAdapter(int layoutResId, @Nullable List<DepartmentListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DepartmentListBean item) {
        helper.setText(R.id.tv_title,""+item.getName());

    }
}
