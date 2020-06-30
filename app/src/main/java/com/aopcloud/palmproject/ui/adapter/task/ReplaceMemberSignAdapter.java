package com.aopcloud.palmproject.ui.adapter.task;

import android.support.annotation.Nullable;

import com.aopcloud.palmproject.BuildConfig;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.loader.AppImageLoader;
import com.aopcloud.palmproject.ui.activity.project.bean.DashboardAttendanceBean;
import com.aopcloud.palmproject.ui.activity.task.bean.ReplaceMemberSignBean;
import com.aopcloud.palmproject.view.CircleImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @PackageName : com.aopcloud.palmproject.ui.adapter.task
 * @File : ReplaceMemberSignAdapter.java
 * @Date : 2020/5/18 2020/5/18
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class ReplaceMemberSignAdapter extends BaseQuickAdapter<DashboardAttendanceBean, BaseViewHolder> {
    public ReplaceMemberSignAdapter(int layoutResId, @Nullable List<DashboardAttendanceBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DashboardAttendanceBean item) {

        helper.setChecked(R.id.checkbox, item.isSelect());

        CircleImageView imageView = helper.getView(R.id.iv_img);
        AppImageLoader.loadCircleImage(mContext
                , BuildConfig.BASE_URL+item.getUser_avatar()
                , imageView);

    }
}
