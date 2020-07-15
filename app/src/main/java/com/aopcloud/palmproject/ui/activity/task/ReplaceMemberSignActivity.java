package com.aopcloud.palmproject.ui.activity.task;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.aopcloud.base.annotation.Layout;
import com.aopcloud.base.util.ResourceUtil;
import com.aopcloud.base.util.ToastUtil;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.api.ApiConstants;
import com.aopcloud.palmproject.common.ResultBean;
import com.aopcloud.palmproject.ui.activity.BaseAc;
import com.aopcloud.palmproject.ui.activity.project.bean.DashboardAttendanceBean;
import com.aopcloud.palmproject.ui.adapter.task.ReplaceMemberSignAdapter;
import com.aopcloud.palmproject.utils.LoginUserUtil;
import com.aopcloud.palmproject.view.decoration.DividerItemDecoration;
import com.aopcloud.palmproject.view.segmented.SegmentItem;
import com.aopcloud.palmproject.view.segmented.SegmentedBarView;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @PackageName : com.aopcloud.palmproject.ui.activity.task
 * @File : ReplaceMemberSignActivity.java
 * @Date : 2020/5/18 2020/5/18
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
@Layout(R.layout.activity_task_replace_sign)
public class ReplaceMemberSignActivity extends BaseAc implements ReplaceMemberSignAdapter.OnItemClickListener, SegmentedBarView.OnSegItemClickListener {
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
    @BindView(R.id.sb_view)
    SegmentedBarView mSbView;
    @BindView(R.id.checkbox_all)
    CheckBox mCheckboxAll;
    @BindView(R.id.rv_list)
    RecyclerView mRvList;
    @BindView(R.id.tv_submit)
    TextView mTvSubmit;


    private ReplaceMemberSignAdapter mAdapter;
    private List<DashboardAttendanceBean> mBeanList = new ArrayList<>();
    private List<DashboardAttendanceBean> mSignInBeanList = new ArrayList<>();
    private List<DashboardAttendanceBean> mSignOutBeanList = new ArrayList<>();
    private String task_id;
    private String project_id;
    private int scope;
    private double task_latitude;
    private double task_longitude;
    private int type = 0;

    @Override
    protected void initData() {
        super.initData();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            scope = bundle.getInt("scope");
            task_latitude = bundle.getDouble("latitude");
            task_longitude = bundle.getDouble("longitude");
            task_id = bundle.getString("task_id");
            project_id = bundle.getString("project_id");
        }
        toRequest(ApiConstants.EventTags.attendance_all);
    }

    @Override
    protected void initView() {
        mTvHeaderTitle.setText("帮班员代签");

        mSbView.setOnSegItemClickListener(this::onSegItemClick);
        mCheckboxAll.clearAnimation();


        mAdapter = new ReplaceMemberSignAdapter(R.layout.item_task_replace_sign, mBeanList);
        mAdapter.setOnItemClickListener(this::onItemClick);
        mRvList.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration itemDecoration = new DividerItemDecoration.Builder()
                .color(ResourceUtil.getColor(R.color.theme_background_f5))
                .height(2)
                .build();
        mRvList.addItemDecoration(itemDecoration);
        mRvList.setAdapter(mAdapter);
    }

    @Override
    public void onSegItemClick(SegmentItem item, int position) {
        mBeanList.clear();
        type = position;
        if (position == 0) {
            mTvSubmit.setText("帮TA签到");
            mBeanList.addAll(mSignInBeanList);
        } else {
            mBeanList.addAll(mSignOutBeanList);
            mTvSubmit.setText("帮TA签退");
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        mBeanList.get(position).setSelect(!mBeanList.get(position).isSelect());
        mAdapter.notifyDataSetChanged();

        boolean all = true;
        for (int i = 0; i < mBeanList.size(); i++) {
            if (!mBeanList.get(i).isSelect()) {
                all = false;
            }
        }
        mCheckboxAll.setChecked(all);

    }


    private void setAttendanceView(List<DashboardAttendanceBean> beanList) {
        mSignInBeanList.clear();
        mSignOutBeanList.clear();
        mBeanList.clear();
        for (int i = 0; i < beanList.size(); i++) {
            if (beanList.get(i).getType() == 0) {
                mSignOutBeanList.add(beanList.get(i));
            } else if (beanList.get(i).getType() == 1) {
            } else if (beanList.get(i).getType() == 2) {
                mSignInBeanList.add(beanList.get(i));
            }
        }
        List<SegmentItem> m = new ArrayList();
        m.add(new SegmentItem("待签到(" + mSignInBeanList.size() + ")"));
        m.add(new SegmentItem("待签退(" + mSignOutBeanList.size() + ")"));
        mSbView.addSegmentedBars(m);
        mBeanList.addAll(mSignInBeanList);
        mAdapter.notifyDataSetChanged();

    }

    @OnClick({R.id.ll_header_back, R.id.ll_header_right, R.id.checkbox_all, R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_header_back:
                finish();
                break;
            case R.id.ll_header_right:
                break;
            case R.id.checkbox_all:
                for (int i = 0; i < mBeanList.size(); i++) {
                    mBeanList.get(i).setSelect(mCheckboxAll.isChecked());
                }
                mAdapter.notifyDataSetChanged();
                break;
            case R.id.tv_submit:
                List list = new ArrayList();

                if (type == 0) {
                    for (int i = 0; i < mSignInBeanList.size(); i++) {
                        if (mSignInBeanList.get(i).isSelect()) {
                            list.add(mSignInBeanList.get(i));
                        }
                    }
                } else {
                    for (int i = 0; i < mSignOutBeanList.size(); i++) {
                        if (mSignOutBeanList.get(i).isSelect()) {
                            list.add(mSignOutBeanList.get(i));
                        }
                    }
                }
                if (list.size() == 0) {
                    ToastUtil.showToast("请选择代签人员");
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString("json", JSON.toJSONString(list));
                bundle.putString("type", "" + type);
                bundle.putInt("scope", scope);
                bundle.putDouble("longitude", task_latitude);
                bundle.putDouble("latitude", task_longitude);
                bundle.putString("task_id", task_id);
                gotoActivity(TaskReportLocationActivity.class, bundle, 0);
                finish();
                break;
        }
    }


    @Override
    public void toRequest(int eventTag) {
        super.toRequest(eventTag);
        Map map = new HashMap();
        map.put("token", "" + LoginUserUtil.getToken(this));
        map.put("code", "" + LoginUserUtil.getCurrentEnterpriseNo(this));
        map.put("task_id", "" + task_id);
        if (eventTag == ApiConstants.EventTags.attendance_all) {
            map.put("start_time", "" + getStartTime(Calendar.getInstance()));
            map.put("end_time", "" + getEndTime(Calendar.getInstance()));
//            map.put("page", "" + 1);
//            map.put("page_size", "" + 100000);
            iCommonRequestPresenter.requestPost(eventTag, this, ApiConstants.attendance_all, map);
        }
    }

    @Override
    public void getRequestData(int eventTag, String result) {
        super.getRequestData(eventTag, result);
        ResultBean bean = JSON.parseObject(result, ResultBean.class);
        if (bean != null && bean.getCode() == 0) {
            if (eventTag == ApiConstants.EventTags.attendance_all) {
                List<DashboardAttendanceBean> beanList = JSON.parseArray(bean.getData(), DashboardAttendanceBean.class);
                setAttendanceView(beanList);
            }

        } else {
            ToastUtil.showToast(bean != null ? bean.getMsg() : "加载错误，请重试");
        }

    }

    @Override
    public void onRequestFailureException(int eventTag, String msg) {
        super.onRequestFailureException(eventTag, msg);
    }

    public static Long getStartTime(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis() / 1000;
    }

    public static Long getEndTime(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTimeInMillis() / 1000;
    }
}
