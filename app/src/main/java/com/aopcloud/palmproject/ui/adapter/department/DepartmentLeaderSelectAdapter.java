package com.aopcloud.palmproject.ui.adapter.department;

import android.support.annotation.Nullable;

import com.aopcloud.palmproject.BuildConfig;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.loader.AppImageLoader;
import com.aopcloud.palmproject.ui.activity.department.bean.SelectDepartmentLeaderBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @PackageName : com.aopcloud.palmproject.ui.adapter.department
 * @File : DepartmentLeaderSelectAdapter.java
 * @Date : 2020/5/11 2020/5/11
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class DepartmentLeaderSelectAdapter extends BaseQuickAdapter<SelectDepartmentLeaderBean, BaseViewHolder> {
    public DepartmentLeaderSelectAdapter(int layoutResId, @Nullable List<SelectDepartmentLeaderBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SelectDepartmentLeaderBean item) {
        helper.setText(R.id.tv_name,item.getName())
                .setText(R.id.tv_tag,"")
                .setChecked(R.id.checkbox,item.isSelect());
        AppImageLoader.loadCircleImage(mContext
                , BuildConfig.BASE_URL+item.getAvatar()
                , helper.getView(R.id.iv_img));

    }
}
