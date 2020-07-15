package com.aopcloud.palmproject.ui.activity.department;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.aopcloud.base.annotation.Layout;
import com.aopcloud.base.util.ToastUtil;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.api.ApiConstants;
import com.aopcloud.palmproject.common.ResultBean;
import com.aopcloud.palmproject.ui.activity.BaseAc;
import com.aopcloud.palmproject.ui.activity.department.bean.DepartmentNodeBean;
import com.aopcloud.palmproject.utils.LoginUserUtil;
import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @PackageName : com.aopcloud.palmproject.ui.activity.enterprise
 * @File : EnterpriseSettingActivity.java
 * @Date : 2020/4/20 2020/4/20
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
@Layout(R.layout.activity_enterprise_department)
public class EnterpriseDepartmentActivity extends BaseAc {
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
    ViewGroup mNodeRoot;
    @BindView(R.id.tv_department_child)
    TextView mTvDepartmentChild;
    @BindView(R.id.tv_department)
    TextView mTvDepartment;


    private TreeNode root;
    private String del_department_id;

    @Override
    protected void initData() {
        super.initData();
        toRequest(ApiConstants.EventTags.department_all);
    }

    @Override
    protected void initView() {
        mTvHeaderTitle.setText("组织架构管理");

    }

    private void setViewData(List<DepartmentNodeBean> beanList) {
        root = TreeNode.root();
        List<TreeNode> node = new ArrayList();
        for (int i = 0; i < beanList.size(); i++) {
            TreeNode treeNode = new TreeNode(beanList.get(i)).setViewHolder(new MyHolder(this));
            if (null != beanList.get(i).getChilden() && beanList.get(i).getChilden().size() > 0) {
                addNode(beanList.get(i).getChilden(), treeNode);
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


    @OnClick({R.id.ll_header_back, R.id.ll_header_right, R.id.tv_department_child, R.id.tv_department})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_header_back:
                finish();
                break;
            case R.id.ll_header_right:
                break;
            case R.id.tv_department_child:
                gotoActivity(DepartmentSelectActivity.class, 2);
                break;
            case R.id.tv_department:
                gotoActivity(EnterpriseDepartmentAddActivity.class, 0);
                break;
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
        } else if (eventTag == ApiConstants.EventTags.department_del) {
            map.put("department_id", "" + del_department_id);
            iCommonRequestPresenter.requestPost(eventTag, this, ApiConstants.department_del, map);
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
        if (requestCode==0){
            toRequest(ApiConstants.EventTags.department_all);
        }else {
//            gotoActivity(TeamAddActivity.class, data.getExtras(), 0);
        }
    }

    public class MyHolder extends TreeNode.BaseNodeViewHolder<DepartmentNodeBean> {
        ImageView mImageView;

        public MyHolder(Context context) {
            super(context);
        }

        @Override
        public View createNodeView(TreeNode node, DepartmentNodeBean nodeBean) {
            final LayoutInflater inflater = LayoutInflater.from(context);
            final View view = inflater.inflate(R.layout.item_layout_department_node, null, false);
            mImageView = view.findViewById(R.id.iv_folder);
            TextView tvValue = (TextView) view.findViewById(R.id.tv_node_name);
            tvValue.setText(nodeBean.getName());
            view.findViewById(R.id.iv_del).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    del_department_id = nodeBean.getDepartment_id() + "";
                    getTreeView().removeNode(node);
                    showPopXupLoading("删除中");
                    toRequest(ApiConstants.EventTags.department_del);
                }
            });
            view.findViewById(R.id.iv_edit).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("json",JSON.toJSONString(nodeBean));
                    gotoActivity(EnterpriseDepartmentEditActivity.class, bundle, 0);
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