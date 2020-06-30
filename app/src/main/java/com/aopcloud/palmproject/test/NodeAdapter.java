package com.aopcloud.palmproject.test;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.aopcloud.palmproject.R;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @PackageName : com.aopcloud.palmproject.test
 * @File : NodeAdapter.java
 * @Date : 2020/4/22 9:52
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @VersionCode : V 1.0
 * @Describe ：
 */
public class NodeAdapter extends BaseQuickAdapter<DeptNodeBean, BaseViewHolder> {


    public NodeAdapter(int layoutResId, @Nullable List<DeptNodeBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DeptNodeBean item) {
        helper.setText(R.id.tv_name,item.getName());


        if (null!=item.get_childrenList()&&item.get_childrenList().size()>0){
            RecyclerView recyclerView = helper.getView(R.id.rv_list);
            NodeAdapter adapter = new NodeAdapter(R.layout.a_test_item_node,item.get_childrenList());
            recyclerView.setAdapter(adapter);
        }

    }



}
