package com.aopcloud.palmproject.ui.adapter.task;

import android.support.annotation.Nullable;

import com.aopcloud.base.base.BaseActivity;
import com.aopcloud.palmproject.BuildConfig;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.loader.AppImageLoader;
import com.aopcloud.palmproject.ui.activity.team.bean.TeamMemberBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @PackageName : com.aopcloud.palmproject.ui.adapter.task
 * @File : SelectTaskLeaderAdapter.java
 * @Date : 2020/6/27 2020/6/27
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class SelectTaskLeaderAdapter extends BaseQuickAdapter<TeamMemberBean, BaseViewHolder> {
    public SelectTaskLeaderAdapter(int layoutResId, @Nullable List<TeamMemberBean> data) {
        super(layoutResId, data);
    }
    @Override
    protected void convert(BaseViewHolder helper, TeamMemberBean item) {

        helper.setText(R.id.tv_name,item.getUser_name())
                .setText(R.id.tv_tag,"")
                .setChecked(R.id.checkbox,item.isSelect());
        AppImageLoader.loadCircleImage(mContext
                , BuildConfig.BASE_URL+item.getAvatar()
                , helper.getView(R.id.iv_img));

    }
}
