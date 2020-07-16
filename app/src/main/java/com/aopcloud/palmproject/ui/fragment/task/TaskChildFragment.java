package com.aopcloud.palmproject.ui.fragment.task;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.aopcloud.base.base.BaseFragment;
import com.aopcloud.base.util.ToastUtil;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.api.ApiConstants;
import com.aopcloud.palmproject.common.ResultBean;
import com.aopcloud.palmproject.ui.activity.task.AddChildTaskActivity;
import com.aopcloud.palmproject.ui.activity.task.bean.TaskChildBean;
import com.aopcloud.palmproject.ui.adapter.task.TaskChildAdapter;
import com.aopcloud.palmproject.ui.adapter.task.TaskChildManageAdapter;
import com.aopcloud.palmproject.utils.LoginUserUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @PackageName : com.aopcloud.palmproject.ui.fragment.task
 * @File : TaskExecuteFragment.java
 * @Date : 2020/5/8 2020/5/8
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class TaskChildFragment extends BaseFragment {
    @BindView(R.id.tv_add)
    TextView mTvAdd;
    @BindView(R.id.tv_manager)
    TextView mTvManager;
    @BindView(R.id.rv_list)
    RecyclerView mRvList;
    private String task_id;
    private String task_name;private String team_id;
    private boolean isMange;


    private TaskChildAdapter mAdapter;
    private TaskChildManageAdapter mManageAdapter;
    private List<TaskChildBean.SubordinatesBean> mBeanList = new ArrayList();


    private ItemTouchHelper mItemTouchHelper;
    private ItemDragAndSwipeCallback mItemDragAndSwipeCallback;

    public static TaskChildFragment getInstance(String task_id,String task_name,String team_id) {
        Bundle bundle = new Bundle();
        bundle.putString("task_id", task_id);
        bundle.putString("team_id",""+team_id);
        bundle.putString("task_name",""+task_name);
        TaskChildFragment fragment = new TaskChildFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initData() {
        super.initData();
        Bundle bundle = getArguments();
        if (bundle != null) {
            task_id = bundle.getString("task_id");
            task_name= bundle.getString("task_name");
            team_id= bundle.getString("team_id");
        }
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_task_child;
    }

    @Override
    protected void onFirstVisible() {
        super.onFirstVisible();
        toRequest(ApiConstants.EventTags.task_tasks);
    }

    @Override
    protected void initView(View view) {
        super.initView(view);

        mAdapter = new TaskChildAdapter(R.layout.item_task_child_list, mBeanList);
        mItemDragAndSwipeCallback = new ItemDragAndSwipeCallback(mAdapter) {
            @Override
            public boolean canDropOver(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder current, @NonNull RecyclerView.ViewHolder target) {
                return target.getAdapterPosition() != recyclerView.getAdapter().getItemCount() - 1 && super.canDropOver(recyclerView, current, target);
            }
        };
        mItemTouchHelper = new ItemTouchHelper(mItemDragAndSwipeCallback);
        mItemTouchHelper.attachToRecyclerView(mRvList);
        mAdapter.enableDragItem(mItemTouchHelper, R.id.iv_sort, false);


        mManageAdapter = new TaskChildManageAdapter(R.layout.item_task_child_manage, mBeanList);
        mRvList.setLayoutManager(new LinearLayoutManager(mActivity));
        mRvList.setAdapter(mManageAdapter);
    }

    private void setViewData(TaskChildBean taskBeans) {

        mBeanList.addAll(taskBeans.getSubordinates());
        mAdapter.notifyDataSetChanged();
        mManageAdapter.notifyDataSetChanged();
    }

    @Override
    protected void setListener() {
        super.setListener();
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.iv_del) {
                    mBeanList.remove(position);
                    mAdapter.notifyDataSetChanged();
                } else if (view.getId() == R.id.tv_name) {
                    mBeanList.get(position).setDevelop(!mBeanList.get(position).isComplete());
                    mAdapter.notifyItemChanged(position);
                }
            }
        });

        mManageAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.tv_name) {
                    mBeanList.get(position).setDevelop(!mBeanList.get(position).isDevelop());
                    mManageAdapter.notifyItemChanged(position);
                }
            }
        });
    }

    @OnClick({R.id.tv_add, R.id.tv_manager})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_add:
                Bundle bundle = new Bundle();
                bundle.putString("task_id", ""+task_id);
                bundle.putString("task_name", ""+task_name);
                bundle.putString("team_id", ""+team_id);
                gotoActivity(AddChildTaskActivity.class, bundle, 0);
                break;
            case R.id.tv_manager:
                String str = mTvManager.getText().toString();
                if (str.equals("管理")) {
                    mTvManager.setText("列表");
                    mTvAdd.setVisibility(View.GONE);
                    mRvList.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                } else {
                    mTvManager.setText("管理");
                    mTvAdd.setVisibility(View.VISIBLE);
                    mRvList.setAdapter(mManageAdapter);
                    mManageAdapter.notifyDataSetChanged();
                }
                break;
        }
    }

    @Override
    public void toRequest(int eventTag) {
        super.toRequest(eventTag);
        Map map = new HashMap();
        map.put("token", "" + LoginUserUtil.getToken(mActivity));
        map.put("code", "" + LoginUserUtil.getCurrentEnterpriseNo(mActivity));
        map.put("task_id", "" + task_id);
        if (eventTag == ApiConstants.EventTags.task_tasks) {
            iCommonRequestPresenter.requestPost(eventTag, mActivity, ApiConstants.task_tasks, map);
        }
    }

    @Override
    public void getRequestData(int eventTag, String result) {
        super.getRequestData(eventTag, result);
        ResultBean bean = JSON.parseObject(result, ResultBean.class);
        if (bean != null && bean.getCode() == 0) {
            if (eventTag == ApiConstants.EventTags.task_tasks) {
                TaskChildBean taskBeans = JSON.parseObject(bean.getData(), TaskChildBean.class);
                setViewData(taskBeans);
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