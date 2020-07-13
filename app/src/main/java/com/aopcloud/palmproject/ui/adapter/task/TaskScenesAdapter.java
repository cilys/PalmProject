package com.aopcloud.palmproject.ui.adapter.task;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.aopcloud.base.util.ResourceUtil;
import com.aopcloud.palmproject.BuildConfig;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.ui.activity.camera.PreviewActivity;
import com.aopcloud.palmproject.ui.activity.task.bean.TaskScenesBean;
import com.aopcloud.palmproject.ui.adapter.file.PreviewAdapter;
import com.aopcloud.palmproject.ui.adapter.project.ProjectScenesChildAdapter;
import com.aopcloud.palmproject.view.decoration.DividerItemDecoration;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @PackageName : com.aopcloud.palmproject.ui.adapter.task
 * @File : TaskScenesAdapter.java
 * @Date : 2020/5/21 2020/5/21
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class TaskScenesAdapter extends BaseQuickAdapter<TaskScenesBean, BaseViewHolder> {
    public TaskScenesAdapter(int layoutResId, @Nullable List<TaskScenesBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TaskScenesBean item) {
        SimpleDateFormat dateFormat = new SimpleDateFormat();
        Date date = new Date();

        List list = new ArrayList();
//        list.add("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3632331434,3091286222&fm=26&gp=0.jpg");
//        list.add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3526019520,2878521444&fm=26&gp=0.jpg");
//        list.add("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3753101202,866157521&fm=26&gp=0.jpg");
//        list.add("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=267007988,3563129470&fm=26&gp=0.jpg");
//        list.add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=140767,3453015475&fm=26&gp=0.jpg");
//        list.add("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2451825562,3895880353&fm=26&gp=0.jpg");
//        list.add("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2286749956,219396605&fm=26&gp=0.jpg");
//        list.add("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3484407991,1840937079&fm=26&gp=0.jpg");
//        list.add("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2427041237,419913377&fm=26&gp=0.jpg");


        if (!TextUtils.isEmpty(item.getAttach())) {
            String[] url = item.getAttach().split(",");
            for (int i = 0; i < url.length; i++) {
                list.add(BuildConfig.BASE_URL + url[i]);
            }
        }
        helper.setText(R.id.tv_name, "" + item.getTask_name())
                .setText(R.id.tv_count, "" + list.size())
                .setText(R.id.tv_time, "" + dateFormat.format(date))
                .setVisible(R.id.ll_count, list.size() > 9);

        ProjectScenesChildAdapter adapter = new ProjectScenesChildAdapter(R.layout.item_project_scenes_img, list);
        RecyclerView gridView = helper.getView(R.id.rv_list);
        gridView.setLayoutManager(new GridLayoutManager(mContext, 3));
        DividerItemDecoration itemDecoration= new DividerItemDecoration.Builder()
                .color(ResourceUtil.getColor(R.color.theme_background_f5))
                .height(2)
                .build();
        gridView.addItemDecoration(itemDecoration);
        gridView.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
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
