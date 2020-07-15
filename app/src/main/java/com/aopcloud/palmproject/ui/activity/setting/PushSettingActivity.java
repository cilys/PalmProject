package com.aopcloud.palmproject.ui.activity.setting;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.aopcloud.base.annotation.Layout;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.ui.activity.BaseAc;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @PackageName : com.aopcloud.basic.ui.activity.setting
 * @File : PushSettingActivity.java
 * @Date : 2020/4/10 2020/4/10
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
@Layout(R.layout.activity_setting_push)
public class PushSettingActivity extends BaseAc {
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
    @BindView(R.id.sw_new_push)
    Switch mSwNewPush;
    @BindView(R.id.rl_push_new)
    RelativeLayout mRlPushNew;
    @BindView(R.id.tv_push_audio)
    TextView mTvPushAudio;
    @BindView(R.id.rl_push_audio)
    RelativeLayout mRlPushAudio;

    @Override
    protected void initView() {
        mTvHeaderTitle.setText("消息推送设置");
    }


    @OnClick({R.id.ll_header_back, R.id.sw_new_push, R.id.rl_push_new, R.id.tv_push_audio, R.id.rl_push_audio})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_header_back:finish();
                break;
            case R.id.sw_new_push:
            case R.id.rl_push_new:
                break;
            case R.id.tv_push_audio:
            case R.id.rl_push_audio:
                break;
        }
    }
}
