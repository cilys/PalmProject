package com.aopcloud.palmproject;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.aopcloud.base.base.BaseActivity;
import com.aopcloud.base.log.Logcat;
import com.aopcloud.palmproject.ui.activity.mine.LoginActivity;
import com.aopcloud.palmproject.utils.LoginUserUtil;
import com.aopcloud.palmproject.utils.SharedPreferencesUtils;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @PackageName : com.aopcloud.palmproject
 * @File : SplashActivity.java
 * @Date : 2020/5/30 2020/5/30
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class SplashActivity extends BaseActivity {


    private Handler mTimeHandler = new Handler();
    private Runnable mTimeRunnable;
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
        mTimeHandler.postDelayed(mTimeRunnable=new Runnable() {
            @Override
            public void run() {
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
        },0);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTimeHandler.removeCallbacks(mTimeRunnable);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==0){
            gotoActivity(MainActivity.class);
        }
    }
}