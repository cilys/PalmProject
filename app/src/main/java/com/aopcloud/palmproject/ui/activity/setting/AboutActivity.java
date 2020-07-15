package com.aopcloud.palmproject.ui.activity.setting;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aopcloud.base.annotation.Layout;
import com.aopcloud.base.util.ToastUtil;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.ui.activity.BaseAc;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @PackageName : com.aopcloud.basic.ui.activity.setting
 * @File : AboutActivity.java
 * @Date : 2020/4/10 2020/4/10
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
@Layout(R.layout.activity_about)
public class AboutActivity extends BaseAc {
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
    @BindView(R.id.iv_logo)
    ImageView mIvLogo;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_version_name)
    TextView mTvVersionName;
    @BindView(R.id.tv_version_log)
    TextView mTvVersionLog;

    @Override
    protected void initView() {

    }

    @OnClick({R.id.ll_header_back, R.id.tv_version_log})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_header_back:finish();
                break;
            case R.id.tv_version_log:
                ToastUtil.showToast("暂无更新日志");
                break;
        }
    }
}
