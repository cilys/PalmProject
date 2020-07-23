package com.aopcloud.palmproject.ui.fragment;

import com.aopcloud.base.base.BaseFragment;
import com.aopcloud.palmproject.utils.LoginUserUtil;

public abstract class BaseFg extends BaseFragment {


    protected String getUserId(){
        return "" + LoginUserUtil.getLoginUserBean(getActivity()).getId();
    }
}
