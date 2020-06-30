package com.aopcloud.palmproject.ui.fragment.project;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.aopcloud.base.base.BaseFragment;
import com.aopcloud.base.log.Logcat;
import com.aopcloud.base.util.KeyboardUtil;
import com.aopcloud.base.util.ToastUtil;
import com.aopcloud.base.util.ViewUtil;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.api.ApiConstants;
import com.aopcloud.palmproject.common.ResultBean;
import com.aopcloud.palmproject.ui.activity.map.SelectLocationActivity;
import com.aopcloud.palmproject.ui.activity.project.ProjectScenesAddActivity;
import com.aopcloud.palmproject.ui.activity.project.ProjectTaskAddActivity;
import com.aopcloud.palmproject.ui.activity.project.ProjectTaskDetailActivity;
import com.aopcloud.palmproject.ui.activity.project.bean.ProjectTaskBean;
import com.aopcloud.palmproject.ui.activity.project.bean.ProjectTeamListBean;
import com.aopcloud.palmproject.ui.adapter.base.CommonAdapter;
import com.aopcloud.palmproject.ui.adapter.base.ViewHolder;
import com.aopcloud.palmproject.ui.adapter.project.ProjectTaskAdapter;
import com.aopcloud.palmproject.ui.adapter.project.SpinnerTeamAdapter;
import com.aopcloud.palmproject.utils.LoginUserUtil;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @PackageName : com.aopcloud.palmproject.ui.fragment.project
 * @File : ProjectTaskFragment.java
 * @Date : 2020/5/3 2020/5/3
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class ProjectTaskFragment extends BaseFragment implements TextView.OnEditorActionListener {


    @BindView(R.id.et_search)
    EditText mEtSearch;
    @BindView(R.id.iv_chart)
    ImageView mIvChart;
    @BindView(R.id.rv_list)
    RecyclerView mRvList;
    @BindView(R.id.tv_create)
    TextView mTvCreate;
    @BindView(R.id.tv_assign)
    TextView mTvAssign;
    @BindView(R.id.checkbox)
    CheckBox mCheckbox;
    @BindView(R.id.tv_type)
    TextView mTvType;

    private ProjectTaskAdapter mAdapter;
    private List<ProjectTaskBean> mAllList = new ArrayList();
    private List<ProjectTaskBean> mBeanList = new ArrayList();
    private List<ProjectTeamListBean> mTeamListBeans = new ArrayList<>();

    public static ProjectTaskFragment getInstance(String project_id) {
        ProjectTaskFragment fragment = new ProjectTaskFragment();
        Bundle bundle = new Bundle();
        bundle.putString("project_id", "" + project_id);
        fragment.setArguments(bundle);
        return fragment;
    }

    private String project_id;
    private String task_type = "0";

    @Override
    protected void initData() {
        super.initData();
        Bundle bundle = getArguments();
        if (bundle != null) {
            project_id = bundle.getString("project_id");
        }
        toRequest(ApiConstants.EventTags.project_tasks);
        toRequest(ApiConstants.EventTags.project_team);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_project_worker_sheet;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mAdapter = new ProjectTaskAdapter(R.layout.item_project_work_sheet, mBeanList);
        mRvList.setLayoutManager(new LinearLayoutManager(mActivity));
        mRvList.setAdapter(mAdapter);
        mAdapter.setEmptyView(R.layout.layout_common_empty, mRvList);
        mAdapter.isUseEmpty(true);

        mEtSearch.setOnEditorActionListener(this::onEditorAction);

    }


    private void setViewData(List<ProjectTaskBean> beanList) {

        mAllList.clear();
        mAllList.addAll(beanList);
        setFilter("全部");
    }


    private void setFilter(String type) {
        mBeanList.clear();
        if (type.equals("全部")) {
            mBeanList.addAll(mAllList);
        } else {
            for (int i = 0; i < mAllList.size(); i++) {
                if (type.equals(mAllList.get(i).getStatus_str())) {
                    mBeanList.add(mAllList.get(i));
                }
            }
        }
        mTvType.setText("" + type);
        mAdapter.notifyDataSetChanged();
    }


    private void searchList(String k) {
        mBeanList.clear();
        for (int i = 0; i < mAllList.size(); i++) {
            if (mAllList.get(i).getName().contains(k)) {
                mBeanList.add(mAllList.get(i));
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void setListener() {
        super.setListener();
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("project_id", "" + project_id);
                bundle.putString("task_id", "" + mBeanList.get(position).getTask_id());
                gotoActivity(ProjectTaskDetailActivity.class, 0, bundle);
            }
        });
        mCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    task_type = "1";
                } else {
                    task_type = "0";
                }
                toRequest(ApiConstants.EventTags.project_tasks);
            }
        });
    }

    @OnClick({R.id.iv_chart, R.id.tv_create, R.id.tv_assign, R.id.checkbox, R.id.tv_type})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_chart:
                ToastUtil.showToast("敬请期待");
                break;
            case R.id.checkbox:
                toRequest(ApiConstants.EventTags.project_tasks);
                break;
            case R.id.tv_type:
                showTaskTypeDialog();
                break;
            case R.id.tv_create:
                Bundle bundle = new Bundle();
                bundle.putString("project_id", "" + project_id);
                gotoActivity(ProjectTaskAddActivity.class, 0, bundle);
                break;
            case R.id.tv_assign:
                showTaskListDialog();
                break;
        }
    }

    public void showTaskTypeDialog() {
        BottomSheetDialog dialog = new BottomSheetDialog(mActivity);
        dialog.setContentView(R.layout.dialog_project_task_type);
        dialog.show();
        dialog.findViewById(R.id.tv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.tv_all).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFilter("全部");
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.tv_arrange).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFilter("未安排");
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.tv_start_before).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFilter("未开始");
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.tv_progress).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFilter("进行中");
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.tv_time_out).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFilter("已逾期");
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.tv_complete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFilter("已完成");
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.tv_stop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFilter("已暂停");
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFilter("已取消");
                dialog.dismiss();
            }
        });
    }

    public void showTaskListDialog() {
        Dialog dialog = new Dialog(mActivity);
        dialog.setContentView(R.layout.dialog_project_task_list);
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        dialog.getWindow().setBackgroundDrawableResource(R.color.theme_transparent);
        lp.width = ViewUtil.getScreenWidth(mActivity) - ViewUtil.dp2px(mActivity, 80);
        dialog.getWindow().setAttributes(lp);
        dialog.show();


        List list = new ArrayList();
        for (int i = 0; i <mAllList.size() ; i++) {
            if (mAllList.get(i).getStatus_str().contains("未安排")){
                list.add(mAllList.get(i));
            }
        }
        ListView listView = dialog.findViewById(R.id.lv_list);
        listView.setAdapter(new CommonAdapter<ProjectTaskBean>(mActivity, R.layout.item_project_task_list, list) {
            @Override
            public void convert(ViewHolder holder, ProjectTaskBean o, int position) {
                holder.setText(R.id.tv_name, o.getName());

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showAssignTaskDialog(mBeanList.get(position));
                dialog.dismiss();
            }
        });
    }

    private TextView mTvAssignDays;
    private TextView mTvAssignStartTime;
    private TextView mTvAssignEndTime;

    private String assignTaskId;
    private String assignAddress;
    private String assignRange;
    private String assignLongitude;
    private String assignLatitude;
    private String assignStartTime;
    private String assignEndTime;
    private String assignLevel;
    private String assignTeamId;

    public void showAssignTaskDialog(ProjectTaskBean taskBean) {
        BottomSheetDialog dialog = new BottomSheetDialog(mActivity);
        dialog.setContentView(R.layout.dialog_project_assign_task);
        dialog.getWindow().findViewById(R.id.design_bottom_sheet).setBackgroundResource(android.R.color.transparent);
        dialog.show();

        TextView mTvTitle = dialog.findViewById(R.id.tv_title);
        mTvAssignDays = dialog.findViewById(R.id.tv_day);
        mTvAssignStartTime = dialog.findViewById(R.id.tv_start_time);
        mTvAssignEndTime = dialog.findViewById(R.id.tv_end_time);
        Spinner mSpLevel = dialog.findViewById(R.id.spinner_level);
        Spinner mSpTeam = dialog.findViewById(R.id.spinner_team);

        SpinnerTeamAdapter adapter = new SpinnerTeamAdapter(mActivity, R.layout.item_task_spinner_team, mTeamListBeans);
        mSpTeam.setAdapter(adapter);


        mTvTitle.setText("" + taskBean.getName());
        dialog.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
//        dialog.findViewById(R.id.tv_address).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                gotoActivity(SelectLocationActivity.class, 2);
//            }
//        });
        dialog.findViewById(R.id.tv_start_time).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker(1);
            }
        });
        dialog.findViewById(R.id.tv_end_time).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker(2);
            }
        });

        dialog.findViewById(R.id.tv_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assignTaskId = taskBean.getTask_id() + "";
                assignLevel = (String) mSpLevel.getSelectedItem();
                assignTeamId = mTeamListBeans.get(mSpTeam.getSelectedItemPosition()).getTeam_id() + "";

                if (TextUtils.isEmpty(assignStartTime)) {
                    ToastUtil.showToast("请选择开始时间");
                    return;
                }
                if (TextUtils.isEmpty(assignStartTime)) {
                    ToastUtil.showToast("请选择结束时间");
                    return;
                }
//                if (TextUtils.isEmpty(assignAddress)) {
//                    ToastUtil.showToast("请选择地址");
//                    return;
//                }

                if (!TextUtils.isEmpty(assignStartTime)
                        && !TextUtils.isEmpty(assignStartTime)
                        && !TextUtils.isEmpty(assignStartTime)) {

                    toRequest(ApiConstants.EventTags.task_assign);
                    dialog.dismiss();
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
        map.put("project_id", "" + project_id);//项目名称
        if (eventTag == ApiConstants.EventTags.project_tasks) {
            map.put("type", "" + task_type);
            Logcat.i("------------" + eventTag + "/" + JSON.toJSONString(map));
            iCommonRequestPresenter.requestPost(eventTag, mActivity, ApiConstants.project_tasks, map);
        } else if (eventTag == ApiConstants.EventTags.project_team) {
            Logcat.i("------------" + eventTag + "/" + JSON.toJSONString(map));
            iCommonRequestPresenter.requestPost(eventTag, mActivity, ApiConstants.project_team, map);
        } else if (eventTag == ApiConstants.EventTags.task_assign) {
            map.put("task_id", "" + assignTaskId);
            map.put("start_date", "" + assignStartTime);
            map.put("level", "" + assignLevel);
            map.put("team_id", "" + assignTeamId);
            map.put("end_date", "" + assignEndTime);
//            map.put("address", "" + assignAddress);
//            map.put("longitue", "" + assignLongitude);
//            map.put("latitude", "" + assignLatitude);
//            map.put("scope", "" + assignRange);
            Logcat.i("------------" + eventTag + "/" + JSON.toJSONString(map));
            iCommonRequestPresenter.requestPost(eventTag, mActivity, ApiConstants.task_assign, map);
        }
    }

    @Override
    public void getRequestData(int eventTag, String result) {
        super.getRequestData(eventTag, result);
        Logcat.i("------------" + eventTag + "/" + result);
        ResultBean bean = JSON.parseObject(result, ResultBean.class);
        if (bean != null && bean.getCode() == 0) {
            if (eventTag == ApiConstants.EventTags.project_tasks) {
                List<ProjectTaskBean> beanList = JSON.parseArray(bean.getData(), ProjectTaskBean.class);
                setViewData(beanList);
            } else if (eventTag == ApiConstants.EventTags.project_team) {
                List<ProjectTeamListBean> beanList = JSON.parseArray(bean.getData(), ProjectTeamListBean.class);
                mTeamListBeans.clear();
                mTeamListBeans.addAll(beanList);
            } else if (eventTag == ApiConstants.EventTags.task_assign) {
                ToastUtil.showToast(bean != null ? bean.getMsg() : "操作成功");
                toRequest(ApiConstants.EventTags.project_tasks);
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

    public void showTimePicker(int type) {
        TimePickerView pickerView = new TimePickerBuilder(mActivity, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                if (type == 1) {
                    mTvAssignStartTime.setText(dateFormat.format(date));
                    assignStartTime = dateFormat.format(date);

                    String startTime = mTvAssignStartTime.getText().toString();
                    String endTime = mTvAssignEndTime.getText().toString();

                    if (!TextUtils.isEmpty(startTime) && !TextUtils.isEmpty(endTime)) {
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        try {
                            Date startDate = format.parse(startTime);
                            Date endDate = format.parse(endTime);
                            long betweenDays = ((endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24));
                            mTvAssignDays.setText("" + betweenDays);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }

                } else if (type == 2) {
                    mTvAssignEndTime.setText(dateFormat.format(date));
                    assignEndTime = dateFormat.format(date);

                    String startTime = mTvAssignStartTime.getText().toString();
                    String endTime = mTvAssignEndTime.getText().toString();

                    if (!TextUtils.isEmpty(startTime) && !TextUtils.isEmpty(endTime)) {
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        try {
                            Date startDate = format.parse(startTime);
                            Date endDate = format.parse(endTime);
                            long betweenDays = ((endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24));
                            mTvAssignDays.setText("" + betweenDays);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        })
                .setLineSpacingMultiplier(2)
                .isDialog(true)
                .setDecorView((ViewGroup) mRootView)
                .build();
        pickerView.show();


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }

        if (requestCode == 0) {
            toRequest(ApiConstants.EventTags.project_tasks);
        }
        if (requestCode == 2) {
            Bundle bundle = data.getExtras();
            assignLongitude = bundle.getString("longitude");
            assignLatitude = bundle.getString("latitude");
            assignAddress = bundle.getString("address");
            assignRange = bundle.getString("range");

        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        String keyWord = v.getText().toString();
        if (TextUtils.isEmpty(keyWord)) {
            ToastUtil.showToast("请输入搜索内容");
        } else {
            KeyboardUtil.closeKeyboard(mEtSearch,mActivity);
            mEtSearch.clearFocus();
            searchList(keyWord);
        }
        return true;
    }
}
