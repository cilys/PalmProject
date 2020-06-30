package com.aopcloud.palmproject.ui.activity.map.adapter;

import android.support.annotation.Nullable;
import android.view.View;

import com.aopcloud.base.util.ResourceUtil;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.ui.activity.map.bean.LocationRangeBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @PackageName : com.aopcloud.palmproject.ui.activity.map.adapter
 * @File : SelectLocationRangeAdapter.java
 * @Date : 2020/4/18 2020/4/18
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class SelectLocationRangeAdapter extends BaseQuickAdapter<LocationRangeBean, BaseViewHolder> {


    public int selectPosition;

    public SelectLocationRangeAdapter(int layoutResId, @Nullable List<LocationRangeBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, LocationRangeBean item) {
        helper.getView(R.id.view_left_line).setVisibility(helper.getAdapterPosition() == 0 ? View.INVISIBLE : View.VISIBLE);
        helper.getView(R.id.view_right_line).setVisibility(helper.getAdapterPosition() == mData.size() - 1 ? View.INVISIBLE : View.VISIBLE);
        helper.setText(R.id.tv_range, item.getValue() + "")
                .setTextColor(R.id.tv_range, helper.getAdapterPosition() > selectPosition ? ResourceUtil.getColor("#FF888888") : ResourceUtil.getColor("#FF101010"))
                .setBackgroundRes(R.id.view_tag, helper.getAdapterPosition() >= selectPosition ? R.drawable.shape_map_range_n2 : R.drawable.shape_map_range_n);
        helper.getView(R.id.view_tag).setVisibility(!item.isSelect() ? View.VISIBLE : View.GONE);
        helper.getView(R.id.view_tag_select).setVisibility(item.isSelect() ? View.VISIBLE : View.GONE);

    }

    public void setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
    }

    public int getSelectPosition() {
        return selectPosition;
    }
}
