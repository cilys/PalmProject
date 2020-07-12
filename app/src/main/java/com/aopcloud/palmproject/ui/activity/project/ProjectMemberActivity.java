package com.aopcloud.palmproject.ui.activity.project;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.aopcloud.base.annotation.Layout;
import com.aopcloud.base.base.BaseActivity;
import com.aopcloud.base.util.ResourceUtil;
import com.aopcloud.base.util.ToastUtil;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.api.ApiConstants;
import com.aopcloud.palmproject.common.ResultBean;
import com.aopcloud.palmproject.ui.activity.project.bean.ProjectMemberBean;
import com.aopcloud.palmproject.ui.adapter.project.ProjectMemberAdapter;
import com.aopcloud.palmproject.utils.JumpActionUtil;
import com.aopcloud.palmproject.utils.LoginUserUtil;
import com.aopcloud.palmproject.view.decoration.DividerItemDecoration;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @PackageName : com.aopcloud.palmproject.ui.activity.project
 * @File : ProjectManagerActivity.java
 * @Date : 2020/5/4 2020/5/4
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
@Layout(R.layout.activity_project_member)
public class ProjectMemberActivity extends BaseActivity implements ProjectMemberAdapter.OnItemClickListener, ProjectMemberAdapter.OnItemChildClickListener {


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
    @BindView(R.id.iv_qr_code)
    ImageView mIvQrCode;
    @BindView(R.id.tv_leader)
    TextView mTvLeader;
    @BindView(R.id.tv_leader_count)
    TextView mTvLeaderCount;
    @BindView(R.id.rl_leader)
    RelativeLayout mRlLeader;
    @BindView(R.id.tv_user)
    TextView mTvUser;
    @BindView(R.id.tv_users)
    TextView mTvUsers;
    @BindView(R.id.rl_user)
    RelativeLayout mRlUser;
    @BindView(R.id.tv_team)
    TextView mTvTeam;
    @BindView(R.id.tv_teams_count)
    TextView mTvTeamsCount;
    @BindView(R.id.rl_team)
    RelativeLayout mRlTeam;
    @BindView(R.id.tv_count)
    TextView mTvCount;
    @BindView(R.id.tv_project_name)
    TextView mTvProject;
    @BindView(R.id.rv_list)
    RecyclerView mRvList;


    private String project_id;
    private String project_name;
    private String project_user_id;
    private ProjectMemberAdapter mAdapter;
    private List mBeanList = new ArrayList();

    @Override
    protected void initData() {
        super.initData();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            project_id = bundle.getString("project_id");
            project_name = bundle.getString("project_name");
        }
        toRequest(ApiConstants.EventTags.project_member);
    }

    @Override
    protected void initView() {
        mTvHeaderTitle.setText("项目成员");
        mTvProject.setText("" + project_name);

        mAdapter = new ProjectMemberAdapter(R.layout.item_project_member, mBeanList);
        mAdapter.setOnItemChildClickListener(this);
        mAdapter.setOnItemClickListener(this);
        mRvList.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration itemDecoration = new DividerItemDecoration.Builder()
                .color(ResourceUtil.getColor(R.color.theme_background_f5))
                .height(2)
                .build();
        mRvList.addItemDecoration(itemDecoration);
        mRvList.setAdapter(mAdapter);
    }


    private void setViewData(ProjectMemberBean detailBean) {
        mTvTeamsCount.setText("(" + detailBean.getTeams().size() + "个)");

        Map map = new HashMap();

        List list = new ArrayList();
        List user = new ArrayList();
        for (int i = 0; i < detailBean.getUsers().size(); i++) {
            map.put(detailBean.getUsers().get(i).getUser_id(),"");
            //0,管理员，1：施工员
            if (detailBean.getUsers().get(i).getType() == 0) {
                list.add(detailBean.getUsers().get(i));
            } else if (detailBean.getUsers().get(i).getType() == 1) {
                user.add(detailBean.getUsers().get(i));
            }
        }
        for (int i = 0; i <detailBean.getTeams().size() ; i++) {
            for (int j = 0; j <detailBean.getTeams().get(i).getMember().size() ; j++) {
                map.put(detailBean.getTeams().get(i).getMember().get(j).getUser_id(),"");
            }
        }
        map.put(detailBean.getLeader().getUser_id(),"");
        mTvLeaderCount.setText("(" + list.size() + "人)");
        mTvUsers.setText("(" + user.size() + "人)");
        mTvCount.setText("全部成员(" + (map.size()) + "人)");
        mBeanList.clear();
        mBeanList.addAll(detailBean.getUsers());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ProjectMemberBean.UsersBean usersBean = (ProjectMemberBean.UsersBean) mBeanList.get(position);
        Bundle bundle = new Bundle();
        bundle.putString("project_id", "" + project_id);
        bundle.putString("user_id", "" + usersBean.getUser_id());
        gotoActivity(ProjectMemberDetailActivity.class, bundle, 0);

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        if (view.getId() == R.id.tv_del) {
            ProjectMemberBean.UsersBean usersBean = (ProjectMemberBean.UsersBean) mBeanList.get(position);
            project_user_id = usersBean.getProject_user_id() + "";
        } else if (view.getId() == R.id.iv_call) {
            ProjectMemberBean.UsersBean usersBean = (ProjectMemberBean.UsersBean) mBeanList.get(position);
            JumpActionUtil.jump2CallPhone(ProjectMemberActivity.this, usersBean.getTel() + "");
        } else if (view.getId() == R.id.iv_send_sms) {
            ProjectMemberBean.UsersBean usersBean = (ProjectMemberBean.UsersBean) mBeanList.get(position);
            JumpActionUtil.jump2SendSms(ProjectMemberActivity.this, usersBean.getTel() + "");
        }
    }


    @OnClick({R.id.ll_header_back, R.id.ll_header_right, R.id.iv_qr_code, R.id.rl_leader, R.id.rl_user, R.id.tv_team, R.id.rl_team})
    public void onViewClicked(View view) {
        Bundle bundle;
        switch (view.getId()) {
            case R.id.ll_header_back:
                finish();
                break;
            case R.id.ll_header_right:
                break;
            case R.id.iv_qr_code:
                break;
            case R.id.rl_leader:
                bundle = new Bundle();
                bundle.putString("project_id", "" + project_id);
                gotoActivity(ProjectLeaderListActivity.class, bundle, 0);
                break;
            case R.id.rl_user:
                bundle = new Bundle();
                bundle.putString("project_id", "" + project_id);
                gotoActivity(ProjectMemberListActivity.class, bundle, 0);
                break;
            case R.id.rl_team:
                bundle = new Bundle();
                bundle.putString("project_id", "" + project_id);
                gotoActivity(ProjectTeamListActivity.class, bundle, 0);
                break;
        }
    }

    @Override
    public void toRequest(int eventTag) {
        super.toRequest(eventTag);
        Map map = new HashMap();
        map.put("token", "" + LoginUserUtil.getToken(this));
        map.put("code", "" + LoginUserUtil.getCurrentEnterpriseNo(this));
        if (eventTag == ApiConstants.EventTags.project_member) {
            map.put("project_id", "" + project_id);//项目名称
            iCommonRequestPresenter.requestPost(eventTag, this, ApiConstants.project_member, map);
        } else if (eventTag == ApiConstants.EventTags.project_deluser) {
            map.put("project_user_id", "" + project_user_id);
            iCommonRequestPresenter.requestPost(eventTag, this, ApiConstants.project_deluser, map);
        }
    }

    @Override
    public void getRequestData(int eventTag, String result) {
        super.getRequestData(eventTag, result);
        ResultBean bean = JSON.parseObject(result, ResultBean.class);
        if (bean != null && bean.getCode() == 0) {
            if (eventTag == ApiConstants.EventTags.project_member) {
                ProjectMemberBean detailBean = JSON.parseObject(bean.getData(), ProjectMemberBean.class);
                setViewData(detailBean);
            }else if (eventTag == ApiConstants.EventTags.project_deluser) {
                toRequest(ApiConstants.EventTags.project_member);
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
        if (requestCode==0){
            toRequest(ApiConstants.EventTags.project_member);
        }
    }
}