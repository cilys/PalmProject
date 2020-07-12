package com.aopcloud.palmproject.ui.adapter.file;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.aopcloud.palmproject.BuildConfig;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.loader.AppImageLoader;
import com.aopcloud.palmproject.player.PreviewPlayerController;

import org.yczbj.ycvideoplayerlib.constant.ConstantKeys;
import org.yczbj.ycvideoplayerlib.player.VideoPlayer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @PackageName : com.aopcloud.palmproject.ui.adapter.file
 * @File : PreviewAdapter.java
 * @Date : 2020/6/1 2020/6/1
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class PreviewAdapter extends PagerAdapter {

    private Activity mActivity;
    private List<PreviewBean> mPreviewBeans = new ArrayList<>();

    public PreviewAdapter(Activity activity, List<PreviewBean> images) {
        this.mActivity = activity;
        this.mPreviewBeans = images;

    }

    public void setPreviewBeans(List<PreviewBean> previewBeans) {
        this.mPreviewBeans = previewBeans;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        boolean v = !TextUtils.isEmpty(mPreviewBeans.get(position).getUrl()) && mPreviewBeans.get(position).getUrl().contains(".mp4");
        View view = View.inflate(mActivity, R.layout.item_preview_file, null);
        VideoPlayer mVideoPlayer = view.findViewById(R.id.video_player);
        ImageView imageView = view.findViewById(R.id.iv_img);
        if (!v) {
            imageView.setVisibility(View.VISIBLE);
            mVideoPlayer.setVisibility(View.GONE);
            Log.i(PreviewAdapter.class.getSimpleName(), "=======" + BuildConfig.BASE_URL + mPreviewBeans.get(position).getUrl());
            AppImageLoader.load(mActivity,  mPreviewBeans.get(position).getUrl(), imageView);
        } else {
            imageView.setVisibility(View.GONE);
            mVideoPlayer.setVisibility(View.VISIBLE);
            mVideoPlayer.setPlayerType(ConstantKeys.IjkPlayerType.TYPE_NATIVE);
            mVideoPlayer.setUp( mPreviewBeans.get(position).getUrl(), null);
            PreviewPlayerController playerController = new PreviewPlayerController(mActivity);
            playerController.setTitle("");
            playerController.setBackVisibility(View.GONE);
            playerController.setTopVisibility(false);
            playerController.setTvAndAudioVisibility(false, true);
            mVideoPlayer.setController(playerController);
            playerController.setBackVisibility(View.GONE);

            mPlayers.put(position,mVideoPlayer);
            AppImageLoader.load(mActivity, mPreviewBeans.get(position).getUrl(), playerController.imageView());
        }
        container.addView(view);
        return view;
    }

   public Map<Integer,VideoPlayer> mPlayers = new HashMap<>();

    public Map<Integer,VideoPlayer> getPlayers() {
        return mPlayers;
    }

    @Override
    public int getCount() {
        return mPreviewBeans.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }


    public static class PreviewBean  implements Serializable {
        private boolean video;
        private String url;

        public PreviewBean(String url) {
            this.url = url;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public boolean isVideo() {
            return video;
        }

        public void setVideo(boolean video) {
            this.video = video;
        }
    }
}
