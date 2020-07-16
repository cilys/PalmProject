package com.aopcloud.palmproject.ui.fragment.task;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.aopcloud.base.base.BaseFragment;
import com.aopcloud.base.util.ToastUtil;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.api.ApiConstants;
import com.aopcloud.palmproject.common.ResultBean;
import com.aopcloud.palmproject.ui.activity.task.TaskWorkRecordActivity;
import com.aopcloud.palmproject.ui.activity.task.TaskWorkStatisticsActivity;
import com.aopcloud.palmproject.ui.activity.task.bean.SalaryBean;
import com.aopcloud.palmproject.ui.adapter.task.TaskRecordMonthAdapter;
import com.aopcloud.palmproject.ui.adapter.task.TaskWorkRecordAdapter;
import com.aopcloud.palmproject.utils.LoginUserUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @PackageName : com.aopcloud.palmproject.ui.fragment.task
 * @File : TaskExecuteFragment.java
 * @Date : 2020/5/8 2020/5/8
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class TaskRecordFragment extends BaseFragment implements TaskWorkRecordAdapter.OnItemClickListener {
    @BindView(R.id.iv_reduce)
    ImageView mIvReduce;
    @BindView(R.id.tv_month)
    TextView mTvMonth;
    @BindView(R.id.iv_add)
    ImageView mIvAdd;
    @BindView(R.id.rl_statistics)
    RelativeLayout mRlStatistics;
    @BindView(R.id.rv_list)
    RecyclerView mRvList;

    private String task_id;

    private int year;
    private int month;
    private SalaryBean mSalaryBean;

    private Calendar calendar;

    private TaskRecordMonthAdapter mMonthAdapter;

    public static TaskRecordFragment getInstance(String task_id) {
        Bundle bundle = new Bundle();
        bundle.putString("task_id", task_id);
        TaskRecordFragment fragment = new TaskRecordFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initData() {
        super.initData();
        Bundle bundle = getArguments();
        if (bundle != null) {
            task_id = bundle.getString("task_id");
        }
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        toRequest(ApiConstants.EventTags.salary_all);
    }


    @Override
    protected int setLayoutId() {
        return R.layout.fragment_task_record;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mTvMonth.setText("" + calendar.get(Calendar.YEAR) + "." + (calendar.get(Calendar.MONTH) + 1));

        mMonthAdapter = new TaskRecordMonthAdapter(R.layout.item_task_record_month, mMonthBeans);
        mMonthAdapter.setOnItemClickListener(this);
        mRvList.setLayoutManager(new GridLayoutManager(mActivity, 7));
        mRvList.setAdapter(mMonthAdapter);
    }

    private void setViewData(SalaryBean salaryBean) {

        Map<String, Boolean> map = new HashMap<>();
        for (int i = 0; i < salaryBean.getUserList().size(); i++) {
            for (int j = 0; j < salaryBean.getUserList().get(i).getStatistic().size(); j++) {
                map.put(salaryBean.getUserList().get(i).getStatistic().get(j).getDay(), false);
            }
        }

        setMonth(year, month, map);
    }

    private List<MonthBean> mMonthBeans = new ArrayList<>();

    public void setMonth(int year, int month, Map<String, Boolean> map) {
        mMonthBeans.clear();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int days = calendar.getActualMaximum(Calendar.DATE);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        SimpleDateFormat format = new SimpleDateFormat("E");

        int placeholder = dayOfWeek - 1;
        for (int i = 0; i < placeholder; i++) {
            MonthBean bean = new MonthBean();
            bean.setTimestamp(0);
            bean.setPlaceholder(true);
            mMonthBeans.add(bean);
        }
        for (int i = 0; i < days; i++) {
            MonthBean bean = new MonthBean();
            bean.setTimestamp(calendar.getTimeInMillis());
            mMonthBeans.add(bean);
            calendar.add(Calendar.DATE, +1);
        }
        for (String s : map.keySet()) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date=null;
            try {
                 date =  simpleDateFormat.parse(s);
            } catch (ParseException e) {
                e.printStackTrace();
                date = new Date();
            }
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(date);
            if (!map.get(s)) {
                mMonthBeans.get(calendar1.get(Calendar.DAY_OF_MONTH)).setType(1);
            } else {
                mMonthBeans.get(calendar1.get(Calendar.DAY_OF_MONTH)).setType(2);
            }
        }
        mMonthAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (mMonthBeans.get(position).isPlaceholder()) {
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString("task_id", task_id);
        bundle.putLong("timestamp", mMonthBeans.get(position).getTimestamp());
        gotoActivity(TaskWorkRecordActivity.class, bundle);
    }

    @OnClick({R.id.iv_reduce, R.id.iv_add, R.id.rl_statistics})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_reduce:
                calendar.add(Calendar.MONTH, -1);
                mTvMonth.setText("" + calendar.get(Calendar.YEAR) + "." + (calendar.get(Calendar.MONTH) + 1));
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                toRequest(ApiConstants.EventTags.salary_all);
                break;
            case R.id.iv_add:
                calendar.add(Calendar.MONTH, +1);
                mTvMonth.setText("" + calendar.get(Calendar.YEAR) + "." + (calendar.get(Calendar.MONTH) + 1));
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                toRequest(ApiConstants.EventTags.salary_all);
                break;
            case R.id.rl_statistics:
                Bundle bundle = new Bundle();
                bundle.putString("task_id", task_id);
                gotoActivity(TaskWorkStatisticsActivity.class, bundle);
                break;
        }
    }


    @Override
    public void toRequest(int eventTag) {
        super.toRequest(eventTag);
        Map map = new HashMap();
        map.put("token", "" + LoginUserUtil.getToken(mActivity));
        map.put("code", "" + LoginUserUtil.getCurrentEnterpriseNo(mActivity));
        map.put("task_id", "" + task_id);
        if (eventTag == ApiConstants.EventTags.task_tasks) {
            iCommonRequestPresenter.requestPost(eventTag, mActivity, ApiConstants.task_tasks, map);
        } else if (eventTag == ApiConstants.EventTags.salary_all) {
            map.put("start_date", "" + getStartTime(year, month));
            map.put("end_date", "" + getEndTime(year, month));

            iCommonRequestPresenter.requestPost(eventTag, mActivity, ApiConstants.salary_all, map);
        }
    }

    @Override
    public void getRequestData(int eventTag, String result) {
        super.getRequestData(eventTag, result);
        ResultBean bean = JSON.parseObject(result, ResultBean.class);
        if (bean != null && bean.getCode() == 0) {
            if (eventTag == ApiConstants.EventTags.task_tasks) {

            } else if (eventTag == ApiConstants.EventTags.salary_all) {
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

    public static class MonthBean {
        private long timestamp;
        private boolean placeholder;
        private int type;//0 空白  1.有记录   .记录中
        private long minute;


        public long getMinute() {
            return minute;
        }

        public void setMinute(long minute) {
            this.minute = minute;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }

        public boolean isPlaceholder() {
            return placeholder;
        }

        public void setPlaceholder(boolean placeholder) {
            this.placeholder = placeholder;
        }
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