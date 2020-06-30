package com.aopcloud.palmproject.ui.adapter.project;

import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.loader.AppImageLoader;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.guoxiaoxing.phoenix.core.model.MediaEntity;

import java.util.List;

/**
 * @PackageName : com.aopcloud.palmproject.ui.adapter.project
 * @File : ProjectScenesAdapter.java
 * @Date : 2020/5/5 2020/5/5
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class ProjectScenesChildAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public ProjectScenesChildAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {


        AppImageLoader.load(mContext, item, helper.getView(R.id.iv_img), 4);
        boolean v = item.contains(".mp4");
        helper.setVisible(R.id.iv_play, v);
    }
}
