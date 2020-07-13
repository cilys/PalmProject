package com.aopcloud.palmproject.ui.activity.staff;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.aopcloud.base.annotation.Layout;
import com.aopcloud.base.base.BaseActivity;
import com.aopcloud.base.util.ToastUtil;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.api.ApiConstants;
import com.aopcloud.palmproject.common.ResultBean;
import com.aopcloud.palmproject.ui.activity.staff.bean.StaffListBean;
import com.aopcloud.palmproject.ui.adapter.staff.StaffListAdapter;
import com.aopcloud.palmproject.utils.JumpActionUtil;
import com.aopcloud.palmproject.utils.LoginUserUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @PackageName : com.aopcloud.palmproject.ui.activity.staff
 * @File : StaffApprovalActivity.java
 * @Date : 2020/4/23 9:18
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @VersionCode : V 1.0
 * @Describe ：
 */
@Layout(R.layout.activity_staff_quit)
public class StaffQuitActivity extends BaseActivity {
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
    @BindView(R.id.rv_list)
    RecyclerView mRvList;


    private StaffListAdapter mAdapter;
    private List<StaffListBean> mBeanList = new ArrayList<>();

    @Override
    protected void initData() {
        super.initData();
        toRequest(ApiConstants.EventTags.company_usermange);
    }

    @Override
    protected void initView() {
        mTvHeaderTitle.setText("离退人员");
        mTvHeaderRight.setText("选择删除");
        mAdapter = new StaffListAdapter(R.layout.item_staff_home, mBeanList);
        mRvList.setLayoutManager(new LinearLayoutManager(this));
        mRvList.setAdapter(mAdapter);
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
                bundle.putString("user_id", "" + mBeanList.get(position).getUser_id());
                bundle.getString("status", "" + mBeanList.get(position).getStatus());
                bundle.putString("company_user_id", "" + mBeanList.get(position).getCompany_user_id());
                gotoActivity(StaffQuitDetailActivity.class,  bundle, 0);
            }
        });
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.iv_call) {
                    JumpActionUtil.jump2CallPhone(StaffQuitActivity.this,mBeanList.get(position).getTel()+"");
                } else if (view.getId() == R.id.iv_send_sms) {
                    JumpActionUtil.jump2SendSms(StaffQuitActivity.this,mBeanList.get(position).getTel()+"");

                }
            }
        });
    }

    @OnClick({R.id.ll_header_back, R.id.ll_header_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_header_back:
                finish();
                break;
            case R.id.ll_header_right:
                finish();
                break;
        }
    }

    @Override
    public void toRequest(int eventTag) {
        super.toRequest(eventTag);
        Map map = new HashMap();
        map.put("token", "" + LoginUserUtil.getToken(this));
        map.put("code", "" + LoginUserUtil.getCurrentEnterpriseNo(this));
        if (eventTag == ApiConstants.EventTags.company_usermange) {
            map.put("status", "" + 3);
            iCommonRequestPresenter.requestPost(eventTag, this, ApiConstants.company_usermange, map);
        }
    }

    @Override
    public void getRequestData(int eventTag, String result) {
        super.getRequestData(eventTag, result);
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
    }
}


