package com.aopcloud.palmproject.ui.activity.camera;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.aopcloud.base.annotation.Layout;
import com.aopcloud.base.base.BaseActivity;
import com.aopcloud.base.log.Logcat;
import com.aopcloud.base.util.ToastUtil;
import com.aopcloud.base.util.ViewUtil;
import com.aopcloud.palmproject.BuildConfig;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.common.GlideRoundTransform;
import com.aopcloud.palmproject.ui.adapter.file.PreviewAdapter;
import com.aopcloud.palmproject.utils.DownLoadUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.SimpleResource;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.guoxiaoxing.phoenix.core.PhoenixOption;
import com.guoxiaoxing.phoenix.core.model.MediaEntity;
import com.zhy.http.okhttp.OkHttpUtils;

import org.yczbj.ycvideoplayerlib.manager.VideoPlayerManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @PackageName : com.aopcloud.palmproject.ui.activity.camera
 * @File : PreviewActivity.java
 * @Date : 2020/6/1 2020/6/1
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
@Layout(R.layout.activity_perview_file)
public class PreviewActivity extends BaseActivity {
    @BindView(R.id.ll_header_back)
    LinearLayout mLlHeaderBack;
    @BindView(R.id.tv_header_title)
    TextView mTvHeaderTitle;
    @BindView(R.id.iv_header_right)
    ImageView mIvHeaderRight;
    @BindView(R.id.ll_header_right)
    LinearLayout mLlHeaderRight;
    @BindView(R.id.ll_common_layout)
    LinearLayout mLlCommonLayout;
    @BindView(R.id.page_view)
    ViewPager mPageView;
    private PreviewAdapter mPreviewAdapter;

    private List<PreviewAdapter.PreviewBean> mPreviewBeans = new ArrayList<>();

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.reset().titleBar(mLlCommonLayout).statusBarDarkFont(false).init();
    }

    @Override
    protected void initData() {
        super.initData();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            List<PreviewAdapter.PreviewBean> val = (List<PreviewAdapter.PreviewBean>) bundle.getSerializable("PreviewBean");
            Logcat.i("======="+ JSON.toJSONString(val));
            mPreviewBeans.clear();
            mPreviewBeans.addAll(val);
        }

    }


    @Override
    protected void initView() {
        mTvHeaderTitle.setText(+1+"/"+mPreviewBeans.size());

        Logcat.i("======="+ JSON.toJSONString(mPreviewBeans));
        mPreviewAdapter = new PreviewAdapter(this, mPreviewBeans);
        mPageView.setAdapter(mPreviewAdapter);
        mPageView.setOffscreenPageLimit(1);
    }

    @Override
    protected void setListener() {
        super.setListener();
        mPageView.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                mTvHeaderTitle.setText((i+1)+"/"+mPreviewBeans.size());

                for(int position:mPreviewAdapter.getPlayers().keySet()){
                    Logcat.d(""+position+"/"+i);
                    if (position!=i){
                        mPreviewAdapter.getPlayers().get(position).pause();
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @OnClick({R.id.ll_header_back, R.id.ll_header_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_header_back:
                finish();
                break;
            case R.id.ll_header_right:
                int position = mPageView.getCurrentItem();
                DownLoadUtil.download(mPreviewBeans.get(position).getUrl());
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        VideoPlayerManager.instance().releaseVideoPlayer();
    }

    @Override
    public void onBackPressed() {
        if (VideoPlayerManager.instance().onBackPressed()) return;
        super.onBackPressed();
    }

}
