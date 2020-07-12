package com.aopcloud.palmproject.ui.activity.tag;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.aopcloud.base.annotation.Layout;
import com.aopcloud.base.base.BaseActivity;
import com.aopcloud.base.util.ResourceUtil;
import com.aopcloud.base.util.ToastUtil;
import com.aopcloud.base.util.ViewUtil;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.api.ApiConstants;
import com.aopcloud.palmproject.common.ResultBean;
import com.aopcloud.palmproject.ui.activity.tag.bean.DepartmentTagBean;
import com.aopcloud.palmproject.ui.adapter.tag.DepartmentTagManageAdapter;
import com.aopcloud.palmproject.ui.adapter.tag.DepartmentTagSelectAdapter;
import com.aopcloud.palmproject.utils.LoginUserUtil;
import com.aopcloud.palmproject.view.TipsDialog;
import com.aopcloud.palmproject.view.decoration.HorizontalDividerItemDecoration;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @PackageName : com.aopcloud.palmproject.ui.activity.worker
 * @File :DepartmentTagManagerActivity.java
 * @Date : 2020/4/22 2020/4/22
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
@Layout(R.layout.activity_department_tag_manger)
public class DepartmentTagManagerActivity extends BaseActivity implements DepartmentTagSelectAdapter.OnItemChildClickListener {


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
    @BindView(R.id.rv_list)
    RecyclerView mRvList;

    private LinearLayout mLlAddPost;
    private List<DepartmentTagBean> mBeanList = new ArrayList();
    private DepartmentTagManageAdapter mAdapter;

    private String department_name;
    private String department_id;
    private String del_tag_id;


    private ItemTouchHelper mItemTouchHelper;
    private ItemDragAndSwipeCallback mItemDragAndSwipeCallback;

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
        mTvHeaderTitle.setText("职务管理");
        mIvHeaderMore.setImageResource(R.mipmap.icon_header_sure_w);

        mTvDepartment.setText("" + department_name);

        mAdapter = new DepartmentTagManageAdapter(R.layout.item_department_tag_manager, mBeanList);
        mItemDragAndSwipeCallback = new ItemDragAndSwipeCallback(mAdapter) {
            @Override
            public boolean canDropOver(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder current, @NonNull RecyclerView.ViewHolder target) {
                return target.getAdapterPosition() != recyclerView.getAdapter().getItemCount() - 1 && super.canDropOver(recyclerView, current, target);
            }
        };
        mItemTouchHelper = new ItemTouchHelper(mItemDragAndSwipeCallback);
        mItemTouchHelper.attachToRecyclerView(mRvList);
        mAdapter.enableDragItem(mItemTouchHelper, R.id.iv_sort, false);
        mAdapter.setOnItemChildClickListener(this);
        View view = getLayoutInflater().inflate(R.layout.footer_layout_department_tag_manager, (ViewGroup) mRvList.getParent(), false);
        mLlAddPost = view.findViewById(R.id.ll_post_add);
        mAdapter.removeAllFooterView();
        mAdapter.addFooterView(view);
        HorizontalDividerItemDecoration decoration = new HorizontalDividerItemDecoration.Builder(this)
                .margin(0)
                .size(ViewUtil.dp2px(this, 2))
                .color(ResourceUtil.getColor(R.color.transparent))
                .build();
        mRvList.addItemDecoration(decoration);
        mRvList.setLayoutManager(new LinearLayoutManager(this));
        mRvList.setAdapter(mAdapter);

    }

    private void setViewData(List<DepartmentTagBean> list) {
        mBeanList.clear();
        mBeanList.addAll(list);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

        if (view.getId() == R.id.iv_edit) {
            Bundle bundle = new Bundle();
            bundle.putString("department_id", department_id);
            bundle.putString("json", JSON.toJSONString(mBeanList.get(position)));
            gotoActivity(DepartmentTagEditActivity.class, bundle, 0);
        } else if (view.getId() == R.id.iv_del) {
            TipsDialog.wrap(this).setMsg("确认删除？").setOnActionClickListener(new TipsDialog.onActionClickListener() {
                @Override
                public void onSure(Dialog dialog) {
                    del_tag_id = mBeanList.get(position).getTag_id() + "";
                    showPopXupLoading("删除中");
                    toRequest(ApiConstants.EventTags.roletag_del);
                    dialog.dismiss();
                }
            }).show();

        } else if (view.getId() == R.id.iv_sort) {

        }

    }

    @Override
    protected void setListener() {
        super.setListener();
        mLlAddPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("department_id", "" + department_id);
                gotoActivity(DepartmentTagAddActivity.class, bundle, 0);
            }
        });
    }

    @OnClick({R.id.ll_header_back, R.id.ll_header_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_header_back:
                finish();
                break;
            case R.id.ll_header_right:


                ids.clear();
                sort.clear();
                for (int i = 0; i < mBeanList.size(); i++) {
                    ids.add(mBeanList.get(i).getTag_id() + "");
                    sort.add(mBeanList.size()- 1);
                }
                toRequest(ApiConstants.EventTags.roletag_sort);
                break;
        }
    }

    List ids = new ArrayList();
    List<Integer> sort = new ArrayList<>();

    public String getList(List list) {
        return list.toString().replace("[", "").replace("]", "");
    }

    @Override
    public void toRequest(int eventTag) {
        super.toRequest(eventTag);
        Map map = new HashMap();
        map.put("token", "" + LoginUserUtil.getToken(this));
        map.put("code", "" + LoginUserUtil.getCurrentEnterpriseNo(this));
        map.put("department_id", "" + department_id);
        if (eventTag == ApiConstants.EventTags.roletag_all) {
            map.put("department_id", "" + department_id);
            iCommonRequestPresenter.requestPost(eventTag, this, ApiConstants.roletag_all, map);
        } else if (eventTag == ApiConstants.EventTags.roletag_del) {
            map.put("tag_id", "" + del_tag_id);
            iCommonRequestPresenter.requestPost(eventTag, this, ApiConstants.roletag_del, map);
        } else if (eventTag == ApiConstants.EventTags.roletag_sort) {
            map.put("tag_ids", "" + getList(ids));
            map.put("levels", "" + getList(sort));
            iCommonRequestPresenter.requestPost(eventTag, this, ApiConstants.roletag_sort, map);
        }
    }


    @Override
    public void getRequestData(int eventTag, String result) {
        super.getRequestData(eventTag, result);
        dismissPopupLoading();
        ResultBean bean = JSON.parseObject(result, ResultBean.class);
        if (bean != null && bean.getCode() == 0) {
            if (eventTag == ApiConstants.EventTags.roletag_all) {
                List<DepartmentTagBean> list = JSON.parseArray(bean.getData(), DepartmentTagBean.class);
                setViewData(list);

            } else if (eventTag == ApiConstants.EventTags.roletag_del) {
                toRequest(ApiConstants.EventTags.roletag_all);
                ToastUtil.showToast("删除成功");
            } else if (eventTag == ApiConstants.EventTags.roletag_sort) {
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
        toRequest(ApiConstants.EventTags.roletag_all);
    }
}