package com.aopcloud.palmproject.ui.activity.project;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.aopcloud.base.annotation.Layout;
import com.aopcloud.base.util.ToastUtil;
import com.aopcloud.palmproject.BuildConfig;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.api.ApiConstants;
import com.aopcloud.palmproject.common.ResultBean;
import com.aopcloud.palmproject.loader.AppImageLoader;
import com.aopcloud.palmproject.ui.activity.BaseAc;
import com.aopcloud.palmproject.ui.activity.project.bean.ProjectMemberDetailBean;
import com.aopcloud.palmproject.ui.adapter.project.ProjectMemberProjectAdapter;
import com.aopcloud.palmproject.utils.JumpActionUtil;
import com.aopcloud.palmproject.utils.LoginUserUtil;
import com.aopcloud.palmproject.view.CircleImageView;
import com.aopcloud.palmproject.view.TipsDialog;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
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
@Layout(R.layout.activity_project_member_detail)
public class ProjectMemberDetailActivity extends BaseAc {


    @BindView(R.id.ll_header_back)
    LinearLayout mLlHeaderBack;
    @BindView(R.id.tv_header_title)
    TextView mTvHeaderTitle;
    @BindView(R.id.iv_header_more)
    ImageView mIvHeaderMore;
    @BindView(R.id.ll_header_right)
    LinearLayout mLlHeaderRight;
    @BindView(R.id.ll_common_layout)
    LinearLayout mLlCommonLayout;
    @BindView(R.id.iv_img)
    CircleImageView mIvImg;
    @BindView(R.id.iv_sex)
    ImageView mIvSex;
    @BindView(R.id.iv_add_friend)
    ImageView mIvAddFriend;
    @BindView(R.id.iv_star)
    ImageView mIvStar;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_age_other)
    TextView mTvAgeOther;
    @BindView(R.id.tv_real_name)
    TextView mTvRealName;
    @BindView(R.id.tv_desc)
    TextView mTvDesc;
    @BindView(R.id.tv_city)
    TextView mTvCity;
    @BindView(R.id.rl_city)
    RelativeLayout mRlCity;
    @BindView(R.id.tv_education)
    TextView mTvEducation;
    @BindView(R.id.rl_education)
    RelativeLayout mRlEducation;
    @BindView(R.id.tv_school)
    TextView mTvSchool;
    @BindView(R.id.rl_school)
    RelativeLayout mRlSchool;
    @BindView(R.id.tv_work_type)
    TextView mTvWorkType;
    @BindView(R.id.rl_work_type)
    RelativeLayout mRlWorkType;
    @BindView(R.id.tv_level)
    TextView mTvLevel;
    @BindView(R.id.rl_level)
    RelativeLayout mRlLevel;
    @BindView(R.id.tv_mobile)
    TextView mTvMobile;
    @BindView(R.id.iv_sms)
    ImageView mIvSms;
    @BindView(R.id.iv_call)
    ImageView mIvCall;
    @BindView(R.id.tv_email)
    TextView mTvEmail;
    @BindView(R.id.iv_send_email)
    ImageView mIvSendEmail;
    @BindView(R.id.tv_project_name)
    TextView mTvProjectName;
    @BindView(R.id.tv_team)
    TextView mTvTeam;
    @BindView(R.id.tv_role)
    TextView mTvRole;
    @BindView(R.id.tv_day_wages)
    TextView mTvDayWages;
    @BindView(R.id.tv_project_count)
    TextView mTvProjectCount;
    @BindView(R.id.iv_project_count)
    ImageView mIvProjectCount;
    @BindView(R.id.rv_project)
    RecyclerView mRvProject;
    @BindView(R.id.ll_project_list)
    LinearLayout mLlProjectList;


    private String project_id;
    private String user_id;
    private String team_id;
    private String project_name;

    private ProjectMemberDetailBean mDetailBean;
    private ProjectMemberProjectAdapter mProjectAdapter;

    @Override
    protected void initData() {
        super.initData();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            project_id = bundle.getString("project_id");
            project_name = bundle.getString("project_name");
            user_id = bundle.getString("user_id");
            team_id = bundle.getString("team_id");
        }
        toRequest(ApiConstants.EventTags.user_info);
    }

    @Override
    protected void initView() {
        mTvHeaderTitle.setText("详细信息");
        mLlProjectList.setVisibility(View.GONE);
    }


    private void setViewData() {
        AppImageLoader.loadCircleImage(this
                , BuildConfig.BASE_URL + mDetailBean.getAvatar()
                , mIvImg);
        mIvSex.setImageResource(mDetailBean.getSex().equals("男") ? R.mipmap.icon_sex_boy : R.mipmap.icon_sex_girl);

        mTvName.setText("" + mDetailBean.getName());
        mTvRealName.setText(mDetailBean.getStatus() == 1 ? "已实名" : "未实名");
        mTvRealName.setBackgroundResource(mDetailBean.getStatus() == 1 ? R.drawable.shape_real_name_s : R.drawable.shape_real_name_n);

        mTvAgeOther.setText(" " + mDetailBean.getSex() + " " + mDetailBean.getNation());

        mTvDesc.setText("" + mDetailBean.getSinature());
        mTvCity.setText(mDetailBean.getDistrict());
        mTvEducation.setText("" + mDetailBean.getEducation());
        mTvSchool.setText("" + mDetailBean.getSchool());
        mTvWorkType.setText("");
        mTvLevel.setText("" + mDetailBean.getSkills());

        mTvEmail.setText("" + mDetailBean.getEmail());
        mTvMobile.setText("" + mDetailBean.getTel());


        mTvProjectName.setText(null!=mDetailBean.getProjects()&&mDetailBean.getProjects().size()>0?""+mDetailBean.getProjects().get(0).getProject_name():"");
        if (mDetailBean.getTeam()!=null){
            mTvTeam.setText(""+mDetailBean.getTeam().getTeam_name());
            mTvRole.setText(""+mDetailBean.getTeam().getMajors());
            mTvDayWages.setText(""+mDetailBean.getTeam().getSalary());
        }

        mTvProjectCount.setText(""+mDetailBean.getProjects().size()+ "个");

        mProjectAdapter = new ProjectMemberProjectAdapter(R.layout.item_project_member_detail, mDetailBean.getProjects());
        mRvProject.setLayoutManager(new LinearLayoutManager(this));
        mRvProject.setAdapter(mProjectAdapter);

    }

    @OnClick({R.id.ll_header_back, R.id.ll_header_right, R.id.tv_project_count, R.id.iv_project_count, R.id.iv_sms, R.id.iv_call, R.id.iv_send_email})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_header_back:
                finish();
                break;
            case R.id.ll_header_right:
                break;
            case R.id.tv_project_count:
            case R.id.iv_project_count:
                mLlProjectList.setVisibility(mLlProjectList.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                break;
            case R.id.iv_sms:
                JumpActionUtil.jump2SendSms(this, "" + mDetailBean.getTel());
                break;
            case R.id.iv_call:
                callPhone();
                break;
            case R.id.iv_send_email:
                JumpActionUtil.jump2SendEmail(this, mDetailBean.getEmail(), "");
                break;
        }
    }
    private void callPhone() {
        TipsDialog.wrap(this).setMsg("呼叫 :" + mDetailBean.getTel()).setOnActionClickListener(new TipsDialog.onActionClickListener() {

            @Override
            public void onSure(Dialog dialog) {
                JumpActionUtil.jump2CallPhone(ProjectMemberDetailActivity.this, "" + mDetailBean.getTel());
                dialog.dismiss();
            }
        }).show();
    }
    @Override
    public void toRequest(int eventTag) {
        super.toRequest(eventTag);
        Map map = new HashMap();
        map.put("token", "" + LoginUserUtil.getToken(this));
        map.put("code", "" + LoginUserUtil.getCurrentEnterpriseNo(this));
        if (eventTag == ApiConstants.EventTags.user_info) {
            map.put("user_id", "" + user_id);
            if (!TextUtils.isEmpty(team_id)){
                map.put("team_id", "" + team_id);
            }
            iCommonRequestPresenter.requestPost(eventTag, this, ApiConstants.user_info, map);
        }
    }

    @Override
    public void getRequestData(int eventTag, String result) {
        super.getRequestData(eventTag, result);
        dismissPopupLoading();
        ResultBean bean = JSON.parseObject(result, ResultBean.class);
        if (bean != null && bean.getCode() == 0) {
            if (eventTag == ApiConstants.EventTags.user_info) {
                mDetailBean = JSON.parseObject(bean.getData(), ProjectMemberDetailBean.class);
                setViewData();
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