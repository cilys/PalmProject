package com.aopcloud.palmproject.ui.fragment;

import com.aopcloud.base.base.BaseFragment;
import com.aopcloud.palmproject.bean.UserBean;
import com.aopcloud.palmproject.utils.LoginUserUtil;
import com.aopcloud.palmproject.utils.user.UserUtils;

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
}
