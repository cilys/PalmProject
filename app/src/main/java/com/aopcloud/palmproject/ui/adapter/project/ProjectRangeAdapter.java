package com.aopcloud.palmproject.ui.adapter.project;

import android.support.annotation.Nullable;

import com.aopcloud.base.util.ResourceUtil;
import com.aopcloud.palmproject.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @PackageName : com.aopcloud.palmproject.ui.adapter.project
 * @File : ProjectRangeAdapter.java
 * @Date : 2020/5/31 2020/5/31
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class ProjectRangeAdapter extends BaseQuickAdapter<ProjectRangeAdapter.ProjectRangeBean, BaseViewHolder> {
    public ProjectRangeAdapter(int layoutResId, @Nullable List<ProjectRangeBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProjectRangeBean item) {
        helper.setText(R.id.tv_name, item.getName())
                .setText(R.id.tv_desc, item.getDesc())
                .setTextColor(R.id.tv_name,item.isSelect()? ResourceUtil.getColor("#FF108CF7"):ResourceUtil.getColor("#FF101010"))
                .setTextColor(R.id.tv_desc,item.isSelect()? ResourceUtil.getColor("#FF108CF7"):ResourceUtil.getColor("#FF101010"))
                .setVisible(R.id.iv_select, item.isSelect());

    }


    public static class ProjectRangeBean {
        private String type;
        private String name;
        private String desc;
        private boolean select;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public boolean isSelect() {
            return select;
        }

        public void setSelect(boolean select) {
            this.select = select;
        }
    }
}
