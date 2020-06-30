package com.aopcloud.palmproject.ui.adapter.enterprise;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @PackageName : com.aopcloud.palmproject.ui.adapter.enterprise
 * @File : EnterpriseTodoAdapter.java
 * @Date : 2020/4/19 2020/4/19
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class EnterpriseTodoAdapter extends BaseQuickAdapter<Object, BaseViewHolder> {
    public EnterpriseTodoAdapter(int layoutResId, @Nullable List<Object> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Object item) {

    }
}
