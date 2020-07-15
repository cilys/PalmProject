package com.aopcloud.palmproject.ui.activity.enterprise;

import android.os.Bundle;
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
import com.aopcloud.palmproject.ui.activity.enterprise.bean.EnterpriseManagerBean;
import com.aopcloud.palmproject.ui.adapter.department.DepartmentLeaderSelectAdapter;
import com.aopcloud.palmproject.ui.adapter.enterprise.TransferEnterpriseAdapter;
import com.aopcloud.palmproject.utils.LoginUserUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @PackageName : com.aopcloud.palmproject.ui.activity.enterprise
 * @File : TransferEnterpriseActivity.java
 * @Date : 2020/5/31 2020/5/31
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
@Layout(R.layout.activity_rv)
public class TransferEnterpriseActivity extends BaseAc implements DepartmentLeaderSelectAdapter.OnItemClickListener {
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
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mRefreshLayout;


    private TransferEnterpriseAdapter mAdapter;

    private List<EnterpriseManagerBean> mBeanList = new ArrayList();
    private String title;

    @Override
    protected void initData() {
        super.initData();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            title = bundle.getString("title");
        }
        toRequest(ApiConstants.EventTags.manage_all);
    }


    @Override
    protected void initView() {
        mTvHeaderTitle.setText("请选择负责人");
        mTvHeaderRight.setText("确定");


        mAdapter = new TransferEnterpriseAdapter(R.layout.item_deparment_select_leader, mBeanList);
        mAdapter.setOnItemClickListener(this);
        mRvList.setLayoutManager(new LinearLayoutManager(this));
        mRvList.setAdapter(mAdapter);

        mAdapter.setEmptyView(R.layout.base_layout_empty, mRvList);
        mAdapter.isUseEmpty(true);
    }

    private void setViewData(List<EnterpriseManagerBean> beanList) {
        mBeanList.clear();
        mBeanList.addAll(beanList);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        for (int i = 0; i < mBeanList.size(); i++) {
            if (i == position) {
                mBeanList.get(i).setSelect(!mBeanList.get(i).isSelect());
            } else {
                mBeanList.get(i).setSelect(false);
            }
            mAdapter.notifyDataSetChanged();
        }
    }


    @OnClick({R.id.ll_header_back, R.id.ll_header_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_header_back:
                finish();
                break;
            case R.id.ll_header_right:
                for (int i = 0; i < mBeanList.size(); i++) {
                    if (mBeanList.get(i).isSelect()) {
                        leaderBean = mBeanList.get(i);
                    }
                }
                if (null == leaderBean) {
                    ToastUtil.showToast("请选择负责人");
                    return;
                }
                toRequest(ApiConstants.EventTags.company_update);
                break;
        }
    }

    private EnterpriseManagerBean leaderBean = null;

    @Override
    public void toRequest(int eventTag) {
        super.toRequest(eventTag);
        Map params = new HashMap();
        params.put("token", "" + LoginUserUtil.getToken(this));
        params.put("code", "" + LoginUserUtil.getCurrentEnterpriseNo(this));
        if (eventTag == ApiConstants.EventTags.manage_all) {
            iCommonRequestPresenter.requestPost(eventTag, this, ApiConstants.manage_all, params);
        } else if (eventTag == ApiConstants.EventTags.company_update) {
            params.put("leader_id", "" + leaderBean.getUser_id());
            iCommonRequestPresenter.requestPost(eventTag, this, ApiConstants.company_update, params);
        }
    }

    @Override
    public void getRequestData(int eventTag, String result) {
        super.getRequestData(eventTag, result);
        dismissPopupLoading();
        ResultBean bean = JSON.parseObject(result, ResultBean.class);
        if (bean != null && bean.getCode() == 0) {
            if (eventTag == ApiConstants.EventTags.manage_all) {
                List<EnterpriseManagerBean> beanList = JSON.parseArray(bean.getData(), EnterpriseManagerBean.class);
                setViewData(beanList);
            } else if (eventTag == ApiConstants.EventTags.company_update) {
                setResult(0);
                finish();
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
