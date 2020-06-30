package com.aopcloud.palmproject.ui.activity.log;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aopcloud.base.annotation.Layout;
import com.aopcloud.base.base.BaseActivity;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.ui.adapter.feagment.AppFragmentPagerAdapter;
import com.aopcloud.palmproject.ui.fragment.log.MyWorkLogFragment;
import com.aopcloud.palmproject.ui.fragment.log.WorkLogPreviewFragment;
import com.aopcloud.palmproject.ui.fragment.log.WorkLogStatisticsFragment;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @PackageName : com.aopcloud.palmproject.ui.activity.log
 * @File : WorkLogManagerActivity.java
 * @Date : 2020/4/21 2020/4/21
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
@Layout(R.layout.activity_work_log)
public class WorkLogManagerActivity extends BaseActivity {
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
    @BindView(R.id.page_view)
    ViewPager mPageView;
    @BindView(R.id.tv_submit)
    TextView mTvSubmit;
    private List<Fragment> mFragments;
    private List<String> mTabs;

    @Override
    protected void initView() {
        mTvHeaderTitle.setText("工作汇报");
        mFragments = new ArrayList<>();
        mTabs = new ArrayList<>();
        mTabs.add("我的汇报");
        mTabs.add("汇报评阅");
        mTabs.add("汇报统计");
        mFragments.add(new MyWorkLogFragment());
        mFragments.add(new WorkLogPreviewFragment());
        mFragments.add(new WorkLogStatisticsFragment());
        mPageView.setAdapter(new AppFragmentPagerAdapter(getSupportFragmentManager(), mFragments, mTabs));
        mPageView.setCurrentItem(0);
        mTabView.setViewPager(mPageView);
        mPageView.setOffscreenPageLimit(mFragments.size());
    }


    @OnClick({R.id.ll_header_back, R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_header_back:
                finish();
                break;
            case R.id.tv_submit:
                gotoActivity(AddWorkLogActivity.class, 0);
                break;
        }
    }
}
