package com.aopcloud.palmproject.ui.adapter.staff;

import android.support.annotation.Nullable;

import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.ui.activity.staff.bean.ContactBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @PackageName : com.aopcloud.palmproject.ui.adapter.staff
 * @File : SelectMobileListAdapter.java
 * @Date : 2020/5/10 2020/5/10
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class SelectMobileListAdapter extends BaseQuickAdapter<ContactBean, BaseViewHolder> {
    public SelectMobileListAdapter(int layoutResId, @Nullable List<ContactBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ContactBean item) {
        helper.setText(R.id.tv_name,item.getName())
                .setText(R.id.tv_phone,item.getPhone())
        .setChecked(R.id.checkbox,item.isSelect());

    }
}
