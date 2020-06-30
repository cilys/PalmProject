package com.aopcloud.palmproject.ui.adapter.enterprise;

import android.support.annotation.Nullable;

import com.aopcloud.base.util.ResourceUtil;
import com.aopcloud.palmproject.BuildConfig;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.loader.AppImageLoader;
import com.aopcloud.palmproject.ui.activity.enterprise.bean.EnterpriseManagerBean;
import com.aopcloud.palmproject.view.CircleImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @PackageName : com.aopcloud.palmproject.ui.adapter.enterprise
 * @File : EnterpriseManagerAdapter.java
 * @Date : 2020/4/21 16:38
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @VersionCode : V 1.0
 * @Describe ：
 */
public class EnterpriseManagerAdapter extends BaseQuickAdapter<EnterpriseManagerBean, BaseViewHolder> {


    public EnterpriseManagerAdapter(int layoutResId, @Nullable List<EnterpriseManagerBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, EnterpriseManagerBean item) {
        CircleImageView imageView = helper.getView(R.id.iv_img);
        AppImageLoader.loadCircleImage(mContext, BuildConfig.BASE_URL + item.getAvatar(), imageView);
        helper.setText(R.id.tv_name, item.getUser_name())
                .setTextColor(R.id.tv_type, item.getRights().equals("all") ? ResourceUtil.getColor("#E26D43") : ResourceUtil.getColor("#108CF7"))
                .setBackgroundRes(R.id.tv_type, item.getRights().equals("all") ? R.drawable.shape_enterprise_manager_bg_1 : R.drawable.shape_enterprise_manager_bg_2)
                .setText(R.id.tv_type, item.getType()).setVisible(R.id.iv_more, helper.getAdapterPosition() != 0);

    }

}
