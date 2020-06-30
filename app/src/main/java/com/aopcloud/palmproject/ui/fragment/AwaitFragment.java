package com.aopcloud.palmproject.ui.fragment;

import com.aopcloud.base.base.BaseFragment;
import com.aopcloud.palmproject.R;

/**
 * @PackageName : com.aopcloud.palmproject.ui.fragment
 * @File : AwaitFragment.java
 * @Date : 2020/5/2 2020/5/2
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class AwaitFragment extends BaseFragment {

    public static AwaitFragment getInstance() {
        AwaitFragment fragment = new AwaitFragment();
        return fragment;
    }
    @Override
    protected int setLayoutId() {
        return R.layout.fragment_await;
    }
}
