package com.aopcloud.palmproject.ui.activity.project;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.aopcloud.base.annotation.Layout;
import com.aopcloud.base.util.ToastUtil;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.api.ApiConstants;
import com.aopcloud.palmproject.common.ResultBean;
import com.aopcloud.palmproject.ui.activity.BaseAc;
import com.aopcloud.palmproject.ui.activity.project.bean.ProjectTrendsBean;
import com.aopcloud.palmproject.ui.adapter.project.ProjectProgressAdapter;
import com.aopcloud.palmproject.utils.LoginUserUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @PackageName : com.aopcloud.palmproject.ui.activity.project
 * @File : ProjectProgressActivity.java
 * @Date : 2020/5/3 2020/5/3
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
@Layout(R.layout.activity_project_progress)
public class ProjectProgressActivity extends BaseAc implements ProjectProgressAdapter.OnItemChildClickListener {
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
    @BindView(R.id.rv_list)
    RecyclerView mRvList;
    @BindView(R.id.tv_submit)
    TextView mTvSubmit;


    private ProjectProgressAdapter mAdapter;

    private List<ProjectTrendsBean> mBeanList = new ArrayList();

    private String project_id;

    @Override
    protected void initData() {
        super.initData();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            project_id = bundle.getString("project_id");
        }
        toRequest(ApiConstants.EventTags.trends_all);
    }

    @Override
    protected void initView() {
        mTvHeaderTitle.setText("项目进度");
        mAdapter = new ProjectProgressAdapter(R.layout.item_project_progress, mBeanList);
        mAdapter.setOnItemChildClickListener(this);
        mRvList.setLayoutManager(new LinearLayoutManager(this));
        mRvList.setAdapter(mAdapter);
    }

    private void setViewData(List<ProjectTrendsBean> beanList) {

        mBeanList.clear();
        mBeanList.addAll(beanList);
        mAdapter.notifyDataSetChanged();

    }

    @OnClick({R.id.ll_header_back, R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_header_back:
                finish();
                break;
            case R.id.tv_submit:
                Bundle bundle = new Bundle();
                bundle.putString("project_id", "" + project_id);
                if (mBeanList.size()>0){
                    bundle.putLong("last_time", mBeanList.get(0).getMake_time());
                }
                gotoActivity(UpdateProjectProgressActivity.class, bundle, 0);
                break;
        }
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        if (view.getId() == R.id.iv_edit) {
            Bundle bundle = new Bundle();
            bundle.putString("project_id", "" + project_id);
            gotoActivity(UpdateProjectProgressActivity.class, bundle, 0);
        }
    }

    @Override
    public void toRequest(int eventTag) {
        super.toRequest(eventTag);
        Map map = new HashMap();
        map.put("token", "" + LoginUserUtil.getToken(this));
        map.put("code", "" + LoginUserUtil.getCurrentEnterpriseNo(this));
        map.put("project_id", "" + project_id);//项目名称
        map.put("type", "0");//项目名称
        if (eventTag == ApiConstants.EventTags.trends_all) {
            iCommonRequestPresenter.requestPost(eventTag, this, ApiConstants.trends_all, map);
        }
    }

    @Override
    public void getRequestData(int eventTag, String result) {
        super.getRequestData(eventTag, result);
        ResultBean bean = JSON.parseObject(result, ResultBean.class);
        if (bean != null && bean.getCode() == 0) {
            if (eventTag == ApiConstants.EventTags.trends_all) {
                List<ProjectTrendsBean> beanList = JSON.parseArray(bean.getData(), ProjectTrendsBean.class);
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        initData();
        initView();
    }
}
