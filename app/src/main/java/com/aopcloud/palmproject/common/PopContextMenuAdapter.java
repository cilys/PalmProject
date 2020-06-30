package com.aopcloud.palmproject.common;

import android.content.Context;

import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.bean.PopMenuBean;
import com.aopcloud.palmproject.ui.adapter.base.CommonAdapter;
import com.aopcloud.palmproject.ui.adapter.base.ViewHolder;

import java.util.List;

/**
 * @PackageName : com.aopcloud.palmproject.common
 * @File : PopContextMenuAdapter.java
 * @Date : 2020/5/3 2020/5/3
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class PopContextMenuAdapter extends CommonAdapter<PopMenuBean> {


    public PopContextMenuAdapter(Context context, int layoutId, List<PopMenuBean> mBeanList) {
        super(context, layoutId, mBeanList);
    }

    @Override
    public void convert(ViewHolder holder, PopMenuBean menuBean, int position) {
        holder.setText(R.id.tv_menu_item_text, menuBean.getText());
//        holder.setImageResource(R.id.iv_menu_item_icon, menuBean.getIcon());
    }
}
