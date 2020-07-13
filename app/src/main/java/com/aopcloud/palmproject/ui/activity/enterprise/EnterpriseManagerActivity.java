package com.aopcloud.palmproject.ui.activity.enterprise;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
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
import com.aopcloud.palmproject.ui.activity.enterprise.bean.EnterpriseManagerBean;
import com.aopcloud.palmproject.ui.adapter.enterprise.EnterpriseManagerAdapter;
import com.aopcloud.palmproject.utils.LoginUserUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @PackageName : com.aopcloud.palmproject.ui.activity.enterprise
 * @File : EnterpriseManagerActivity.java
 * @Date : 2020/4/20 2020/4/20
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
@Layout(R.layout.activity_enterprise_manager)
public class EnterpriseManagerActivity extends BaseActivity {

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
    @BindView(R.id.rl_add_manager)
    LinearLayout mRlAddManager;
    @BindView(R.id.tv_log)
    TextView mTvLog;

    private EnterpriseManagerAdapter mAdapter;
    private List<EnterpriseManagerBean> mBeanList = new ArrayList<>();

    @Override
    protected void initData() {
        super.initData();
        toRequest(ApiConstants.EventTags.manage_all);
    }

    @Override
    protected void initView() {
        mTvHeaderTitle.setText("系统管理员");
        mAdapter = new EnterpriseManagerAdapter(R.layout.item_enterprise_manger, mBeanList);
        mRvList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRvList.setLayoutManager(new LinearLayoutManager(this));
        mRvList.setAdapter(mAdapter);
    }

    @Override
    protected void setListener() {
        super.setListener();
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (!mBeanList.get(position).getRights().equals("all")){
                    Bundle bundle = new Bundle();
                    bundle.putString("info", JSON.toJSONString(mBeanList.get(position)));
                    gotoActivity(EnterpriseSettingManagerActivity.class,bundle,0);
                }
            }
        });
    }

    @OnClick({R.id.ll_header_back, R.id.rl_add_manager, R.id.tv_log})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_header_back:
                finish();
                break;
            case R.id.rl_add_manager:
                gotoActivity(EnterpriseSettingManagerActivity.class,0);
                break;
            case R.id.tv_log:
                ToastUtil.showToast("敬请期待");
                break;
        }
    }

    private void setViewData(List<EnterpriseManagerBean> beanList) {

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
        if (eventTag == ApiConstants.EventTags.manage_all) {
            iCommonRequestPresenter.requestPost(eventTag, this, ApiConstants.manage_all, map);
        }
    }

    @Override
    public void getRequestData(int eventTag, String result) {
        super.getRequestData(eventTag, result);
        dismissPopupLoading();
        ResultBean bean = JSON.parseObject(result, ResultBean.class);
        if (bean != null && bean.getCode() == 0) {
            if (eventTag == ApiConstants.EventTags.manage_all) {
                List<EnterpriseManagerBean> beanList = JSON.parseArray(bean.getData(),EnterpriseManagerBean.class);
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
        if (resultCode==0){
            toRequest(ApiConstants.EventTags.manage_all);
        }
    }
}
