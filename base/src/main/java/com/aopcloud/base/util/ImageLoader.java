package com.aopcloud.base.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @PackageName : com.aopcloud.base.util
 * @File : ImageLoader.java
 * @Date : 2019/12/26 11:21
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @VersionCode : V 1.0
 * @Describe ： Image loader
 */
public class ImageLoader {
    private static ImageLoader imageLoader = null;

    public static ImageLoader getInstance() {
        if (imageLoader == null) {
            imageLoader = new ImageLoader();
        }
        return imageLoader;
    }

    public ImageLoader() {

    }


    /**
     * @param path      show resource  @Drawable @file @Url
     * @param imageView
     */
    public void showImage(Object path, ImageView imageView) {


    }

    /**
     * @param path      show  resource @Drawable @file @Url
     * @param imageView
     * @param radius
     */
    public void showImage(Object path, ImageView imageView, int radius) {


    }

    /**
     * @param path      show  resource @Drawable @file @Url
     * @param imageView show view
     * @param defaultId errorResId and placeholderResId
     * @param radius
     */
    public void showImage(Object path, ImageView imageView, @DrawableRes int defaultId, int radius) {


    }

    /**
     * @param path          show resource @Drawable @file @Url
     * @param imageView     show view
     * @param errorId       errorResId
     * @param placeholderId placeholderResId
     * @param radius
     */
    public void showImage(Object path, ImageView imageView, @DrawableRes int errorId, @DrawableRes int placeholderId, int radius) {


    }


    /**
     * @param path
     * @param imageView
     */
    public void showBigImages(Object path, ImageView imageView) {


    }


    private static final String SD_PATH = "/sdcard/com.app.jibuqi/pic/";
    private static final String IN_PATH = "/com.app.jibuqi/pic/";

    /**
     * @param context
     * @param fileName
     * @param mBitmap
     * @return
     */
    public static String saveBitmap(Context context, String fileName, Bitmap mBitmap) {

        String savePath;
        File filePic;
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            savePath = SD_PATH;
        } else {
            savePath = context.getApplicationContext().getFilesDir()
                    .getAbsolutePath()
                    + IN_PATH;
        }
        try {
            filePic = new File(savePath + fileName);
            if (!filePic.exists()) {
                filePic.getParentFile().mkdirs();
                filePic.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(filePic);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return savePath;
    }

}
