package com.aopcloud.palmproject.ui.activity.enterprise;

import android.os.Bundle;
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
import com.aopcloud.palmproject.common.MassageEvent;
import com.aopcloud.palmproject.common.ResultBean;
import com.aopcloud.palmproject.ui.activity.enterprise.bean.EnterpriseListBean;
import com.aopcloud.palmproject.ui.adapter.enterprise.SwitchEnterpriseAdapter;
import com.aopcloud.palmproject.utils.LoginUserUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * @PackageName : com.aopcloud.palmproject.ui.activity.enterprise
 * @File : SwitchEnterpriseActivity.java
 * @Date : 2020/4/20 2020/4/20
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
@Layout(R.layout.activity_enterprise_switch)
public class SwitchEnterpriseActivity extends BaseActivity {
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
    @BindView(R.id.ll_add_join)
    LinearLayout mLlAddJoin;

    private SwitchEnterpriseAdapter mEnterpriseAdapter;
    private List<EnterpriseListBean> mList = new ArrayList();

    private int defaultIndex=-1;
    @Override
    protected void initData() {
        super.initData();
        Bundle bundle =getIntent().getExtras();
        if (bundle!=null){
            defaultIndex =bundle.getInt("defaultIndex",-1);
        }
        toRequest(ApiConstants.EventTags.company_my);
    }

    @Override
    protected void initView() {
        mTvHeaderTitle.setText("切换企业");
        mEnterpriseAdapter = new SwitchEnterpriseAdapter(R.layout.item_enterprise_switch_list, mList);
        mRvList.setLayoutManager(new LinearLayoutManager(this));
        mRvList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRvList.setAdapter(mEnterpriseAdapter);

    }

    private void setViewData(List<EnterpriseListBean> beanList) {
        String code = "" + LoginUserUtil.getCurrentEnterpriseNo(this);
        for (int i = 0; i < beanList.size(); i++) {
            if (beanList.get(i).getCode().equals(code)) {
                beanList.get(i).setSelect(true);
            }
        }
        if (defaultIndex!=-1){
            beanList.get(0).setSelect(true);
        }
        mList.addAll(beanList);
        mEnterpriseAdapter.notifyDataSetChanged();
    }

    @Override
    protected void setListener() {
        super.setListener();
        mEnterpriseAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                for (int i = 0; i < mList.size(); i++) {
                    mList.get(i).setSelect(false);
                }
                mList.get(position).setSelect(true);
                LoginUserUtil.setCurrentEnterpriseNo(SwitchEnterpriseActivity.this, mList.get(position).getCode());
                LoginUserUtil.setCurrentEnterpriseBean(SwitchEnterpriseActivity.this, mList.get(position));
                mEnterpriseAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        EventBus.getDefault().post(new MassageEvent(MassageEvent.SWITCH_COMPANY));
        setResult(0);
        finish();
    }

    @OnClick({R.id.ll_header_back, R.id.ll_add_join})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_header_back:
                EventBus.getDefault().post(new MassageEvent(MassageEvent.SWITCH_COMPANY));
                setResult(0);
                finish();
                break;
            case R.id.ll_add_join:
                gotoActivity(CreateOrJoinActivity.class);
                break;
        }
    }

    @Override
    public void toRequest(int eventTag) {
        super.toRequest(eventTag);
        Map map = new HashMap();
        map.put("token", "" + LoginUserUtil.getToken(this));
        if (eventTag == ApiConstants.EventTags.company_my) {
            iCommonRequestPresenter.requestPost(eventTag, this, ApiConstants.company_my, map);
        }
    }

    @Override
    public void getRequestData(int eventTag, String result) {
        super.getRequestData(eventTag, result);
        dismissPopupLoading();
        ResultBean bean = JSON.parseObject(result, ResultBean.class);
        if (bean != null && bean.getCode() == 0) {
            if (eventTag == ApiConstants.EventTags.company_my) {
                List<EnterpriseListBean> beanList = JSON.parseArray(bean.getData(), EnterpriseListBean.class);
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
