package com.aopcloud.palmproject.ui.activity.task;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.aopcloud.base.annotation.Layout;
import com.aopcloud.base.base.BaseActivity;
import com.aopcloud.base.util.ToastUtil;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.api.ApiConstants;
import com.aopcloud.palmproject.common.ResultBean;
import com.aopcloud.palmproject.ui.activity.project.ProjectTaskDiscussActivity;
import com.aopcloud.palmproject.ui.activity.task.bean.TaskTrendsBean;
import com.aopcloud.palmproject.ui.adapter.task.TaskTrendsAdapter;
import com.aopcloud.palmproject.utils.LoginUserUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import vip.devkit.view.common.dtextview.DrawableTextView;

/**
 * @PackageName : com.aopcloud.palmproject.ui.activity.task
 * @File : TaskTrendsActivity.java
 * @Date : 2020/5/21 2020/5/21
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
@Layout(R.layout.activity_task_trends)
public class TaskTrendsActivity extends BaseActivity {
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
    @BindView(R.id.tv_discuss)
    DrawableTextView mTvDiscuss;
    @BindView(R.id.rv_list)
    RecyclerView mRvList;


    private String task_id;

    private TaskTrendsAdapter mAdapter;
    private List<TaskTrendsBean> mBeanList = new ArrayList();

    @Override
    protected void initData() {
        super.initData();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            task_id = bundle.getString("task_id");
        }
        toRequest(ApiConstants.EventTags.trends_all);
    }

    @Override
    protected void initView() {
        mTvHeaderTitle.setText("动态");


        mAdapter = new TaskTrendsAdapter(R.layout.item_task_trends, mBeanList);
        mRvList.setBackgroundResource(R.color.theme_background_f5);
        mRvList.setLayoutManager(new LinearLayoutManager(this));
        mRvList.setAdapter(mAdapter);
        mAdapter.setEmptyView(R.layout.layout_common_empty, mRvList);
        mAdapter.isUseEmpty(true);
    }


    @OnClick({R.id.ll_header_back, R.id.ll_header_right, R.id.tv_discuss})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_header_back:
                finish();
                break;
            case R.id.ll_header_right:
                break;
            case R.id.tv_discuss:
                Bundle bundle = new Bundle();
                bundle.putString("task_id", "" + task_id);
                gotoActivity(ProjectTaskDiscussActivity.class, bundle, 0);
                break;
        }
    }

    private void setViewData(List<TaskTrendsBean> beanList) {
        mBeanList.clear();
        mBeanList.addAll(beanList);
        mAdapter.notifyDataSetChanged();
    }


    @Override
    public void toRequest(int eventTag) {
        super.toRequest(eventTag);
        Map map = new HashMap();
        map.put("token", "" + LoginUserUtil.getToken(this));
        map.put("code", "" + LoginUserUtil.getCurrentEnterpriseNo(this));
        if (eventTag == ApiConstants.EventTags.trends_all) {
            map.put("task_id", "" + task_id);//项目名称
            map.put("type", "-1");
            iCommonRequestPresenter.requestPost(eventTag, this, ApiConstants.trends_all, map);
        }
    }

    @Override
    public void getRequestData(int eventTag, String result) {
        super.getRequestData(eventTag, result);
        ResultBean bean = JSON.parseObject(result, ResultBean.class);
        if (bean != null && bean.getCode() == 0) {
            if (eventTag == ApiConstants.EventTags.trends_all) {
                List<TaskTrendsBean> beanList = JSON.parseArray(bean.getData(), TaskTrendsBean.class);
                setViewData(beanList);
            }
        } else {
            ToastUtil.showToast(bean != null ? bean.getMsg() : "加载错误，请重试");
        }

    }

    @Override
    public void onRequestFailureException(int eventTag, String msg) {
        super.onRequestFailureException(eventTag, msg);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        toRequest(ApiConstants.EventTags.trends_all);
    }
}
