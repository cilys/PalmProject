package com.aopcloud.palmproject.ui.fragment;

import android.Manifest;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.aopcloud.base.base.BaseFragment;
import com.aopcloud.base.common.BaseEvent;
import com.aopcloud.base.util.ResourceUtil;
import com.aopcloud.base.util.ToastUtil;
import com.aopcloud.palmproject.BuildConfig;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.api.ApiConstants;
import com.aopcloud.palmproject.bean.UserBean;
import com.aopcloud.palmproject.common.ResultBean;
import com.aopcloud.palmproject.loader.AppImageLoader;
import com.aopcloud.palmproject.ui.activity.enterprise.JoinEnterpriseActivity;
import com.aopcloud.palmproject.ui.adapter.feagment.AppFragmentPagerAdapter;
import com.aopcloud.palmproject.ui.fragment.home.HomeProjectFragment;
import com.aopcloud.palmproject.ui.fragment.home.HomeTaskFragment;
import com.aopcloud.palmproject.utils.LoginUserUtil;
import com.aopcloud.palmproject.view.CircleImageView;
import com.cilys.app.view.NoScrollViewPager;
import com.hjq.permissions.OnPermission;
import com.hjq.permissions.XXPermissions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * @PackageName : com.aopcloud.basic.ui.fragment
 * @File : HomeFragment.java
 * @Date : 2020/4/10 2020/4/10
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class HomeFragment extends BaseFragment implements ViewPager.OnPageChangeListener {


    @BindView(R.id.iv_avatar)
    CircleImageView mIvAvatar;
    @BindView(R.id.tv_city)
    TextView mTvCity;
    @BindView(R.id.iv_city)
    ImageView mIvCity;
    @BindView(R.id.iv_scan_qr_code)
    ImageView mIvScanQrCode;
    @BindView(R.id.tv_task)
    TextView mTvTask;
    @BindView(R.id.tv_project)
    TextView mTvProject;
    @BindView(R.id.page_view)
//    ViewPager mPageView;
    NoScrollViewPager mPageView;


    private DrawerLayout mDrawerLayout;
    private List<Fragment> mFragments = new ArrayList<>();

    public void setDrawerLayout(DrawerLayout drawerLayout) {
        mDrawerLayout = drawerLayout;
    }

    protected int setLayoutId() {
        return R.layout.fragment_home;
    }


    @Override
    protected void onFirstVisible() {
        super.onFirstVisible();
        initImmersionBar();
    }

    @Override
    protected void onVisible() {
        super.onVisible();
        toRequest(ApiConstants.EventTags.user_info);
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.reset().fitsSystemWindows(true)
                .statusBarDarkFont(true)
                .navigationBarEnable(true)
                .autoNavigationBarDarkModeEnable(true, 0.2f)
                .navigationBarColor("#111111")
                .statusBarColor(R.color.theme_transparent)
                .init();
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        if (mPageView != null){
            mPageView.setScrollable(false);
        }

        mPageView.addOnPageChangeListener(this);

        if (LoginUserUtil.isLogin(mActivity) && null != LoginUserUtil.getLoginUserBean(mActivity)) {
            AppImageLoader.loadCircleImage(mActivity, BuildConfig.BASE_URL + LoginUserUtil.getLoginUserBean(mActivity).getAvatar(), mIvAvatar);
        }
        mFragments.clear();
        mFragments.add(HomeTaskFragment.getInstance());
        mFragments.add(HomeProjectFragment.getInstance());
        mPageView.setAdapter(new AppFragmentPagerAdapter(getChildFragmentManager(), mFragments, null));
        mPageView.setOffscreenPageLimit(mFragments.size());
        selectTab(0);
    }

    private void setLoginUser(UserBean userBean) {
        if (userBean != null) {
            if (LoginUserUtil.isLogin(mActivity) && null != LoginUserUtil.getLoginUserBean(mActivity)) {
                AppImageLoader.loadCircleImage(mActivity, BuildConfig.BASE_URL + LoginUserUtil.getLoginUserBean(mActivity).getAvatar(), mIvAvatar);
            }
        } else {
        }
    }

    @OnClick({R.id.iv_avatar, R.id.tv_city, R.id.iv_city, R.id.iv_scan_qr_code, R.id.tv_task, R.id.tv_project})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_avatar:
                if (mDrawerLayout != null) {
                    mDrawerLayout.openDrawer(Gravity.START);
                }
                break;
            case R.id.tv_city:
            case R.id.iv_city:
                break;
            case R.id.iv_scan_qr_code:
                List<String> list = new ArrayList<>();
                list.add(Manifest.permission.READ_EXTERNAL_STORAGE);
                list.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                list.add(Manifest.permission.CAMERA);
                XXPermissions.with(mActivity).permission(list).request(new OnPermission() {
                    @Override
                    public void hasPermission(List<String> granted, boolean isAll) {
                        if (isAll) {
//                            gotoActivity(QrCodeScanActivity.class, 0);
                            Bundle bundle = new Bundle();
                            bundle.putString("activityTo", "QrCodeScanActivity");
                            gotoActivity(JoinEnterpriseActivity.class, bundle);
                        } else {
                            ToastUtil.showToast("请先开启权限");
                        }
                    }

                    @Override
                    public void noPermission(List<String> denied, boolean quick) {
                        ToastUtil.showToast("请开启相机以及内存卡读写权限");
                    }
                });
                break;
            case R.id.tv_task:
                selectTab(0);
                break;
            case R.id.tv_project:
                selectTab(1);
                break;
        }
    }

    public void selectTab(int index) {
        mPageView.setCurrentItem(index);
        if (index == 0) {
            mTvTask.setTextColor(ResourceUtil.getColor(R.color.theme_font_orange));
            mTvProject.setTextColor(ResourceUtil.getColor(R.color.theme_font_black));
            mTvTask.setTextSize(18);
            mTvTask.setTypeface(Typeface.DEFAULT_BOLD);
            mTvProject.setTypeface(Typeface.DEFAULT);
            mTvProject.setTextSize(16);
        } else {
            mTvTask.setTextColor(ResourceUtil.getColor(R.color.theme_font_black));
            mTvProject.setTextColor(ResourceUtil.getColor(R.color.theme_font_orange));
            mTvTask.setTextSize(16);
            mTvProject.setTextSize(18);
            mTvTask.setTypeface(Typeface.DEFAULT);
            mTvProject.setTypeface(Typeface.DEFAULT_BOLD);
        }
    }


    @Override
    public void toRequest(int eventTag) {
        super.toRequest(eventTag);
        Map map = new HashMap();
        map.put("token", "" + LoginUserUtil.getToken(mActivity));
        if (eventTag == ApiConstants.EventTags.user_info) {
            iCommonRequestPresenter.requestPost(eventTag, mActivity, ApiConstants.user_info, map);
        }
    }

    @Override
    public void getRequestData(int eventTag, String result) {
        super.getRequestData(eventTag, result);
        mActivity.dismissPopupLoading();
        ResultBean bean = JSON.parseObject(result, ResultBean.class);
        if (bean != null && bean.getCode() == 0) {
            if (eventTag == ApiConstants.EventTags.user_info) {
                UserBean userBean = JSON.parseObject(bean.getData(), UserBean.class);
                setLoginUser(userBean);
            }
        } else {
            ToastUtil.showToast(bean != null ? bean.getMsg() : "加载错误，请重试");
            if (bean.getCode() == 1 || bean.getCode() == 22) {
                EventBus.getDefault().post(new BaseEvent(BaseEvent.EVENT_LOGOUT));
            }
        }

    }

    @Override
    public void onRequestFailureException(int eventTag, String msg) {
        super.onRequestFailureException(eventTag, msg);
    }

    @Override
    public void onEvent(BaseEvent postResult) {
        super.onEvent(postResult);
        if (postResult.type.equals(BaseEvent.EVENT_LOGIN)) {
            toRequest(ApiConstants.EventTags.user_info);
        }
        if (postResult.type.equals(BaseEvent.EVENT_LOGOUT)) {
            setLoginUser(null);
        }
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {


    }

    @Override
    public void onPageSelected(int i) {
        selectTab(i);
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}
