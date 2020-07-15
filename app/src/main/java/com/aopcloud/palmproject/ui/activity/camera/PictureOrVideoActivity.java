package com.aopcloud.palmproject.ui.activity.camera;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresPermission;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.aopcloud.base.annotation.Layout;
import com.aopcloud.base.base.BaseActivity;
import com.aopcloud.base.util.ToastUtil;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.ui.activity.BaseAc;
import com.aopcloud.palmproject.ui.activity.project.ProjectScenesAddActivity;
import com.aopcloud.palmproject.utils.BitmapUtil;
import com.aopcloud.palmproject.utils.LoginUserUtil;
import com.aopcloud.palmproject.utils.WatermarkUtil;
import com.hjq.permissions.OnPermission;
import com.hjq.permissions.XXPermissions;
import com.otaliastudios.cameraview.CameraException;
import com.otaliastudios.cameraview.CameraListener;
import com.otaliastudios.cameraview.CameraOptions;
import com.otaliastudios.cameraview.CameraUtils;
import com.otaliastudios.cameraview.CameraView;
import com.otaliastudios.cameraview.Facing;
import com.otaliastudios.cameraview.Gesture;
import com.otaliastudios.cameraview.GestureAction;
import com.otaliastudios.cameraview.SessionType;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @PackageName : com.aopcloud.palmproject.ui.activity.camera
 * @File : PictureOrVideoActivity.java
 * @Date : 2020/6/5 2020/6/5
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
@SuppressLint("MissingPermission")
@Layout(R.layout.activity_picture_or_video)
public class PictureOrVideoActivity extends BaseAc {
    @BindView(R.id.frame_layout)
    FrameLayout mFrameLayout;
    @BindView(R.id.iv_close)
    ImageView mIvClose;
    @BindView(R.id.iv_refresh)
    ImageView mIvRefresh;
    @BindView(R.id.rl_head_layout)
    RelativeLayout mRlHeadLayout;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.ll_time_layout)
    LinearLayout mLlTimeLayout;
    @BindView(R.id.iv_picture)
    ImageView mIvPicture;
    @BindView(R.id.iv_video)
    ImageView mIvVideo;
    @BindView(R.id.tv_complete)
    TextView mTvComplete;
    @BindView(R.id.rl_bottom_layout)
    RelativeLayout mRlBottomLayout;
    @BindView(R.id.switch_view)
    Switch mSwitch;
    @BindView(R.id.camera_view)
    CameraView mCameraView;
    @BindView(R.id.tv_take_picture)
    TextView mTvTakePicture;
    @BindView(R.id.tv_take_video)
    TextView mTvTakeVideo;
    @BindView(R.id.tv_count)
    TextView mTvCount;


    int picCount = 0;
    int videoCount = 0;

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.reset().titleBar(mRlHeadLayout).init();
    }

    private String project_id;
    private String task_id;

    @Override
    protected void initData() {
        super.initData();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            task_id = bundle.getString("task_id");
            project_id = bundle.getString("project_id");
        }

    }

    @RequiresPermission(Manifest.permission.CAMERA)
    @Override
    protected void initView() {

        mCameraView.start();
        mIvPicture.setVisibility(View.VISIBLE);
        mIvVideo.setVisibility(View.GONE);
        mLlTimeLayout.setVisibility(View.GONE);
        mCameraView.mapGesture(Gesture.PINCH, GestureAction.ZOOM); // 双指缩放!
        mCameraView.mapGesture(Gesture.TAP, GestureAction.FOCUS_WITH_MARKER); // 点击对焦!


        mCameraView.addCameraListener(new CameraListener() {
            @Override
            public void onPictureTaken(byte[] jpeg) {
                super.onPictureTaken(jpeg);
                Log.d(TAG, "-------onPictureTaken----");
                Bitmap bitmap = BitmapUtil.Bytes2Bimap(jpeg);
                Bitmap watermark = WatermarkUtil.createWatermark(PictureOrVideoActivity.this,
                        "测试项目"
                        , ""
                        , ""
                        , LoginUserUtil.getLoginUserBean(PictureOrVideoActivity.this).getNickname()
                        , "天气：晴 温度 19度 温度49% 风向 西北风力小于3级"
                        , "梅州市梅江区江南低坝路怡乐新村16号");
                Bitmap bitmap1 = WatermarkUtil.createWaterMaskLeftBottom(PictureOrVideoActivity.this
                        , bitmap
                        , watermark
                        , 0
                        , 0);
                List<String> list = new ArrayList<>();
                list.add(Manifest.permission.READ_EXTERNAL_STORAGE);
                list.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                XXPermissions.with(PictureOrVideoActivity.this).permission(list).request(new OnPermission() {
                    @Override
                    public void hasPermission(List<String> granted, boolean isAll) {
                        if (isAll) {
                            saveImage(bitmap1);
                            picCount++;
                            if (picCount > 0) {
                                mTvCount.setText("" + picCount);
                                mIvPicture.setImageResource(0);
                            }
                        } else {
                            ToastUtil.showToast("请先开启权限");
                        }
                    }

                    @Override
                    public void noPermission(List<String> denied, boolean quick) {

                    }
                });


            }

            @Override
            public void onVideoTaken(File video) {
                super.onVideoTaken(video);
                videoCount++;

                if (videoCount > 0) {
                    mTvCount.setText("" + videoCount);
                } else {
                    mTvCount.setText("");
                }
//                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + video.getPath())));
                Log.i(TAG, "-------onVideoTaken----" + video.getPath());
            }

            @Override
            public void onCameraOpened(CameraOptions options) {
                super.onCameraOpened(options);
            }

            @Override
            public void onCameraError(@NonNull CameraException exception) {
                super.onCameraError(exception);
            }

            @Override
            public void onCameraClosed() {
                super.onCameraClosed();
            }
        });
    }


    public File saveImage(Bitmap bmp) {
        File appDir = new File(Environment.getExternalStorageDirectory(), "PalmProject");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.getPath())));
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    protected void setListener() {
        super.setListener();
        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    if (picCount > 0) {
                        mTvCount.setText("" + picCount);
                        mIvPicture.setImageResource(0);
                    } else {
                        mTvCount.setText("");
                        mIvPicture.setImageResource(R.mipmap.paizhao);
                    }

                    mIvPicture.setVisibility(View.VISIBLE);
                    mIvVideo.setVisibility(View.GONE);
                    mLlTimeLayout.setVisibility(View.GONE);
                } else {
                    if (videoCount > 0) {
                        mTvCount.setText("" + videoCount);
                    } else {
                        mTvCount.setText("");
                    }
                    mIvPicture.setVisibility(View.GONE);
                    mIvVideo.setVisibility(View.VISIBLE);
                    mLlTimeLayout.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    @OnClick({R.id.iv_close, R.id.iv_refresh, R.id.iv_picture, R.id.iv_video, R.id.tv_complete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
                finish();
                break;
            case R.id.iv_refresh:
                if (mCameraView.getFacing() == Facing.BACK) {
                    mCameraView.setFacing(Facing.FRONT);
                } else {
                    mCameraView.setFacing(Facing.BACK);
                }
                break;
            case R.id.iv_picture:

                mCameraView.setSessionType(SessionType.PICTURE);
                mCameraView.capturePicture();

                break;
            case R.id.iv_video:
                List<String> list = new ArrayList<>();
                list.add(Manifest.permission.READ_EXTERNAL_STORAGE);
                list.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                XXPermissions.with(this).permission(list).request(new OnPermission() {
                    @Override
                    public void hasPermission(List<String> granted, boolean isAll) {
                        if (isAll) {

                            if (!mCameraView.isCapturingVideo()) {
                                mCameraView.setSessionType(SessionType.VIDEO);
                                File appDir = new File(Environment.getExternalStorageDirectory(), "PalmProject");
                                if (!appDir.exists()) {
                                    appDir.mkdir();
                                }
                                String fileName = System.currentTimeMillis() + ".mp4";
                                File file = new File(appDir, fileName);
                                Log.i(TAG, "------isCapturingVideo------------------" +file.exists());
                                if (!file.exists()) {
                                    try {
                                        file.createNewFile();
                                        Log.i(TAG, "------isCapturingVideo------------------" +file.exists());
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                                mCameraView.startCapturingVideo(file);
                                mHandler.postDelayed(timeRunnable, 0);
                            } else {
                                currentSecond = 0;
                                mCameraView.stopCapturingVideo();
                                mHandler.removeCallbacks(timeRunnable);
                                mTvTime.setText("00:00");
                            }


                        } else {
                            ToastUtil.showToast("请先开启权限");
                        }
                    }

                    @Override
                    public void noPermission(List<String> denied, boolean quick) {

                    }
                });

                break;
            case R.id.tv_complete:
                Bundle bundle = new Bundle();
                bundle.putString("task_id", "" + task_id);
                bundle.putString("project_id", "" + project_id);
                gotoActivity(ProjectScenesAddActivity.class, bundle, 0);
                finish();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCameraView.start();
    }


    @Override
    public void onPause() {
        super.onPause();
        mCameraView.stop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCameraView.destroy();
        mHandler.removeCallbacks(timeRunnable);
    }

    private long currentSecond = 0;
    private Handler mHandler = new Handler();

    private Runnable timeRunnable = new Runnable() {
        @Override
        public void run() {
            currentSecond = currentSecond + 1;
            if (currentSecond > 0) {
                secondToTime(currentSecond);
                mHandler.postDelayed(this, 1000);
            }
        }
    };

    public void secondToTime(long second) {
//        long days = second / 86400;            //转换天数
//        second = second % 86400;            //剩余秒数
        long hours = second / 3600;            //转换小时
        second = second % 3600;                //剩余秒数
        long minutes = second / 60;            //转换分钟
        second = second % 60;                //剩余秒数


        String h = (hours < 10) ? "0" + hours : "" + hours;
        String m = (minutes < 10) ? "0" + minutes : "" + minutes;
        String s = (second < 10) ? "0" + second : "" + second;
        if (mTvTime != null) {
            mTvTime.setText(h + ":" + m + ":" + s);
        }
    }

}
