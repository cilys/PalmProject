package com.aopcloud.palmproject.ui.fragment.news;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.aopcloud.base.base.BaseFragment;
import com.aopcloud.base.common.BaseEvent;
import com.aopcloud.base.log.Logcat;
import com.aopcloud.base.util.ResourceUtil;
import com.aopcloud.base.util.ToastUtil;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.api.ApiConstants;
import com.aopcloud.palmproject.bean.MsgTodoBean;
import com.aopcloud.palmproject.bean.MsgTodoNotifyBean;
import com.aopcloud.palmproject.common.ResultBean;
import com.aopcloud.palmproject.ui.activity.project.ProjectTaskDetailActivity;
import com.aopcloud.palmproject.ui.activity.staff.StaffApprovalDetailActivity;
import com.aopcloud.palmproject.ui.activity.task.TaskUpdateProgressActivity;
import com.aopcloud.palmproject.ui.adapter.msg.MsgTodoNotifyAdapter;
import com.aopcloud.palmproject.utils.ListUtil;
import com.aopcloud.palmproject.utils.LoginUserUtil;
import com.aopcloud.palmproject.view.decoration.DividerItemDecoration;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * @PackageName : com.aopcloud.basic.ui.fragment.news
 * @File : NewsTodoFragment.java
 * @Date : 2020/4/11 2020/4/11
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class NewsTodoFragment extends BaseFragment {
    @BindView(R.id.rv_list)
    RecyclerView mRvList;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mRefreshLayout;


    private MsgTodoNotifyAdapter mAdapter;
    private List<MsgTodoNotifyBean> mBeanList = new ArrayList<>();

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_rv;
    }

    @Override
    protected boolean isImmersionBarEnabled() {
        return false;
    }

    @Override
    protected void initData() {
        super.initData();
        mBeanList.clear();
        if (!TextUtils.isEmpty(LoginUserUtil.getCurrentEnterpriseNo(mActivity))) {
            toRequest(ApiConstants.EventTags.msg_todo);
        }

    }

    @Override
    protected void onVisible() {
        super.onVisible();
        if (!TextUtils.isEmpty(LoginUserUtil.getCurrentEnterpriseNo(mActivity))) {
            toRequest(ApiConstants.EventTags.msg_todo);
        }

    }

    @Override
    protected void initView(View view) {
        super.initView(view);

        mRefreshLayout.setRefreshHeader(new ClassicsHeader(mActivity));
        mRefreshLayout.setRefreshFooter(new ClassicsFooter(mActivity));
        mRefreshLayout.setEnableLoadMore(false);

        mAdapter = new MsgTodoNotifyAdapter(R.layout.item_msg_notify_todo, mBeanList);
        mRvList.setLayoutManager(new LinearLayoutManager(mActivity));
        DividerItemDecoration itemDecoration = new DividerItemDecoration.Builder()
                .color(ResourceUtil.getColor(R.color.theme_background_f5))
                .height(2)
                .build();
        mRvList.addItemDecoration(itemDecoration);
        mRvList.setAdapter(mAdapter);
    }

    private void setViewData(MsgTodoBean msgTodoBean) {
        mRefreshLayout.finishRefresh();
        mBeanList.clear();
        if (!ListUtil.isEmpty(msgTodoBean.getPersonnel_approval())) {
            for (int i = 0; i < msgTodoBean.getPersonnel_approval().size(); i++) {
                MsgTodoNotifyBean workNotifyBean = new MsgTodoNotifyBean();
                workNotifyBean.setType(1);
                workNotifyBean.setUser_id(msgTodoBean.getPersonnel_approval().get(i).getUser_id());
                workNotifyBean.setCompany_user_id(msgTodoBean.getPersonnel_approval().get(i).getCompany_user_id());
                workNotifyBean.setUser_status(msgTodoBean.getPersonnel_approval().get(i).getUser_status());
                workNotifyBean.setMsg("" + msgTodoBean.getPersonnel_approval().get(i).getUser_name() + "申请加入 ...");
                workNotifyBean.setTime(msgTodoBean.getPersonnel_approval().get(i).getMake_time());
                workNotifyBean.setTitle("新成员加入审批");
                workNotifyBean.setTypeName("审批");
                mBeanList.add(workNotifyBean);
            }

        }
        if (!ListUtil.isEmpty(msgTodoBean.getTasks_doing())) {
            for (int i = 0; i < msgTodoBean.getTasks_doing().size(); i++) {
                MsgTodoNotifyBean workNotifyBean = new MsgTodoNotifyBean();
                workNotifyBean.setType(2);
                workNotifyBean.setTitle("您的任务");
                workNotifyBean.setTask_id(msgTodoBean.getTasks_doing().get(i).getTask_id());
                workNotifyBean.setProject_id(msgTodoBean.getTasks_doing().get(i).getProject_id());
                workNotifyBean.setName(msgTodoBean.getTasks_doing().get(i).getName());
                workNotifyBean.setWork_value(msgTodoBean.getTasks_doing().get(i).getWork_value());
                workNotifyBean.setWork_unit(msgTodoBean.getTasks_doing().get(i).getWork_unit());
                workNotifyBean.setMsg(msgTodoBean.getTasks_doing().get(i).getProject_name() + "/" + msgTodoBean.getTasks_doing().get(i).getName());
                workNotifyBean.setTime(msgTodoBean.getTasks_doing().get(i).getMake_time());
                workNotifyBean.setTypeName("任务");
                mBeanList.add(workNotifyBean);
            }

        }
        if (!ListUtil.isEmpty(msgTodoBean.getTask_for_feedback())) {
            for (int i = 0; i < msgTodoBean.getTask_for_feedback().size(); i++) {
                MsgTodoNotifyBean workNotifyBean = new MsgTodoNotifyBean();
                workNotifyBean.setType(3);
                workNotifyBean.setTask_id(msgTodoBean.getTask_for_feedback().get(i).getTask_id());
                workNotifyBean.setProject_id(msgTodoBean.getTask_for_feedback().get(i).getProject_id());
                workNotifyBean.setName(msgTodoBean.getTask_for_feedback().get(i).getName());
                workNotifyBean.setWork_value(msgTodoBean.getTask_for_feedback().get(i).getWork_value());
                workNotifyBean.setWork_unit(msgTodoBean.getTask_for_feedback().get(i).getWork_unit());
                workNotifyBean.setTitle("有1个任务待反馈");
                workNotifyBean.setMsg(msgTodoBean.getTask_for_feedback().get(i).getProject_name() + "/" + msgTodoBean.getTask_for_feedback().get(i).getName());
                workNotifyBean.setTime(msgTodoBean.getTask_for_feedback().get(i).getMake_time());
                workNotifyBean.setTypeName("任务");
                mBeanList.add(workNotifyBean);
            }


        }
        if (mOnLoadCountListener!=null){
            mOnLoadCountListener.onLoadCount(mBeanList.size());
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void setListener() {
        super.setListener();
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                toRequest(ApiConstants.EventTags.msg_todo);
            }
        });

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (mBeanList.get(position).getType() == 1) {
                    Bundle bundle = new Bundle();
                    bundle.putString("StaffListBean", JSON.toJSONString(mBeanList.get(position)));
                    bundle.putString("user_id", "" + mBeanList.get(position).getUser_id());
                    bundle.getString("status", "" + mBeanList.get(position).getUser_status());
                    bundle.putString("company_user_id", "" + mBeanList.get(position).getCompany_user_id());
                    gotoActivity(StaffApprovalDetailActivity.class, bundle,0);
                } else if (mBeanList.get(position).getType() == 2) {
                    Bundle bundle = new Bundle();
                    bundle.putString("project_id", "" + mBeanList.get(position).getProject_id());
                    bundle.putString("task_id", "" + mBeanList.get(position).getTask_id());
                    gotoActivity(ProjectTaskDetailActivity.class, bundle,0);
                } else if (mBeanList.get(position).getType() == 3) {
                    Bundle bundle = new Bundle();
                    bundle.putString("task_id", "" + mBeanList.get(position).getTask_id());
                    bundle.putString("work_unit", mBeanList.get(position).getWork_unit() + "");
                    bundle.putString("work_value", mBeanList.get(position).getWork_value() + "");
                    bundle.putString("task_name", "" + mBeanList.get(position).getName());
                    bundle.putBoolean("child", mBeanList.get(position).getPid() != 0);
                    gotoActivity(TaskUpdateProgressActivity.class, bundle,0);
                }
            }
        });
    }

    @Override
    public void toRequest(int eventTag) {
        super.toRequest(eventTag);
        Map map = new HashMap();
        map.put("token", "" + LoginUserUtil.getToken(mActivity));
        map.put("code", "" + LoginUserUtil.getCurrentEnterpriseNo(mActivity));
        if (eventTag == ApiConstants.EventTags.msg_todo) {
            Logcat.i("------------" + eventTag + "/" + JSON.toJSONString(map));
            iCommonRequestPresenter.requestPost(eventTag, mActivity, ApiConstants.msg_todo, map);
        }
    }

    @Override
    public void getRequestData(int eventTag, String result) {
        super.getRequestData(eventTag, result);
        Logcat.i("------------" + eventTag + "/" + result);
        mActivity.dismissPopupLoading();
        ResultBean bean = JSON.parseObject(result, ResultBean.class);
        if (bean != null && bean.getCode() == 0) {
            if (eventTag == ApiConstants.EventTags.msg_todo) {
                MsgTodoBean msgTodoBean = JSON.parseObject(bean.getData(), MsgTodoBean.class);
                setViewData(msgTodoBean);
            }
        } else {
            ToastUtil.showToast(bean != null ? bean.getMsg() : "加载错误，请重试");
            mRefreshLayout.finishRefresh();
        }

    }

    @Override
    public void onRequestFailureException(int eventTag, String msg) {
        super.onRequestFailureException(eventTag, msg);
        Logcat.i("------------" + eventTag + "/" + msg);
        mRefreshLayout.finishRefresh();
    }

    @Override
    public void onEvent(BaseEvent postResult) {
        super.onEvent(postResult);
        if (postResult.type.equals(BaseEvent.EVENT_LOGIN)) {
            toRequest(ApiConstants.EventTags.msg_todo);
        }
    }
    OnLoadCountListener mOnLoadCountListener;

    public void setOnLoadCountListener(OnLoadCountListener onLoadCountListener) {
        mOnLoadCountListener = onLoadCountListener;
    }

    public   interface OnLoadCountListener{
        void onLoadCount(int size);
    }
}
