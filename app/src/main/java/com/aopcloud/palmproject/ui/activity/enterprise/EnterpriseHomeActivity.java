package com.aopcloud.palmproject.ui.activity.enterprise;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.ImageView;
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
import com.aopcloud.palmproject.ui.activity.enterprise.bean.EnterpriseInfoBean;
import com.aopcloud.palmproject.ui.activity.enterprise.bean.EnterpriseManagerBean;
import com.aopcloud.palmproject.utils.LoginUserUtil;
import com.aopcloud.palmproject.view.CircleImageView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @PackageName : com.aopcloud.palmproject.ui.activity.enterprise
 * @File : EnterpriseHomeActivity.java
 * @Date : 2020/4/20 2020/4/20
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
@Layout(R.layout.activity_enterprise_home)
public class EnterpriseHomeActivity extends BaseAc {
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.iv_setting)
    ImageView mIvSetting;
    @BindView(R.id.iv_qr_code)
    ImageView mIvQrCode;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_no_code)
    TextView mTvNoCode;
    @BindView(R.id.rl_head_layout)
    RelativeLayout mRlHeadLayout;
    @BindView(R.id.iv_logo)
    CircleImageView mIvLogo;
    @BindView(R.id.rl_info)
    RelativeLayout mRlInfo;
    @BindView(R.id.rl_qr)
    RelativeLayout mRlQr;

    private EnterpriseInfoBean enterpriseInfoBean;

    @Override
    protected void initData() {
        super.initData();
        toRequest(ApiConstants.EventTags.company_get);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void initView() {

//        AppImageLoader.loadCircleImage(this, "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3347747746,2219281735&fm=26&gp=0.jpg", mIvLogo);

    }

    private void setViewData(EnterpriseInfoBean enterpriseInfoBean) {
        this.enterpriseInfoBean = enterpriseInfoBean;
        AppImageLoader.loadCircleImage(this, BuildConfig.BASE_URL + enterpriseInfoBean.getLogo(), mIvLogo);

        mTvName.setText(enterpriseInfoBean.getName());
        mTvNoCode.setText("团队编码：" + enterpriseInfoBean.getCode() + " 企业负责人:" + enterpriseInfoBean.getLeader_name());


    }

    @OnClick({R.id.iv_back, R.id.iv_setting, R.id.iv_qr_code, R.id.rl_info, R.id.rl_qr})
    public void onViewClicked(View view) {
        Bundle bundle;
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_setting:
                toRequest(ApiConstants.EventTags.manage_all);
                break;
            case R.id.rl_info:
                bundle = new Bundle();
                bundle.putString("enterpriseInfoBean", JSON.toJSONString(enterpriseInfoBean));
                gotoActivity(EnterpriseInfoActivity.class, bundle, 0);
                break;
            case R.id.iv_qr_code:
            case R.id.rl_qr:
                bundle = new Bundle();
                bundle.putString("enterpriseInfoBean", JSON.toJSONString(enterpriseInfoBean));
                gotoActivity(EnterpriseQRActivity.class, bundle, 0);
                break;
        }
    }

    @Override
    public void toRequest(int eventTag) {
        super.toRequest(eventTag);
        Map map = new HashMap();
        map.put("token", "" + LoginUserUtil.getToken(this));
        map.put("code", "" + LoginUserUtil.getCurrentEnterpriseNo(this));
        if (eventTag == ApiConstants.EventTags.company_get) {
            iCommonRequestPresenter.requestPost(eventTag, this, ApiConstants.company_get, map);
        } else if (eventTag == ApiConstants.EventTags.manage_all) {
            iCommonRequestPresenter.requestPost(eventTag, this, ApiConstants.manage_all, map);
        }
    }

    @Override
    public void getRequestData(int eventTag, String result) {
        super.getRequestData(eventTag, result);
        dismissPopupLoading();
        ResultBean bean = JSON.parseObject(result, ResultBean.class);
        if (bean != null && bean.getCode() == 0) {
            if (eventTag == ApiConstants.EventTags.company_get) {
                EnterpriseInfoBean enterpriseInfoBean = JSON.parseObject(bean.getData(), EnterpriseInfoBean.class);
                setViewData(enterpriseInfoBean);
            } else if (eventTag == ApiConstants.EventTags.manage_all) {
                List<EnterpriseManagerBean> beanList = JSON.parseArray(bean.getData(), EnterpriseManagerBean.class);

                boolean isManager = false;
                for (int i = 0; i < beanList.size(); i++) {
                    if (beanList.get(i).getUser_id() == LoginUserUtil.getLoginUserBean(this).getId()
                            &&beanList.get(i).getRights().contains("all")){
                        isManager = true;
                    }
                }
                if (isManager) {
                    Bundle    bundle = new Bundle();
                    bundle.putString("enterpriseInfoBean", JSON.toJSONString(enterpriseInfoBean));
                    gotoActivity(EnterpriseSettingActivity.class, bundle, 0);
                }else {
                    ToastUtil.showToast("您没有权限");
                }
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
        if (requestCode == 0 && resultCode == 0) {
            toRequest(ApiConstants.EventTags.company_get);
        }
    }
}
