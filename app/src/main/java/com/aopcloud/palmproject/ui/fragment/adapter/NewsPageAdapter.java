package com.aopcloud.palmproject.ui.fragment.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @PackageName : com.aopcloud.basic.ui.fragment.adapter
 * @File : NewsPageAdapter.java
 * @Date : 2020/4/11 2020/4/11
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class NewsPageAdapter  extends FragmentStatePagerAdapter {


    private List<Fragment> fragments = new ArrayList<>();
    private List<String> mTitle = new ArrayList<>();

    public NewsPageAdapter(FragmentManager fm, List<Fragment> fragments, List<String> mTitles) {
        super(fm);
        this.fragments = fragments;
        this.mTitle = mTitles;
    }


    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitle==null?"":mTitle.get(position);
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
