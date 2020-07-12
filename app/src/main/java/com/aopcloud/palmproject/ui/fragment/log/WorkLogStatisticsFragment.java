package com.aopcloud.palmproject.ui.fragment.log;

import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.aopcloud.base.base.BaseFragment;
import com.aopcloud.base.log.Logcat;
import com.aopcloud.base.util.ToastUtil;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.api.ApiConstants;
import com.aopcloud.palmproject.common.ResultBean;
import com.aopcloud.palmproject.ui.activity.log.bean.WorkLogStatisticsBean;
import com.aopcloud.palmproject.utils.LoginUserUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @PackageName : com.aopcloud.palmproject.ui.fragment.log
 * @File : WorkLogStatisticsFragment.java
 * @Date : 2020/4/22 14:07
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @VersionCode : V 1.0
 * @Describe ：
 */
public class WorkLogStatisticsFragment extends BaseFragment {
    @BindView(R.id.iv_reduce)
    ImageView mIvReduce;
    @BindView(R.id.tv_date)
    TextView mTvDate;
    @BindView(R.id.iv_add)
    ImageView mIvAdd;
    @BindView(R.id.progress_daily)
    SeekBar mProgressDaily;
    @BindView(R.id.tv_daily)
    TextView mTvDaily;
    @BindView(R.id.progress_week)
    SeekBar mProgressWeek;
    @BindView(R.id.tv_week)
    TextView mTvWeek;
    @BindView(R.id.progress_month)
    SeekBar mProgressMonth;
    @BindView(R.id.tv_month)
    TextView mTvMonth;

    private Calendar mCalendar;
    private SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private String start_time = "";
    private String end_time = "";

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_work_log_statistics;
    }

    @Override
    protected void initData() {
        super.initData();
        toRequest(ApiConstants.EventTags.reportjob_statistics);
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mCalendar = Calendar.getInstance();
        mTvDate.setText(mDateFormat.format(mCalendar.getTime()));
        mProgressDaily.setPadding(0, 0, 0, 0);
        mProgressWeek.setPadding(0, 0, 0, 0);
        mProgressMonth.setPadding(0, 0, 0, 0);
    }


    private void setViewData(WorkLogStatisticsBean statisticsBean) {
        mTvDaily.setText(statisticsBean.getReport_day() + "/" + statisticsBean.getTotal());
        mTvWeek.setText(statisticsBean.getReport_week() + "/" + statisticsBean.getTotal());
        mTvMonth.setText(statisticsBean.getReport_month() + "/" + statisticsBean.getTotal());
        mProgressDaily.setProgress(statisticsBean.getReport_day());
        mProgressWeek.setProgress(statisticsBean.getReport_week());
        mProgressMonth.setProgress(statisticsBean.getReport_month());
    }

    @OnClick({R.id.iv_reduce, R.id.iv_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_reduce:
                mCalendar.add(Calendar.DATE, -1);
                mTvDate.setText(mDateFormat.format(mCalendar.getTime()));
                toRequest(ApiConstants.EventTags.reportjob_statistics);
                break;
            case R.id.iv_add:
                mCalendar.add(Calendar.DATE, 1);
                mTvDate.setText(mDateFormat.format(mCalendar.getTime()));
                toRequest(ApiConstants.EventTags.reportjob_statistics);
                break;
        }
    }


    @Override
    public void toRequest(int eventTag) {
        super.toRequest(eventTag);
        Map map = new HashMap();
        map.put("token", "" + LoginUserUtil.getToken(mActivity));
        if (eventTag == ApiConstants.EventTags.reportjob_statistics) {
            map.put("code", "" + LoginUserUtil.getCurrentEnterpriseNo(mActivity));
            map.put("role","1");
            map.put("start_time", "" + start_time);
            map.put("end_time", "" + end_time);
            iCommonRequestPresenter.requestPost(eventTag, mActivity, ApiConstants.reportjob_statistics, map);
        }
    }

    @Override
    public void getRequestData(int eventTag, String result) {
        super.getRequestData(eventTag, result);
        ResultBean bean = JSON.parseObject(result, ResultBean.class);
        if (bean != null && bean.getCode() == 0) {
            if (eventTag == ApiConstants.EventTags.reportjob_statistics) {
                WorkLogStatisticsBean statisticsBean = JSON.parseObject(bean.getData(), WorkLogStatisticsBean.class);
                setViewData(statisticsBean);
            }
        } else {
            ToastUtil.showToast(bean != null ? bean.getMsg() : "加载错误，请重试");
        }
    }

    @Override
    public void onRequestFailureException(int eventTag, String msg) {
        super.onRequestFailureException(eventTag, msg);
    }
}
