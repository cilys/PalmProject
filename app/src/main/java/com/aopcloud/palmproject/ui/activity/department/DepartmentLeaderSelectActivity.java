package com.aopcloud.palmproject.ui.activity.department;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.aopcloud.base.annotation.Layout;
import com.aopcloud.base.base.BaseActivity;
import com.aopcloud.base.log.Logcat;
import com.aopcloud.base.util.ToastUtil;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.api.ApiConstants;
import com.aopcloud.palmproject.common.ResultBean;
import com.aopcloud.palmproject.ui.activity.department.bean.SelectDepartmentLeaderBean;
import com.aopcloud.palmproject.ui.adapter.department.DepartmentLeaderSelectAdapter;
import com.aopcloud.palmproject.ui.fragment.project.ImagesFragment;
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
 * @PackageName : com.aopcloud.palmproject.ui.activity.department
 * @File : DepartmentLeaderSelectActivity.java
 * @Date : 2020/5/11 2020/5/11
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
@Layout(R.layout.activity_rv)
public class DepartmentLeaderSelectActivity extends BaseActivity implements DepartmentLeaderSelectAdapter.OnItemClickListener {
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


    private DepartmentLeaderSelectAdapter mAdapter;

    private List<SelectDepartmentLeaderBean> mBeanList = new ArrayList();
   private String title;
    @Override
    protected void initData() {
        super.initData();
        Bundle bundle=getIntent().getExtras();
        if (bundle!=null){
            title = bundle.getString("title");
        }
        toRequest(ApiConstants.EventTags.company_usermange);
    }


    @Override
    protected void initView() {
        if (TextUtils.isEmpty(title)){
            mTvHeaderTitle.setText("人员选择");
        }else {
            mTvHeaderTitle.setText(title);
        }
        mTvHeaderRight.setText("确定");


        mAdapter = new DepartmentLeaderSelectAdapter(R.layout.item_deparment_select_leader, mBeanList);
        mAdapter.setOnItemClickListener(this);
        mRvList.setLayoutManager(new LinearLayoutManager(this));
        mRvList.setAdapter(mAdapter);

        mAdapter.setEmptyView(R.layout.base_layout_empty, mRvList);
        mAdapter.isUseEmpty(true);
    }

    private void setViewData(List<SelectDepartmentLeaderBean> beanList) {
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

                SelectDepartmentLeaderBean leaderBean = null;
                for (int i = 0; i < mBeanList.size(); i++) {
                    if (mBeanList.get(i).isSelect()) {
                        leaderBean = mBeanList.get(i);
                    }
                }
                if (null == leaderBean) {
                    ToastUtil.showToast("请选择部门领导人");
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString("user_id", "" + leaderBean.getUser_id());
                bundle.putString("user_name", "" + leaderBean.getName());
                Intent intent = new Intent();
                intent.putExtras(bundle);
                setResult(0, intent);
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
            map.put("status","1");//(可选)用户状态，-1:所有，0:审核中，1:正常，2:已拒绝，3:已离职，默认：-1
            iCommonRequestPresenter.requestPost(eventTag, this, ApiConstants.company_usermange, map);
        }
    }

    @Override
    public void getRequestData(int eventTag, String result) {
        super.getRequestData(eventTag, result);
        dismissPopupLoading();
        ResultBean bean = JSON.parseObject(result, ResultBean.class);
        if (bean != null && bean.getCode() == 0) {
            if (eventTag == ApiConstants.EventTags.company_usermange) {
                List<SelectDepartmentLeaderBean> beanList = JSON.parseArray(bean.getData(), SelectDepartmentLeaderBean.class);
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
