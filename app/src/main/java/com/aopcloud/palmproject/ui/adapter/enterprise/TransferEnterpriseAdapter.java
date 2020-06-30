package com.aopcloud.palmproject.ui.adapter.enterprise;

import android.support.annotation.Nullable;

import com.aopcloud.palmproject.BuildConfig;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.loader.AppImageLoader;
import com.aopcloud.palmproject.ui.activity.enterprise.bean.EnterpriseManagerBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @PackageName : com.aopcloud.palmproject.ui.adapter.enterprise
 * @File : TransferEnterpriseAdapter.java
 * @Date : 2020/5/31 2020/5/31
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class TransferEnterpriseAdapter extends BaseQuickAdapter<EnterpriseManagerBean, BaseViewHolder> {
    public TransferEnterpriseAdapter(int layoutResId, @Nullable List<EnterpriseManagerBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, EnterpriseManagerBean item) {
        helper.setText(R.id.tv_name,item.getUser_name())
                .setText(R.id.tv_tag,"")
                .setChecked(R.id.checkbox,item.isSelect());
        AppImageLoader.loadCircleImage(mContext
                , BuildConfig.BASE_URL+item.getAvatar()
                , helper.getView(R.id.iv_img));

    }
}
