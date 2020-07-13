package com.aopcloud.palmproject.ui.activity.task;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.aopcloud.base.annotation.Layout;
import com.aopcloud.base.base.BaseActivity;
import com.aopcloud.base.util.ToastUtil;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.api.ApiConstants;
import com.aopcloud.palmproject.common.ResultBean;
import com.aopcloud.palmproject.ui.activity.project.ProjectTaskUpdateTeamActivity;
import com.aopcloud.palmproject.ui.activity.project.bean.ProjectTeamListBean;
import com.aopcloud.palmproject.utils.LoginUserUtil;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.cily.utils.base.StrUtils;
import com.cily.utils.base.time.TimeType;
import com.cily.utils.base.time.TimeUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 派单给班组
 */
@Layout(R.layout.ac_arrange_task)
public class ArrangeTaskAc extends BaseActivity {
    private String team_id, task_id;
    private TextView tv_class_name, tv_start_date, tv_end_date;

    private String start_date, end_date;

    @Override
    protected void initView() {
        String company_id = getIntent().getStringExtra("company_id");
        String company_name = getIntent().getStringExtra("company_name");
        String project_id = getIntent().getStringExtra("project_id");
        String project_name = getIntent().getStringExtra("project_name");
        task_id = getIntent().getStringExtra("task_id");
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

        TextView tv_title = (TextView)findViewById(R.id.tv_header_title);
        tv_title.setText("派单");

        TextView tv_company = (TextView)findViewById(R.id.tv_company);
        tv_company.setText(company_name);

        TextView tv_project_name = (TextView)findViewById(R.id.tv_project_name);
        tv_project_name.setText(project_name);

        TextView tv_task_name = (TextView)findViewById(R.id.tv_task_name);
        tv_task_name.setText(task_name);

        TextView tv_work = (TextView)findViewById(R.id.tv_work);
        tv_work.setText(work_count + work_unit);

        tv_class_name = (TextView)findViewById(R.id.tv_class_name);

        tv_start_date = (TextView)findViewById(R.id.tv_start_date);
        tv_end_date = (TextView)findViewById(R.id.tv_end_date);

        findViewById(R.id.ll_class_name).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        Map map = new HashMap();
        map.put("token", "" + LoginUserUtil.getToken(this));
        map.put("code", "" + LoginUserUtil.getCurrentEnterpriseNo(this));

        if (eventTag == ApiConstants.EventTags.task_assign) {
            map.put("task_id", task_id);
            map.put("start_date", startDate);
            map.put("end_date", endDate);
            map.put("level", "普通");
            map.put("team_id", team_id);
            iCommonRequestPresenter.requestPost(eventTag, this, ApiConstants.task_assign, map);
        }
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
            }
        } else {
            ToastUtil.showToast(bean != null ? bean.getMsg() : "加载错误，请重试");
        }
    }
}