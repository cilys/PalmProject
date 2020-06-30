package com.aopcloud.palmproject.ui.activity.enterprise;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aopcloud.base.annotation.Layout;
import com.aopcloud.base.base.BaseActivity;
import com.aopcloud.palmproject.MainActivity;
import com.aopcloud.palmproject.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @PackageName : com.aopcloud.palmproject.ui.activity.enterprise
 * @File : CreateOrJoinActivity.java
 * @Date : 2020/4/19 2020/4/19
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
@Layout(R.layout.activity_enterprise_create_or_join)
public class CreateOrJoinActivity extends BaseActivity {
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
    @BindView(R.id.tv_create)
    TextView mTvCreate;
    @BindView(R.id.tv_join)
    TextView mTvJoin;
    @BindView(R.id.tv_skip)
    TextView mTvSkip;

    @Override
    protected void initView() {
        mTvHeaderTitle.setText("创建或加入");

    }


    @OnClick({R.id.ll_header_back, R.id.tv_create, R.id.tv_join, R.id.tv_skip})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_header_back:
                gotoActivity(MainActivity.class);
                finish();
                break;
            case R.id.tv_create:
                gotoActivity(CreateEnterpriseActivity.class);
                break;
            case R.id.tv_join:
                gotoActivity(JoinEnterpriseActivity.class);
                break;
            case R.id.tv_skip:
                finish();
                break;
        }
    }
}
