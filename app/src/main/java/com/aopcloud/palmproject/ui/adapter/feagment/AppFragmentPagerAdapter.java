package com.aopcloud.palmproject.ui.adapter.feagment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.List;

/**
 * @PackageName : com.aopcloud.palmproject.ui.adapter.feagment
 * @File : AppFragmentPagerAdapter.java
 * @Date : 2020/4/21 2020/4/21
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class AppFragmentPagerAdapter extends android.support.v4.app.FragmentPagerAdapter {
    private List<String> titleList;
    private List<Fragment> fragmentList;

    public AppFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragmentList, List<String> titleList) {
        super(fm);
        this.fragmentList=fragmentList;
        this.titleList = titleList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return  fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleList==null?"":titleList.get(position % titleList.size());
    }
}
