package com.aopcloud.palmproject.ui.adapter.team;

import android.support.annotation.Nullable;

import com.aopcloud.palmproject.BuildConfig;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.loader.AppImageLoader;
import com.aopcloud.palmproject.ui.activity.team.bean.TeamMemberBean;
import com.aopcloud.palmproject.view.CircleImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @PackageName : com.aopcloud.palmproject.ui.adapter.team
 * @File : TeamMemberAdapter.java
 * @Date : 2020/5/16 2020/5/16
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class TeamMemberAdapter extends BaseQuickAdapter<TeamMemberBean, BaseViewHolder> {
    public TeamMemberAdapter(int layoutResId, @Nullable List<TeamMemberBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TeamMemberBean item) {
        helper.setText(R.id.tv_name, item.getUser_name())
                .setText(R.id.tv_real_name, "已实名")
                .setText(R.id.tv_majors, item.getMajors())
                .setText(R.id.tv_type, item.getType() == 0 ? "普通成员" : "班长")
                .setText(R.id.tv_day_wages, "日工资：" + item.getSalary() + "元")
                .addOnClickListener(R.id.tv_edit)
                .addOnClickListener(R.id.tv_del)
                .addOnClickListener(R.id.item_content)
        ;
        CircleImageView imageView = helper.getView(R.id.iv_img);
        AppImageLoader.loadCircleImage(mContext
                , BuildConfig.BASE_URL + item.getAvatar()
                , imageView);

    }
}
