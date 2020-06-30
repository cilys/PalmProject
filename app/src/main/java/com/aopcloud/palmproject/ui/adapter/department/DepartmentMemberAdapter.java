package com.aopcloud.palmproject.ui.adapter.department;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.aopcloud.palmproject.BuildConfig;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.loader.AppImageLoader;
import com.aopcloud.palmproject.ui.activity.department.bean.DepartmentMemberBean;
import com.aopcloud.palmproject.ui.activity.department.bean.SelectDepartmentLeaderBean;
import com.aopcloud.palmproject.ui.activity.staff.bean.StaffListBean;
import com.aopcloud.palmproject.view.CircleImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @PackageName : com.aopcloud.palmproject.ui.adapter.department
 * @File : DepartmentMemberAdapter.java
 * @Date : 2020/5/11 2020/5/11
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class DepartmentMemberAdapter extends BaseQuickAdapter<DepartmentMemberBean, BaseViewHolder> {
    public DepartmentMemberAdapter(int layoutResId, @Nullable List<DepartmentMemberBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DepartmentMemberBean item) {
        helper.setText(R.id.tv_name, item.getUser_name())
                .setText(R.id.tv_role, item.getRole())
                .setText(R.id.tv_tag, "" + getRole(item.getRoleTags()))
                .setVisible(R.id.tv_role, TextUtils.isEmpty(item.getRole()) ? false : true)
                .addOnClickListener(R.id.tv_del)
                .addOnClickListener(R.id.item_content)
                .addOnClickListener(R.id.iv_send_sms)
                .addOnClickListener(R.id.iv_call);
        CircleImageView imageView = helper.getView(R.id.iv_img);
        AppImageLoader.loadCircleImage(mContext
                , BuildConfig.BASE_URL + item.getAvatar()
                , imageView);

    }

    private String getRole(List<DepartmentMemberBean.RoleTagsBean> list) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < list.size(); i++) {
            stringBuffer.append("" + list.get(i).getName()).append(" ");
        }
        return "" + stringBuffer.toString();
    }
}