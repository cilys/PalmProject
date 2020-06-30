package com.aopcloud.palmproject.ui.adapter.project;

import android.support.annotation.Nullable;
import android.view.View;

import com.aopcloud.base.base.BaseActivity;
import com.aopcloud.base.util.ResourceUtil;
import com.aopcloud.base.widget.SwipeMenuLayout;
import com.aopcloud.palmproject.BuildConfig;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.loader.AppImageLoader;
import com.aopcloud.palmproject.ui.activity.project.bean.ProjectMemberBean;
import com.aopcloud.palmproject.view.CircleImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @PackageName : com.aopcloud.palmproject.ui.adapter.project
 * @File : ProjectMemberAdapter.java
 * @Date : 2020/5/13 2020/5/13
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class ProjectMemberAdapter extends BaseQuickAdapter<Object, BaseViewHolder> {
    private boolean leader;

    public ProjectMemberAdapter(int layoutResId, @Nullable List<Object> data) {
        super(layoutResId, data);
    }

    public void setLeader(boolean leader) {
        this.leader = leader;
    }

    @Override
    protected void convert(BaseViewHolder helper, Object item) {
        ProjectMemberBean.UsersBean usersBean = (ProjectMemberBean.UsersBean) item;
        helper.setText(R.id.tv_name, usersBean.getUser_name())
                .setText(R.id.tv_tag, ((ProjectMemberBean.UsersBean) item).getType() == 0 ? "管理员" : "施工员")
                .setText(R.id.tv_real_name, ((ProjectMemberBean.UsersBean) item).getUser_status() == 1 ? "已实名" : "未实名")
                .setTextColor(R.id.tv_real_name, ((ProjectMemberBean.UsersBean) item).getUser_status() == 1 ? ResourceUtil.getColor("#FF108CF7") : ResourceUtil.getColor("#FFFFB342"))
                .addOnClickListener(R.id.tv_del)
                .addOnClickListener(R.id.iv_send_sms)
                .addOnClickListener(R.id.iv_call)
                .addOnClickListener(R.id.item_content);
        CircleImageView imageView = helper.getView(R.id.iv_img);
        SwipeMenuLayout mSwipeMenuLayout = helper.getView(R.id.swipe_menu_layout);
        mSwipeMenuLayout.computeScroll();
        AppImageLoader.loadCircleImage(mContext
                , BuildConfig.BASE_URL + ((ProjectMemberBean.UsersBean) item).getUser_avatar()
                , imageView);

        helper.getView(R.id.item_content).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getOnItemClickListener().onItemClick(ProjectMemberAdapter.this, view, helper.getLayoutPosition());
            }
        });
    }

}
