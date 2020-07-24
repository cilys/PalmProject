package com.aopcloud.palmproject.ui.fragment;

import com.aopcloud.base.base.BaseFragment;
import com.aopcloud.palmproject.bean.UserBean;
import com.aopcloud.palmproject.utils.LoginUserUtil;

public abstract class BaseFg extends BaseFragment {


    protected String getUserId(){
        UserBean ub =  LoginUserUtil.getLoginUserBean(getActivity());
        return ub == null ? "" : String.valueOf(ub.getId());
    }
}
