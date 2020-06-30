package com.aopcloud.palmproject.common;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Handler;
import android.widget.ImageView;

import com.aopcloud.base.common.AppHelper;
import com.aopcloud.base.log.LogConfig;
import com.aopcloud.palmproject.loader.AppImageLoader;
import com.guoxiaoxing.phoenix.core.listener.ImageLoader;
import com.guoxiaoxing.phoenix.picker.Phoenix;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.cookie.CookieJarImpl;
import com.zhy.http.okhttp.cookie.store.PersistentCookieStore;
import com.zhy.http.okhttp.https.HttpsUtils;
import com.zhy.http.okhttp.log.LoggerInterceptor;

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
        Phoenix.config()
                .imageLoader(new ImageLoader() {
                    @Override
                    public void loadImage(Context mContext, ImageView imageView, String imagePath, int type) {
                        AppImageLoader.load(mContext, imagePath, imageView);
                    }
                });

    }

    public static void initOkHttp() {
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new LoggerInterceptor("dk_log", false))
                .connectTimeout(30000L, TimeUnit.MILLISECONDS)
                .readTimeout(30000L, TimeUnit.MILLISECONDS)
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                .build();
        OkHttpUtils.initClient(okHttpClient);
    }
}