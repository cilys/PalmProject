package com.aopcloud.palmproject.ui.activity.mine;

import android.app.Dialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.aopcloud.base.annotation.Layout;
import com.aopcloud.base.base.BaseActivity;
import com.aopcloud.base.log.Logcat;
import com.aopcloud.base.util.ToastUtil;
import com.aopcloud.palmproject.BuildConfig;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.api.ApiConstants;
import com.aopcloud.palmproject.bean.UserBean;
import com.aopcloud.palmproject.common.ResultBean;
import com.aopcloud.palmproject.loader.AppImageLoader;
import com.aopcloud.palmproject.utils.JumpActionUtil;
import com.aopcloud.palmproject.utils.LoginUserUtil;
import com.aopcloud.palmproject.view.CircleImageView;
import com.aopcloud.palmproject.view.TipsDialog;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @PackageName : com.aopcloud.basic.ui.activity.mine
 * @File : MyInfoCardActivity.java
 * @Date : 2020/4/10 2020/4/10
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
@Layout(R.layout.activity_mine_info_card)
public class MyInfoCardActivity extends BaseActivity {
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
    @BindView(R.id.rv_list_img)
    RecyclerView mRvListImg;
    @BindView(R.id.ll_email)
LinearLayout mLLEmil;

    private UserBean mUserBean;

    @Override
    protected void initView() {
        mTvHeaderTitle.setText("我的名片");
        toRequest(ApiConstants.EventTags.user_info);
    }

    private void setLoginUser(UserBean userBean) {
        mUserBean = userBean;
        LoginUserUtil.setLoginUserBean(this, userBean);
        Logcat.d("------" + JSON.toJSONString(userBean));
        AppImageLoader.loadCircleImage(this, BuildConfig.BASE_URL + userBean.getAvatar(), mIvImg);


        mTvName.setText("" + userBean.getNickname());
        mTvCity.setText("" + userBean.getAddress());
        mTvDesc.setText("" + userBean.getSinature());
        mTvName.setText("" + userBean.getName());
        mTvRealName.setText(userBean.getStatus() == 1 ? "已实名" : "未实名");
        mTvRealName.setBackgroundResource(userBean.getStatus() == 1 ? R.drawable.shape_real_name_s : R.drawable.shape_real_name_n);

        mTvMobile.setText("" + userBean.getTel());
        mTvEmail.setText("" + userBean.getEmail());
        mTvAgeOther.setText("" + userBean.getNation());
        mTvEducation.setText("" + userBean.getAccount_type());
        mTvSchool.setText("" + userBean.getSchool());
        mTvEducation.setText("" + userBean.getEducation());
        mTvWorkType.setText("" + "");
        mTvLevel.setText("" + userBean.getSkills());


        if (TextUtils.isEmpty(userBean.getEmail())){
            mLLEmil.setVisibility(View.GONE);
        }


    }

    @OnClick({R.id.ll_header_back, R.id.ll_header_right, R.id.iv_add_friend, R.id.iv_star, R.id.iv_sms, R.id.iv_call, R.id.iv_send_email})
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
            case R.id.iv_sms:
                JumpActionUtil.jump2SendSms(this, "" + mUserBean.getTel());
                break;
            case R.id.iv_call:
                callPhone();
                break;
            case R.id.iv_send_email:
                JumpActionUtil.jump2SendEmail(this, mUserBean.getEmail(), "");
                break;
        }
    }

    private void callPhone() {
        TipsDialog.wrap(this).setMsg("呼叫 :" + mUserBean.getTel()).setOnActionClickListener(new TipsDialog.onActionClickListener() {

            @Override
            public void onSure(Dialog dialog) {
                JumpActionUtil.jump2CallPhone(MyInfoCardActivity.this, "" + mUserBean.getTel());
                dialog.dismiss();
            }
        }).show();
    }

    @Override
    public void toRequest(int eventTag) {
        super.toRequest(eventTag);
        Map map = new HashMap();
        map.put("token", "" + LoginUserUtil.getToken(this));
        if (eventTag == ApiConstants.EventTags.user_info) {
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
                UserBean userBean = JSON.parseObject(bean.getData(), UserBean.class);
                setLoginUser(userBean);
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
