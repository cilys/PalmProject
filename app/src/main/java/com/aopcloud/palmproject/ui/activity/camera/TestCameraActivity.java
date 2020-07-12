package com.aopcloud.palmproject.ui.activity.camera;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;

import com.aopcloud.base.annotation.Layout;
import com.aopcloud.base.base.BaseActivity;
import com.aopcloud.base.log.Logcat;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.api.ApiConstants;
import com.aopcloud.palmproject.ui.activity.project.ProjectScenesAddActivity;
import com.cjt2325.cameralibrary.JCameraView;
import com.cjt2325.cameralibrary.listener.JCameraListener;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @PackageName : com.aopcloud.palmproject.ui.activity.camera
 * @File : TestCameraActivity.java
 * @Date : 2020/4/24 10:19
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @VersionCode : V 1.0
 * @Describe ：
 */
@Layout(R.layout.a_test_camera)
public class TestCameraActivity extends BaseActivity implements JCameraListener{
    @BindView(R.id.camera_view)
    JCameraView mCameraView;

    private String task_id;
    @Override
    protected void initData() {
        super.initData();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            task_id = bundle.getString("task_id");
        }
    }

    @Override
    protected void initView() {
        mCameraView.setSaveVideoPath(Environment.getExternalStorageDirectory().getPath() + File.separator + "JCamera");
        mCameraView.setJCameraLisenter(this);
        mCameraView.setRight(0);
        mCameraView.setLeft(0);
    }
    @Override
    protected void onResume() {
        super.onResume();
        mCameraView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        mCameraView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void captureSuccess(Bitmap bitmap) {
        Bundle bundle = new Bundle();
        bundle.putString("task_id",""+task_id);
        gotoActivity(ProjectScenesAddActivity.class,bundle,0);
        finish();
    }

    @Override
    public void recordSuccess(String url, Bitmap firstFrame) {
        Bundle bundle = new Bundle();
        bundle.putString("task_id",""+task_id);
        gotoActivity(ProjectScenesAddActivity.class,bundle,0);
        finish();
    }

}
