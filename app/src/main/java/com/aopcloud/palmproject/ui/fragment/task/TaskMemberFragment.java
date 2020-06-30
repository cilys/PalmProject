package com.aopcloud.palmproject.ui.fragment.task;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.aopcloud.base.base.BaseFragment;
import com.aopcloud.base.log.Logcat;
import com.aopcloud.base.util.ToastUtil;
import com.aopcloud.palmproject.BuildConfig;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.api.ApiConstants;
import com.aopcloud.palmproject.common.ResultBean;
import com.aopcloud.palmproject.loader.AppImageLoader;
import com.aopcloud.palmproject.ui.activity.project.bean.ProjectTaskDetailBean;
import com.aopcloud.palmproject.ui.activity.team.bean.TeamMemberBean;
import com.aopcloud.palmproject.ui.adapter.task.TaskMemberAdapter;
import com.aopcloud.palmproject.utils.LoginUserUtil;
import com.aopcloud.palmproject.view.CircleImageView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @PackageName : com.aopcloud.palmproject.ui.fragment.task
 * @File : TaskMemberFragment.java
 * @Date : 2020/5/8 2020/5/8
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class TaskMemberFragment extends BaseFragment {

    String task_id;
    @BindView(R.id.iv_assigner_img)
    CircleImageView mIvAssignerImg;
    @BindView(R.id.tv_assigner_name)
    TextView mTvAssignerName;
    @BindView(R.id.tv_enterprise_name)
    TextView mTvEnterpriseName;
    @BindView(R.id.tv_assign_time)
    TextView mTvAssignTime;
    @BindView(R.id.tv_team)
    TextView mTvTeam;
    @BindView(R.id.tv_enterprise_name_2)
    TextView mTvEnterpriseName2;
    @BindView(R.id.iv_leader_img)
    CircleImageView mIvLeaderImg;
    @BindView(R.id.tv_leader_name)
    TextView mTvLeaderName;
    @BindView(R.id.tv_receipt_time)
    TextView mTvReceiptTime;
    @BindView(R.id.rv_list)
    RecyclerView mRvList;


    private TaskMemberAdapter mAdapter;
    private List<TeamMemberBean> mBeanList = new ArrayList<>();
    public static TaskMemberFragment getInstance(String task_id) {
        Bundle bundle = new Bundle();
        bundle.putString("task_id", task_id);
        TaskMemberFragment fragment = new TaskMemberFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initData() {
        super.initData();
        Bundle bundle = getArguments();
        if (bundle != null) {
            task_id = bundle.getString("task_id");
        }

    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_task_member;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);

        mAdapter = new TaskMemberAdapter(R.layout.item_task_team_user,mBeanList);
        mRvList.setLayoutManager(new LinearLayoutManager(mActivity,LinearLayoutManager.HORIZONTAL,false));
        mRvList.setAdapter(mAdapter);
    }

    @Override
    protected void onFirstVisible() {
        super.onFirstVisible();
        toRequest(ApiConstants.EventTags.task_get);
    }

    String team_id;

    private void setViewData(ProjectTaskDetailBean mTaskDetailBean) {

        if (mTaskDetailBean != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            date.setTime(mTaskDetailBean.getAssign_time()*1000);
            mTvAssignTime.setText("" + dateFormat.format(date));
            mTvAssignerName.setText("" + mTaskDetailBean.getLeader_name());
            AppImageLoader.loadCircleImage(mActivity
                    , BuildConfig.BASE_URL + mTaskDetailBean.getLeader_avatar()
                    , mIvAssignerImg);
            mTvReceiptTime.setText("" + dateFormat.format(date));
            mTvTeam.setText(""+mTaskDetailBean.getTeam_name());
            mTvEnterpriseName.setText("");
            mTvEnterpriseName2.setText("");

            team_id = mTaskDetailBean.getTeam_id() + "";
            toRequest(ApiConstants.EventTags.teammember_all);

        }
    }

    private void setTeamData(List<TeamMemberBean> beanList) {

        List<TeamMemberBean> list = new ArrayList<>();
        for (int i = 0; i <beanList.size() ; i++) {
            if (beanList.get(i).getType()==1){
                mTvLeaderName.setText(""+beanList.get(i).getUser_name());
                AppImageLoader.loadCircleImage(mActivity
                        , BuildConfig.BASE_URL + beanList.get(i).getAvatar()
                        , mIvLeaderImg);

            }else {
                list.add(beanList.get(i));
            }
        }
        mBeanList.clear();
        mBeanList.addAll(list);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void toRequest(int eventTag) {
        super.toRequest(eventTag);
        Map map = new HashMap();
        map.put("token", "" + LoginUserUtil.getToken(mActivity));
        map.put("code", "" + LoginUserUtil.getCurrentEnterpriseNo(mActivity));
        map.put("task_id", "" + task_id);
        if (eventTag == ApiConstants.EventTags.task_get) {
            Logcat.i("------------" + eventTag + "/" + JSON.toJSONString(map));
            iCommonRequestPresenter.requestPost(eventTag, mActivity, ApiConstants.task_get, map);
        } else if (eventTag == ApiConstants.EventTags.teammember_all) {
            map.put("team_id", "" + team_id);//项目名称
            Logcat.i("------------" + eventTag + "/" + JSON.toJSONString(map));
            iCommonRequestPresenter.requestPost(eventTag, mActivity, ApiConstants.teammember_all, map);
        }
    }

    @Override
    public void getRequestData(int eventTag, String result) {
        super.getRequestData(eventTag, result);
        Logcat.i("------------" + eventTag + "/" + result);
        ResultBean bean = JSON.parseObject(result, ResultBean.class);
        if (bean != null && bean.getCode() == 0) {
            if (eventTag == ApiConstants.EventTags.task_get) {
                ProjectTaskDetailBean mTaskDetailBean = JSON.parseObject(bean.getData(), ProjectTaskDetailBean.class);
                setViewData(mTaskDetailBean);
            } else if (eventTag == ApiConstants.EventTags.teammember_all) {
                List<TeamMemberBean> beanList = JSON.parseArray(bean.getData(), TeamMemberBean.class);
                setTeamData(beanList);
            }
        } else {
            ToastUtil.showToast(bean != null ? bean.getMsg() : "加载错误，请重试");
        }

    }

}
