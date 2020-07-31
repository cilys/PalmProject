package com.aopcloud.palmproject.ui.fragment;

import com.aopcloud.base.base.BaseFragment;
import com.aopcloud.palmproject.bean.UserBean;
import com.aopcloud.palmproject.utils.LoginUserUtil;
import com.aopcloud.palmproject.utils.SpUtils;
import com.aopcloud.palmproject.utils.user.UserUtils;
import com.cily.utils.base.StrUtils;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public abstract class BaseFg extends BaseFragment {


    protected String getUserId(){
        UserBean ub =  LoginUserUtil.getLoginUserBean(getActivity());
        return ub == null ? "" : String.valueOf(ub.getId());
    }

    /**
     * 是否可以创建工单
     * @return
     */
    protected boolean canAddProjectOrder(){
        String type = LoginUserUtil.getUserRole(getActivity());
        return UserUtils.canAddOrder(type);
    }

    protected String[] getRecentlyUsedProjects(){
        Set<String> sets = SpUtils.getRecentlyUsedProjects(getActivity());
        if (sets != null) {
           return sets.toArray(new String[sets.size()]);
        }
        return null;
    }


    private LinkedHashSet<String> getSaveSets(String[] strs, String key){
        if (StrUtils.isEmpty(key)) {
            return null;
        }

        LinkedHashSet<String> set = new LinkedHashSet<>();

        if (strs == null) {
            set.add(key);
        } else {
            if (strs.length == 0) {
                set.add(key);
            }else if (strs.length == 1) {
                String s = strs[0];
                if (key.equals(s)) {
                    set.add(s);
                } else {
                    set.add(key);
                    set.add(s);
                }
            } else if (strs.length == 2) {
                String s0 = strs[0];
                String s1 = strs[1];
                if (key.equals(s0)) {
                    set.add(s0);
                    set.add(s1);
                } else if (key.equals(s1)) {
                    set.add(s1);
                    set.add(s0);
                } else {
                    set.add(key);
                    set.add(s0);
                    set.add(s1);
                }
            } else if (strs.length == 3) {
                String s0 = strs[0];
                String s1 = strs[1];
                String s2 = strs[1];
                if (key.equals(s0)) {
                    set.add(s0);
                    set.add(s1);
                    set.add(s2);
                } else if (key.equals(s1)) {
                    set.add(s1);
                    set.add(s0);
                    set.add(s2);
                } else if (key.equals(s2)) {
                    set.add(s2);
                    set.add(s0);
                    set.add(s1);
                } else {
                    set.add(key);
                    set.add(s0);
                    set.add(s1);
                }
            }
        }
        return set;
    }

    protected String[] getRecentlyUsedTasks(){
        Set<String> sets = SpUtils.getRecentlyUsedTasks(getActivity());
        if (sets != null) {
            return sets.toArray(new String[sets.size()]);
        }
        return null;
    }
}
