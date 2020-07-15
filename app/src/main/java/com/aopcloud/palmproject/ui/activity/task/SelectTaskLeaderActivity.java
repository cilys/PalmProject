package com.aopcloud.palmproject.ui.activity.task;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.aopcloud.base.annotation.Layout;
import com.aopcloud.base.util.ToastUtil;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.api.ApiConstants;
import com.aopcloud.palmproject.common.ResultBean;
import com.aopcloud.palmproject.ui.activity.BaseAc;
import com.aopcloud.palmproject.ui.activity.team.bean.TeamMemberBean;
import com.aopcloud.palmproject.ui.adapter.task.SelectTaskLeaderAdapter;
import com.aopcloud.palmproject.utils.LoginUserUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @PackageName : com.aopcloud.palmproject.ui.activity.task
 * @File : SelectTaskLeaderActivity.java
 * @Date : 2020/6/27 2020/6/27
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
@Layout(R.layout.activity_task_member_select)
public class SelectTaskLeaderActivity extends BaseAc implements SelectTaskLeaderAdapter.OnItemClickListener {
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
    @BindView(R.id.tv_submit)
    TextView mTvSubmit;


    private SelectTaskLeaderAdapter mAdapter;
    private List<TeamMemberBean> mBeanList = new ArrayList<>();
    private String team_id;
    private String title;

    @Override
    protected void initData() {
        super.initData();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            team_id = bundle.getString("team_id");
            title = bundle.getString("title", "人员选择");
        }
        toRequest(ApiConstants.EventTags.teammember_all);
    }

    @Override
    protected void initView() {
        mTvHeaderTitle.setText("" + title);
        mAdapter = new SelectTaskLeaderAdapter(R.layout.item_task_select_leader, mBeanList);
        mAdapter.setOnItemClickListener(this::onItemClick);
        mRvList.setLayoutManager(new LinearLayoutManager(this));
        mRvList.setAdapter(mAdapter);
        mTvHeaderRight.setText("确定");
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (title.contains("负责人")) {
            for (int i = 0; i < mBeanList.size(); i++) {
                if (i == position) {
                    mBeanList.get(i).setSelect(!mBeanList.get(i).isSelect());
                } else {
                    mBeanList.get(i).setSelect(false);
                }
            }
        } else {
            mBeanList.get(position).setSelect(!mBeanList.get(position).isSelect());
        }
        mAdapter.notifyDataSetChanged();
    }

    @OnClick({R.id.ll_header_back, R.id.ll_header_right, R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_header_back:
                finish();
                break;
            case R.id.ll_header_right:
            case R.id.tv_submit:
                List list = new ArrayList();
                for (int i = 0; i < mBeanList.size(); i++) {
                    if (mBeanList.get(i).isSelect()) {
                        list.add(mBeanList.get(i));
                    }
                }
                if (list.size() == 0) {
                    ToastUtil.showToast("请选择人员");
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString("json", "" + JSON.toJSON(list));
                Intent intent = new Intent();
                intent.putExtras(bundle);
                setResult(0, intent);
                finish();
                break;
        }
    }

    private void setViewData(List<TeamMemberBean> beanList) {
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
        map.put("team_id", "" + team_id);//项目名称
        if (eventTag == ApiConstants.EventTags.teammember_all) {
            iCommonRequestPresenter.requestPost(eventTag, this, ApiConstants.teammember_all, map);
        }
    }

    @Override
    public void getRequestData(int eventTag, String result) {
        super.getRequestData(eventTag, result);
        ResultBean bean = JSON.parseObject(result, ResultBean.class);
        if (bean != null && bean.getCode() == 0) {
            if (eventTag == ApiConstants.EventTags.teammember_all) {
                List<TeamMemberBean> beanList = JSON.parseArray(bean.getData(), TeamMemberBean.class);
                setViewData(beanList);
            } else if (eventTag == ApiConstants.EventTags.teammember_del) {
                toRequest(ApiConstants.EventTags.teammember_all);
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
