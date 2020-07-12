package com.aopcloud.palmproject.ui.activity.tag;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.aopcloud.base.annotation.Layout;
import com.aopcloud.base.base.BaseActivity;
import com.aopcloud.base.log.Logcat;
import com.aopcloud.base.util.ToastUtil;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.api.ApiConstants;
import com.aopcloud.palmproject.common.ResultBean;
import com.aopcloud.palmproject.utils.LoginUserUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @PackageName : com.aopcloud.palmproject.ui.activity.worker
 * @File : DepartmentTagAddActivity.java
 * @Date : 2020/4/22 2020/4/22
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
@Layout(R.layout.activity_department_tag_add)
public class DepartmentTagAddActivity extends BaseActivity {



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
    @BindView(R.id.switch_team)
    Switch mSwitchTeam;
    @BindView(R.id.spinner_project)
    Spinner mSpinnerProject;
    @BindView(R.id.switch_project)
    Switch mSwitchProject;
    @BindView(R.id.switch_department)
    Switch mSwitchDepartment;



    private String department_id = "";
    private String name;
    private String rights = "";

    @Override
    protected void initData() {
        super.initData();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            department_id = bundle.getString("department_id");
        }
    }


    @Override
    protected void initView() {
        mTvHeaderTitle.setText("添加职务");
        mIvHeaderMore.setImageResource(R.mipmap.icon_header_sure_w);

    }

    @OnClick({R.id.ll_header_back, R.id.ll_header_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_header_back:
                break;
            case R.id.ll_header_right:
                name = mEtName.getText().toString();
                if (TextUtils.isEmpty(name)) {
                    ToastUtil.showToast("请输入职务名称");
                    return;
                }
                if (mSwitchTeam.isChecked()) {
                    if (TextUtils.isEmpty(rights)) {
                        rights = "department:team";
                    }
                }
                if (mSwitchProject.isChecked()) {
                    String str = (String) mSpinnerProject.getSelectedItem();
                    if (rights.length() > 0) {
                        rights = rights + ",";
                    }
                    if (str.equals("全部")) {
                        rights = rights + "project:add" + "," + "project:edit" + "," + "project:find" + "";
                    } else if (str.equals("新建项目")) {
                        rights = rights + "project:add";
                    } else if (str.equals("编辑企业所有项目")) {
                        rights = rights + "project:edit";
                    } else if (str.equals("查看企业所有项目")) {
                        rights = rights + "project:find";

                    }
                }
                if (mSwitchDepartment.isChecked()) {
                    if (rights.length() > 0) {
                        rights = rights + ",";
                    }
                    rights = rights + "department:member";
                }
                toRequest(ApiConstants.EventTags.roletag_add);
                break;
        }
    }

    @Override
    public void toRequest(int eventTag) {
        super.toRequest(eventTag);
        Map map = new HashMap();
        map.put("token", "" + LoginUserUtil.getToken(this));
        map.put("code", "" + LoginUserUtil.getCurrentEnterpriseNo(this));
        if (eventTag == ApiConstants.EventTags.roletag_add) {
            map.put("department_id", "" + department_id);
            map.put("name", "" + name);
            map.put("rights", "" + rights);
            map.put("level", "" + 0);
            iCommonRequestPresenter.requestPost(eventTag, this, ApiConstants.roletag_add, map);
        }
    }

    @Override
    public void getRequestData(int eventTag, String result) {
        super.getRequestData(eventTag, result);
        dismissPopupLoading();
        ResultBean bean = JSON.parseObject(result, ResultBean.class);
        if (bean != null && bean.getCode() == 0) {
            if (eventTag == ApiConstants.EventTags.roletag_add) {
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