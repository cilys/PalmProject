package com.aopcloud.palmproject.ui.adapter.enterprise;

import android.support.annotation.Nullable;

import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.ui.activity.enterprise.bean.EnterpriseListBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @PackageName : com.aopcloud.palmproject.ui.adapter.enterprise
 * @File : SwitchEnterpriseAdapter.java
 * @Date : 2020/4/20 2020/4/20
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class SwitchEnterpriseAdapter extends BaseQuickAdapter<EnterpriseListBean, BaseViewHolder> {
    public SwitchEnterpriseAdapter(int layoutResId, @Nullable List<EnterpriseListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, EnterpriseListBean item) {
        helper.setText(R.id.tv_name,item.getName())
                .setVisible(R.id.iv_select,item.isSelect());

    }
}
