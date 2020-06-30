package com.aopcloud.palmproject.ui.adapter.msg;

import android.support.annotation.Nullable;

import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.bean.MsgTodoNotifyBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @PackageName : com.aopcloud.palmproject.ui.adapter.msg
 * @File : MsgTodoNotifyAdapter.java
 * @Date : 2020/6/8 2020/6/8
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class MsgTodoNotifyAdapter extends BaseQuickAdapter<MsgTodoNotifyBean, BaseViewHolder> {
    public MsgTodoNotifyAdapter(int layoutResId, @Nullable List<MsgTodoNotifyBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MsgTodoNotifyBean item) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        date.setTime(item.getTime() * 1000);
        helper.setText(R.id.tv_type, item.getTypeName())
                .setText(R.id.tv_title, item.getTitle())
                .setText(R.id.tv_msg, item.getMsg())
                .setText(R.id.tv_time, format.format(date))
                .setBackgroundRes(R.id.tv_type, getTypeBg(item.getTypeName()));
    }


    public int getTypeBg(String type) {
        int bg = 0;
        if (type.equals("审批")) {
            bg = R.drawable.shape_msg_todo_approval;
        } else if (type.equals("任务")) {
            bg = R.drawable.shape_msg_todo_task;
        } else if (type.equals("物资")) {
            bg = R.drawable.shape_msg_todo_goods;
        } else if (type.equals("项目")) {
            bg = R.drawable.shape_msg_todo_project;
        } else if (type.equals("掌项")) {
            bg = R.drawable.shape_msg_todo_found;
        }
        return bg;
    }
}
