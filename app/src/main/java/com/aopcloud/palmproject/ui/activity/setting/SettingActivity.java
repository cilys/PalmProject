package com.aopcloud.palmproject.ui.activity.setting;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.aopcloud.base.annotation.Layout;
import com.aopcloud.base.common.BaseEvent;
import com.aopcloud.base.util.ToastUtil;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.common.Constants;
import com.aopcloud.palmproject.ui.activity.BaseAc;
import com.aopcloud.palmproject.ui.activity.mine.AccountSettingActivity;
import com.aopcloud.palmproject.ui.activity.mine.LoginActivity;
import com.aopcloud.palmproject.ui.activity.web.WebActivity;
import com.aopcloud.palmproject.utils.CleanUtil;
import com.aopcloud.palmproject.utils.LoginUserUtil;
import com.aopcloud.palmproject.utils.ThreadUtil;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * @PackageName : com.aopcloud.basic.ui.activity.setting
 * @File : SettingActivity.java
 * @Date : 2020/4/10 2020/4/10
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
@Layout(R.layout.activity_setting)
public class SettingActivity extends BaseAc {
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
    @BindView(R.id.rl_account_setting)
    RelativeLayout mRlAccountSetting;
    @BindView(R.id.rl_about)
    RelativeLayout mRlAbout;
    @BindView(R.id.rl_clean_cache)
    RelativeLayout mRlCleanCache;
    @BindView(R.id.rl_push_setting)
    RelativeLayout mRlPushSetting;
    @BindView(R.id.rl_fingerprint_setting)
    RelativeLayout mRlFingerprintSetting;
    @BindView(R.id.rl_no_disturb)
    RelativeLayout mRlNoDisturb;
    @BindView(R.id.tv_cache_size)
    TextView mTvCacheSize;
    @BindView(R.id.sw_fingerprint_setting)
    Switch mSwFingerprintSetting;
    @BindView(R.id.sw_no_disturb)
    Switch mSwNoDisturb;
    @BindView(R.id.ll_logout)
    LinearLayout mLlLogout;


    @Override
    protected void initView() {
        mTvHeaderTitle.setText(R.string.mine_setting);
        File getCache = SettingActivity.this.getApplication().getCacheDir();
        try {
            long cache = CleanUtil.getFolderSize(getCache);
            mTvCacheSize.setText(CleanUtil.getFormatSize(cache));
            Log.i(TAG, "缓存文件大小：" + cache + " 格式化之后：" + CleanUtil.getFormatSize(cache));
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (LoginUserUtil.isLogin(this)) {
            mLlLogout.setVisibility(View.VISIBLE);
        } else {
            mLlLogout.setVisibility(View.GONE);
        }
    }


    @OnClick({R.id.ll_header_back, R.id.tv_header_right, R.id.ll_header_right,
            R.id.ll_common_layout, R.id.rl_account_setting, R.id.rl_about, R.id.rl_clean_cache,
            R.id.rl_push_setting, R.id.rl_fingerprint_setting, R.id.rl_no_disturb,R.id.ll_logout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_header_back:
                finish();
                break;
            case R.id.tv_header_right:
                break;
            case R.id.ll_header_right:
                break;
            case R.id.ll_common_layout:
                break;
            case R.id.rl_account_setting:
                if (LoginUserUtil.isLogin(this)) {
                    gotoActivity(AccountSettingActivity.class);
                } else {
                    gotoActivity(LoginActivity.class);
                }
                break;
            case R.id.rl_about:

//                gotoActivity(AboutActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("title",""+"关于我们");
                bundle.putString("url",""+ Constants.URL_ABOUT);
                gotoActivity(WebActivity.class,bundle,0);
                break;
            case R.id.rl_clean_cache:
                cleanCache();
                break;
            case R.id.rl_push_setting:
                gotoActivity(PushSettingActivity.class);
                break;
            case R.id.rl_fingerprint_setting:
                break;
            case R.id.rl_no_disturb:
                break;
            case R.id.ll_logout:
                showPopXupLoading("退出中...");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        LoginUserUtil.exitLogin(SettingActivity.this);
                        EventBus.getDefault().post(new BaseEvent(BaseEvent.EVENT_LOGOUT));
                        dismissPopupLoading();
                        gotoActivity(LoginActivity.class);
                        finish();
                    }
                }, 2000);
                break;
        }
    }


    /**
     * 清理缓存
     */
    private void cleanCache() {
        ToastUtil.showToast("缓存清理中...");
        new CountDownTimer(1500, 500) {
            @Override
            public void onTick(long l) {
            }

            @Override
            public void onFinish() {
                File getCache = SettingActivity.this.getApplication().getCacheDir();
                cleanFile(getCache);
                ThreadUtil.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showToast("缓存清理完成");
                        File getCache = SettingActivity.this.getApplication().getCacheDir();
                        long cache = 0;
                        try {
                            cache = CleanUtil.getFolderSize(getCache);
                            mTvCacheSize.setText(CleanUtil.getFormatSize(cache));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }.start();
    }


    /**
     * 清除目录下的所有文件
     *
     * @param cacheDir
     */
    public void cleanFile(File cacheDir) {
        try {
            File[] files = cacheDir.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isFile()) {
                    files[i].delete();
                }
                if (files[i].isDirectory()) {
                    cleanFile(files[i]);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
