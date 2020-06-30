package com.aopcloud.palmproject.test;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aopcloud.base.annotation.Layout;
import com.aopcloud.base.base.BaseActivity;
import com.aopcloud.palmproject.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.unnamed.b.atv.model.TreeNode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @PackageName : com.aopcloud.palmproject.test
 * @File : TreeListActivity.java
 * @Date : 2020/4/22 9:48
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @VersionCode : V 1.0
 * @Describe ：
 */
@Layout(R.layout.activity_rv)
public class TreeListActivity extends BaseActivity {


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
    @BindView(R.id.rv_list)
    RecyclerView mRvList;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mRefreshLayout;


    private NodeAdapter mNodeAdapter;
    private List<DeptNodeBean> mList = new ArrayList();

    @Override
    protected void initData() {
        super.initData();
        DeptNodeBean deptNodeBean;
        List<DeptNodeBean> mList;
        for (int i = 0; i < 5; i++) {
            deptNodeBean = new DeptNodeBean();
            mList = new ArrayList();
            for (int j = 0; j < 3; j++) {
                deptNodeBean = new DeptNodeBean();
                ArrayList arrayList = new ArrayList();
                for (int k = 0; k < 4; k++) {
                    deptNodeBean = new DeptNodeBean();
                    ArrayList arrayList1 = new ArrayList();
                    deptNodeBean.set_childrenList(arrayList);
                    arrayList.add(deptNodeBean);
                }
                deptNodeBean.set_childrenList(arrayList);
                mList.add(deptNodeBean);
            }
            deptNodeBean.set_childrenList(mList);
            mList.add(deptNodeBean);
        }

    }

    @Override
    protected void initView() {
        mRefreshLayout.setEnableLoadMore(false);
        mRefreshLayout.setEnableRefresh(false);
        mNodeAdapter = new NodeAdapter(R.layout.a_test_item_node, mList);
        mRvList.setLayoutManager(new LinearLayoutManager(this));
        mRvList.setAdapter(mNodeAdapter);

    }


    @OnClick(R.id.ll_header_back)
    public void onViewClicked() {
    }
}
