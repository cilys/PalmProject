package com.aopcloud.palmproject.ui.activity.staff;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.aopcloud.base.annotation.Layout;
import com.aopcloud.base.util.ToastUtil;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.api.ApiConstants;
import com.aopcloud.palmproject.common.ResultBean;
import com.aopcloud.palmproject.loader.AppImageLoader;
import com.aopcloud.palmproject.ui.activity.BaseAc;
import com.aopcloud.palmproject.ui.activity.QrCodeScanActivity;
import com.aopcloud.palmproject.utils.LoginUserUtil;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @PackageName : com.aopcloud.palmproject.ui.activity.staff
 * @File : StaffApprovalActivity.java
 * @Date : 2020/4/23 9:18
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @VersionCode : V 1.0
 * @Describe ：
 */
@Layout(R.layout.activity_staff_invite)
public class StaffInviteActivity extends BaseAc {

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
    @BindView(R.id.et_search)
    EditText mEtSearch;
    @BindView(R.id.iv_qr_code)
    ImageView mIvQrCode;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_no)
    TextView mTvNo;
    @BindView(R.id.ll_add_input)
    LinearLayout mLlAddInput;
    @BindView(R.id.ll_add_contacts)
    LinearLayout mLlAddContacts;
    @BindView(R.id.ll_add_qr)
    LinearLayout mLlAddQr;
    @BindView(R.id.ll_share)
    LinearLayout mLlShare;

    @Override
    protected void initView() {
        mTvHeaderTitle.setText("邀请新成员");
        Bitmap bitmap = CodeUtils.createImage(LoginUserUtil.getCurrentEnterpriseNo(this), 500, 500, null);
        AppImageLoader.load(this, bitmap, mIvQrCode);
        mTvNo.setText("团队编码：" + LoginUserUtil.getCurrentEnterpriseNo(this));
        mTvTitle.setText("" + LoginUserUtil.getCurrentEnterpriseBean(this).getName());
    }


    @OnClick({R.id.ll_header_back, R.id.ll_header_right, R.id.ll_add_input, R.id.ll_add_contacts, R.id.ll_add_qr, R.id.ll_share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_header_back:
                setResult(0);
                finish();
                break;
            case R.id.ll_header_right:
                break;
            case R.id.ll_add_input:
            case R.id.ll_add_contacts:
                gotoActivity(StaffInviteByMobileActivity.class);
                break;
            case R.id.ll_add_qr:
                gotoActivity(QrCodeScanActivity.class,0);
                break;
            case R.id.ll_share:
                String str = "你好，我正在使用“掌项”APP想邀请你一起加入我的团队，团队编码："+LoginUserUtil.getCurrentEnterpriseNo(this);
                ClipboardManager clipboardManager = (ClipboardManager) this.getSystemService(Context.CLIPBOARD_SERVICE);
                clipboardManager.setPrimaryClip(ClipData.newPlainText(null, "" +str));
                if (clipboardManager.hasPrimaryClip()) {
                    clipboardManager.getPrimaryClip().getItemAt(0).getText();
                }
                ToastUtil.showToast("已经复制团队编码至剪切板，请执行通过聊天软件发送给您的朋友！");
                break;
        }
    }
    private String mobile;
    private String user_id;

    @Override
    public void toRequest(int eventTag) {
        super.toRequest(eventTag);
        Map map = new HashMap();
        map.put("token", "" + LoginUserUtil.getToken(this));
        map.put("code", "" + LoginUserUtil.getCurrentEnterpriseNo(this));
        if (eventTag == ApiConstants.EventTags.company_invite) {
//            map.put("tel", "" + mobile);
            map.put("user_id", ""+user_id);
            iCommonRequestPresenter.requestPost(eventTag, this, ApiConstants.company_invite, map);
        }
    }

    @Override
    public void getRequestData(int eventTag, String result) {
        super.getRequestData(eventTag, result);
        dismissPopupLoading();
        ResultBean bean = JSON.parseObject(result, ResultBean.class);
        if (bean != null && bean.getCode() == 0) {
            if (eventTag == ApiConstants.EventTags.company_invite) {
                ToastUtil.showToast(bean != null ? bean.getMsg() : "提交成功");
            }
        } else {
            ToastUtil.showToast(bean != null ? bean.getMsg() : "加载错误，请重试");
        }

    }

    @Override
    public void onRequestFailureException(int eventTag, String msg) {
        super.onRequestFailureException(eventTag, msg);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data==null){
            return;
        }
        if (requestCode==0){
            Bundle bundle =data.getExtras();
            user_id = bundle.getString("code");
            toRequest(ApiConstants.EventTags.company_invite);
        }
    }
}
