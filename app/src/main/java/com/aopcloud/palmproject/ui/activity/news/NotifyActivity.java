package com.aopcloud.palmproject.ui.activity.news;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.aopcloud.palmproject.ui.activity.news.adapter.WorkNotifyAdapter;
import com.aopcloud.palmproject.ui.activity.news.bean.WorkNotifyBean;
import com.aopcloud.palmproject.utils.LoginUserUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @PackageName : com.aopcloud.basic.ui.activity.news
 * @File : NotifyActivity.java
 * @Date : 2020/4/11 2020/4/11
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
@Layout(R.layout.activity_rv)
public class NotifyActivity extends BaseActivity {
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

    private WorkNotifyAdapter mAdapter;
    private List<WorkNotifyBean> mBeanList = new ArrayList<>();

    @Override
    protected void initData() {
        super.initData();
        toRequest(ApiConstants.EventTags.msg_my);
    }

    @Override
    protected void initView() {
        mAdapter = new WorkNotifyAdapter(R.layout.item_news_work_notify, mBeanList);
        mRvList.setLayoutManager(new LinearLayoutManager(this));
        mRvList.setAdapter(mAdapter);
    }

    @Override
    protected View setContentLayout() {
        return mRvList;
    }

    @OnClick({R.id.ll_header_back, R.id.ll_header_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_header_back:
                finish();
                break;
            case R.id.ll_header_right:
                break;
        }
    }

    private void setViewData(List<WorkNotifyBean> beanList) {
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
        if (eventTag == ApiConstants.EventTags.msg_my) {
            Logcat.i("------------" + eventTag + "/" + JSON.toJSONString(map));
            iCommonRequestPresenter.requestPost(eventTag, this, ApiConstants.msg_my, map);
        }
    }

    @Override
    public void getRequestData(int eventTag, String result) {
        super.getRequestData(eventTag, result);
        Logcat.i("------------" + eventTag + "/" + result);
        dismissPopupLoading();
        ResultBean bean = JSON.parseObject(result, ResultBean.class);
        if (bean != null && bean.getCode() == 0) {
            if (eventTag == ApiConstants.EventTags.msg_my) {
                List<WorkNotifyBean> beanList = JSON.parseArray(bean.getData(), WorkNotifyBean.class);
                setViewData(beanList);
            }
        } else {
            ToastUtil.showToast(bean != null ? bean.getMsg() : "加载错误，请重试");
            mRefreshLayout.finishRefresh();
        }

    }
}