package com.aopcloud.palmproject.ui.activity.task.list;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.aopcloud.base.base.BaseFragment;
import com.aopcloud.base.util.ToastUtil;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.api.ApiConstants;
import com.aopcloud.palmproject.common.ResultBean;
import com.aopcloud.palmproject.conf.TaskStatus;
import com.aopcloud.palmproject.ui.activity.project.ProjectTaskDetailActivity;
import com.aopcloud.palmproject.ui.activity.project.bean.ProjectTaskBean;
import com.aopcloud.palmproject.ui.activity.task.TaskDetailActivity;
import com.aopcloud.palmproject.ui.fragment.home.HomeTaskFragment;
import com.aopcloud.palmproject.utils.LoginUserUtil;
import com.aopcloud.palmproject.utils.task.TaskUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cily.utils.base.time.TimeType;
import com.cily.utils.base.time.TimeUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskListFg extends BaseFragment {
    private RvTaskListAdapter adapter;
    private List<ProjectTaskBean> datas;
    private String type = "1";
    private String state = TaskStatus.STATE_all;
    private String state_big = HomeTaskFragment.STATE_UNDO; //大类、已完成、未完成、已逾期

    @Override
    protected int setLayoutId() {
        return R.layout.fg_task_list;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);

        Bundle bundle = getArguments();
        if (bundle != null){
            type = bundle.getString("type", type);
            state = bundle.getString("state", state);
            state_big = bundle.getString("STATE_BIG", state_big);
        }

        RecyclerView rv = (RecyclerView)view.findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        datas = new ArrayList<>();
        adapter = new RvTaskListAdapter(R.layout.item_rv_task_list, datas);
        rv.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                Bundle bundle = new Bundle();
//                bundle.putString("project_id", "" + datas.get(position).getProject_id());
//                bundle.putString("task_id", "" + datas.get(position).getTask_id());
//                gotoActivity(ProjectTaskDetailActivity.class, bundle, 0);
                Bundle bundle = new Bundle();
                bundle.putString("task_id", datas.get(position).getTask_id() + "");
                bundle.putString("project_id", datas.get(position).getProject_id() + "");
                bundle.putString("task_name", "" + datas.get(position).getName());
                bundle.putString("team_id",datas.get(position).getTeam_id() + "");
                bundle.putString("project_name", datas.get(position).getProject_name());

                bundle.putString("task_status", TaskUtils.getState(datas.get(position)));
                gotoActivity(TaskDetailActivity.class, 0, bundle);
            }
        });

        isShow = true;
        toRequest(ApiConstants.EventTags.task_all);
    }

    @Override
    public void toRequest(int eventTag) {
        super.toRequest(eventTag);
        Map map = new HashMap();
        map.put("token", "" + LoginUserUtil.getToken(mActivity));
        map.put("code", "" + LoginUserUtil.getCurrentEnterpriseNo(mActivity));
        if (eventTag == ApiConstants.EventTags.task_all) {
            map.put("type", "" + type);
            iCommonRequestPresenter.requestPost(eventTag, mActivity, ApiConstants.task_all, map);
        }
    }

    @Override
    public void getRequestData(int eventTag, String result) {
        super.getRequestData(eventTag, result);
        ResultBean bean = JSON.parseObject(result, ResultBean.class);
        if (bean != null && bean.getCode() == 0) {
            if (eventTag == ApiConstants.EventTags.task_all) {
                List<ProjectTaskBean> beanList = JSON.parseArray(bean.getData(), ProjectTaskBean.class);
                parseList(beanList);
            }
        } else {
            ToastUtil.showToast(bean != null ? bean.getMsg() : "加载错误，请重试");
        }
    }

    private void parseList( List<ProjectTaskBean> beanList){
        if (datas == null){
            datas = new ArrayList<>();
        }
        datas.clear();
        if (beanList != null && beanList.size() > 0){
            datas.addAll(TaskUtils.getList(beanList, state));

        }else {
            ToastUtil.showToast("暂无数据");
        }
        if (adapter != null){
            adapter.notifyDataSetChanged();
        }
    }

    public void onChange(String type){
        this.type = type;
        if (isShow){
            toRequest(ApiConstants.EventTags.task_all);
        }
    }

    private boolean isShow = false;
    @Override
    protected void onVisible() {
        super.onVisible();
        isShow = true;

        toRequest(ApiConstants.EventTags.task_all);
    }

    @Override
    protected void onInvisible() {
        super.onInvisible();
        isShow = false;
    }
}