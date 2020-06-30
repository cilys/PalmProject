package com.aopcloud.palmproject.ui.activity.tag;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
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
import com.aopcloud.palmproject.ui.activity.tag.bean.DepartmentTagBean;
import com.aopcloud.palmproject.ui.adapter.tag.DepartmentTagSelectAdapter;
import com.aopcloud.palmproject.utils.LoginUserUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okio.Buffer;

/**
 * @PackageName : com.aopcloud.palmproject.ui.activity.worker
 * @File :DepartmentTagManagerActivity.java
 * @Date : 2020/4/22 2020/4/22
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
@Layout(R.layout.activity_department_tag_list_select)
public class DepartmentTagSelectActivity extends BaseActivity implements DepartmentTagSelectAdapter.OnItemClickListener {
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
    @BindView(R.id.tv_department)
    TextView mTvDepartment;
    @BindView(R.id.rv_list)
    RecyclerView mRvList;
    @BindView(R.id.tv_submit)
    TextView mTvSubmit;

    private List<DepartmentTagBean> mBeanList = new ArrayList();
    private DepartmentTagSelectAdapter mAdapter;

    private String department_name;
    private String department_id;
    private String del_tag_id;


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
        mTvHeaderTitle.setText("职务选择");
        mTvHeaderRight.setText("管理");


        mAdapter = new DepartmentTagSelectAdapter(R.layout.item_department_tag_select, mBeanList);
        mAdapter.setOnItemClickListener(this);
        mRvList.setLayoutManager(new LinearLayoutManager(this));
        mRvList.setAdapter(mAdapter);
    }

    private void setViewData(List<DepartmentTagBean> list) {
        mBeanList.clear();
        mBeanList.addAll(list);
        mAdapter.notifyDataSetChanged();
    }

    @OnClick({R.id.ll_header_back, R.id.ll_header_right, R.id.tv_submit})
    public void onViewClicked(View view) {
        Bundle bundle;
        switch (view.getId()) {
            case R.id.ll_header_back:
                finish();
                break;
            case R.id.ll_header_right:
                bundle = new Bundle();
                bundle.putString("department_id", "" + department_id);
                bundle.putString("department_name", "" + department_name);
                gotoActivity(DepartmentTagManagerActivity.class, bundle,0);
                break;
            case R.id.tv_submit:
                boolean select = false;
                String tagId = "";
                String tagName = "";
                for (int i = 0; i < mBeanList.size(); i++) {
                    if (mBeanList.get(i).isSelect()) {
                        select = true;
                        tagId = mBeanList.get(i).getTag_id() + "";
                        tagName = mBeanList.get(i).getName() + "";
                    }
                }
                if (select) {
                    bundle = new Bundle();
                    bundle.putString("tagId", tagId);
                    bundle.putString("tagName", tagName);
                    Intent intent = new Intent();
                    intent.putExtras(bundle);
                    setResult(0, intent);
                    finish();
                } else {
                    ToastUtil.showToast("请选择职务");
                }
                break;
        }
    }

    @Override
    public void toRequest(int eventTag) {
        super.toRequest(eventTag);
        Map map = new HashMap();
        map.put("token", "" + LoginUserUtil.getToken(this));
        map.put("code", "" + LoginUserUtil.getCurrentEnterpriseNo(this));
        if (eventTag == ApiConstants.EventTags.roletag_all) {
            map.put("department_id", "" + department_id);
            Logcat.i("------------" + eventTag + "/" + JSON.toJSONString(map));
            iCommonRequestPresenter.requestPost(eventTag, this, ApiConstants.roletag_all, map);
        } else if (eventTag == ApiConstants.EventTags.roletag_del) {
            map.put("tag_id", "" + del_tag_id);
            Logcat.i("------------" + eventTag + "/" + JSON.toJSONString(map));
            iCommonRequestPresenter.requestPost(eventTag, this, ApiConstants.roletag_del, map);
        }
    }

    @Override
    public void getRequestData(int eventTag, String result) {
        super.getRequestData(eventTag, result);
        dismissPopupLoading();
        Logcat.i("------------" + eventTag + "/" + result);
        ResultBean bean = JSON.parseObject(result, ResultBean.class);
        if (bean != null && bean.getCode() == 0) {
            if (eventTag == ApiConstants.EventTags.roletag_all) {
                List<DepartmentTagBean> list = JSON.parseArray(bean.getData(), DepartmentTagBean.class);
                setViewData(list);

            } else if (eventTag == ApiConstants.EventTags.roletag_del) {
                toRequest(ApiConstants.EventTags.roletag_all);
                ToastUtil.showToast("删除成功");
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
        toRequest(ApiConstants.EventTags.roletag_all);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        DepartmentTagBean departmentTagBean = mBeanList.get(position);

        for (int i = 0; i < mBeanList.size(); i++) {
            mBeanList.get(i).setSelect(false);
        }
        mBeanList.get(position).setSelect(!departmentTagBean.isSelect());
        mAdapter.notifyDataSetChanged();
    }
}