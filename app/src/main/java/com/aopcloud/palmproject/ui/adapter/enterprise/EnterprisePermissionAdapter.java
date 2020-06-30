package com.aopcloud.palmproject.ui.adapter.enterprise;

import android.support.annotation.Nullable;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.bean.PermissionBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @PackageName : com.aopcloud.palmproject.ui.adapter.enterprise
 * @File : EnterprisePermissionAdapter.java
 * @Date : 2020/4/30 9:36
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @VersionCode : V 1.0
 * @Describe ：
 */
public class EnterprisePermissionAdapter extends BaseQuickAdapter<PermissionBean, BaseViewHolder> {
    public EnterprisePermissionAdapter(int layoutResId, @Nullable List<PermissionBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PermissionBean item) {
        helper.setText(R.id.tv_name,item.getName())
                .setImageResource(R.id.iv_check,item.isSelect()?R.mipmap.shop_icon_click_selected:R.mipmap.shop_icon_click_selected_n)
                .addOnClickListener(R.id.rl_item);
    }
}
