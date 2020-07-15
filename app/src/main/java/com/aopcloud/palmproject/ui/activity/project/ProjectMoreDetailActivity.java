package com.aopcloud.palmproject.ui.activity.project;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.aopcloud.base.annotation.Layout;
import com.aopcloud.base.base.BaseActivity;
import com.aopcloud.base.util.ToastUtil;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.api.ApiConstants;
import com.aopcloud.palmproject.common.ResultBean;
import com.aopcloud.palmproject.loader.AppImageLoader;
import com.aopcloud.palmproject.ui.activity.BaseAc;
import com.aopcloud.palmproject.ui.activity.project.bean.ProjectDetailBean;
import com.aopcloud.palmproject.utils.LoginUserUtil;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @PackageName : com.aopcloud.palmproject.ui.activity.project
 * @File : AddProjectActivity.java
 * @Date : 2020/4/26 2020/4/26
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
@Layout(R.layout.activity_project_detail_more)
public class ProjectMoreDetailActivity extends BaseAc {

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
    @BindView(R.id.tv_no)
    TextView mTvNo;
    @BindView(R.id.iv_qr_code)
    ImageView mIvQrCode;
    @BindView(R.id.tv_start_time)
    TextView mTvStartTime;
    @BindView(R.id.tv_state)
    TextView mTvState;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_address)
    TextView mTvAddress;
    @BindView(R.id.tv_update_time)
    TextView mTvUpdateTime;
    @BindView(R.id.tv_abbreviation)
    TextView mTvAbbreviation;
    @BindView(R.id.tv_type)
    TextView mTvType;
    @BindView(R.id.tv_area)
    TextView mTvArea;
    @BindView(R.id.tv_cost)
    TextView mTvCost;
    @BindView(R.id.tv_desc)
    TextView mTvDesc;
    @BindView(R.id.tv_material)
    TextView mTvMaterial;
    @BindView(R.id.tv_comment)
    TextView mTvComment;
    @BindView(R.id.iv_comment)
    ImageView mIvComment;
    @BindView(R.id.iv_star)
    ImageView mIvStar;
    @BindView(R.id.iv_share)
    ImageView mIvShare;
    private String project_id;
    private List<Fragment> mFragments;
    private List<String> mTabs;
    private ProjectDetailBean mProjectDetailBean;

    @Override
    protected void initData() {
        super.initData();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            project_id = bundle.getString("project_id");
        }
        toRequest(ApiConstants.EventTags.project_get);
    }

    @Override
    protected void initView() {

    }


    private void setViewData(ProjectDetailBean detailBean) {
        mProjectDetailBean = detailBean;
        mTvNo.setText("" + detailBean.getCode());
        mTvStartTime.setText(detailBean.getStart_date());
        mTvState.setText(detailBean.getStatus());
        mTvName.setText(detailBean.getName());
        mTvAddress.setText(detailBean.getAddress());

        mTvUpdateTime.setText(detailBean.getEnd_date());
        mTvAbbreviation.setText(detailBean.getShort_name());
        mTvType.setText(detailBean.getType());
        mTvArea.setText("" + detailBean.getArea());
        mTvCost.setText("" + detailBean.getPrice() + "万元");
        mTvDesc.setText("" + detailBean.getSummary());
        mTvMaterial.setText("" + detailBean.getDistrict());

        Bitmap mLogo = CodeUtils.createImage(detailBean.getCode(), 500, 500, null);
        AppImageLoader.load(this, mLogo, mIvQrCode);


    }

    @Override
    public void toRequest(int eventTag) {
        super.toRequest(eventTag);
        Map map = new HashMap();
        map.put("token", "" + LoginUserUtil.getToken(this));
        map.put("code", "" + LoginUserUtil.getCurrentEnterpriseNo(this));
        if (eventTag == ApiConstants.EventTags.project_get) {
            map.put("project_id", "" + project_id);//项目名称
            iCommonRequestPresenter.requestPost(eventTag, this, ApiConstants.project_get, map);
        }
    }

    @Override
    public void getRequestData(int eventTag, String result) {
        super.getRequestData(eventTag, result);
        ResultBean bean = JSON.parseObject(result, ResultBean.class);
        if (bean != null && bean.getCode() == 0) {
            if (eventTag == ApiConstants.EventTags.project_get) {
                ProjectDetailBean detailBean = JSON.parseObject(bean.getData(), ProjectDetailBean.class);
                setViewData(detailBean);
            }
        } else {
            ToastUtil.showToast(bean != null ? bean.getMsg() : "加载错误，请重试");
        }

    }

    @Override
    public void onRequestFailureException(int eventTag, String msg) {
        super.onRequestFailureException(eventTag, msg);
    }


    @OnClick({R.id.ll_header_back, R.id.ll_header_right, R.id.tv_comment, R.id.iv_comment, R.id.iv_star, R.id.iv_share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_header_back:
                finish();
                break;
            case R.id.ll_header_right:
                break;
            case R.id.tv_comment:
            case R.id.iv_comment:
                Bundle bundle = new Bundle();
                bundle.putString("project_id", "" + project_id);
                gotoActivity(ProjectTaskDiscussActivity.class, bundle, 0);
                break;
            case R.id.iv_star:
                ToastUtil.showToast("敬请期待");
                break;
            case R.id.iv_share:
                ToastUtil.showToast("敬请期待");
                break;
        }
    }
}