package com.aopcloud.palmproject.ui.activity.team;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.aopcloud.base.annotation.Layout;
import com.aopcloud.base.util.ResourceUtil;
import com.aopcloud.base.util.ToastUtil;
import com.aopcloud.base.util.ViewUtil;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.api.ApiConstants;
import com.aopcloud.palmproject.common.ResultBean;
import com.aopcloud.palmproject.ui.activity.BaseAc;
import com.aopcloud.palmproject.ui.activity.project.ProjectMemberDetailActivity;
import com.aopcloud.palmproject.ui.activity.team.bean.TeamMemberBean;
import com.aopcloud.palmproject.ui.adapter.team.TeamMemberAdapter;
import com.aopcloud.palmproject.utils.LoginUserUtil;
import com.aopcloud.palmproject.view.decoration.HorizontalDividerItemDecoration;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @PackageName : com.aopcloud.palmproject.ui.activity.team
 * @File : TeamMemberListActivity.java
 * @Date : 2020/5/16 2020/5/16
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
@Layout(R.layout.activity_team_member)
public class TeamMemberListActivity extends BaseAc {
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
    @BindView(R.id.ll_add)
    LinearLayout mLlAdd;
    @BindView(R.id.rv_list)
    RecyclerView mRvList;

    private String team_id;
    private String name;
    private String industry;

    private String user_ids;

    private TeamMemberAdapter mAdapter;
    private List<TeamMemberBean> mBeanList = new ArrayList<>();

    @Override
    protected void initData() {
        super.initData();
        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            team_id = bundle.getString("team_id");
            name = bundle.getString("name");
            industry = bundle.getString("industry");
        }
        toRequest(ApiConstants.EventTags.teammember_all);
    }

    @Override
    protected void initView() {
        mTvHeaderTitle.setText("" + name);
        mTvHeaderRight.setText("设置");

        mAdapter = new TeamMemberAdapter(R.layout.item_team_member, mBeanList);
        HorizontalDividerItemDecoration decoration = new HorizontalDividerItemDecoration.Builder(this)
                .margin(0)
                .size(ViewUtil.dp2px(this, 2))
                .color(ResourceUtil.getColor(R.color.transparent))
                .build();
        mRvList.addItemDecoration(decoration);
        mRvList.setLayoutManager(new LinearLayoutManager(this));
        mRvList.setAdapter(mAdapter);
    }

    @Override
    protected void setListener() {
        super.setListener();
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.tv_edit) {
                    Bundle   bundle = new Bundle();
                    bundle.putString("team_id", "" + team_id);
                    bundle.putString("TeamMemberBean", "" +JSON.toJSONString(mBeanList.get(position)));
                    gotoActivity(TeamEditMemberActivity.class, bundle, 0);
                } else if (view.getId() == R.id.tv_del) {
                    user_ids = mBeanList.get(position).getUser_id()+"";
                    toRequest(ApiConstants.EventTags.teammember_del);
                } else if (view.getId() == R.id.item_content) {
                    Bundle bundle = new Bundle();
                    bundle.putString("project_id", "" + "");
                    bundle.putString("team_id", "" + team_id);
                    bundle.putString("user_id", "" + mBeanList.get(position).getUser_id());
                    gotoActivity(ProjectMemberDetailActivity.class, bundle, 0);
                }
            }
        });
    }

    @OnClick({R.id.ll_header_back, R.id.ll_header_right, R.id.ll_add})
    public void onViewClicked(View view) {
        Bundle bundle;
        switch (view.getId()) {
            case R.id.ll_header_back:
                setResult(0);
                finish();
                break;
            case R.id.ll_header_right:
                bundle = new Bundle();
                bundle.putString("team_id", "" + team_id);
                bundle.putString("name", "" + name);
                bundle.putString("industry", "" + industry);
                gotoActivity(TeamEditActivity.class, bundle, 0);
                break;
            case R.id.ll_add:
                bundle = new Bundle();
                bundle.putString("team_id", "" + team_id);
                gotoActivity(TeamAddMemberActivity.class, bundle, 0);
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
        }if (eventTag == ApiConstants.EventTags.teammember_del) {
            map.put("user_ids", "" + user_ids);//项目名称
            iCommonRequestPresenter.requestPost(eventTag, this, ApiConstants.teammember_del, map);
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
            }else if (eventTag==ApiConstants.EventTags.teammember_del){
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1) {
            setResult(0);
            finish();
        }
        if (requestCode == 0) {
            toRequest(ApiConstants.EventTags.teammember_all);
        }
    }
}
