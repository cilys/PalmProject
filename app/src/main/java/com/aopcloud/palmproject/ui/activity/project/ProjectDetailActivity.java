package com.aopcloud.palmproject.ui.activity.project;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.aopcloud.base.annotation.Layout;
import com.aopcloud.base.util.ToastUtil;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.api.ApiConstants;
import com.aopcloud.palmproject.bean.PopMenuBean;
import com.aopcloud.palmproject.common.ResultBean;
import com.aopcloud.palmproject.loader.AppImageLoader;
import com.aopcloud.palmproject.ui.activity.BaseAc;
import com.aopcloud.palmproject.ui.activity.project.bean.ProjectDetailBean;
import com.aopcloud.palmproject.ui.adapter.feagment.AppFragmentPagerAdapter;
import com.aopcloud.palmproject.ui.fragment.home.HomeProjectFragment;
import com.aopcloud.palmproject.ui.fragment.project.DashboardFragment;
import com.aopcloud.palmproject.ui.fragment.project.ImagesFragment;
import com.aopcloud.palmproject.ui.fragment.project.ProjectLogFragment;
import com.aopcloud.palmproject.ui.fragment.project.ProjectTaskFragment;
import com.aopcloud.palmproject.utils.LoginUserUtil;
import com.aopcloud.palmproject.view.PopContextMenu;
import com.cily.utils.base.StrUtils;
import com.flyco.tablayout.SlidingTabLayout;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.util.ArrayList;
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
@Layout(R.layout.activity_project_detail)
public class ProjectDetailActivity extends BaseAc {

    @BindView(R.id.ll_header_back)
    LinearLayout mLlHeaderBack;
    @BindView(R.id.tv_header_title)
    TextView mTvHeaderTitle;
    @BindView(R.id.ll_header_right)
    LinearLayout mLlHeaderRight;
    @BindView(R.id.iv_qr_code)
    ImageView mIvQrCode;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.progress_view)
    SeekBar mProgressView;
    @BindView(R.id.tv_progress)
    TextView mTvProgress;
    @BindView(R.id.tv_state)
    TextView mTvState;
    @BindView(R.id.tv_manager)
    TextView mTvManager;
    @BindView(R.id.tv_start_time)
    TextView mTvStartTime;
    @BindView(R.id.tv_end_time)
    TextView mTvEndTime;
    @BindView(R.id.tab_view)
    SlidingTabLayout mTabView;
    @BindView(R.id.page_view)
    ViewPager mPageView;
    @BindView(R.id.tv_team)
    TextView mTvTeam;
    @BindView(R.id.iv_header_more)
    ImageView mIvHeaderMore;
    @BindView(R.id.ll_progress)
    LinearLayout mLlProgress;


    private String project_id;
    private List<Fragment> mFragments;
    private List<String> mTabs;
    private ProjectDetailBean mProjectDetailBean;
    private String project_status;

    private String start_date, end_date;

    @Override
    protected void initData() {
        super.initData();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            project_id = bundle.getString("project_id");
            project_name = bundle.getString("project_name");
            company_id = bundle.getString("company_id");
            company_name = bundle.getString("company_name");
            project_status = bundle.getString("project_status");

            start_date = bundle.getString("start_date");
            end_date = bundle.getString("end_date");
        }
        toRequest(ApiConstants.EventTags.project_get);
    }

    private String project_name, company_id, company_name;
    @Override
    protected void initView() {
        mFragments = new ArrayList<>();
        mTabs = new ArrayList<>();
        mTabs.add("看板");
        mTabs.add("工单");
//        mTabs.add("预算");
//        mTabs.add("成本");
//        mTabs.add("物资");
        mTabs.add("影像");
        mTabs.add("日报");
        mFragments.add(DashboardFragment.getInstance(project_id));

        ProjectTaskFragment projectTaskFragment = new ProjectTaskFragment();
        Bundle taskBundle = new Bundle();
        taskBundle.putString("project_id", project_id);
        taskBundle.putString("project_name", project_name);
        taskBundle.putString("company_id", company_id);
        taskBundle.putString("company_name", company_name);
        taskBundle.putString("project_status", project_status);
        taskBundle.putString("start_date", start_date);
        taskBundle.putString("end_date", end_date);

        projectTaskFragment.setArguments(taskBundle);
        mFragments.add(projectTaskFragment);
//        mFragments.add(ProjectTaskFragment.getInstance(project_id));
//        mFragments.add(AwaitFragment.getInstance());
//        mFragments.add(AwaitFragment.getInstance());
//        mFragments.add(AwaitFragment.getInstance());
        mFragments.add(ImagesFragment.getInstance(project_id));
        mFragments.add(ProjectLogFragment.getInstance(project_id));
        mPageView.setAdapter(new AppFragmentPagerAdapter(getSupportFragmentManager(), mFragments, mTabs));
        mPageView.setCurrentItem(0);
        mTabView.setViewPager(mPageView);
        mPageView.setOffscreenPageLimit(mFragments.size());
        mProgressView.setPadding(0, 0, 0, 0);
        mProgressView.setMax(100);
    }

    @Override
    protected void setListener() {
        super.setListener();
        mProgressView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }

    private void setViewData(ProjectDetailBean detailBean) {
        mProjectDetailBean = detailBean;
        mTvName.setText(detailBean.getName());
        mProgressView.setProgress(detailBean.getProgress());
        mTvProgress.setText(detailBean.getProgress() + "%");
        mTvManager.setText(detailBean.getLeader_name());
        mTvStartTime.setText(detailBean.getStart_date());
        mTvEndTime.setText(detailBean.getEnd_date());
        mTvState.setText(detailBean.getStatus());
        Bitmap mLogo = CodeUtils.createImage(detailBean.getCode(), 500, 500, null);
        AppImageLoader.load(this, mLogo, mIvQrCode);
    }

    @OnClick({R.id.ll_header_back, R.id.iv_qr_code, R.id.tv_team, R.id.ll_header_right, R.id.ll_progress})
    public void onViewClicked(View view) {
        Bundle bundle;
        switch (view.getId()) {
            case R.id.ll_header_back:
                finish();
                break;
            case R.id.iv_qr_code:
                bundle = new Bundle();
                bundle.putString("project_id", "" + project_id);
                gotoActivity(ProjectMoreDetailActivity.class, bundle, 0);
                break;
            case R.id.tv_team:
                bundle = new Bundle();
                bundle.putString("project_id", project_id);
                bundle.putString("project_name", "" + mProjectDetailBean.getName());
                gotoActivity(ProjectMemberActivity.class, bundle, 0);
                break;
            case R.id.ll_header_right:

                final List<PopMenuBean> menuItems = new ArrayList<>();
//                menuItems.add(new PopMenuBean("关注"));
//                menuItems.add(new PopMenuBean("编辑"));
//                menuItems.add(new PopMenuBean("完成"));
//                menuItems.add(new PopMenuBean("移交/退出"));
                menuItems.add(new PopMenuBean("编辑"));
                menuItems.add(new PopMenuBean("状态"));
                menuItems.add(new PopMenuBean("分部"));
                menuItems.add(new PopMenuBean("删除"));
                PopContextMenu contextMenu = new PopContextMenu(this)
                        .addMenuList(menuItems)
                        .dimBackground(true)
                        .setOnItemSelectListener(new PopContextMenu.OnItemSelectListener() {
                            @Override
                            public void onItemSelect(int position) {
                                if (position == 0) {
                                    Bundle bundle = new Bundle();
                                    bundle.putString("bean", "" + JSON.toJSONString(mProjectDetailBean));
                                    gotoActivity(EditProjectActivity.class, bundle, 0);
                                } else if (position == 1) {
                                    showProjectStatusDialog();
                                }
                            }
                        });
                contextMenu.showMenu(mLlHeaderRight);
                break;
            case R.id.ll_progress:
//                showProjectStatusDialog();
                break;
        }
    }

    private void toProjectProgress(){
//        Bundle bundle = new Bundle();
//        bundle.putString("project_id", "" + project_id);
//        gotoActivity(ProjectProgressActivity.class, bundle, 0);

        toRequest(ApiConstants.EventTags.project_feedback);
    }

    private String projectStatus;
    @Override
    public void toRequest(int eventTag) {
        super.toRequest(eventTag);
        Map map = new HashMap();
        map.put("token", "" + LoginUserUtil.getToken(this));
        map.put("code", "" + LoginUserUtil.getCurrentEnterpriseNo(this));
        map.put("project_id", "" + project_id);//项目名称
        if (eventTag == ApiConstants.EventTags.project_get) {
            iCommonRequestPresenter.requestPost(eventTag, this, ApiConstants.project_get, map);
        } else if (eventTag == ApiConstants.EventTags.project_feedback) {
            map.put("status", projectStatus);
            map.put("progress", "" + mProgressView.getProgress());
            iCommonRequestPresenter.requestPost(eventTag, this, ApiConstants.project_feedback, map);
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
            } else if (eventTag == ApiConstants.EventTags.project_feedback) {
                if (!StrUtils.isEmpty(projectStatus)) {
                    mTvState.setText(projectStatus);
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
        toRequest(ApiConstants.EventTags.project_get);
    }

    private void showProjectStatusDialog(){
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.setContentView(R.layout.dialog_project_status);
        dialog.show();
        dialog.findViewById(R.id.tv_state_design).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                projectStatus = HomeProjectFragment.STATE_design;
                toProjectProgress();
            }
        });
        dialog.findViewById(R.id.tv_state_ready).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                projectStatus = HomeProjectFragment.STATE_ready;
                toProjectProgress();
            }
        });
        dialog.findViewById(R.id.tv_state_build).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                projectStatus = HomeProjectFragment.STATE_build;
                toProjectProgress();
            }
        });
        dialog.findViewById(R.id.tv_state_check).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
//                projectStatus = HomeProjectFragment.STATE_c;
                toProjectProgress();
            }
        });
        dialog.findViewById(R.id.tv_state_completed).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                projectStatus = HomeProjectFragment.STATE_completed;
                toProjectProgress();
            }
        });
        dialog.findViewById(R.id.tv_state_finish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                projectStatus = HomeProjectFragment.STATE_finish;
                toProjectProgress();
            }
        });
        dialog.findViewById(R.id.tv_state_termination).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                projectStatus = HomeProjectFragment.STATE_termination;
                toProjectProgress();
            }
        });
        dialog.findViewById(R.id.tv_state_stop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                projectStatus = HomeProjectFragment.STATE_stop;
                toProjectProgress();
            }
        });
        dialog.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}