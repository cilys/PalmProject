package com.aopcloud.palmproject.ui.activity.task;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.aopcloud.base.annotation.Layout;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.ui.activity.BaseAc;
import com.aopcloud.palmproject.ui.adapter.feagment.AppFragmentPagerAdapter;
import com.aopcloud.palmproject.ui.fragment.task.TaskChildFragment;
import com.aopcloud.palmproject.ui.fragment.task.TaskExecuteFragment;
import com.aopcloud.palmproject.ui.fragment.task.TaskMemberFragment;
import com.aopcloud.palmproject.ui.fragment.task.TaskRecordFragment;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @PackageName : com.aopcloud.palmproject.ui.activity.task
 * @File : TaskDetailActivity.java
 * @Date : 2020/5/8 2020/5/8
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
@Layout(R.layout.activity_task_detail)
public class TaskDetailActivity extends BaseAc {
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
    @BindView(R.id.tab_view)
    SlidingTabLayout mTabView;
    @BindView(R.id.page_view)
    ViewPager mPageView;
    private List<Fragment> mFragments;
    private List<String> mTabs;

    private String task_name;
    private String task_id;
    private String project_id;
    private String team_id;

    @Override
    protected void initData() {
        super.initData();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            task_id = bundle.getString("task_id");
            project_id = bundle.getString("project_id");
            task_name =bundle.getString("task_name");
            team_id = bundle.getString("team_id");
        }
    }

    @Override
    protected void initView() {
        mTvHeaderTitle.setText(""+task_name);

        mFragments = new ArrayList<>();
        mTabs = new ArrayList<>();
        mTabs.add("执行");
        mTabs.add("人工");
        mTabs.add("人员");
        mTabs.add("子任务");
        mFragments.add(TaskExecuteFragment.getInstance(task_id, project_id));
        mFragments.add(TaskRecordFragment.getInstance(task_id));
        mFragments.add(TaskMemberFragment.getInstance(task_id));
        mFragments.add(TaskChildFragment.getInstance(task_id,task_name,team_id));
        mPageView.setAdapter(new AppFragmentPagerAdapter(getSupportFragmentManager(), mFragments, mTabs));
        mPageView.setCurrentItem(0);
        mTabView.setViewPager(mPageView);
        mPageView.setOffscreenPageLimit(mFragments.size());
    }


    @OnClick({R.id.ll_header_back, R.id.ll_header_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_header_back:
                finish();
                break;
            case R.id.ll_header_right:
                PopupMenu popupMenu = new PopupMenu(this, view);
                popupMenu.getMenuInflater().inflate(R.menu.menu_task_detail, popupMenu.getMenu());
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        popupMenu.dismiss();
                        return false;
                    }
                });
                break;
        }
    }


}
