package com.aopcloud.palmproject.ui.activity.department;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.aopcloud.base.annotation.Layout;
import com.aopcloud.base.base.BaseActivity;
import com.aopcloud.base.log.Logcat;
import com.aopcloud.base.util.ToastUtil;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.api.ApiConstants;
import com.aopcloud.palmproject.common.ResultBean;
import com.aopcloud.palmproject.ui.activity.department.bean.DepartmentNodeBean;
import com.aopcloud.palmproject.utils.LoginUserUtil;
import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @PackageName : com.aopcloud.palmproject.ui.activity.enterprise
 * @File : DepartmentSelectActivity.java
 * @Date : 2020/4/19 2020/4/19
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
@Layout(R.layout.activity_department_select)
public class DepartmentSelectActivity extends BaseActivity {
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
    @BindView(R.id.node_root)
    RelativeLayout mNodeRoot;
    @BindView(R.id.tv_next)
    TextView mTvNext;


    private TreeNode root;
    private List<DepartmentNodeBean> mNodeBeans = new ArrayList<>();

    @Override
    protected void initData() {
        super.initData();
        toRequest(ApiConstants.EventTags.department_all);
    }

    @Override
    protected void initView() {
        mTvHeaderTitle.setText("所属部门");
    }

    private void setViewData(List<DepartmentNodeBean> beanList) {
        mNodeBeans = beanList;
        root = TreeNode.root();
        List<TreeNode> node = new ArrayList();
        for (int i = 0; i < mNodeBeans.size(); i++) {
            TreeNode treeNode = new TreeNode(mNodeBeans.get(i)).setViewHolder(new MyHolder(this));
            if (null != mNodeBeans.get(i).getChilden() && mNodeBeans.get(i).getChilden().size() > 0) {
                addNode(mNodeBeans.get(i).getChilden(), treeNode);
            }
            node.add(treeNode);
        }
        root.addChildren(node);

        AndroidTreeView treeView = new AndroidTreeView(this, root);
        treeView.setDefaultAnimation(true);
        treeView.setDefaultContainerStyle(R.style.TreeNodeStyleDivided, true);
        mNodeRoot.removeAllViews();
        mNodeRoot.addView(treeView.getView());
    }


    private void addNode(List<DepartmentNodeBean> beanList, TreeNode node) {
        List treeNodes = new ArrayList();
        for (int i = 0; i < beanList.size(); i++) {
            TreeNode treeNode = new TreeNode(beanList.get(i)).setViewHolder(new MyHolder(this));
            if (null != beanList.get(i).getChilden() && beanList.get(i).getChilden().size() > 0) {
                addNode(beanList.get(i).getChilden(), treeNode);
            }
            treeNodes.add(treeNode);
        }
        node.addChildren(treeNodes);

    }


    @OnClick({R.id.ll_header_back, R.id.ll_header_right, R.id.tv_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_header_back:
                break;
            case R.id.ll_header_right:
                break;
            case R.id.tv_next:
                getSelect(mNodeBeans);
                if (selectDepartments.size() > 1) {
                    ToastUtil.showToast("只能选择一个上级部门");
                    return;
                }
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("department_id", "" + selectDepartments.get(0).getDepartment_id());
                bundle.putString("department_name", "" + selectDepartments.get(0).getName());
                intent.putExtras(bundle);
                setResult(0,intent);
                finish();
                break;
        }
    }

    List<DepartmentNodeBean> selectDepartments = new ArrayList<>();

    public void getSelect(List<DepartmentNodeBean> beanList) {
        for (int i = 0; i < beanList.size(); i++) {
            if (beanList.get(i).isSelect()) {
                selectDepartments.add(beanList.get(i));
            }
            if (null != beanList.get(i).getChilden() && beanList.get(i).getChilden().size() > 0) {
                getSelect(beanList.get(i).getChilden());
            }
        }
    }

    @Override
    public void toRequest(int eventTag) {
        super.toRequest(eventTag);
        Map map = new HashMap();
        map.put("token", "" + LoginUserUtil.getToken(this));
        map.put("code", "" + LoginUserUtil.getCurrentEnterpriseNo(this));
        if (eventTag == ApiConstants.EventTags.department_all) {
            map.put("loop", "1");
            iCommonRequestPresenter.requestPost(eventTag, this, ApiConstants.department_all, map);
        }
    }

    @Override
    public void getRequestData(int eventTag, String result) {
        super.getRequestData(eventTag, result);
        dismissPopupLoading();
        ResultBean bean = JSON.parseObject(result, ResultBean.class);
        if (bean != null && bean.getCode() == 0) {
            if (eventTag == ApiConstants.EventTags.department_all) {
                String json = bean.getData().substring(1, bean.getData().length() - 1);
                List<DepartmentNodeBean> beanList = JSON.parseArray(json, DepartmentNodeBean.class);
                setViewData(beanList);
            } else if (eventTag == ApiConstants.EventTags.department_del) {
                toRequest(ApiConstants.EventTags.department_all);
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
        if (resultCode == 0) {
            toRequest(ApiConstants.EventTags.department_all);
        }
    }

    public class MyHolder extends TreeNode.BaseNodeViewHolder<DepartmentNodeBean> {
        ImageView mImageView;
        CheckBox mCheckBox;

        public MyHolder(Context context) {
            super(context);
        }

        @Override
        public View createNodeView(TreeNode node, DepartmentNodeBean nodeBean) {
            final LayoutInflater inflater = LayoutInflater.from(context);
            final View view = inflater.inflate(R.layout.item_ldepartment_node_select, null, false);
            mImageView = view.findViewById(R.id.iv_folder);
            mCheckBox = view.findViewById(R.id.checkbox);
            TextView tvValue = (TextView) view.findViewById(R.id.tv_node_name);
            tvValue.setText(nodeBean.getName());

            mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    nodeBean.setSelect(isChecked);
                }
            });
            return view;
        }

        @Override
        public void toggle(boolean active) {
            mImageView.setImageResource(active ? R.mipmap.icon_folder_s : R.mipmap.icon_folder_n);
        }

        @Override
        public void toggleSelectionMode(boolean editModeEnabled) {

        }

        @Override
        public int getContainerStyle() {
            return R.style.TreeNodeStyleCustom;
        }
    }


}