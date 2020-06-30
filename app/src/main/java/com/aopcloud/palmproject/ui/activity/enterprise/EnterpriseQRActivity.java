package com.aopcloud.palmproject.ui.activity.enterprise;

import android.Manifest;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.aopcloud.base.annotation.Layout;
import com.aopcloud.base.base.BaseActivity;
import com.aopcloud.base.util.ToastUtil;
import com.aopcloud.palmproject.BuildConfig;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.common.Constants;
import com.aopcloud.palmproject.loader.AppImageLoader;
import com.aopcloud.palmproject.ui.activity.enterprise.bean.EnterpriseInfoBean;
import com.aopcloud.palmproject.view.CircleImageView;
import com.hjq.permissions.OnPermission;
import com.hjq.permissions.XXPermissions;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @PackageName : com.aopcloud.palmproject.ui.activity.enterprise
 * @File : EnterpriseQRActivity.java
 * @Date : 2020/4/20 2020/4/20
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
@Layout(R.layout.activity_enterprise_qr_code)
public class EnterpriseQRActivity extends BaseActivity {
    @BindView(R.id.ll_header_back)
    LinearLayout mLlHeaderBack;
    @BindView(R.id.tv_header_title)
    TextView mTvHeaderTitle;
    @BindView(R.id.tv_header_right)
    TextView mTvHeaderRight;
    @BindView(R.id.ll_header_right)
    LinearLayout mLlHeaderRight;
    @BindView(R.id.ll_common_layout)
    LinearLayout mLlCommonLayout;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.iv_qr_code)
    ImageView mIvQrCode;
    @BindView(R.id.tv_tips)
    TextView mTvTips;
    @BindView(R.id.iv_logo)
    CircleImageView mIvLogo;
    private EnterpriseInfoBean mEnterpriseInfoBean;
    private Bitmap mLogo;

    @Override
    protected void initData() {
        super.initData();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mEnterpriseInfoBean = JSON.parseObject(bundle.getString("enterpriseInfoBean"), EnterpriseInfoBean.class);
        }
    }

    @Override
    protected void initView() {
        mTvHeaderTitle.setText("企业二维码");
        mTvHeaderRight.setText("更多");
        mLogo = CodeUtils.createImage(mEnterpriseInfoBean.getCode(), 500, 500, null);
        AppImageLoader.load(this, mLogo, mIvQrCode);
        AppImageLoader.loadCircleImage(this, BuildConfig.BASE_URL + mEnterpriseInfoBean.getLogo(), mIvLogo);

        mTvName.setText(mEnterpriseInfoBean.getName());
        mTvTips.setText("团队编码：" + mEnterpriseInfoBean.getCode() + " 企业负责人:" + mEnterpriseInfoBean.getLeader_name());


    }


    @OnClick({R.id.ll_header_back, R.id.ll_header_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_header_back:
                finish();
                break;
            case R.id.ll_header_right:
                showShare();
                break;
        }
    }


    Dialog dialog;

    private void showShare() {
        dialog = new Dialog(this, R.style.quick_option_dialog);
        dialog.setContentView(R.layout.dialog_share);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().setGravity(Gravity.BOTTOM | Gravity.CENTER);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setWindowAnimations((R.style.popWindow_anim_style));
        dialog.show();
        LinearLayout mLlWx = (LinearLayout) dialog.findViewById(R.id.iv_wx);
        LinearLayout mLlWxTimeLine = (LinearLayout) dialog.findViewById(R.id.iv_wx2);
        LinearLayout mLlQQ = (LinearLayout) dialog.findViewById(R.id.ll_qq);
        LinearLayout mLlCopy = (LinearLayout) dialog.findViewById(R.id.ll_save);
        TextView tv_cancel_share = (TextView) dialog.findViewById(R.id.tv_cancel_share);
        mLlQQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        mLlWx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        mLlWxTimeLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });
        mLlCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveImage();

                dialog.dismiss();
            }
        });
        tv_cancel_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    public void saveImage() {
        List<String> list = new ArrayList<>();
        list.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        list.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        XXPermissions.with(this).permission(list).request(new OnPermission() {
            @Override
            public void hasPermission(List<String> granted, boolean isAll) {
                if (isAll) {
                    saveImage(mLogo);
                } else {
                    ToastUtil.showToast("请先开启权限");
                }
            }

            @Override
            public void noPermission(List<String> denied, boolean quick) {

            }
        });
    }

    public File saveImage(Bitmap bmp) {
        File appDir = new File(Constants.STORE_FOLDER);
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName =  "PalmProject_"+System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            ToastUtil.showToast("保存成功 \n路径：" + appDir.getAbsolutePath() + "/" + fileName);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
