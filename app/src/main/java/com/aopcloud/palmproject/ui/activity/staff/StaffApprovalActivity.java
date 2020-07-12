package com.aopcloud.palmproject.ui.activity.staff;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.aopcloud.base.annotation.Layout;
import com.aopcloud.base.base.BaseActivity;
import com.aopcloud.base.log.Logcat;
import com.aopcloud.base.util.ToastUtil;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.api.ApiConstants;
import com.aopcloud.palmproject.common.ResultBean;
import com.aopcloud.palmproject.ui.activity.staff.bean.StaffListBean;
import com.aopcloud.palmproject.ui.adapter.feagment.AppFragmentPagerAdapter;
import com.aopcloud.palmproject.ui.fragment.log.MyWorkLogFragment;
import com.aopcloud.palmproject.ui.fragment.log.WorkLogPreviewFragment;
import com.aopcloud.palmproject.ui.fragment.log.WorkLogStatisticsFragment;
import com.aopcloud.palmproject.ui.fragment.staff.StaffApprovalFragment;
import com.aopcloud.palmproject.utils.LoginUserUtil;
import com.flyco.tablayout.SlidingTabLayout;

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
@Layout(R.layout.activity_staff_approval)
public class StaffApprovalActivity extends BaseActivity implements ViewPager.OnPageChangeListener {


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
    @BindView(R.id.tab_view)
    SlidingTabLayout mTabView;
    @BindView(R.id.et_search)
    EditText mEtSearch;
    @BindView(R.id.page_view)
    ViewPager mPageView;
    private List<Fragment> mFragments;
    private List<String> mTabs;

    @Override
    protected void initView() {
        mTvHeaderTitle.setText("新同事审核");
        mTvHeaderRight.setText("全部通过");

        mFragments = new ArrayList<>();
        mTabs = new ArrayList<>();
        mTabs.add("待审核");
        mTabs.add("已拒绝");
        //(可选)用户状态，-1:所有，0:审核中，1:正常，2:已拒绝，3:已离职，默认：-1
        mFragments.add(StaffApprovalFragment.getInstance("0"));
        mFragments.add(StaffApprovalFragment.getInstance("2"));
        mPageView.setAdapter(new AppFragmentPagerAdapter(getSupportFragmentManager(), mFragments, mTabs));
        mPageView.setCurrentItem(0);
        mTabView.setViewPager(mPageView);
        mPageView.addOnPageChangeListener(this);
        mPageView.setOffscreenPageLimit(mFragments.size());
    }


    @Override
    public void onPageScrolled(int i, float v, int i1) {
        if (i == 0) {
            mTvHeaderRight.setVisibility(View.VISIBLE);
            mLlHeaderRight.setEnabled(true);
        } else {
            mTvHeaderRight.setVisibility(View.INVISIBLE);
            mLlHeaderRight.setEnabled(false);
        }
    }

    @Override
    public void onPageSelected(int i) {

    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }


    @OnClick({R.id.ll_header_back, R.id.ll_header_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_header_back:
                finish();
                break;
            case R.id.ll_header_right:
                toRequest(ApiConstants.EventTags.company_allstatus);
                break;
        }
    }

    @Override
    public void toRequest(int eventTag) {
        super.toRequest(eventTag);
        Map map = new HashMap();
        map.put("token", "" + LoginUserUtil.getToken(this));
        map.put("code", "" + LoginUserUtil.getCurrentEnterpriseNo(this));
        if (eventTag == ApiConstants.EventTags.company_allstatus) {
            map.put("status", "1");
            iCommonRequestPresenter.requestPost(eventTag, this, ApiConstants.company_allstatus, map);
        }
    }

    @Override
    public void getRequestData(int eventTag, String result) {
        super.getRequestData(eventTag, result);
        ResultBean bean = JSON.parseObject(result, ResultBean.class);
        if (bean != null && bean.getCode() == 0) {
            if (eventTag == ApiConstants.EventTags.company_allstatus) {
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
