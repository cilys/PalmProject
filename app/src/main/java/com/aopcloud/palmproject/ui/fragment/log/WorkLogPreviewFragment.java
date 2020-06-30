package com.aopcloud.palmproject.ui.fragment.log;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.aopcloud.base.base.BaseFragment;
import com.aopcloud.base.log.Logcat;
import com.aopcloud.base.util.ToastUtil;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.api.ApiConstants;
import com.aopcloud.palmproject.common.ResultBean;
import com.aopcloud.palmproject.ui.activity.log.WorkLogSearchActivity;
import com.aopcloud.palmproject.ui.activity.log.bean.WorkLogListBean;
import com.aopcloud.palmproject.ui.adapter.log.WorkLogAdapter;
import com.aopcloud.palmproject.utils.LoginUserUtil;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @PackageName : com.aopcloud.palmproject.ui.fragment.log
 * @File : MyWorkLogFragment.java
 * @Date : 2020/4/21 2020/4/21
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class WorkLogPreviewFragment extends BaseFragment {
    @BindView(R.id.rv_list)
    RecyclerView mRvList;
    @BindView(R.id.tv_search)
    TextView mTvSearch;
    @BindView(R.id.spinner_type)
    Spinner mSpinnerType;
    @BindView(R.id.tv_date)
    TextView mTvDate;
    @BindView(R.id.iv_date)
    ImageView mIvDate;
    private WorkLogAdapter mLogAdapter;
    private List<WorkLogListBean> mListBean = new ArrayList();
    private SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private int pageIndex =1;
    @Override
    protected int setLayoutId() {
        return R.layout.fragment_work_log_preview;
    }

    @Override
    protected void initData() {
        super.initData();
        toRequest(ApiConstants.EventTags.reportjob_all);
    }


    @Override
    protected void initView(View view) {
        super.initView(view);
        mLogAdapter = new WorkLogAdapter(R.layout.item_work_log, mListBean);
        mRvList.setLayoutManager(new LinearLayoutManager(mActivity));
        mRvList.setAdapter(mLogAdapter);
        mLogAdapter.setEmptyView(R.layout.base_layout_empty,mRvList);
        mLogAdapter.isUseEmpty(true);
    }

    private void setViewData(List<WorkLogListBean> beanList) {
        if (pageIndex == 1) {
            mListBean.clear();
        }
        mListBean.addAll(beanList);
        mLogAdapter.notifyDataSetChanged();
    }
    @OnClick({R.id.tv_search, R.id.tv_date, R.id.iv_date})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_search:
                gotoActivity(WorkLogSearchActivity.class, 0);
                break;
            case R.id.tv_date:
            case R.id.iv_date:
                showPicker();
                break;
        }
    }

    private void showPicker() {
        TimePickerView pickerView = new TimePickerBuilder(mActivity, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd");
                mTvDate.setText(dateFormat.format(date));
                toRequest(ApiConstants.EventTags.reportjob_all);
            }
        })
                .setLineSpacingMultiplier(2).build();
        pickerView.show();
    }

    @Override
    public void toRequest(int eventTag) {
        super.toRequest(eventTag);
        Map map = new HashMap();
        map.put("token", "" + LoginUserUtil.getToken(mActivity));
        if (eventTag == ApiConstants.EventTags.reportjob_all) {
            map.put("code", "" + LoginUserUtil.getCurrentEnterpriseNo(mActivity));
            map.put("role","2");
            Logcat.i("------------" + eventTag + "/" + JSON.toJSONString(map));
            iCommonRequestPresenter.requestPost(eventTag, mActivity, ApiConstants.reportjob_all, map);
        }
    }

    @Override
    public void getRequestData(int eventTag, String result) {
        super.getRequestData(eventTag, result);
        Logcat.i("------------" + eventTag + "/" + result);
        ResultBean bean = JSON.parseObject(result, ResultBean.class);
        if (bean != null && bean.getCode() == 0) {
            if (eventTag == ApiConstants.EventTags.reportjob_all) {
                List<WorkLogListBean> beanList = JSON.parseArray(bean.getData(), WorkLogListBean.class);
                setViewData(beanList);
            }
        } else {
            ToastUtil.showToast(bean != null ? bean.getMsg() : "加载错误，请重试");
        }

    }

    @Override
    public void onRequestFailureException(int eventTag, String msg) {
        super.onRequestFailureException(eventTag, msg);
        Logcat.i("------------" + eventTag + "/" + msg);
    }


}
