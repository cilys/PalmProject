package com.aopcloud.palmproject.ui.activity.news.adapter;

import android.support.annotation.Nullable;
import android.widget.TextView;

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
public class WorkNotifyAdapter extends BaseQuickAdapter<WorkNotifyBean, BaseViewHolder> {
    public WorkNotifyAdapter(int layoutResId, @Nullable List<WorkNotifyBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WorkNotifyBean item) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date();
        date.setTime(item.getMake_time() * 1000);
        helper.setText(R.id.tv_type, item.getTitle())
                .setText(R.id.tv_msg, item.getContent())
                .setText(R.id.tv_time, dateFormat.format(date));
        TextView mTvType = helper.getView(R.id.tv_type);
        if (item.getData().getMsg_type().equals("t4")) {
            mTvType.setBackgroundResource(R.drawable.shape_news_notify_team_bg);
        } else if (item.getData().getMsg_type().equals("t4")) {
            mTvType.setBackgroundResource(R.drawable.shape_news_notify_project_bg);
        } else if (item.getData().getMsg_type().equals("t4")) {
            mTvType.setBackgroundResource(R.drawable.shape_news_notify_task_bg);
        } else  {
            mTvType.setBackgroundResource(R.drawable.shape_news_notify_project_bg);
        }
    }
}
