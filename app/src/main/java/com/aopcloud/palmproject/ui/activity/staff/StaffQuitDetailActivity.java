package com.aopcloud.palmproject.ui.activity.staff;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.aopcloud.base.annotation.Layout;
import com.aopcloud.base.base.BaseActivity;
import com.aopcloud.base.log.Logcat;
import com.aopcloud.base.util.ToastUtil;
import com.aopcloud.palmproject.BuildConfig;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.api.ApiConstants;
import com.aopcloud.palmproject.common.ResultBean;
import com.aopcloud.palmproject.loader.AppImageLoader;
import com.aopcloud.palmproject.ui.activity.staff.bean.StaffApprovalBean;
import com.aopcloud.palmproject.ui.activity.staff.bean.StaffQuitDetailBean;
import com.aopcloud.palmproject.ui.adapter.staff.StaffTrendAdapter;
import com.aopcloud.palmproject.utils.JumpActionUtil;
import com.aopcloud.palmproject.utils.LoginUserUtil;
import com.aopcloud.palmproject.view.CircleImageView;
import com.aopcloud.palmproject.view.TipsDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
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
@Layout(R.layout.activity_staff_quit_detail)
public class StaffQuitDetailActivity extends BaseActivity {


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

    private String user_id;
    private String company_user_id;
    private String status;

    private StaffQuitDetailBean mDetailBean;

    @Override
    protected void initData() {
        super.initData();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            user_id = bundle.getString("user_id");
            company_user_id= bundle.getString("company_user_id");
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

        mTvEmail.setText(""+mDetailBean.getEmail());
        mTvMobile.setText(""+mDetailBean.getTel());
        AppImageLoader.loadCircleImage(this
                , BuildConfig.BASE_URL + mDetailBean.getAvatar()
                , mIvImg);
        mIvSex.setImageResource(mDetailBean.getSex().equals("男") ? R.mipmap.icon_sex_boy : R.mipmap.icon_sex_girl);


        List list = new ArrayList();
        list.addAll(mDetailBean.getTrends());
        StaffTrendAdapter trendAdapter = new StaffTrendAdapter(R.layout.item_member_detail_trend,list);
        trendAdapter.setAvatar(mDetailBean.getAvatar());
        mRvTrends.setLayoutManager(new LinearLayoutManager(this));
        mRvTrends.setAdapter(trendAdapter);
    }
    @OnClick({R.id.ll_header_back, R.id.ll_header_right,R.id.iv_add_friend, R.id.iv_star, R.id.iv_sms, R.id.iv_call, R.id.tv_email, R.id.iv_send_email})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_add_friend:
                break;
            case R.id.iv_star:
                break;
            case R.id.ll_header_back:
                finish();
                break;
            case R.id.ll_header_right:
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
                JumpActionUtil.jump2CallPhone(StaffQuitDetailActivity.this, "" + mDetailBean.getTel());
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
            Logcat.i("------------" + eventTag + "/" + JSON.toJSONString(map));
            iCommonRequestPresenter.requestPost(eventTag, this, ApiConstants.user_info, map);
        }
    }

    @Override
    public void getRequestData(int eventTag, String result) {
        super.getRequestData(eventTag, result);
        Logcat.i("------------" + eventTag + "/" + result);
        dismissPopupLoading();
        ResultBean bean = JSON.parseObject(result, ResultBean.class);
        if (bean != null && bean.getCode() == 0) {
            if (eventTag == ApiConstants.EventTags.user_info) {
                mDetailBean = JSON.parseObject(bean.getData(), StaffQuitDetailBean.class);
                setViewData();
            }
        } else {
            ToastUtil.showToast(bean != null ? bean.getMsg() : "加载错误，请重试");
        }

    }

    @Override
    public void onRequestFailureException(int eventTag, String msg) {
        super.onRequestFailureException(eventTag, msg);
        Logcat.i("------------" + eventTag + "/" + msg);
    }


}
