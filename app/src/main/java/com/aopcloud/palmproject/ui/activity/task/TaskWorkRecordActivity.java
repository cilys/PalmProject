package com.aopcloud.palmproject.ui.activity.task;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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
import com.aopcloud.palmproject.ui.adapter.task.TaskWorkRecordAdapter;
import com.aopcloud.palmproject.utils.LoginUserUtil;
import com.aopcloud.palmproject.view.decoration.DividerItemDecoration;
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
 * @File : TaskWorkRecordActivity.java
 * @Date : 2020/5/18 2020/5/18
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
@Layout(R.layout.activity_task_record)
public class TaskWorkRecordActivity extends BaseAc implements TaskWorkRecordAdapter.OnItemClickListener{

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
    @BindView(R.id.tv_project_name)
    TextView mTvProjectName;
    @BindView(R.id.tv_task_name)
    TextView mTvTaskName;
    @BindView(R.id.tv_task_count)
    TextView mTvTaskCount;
    @BindView(R.id.tv_leader_name)
    TextView mTvLeaderName;
    @BindView(R.id.tv_total_time)
    TextView mTvTotalTime;
    @BindView(R.id.tv_total_day)
    TextView mTvTotalDay;
    @BindView(R.id.tv_total_price)
    TextView mTvTotalPrice;
    @BindView(R.id.rv_list)
    RecyclerView mRvList;
    @BindView(R.id.tv_time)
    TextView mTvTime;

    private TaskWorkRecordAdapter mAdapter;
    private List<SalaryBean.UserListBean> mBeanList = new ArrayList<>();
    private long timestamp;
    private String task_id;


    private SalaryBean mSalaryBean;

    @Override
    protected void initData() {
        super.initData();
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
            timestamp = bundle.getLong("timestamp");
            task_id = bundle.getString("task_id");
        }
        toRequest(ApiConstants.EventTags.salary_all);

    }

    @Override
    protected void initView() {
        mTvHeaderTitle.setText("人工记录");


        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        date.setTime(timestamp);
        mTvTime.setText(""+dateFormat.format(date));


        mAdapter = new TaskWorkRecordAdapter(R.layout.item_task_work_record, mBeanList);
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
                if (view.getId()==R.id.tv_price){
                    mBeanList.get(position).setExpand(!mBeanList.get(position).isExpand());
                    mAdapter.notifyItemChanged(position);
                }
            }
        });
    }
    @OnClick({R.id.ll_header_back, R.id.ll_header_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_header_back:finish();
                break;
            case R.id.ll_header_right:
                break;
        }
    }

    @Override
    public void toRequest(int eventTag) {
        super.toRequest(eventTag);
        Map map = new HashMap();
        map.put("token", "" + LoginUserUtil.getToken(this));
        map.put("code", "" + LoginUserUtil.getCurrentEnterpriseNo(this));
        map.put("task_id", "" + task_id);
        map.put("start_date", "" + getStartTime());
        map.put("end_date", "" + getEndTime());
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


    public  String getStartTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return "" + dateFormat.format(calendar.getTime());
    }

    public  String getEndTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return "" + dateFormat.format(calendar.getTime());
    }
}
