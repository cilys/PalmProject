package com.aopcloud.palmproject.ui.activity.mine;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aopcloud.base.annotation.Layout;
import com.aopcloud.base.base.BaseActivity;
import com.aopcloud.base.util.ToastUtil;
import com.aopcloud.palmproject.BuildConfig;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.bean.UserBean;
import com.aopcloud.palmproject.loader.AppImageLoader;
import com.aopcloud.palmproject.utils.LoginUserUtil;
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
 * @PackageName : com.aopcloud.basic.ui.activity.mine
 * @File : QrCodeActivity.java
 * @Date : 2020/4/10 2020/4/10
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
@Layout(R.layout.activity_mine_qr_code)
public class QrCodeActivity extends BaseActivity {
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
    @BindView(R.id.iv_sex)
    ImageView mIvSex;
    @BindView(R.id.tv_address)
    TextView mTvAddress;
    @BindView(R.id.iv_qr_code)
    ImageView mIvQrCode;
    @BindView(R.id.tv_tips)
    TextView mTvTips;
    @BindView(R.id.iv_img)
    CircleImageView mIvImg;

    @Override
    protected void initView() {
        mTvHeaderTitle.setText(R.string.mine_qr_code);
        mTvHeaderRight.setText(R.string.mine_qr_code_save);

        UserBean userBean = LoginUserUtil.getLoginUserBean(this);
        AppImageLoader.loadCircleImage(this, BuildConfig.BASE_URL + userBean.getAvatar(), mIvImg);
        mTvAddress.setText(""+userBean.getAddress());
        mTvName.setText(""+userBean.getNickname());
        mIvSex.setImageResource(userBean.getSex().equals("男")?R.mipmap.icon_sex_boy:R.mipmap.icon_sex_girl);
        Bitmap bitmap = CodeUtils.createImage(userBean.getTel()+"", 500, 500, null);
        AppImageLoader.load(this,bitmap,mIvQrCode,8);

    }


    @OnClick({R.id.ll_header_back, R.id.ll_header_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_header_back:finish();
                break;
            case R.id.ll_header_right:
                List<String> list = new ArrayList<>();
                list.add(Manifest.permission.READ_EXTERNAL_STORAGE);
                list.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);

                XXPermissions.with(this).permission(list).request(new OnPermission() {
                    @Override
                    public void hasPermission(List<String> granted, boolean isAll) {
                        if (isAll) {
                            saveImage(loadBitmapFromView(mIvQrCode));
                        } else {
                            ToastUtil.showToast("请先开启权限");
                        }
                    }

                    @Override
                    public void noPermission(List<String> denied, boolean quick) {
                        ToastUtil.showToast("请开启内存卡读写权限");
                    }
                });                break;
        }
    }


    private Bitmap loadBitmapFromView(View v) {
        int w = v.getWidth();
        int h = v.getHeight();
        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);
        c.drawColor(Color.WHITE);
        /** 如果不设置canvas画布为白色，则生成透明 */
//        v.layout(0, 0, w, h);
        v.draw(c);
        return bmp;
    }

    public File saveImage(Bitmap bmp) {
        File appDir = new File(Environment.getExternalStorageDirectory(), "PalmProject");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = "PalmProject_"+System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            ToastUtil.showToast("保存成功 \n路径：" + appDir.getAbsolutePath() + "/" + fileName);
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
}
