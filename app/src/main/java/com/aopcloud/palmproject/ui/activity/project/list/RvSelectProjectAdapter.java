package com.aopcloud.palmproject.ui.activity.project.list;

import android.support.annotation.Nullable;
import android.view.View;

import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.ui.activity.project.bean.ProjectListBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class RvSelectProjectAdapter extends BaseQuickAdapter<ProjectListBean, BaseViewHolder> {
    public RvSelectProjectAdapter(@Nullable List<ProjectListBean> data) {
        super(R.layout.item_rv_select_project, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProjectListBean item) {
        helper.setText(R.id.tv_title, item.getName())
                .setText(R.id.tv_leader, item.getLeader_name() + "")
                .setText(R.id.tv_day, item.getStart_date() + "")
                .setText(R.id.tv_progress, item.getProgress() + "%")
                .setImageResource(R.id.img_checked, item.isSelect() ? R.mipmap.shop_icon_click_selected : R.mipmap.shop_icon_click_selected_n);

        helper.getView(R.id.img_checked).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (item.isSelect()){
                    item.setSelect(false);
                } else {
                    for (ProjectListBean b : mData){
                        b.setSelect(false);
                    }
                    item.setSelect(true);
                }
                notifyDataSetChanged();
            }
        });
    }
}
