package com.aopcloud.palmproject.ui.activity.map.adapter;

import android.support.annotation.Nullable;

import com.amap.api.services.core.PoiItem;
import com.aopcloud.palmproject.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @PackageName : com.aopcloud.palmproject.ui.activity.map.adapter
 * @File : SelectLocationConfirmAdapter.java
 * @Date : 2020/5/25 10:44
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @VersionCode : V 1.0
 * @Describe ：
 */
public class SelectLocationConfirmAdapter extends BaseQuickAdapter<PoiItem, BaseViewHolder> {

    private int selectPosition = -1;

    public SelectLocationConfirmAdapter(int layoutResId, @Nullable List<PoiItem> data) {
        super(layoutResId, data);
    }

    public void setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
    }

    public int getSelectPosition() {
        return selectPosition;
    }

    @Override
    protected void convert(BaseViewHolder helper, PoiItem item) {
        helper.setText(R.id.tv_title, item.getTitle())
                .setText(R.id.tv_address, item.getSnippet())
        .setVisible(R.id.iv_select,selectPosition==helper.getAdapterPosition());


    }
}
