package com.aopcloud.palmproject.ui.adapter.file;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.aopcloud.palmproject.BuildConfig;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.bean.ImageItem;
import com.aopcloud.palmproject.loader.AppImageLoader;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.io.File;
import java.util.List;

/**
 * @PackageName : com.aopcloud.palmproject.ui.adapter.file
 * @File : TodayCacheAdapter.java
 * @Date : 2020/6/7 2020/6/7
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class TodayCacheAdapter extends BaseQuickAdapter<ImageItem, BaseViewHolder> {
    public TodayCacheAdapter(int layoutResId, @Nullable List<ImageItem> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ImageItem item) {
        boolean v = !TextUtils.isEmpty(item.path) && item.path.contains(".mp4");

        helper.setVisible(R.id.iv_play, v)
                .setVisible(R.id.checkbox,helper.getLayoutPosition() != 0)
                .setChecked(R.id.checkbox, item.select);
        ImageView imageView = helper.getView(R.id.iv_img);
        if (helper.getLayoutPosition() == 0) {
            AppImageLoader.load(mContext, R.mipmap.icon_default_square_add, imageView);
        } else {
            AppImageLoader.load(mContext, new File(item.path), imageView);
        }
    }
}
