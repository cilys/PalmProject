package com.aopcloud.palmproject.ui.adapter.file;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.aopcloud.palmproject.BuildConfig;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.loader.AppImageLoader;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.guoxiaoxing.phoenix.core.model.MediaEntity;

import java.io.File;
import java.util.List;

/**
 * @PackageName : com.aopcloud.palmproject.ui.adapter.file
 * @File : FileListAdapter.java
 * @Date : 2020/4/29 2020/4/29
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class FileListAdapter extends BaseQuickAdapter<MediaEntity, BaseViewHolder> {

    private boolean edit;

    public void setEdit(boolean edit) {
        this.edit = edit;
    }

    public FileListAdapter(int layoutResId, @Nullable List<MediaEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MediaEntity item) {
        boolean v = !TextUtils.isEmpty(item.getLocalPath()) && item.getLocalPath().contains(".mp4");

        helper.setVisible(R.id.iv_play, v).addOnClickListener(R.id.iv_del);
        ImageView imageView = helper.getView(R.id.iv_img);
        helper.getView(R.id.iv_del).setVisibility(edit ? View.VISIBLE : View.GONE);

        if (edit && helper.getLayoutPosition() == getItemCount() - 1) {
            AppImageLoader.load(mContext, R.mipmap.icon_default_square_add, imageView);
            helper.getView(R.id.iv_del).setVisibility(View.GONE);
        } else {
            if (!TextUtils.isEmpty(item.getMediaName()) && item.getMediaName().equals("img_add_network")) {
                AppImageLoader.load(mContext, BuildConfig.BASE_URL + item.getLocalPath(), imageView);
            } else {
                AppImageLoader.load(mContext, new File(item.getLocalPath()), imageView);
            }
        }
    }
}
