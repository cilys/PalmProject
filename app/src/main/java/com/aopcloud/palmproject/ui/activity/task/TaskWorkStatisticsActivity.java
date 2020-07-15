package com.aopcloud.palmproject.ui.activity.task;

import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.aopcloud.base.annotation.Layout;
import com.aopcloud.base.util.ResourceUtil;
import com.aopcloud.base.util.ToastUtil;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.api.ApiConstants;
import com.aopcloud.palmproject.common.ResultBean;
import com.aopcloud.palmproject.ui.activity.BaseAc;
import com.aopcloud.palmproject.ui.activity.task.bean.SalaryBean;
import com.aopcloud.palmproject.ui.adapter.task.TaskWorkStatisticsAdapter;
import com.aopcloud.palmproject.utils.LoginUserUtil;
import com.aopcloud.palmproject.view.decoration.DividerItemDecoration;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @PackageName : com.aopcloud.palmproject.ui.activity.task
 * @File : TaskWorkStatisticsActivity.java
 * @Date : 2020/5/18 2020/5/18
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
@Layout(R.layout.activity_task_statistics)
public class TaskWorkStatisticsActivity extends BaseAc implements TaskWorkStatisticsAdapter.OnItemClickListener {
    @BindView(R.id.ll_header_back)
    LinearLayout mLlHeaderBack;
    @BindView(R.id.tv_header_title)
    TextView mTvHeaderTitle;
    @BindView(R.id.tv_header_right)
    TextView mTvHeaderRight;
    @BindView(R.id.ll_header_right)
    LinearLayout mLlHeaderRight;
    @BindView(R.id.ll_common_layout)
    LinearLayout mLlCommonLayout;
    @BindView(R.id.et_search)
    EditText mEtSearch;
    @BindView(R.id.tv_type)
    TextView mTvType;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.tv_project_name)
    TextView mTvProjectName;
    @BindView(R.id.tv_task_name)
    TextView mTvTaskName;
    @BindView(R.id.tv_task_count)
    TextView mTvTaskCount;
    @BindView(R.id.tv_leader_name)
    TextView mTvLeaderName;
    @BindView(R.id.rv_list)
    RecyclerView mRvList;
    @BindView(R.id.tv_total_time)
    TextView mTvTotalTime;
    @BindView(R.id.tv_total_day)
    TextView mTvTotalDay;
    @BindView(R.id.tv_total_price)
    TextView mTvTotalPrice;
    private TaskWorkStatisticsAdapter mAdapter;
    private List<SalaryBean.UserListBean> mBeanList = new ArrayList<>();
    private long timestamp;
    private String task_id;


    private int year;
    private int month;

    private SalaryBean mSalaryBean;

    @Override
    protected void initData() {
        super.initData();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            timestamp = bundle.getLong("timestamp");
            task_id = bundle.getString("task_id");
        }

        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        toRequest(ApiConstants.EventTags.salary_all);

    }

    @Override
    protected void initView() {
        mTvHeaderTitle.setText("人工统计");

        mEtSearch.setFocusable(false);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        Date date = new Date();
        mTvTime.setText("" + dateFormat.format(date));


        mAdapter = new TaskWorkStatisticsAdapter(R.layout.item_task_work_statistics, mBeanList);
        mAdapter.setOnItemClickListener(this::onItemClick);
        mRvList.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration itemDecoration = new DividerItemDecoration.Builder()
                .color(ResourceUtil.getColor(R.color.theme_background_f5))
                .height(2)
                .build();
        mRvList.addItemDecoration(itemDecoration);
        mRvList.setAdapter(mAdapter);
    }

    private void setViewData(SalaryBean salaryBean) {
        if (salaryBean != null) {
            mTvProjectName.setText(salaryBean.getProject_name());
            mTvTaskCount.setText("" + salaryBean.getWork_value() + "" + salaryBean.getWork_unit());
            mTvTaskName.setText(""+salaryBean.getTeam_name());
            mTvLeaderName.setText(salaryBean.getLeader_name());
            mTvTotalTime.setText("" + salaryBean.getTotal_hours());
            mTvTotalPrice.setText("" + salaryBean.getTotal_salary());
            mTvTotalDay.setText("" + salaryBean.getTotal_days());


            mBeanList.clear();
            mBeanList.addAll(salaryBean.getUserList());

            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    protected void setListener() {
        super.setListener();
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId()==R.id.tv_total){
                 mBeanList.get(position).setExpand(!mBeanList.get(position).isExpand());
                 mAdapter.notifyItemChanged(position);
                }
            }
        });
    }

    @OnClick({R.id.ll_header_back, R.id.ll_header_right, R.id.tv_type, R.id.tv_time})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_header_back:
                finish();
                break;
            case R.id.ll_header_right:
                break;
            case R.id.tv_type:
                showTypeDialog();
                break;
            case R.id.tv_time:
                showTimePicker();
                break;
        }
    }


    public void showTypeDialog() {
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.setContentView(R.layout.dialog_project_task_statistics_type);
        dialog.getWindow().findViewById(R.id.design_bottom_sheet).setBackgroundResource(android.R.color.transparent);
        dialog.show();

        dialog.findViewById(R.id.tv_day).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.setType(2);
                mAdapter.notifyDataSetChanged();
                mTvType.setText("工日");
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.tv_hours).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.setType(3);
                mTvType.setText("工时");mAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.tv_salary).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.setType(1);mAdapter.notifyDataSetChanged();
                mTvType.setText("工资");
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
    }


    public void showTimePicker() {
        TimePickerView pickerView = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
                mTvTime.setText("" + dateFormat.format(date));
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                year = calendar.get(Calendar.YEAR);
                month =calendar.get(Calendar.MONTH);
                toRequest(ApiConstants.EventTags.salary_all);
            }
        })
                .setLineSpacingMultiplier(2)
                .setType(new boolean[]{true, true, false, false, false, false})
                .build();
        pickerView.show();


    }


    @Override
    public void toRequest(int eventTag) {
        super.toRequest(eventTag);
        Map map = new HashMap();
        map.put("token", "" + LoginUserUtil.getToken(this));
        map.put("code", "" + LoginUserUtil.getCurrentEnterpriseNo(this));
        map.put("task_id", "" + task_id);
        map.put("start_date", "" + getStartTime(year, month));
        map.put("end_date", "" + getEndTime(year, month));
        if (eventTag == ApiConstants.EventTags.salary_all) {
            iCommonRequestPresenter.requestPost(eventTag, this, ApiConstants.salary_all, map);
        }
    }

    @Override
    public void getRequestData(int eventTag, String result) {
        super.getRequestData(eventTag, result);
        ResultBean bean = JSON.parseObject(result, ResultBean.class);
        if (bean != null && bean.getCode() == 0) {
            if (eventTag == ApiConstants.EventTags.salary_all) {
                mSalaryBean = JSON.parseObject(bean.getData(), SalaryBean.class);
                setViewData(mSalaryBean);
            }
        } else {
            ToastUtil.showToast(bean != null ? bean.getMsg() : "加载错误，请重试");
        }

    }

    @Override
    public void onRequestFailureException(int eventTag, String msg) {
        super.onRequestFailureException(eventTag, msg);
    }


    public static String getStartTime(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return "" + dateFormat.format(calendar.getTime());
    }

    public static String getEndTime(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return "" + dateFormat.format(calendar.getTime());
    }
}
