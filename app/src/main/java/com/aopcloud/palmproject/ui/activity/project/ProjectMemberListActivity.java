package com.aopcloud.palmproject.ui.activity.project;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.aopcloud.base.annotation.Layout;
import com.aopcloud.base.util.ResourceUtil;
import com.aopcloud.base.util.ToastUtil;
import com.aopcloud.base.util.ViewUtil;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.api.ApiConstants;
import com.aopcloud.palmproject.common.ResultBean;
import com.aopcloud.palmproject.ui.activity.BaseAc;
import com.aopcloud.palmproject.ui.activity.department.DepartmentLeaderSelectActivity;
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
import butterknife.OnClick;
import vip.devkit.view.common.dtextview.DrawableTextView;

/**
 * @PackageName : com.aopcloud.palmproject.ui.activity.project
 * @File : ProjectManagerActivity.java
 * @Date : 2020/5/4 2020/5/4
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
@Layout(R.layout.activity_project_member_list)
public class ProjectMemberListActivity extends BaseAc implements ProjectMemberAdapter.OnItemChildClickListener, ProjectMemberAdapter.OnItemClickListener {
    @BindView(R.id.ll_header_back)
    LinearLayout mLlHeaderBack;
    @BindView(R.id.tv_header_title)
    DrawableTextView mTvHeaderTitle;
    @BindView(R.id.iv_header_more)
    ImageView mIvHeaderMore;
    @BindView(R.id.ll_header_right)
    LinearLayout mLlHeaderRight;
    @BindView(R.id.rl_common_layout)
    RelativeLayout mRlCommonLayout;
    @BindView(R.id.ll_add)
    LinearLayout mLlAdd;
    @BindView(R.id.rv_list)
    RecyclerView mRvList;


    private String project_id;
    private String project_name;
    private String project_user_id;
    private String user_id;
    private ProjectMemberAdapter mAdapter;
    private List mBeanList = new ArrayList();

    @Override
    protected void initData() {
        super.initData();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            project_id = bundle.getString("project_id");
        }
        toRequest(ApiConstants.EventTags.project_member);
    }

    @Override
    protected void initView() {
        mTvHeaderTitle.setText("施工员");

        mAdapter = new ProjectMemberAdapter(R.layout.item_project_member, mBeanList);
        mAdapter.setOnItemChildClickListener(this);
        mAdapter.setOnItemClickListener(this);
        DividerItemDecoration itemDecoration = new DividerItemDecoration.Builder()
                .color(ResourceUtil.getColor(R.color.theme_background_f5))
                .width(1)
                .build();
        mRvList.addItemDecoration(itemDecoration);
        mRvList.setLayoutManager(new LinearLayoutManager(this));
        mRvList.setAdapter(mAdapter);
    }


    private void setViewData(ProjectMemberBean detailBean) {


        List list = new ArrayList();
        for (int i = 0; i < detailBean.getUsers().size(); i++) {
            //0,管理员，1：施工员
            if (detailBean.getUsers().get(i).getType() == 1) {
                list.add(detailBean.getUsers().get(i));
            }
        }

        mBeanList.clear();
        mBeanList.addAll(list);
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
            toRequest(ApiConstants.EventTags.project_deluser);
        } else if (view.getId() == R.id.iv_call) {
            ProjectMemberBean.UsersBean usersBean = (ProjectMemberBean.UsersBean) mBeanList.get(position);
            JumpActionUtil.jump2CallPhone(ProjectMemberListActivity.this, usersBean.getTel() + "");
        } else if (view.getId() == R.id.iv_send_sms) {
            ProjectMemberBean.UsersBean usersBean = (ProjectMemberBean.UsersBean) mBeanList.get(position);
            JumpActionUtil.jump2SendSms(ProjectMemberListActivity.this, usersBean.getTel() + "");
        }
    }

    @Override
    public void toRequest(int eventTag) {
        super.toRequest(eventTag);
        Map map = new HashMap();
        map.put("token", "" + LoginUserUtil.getToken(this));
        map.put("code", "" + LoginUserUtil.getCurrentEnterpriseNo(this));
        map.put("project_id", "" + project_id);//项目名称
        if (eventTag == ApiConstants.EventTags.project_member) {
            iCommonRequestPresenter.requestPost(eventTag, this, ApiConstants.project_member, map);
        } else if (eventTag == ApiConstants.EventTags.project_adduser) {
            map.put("user_id", "" + user_id);
            map.put("type", "" + 1);
            iCommonRequestPresenter.requestPost(eventTag, this, ApiConstants.project_adduser, map);
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
            } else if (eventTag == ApiConstants.EventTags.project_adduser) {
                toRequest(ApiConstants.EventTags.project_member);
            } else if (eventTag == ApiConstants.EventTags.project_deluser) {
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


    @OnClick({R.id.ll_header_back, R.id.tv_header_title, R.id.ll_header_right, R.id.ll_add})
    public void onViewClicked(View view) {
        Bundle bundle;
        switch (view.getId()) {
            case R.id.ll_header_back:
                finish();
                break;
            case R.id.tv_header_title:
                showTips();
                break;
            case R.id.ll_header_right:
                break;
            case R.id.ll_add:
                bundle = new Bundle();
                bundle.putString("title", "团队人员选择");
                gotoActivity(DepartmentLeaderSelectActivity.class, bundle, 1);
                break;
        }
    }


    private void showTips() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_project_member_tips);
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = ViewUtil.getScreenWidth(this) - ViewUtil.dp2px(this, 40);
        dialog.getWindow().setAttributes(lp);
        dialog.setCancelable(false);
        dialog.show();
        dialog.findViewById(R.id.tv_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        if (requestCode == 1) {
            Bundle bundle = data.getExtras();
            user_id = bundle.getString("user_id", "");
            String user_name = bundle.getString("user_name", "");
            toRequest(ApiConstants.EventTags.project_adduser);
        }
    }
}