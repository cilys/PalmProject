package com.aopcloud.palmproject.ui.adapter.project;

import android.content.Context;

import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.ui.activity.project.bean.ProjectLogBean;
import com.aopcloud.palmproject.ui.adapter.base.CommonAdapter;
import com.aopcloud.palmproject.ui.adapter.base.ViewHolder;

import java.util.List;

/**
 * @PackageName : com.aopcloud.palmproject.ui.adapter.project
 * @File : SpinnerItemAdapter.java
 * @Date : 2020/6/1 2020/6/1
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class SpinnerItemAdapter extends CommonAdapter<SpinnerItemAdapter.SpinnerItemBean> {

    public SpinnerItemAdapter(Context context, int layoutId, List<SpinnerItemBean> mBeanList) {
        super(context, layoutId, mBeanList);
    }

    @Override
    public void convert(ViewHolder holder, SpinnerItemBean itemBean, int position) {
        holder.setText(R.id.tv_name, itemBean.getName());

    }


    public static class SpinnerItemBean {
        private int id;
        private String name;

        public SpinnerItemBean(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }


}
