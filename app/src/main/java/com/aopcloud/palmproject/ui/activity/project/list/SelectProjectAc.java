package com.aopcloud.palmproject.ui.activity.project.list;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.aopcloud.base.annotation.Layout;
import com.aopcloud.base.util.ToastUtil;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.api.ApiConstants;
import com.aopcloud.palmproject.common.ResultBean;
import com.aopcloud.palmproject.ui.activity.BaseAc;
import com.aopcloud.palmproject.ui.activity.project.bean.ProjectListBean;
import com.aopcloud.palmproject.ui.fragment.home.HomeProjectFragment;
import com.aopcloud.palmproject.utils.LoginUserUtil;
import com.cily.utils.base.StrUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 选择项目
 */
@Layout(R.layout.ac_select_project)
public class SelectProjectAc extends BaseAc {
    private SwipeRefreshLayout srl;
    private List<ProjectListBean> datas;
    private RvSelectProjectAdapter adapter;
    private String selected_project_id;
    private String company_id;

    @Override
    protected void initView() {
        selected_project_id = getIntent().getStringExtra("selected_project_id");
        company_id = getIntent().getStringExtra("company_id");
        if (StrUtils.isEmpty(company_id)){
            company_id = LoginUserUtil.getCurrentEnterpriseNo(this);
        }

        findViewById(R.id.ll_header_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        TextView tv_title = (TextView)findViewById(R.id.tv_header_title);
        tv_title.setText("选择项目");
        ImageView img_right = (ImageView)findViewById(R.id.iv_header_more);
        img_right.setImageResource(R.mipmap.icon_header_sure);
        findViewById(R.id.ll_header_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                ProjectListBean b = getSelectedProject();
                if (b != null) {
                    i.putExtra("selected_project_id", String.valueOf(b.getProject_id()));
                    i.putExtra("selected_project_name", b.getName());
                }
                setResult(RESULT_OK, i);
                finish();
            }
        });


        srl = (SwipeRefreshLayout)findViewById(R.id.srl);
        datas = new ArrayList<>();
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                toRequest(ApiConstants.EventTags.project_all);
            }
        });
        RecyclerView rv = (RecyclerView)findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RvSelectProjectAdapter(datas);
        rv.setAdapter(adapter);

        toRequest(ApiConstants.EventTags.project_all);
    }

    @Override
    public void toRequest(int eventTag) {
        super.toRequest(eventTag);

        Map map = new HashMap();
        map.put("token", "" + LoginUserUtil.getToken(this));
        map.put("code", company_id);
        if (eventTag == ApiConstants.EventTags.project_all) {
            map.put("type", "" + 2);
            iCommonRequestPresenter.requestPost(eventTag, this, ApiConstants.project_all, map);
        }
    }

    private ProjectListBean getSelectedProject(){
        if (datas != null && datas.size() > 0){
            for (ProjectListBean b : datas) {
                if (b.isSelect()){
                    return b;
                }
            }
        }
        return null;
    }

    @Override
    public void getRequestData(int eventTag, String result) {
        super.getRequestData(eventTag, result);
        ResultBean bean = JSON.parseObject(result, ResultBean.class);
        if (bean != null && bean.getCode() == 0) {
            if (eventTag == ApiConstants.EventTags.project_all) {
                List<ProjectListBean> beanList = JSON.parseArray(bean.getData(), ProjectListBean.class);
                setViewData(beanList);
            }
        } else {
            ToastUtil.showToast(bean != null ? bean.getMsg() : "加载错误，请重试");
        }
        endRefresh();
    }

    private void setViewData(List<ProjectListBean> beanList){
        if (datas == null){
            datas = new ArrayList<>();
        }
        datas.clear();

        if (beanList != null && beanList.size() > 0){
            for (ProjectListBean b : beanList) {
                String status = b.getStatus();

                if (!StrUtils.isEmpty(selected_project_id)){
                    if (selected_project_id.equals(String.valueOf(b.getProject_id()))) {
                        b.setSelect(true);
                    }
                }

                if (HomeProjectFragment.STATE_stop.equals(status)
                    || HomeProjectFragment.STATE_termination.equals(status)){

                }else {
                    datas.add(b);
                }
            }
        }
        if (adapter != null){
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRequestFailureException(int eventTag, String msg) {
        super.onRequestFailureException(eventTag, msg);
        endRefresh();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        endRefresh();
    }

    private void endRefresh(){
        if (srl != null){
            srl.setRefreshing(false);
        }
    }
}