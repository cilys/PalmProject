package com.aopcloud.palmproject.ui.activity.staff;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.aopcloud.base.annotation.Layout;
import com.aopcloud.base.base.BaseActivity;
import com.aopcloud.base.util.ToastUtil;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.api.ApiConstants;
import com.aopcloud.palmproject.common.ResultBean;
import com.aopcloud.palmproject.ui.activity.staff.bean.StaffSDetailBean;
import com.aopcloud.palmproject.utils.LoginUserUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @PackageName : com.aopcloud.palmproject.ui.activity.staff
 * @File : StaffInfoEditActivity.java
 * @Date : 2020/4/22 2020/4/22
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
@Layout(R.layout.activity_staff_info_edit)
public class StaffInfoEditActivity extends BaseActivity {
    @BindView(R.id.ll_header_back)
    LinearLayout mLlHeaderBack;
    @BindView(R.id.tv_header_title)
    TextView mTvHeaderTitle;
    @BindView(R.id.iv_header_more)
    ImageView mIvHeaderMore;
    @BindView(R.id.ll_header_right)
    LinearLayout mLlHeaderRight;
    @BindView(R.id.ll_common_layout)
    LinearLayout mLlCommonLayout;
    @BindView(R.id.et_name)
    EditText mEtName;
    @BindView(R.id.et_mobile)
    EditText mEtMobile;
    @BindView(R.id.et_worker_id)
    EditText mEtWorkerId;
    @BindView(R.id.et_worker_start_time)
    EditText mEtWorkerStartTime;


    private StaffSDetailBean mDetailBean;

    private String user_code;

    @Override
    protected void initData() {
        super.initData();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String json = bundle.getString("StaffSDetailBean");
            mDetailBean = JSON.parseObject(json, StaffSDetailBean.class);
        }
    }

    @Override
    protected void initView() {
        mTvHeaderTitle.setText("编辑员工");
        mIvHeaderMore.setImageResource(R.mipmap.icon_header_sure);

        mEtMobile.setText("" + mDetailBean.getTel());
        mEtName.setText("" + mDetailBean.getName());

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        date.setTime((mDetailBean.getMake_time()*1000));
        mEtWorkerStartTime.setText("" + dateFormat.format(date));
        mEtMobile.setClickable(false);
        mEtMobile.setFocusable(false);
        mEtMobile.setFocusableInTouchMode(false);
        mEtName.setClickable(false);
        mEtName.setFocusable(false);
        mEtName.setFocusableInTouchMode(false);
        mEtWorkerStartTime.setClickable(false);
        mEtWorkerStartTime.setFocusable(false);
        mEtWorkerStartTime.setFocusableInTouchMode(false);

        mEtWorkerId.setText("" + mDetailBean.getCompany().getUser_code());


    }


    @OnClick({R.id.ll_header_back, R.id.ll_header_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_header_back:
                finish();
                break;
            case R.id.ll_header_right:
                user_code = mEtWorkerId.getText().toString();
                if (TextUtils.isEmpty(user_code)) {
                    ToastUtil.showToast("请输入员工编号");
                    return;
                }
                toRequest(ApiConstants.EventTags.company_changecode);
                break;
        }
    }

    @Override
    public void toRequest(int eventTag) {
        super.toRequest(eventTag);
        Map map = new HashMap();
        map.put("token", "" + LoginUserUtil.getToken(this));
        map.put("code", "" + LoginUserUtil.getCurrentEnterpriseNo(this));
        if (eventTag == ApiConstants.EventTags.company_changecode) {
            map.put("user_code",""+user_code);
            map.put("user_id",""+mDetailBean.getId());
            iCommonRequestPresenter.requestPost(eventTag, this, ApiConstants.company_changecode, map);
        }
    }

    @Override
    public void getRequestData(int eventTag, String result) {
        super.getRequestData(eventTag, result);
        dismissPopupLoading();
        ResultBean bean = JSON.parseObject(result, ResultBean.class);
        if (bean != null && bean.getCode() == 0) {
            if (eventTag == ApiConstants.EventTags.company_changecode) {
                setResult(0);
                finish();
            }
        } else {
            ToastUtil.showToast(bean != null ? bean.getMsg() : "加载错误，请重试");
        }

    }

    @Override
    public void onRequestFailureException(int eventTag, String msg) {
        super.onRequestFailureException(eventTag, msg);
    }
}
