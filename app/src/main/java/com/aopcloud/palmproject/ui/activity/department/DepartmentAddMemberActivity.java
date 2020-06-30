package com.aopcloud.palmproject.ui.activity.department;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.aopcloud.base.annotation.Layout;
import com.aopcloud.base.base.BaseActivity;
import com.aopcloud.base.log.Logcat;
import com.aopcloud.base.util.ToastUtil;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.api.ApiConstants;
import com.aopcloud.palmproject.common.ResultBean;
import com.aopcloud.palmproject.ui.activity.tag.DepartmentTagSelectActivity;
import com.aopcloud.palmproject.utils.LoginUserUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @PackageName : com.aopcloud.palmproject.ui.activity.department
 * @File : DepartmentAddMemberActivity.java
 * @Date : 2020/4/22 2020/4/22
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
@Layout(R.layout.activity_department_add_member)
public class DepartmentAddMemberActivity extends BaseActivity {


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
    @BindView(R.id.tv_department)
    TextView mTvDepartment;
    @BindView(R.id.tv_member)
    TextView mTvMember;
    @BindView(R.id.tv_post)
    TextView mTvPost;
    @BindView(R.id.iv_member)
    ImageView mIvMember;
    @BindView(R.id.iv_post)
    ImageView mIvPost;
    private String department_id = "";
    private String department_name = "";

    private String role_tag_id;

    private String user_id;

    @Override
    protected void initData() {
        super.initData();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            department_id = bundle.getString("department_id");
            department_name = bundle.getString("department_name");
        }
        toRequest(ApiConstants.EventTags.roletag_all);
    }

    @Override
    protected void initView() {
        mIvHeaderMore.setImageResource(R.mipmap.icon_header_sure);
        mTvDepartment.setText("" + department_name);

    }


    @OnClick({R.id.ll_header_back, R.id.ll_header_right, R.id.tv_member, R.id.iv_member, R.id.iv_post,R.id.tv_post})
    public void onViewClicked(View view) {
        Bundle bundle;
        switch (view.getId()) {
            case R.id.ll_header_back:finish();
                break;
            case R.id.ll_header_right:
                if (TextUtils.isEmpty(user_id)) {
                    ToastUtil.showToast("请选择部门成员");
                    return;
                }
                if (TextUtils.isEmpty(role_tag_id)) {
                    ToastUtil.showToast("请选择职务");
                    return;
                }
                toRequest(ApiConstants.EventTags.department_adduser);
                break;
            case R.id.iv_member:
            case R.id.tv_member:
                gotoActivity(DepartmentLeaderSelectActivity.class, 2);
                break;
            case R.id.iv_post:
            case R.id.tv_post:
                bundle = new Bundle();
                bundle.putString("department_id", "" + department_id);
                bundle.putString("department_name", "" + department_name);
                gotoActivity(DepartmentTagSelectActivity.class, bundle, 3);
                break;
        }
    }

    @Override
    public void toRequest(int eventTag) {
        super.toRequest(eventTag);
        Map map = new HashMap();
        map.put("token", "" + LoginUserUtil.getToken(this));
        map.put("code", "" + LoginUserUtil.getCurrentEnterpriseNo(this));
        map.put("department_id", "" + department_id);
        if (eventTag == ApiConstants.EventTags.department_adduser) {
            map.put("user_id", "" + user_id);
            map.put("role_tag_id", "" + role_tag_id);
            Logcat.i("------------" + eventTag + "/" + JSON.toJSONString(map));
            iCommonRequestPresenter.requestPost(eventTag, this, ApiConstants.department_adduser, map);
        }
    }

    @Override
    public void getRequestData(int eventTag, String result) {
        super.getRequestData(eventTag, result);
        Logcat.i("------------" + eventTag + "/" + result);
        dismissPopupLoading();
        ResultBean bean = JSON.parseObject(result, ResultBean.class);
        if (bean != null && bean.getCode() == 0) {
            if (eventTag == ApiConstants.EventTags.department_adduser) {
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
        Logcat.i("------------" + eventTag + "/" + msg);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        if (requestCode == 1) {
            gotoActivity(DepartmentLeaderSelectActivity.class, data.getExtras(), 2);
        } else if (requestCode == 2) {
            Bundle bundle = data.getExtras();
            user_id = bundle.getString("user_id", "");
            String user_name = bundle.getString("user_name", "");
            mTvMember.setText("" + user_name);
        } else if (requestCode == 3) {
            Bundle bundle = data.getExtras();
            role_tag_id = bundle.getString("tagId", "");
            String tagName = bundle.getString("tagName", "");
            mTvPost.setText("" + tagName);
        }
    }

}