package com.aopcloud.palmproject.ui.activity.camera;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aopcloud.base.annotation.Layout;
import com.aopcloud.base.base.BaseActivity;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.ui.adapter.file.PreviewAdapter;
import com.aopcloud.palmproject.utils.DownLoadUtil;

import org.yczbj.ycvideoplayerlib.manager.VideoPlayerManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
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
            mPreviewBeans.clear();
            mPreviewBeans.addAll(val);
        }

    }


    @Override
    protected void initView() {
        mTvHeaderTitle.setText(+1+"/"+mPreviewBeans.size());

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
                    Log.d(TAG, ""+position+"/"+i);
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
