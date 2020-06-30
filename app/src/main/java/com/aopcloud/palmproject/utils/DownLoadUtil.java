package com.aopcloud.palmproject.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.aopcloud.base.log.Logcat;
import com.aopcloud.base.util.ToastUtil;
import com.aopcloud.palmproject.common.AppContext;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

import static com.autonavi.amap.navicore.eyrie.AMapNaviCoreEyrieView.TAG;

/**
 * @PackageName : com.aopcloud.palmproject.utils
 * @File : DownLoadUtil.java
 * @Date : 2020/6/1 2020/6/1
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class DownLoadUtil {


    public static void download(String url) {
        File appDir = new File(Environment.getExternalStorageDirectory(), "PalmProject");
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new FileCallBack(Environment.getExternalStorageDirectory().getAbsolutePath(), getPictureSuffix(url)) {
                    @Override
                    public File saveFile(Response response, int id) throws IOException {
                        return super.saveFile(response, id);
                    }

                    @Override
                    public File parseNetworkResponse(Response response, int id) throws Exception {
                        return super.parseNetworkResponse(response, id);
                    }

                    @Override
                    public void inProgress(float progress, long total, int id) {
                        super.inProgress(progress, total, id);

                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.showToast("保存出错,请重试");
                        Logcat.e("getPictureSuffix:" + getPictureSuffix(url)
                                + "\turl:" + url
                                + "\tgetPath:" + appDir.getPath()
                                + "\tgetAbsolutePath:" + appDir.getAbsolutePath());
                    }

                    @Override
                    public void onResponse(File response, int id) {
                        Logcat.i("getAbsolutePath:" + response.getAbsolutePath()
                                + "\tgetPath:" + response.getPath()
                                + "\tgetName:" + response.getName()
                                +"\tgetPictureSuffix:" + getPictureSuffix(url));

                        ToastUtil.showToast("保存成功 \n路径：" + appDir.getAbsolutePath() + "/" + response);
                        AppContext.getInstance().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file:" + response.getPath())));
                    }

                });

    }

    private static String getPictureSuffix(String path) {
        if (path.contains(".")) {
            return "PalmProject_" + System.currentTimeMillis() + path.substring(path.lastIndexOf("."), path.length());
        }
        return "PalmProject_" + System.currentTimeMillis() + ".png";
    }

}
