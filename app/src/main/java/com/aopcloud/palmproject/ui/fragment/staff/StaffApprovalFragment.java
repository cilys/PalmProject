package com.aopcloud.palmproject.ui.fragment.staff;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.aopcloud.base.base.BaseFragment;
import com.aopcloud.base.log.Logcat;
import com.aopcloud.base.util.ResourceUtil;
import com.aopcloud.base.util.ToastUtil;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.api.ApiConstants;
import com.aopcloud.palmproject.common.ResultBean;
import com.aopcloud.palmproject.ui.activity.staff.StaffRefuseApplyDetailActivity;
import com.aopcloud.palmproject.ui.activity.staff.StaffApprovalDetailActivity;
import com.aopcloud.palmproject.ui.activity.staff.bean.StaffListBean;
import com.aopcloud.palmproject.ui.adapter.staff.StaffApprovalAdapter;
import com.aopcloud.palmproject.utils.LoginUserUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * @PackageName : com.aopcloud.palmproject.ui.fragment.worker
 * @File : StaffApprovalFragment.java
 * @Date : 2020/4/23 9:23
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @VersionCode : V 1.0
 * @Describe ：
 */
public class StaffApprovalFragment extends BaseFragment {

    @BindView(R.id.rv_list)
    RecyclerView mRvList;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mRefreshLayout;


    private StaffApprovalAdapter mAdapter;
    private List<StaffListBean> mBeanList = new ArrayList();
    private String status;


    public static StaffApprovalFragment getInstance(String status) {

        StaffApprovalFragment fragment = new StaffApprovalFragment();
        Bundle bundle = new Bundle();
        bundle.putString("status", "" + status);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initData() {
        super.initData();
        Bundle bundle = getArguments();
        status = bundle.getString("status");
        toRequest(ApiConstants.EventTags.company_usermange);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_rv;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);

        mRefreshLayout.setEnableRefresh(false);
        mRefreshLayout.setEnableLoadMore(false);

        mAdapter = new StaffApprovalAdapter(R.layout.item_staff_approval, mBeanList);
        mRvList.setLayoutManager(new LinearLayoutManager(mActivity));
        mRvList.addItemDecoration(new DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL));
        mRvList.setAdapter(mAdapter);
        mRvList.setBackgroundColor(ResourceUtil.getColor(R.color.theme_background_f5));

        mAdapter.setEmptyView(R.layout.base_layout_empty, mRvList);
        mAdapter.isUseEmpty(true);
    }

    private void setViewData(List<StaffListBean> beanList) {

        mBeanList.clear();
        mBeanList.addAll(beanList);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void setListener() {
        super.setListener();
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("StaffListBean",JSON.toJSONString(mBeanList.get(position)));
                bundle.putString("user_id", "" + mBeanList.get(position).getUser_id());
                bundle.getString("status", "" + mBeanList.get(position).getStatus());
                bundle.putString("company_user_id", "" + mBeanList.get(position).getCompany_user_id());
                if (mBeanList.get(position).getStatus() == 2) {
                    gotoActivity(StaffRefuseApplyDetailActivity.class, 0, bundle);
                } else {
                    gotoActivity(StaffApprovalDetailActivity.class, 0, bundle);
                }
            }
        });
    }

    @Override
    public void toRequest(int eventTag) {
        super.toRequest(eventTag);
        Map map = new HashMap();
        map.put("token", "" + LoginUserUtil.getToken(mActivity));
        map.put("code", "" + LoginUserUtil.getCurrentEnterpriseNo(mActivity));
        if (eventTag == ApiConstants.EventTags.company_usermange) {
            map.put("status", "" + status);
            Logcat.i("------------" + eventTag + "/" + JSON.toJSONString(map));
            iCommonRequestPresenter.requestPost(eventTag, mActivity, ApiConstants.company_usermange, map);
        }
    }

    @Override
    public void getRequestData(int eventTag, String result) {
        super.getRequestData(eventTag, result);
        Logcat.i("------------" + eventTag + "/" + result);
        ResultBean bean = JSON.parseObject(result, ResultBean.class);
        if (bean != null && bean.getCode() == 0) {
            if (eventTag == ApiConstants.EventTags.company_usermange) {
                List<StaffListBean> beanList = JSON.parseArray(bean.getData(), StaffListBean.class);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        toRequest( ApiConstants.EventTags.company_usermange);
    }
}

