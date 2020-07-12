package com.aopcloud.palmproject.ui.activity.staff;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.aopcloud.base.annotation.Layout;
import com.aopcloud.base.base.BaseActivity;
import com.aopcloud.base.util.ToastUtil;
import com.aopcloud.palmproject.BuildConfig;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.api.ApiConstants;
import com.aopcloud.palmproject.common.ResultBean;
import com.aopcloud.palmproject.loader.AppImageLoader;
import com.aopcloud.palmproject.ui.activity.staff.bean.StaffSDetailBean;
import com.aopcloud.palmproject.ui.adapter.staff.StaffTrendAdapter;
import com.aopcloud.palmproject.utils.JumpActionUtil;
import com.aopcloud.palmproject.utils.LoginUserUtil;
import com.aopcloud.palmproject.view.CircleImageView;
import com.aopcloud.palmproject.view.TipsDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @PackageName : com.aopcloud.palmproject.ui.activity.staff
 * @File : StaffApprovalActivity.java
 * @Date : 2020/4/23 9:18
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @VersionCode : V 1.0
 * @Describe ：
 */
@Layout(R.layout.activity_staff_detail)
public class StaffDetailActivity extends BaseActivity {
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
    @BindView(R.id.tv_state)
    TextView mTvState;
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
    @BindView(R.id.rv_trends)
    RecyclerView mRvTrends;
    @BindView(R.id.tv_staff_id)
    TextView mTvStaffId;
    @BindView(R.id.tv_department)
    TextView mTvDepartment;
    @BindView(R.id.tv_post_name)
    TextView mTvPostName;
    @BindView(R.id.tv_start_time)
    TextView mTvStartTime;
    @BindView(R.id.iv_del)
    ImageView mIvDel;
    @BindView(R.id.tv_del)
    TextView mTvDel;

    private String user_id;
    private String team_id = "";
    private String company_user_id;

    private StaffSDetailBean mDetailBean;

    @Override
    protected void initData() {
        super.initData();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            user_id = bundle.getString("user_id");
            team_id = bundle.getString("team_id");
            company_user_id = bundle.getString("company_user_id");

            if (TextUtils.isEmpty(team_id)) {
                team_id = "";
            }
        }
        toRequest(ApiConstants.EventTags.user_info);
    }

    @Override
    protected void initView() {
        mTvHeaderTitle.setText("详细信息");
        mIvHeaderMore.setImageResource(R.mipmap.icon_info_edit);
    }

    private void setViewData(StaffSDetailBean detailBean) {
        AppImageLoader.loadCircleImage(this, BuildConfig.BASE_URL + detailBean.getAvatar(), mIvImg);

        mTvName.setText(detailBean.getName());


        mTvPostName.setText("" + detailBean.getNickname());
        mIvSex.setImageResource(detailBean.getSex().equals("男") ? R.mipmap.icon_sex_boy : R.mipmap.icon_sex_girl);
        mTvName.setText("" + detailBean.getName());
        mTvRealName.setText(detailBean.getStatus() == 1 ? "已实名" : "未实名");
        mTvRealName.setBackgroundResource(detailBean.getStatus() == 1 ? R.drawable.shape_real_name_s : R.drawable.shape_real_name_n);

        mTvMobile.setText("" + detailBean.getTel());
        mTvEmail.setText("" + detailBean.getEmail());
        mTvAgeOther.setText("" + detailBean.getNation());


        mTvStaffId.setText(""+mDetailBean.getCompany().getUser_code());
        mTvDepartment.setText(""+getDepartment(mDetailBean.getRoleTags()));
        mTvPostName.setText(""+getDepartmentRole(mDetailBean.getRoleTags()));


        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        date.setTime(mDetailBean.getCompany().getTime() * 1000);
        mTvStartTime.setText(""+dateFormat.format(date));

        List list = new ArrayList();
        list.addAll(mDetailBean.getTrends());
        StaffTrendAdapter trendAdapter = new StaffTrendAdapter(R.layout.item_member_detail_trend,list);
        trendAdapter.setAvatar(mDetailBean.getAvatar());
        mRvTrends.setLayoutManager(new LinearLayoutManager(this));
        mRvTrends.setAdapter(trendAdapter);

    }


    private String getDepartment(List<StaffSDetailBean.RoleTagsBean> list){
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i <list.size() ; i++) {
            stringBuffer.append(list.get(i).getDepartment_name()).append(" ");
        }
        return ""+stringBuffer.toString();
    }

    private String getDepartmentRole(List<StaffSDetailBean.RoleTagsBean> list){
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i <list.size() ; i++) {
            stringBuffer.append(list.get(i).getName()).append(" ");
        }
        return ""+stringBuffer.toString();
    }





    @OnClick({R.id.ll_header_back, R.id.ll_header_right, R.id.iv_add_friend, R.id.iv_star, R.id.iv_sms,
            R.id.iv_call, R.id.tv_email, R.id.iv_del, R.id.tv_del})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_header_back:
                finish();
                break;
            case R.id.ll_header_right:
                Bundle bundle = new Bundle();
                bundle.putString("StaffSDetailBean", "" + JSON.toJSONString(mDetailBean));
                gotoActivity(StaffInfoEditActivity.class, bundle, 0);
                break;
            case R.id.iv_add_friend:
                break;
            case R.id.iv_star:
                break;
            case R.id.iv_del:
            case R.id.tv_del:
                toRequest(ApiConstants.EventTags.company_status);
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
                JumpActionUtil.jump2CallPhone(StaffDetailActivity.this, "" + mDetailBean.getTel());
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
        map.put("user_id", "" + user_id);
//        map.put("team_id", "" + team_id);
        if (eventTag == ApiConstants.EventTags.user_info) {
            iCommonRequestPresenter.requestPost(eventTag, this, ApiConstants.user_info, map);
        } else if (eventTag == ApiConstants.EventTags.company_status) {
            map.put("company_user_id", "" + company_user_id);
            map.put("status", "" + 3);//设置用户状态，1:正常，2:已拒绝，3:已离职，4：黑名单

            iCommonRequestPresenter.requestPost(eventTag, this, ApiConstants.company_status, map);
        }
    }

    @Override
    public void getRequestData(int eventTag, String result) {
        super.getRequestData(eventTag, result);
        dismissPopupLoading();
        ResultBean bean = JSON.parseObject(result, ResultBean.class);
        if (bean != null && bean.getCode() == 0) {
            if (eventTag == ApiConstants.EventTags.user_info) {
                mDetailBean = JSON.parseObject(bean.getData(), StaffSDetailBean.class);
                setViewData(mDetailBean);
            } else if (eventTag == ApiConstants.EventTags.company_status) {
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        toRequest(ApiConstants.EventTags.user_info);
    }

}
