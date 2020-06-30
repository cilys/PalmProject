package com.aopcloud.palmproject.ui.adapter.staff;

import android.support.annotation.Nullable;
import android.text.TextUtils;

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
 * @File : StaffListAdapter.java
 * @Date : 2020/4/23 16:42
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @VersionCode : V 1.0
 * @Describe ：
 */
public class StaffListAdapter extends BaseQuickAdapter<StaffListBean, BaseViewHolder> {


    public StaffListAdapter(int layoutResId, @Nullable List<StaffListBean> data) {
        super(layoutResId, data);


    }

    @Override
    protected void convert(BaseViewHolder helper, StaffListBean item) {
        helper.setText(R.id.tv_name, item.getName())
                .setText(R.id.tv_role, item.getRole())
                .setText(R.id.tv_tag, ""+getRole(item.getRoleTags()))
                .setVisible(R.id.tv_role, TextUtils.isEmpty(item.getRole()) ? false : true)
        .addOnClickListener(R.id.iv_send_sms)     .addOnClickListener(R.id.iv_call);
        ;
        CircleImageView imageView = helper.getView(R.id.iv_img);
        AppImageLoader.loadCircleImage(mContext
                , BuildConfig.BASE_URL + item.getAvatar()
                , imageView);

    }

    private String getRole(List<StaffListBean.RoleTagsBean> list) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i <list.size() ; i++) {
            stringBuffer.append(""+list.get(i).getName()).append(" ");
        }
        return ""+stringBuffer.toString();
    }
}
