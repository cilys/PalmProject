package com.aopcloud.palmproject.ui.activity.map.adapter;

import android.support.annotation.Nullable;

import com.amap.api.services.core.PoiItem;
import com.aopcloud.palmproject.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @PackageName : com.aopcloud.palmproject.ui.activity.map.adapter
 * @File : SelectLocationSearchAdapter.java
 * @Date : 2020/4/18 2020/4/18
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class SelectLocationSearchAdapter  extends BaseQuickAdapter<PoiItem, BaseViewHolder> {
    public SelectLocationSearchAdapter(int layoutResId, @Nullable List<PoiItem> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PoiItem item) {
        helper.setText(R.id.tv_title,item.getTitle())
                .setText(R.id.tv_address,item.getSnippet());

    }
}
