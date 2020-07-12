package com.aopcloud.palmproject.ui.fragment.news;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.aopcloud.base.base.BaseFragment;
import com.aopcloud.base.common.BaseEvent;
import com.aopcloud.base.util.ToastUtil;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.api.ApiConstants;
import com.aopcloud.palmproject.common.ResultBean;
import com.aopcloud.palmproject.ui.activity.news.NotifyActivity;
import com.aopcloud.palmproject.ui.activity.news.adapter.NewsNotifyAdapter;
import com.aopcloud.palmproject.ui.activity.news.bean.WorkNotifyBean;
import com.aopcloud.palmproject.utils.LoginUserUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * @PackageName : com.aopcloud.basic.ui.fragment.news
 * @File : NewsTodoFragment.java
 * @Date : 2020/4/11 2020/4/11
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class NewsNotifyFragment extends BaseFragment implements BaseQuickAdapter.OnItemClickListener {
    @BindView(R.id.rv_list)
    RecyclerView mRvList;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mRefreshLayout;


    private NewsNotifyAdapter mAdapter;
    private List<WorkNotifyBean> mBeanList = new ArrayList<>();

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_rv;
    }

    @Override
    protected void initData() {
        super.initData();
        toRequest(ApiConstants.EventTags.msg_my);
    }

    @Override
    protected boolean isImmersionBarEnabled() {
        return false;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);


        mRefreshLayout.setRefreshHeader(new ClassicsHeader(mActivity));
        mRefreshLayout.setRefreshFooter(new ClassicsFooter(mActivity));


        mAdapter = new NewsNotifyAdapter(R.layout.item_news_notify, mBeanList);
        mAdapter.setOnItemClickListener(this::onItemClick);
        mRvList.setLayoutManager(new LinearLayoutManager(mActivity));
        mRvList.setAdapter(mAdapter);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        gotoActivity(NotifyActivity.class, 0);
    }

    @Override
    protected void setListener() {
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                toRequest(ApiConstants.EventTags.msg_my);
            }
        });
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
        map.put("token", "" + LoginUserUtil.getToken(mActivity));
        map.put("code", "" + LoginUserUtil.getCurrentEnterpriseNo(mActivity));
        if (eventTag == ApiConstants.EventTags.msg_my) {
            iCommonRequestPresenter.requestPost(eventTag, mActivity, ApiConstants.msg_my, map);
        }
    }

    @Override
    public void getRequestData(int eventTag, String result) {
        super.getRequestData(eventTag, result);
        mActivity.dismissPopupLoading();
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

    @Override
    public void onRequestFailureException(int eventTag, String msg) {
        super.onRequestFailureException(eventTag, msg);
        mRefreshLayout.finishRefresh();
    }

    @Override
    public void onEvent(BaseEvent postResult) {
        super.onEvent(postResult);
        if (postResult.type.equals(BaseEvent.EVENT_LOGIN)) {
            toRequest(ApiConstants.EventTags.msg_todo);
        }
    }
    OnLoadCountListener mOnLoadCountListener;

    public void setOnLoadCountListener(OnLoadCountListener onLoadCountListener) {
        mOnLoadCountListener = onLoadCountListener;
    }

    public interface OnLoadCountListener {
        void onLoadCount(int size);
    }


}
