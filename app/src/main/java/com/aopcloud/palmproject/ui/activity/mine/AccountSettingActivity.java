package com.aopcloud.palmproject.ui.activity.mine;

import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aopcloud.base.annotation.Layout;
import com.aopcloud.base.base.BaseActivity;
import com.aopcloud.base.common.BaseEvent;
import com.aopcloud.base.util.ToastUtil;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.utils.LoginUserUtil;

import butterknife.BindView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * @PackageName : com.aopcloud.basic.ui.activity.mine
 * @File : AccountSettingActivity.java
 * @Date : 2020/4/10 2020/4/10
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
@Layout(R.layout.activity_mine_setting)
public class AccountSettingActivity extends BaseActivity {
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
    @BindView(R.id.tv_mobile)
    TextView mTvMobile;
    @BindView(R.id.iv_mobile)
    ImageView mIvMobile;
    @BindView(R.id.rl_update_mobile)
    RelativeLayout mRlUpdateMobile;
    @BindView(R.id.iv_update_password)
    ImageView mIvUpdatePassword;
    @BindView(R.id.rl_update_password)
    RelativeLayout mRlUpdatePassword;


    @Override
    protected void initView() {
        mTvHeaderTitle.setText("账户设置");


    }

    @OnClick({R.id.ll_header_back, R.id.tv_mobile, R.id.iv_mobile, R.id.rl_update_mobile, R.id.iv_update_password, R.id.rl_update_password})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_header_back:
                setResult(0);
                finish();
                break;
            case R.id.tv_mobile:
            case R.id.iv_mobile:
            case R.id.rl_update_mobile:
                if (!LoginUserUtil.isLogin(this)) {
                    ToastUtil.showToast("请先登录");
                    gotoActivity(LoginActivity.class, 0);
                } else {
                    ToastUtil.showToast("敬请期待");
                }
                break;
            case R.id.iv_update_password:
            case R.id.rl_update_password:
                if (!LoginUserUtil.isLogin(this)) {
                    ToastUtil.showToast("请先登录");
                    gotoActivity(LoginActivity.class, 0);
                } else {
                    gotoActivity(UpdatePasswordActivity.class);
                }
                break;
        }
    }
}
