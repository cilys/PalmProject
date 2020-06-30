package com.aopcloud.palmproject.loader;

import android.content.Context;
import android.widget.ImageView;

import vip.devkit.view.common.banner.loader.BannerImageLoader;

/**
 * @PackageName : com.aopcloud.basic.loader
 * @File : BannerGlideImageLoader.java
 * @Date : 2020/4/11 2020/4/11
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class BannerGlideImageLoader extends BannerImageLoader {
    int radius = 6;

    public BannerGlideImageLoader(int radius) {
        this.radius = radius;
    }

    public BannerGlideImageLoader() {
    }

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        AppImageLoader.load(context, path, imageView);
    }
}
