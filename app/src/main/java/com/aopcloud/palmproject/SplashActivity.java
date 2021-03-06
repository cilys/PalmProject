package com.aopcloud.palmproject;

import android.content.Intent;
import android.support.annotation.Nullable;

import com.aopcloud.palmproject.ui.activity.BaseAc;
import com.aopcloud.palmproject.ui.activity.mine.LoginActivity;
import com.aopcloud.palmproject.utils.LoginUserUtil;
import com.aopcloud.palmproject.utils.SharedPreferencesUtils;

/**
 * @PackageName : com.aopcloud.palmproject
 * @File : SplashActivity.java
 * @Date : 2020/5/30 2020/5/30
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class SplashActivity extends BaseAc {
    @Override
    protected void initData() {
    }


    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {
        boolean isFirstStart = (boolean) SharedPreferencesUtils.getSP(this, "isFirstStart", true);

        if (isFirstStart) {
            SharedPreferencesUtils.setSP(SplashActivity.this, "isFirstStart", false);
        }
        if (LoginUserUtil.isLogin(SplashActivity.this)){
            gotoActivity(MainActivity.class);
        }else {
            gotoActivity(LoginActivity.class);
        }
        finish();
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==0){
            gotoActivity(MainActivity.class);
        }
    }
}