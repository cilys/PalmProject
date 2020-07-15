package com.aopcloud.palmproject.ui.activity.enterprise;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.aopcloud.base.annotation.Layout;
import com.aopcloud.base.util.ToastUtil;
import com.aopcloud.base.util.ViewUtil;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.api.ApiConstants;
import com.aopcloud.palmproject.bean.PermissionBean;
import com.aopcloud.palmproject.common.ResultBean;
import com.aopcloud.palmproject.ui.activity.BaseAc;
import com.aopcloud.palmproject.ui.activity.department.DepartmentLeaderSelectActivity;
import com.aopcloud.palmproject.ui.activity.enterprise.bean.EnterpriseManagerBean;
import com.aopcloud.palmproject.ui.adapter.enterprise.EnterprisePermissionAdapter;
import com.aopcloud.palmproject.utils.LoginUserUtil;
import com.aopcloud.palmproject.view.TipsDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @PackageName : com.aopcloud.palmproject.ui.activity.enterprise
 * @File : EnterpriseSettingManagerActivity.java
 * @Date : 2020/4/20 2020/4/20
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
@Layout(R.layout.activity_enterprise_manager_setting)
public class EnterpriseSettingManagerActivity extends BaseAc {

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
    @BindView(R.id.iv_more)
    ImageView mIvMore;
    @BindView(R.id.tv_permission)
    TextView mTvPermission;
    @BindView(R.id.switch_department)
    Switch mSwitchDepartment;
    @BindView(R.id.switch_info)
    Switch mSwitchInfo;
    @BindView(R.id.iv_tips)
    ImageView mIvTips;
    @BindView(R.id.tv_del)
    TextView mTvDel;

    private boolean edit;
    private EnterpriseManagerBean mManagerBean;

    private String rights = "";
    private String user_id = "";

    @Override
    protected void initData() {
        super.initData();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            edit = true;
            mManagerBean = JSON.parseObject(bundle.getString("info"), EnterpriseManagerBean.class);
        }
    }

    @Override
    protected void initView() {
        mTvHeaderTitle.setText(edit ? "编辑管理员" : "添加管理员");
        mTvHeaderRight.setText(edit ? "保存" : "添加");
        mTvDel.setVisibility(edit ? View.VISIBLE : View.GONE);
        if (mManagerBean != null) {

            user_id = "" + mManagerBean.getUser_id();
            rights = mManagerBean.getRights();
            mTvName.setText(mManagerBean.getUser_name() + "");
            mSwitchInfo.setChecked(mManagerBean.getRights().contains("company:info"));
            if (mManagerBean.getRights().equals("all")) {
                mSwitchDepartment.setChecked(true);
                mTvPermission.setText("所有权限");
            } else if (mManagerBean.getRights().contains("department:member")) {
                mSwitchDepartment.setChecked(true);
                mTvPermission.setText("团队成员管理");
            } else if (mManagerBean.getRights().contains("company:department")) {
                mSwitchDepartment.setChecked(true);
                mTvPermission.setText("部门管理");
            }
        }
    }


    private void showTips() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_enterprise_manager_tips);
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = ViewUtil.getScreenWidth(this) - ViewUtil.dp2px(this, 40);
        dialog.getWindow().setAttributes(lp);
        dialog.setCancelable(false);
        dialog.show();
        dialog.findViewById(R.id.tv_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }


    @OnClick({R.id.ll_header_back, R.id.ll_header_right, R.id.tv_permission, R.id.tv_name, R.id.iv_more, R.id.iv_tips, R.id.tv_del})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_header_back:
                finish();
                break;
            case R.id.ll_header_right:

                if (mSwitchDepartment.isChecked()) {

                } else {
                    rights = "";
                }
                if (mSwitchInfo.isChecked()) {
                    if (rights.length() > 0) {
                        rights = rights + ",company:info";
                    } else {
                        rights = "company:info";
                    }
                }


                if (edit) {


                    toRequest(ApiConstants.EventTags.manage_update);
                } else {
                    if (TextUtils.isEmpty(user_id)) {
                        ToastUtil.showToast("请选择管理员");
                        return;
                    }
                    showPopXupLoading("添加中");
                    toRequest(ApiConstants.EventTags.manage_add);
                }
                break;
            case R.id.tv_name:
            case R.id.iv_more:
                gotoActivity(DepartmentLeaderSelectActivity.class, 1);
                break;
            case R.id.tv_permission:
                showPermissionSelect();
                break;
            case R.id.iv_tips:
                showTips();
                break;
            case R.id.tv_del:
                TipsDialog.wrap(this).setMsg("确认删除").setOnActionClickListener(new TipsDialog.onActionClickListener() {
                    @Override
                    public void onSure(Dialog dialog) {
                        toRequest(ApiConstants.EventTags.manage_del);
                        dialog.dismiss();
                    }
                }).show();
                break;
        }
    }


    PermissionBean mPermissionBean;
    List<PermissionBean> mPermissionBeans = new ArrayList<>();
    private EnterprisePermissionAdapter permissionAdapter;

    public void showPermissionSelect() {
        final BottomSheetDialog sheetDialog = new BottomSheetDialog(this, R.style.BottomSheet_dialog);
        sheetDialog.setCancelable(true);
        sheetDialog.setContentView(R.layout.dialog_bottom_permission);
        sheetDialog.show();


        TextView mTvCancel = sheetDialog.findViewById(R.id.tv_cancel);
        TextView mTvSure = sheetDialog.findViewById(R.id.tv_sure);
        TextView mTvAdd = sheetDialog.findViewById(R.id.tv_add);
        TextView mTvTitle = sheetDialog.findViewById(R.id.tv_title);
        TextView mTvTips = sheetDialog.findViewById(R.id.tv_tips);
        RecyclerView mRvList = sheetDialog.findViewById(R.id.rv_list);

        mPermissionBeans.clear();
        PermissionBean permissionBean = new PermissionBean("所有权限", "all");
        mPermissionBeans.add(permissionBean);
        PermissionBean permissionBean2 = new PermissionBean("团队成员管理", "department:member");
        mPermissionBeans.add(permissionBean2);
        PermissionBean permissionBean3 = new PermissionBean("部门管理", "company:department");
        mPermissionBeans.add(permissionBean3);
        permissionAdapter = new EnterprisePermissionAdapter(R.layout.item_dialog_permission, mPermissionBeans);
        mRvList.setLayoutManager(new LinearLayoutManager(this));
        mRvList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRvList.setAdapter(permissionAdapter);
        permissionAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

                PermissionBean permissionBean1 = permissionAdapter.getItem(position);
                for (int i = 0; i < mPermissionBeans.size(); i++) {
                    mPermissionBeans.get(i).setSelect(false);
                }

                mPermissionBeans.get(position).setSelect(!permissionBean1.isSelect());
                rights = permissionAdapter.getItem(position).getPermission();
                permissionAdapter.notifyDataSetChanged();
            }
        });

        mTvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean select = false;
                for (int i = 0; i < mPermissionBeans.size(); i++) {
                    if (mPermissionBeans.get(i).isSelect()) {
                        mPermissionBean = mPermissionBeans.get(i);
                        select = true;
                    }
                }
                if (select) {
                    mTvPermission.setText(mPermissionBean.getName());
                    sheetDialog.dismiss();
                } else {
                    ToastUtil.showToast("请选择权限");
                }

            }
        });


        mTvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sheetDialog.dismiss();
            }
        });


    }

    @Override
    public void toRequest(int eventTag) {
        super.toRequest(eventTag);
        Map map = new HashMap();
        map.put("token", "" + LoginUserUtil.getToken(this));
        map.put("code", "" + LoginUserUtil.getCurrentEnterpriseNo(this));
        if (eventTag == ApiConstants.EventTags.manage_add) {
            map.put("user_id", "" + user_id);
            map.put("rights", "" + rights);
            iCommonRequestPresenter.requestPost(eventTag, this, ApiConstants.manage_add, map);
        } else if (eventTag == ApiConstants.EventTags.manage_update) {
            map.put("user_id", "" + user_id);
            map.put("rights", "" + rights);
            iCommonRequestPresenter.requestPost(eventTag, this, ApiConstants.manage_update, map);
        } else if (eventTag == ApiConstants.EventTags.manage_del) {
            map.put("user_id", "" + user_id);
            iCommonRequestPresenter.requestPost(eventTag, this, ApiConstants.manage_del, map);
        }
    }

    @Override
    public void getRequestData(int eventTag, String result) {
        super.getRequestData(eventTag, result);
        dismissPopupLoading();
        ResultBean bean = JSON.parseObject(result, ResultBean.class);
        if (bean != null && bean.getCode() == 0) {
            if (eventTag == ApiConstants.EventTags.manage_add) {
                ToastUtil.showToast(bean != null ? bean.getMsg() : "操作成功");
                setResult(0);
                finish();
            } else if (eventTag == ApiConstants.EventTags.manage_update) {
                ToastUtil.showToast(bean != null ? bean.getMsg() : "操作成功");
                setResult(0);
                finish();
            } else if (eventTag == ApiConstants.EventTags.manage_del) {
                ToastUtil.showToast(bean != null ? bean.getMsg() : "操作成功");
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            Bundle bundle = data.getExtras();
            user_id = bundle.getString("user_id", "");
            String user_name = bundle.getString("user_name");
            mTvName.setText("" + user_name);
        }
    }
}
