package com.aopcloud.palmproject.ui.activity.team;

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
import com.aopcloud.base.util.ToastUtil;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.api.ApiConstants;
import com.aopcloud.palmproject.common.ResultBean;
import com.aopcloud.palmproject.ui.activity.project.bean.ProjectTeamListBean;
import com.aopcloud.palmproject.ui.adapter.team.ProjectCopyTeamAdapter;
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
 * @PackageName : com.aopcloud.palmproject.ui.activity.team
 * @File : ProjectCopyTeamActivity.java
 * @Date : 2020/6/6 2020/6/6
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
@Layout(R.layout.activity_rv_bottom)
public class ProjectCopyTeamActivity extends BaseActivity {
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
    @BindView(R.id.tv_submit)
    TextView mTvSubmit;


    private String project_id;
    private String copy_team_id;

    private ProjectCopyTeamAdapter mAdapter;
    private List<ProjectTeamListBean> mBeanList = new ArrayList<>();

    @Override
    protected void initData() {
        super.initData();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            project_id = bundle.getString("project_id");
        }
        toRequest(ApiConstants.EventTags.company_team);

    }

    @Override
    protected void initView() {
        mTvHeaderTitle.setText("选择班组导入");

        mAdapter = new ProjectCopyTeamAdapter(R.layout.item_team_copy, mBeanList);
        mRvList.setLayoutManager(new LinearLayoutManager(this));
        mRvList.setAdapter(mAdapter);
    }

    @Override
    protected void setListener() {
        super.setListener();
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                for (int i = 0; i <mBeanList.size() ; i++) {
                    if (i==position){
                        mBeanList.get(i).setSelect(!mBeanList.get(i).isSelect());
                    }else {
                        mBeanList.get(i).setSelect(false);
                    }
                }
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @OnClick({R.id.ll_header_back, R.id.tv_header_right, R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_header_back:
                finish();
                break;
            case R.id.tv_header_right:
                break;
            case R.id.tv_submit:
                for (int i = 0; i < mBeanList.size(); i++) {
                    if (mBeanList.get(i).isSelect()) {
                        copy_team_id = mBeanList.get(i).getTeam_id() + "";
                    }
                }
                if (TextUtils.isEmpty(copy_team_id)) {
                    ToastUtil.showToast("请选择班组");
                    return;
                }
                toRequest(ApiConstants.EventTags.team_copy);
                break;
        }
    }


    private void setViewData(List<ProjectTeamListBean> beanList) {
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
        if (eventTag == ApiConstants.EventTags.company_team) {
            iCommonRequestPresenter.requestPost(eventTag, this, ApiConstants.company_team, map);
        } else if (eventTag == ApiConstants.EventTags.team_copy) {
            map.put("project_id", "" + project_id);
            map.put("copy_team_id", "" + copy_team_id);
            iCommonRequestPresenter.requestPost(eventTag, this, ApiConstants.team_copy, map);
        }
    }

    @Override
    public void getRequestData(int eventTag, String result) {
        super.getRequestData(eventTag, result);
        ResultBean bean = JSON.parseObject(result, ResultBean.class);
        if (bean != null && bean.getCode() == 0) {
            if (eventTag == ApiConstants.EventTags.company_team) {
                List<ProjectTeamListBean> beanList = JSON.parseArray(bean.getData(), ProjectTeamListBean.class);
                setViewData(beanList);
            } else if (eventTag == ApiConstants.EventTags.team_copy) {
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
