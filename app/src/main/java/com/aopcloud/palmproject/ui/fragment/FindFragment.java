package com.aopcloud.palmproject.ui.fragment;

import com.aopcloud.base.base.BaseFragment;
import com.aopcloud.palmproject.R;

/**
 * @PackageName : com.aopcloud.palmproject.ui.fragment
 * @File : FindFragment.java
 * @Date : 2020/4/14 2020/4/14
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class FindFragment extends BaseFragment {

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.reset().fitsSystemWindows(true)
                .statusBarDarkFont(true)
                .navigationBarEnable(true)
                .autoNavigationBarDarkModeEnable(true, 0.2f)
                .navigationBarColor("#111111")
                .statusBarColor(R.color.theme_background_f5)
                .init();
    }
    @Override
    protected int setLayoutId() {
        return R.layout.fragment_await;
    }
}
