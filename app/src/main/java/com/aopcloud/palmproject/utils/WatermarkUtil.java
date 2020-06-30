package com.aopcloud.palmproject.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.aopcloud.base.util.ViewUtil;
import com.aopcloud.palmproject.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @PackageName : com.aopcloud.palmproject.utils
 * @File : WatermarkUtil.java
 * @Date : 2020/6/7 2020/6/7
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class WatermarkUtil {


    public static Bitmap createWatermark(Context context, String project_name, String type, String tag, String user_name, String weather, String address) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH：mm");
        int width = ViewUtil.getScreenWidth(context);
        View view = View.inflate(context, R.layout.layout_watermark, null);
        view.requestLayout();
        view.postInvalidate();
        ((TextView) view.findViewById(R.id.tv_project_name)).setText("" + project_name);
        ((TextView) view.findViewById(R.id.tv_type)).setText("" + type);
        ((TextView) view.findViewById(R.id.tv_tag)).setText("" + tag);
        ((TextView) view.findViewById(R.id.tv_user_time)).setText("" + user_name + " " + dateFormat.format(new Date()));
        ((TextView) view.findViewById(R.id.tv_weather)).setText("" + weather);
        ((TextView) view.findViewById(R.id.tv_address)).setText("" + address);
        layoutView(view, width, ViewUtil.getViewHeight(view));
        return loadBitmapFromView(view);
    }

    /**
     * 然后View和其内部的子View都具有了实际大小，也就是完成了布局，相当与添加到了界面上。接着就可以创建位图并在上面绘制了：
     *
     * @param v
     * @param width
     * @param height
     */
    private static void layoutView(View v, int width, int height) {
        // 指定整个View的大小 参数是左上角 和右下角的坐标
        v.layout(0, 0, width, height);
        int measuredWidth = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
        int measuredHeight = View.MeasureSpec.makeMeasureSpec(15000, View.MeasureSpec.AT_MOST);
        /** 当然，measure完后，并不会实际改变View的尺寸，需要调用View.layout方法去进行布局。
         * 按示例调用layout函数后，View的大小将会变成你想要设置成的大小。
         */
        v.measure(measuredWidth, measuredHeight);
        v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
    }

    private static Bitmap loadBitmapFromView(View v) {
        int w = v.getWidth();
        int h = v.getHeight();
        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);
//        c.drawColor(Color.WHITE);
        /** 如果不设置canvas画布为白色，则生成透明 */
        v.layout(0, 0, w, h);
        v.draw(c);
        return bmp;
    }


    /**
     * 设置水印图片到左下角
     *
     * @param context
     * @param src
     * @param watermark
     * @param paddingLeft
     * @param paddingBottom
     * @return
     */
    public static Bitmap createWaterMaskLeftBottom(Context context, Bitmap src, Bitmap watermark, int paddingLeft, int paddingBottom) {
        return createWaterMaskBitmap(src, watermark, ViewUtil.dp2px(context, paddingLeft),
                src.getHeight() - watermark.getHeight() - ViewUtil.dp2px(context, paddingBottom));
    }

    private static Bitmap createWaterMaskBitmap(Bitmap src, Bitmap watermark, int paddingLeft, int paddingTop) {
        if (src == null) {
            return null;
        }
        int width = src.getWidth();
        int height = src.getHeight();
        //创建一个bitmap
        Bitmap newb = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);// 创建一个新的和SRC长度宽度一样的位图
        //将该图片作为画布
        Canvas canvas = new Canvas(newb);
        //在画布 0，0坐标上开始绘制原始图片
        canvas.drawBitmap(src, 0, 0, null);
        //在画布上绘制水印图片
        canvas.drawBitmap(watermark, paddingLeft, paddingTop, null);
        // 保存
        canvas.save();
        // 存储
        canvas.restore();
        return newb;
    }
}
