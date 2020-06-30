package com.aopcloud.palmproject.utils;

import android.os.CountDownTimer;
import android.widget.TextView;

import com.aopcloud.base.log.Logcat;

/**
 * @PackageName : com.aopcloud.basic.utils
 * @File : CountDownUtil.java
 * @Date : 2020/4/10 2020/4/10
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class CountDownUtil {
    private int time = 60;
    private int runTime = time;
    private CountDownTimer mCountDownTimer;
    private TextView btnSure;


    public CountDownUtil(TextView btnSure) {
        super();
        this.btnSure = btnSure;
    }

    public CountDownUtil(TextView btnSure, int t) {
        super();
        this.btnSure = btnSure;
        this.time = t;
        this.runTime = t;
    }


    public void stopTimer() {
        runTime = time;
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
            mCountDownTimer.onFinish();
        }
        btnSure.setText("发送验证码");
        btnSure.setEnabled(true);
    }

    public void runTimer() {
        runTime = time;
        mCountDownTimer = new CountDownTimer(time * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Logcat.e("-----------" + millisUntilFinished);
                runTime--;
                btnSure.setEnabled(false);
                btnSure.setText("重获验证码(" + runTime + ")");
            }

            @Override
            public void onFinish() {
                btnSure.setEnabled(true);
//                        btnSure.setBackgroundResource(R.color.theme_1);
                btnSure.setText("重发");
                runTime = time;
            }
        };
    }

    public void runTimer(int time) {
        runTime = time;
        mCountDownTimer = new CountDownTimer(time * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Logcat.e("-----------" + millisUntilFinished);
                runTime--;
                btnSure.setEnabled(false);
                btnSure.setText("" + runTime + "s后再发送");
            }

            @Override
            public void onFinish() {
                btnSure.setEnabled(true);
                btnSure.setText("  重发  ");
                runTime = time;
            }
        };
        mCountDownTimer.start();
    }


}


