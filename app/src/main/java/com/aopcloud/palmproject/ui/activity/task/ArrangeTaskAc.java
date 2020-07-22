package com.aopcloud.palmproject.ui.activity.task;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.aopcloud.base.annotation.Layout;
import com.aopcloud.base.util.ResourceUtil;
import com.aopcloud.base.util.ToastUtil;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.api.ApiConstants;
import com.aopcloud.palmproject.common.ResultBean;
import com.aopcloud.palmproject.ui.activity.BaseAc;
import com.aopcloud.palmproject.ui.activity.project.ProjectDetailActivity;
import com.aopcloud.palmproject.ui.activity.project.ProjectTaskUpdateTeamActivity;
import com.aopcloud.palmproject.ui.activity.project.bean.ProjectListBean;
import com.aopcloud.palmproject.ui.activity.project.bean.ProjectTaskBean;
import com.aopcloud.palmproject.ui.adapter.home.HomeProjectAdapter;
import com.aopcloud.palmproject.ui.adapter.home.HomeTaskAdapter;
import com.aopcloud.palmproject.ui.fragment.home.HomeProjectFragment;
import com.aopcloud.palmproject.ui.fragment.home.HomeTaskFragment;
import com.aopcloud.palmproject.utils.LoginUserUtil;
import com.aopcloud.palmproject.view.decoration.DividerItemDecoration;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cily.utils.base.StrUtils;
import com.cily.utils.base.time.TimeType;
import com.cily.utils.base.time.TimeUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 派单给班组
 */
@Layout(R.layout.ac_arrange_task)
public class ArrangeTaskAc extends BaseAc {
    private String team_id, task_id;
    private TextView tv_class_name, tv_start_date, tv_end_date;

    private TextView tv_task_name, tv_project_name, tv_work;

    private String start_date, end_date;
    private boolean canSelectProject = false;
    private boolean canSelectTask = false;
    private String company_id, project_id;

    private boolean clickForProject = false;
    private boolean clickForTask = false;

    @Override
    protected void initView() {
        company_id = getIntent().getStringExtra("company_id");
        if (StrUtils.isEmpty(company_id)){
            company_id = getCompanyId();
        }

        if (!StrUtils.isEmpty(company_id)){
            requestProjectList();
            requestTaskList();
        }

        String company_name = getIntent().getStringExtra("company_name");
        if (StrUtils.isEmpty(company_name)) {
            String cId = getCompanyId();
            if (!StrUtils.isEmpty(company_id)) {
                if (company_id.equals(cId)) {
                    company_name = LoginUserUtil.getCurrentEnterpriseBean(this).getName();
                }
            }
        }

        project_id = getIntent().getStringExtra("project_id");
        canSelectProject = StrUtils.isEmpty(project_id);

        String project_name = getIntent().getStringExtra("project_name");
        task_id = getIntent().getStringExtra("task_id");
        canSelectTask = StrUtils.isEmpty(task_id);
        String task_name = getIntent().getStringExtra("task_name");
        String work_count = getIntent().getStringExtra("work_count");
        String work_unit = getIntent().getStringExtra("work_unit");

        start_date = getIntent().getStringExtra("start_date");
        end_date = getIntent().getStringExtra("end_date");

        findViewById(R.id.ll_header_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        findViewById(R.id.ll_project_name).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (canSelectProject){
                    if (StrUtils.isEmpty(company_id)){
                        showToast("请选择企业");
                        return;
                    }
                    clickForProject = true;
                    if (datas_project == null || datas_project.size() < 1){
                        requestProjectList();
                    } else {
                        showProjectList();
                    }
                }
            }
        });
        findViewById(R.id.ll_project_task_name).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (canSelectTask){
                    if (StrUtils.isEmpty(company_id)){
                        showToast("请选择企业");
                        return;
                    }
                    clickForTask = true;
                    if (datas_task == null || datas_task.size() < 1){
                        requestTaskList();
                    } else {
                        showTaskList();
                    }
                }
            }
        });

        TextView tv_title = (TextView)findViewById(R.id.tv_header_title);
        tv_title.setText("派单");

        TextView tv_company = (TextView)findViewById(R.id.tv_company);
        tv_company.setText(company_name == null ? "" : company_name);

        tv_project_name = (TextView)findViewById(R.id.tv_project_name);
        tv_project_name.setText(project_name);

        tv_task_name = (TextView)findViewById(R.id.tv_task_name);
        tv_task_name.setText(task_name);

        tv_work = (TextView)findViewById(R.id.tv_work);
        tv_work.setText((work_count == null ? "" : work_count) + (work_unit == null ? "" : work_unit));

        tv_class_name = (TextView)findViewById(R.id.tv_class_name);

        tv_start_date = (TextView)findViewById(R.id.tv_start_date);
        tv_end_date = (TextView)findViewById(R.id.tv_end_date);

        findViewById(R.id.ll_class_name).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (StrUtils.isEmpty(project_id)){
                    showToast("请选择项目");
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString("get_team_id", "0");
                bundle.putString("project_id", project_id);
                gotoActivity(ProjectTaskUpdateTeamActivity.class, bundle, 0);
            }
        });
        findViewById(R.id.ll_start_date).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePicker(tv_start_date);
            }
        });
        findViewById(R.id.ll_end_date).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePicker(tv_end_date);
            }
        });

        findViewById(R.id.tv_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (StrUtils.isEmpty(task_id)){
                    showToast("请选择任务");
                    return;
                }

                if (StrUtils.isEmpty(team_id)){
                    ToastUtil.showToast("请选择班组");
                    return;
                }
                String startDate = (String)tv_start_date.getTag();
                if (StrUtils.isEmpty(startDate)){
                    ToastUtil.showToast("请选择开始时间");
                    return;
                }
                long st = TimeUtils.strToMil(startDate, TimeType.DAY_LINE, System.currentTimeMillis());
                long s = TimeUtils.strToMil(start_date, TimeType.DAY_LINE, System.currentTimeMillis());
                if (st < s){
                    showToast("工单开始时间不可早于项目开始时间");
                    return;
                }

                String endDate = (String)tv_end_date.getTag();
                if (StrUtils.isEmpty(endDate)){
                    ToastUtil.showToast("请选择结束时间");
                    return;
                }
                long ed = TimeUtils.strToMil(endDate, TimeType.DAY_LINE, System.currentTimeMillis());
                if (ed < st){
                    showToast("工单结束时间不可早于开始时间");
                    return;
                }
                if (ed < System.currentTimeMillis()){

                }
                toRequest(ApiConstants.EventTags.task_assign, startDate, endDate);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (requestCode == 0) {
                if (data == null){
                    ToastUtil.showToast("未选择班组");
                    return;
                }
                team_id = data.getStringExtra("team_id");
                String team_name = data.getStringExtra("team_name");
                tv_class_name.setText(team_name == null ? "" : team_name);
            }
        }
    }

    public void showTimePicker(TextView tv) {
        TimePickerView pickerView = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                if (tv != null){
                    tv.setText(TimeUtils.milToStr(date.getTime(), "yyyy年MM月dd日"));
                    tv.setTag(TimeUtils.milToStr(date.getTime(), TimeType.DAY_LINE));
                }
            }
        })
                .isDialog(true)
                .setLineSpacingMultiplier(2).build();
        pickerView.show();
    }

    public void toRequest(int eventTag, String startDate, String endDate) {
        super.toRequest(eventTag);
        Map map = baseParamMap();

        if (eventTag == ApiConstants.EventTags.task_assign) {
            map.put("task_id", task_id);
            map.put("start_date", startDate);
            map.put("end_date", endDate);
            map.put("level", "普通");
            map.put("team_id", team_id);
            iCommonRequestPresenter.requestPost(eventTag, this, ApiConstants.task_assign, map);
        }
    }
    private void requestProjectList(){
        Map map = baseParamMap();
        map.put("type", "1");
        iCommonRequestPresenter.requestPost(ApiConstants.EventTags.project_all,
                this, ApiConstants.project_all, map);
    }
    private void requestTaskList(){
        iCommonRequestPresenter.requestPost(ApiConstants.EventTags.task_all,
                this, ApiConstants.task_all, baseParamMap());
    }

    @Override
    public void getRequestData(int eventTag, String result) {
        super.getRequestData(eventTag, result);
        ResultBean bean = JSON.parseObject(result, ResultBean.class);
        if (bean != null && bean.getCode() == 0) {
            if (eventTag == ApiConstants.EventTags.task_assign) {
                showToast("派单成功");
                setResult(RESULT_OK);
                finish();
            } else if (eventTag == ApiConstants.EventTags.task_all) {
                List<ProjectTaskBean> beanList = JSON.parseArray(bean.getData(), ProjectTaskBean.class);
                parseTaskList(beanList);
            } else if (eventTag == ApiConstants.EventTags.project_all) {
                List<ProjectListBean> beanList = JSON.parseArray(bean.getData(), ProjectListBean.class);
                parseProjectList(beanList);
            }
        } else {
            ToastUtil.showToast(bean != null ? bean.getMsg() : "加载错误，请重试");
        }
    }
    private void parseTaskList(List<ProjectTaskBean> beanList){
        if (datas_task == null){
            datas_task = new ArrayList<>();
        }
        datas_task.clear();
        if (beanList != null) {
            for (ProjectTaskBean bean : beanList) {
                if (HomeTaskFragment.STATE_no_plan.equals(bean.getStatus_str())){
                    datas_task.add(bean);
                }
            }
        }
        showTaskList();
    }
    private void parseProjectList(List<ProjectListBean> beanList){
        if (datas_project == null){
            datas_project = new ArrayList<>();
        }
        datas_project.clear();
        if (beanList != null) {
            for (ProjectListBean bean : beanList) {
                if (HomeProjectFragment.STATE_stop.equals(bean.getStatus())
                        || HomeProjectFragment.STATE_termination.equals(bean.getStatus())){

                }else {
                    datas_project.add(bean);
                }
            }
        }
        showProjectList();
    }

    private List<ProjectTaskBean> datas_task;
    private BottomSheetDialog taskListDialog;
    public void showTaskList() {
        if (!clickForTask){
            return;
        }
        clickForTask = true;

        if (taskListDialog != null && taskListDialog.isShowing()){
            return;
        }
        if (datas_task == null){
            datas_task = new ArrayList<>();
        }

        taskListDialog = new BottomSheetDialog(this);
        taskListDialog.setContentView(R.layout.dialog_task_list);
        taskListDialog.getWindow().findViewById(R.id.design_bottom_sheet)
                .setBackgroundResource(android.R.color.transparent);
        taskListDialog.setCancelable(true);
        taskListDialog.show();

        TextView textView = taskListDialog.findViewById(R.id.tv_count);
        RecyclerView recyclerView = taskListDialog.findViewById(R.id.rv_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration itemDecoration = new DividerItemDecoration.Builder()
                .color(ResourceUtil.getColor(R.color.theme_background_f5))
                .height(20)
                .build();
        recyclerView.addItemDecoration(itemDecoration);
        textView.setText("共搜索到" + datas_task.size() + "条任务");
        taskListDialog.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskListDialog.dismiss();
            }
        });
        HomeTaskAdapter adapter = new HomeTaskAdapter(R.layout.item_home_task, datas_task);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                task_id = String.valueOf(datas_task.get(position).getTask_id());
                tv_task_name.setText(datas_task.get(position).getName());
                tv_work.setText(datas_task.get(position).getWork_value() < 0 ? "0" : String.valueOf(datas_task.get(position).getWork_value())
                        + (datas_task.get(position).getWork_unit() == null ? "" : datas_task.get(position).getWork_unit()));
                taskListDialog.dismiss();
            }
        });
    }
    private List<ProjectListBean> datas_project;
    private BottomSheetDialog projectListDialog;
    public void showProjectList() {
        if (!clickForProject){
            return;
        }
        clickForProject = false;
        if (projectListDialog != null && projectListDialog.isShowing()){
            return;
        }
        if (datas_project == null){
            datas_project = new ArrayList<>();
        }
        projectListDialog = new BottomSheetDialog(this);
        projectListDialog.setContentView(R.layout.dialog_home_project_list);
        projectListDialog.getWindow().findViewById(R.id.design_bottom_sheet)
                .setBackgroundResource(android.R.color.transparent);
        projectListDialog.setCancelable(true);
        projectListDialog.show();

        TextView textView = projectListDialog.findViewById(R.id.tv_count);
        RecyclerView recyclerView = projectListDialog.findViewById(R.id.rv_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration itemDecoration = new DividerItemDecoration.Builder()
                .color(ResourceUtil.getColor(R.color.theme_background_f5))
                .height(20)
                .build();
        recyclerView.addItemDecoration(itemDecoration);
        textView.setText("共搜索到" + datas_project.size() + "条项目");
        projectListDialog.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                projectListDialog.dismiss();
            }
        });
        HomeProjectAdapter adapter = new HomeProjectAdapter(R.layout.item_home_project, datas_project);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                project_id = String.valueOf(datas_project.get(position).getProject_id());
                tv_project_name.setText(datas_project.get(position).getName());
                projectListDialog.dismiss();
            }
        });
    }
}