package com.aopcloud.palmproject.ui.activity.enterprise;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.aopcloud.base.annotation.Layout;
import com.aopcloud.base.base.BaseActivity;
import com.aopcloud.base.util.ToastUtil;
import com.aopcloud.base.util.ViewUtil;
import com.aopcloud.palmproject.BuildConfig;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.api.ApiConstants;
import com.aopcloud.palmproject.common.ResultBean;
import com.aopcloud.palmproject.loader.AppImageLoader;
import com.aopcloud.palmproject.ui.activity.enterprise.bean.EnterpriseListBean;
import com.aopcloud.palmproject.utils.LoginUserUtil;
import com.aopcloud.palmproject.view.CircleImageView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @PackageName : com.aopcloud.palmproject.ui.activity.enterprise
 * @File : EnterpriseSettingActivity.java
 * @Date : 2020/4/20 2020/4/20
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
@Layout(R.layout.activity_enterprise_director)
public class EnterpriseSettingDirectorActivity extends BaseActivity {


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
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_position)
    TextView mTvPosition;
    @BindView(R.id.tv_position_2)
    TextView mTvPosition2;

    @Override
    protected void initView() {
        mTvHeaderTitle.setText("企业负责人");
        AppImageLoader.loadCircleImage(this, BuildConfig.BASE_URL+LoginUserUtil.getCurrentEnterpriseBean(this).getAvatar(), mIvImg);

        mTvName.setText(""+LoginUserUtil.getCurrentEnterpriseBean(this).getLeader_name());
    }


    @OnClick({R.id.ll_header_back, R.id.ll_header_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_header_back:
                finish();
                break;
            case R.id.ll_header_right:
                showTips();
                break;
        }
    }

    private void showTips() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_enterprise_director_tips);
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = ViewUtil.getScreenWidth(this);
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().getDecorView().setPadding(0, 0, 0, 0);
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().getDecorView().setBackgroundColor(Color.parseColor("#40000000"));

        dialog.setCancelable(false);
        dialog.show();
        dialog.findViewById(R.id.tv_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.tv_transfer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoActivity(TransferEnterpriseActivity.class,0);
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.tv_dismiss).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toRequest(ApiConstants.EventTags.company_del);
                dialog.dismiss();
            }
        });
    }

    @Override
    public void toRequest(int eventTag) {
        super.toRequest(eventTag);
        Map params = new HashMap();
        params.put("token", "" + LoginUserUtil.getToken(this));
        params.put("code", "" + LoginUserUtil.getCurrentEnterpriseNo(this));
        if (eventTag == ApiConstants.EventTags.company_del) {
            iCommonRequestPresenter.requestPost(eventTag, this, ApiConstants.company_del, params);
        }else  if (eventTag == ApiConstants.EventTags.company_get) {
            iCommonRequestPresenter.requestPost(eventTag, this, ApiConstants.company_get, params);
        }
    }

    @Override
    public void getRequestData(int eventTag, String result) {
        super.getRequestData(eventTag, result);
        dismissPopupLoading();
        ResultBean bean = JSON.parseObject(result, ResultBean.class);
        if (bean != null && bean.getCode() == 0) {
            if (eventTag == ApiConstants.EventTags.company_del) {
                Bundle bundle = new Bundle();
                bundle.putInt("defaultIndex",0);
                gotoActivity(SwitchEnterpriseActivity.class,bundle,0);
                setResult(0);
                finish();
            }else  if (eventTag == ApiConstants.EventTags.company_get) {
                EnterpriseListBean enterpriseInfoBean = JSON.parseObject(bean.getData(), EnterpriseListBean.class);
                LoginUserUtil.setCurrentEnterpriseBean(this,enterpriseInfoBean);
                initView();
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
            toRequest(ApiConstants.EventTags.company_get);
        }
    }
}