package com.aopcloud.palmproject.loader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.common.GlideRoundTransform;
import com.aopcloud.palmproject.view.CircleImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory;
import com.bumptech.glide.request.transition.Transition;

/**
 * @PackageName : com.aopcloud.basic.loader
 * @File : AppImageLoader.java
 * @Date : 2020/4/11 2020/4/11
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class AppImageLoader {

    public static void load(Context context, Object path, ImageView imageView) {
        DrawableCrossFadeFactory drawableCrossFadeFactory =
                new DrawableCrossFadeFactory.Builder(300)
                        .setCrossFadeEnabled(true)
                        .build();
        RequestOptions options = new RequestOptions()
                .error(R.drawable.ic_default_image)
                .placeholder(R.drawable.ic_default_image);
        Glide.with(context)
                .load(path)
                .apply(options)
                .transition(DrawableTransitionOptions.with(drawableCrossFadeFactory))
                .into(imageView);
    }

    public static void load(Context context, Object path, ImageView imageView,int radius) {
        DrawableCrossFadeFactory drawableCrossFadeFactory =
                new DrawableCrossFadeFactory.Builder(300)
                        .setCrossFadeEnabled(true)
                        .build();
        RequestOptions options = new RequestOptions()
                .error(R.drawable.ic_default_image)
                .placeholder(R.drawable.ic_default_image)
                .transforms(new CenterCrop(), new GlideRoundTransform(radius));
        Glide.with(context)
                .load(path)
                .apply(options)
                .transition(DrawableTransitionOptions.with(drawableCrossFadeFactory))
                .into(imageView);
    }

    public static void loadCircleImage(Context context,  Object path, final CircleImageView imageView) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.ic_default_image)
                .priority(Priority.HIGH);
        RequestBuilder<Drawable> requestBuilder = Glide.with(context)
                .load(path);
        requestBuilder.apply(options)
                .thumbnail(Glide.with(context).load(path))
//                .transition(DrawableTransitionOptions.withCrossFade())
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        imageView.setImageDrawable(resource);
                    }
                });
    }

    public static void s(ImageView imageView, Object path){
        Glide.with(imageView.getContext())
                .asBitmap() // 不显示gif图
                .load(path)
//                .transition(DrawableTransitionOptions.withCrossFade(500)) // 渐变
//                .transform(new CircleCrop()) // 圆形图片
//                .transform(new RoundedCorners(20)) // 圆角图片
//                .transform(new BlurTransformation(50, 8)) // 高斯模糊，参数1：模糊度；参数2：图片缩放x倍后再进行模糊
                .listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    } // 加载监听
                }).into(imageView);
    }
}
