package com.aopcloud.palmproject.dialog.adapter;

import android.support.annotation.Nullable;

import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.dialog.bean.MulBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class RvBottomListMulAdapter extends BaseQuickAdapter<MulBean, BaseViewHolder> {

    public RvBottomListMulAdapter(@Nullable List<MulBean> data) {
        super(R.layout.item_mul_select_dialog, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MulBean item) {
        helper.setText(R.id.tv_name, item.getName())
                .setImageResource(R.id.img_status, item.isSelected() ? R.mipmap.shop_icon_click_selected : R.mipmap.shop_icon_click_selected_n)
                ;
    }
}
