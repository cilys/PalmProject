package com.aopcloud.palmproject.ui.adapter.project;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.aopcloud.palmproject.BuildConfig;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.loader.AppImageLoader;
import com.aopcloud.palmproject.view.CircleImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @PackageName : com.aopcloud.palmproject.ui.adapter.project
 * @File : ProjectProgressImgAdapter.java
 * @Date : 2020/5/14 2020/5/14
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class ProjectProgressImgAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public ProjectProgressImgAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ImageView imageView = helper.getView(R.id.iv_img);
        AppImageLoader.load(mContext, BuildConfig.BASE_URL + item, imageView, 4);
        AppImageLoader.load(mContext,  item, imageView, 4);
        helper.addOnClickListener(R.id.iv_img);

    }
}
