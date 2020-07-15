package com.aopcloud.palmproject.ui.activity.project;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aopcloud.base.annotation.Layout;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.api.ApiConstants;
import com.aopcloud.palmproject.ui.activity.BaseAc;
import com.aopcloud.palmproject.ui.adapter.feagment.AppFragmentPagerAdapter;
import com.aopcloud.palmproject.ui.fragment.project.ProjectListFragment;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

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
@Layout(R.layout.activity_project_manage)
public class ProjectManagerActivity extends BaseAc {
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
    @BindView(R.id.tv_add_project)
    TextView mTvAddProject;
    @BindView(R.id.tv_add_project_child)
    TextView mTvAddProjectChild;


    private List<Fragment> mFragments;
    private List<String> mTabs;

    @Override
    protected void initData() {
        super.initData();
        toRequest(ApiConstants.EventTags.project_all);
    }

    @Override
    protected void initView() {
        mTvHeaderTitle.setText("项目");
        mTvHeaderRight.setText("草稿箱");


        mFragments = new ArrayList<>();
        mTabs = new ArrayList<>();
        mTabs.add("我负责的");
        mTabs.add("我参与的");
        mTabs.add("企业全部");
        mFragments.add(ProjectListFragment.getInstance("1"));
        mFragments.add(ProjectListFragment.getInstance("2"));
        mFragments.add(ProjectListFragment.getInstance("3"));
        mPageView.setAdapter(new AppFragmentPagerAdapter(getSupportFragmentManager(), mFragments, mTabs));
        mPageView.setCurrentItem(0);
        mTabView.setViewPager(mPageView);
        mPageView.setOffscreenPageLimit(mFragments.size());
    }

    @OnClick({R.id.ll_header_back, R.id.ll_header_right, R.id.tv_add_project, R.id.tv_add_project_child})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_header_back:
                finish();
                break;
            case R.id.ll_header_right:
                break;
            case R.id.tv_add_project:
                gotoActivity(AddProjectActivity.class, 0);
                break;
            case R.id.tv_add_project_child:
                Bundle bundle = new Bundle();
                bundle.putBoolean("child", true);
                gotoActivity(AddProjectActivity.class, bundle, 0);
                break;
        }
    }
}
