package com.aopcloud.palmproject.ui.activity.enterprise;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aopcloud.base.annotation.Layout;
import com.aopcloud.base.base.BaseActivity;
import com.aopcloud.base.util.ToastUtil;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.ui.activity.department.EnterpriseDepartmentActivity;
import com.aopcloud.palmproject.utils.LoginUserUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @PackageName : com.aopcloud.palmproject.ui.activity.enterprise
 * @File : EnterpriseSettingActivity.java
 * @Date : 2020/4/20 2020/4/20
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
@Layout(R.layout.activity_enterprise_setting)
public class EnterpriseSettingActivity extends BaseActivity {

    @BindView(R.id.ll_header_back)
    LinearLayout mLlHeaderBack;
    @BindView(R.id.tv_header_title)
    TextView mTvHeaderTitle;
    @BindView(R.id.tv_header_right)
    TextView mTvHeaderRight;
    @BindView(R.id.ll_header_right)
    LinearLayout mLlHeaderRight;
    @BindView(R.id.ll_common_layout)
    LinearLayout mLlCommonLayout;
    @BindView(R.id.rl_enterprise_manager)
    RelativeLayout mRlEnterpriseManager;
    @BindView(R.id.rl_enterprise_director)
    RelativeLayout mRlEnterpriseDirector;
    @BindView(R.id.rl_enterprise_department)
    RelativeLayout mRlEnterpriseDepartment;

    @Override
    protected void initView() {
        mTvHeaderTitle.setText("企业系统设置");

    }


    @OnClick({R.id.ll_header_back, R.id.ll_header_right, R.id.rl_enterprise_manager,
            R.id.rl_enterprise_director, R.id.rl_enterprise_department})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_header_back:
                finish();
                break;
            case R.id.ll_header_right:
                break;
            case R.id.rl_enterprise_manager:
                if (LoginUserUtil.getLoginUserBean(this).getId() == LoginUserUtil.getCurrentEnterpriseBean(this).getLeader_id()) {
                    gotoActivity(EnterpriseManagerActivity.class);
                } else {
                    ToastUtil.showToast("您没有权限");
                }
                break;
            case R.id.rl_enterprise_director:
                gotoActivity(EnterpriseSettingDirectorActivity.class);
                break;
            case R.id.rl_enterprise_department:
                gotoActivity(EnterpriseDepartmentActivity.class);
                break;
        }
    }
}