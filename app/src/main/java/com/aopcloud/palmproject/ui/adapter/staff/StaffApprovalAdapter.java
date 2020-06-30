package com.aopcloud.palmproject.ui.adapter.staff;

import android.support.annotation.Nullable;
import android.view.View;

import com.aopcloud.base.util.ResourceUtil;
import com.aopcloud.palmproject.BuildConfig;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.loader.AppImageLoader;
import com.aopcloud.palmproject.ui.activity.staff.bean.StaffListBean;
import com.aopcloud.palmproject.view.CircleImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @PackageName : com.aopcloud.palmproject.ui.adapter.staff
 * @File : StaffApprovalAdapter.java
 * @Date : 2020/4/23 10:21
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @VersionCode : V 1.0
 * @Describe ：
 */
public class StaffApprovalAdapter extends BaseQuickAdapter<StaffListBean, BaseViewHolder> {
    public StaffApprovalAdapter(int layoutResId, @Nullable List<StaffListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, StaffListBean item) {

        helper.setText(R.id.tv_name, item.getName())
                .setText(R.id.tv_mobile, ""+item.getTel())
                .setText(R.id.tv_enterprise, ""+item.getCompany_name())
                .setText(R.id.tv_reason, "" + item.getReason())
                .setText(R.id.tv_source, ""+item.getCome_from())
                .setText(R.id.tv_state, item.getStatus() == 2 ? "已拒绝" : "待审核")
                .setTextColor(R.id.tv_state, item.getStatus() == 2 ? ResourceUtil.getColor("#FFC60707") : ResourceUtil.getColor("#FFEAAF13"));
        helper.getView(R.id.ll_reason).setVisibility(item.getStatus() == 2 ? View.GONE : View.VISIBLE);
        CircleImageView imageView = helper.getView(R.id.iv_img);
        AppImageLoader.loadCircleImage(mContext
                , BuildConfig.BASE_URL + item.getAvatar()
                , imageView);

    }
}
