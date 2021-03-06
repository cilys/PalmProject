package com.aopcloud.palmproject.ui.activity.staff;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.aopcloud.palmproject.ui.activity.staff.bean.StaffApprovalBean;
import com.aopcloud.palmproject.ui.activity.staff.bean.StaffListBean;
import com.aopcloud.palmproject.utils.JumpActionUtil;
import com.aopcloud.palmproject.utils.LoginUserUtil;
import com.aopcloud.palmproject.view.CircleImageView;
import com.aopcloud.palmproject.view.TipsDialog;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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
@Layout(R.layout.activity_staff_approval_detail)
public class StaffApprovalDetailActivity extends BaseAc {

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
    @BindView(R.id.tv_source)
    TextView mTvSource;
    @BindView(R.id.tv_mobile)
    TextView mTvMobile;
    @BindView(R.id.iv_sms)
    ImageView mIvSms;
    @BindView(R.id.iv_call)
    ImageView mIvCall;
    @BindView(R.id.tv_enterprise)
    TextView mTvEnterprise;
    @BindView(R.id.tv_apply_time)
    TextView mTvApplyTime;
    @BindView(R.id.ll_reason)
    LinearLayout mLlReason;
    @BindView(R.id.tv_left_menu)
    TextView mTvLeftMenu;
    @BindView(R.id.tv_right_menu)
    TextView mTvRightMenu;
    private String user_id;
    private String company_user_id;
    private String status;

    private StaffApprovalBean mDetailBean;
    private StaffListBean mStaffListBean;

    @Override
    protected void initData() {
        super.initData();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            user_id = bundle.getString("user_id");
            company_user_id = bundle.getString("company_user_id");

            String json = bundle.getString("StaffListBean");
            mStaffListBean = JSON.parseObject(json, StaffListBean.class);
        }
        toRequest(ApiConstants.EventTags.user_info);
    }


    @Override
    protected void initView() {
        mTvHeaderTitle.setText("详细信息");
    }

    private void setViewData() {

        mTvName.setText("" + mDetailBean.getName());
        mTvRealName.setText(mDetailBean.getStatus() == 1 ? "已实名" : "未实名");
        mTvRealName.setBackgroundResource(mDetailBean.getStatus() == 1 ? R.drawable.shape_real_name_s : R.drawable.shape_real_name_n);

        mTvAgeOther.setText(" " + mDetailBean.getSex() + " " + mDetailBean.getNation());
        AppImageLoader.loadCircleImage(this
                , BuildConfig.BASE_URL + mDetailBean.getAvatar()
                , mIvImg);
        mIvSex.setImageResource(mDetailBean.getSex().equals("男") ? R.mipmap.icon_sex_boy : R.mipmap.icon_sex_girl);

        if (mStaffListBean != null) {
            mTvSource.setText("来源》" + mStaffListBean.getCome_from());
            mTvMobile.setText("" + mStaffListBean.getTel());
            mTvEnterprise.setText("" + mStaffListBean.getCompany_name());
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            date.setTime(mDetailBean.getMake_time() * 1000);
            mTvApplyTime.setText("" + dateFormat.format(date));
        }
    }

    @OnClick({R.id.ll_header_back, R.id.ll_header_right, R.id.iv_add_friend, R.id.iv_star,
            R.id.tv_left_menu, R.id.tv_right_menu,R.id.iv_sms,
            R.id.iv_call})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_header_back:
                finish();
                break;
            case R.id.ll_header_right:
                break;
            case R.id.iv_add_friend:
                break;
            case R.id.iv_star:
                break;
            case R.id.tv_left_menu:
                status = "2";
                toRequest(ApiConstants.EventTags.company_status);
                break;
            case R.id.tv_right_menu:
                status = "1";
                toRequest(ApiConstants.EventTags.company_status);
                break;
            case R.id.iv_sms:
                JumpActionUtil.jump2SendSms(this, "" + mDetailBean.getTel());
                break;
            case R.id.iv_call:
                callPhone();
                break;
        }
    }

    private void callPhone() {
        TipsDialog.wrap(this).setMsg("呼叫 :" + mDetailBean.getTel()).setOnActionClickListener(new TipsDialog.onActionClickListener() {

            @Override
            public void onSure(Dialog dialog) {
                JumpActionUtil.jump2CallPhone(StaffApprovalDetailActivity.this, "" + mDetailBean.getTel());
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
            iCommonRequestPresenter.requestPost(eventTag, this, ApiConstants.user_info, map);
        } else if (eventTag == ApiConstants.EventTags.company_status) {
            map.put("company_user_id", "" + company_user_id);
            map.put("status", "" + status);
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
                mDetailBean = JSON.parseObject(bean.getData(), StaffApprovalBean.class);
                setViewData();
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
}
