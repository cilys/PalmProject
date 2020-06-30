package com.aopcloud.palmproject.listener;

import java.util.List;

/**
 * @PackageName : com.aopcloud.palmproject.listener
 * @File : OnTaskFilterListener.java
 * @Date : 2020/6/6 2020/6/6
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public interface OnTaskFilterListener {
    void onReset();

    void onFilter(String search, List<String> state, List<String> level);

}