package com.aopcloud.palmproject.ui.activity.task.dashboard;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.aopcloud.base.annotation.Layout;
import com.aopcloud.base.util.ToastUtil;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.api.ApiConstants;
import com.aopcloud.palmproject.common.ResultBean;
import com.aopcloud.palmproject.ui.activity.BaseAc;
import com.aopcloud.palmproject.ui.activity.project.ProjectTaskDetailActivity;
import com.aopcloud.palmproject.ui.activity.project.bean.ProjectTaskBean;
import com.aopcloud.palmproject.ui.adapter.project.ProjectTaskAdapter;
import com.aopcloud.palmproject.ui.fragment.project.DashboardFragment;
import com.aopcloud.palmproject.utils.LoginUserUtil;
import com.aopcloud.palmproject.utils.task.TaskUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目详情、看板、任务分类列表
 */
@Layout(R.layout.ac_dashboard_task_list)
public class DashboardTaskListAc extends BaseAc {
    private SwipeRefreshLayout srl;
    private ProjectTaskAdapter adapter;
    private List<ProjectTaskBean> datas;
    private String project_id;
    private String status;

    @Override
    protected void initView() {
        status = getIntent().getStringExtra("status");
        project_id = getIntent().getStringExtra("project_id");

        TextView tv_title = (TextView) findViewById(R.id.tv_header_title);
        if (DashboardFragment.STATUS_UN_PLAN.equals(status)) {
            tv_title.setText("未安排");
        } else if (DashboardFragment.STATUS_UN_START.equals(status)) {
            tv_title.setText("未开始");
        } else if (DashboardFragment.STATUS_IN_PROCESS.equals(status)) {
            tv_title.setText("进行中");
        } else if (DashboardFragment.STATUS_OUT_TIME.equals(status)) {
            tv_title.setText("已逾期");
        } else if (DashboardFragment.STATUS_COMPLETE.equals(status)) {
            tv_title.setText("已完成");
        } else if (DashboardFragment.STATUS_PAUSE.equals(status)) {
            tv_title.setText("已暂停");
        }

        findViewById(R.id.ll_header_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        srl = (SwipeRefreshLayout) findViewById(R.id.srl);
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                toRequest(ApiConstants.EventTags.project_tasks);
            }
        });

        RecyclerView rv = findViewById(R.id.rv);
        datas = new ArrayList<>();
        adapter = new ProjectTaskAdapter(R.layout.item_project_work_sheet, datas);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("project_id", "" + project_id);
                bundle.putString("task_id", "" + datas.get(position).getTask_id());
                gotoActivity(ProjectTaskDetailActivity.class, bundle, 0);
            }
        });

        toRequest(ApiConstants.EventTags.project_tasks);
    }

    private void endRefresh() {
        if (srl != null) {
            srl.setRefreshing(false);
        }
    }

    @Override
    public void toRequest(int eventTag) {
        super.toRequest(eventTag);
        Map map = new HashMap();
        map.put("token", "" + LoginUserUtil.getToken(this));
        map.put("code", "" + LoginUserUtil.getCurrentEnterpriseNo(this));
        map.put("project_id", "" + project_id);//项目名称
        if (eventTag == ApiConstants.EventTags.project_tasks) {
            iCommonRequestPresenter.requestPost(eventTag, this, ApiConstants.project_tasks, map);
        }
    }

    @Override
    public void getRequestData(int eventTag, String result) {
        super.getRequestData(eventTag, result);
        ResultBean bean = JSON.parseObject(result, ResultBean.class);
        if (bean != null && bean.getCode() == 0) {
            if (eventTag == ApiConstants.EventTags.project_tasks) {
                List<ProjectTaskBean> beanList = JSON.parseArray(bean.getData(), ProjectTaskBean.class);
                setViewData(beanList);
            }
        } else {
            ToastUtil.showToast(bean != null ? bean.getMsg() : "加载错误，请重试");
        }
    }

    private void setViewData(List<ProjectTaskBean> beanList) {
        if (datas == null) {
            datas = new ArrayList<>();
        }
        datas.clear();
        if (beanList == null || beanList.size() < 1) {
            if (adapter != null) {
                adapter.notifyDataSetChanged();
            }
            return;
        }
        datas.addAll(TaskUtils.getTypeList(beanList, status));
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }
}