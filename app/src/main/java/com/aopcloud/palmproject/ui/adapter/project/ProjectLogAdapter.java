package com.aopcloud.palmproject.ui.adapter.project;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.aopcloud.base.log.Logcat;
import com.aopcloud.base.util.ResourceUtil;
import com.aopcloud.palmproject.BuildConfig;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.loader.AppImageLoader;
import com.aopcloud.palmproject.ui.activity.camera.PreviewActivity;
import com.aopcloud.palmproject.ui.activity.project.bean.ProjectLogBean;
import com.aopcloud.palmproject.ui.adapter.file.PreviewAdapter;
import com.aopcloud.palmproject.view.CircleImageView;
import com.aopcloud.palmproject.view.decoration.DividerItemDecoration;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @PackageName : com.aopcloud.palmproject.ui.adapter.project
 * @File : ProjectLogAdapter.java
 * @Date : 2020/5/15 9:25
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @VersionCode : V 1.0
 * @Describe ：
 */
public class ProjectLogAdapter extends BaseQuickAdapter<ProjectLogBean, BaseViewHolder> {
    public ProjectLogAdapter(int layoutResId, @Nullable List<ProjectLogBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProjectLogBean item) {
        SimpleDateFormat dateFormat = new SimpleDateFormat();
        Date date = new Date();
        helper.setText(R.id.tv_name, item.getUser_name())
                .setText(R.id.tv_content, item.getContent())
                .setText(R.id.tv_time, dateFormat.format(date))
                .setText(R.id.tv_order,"工单："+item.getTask_name());
        CircleImageView imageView = helper.getView(R.id.iv_img);
        AppImageLoader.loadCircleImage(mContext
                , BuildConfig.BASE_URL + item.getUser_avatar()
                , imageView);

        List list = new ArrayList();
        if (!TextUtils.isEmpty(item.getAttach())) {
            String[] url = item.getAttach().split(",");
            for (int i = 0; i <url.length ; i++) {
                list.add(BuildConfig.BASE_URL+url[i]);
            }
        }
        Logcat.i("-------"+ JSON.toJSONString(list));
        ProjectProgressImgAdapter adapter = new ProjectProgressImgAdapter(R.layout.item_project_progress_img, list);
        RecyclerView recyclerView = helper.getView(R.id.rv_list);
        recyclerView.setVisibility(list.size()>0? View.VISIBLE:View.GONE);
        DividerItemDecoration itemDecoration = new DividerItemDecoration.Builder()
                .color(ResourceUtil.getColor(R.color.theme_background_f5))
                .width(2)
                .build();
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                List<PreviewAdapter.PreviewBean> previewBeans = new ArrayList();
                for (int i = 0; i < list.size(); i++) {
                    previewBeans.add(new PreviewAdapter.PreviewBean(list.get(position).toString()));
                }
                Bundle bundle = new Bundle();
                bundle.putSerializable("PreviewBean", (Serializable) previewBeans);
                Intent intent = new Intent(mContext, PreviewActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtras(bundle);
                mContext.startActivity(intent);

            }
        });
    }
}
