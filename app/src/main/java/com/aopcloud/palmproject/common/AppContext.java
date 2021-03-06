package com.aopcloud.palmproject.common;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import com.aopcloud.base.common.AppHelper;
import com.aopcloud.base.log.LogConfig;
import com.aopcloud.palmproject.BuildConfig;
import com.aopcloud.palmproject.loader.AppImageLoader;
import com.aopcloud.palmproject.net.LoggerInterceptor;
import com.guoxiaoxing.phoenix.core.listener.ImageLoader;
import com.guoxiaoxing.phoenix.picker.Phoenix;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.https.HttpsUtils;

import java.util.concurrent.TimeUnit;

import cn.jpush.android.api.JPushInterface;
import okhttp3.OkHttpClient;

/**
 * @PackageName : com.aopcloud.basic
 * @File : AppContext.java
 * @Date : 2019/12/27 10:04
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @VersionCode : V 1.0
 * @Describe ：
 */
public class AppContext extends Application {
    public static Context context;
    public static Handler mHandler;
    private static AppContext instance;
    private static AppContext app;

    public static Typeface typeface = null;

    public AppContext() {
        app = this;
    }

    public static synchronized AppContext getInstance() {
        if (app == null) {
            app = new AppContext();
        }
        return app;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        mHandler = new Handler();
        instance = this;
        AppHelper.init(this);
        LogConfig.LOG_TAG = "dk_log";

        initPhoenix();
        initOkHttp();
        initZXing();

        initPush();
    }

    private void initPush() {
//        JPushInterface.setDebugMode(true);
//        JPushInterface.init(this);

    }

    private void initZXing() {
        ZXingLibrary.initDisplayOpinion(this);

    }

    private void initPhoenix() {
        Phoenix.config().imageLoader(new ImageLoader() {
                    @Override
                    public void loadImage(Context mContext, ImageView imageView, String imagePath, int type) {
                        AppImageLoader.load(mContext, imagePath, imageView);
                    }
                });

    }

    public static void initOkHttp() {
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new LoggerInterceptor(new LoggerInterceptor.Logger() {
                    @Override
                    public void log(String message) {
                        Log.i(AppContext.class.getSimpleName(), message);
                    }
                }))
                .connectTimeout(30000L, TimeUnit.MILLISECONDS)
                .readTimeout(30000L, TimeUnit.MILLISECONDS)
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                .build();
        OkHttpUtils.initClient(okHttpClient);
    }
}