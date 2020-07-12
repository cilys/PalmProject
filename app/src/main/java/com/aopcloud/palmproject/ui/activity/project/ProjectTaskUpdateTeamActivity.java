package com.aopcloud.palmproject.ui.activity.project;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.aopcloud.base.annotation.Layout;
import com.aopcloud.base.base.BaseActivity;
import com.aopcloud.base.util.ToastUtil;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.api.ApiConstants;
import com.aopcloud.palmproject.common.ResultBean;
import com.aopcloud.palmproject.ui.activity.project.bean.ProjectTaskDetailBean;
import com.aopcloud.palmproject.ui.activity.project.bean.ProjectTeamListBean;
import com.aopcloud.palmproject.ui.adapter.project.ProjectTaskUpdateTeamAdapter;
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
 * @File : ProjectTaskUpdateTeamActivity.java
 * @Date : 2020/6/8 2020/6/8
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
@Layout(R.layout.activity_project_task_team_update)
public class ProjectTaskUpdateTeamActivity extends BaseActivity implements TextView.OnEditorActionListener {
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
    @BindView(R.id.iv_del)
    ImageView mIvDel;
    @BindView(R.id.tv_team_name)
    TextView mTvTeamName;
    @BindView(R.id.tv_submit)
    TextView mTvSubmit;
    @BindView(R.id.rl_team)
    RelativeLayout mRlTeam;

    private int cuurentTeamId;


    private ProjectTaskUpdateTeamAdapter mAdapter;
    private List<ProjectTeamListBean> mBeanList = new ArrayList<>();
    private List<ProjectTeamListBean> mAllBeanList = new ArrayList<>();
    private ProjectTeamListBean mTeamListBean;
    private ProjectTaskDetailBean mTaskDetailBean;

    @Override
    protected void initData() {
        super.initData();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String json = bundle.getString("bean");
            mTaskDetailBean = JSON.parseObject(json, ProjectTaskDetailBean.class);
        }
        toRequest(ApiConstants.EventTags.project_team);
    }

    @Override
    protected void initView() {
        mTvHeaderTitle.setText("选择班组");
        mAdapter = new ProjectTaskUpdateTeamAdapter(R.layout.item_team_copy, mBeanList);
        mRvList.setLayoutManager(new LinearLayoutManager(this));
        mRvList.setAdapter(mAdapter);

        mEtSearch.setOnEditorActionListener(this::onEditorAction);
        mRlTeam.setVisibility(View.GONE);
    }


    @OnClick({R.id.ll_header_back, R.id.ll_header_right, R.id.iv_del, R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_header_back:
                finish();
                break;
            case R.id.ll_header_right:
                break;
            case R.id.iv_del:
                for (int i = 0; i < mBeanList.size(); i++) {
                    mBeanList.get(i).setSelect(false);
                }
                mAdapter.notifyDataSetChanged();
                mRlTeam.setVisibility(View.GONE);
                break;
            case R.id.tv_submit:
                if (mTeamListBean == null) {
                    ToastUtil.showToast("请选择班组");
                    return;
                }
                toRequest(ApiConstants.EventTags.task_assign);
                break;
        }
    }


    @Override
    protected void setListener() {
        super.setListener();
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                for (int i = 0; i < mBeanList.size(); i++) {
                    if (i == position) {
                        mBeanList.get(i).setSelect(!mBeanList.get(i).isSelect());
                        mTeamListBean = mBeanList.get(i).isSelect() ? mBeanList.get(i) : null;
                        if (mBeanList.get(i).isSelect()) {
                            mTvTeamName.setText("" + mBeanList.get(i).getTeam_name());
                        }
                        mRlTeam.setVisibility(mBeanList.get(i).isSelect() ? View.VISIBLE : View.GONE);
                    } else {
                        mBeanList.get(i).setSelect(false);
                    }
                }
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    private void setViewData(List<ProjectTeamListBean> beanList) {
        mBeanList.clear();
        mAllBeanList.clear();
        mAllBeanList.addAll(beanList);
        mBeanList.addAll(mAllBeanList);
        for (int i = 0; i < mBeanList.size(); i++) {
            if (mBeanList.get(i).getTeam_id() == cuurentTeamId) {
                mBeanList.get(i).setSelect(true);
            }
        }
        mAdapter.notifyDataSetChanged();
    }


    @Override
    public void toRequest(int eventTag) {
        super.toRequest(eventTag);
        Map map = new HashMap();
        map.put("token", "" + LoginUserUtil.getToken(this));
        map.put("code", "" + LoginUserUtil.getCurrentEnterpriseNo(this));
        if (eventTag == ApiConstants.EventTags.project_team) {
            map.put("project_id", "" + mTaskDetailBean.getProject_id());//项目名称
            iCommonRequestPresenter.requestPost(eventTag, this, ApiConstants.project_team, map);
        } else if (eventTag == ApiConstants.EventTags.task_assign) {
            map.put("task_id", "" + mTaskDetailBean.getTask_id());
            map.put("start_date", "" + mTaskDetailBean.getStart_date());
            map.put("end_date", "" + mTaskDetailBean.getEnd_date());
            map.put("level", "" + mTaskDetailBean.getLevel());
            map.put("team_id", "" + mTeamListBean.getTeam_id());
            iCommonRequestPresenter.requestPost(eventTag, this, ApiConstants.task_assign, map);
        }
    }

    @Override
    public void getRequestData(int eventTag, String result) {
        super.getRequestData(eventTag, result);
        ResultBean bean = JSON.parseObject(result, ResultBean.class);
        if (bean != null && bean.getCode() == 0) {
            if (eventTag == ApiConstants.EventTags.project_team) {
                List<ProjectTeamListBean> beanList = JSON.parseArray(bean.getData(), ProjectTeamListBean.class);
                setViewData(beanList);
            } else if (eventTag == ApiConstants.EventTags.task_assign) {
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

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        String s = mEtSearch.getText().toString();
        if (TextUtils.isEmpty(s)) {
            ToastUtil.showToast("请输入搜索内容");
        } else {
            mEtSearch.setText("");
            mEtSearch.clearFocus();
            mBeanList.clear();
            for (int i = 0; i < mAllBeanList.size(); i++) {
                if (mBeanList.get(i).getTeam_name().contains(s)) {
                    mBeanList.add(mAllBeanList.get(i));
                }
            }
            mAdapter.notifyDataSetChanged();
        }
        return true;
    }
}