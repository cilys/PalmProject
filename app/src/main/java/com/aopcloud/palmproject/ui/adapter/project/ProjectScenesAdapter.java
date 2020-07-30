package com.aopcloud.palmproject.ui.adapter.project;

import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.aopcloud.base.util.ResourceUtil;
import com.aopcloud.palmproject.BuildConfig;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.api.ApiConstants;
import com.aopcloud.palmproject.loader.AppImageLoader;
import com.aopcloud.palmproject.ui.activity.project.bean.ProjectSceneBean;
import com.aopcloud.palmproject.utils.DateUtils;
import com.aopcloud.palmproject.view.decoration.DividerItemDecoration;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
public class ProjectScenesAdapter extends BaseQuickAdapter<ProjectSceneBean.ScenesBean, BaseViewHolder> {

    private OnItemFileClickListener mOnItemFileClickListener;

    public void setOnItemFileClickListener(OnItemFileClickListener onItemFileClickListener) {
        mOnItemFileClickListener = onItemFileClickListener;
    }

    public ProjectScenesAdapter(int layoutResId, @Nullable List<ProjectSceneBean.ScenesBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProjectSceneBean.ScenesBean item) {
        SimpleDateFormat dateFormat = new SimpleDateFormat();

        List list = new ArrayList();

        if (!TextUtils.isEmpty(item.getAttach())) {
            String[] url = item.getAttach().split(",");
            for (int i = 0; i < url.length; i++) {
                list.add(BuildConfig.BASE_URL + url[i]);
            }
        }
        helper.setText(R.id.tv_name, "" + item.getTask_name())
                .setText(R.id.tv_count, "" + list.size())
                .setText(R.id.tv_user_name, item.getUser_name())
                .setText(R.id.tv_week, DateUtils.getWeekByDay(item.getMake_time() * 1000))
                .setText(R.id.tv_day, DateUtils.fomcatDay(item.getMake_time() * 1000))
                .setVisible(R.id.ll_count, list.size() > 9);
        AppImageLoader.load(mContext,
                ApiConstants.BASE_URL_PRODUCTION + item.getUser_avatar(),
                helper.getView(R.id.img_user_avater), 4);

        ProjectScenesChildAdapter adapter = new ProjectScenesChildAdapter(R.layout.item_project_scenes_img, list);
        RecyclerView gridView = helper.getView(R.id.rv_list);
        gridView.setLayoutManager(new GridLayoutManager(mContext, 3));
        DividerItemDecoration itemDecoration = new DividerItemDecoration.Builder()
                .color(ResourceUtil.getColor(R.color.theme_background_f5))
                .height(4)
                .build();
        gridView.addItemDecoration(itemDecoration);
        gridView.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (mOnItemFileClickListener!=null){
                    mOnItemFileClickListener.onItemClick(ProjectScenesAdapter.this, view, helper.getAdapterPosition());
                }
            }
        });
    }

    public interface OnItemFileClickListener{
       void onItemClick(BaseQuickAdapter adapter, View view, int position);
    }
}