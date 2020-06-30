package com.aopcloud.palmproject.ui.activity.news.adapter;

import android.support.annotation.Nullable;

import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.ui.activity.news.bean.WorkNotifyBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @PackageName : com.aopcloud.basic.ui.adapter.news
 * @File : WorkNotifyAdapter.java
 * @Date : 2020/4/11 2020/4/11
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class NewsNotifyAdapter extends BaseQuickAdapter<WorkNotifyBean, BaseViewHolder> {
    public NewsNotifyAdapter(int layoutResId, @Nullable List<WorkNotifyBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WorkNotifyBean item) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date();
        date.setTime(item.getMake_time()*1000);
        helper.setText(R.id.tv_notify,"["+item.getTitle()+"]"+item.getContent())
        .setText(R.id.tv_time,""+dateFormat.format(date));


    }
}
