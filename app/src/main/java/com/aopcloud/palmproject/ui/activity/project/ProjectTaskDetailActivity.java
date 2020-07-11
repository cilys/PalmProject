package com.aopcloud.palmproject.ui.activity.project;

import android.app.Dialog;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.aopcloud.base.annotation.Layout;
import com.aopcloud.base.base.BaseActivity;
import com.aopcloud.base.log.Logcat;
import com.aopcloud.base.util.ResourceUtil;
import com.aopcloud.base.util.ToastUtil;
import com.aopcloud.base.util.ViewUtil;
import com.aopcloud.palmproject.BuildConfig;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.api.ApiConstants;
import com.aopcloud.palmproject.bean.PopMenuBean;
import com.aopcloud.palmproject.common.ResultBean;
import com.aopcloud.palmproject.ui.activity.camera.PreviewActivity;
import com.aopcloud.palmproject.ui.activity.map.SelectLocationActivity;
import com.aopcloud.palmproject.ui.activity.project.bean.ProjectTaskDetailBean;
import com.aopcloud.palmproject.ui.activity.project.bean.ProjectTeamListBean;
import com.aopcloud.palmproject.ui.activity.task.TaskDetailActivity;
import com.aopcloud.palmproject.ui.adapter.file.PreviewAdapter;
import com.aopcloud.palmproject.ui.adapter.project.ProjectScenesChildAdapter;
import com.aopcloud.palmproject.ui.adapter.project.ProjectTaskTrendsAdapter;
import com.aopcloud.palmproject.ui.adapter.project.SpinnerTeamAdapter;
import com.aopcloud.palmproject.utils.LbsUtil;
import com.aopcloud.palmproject.utils.LoginUserUtil;
import com.aopcloud.palmproject.view.PopContextMenu;
import com.aopcloud.palmproject.view.decoration.DividerItemDecoration;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vip.devkit.view.common.dtextview.DrawableTextView;

/**
 * @PackageName : com.aopcloud.palmproject.ui.activity.project
 * @File : ProjectTaskAddActivity.java
 * @Date : 2020/5/7 2020/5/7
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
@Layout(R.layout.activity_project_task_detail)
public class ProjectTaskDetailActivity extends BaseActivity {
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
    @BindView(R.id.tv_discuss)
    DrawableTextView mTvDiscuss;
    @BindView(R.id.tv_plan_edit)
    DrawableTextView mTvPlanEdit;
    @BindView(R.id.tv_assign)
    DrawableTextView mTvAssign;
    @BindView(R.id.tv_action)
    DrawableTextView mTvAction;
    @BindView(R.id.tv_project_name)
    TextView mTvProjectName;
    @BindView(R.id.tv_task_name)
    TextView mTvTaskName;
    @BindView(R.id.tv_count_unit)
    TextView mTvCountUnit;
    @BindView(R.id.tv_state)
    TextView mTvState;
    @BindView(R.id.tv_progress)
    TextView mTvProgress;
    @BindView(R.id.tv_plan_start)
    TextView mTvPlanStart;
    @BindView(R.id.tv_plan_end)
    TextView mTvPlanEnd;
    @BindView(R.id.tv_plan_day)
    TextView mTvPlanDay;
    @BindView(R.id.tv_execute_start)
    TextView mTvExecuteStart;
    @BindView(R.id.tv_execute_end)
    TextView mTvExecuteEnd;
    @BindView(R.id.tv_execute_day)
    TextView mTvExecuteDay;
    @BindView(R.id.tv_address)
    TextView mTvAddress;
    @BindView(R.id.iv_address)
    ImageView mIvAddress;
    @BindView(R.id.tv_team_type)
    TextView mTvTeamType;
    @BindView(R.id.iv_team_img)
    ImageView mIvTeamImg;
    @BindView(R.id.tv_team_name)
    TextView mTvTeamName;
    @BindView(R.id.tv_team_count)
    TextView mTvTeamCount;
    @BindView(R.id.tv_team_progress)
    TextView mTvTeamProgress;
    @BindView(R.id.iv_team_scene)
    ImageView mIvTeamScene;
    @BindView(R.id.tv_scene)
    TextView mTvScene;
    @BindView(R.id.ll_assign)
    LinearLayout mLlAssign;
    @BindView(R.id.tv_demand)
    TextView mTvDemand;
    @BindView(R.id.tv_img_count)
    TextView mTvImgCount;
    @BindView(R.id.rv_list)
    RecyclerView mRvList;
    @BindView(R.id.tv_level)
    TextView mTvLevel;
    @BindView(R.id.rv_list_img)
    RecyclerView mRvImgList;


    private String task_id;
    private String project_id;
    private ProjectTaskDetailBean mTaskDetailBean;
    private List<ProjectTeamListBean> mTeamListBeans = new ArrayList<>();
    private List<ProjectTaskDetailBean.TrendsBean> mTrendsBeans = new ArrayList<>();

    private ProjectTaskTrendsAdapter mTrendsAdapter;

    @Override
    protected void initData() {
        super.initData();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            task_id = bundle.getString("task_id");
            project_id = bundle.getString("project_id");
        }
        toRequest(ApiConstants.EventTags.project_team);
        toRequest(ApiConstants.EventTags.task_get);
    }

    @Override
    protected void initView() {
        mTvHeaderTitle.setText("工单详情");

        mTrendsAdapter = new ProjectTaskTrendsAdapter(R.layout.item_task_detail_trends, mTrendsBeans);
        mRvList.setLayoutManager(new LinearLayoutManager(this));
        mRvList.setAdapter(mTrendsAdapter);
    }


    private void setViewData() {
        if (mTaskDetailBean==null){
            return;
        }

        mTvProjectName.setText("" + mTaskDetailBean.getProject_name());
        mTvTaskName.setText("" + mTaskDetailBean.getName());
        mTvCountUnit.setText("" + mTaskDetailBean.getWork_value_finished() + "/" + mTaskDetailBean.getWork_value() + "" + mTaskDetailBean.getWork_unit());
        mTvState.setText(mTaskDetailBean.getStatus_str());
        mTvState.setTextColor(getStateColor(mTaskDetailBean.getStatus_str()));
        mTvAddress.setText("" + mTaskDetailBean.getAddress());
        mTvDemand.setText("" + mTaskDetailBean.getWork_des());


        List list = new ArrayList();
        if (!TextUtils.isEmpty(mTaskDetailBean.getAttach())) {
            String[] url = mTaskDetailBean.getAttach().split(",");
            for (int i = 0; i < url.length; i++) {
                list.add(BuildConfig.BASE_URL + url[i]);
            }
        }
        mTvImgCount.setText("附件(" + list.size() + "/9)");

        ProjectScenesChildAdapter adapter = new ProjectScenesChildAdapter(R.layout.item_task_file, list);
        DividerItemDecoration itemDecoration = new DividerItemDecoration.Builder()
                .color(ResourceUtil.getColor(R.color.theme_white))
                .width(12)
                .build();
        mRvImgList.addItemDecoration(itemDecoration);
        mRvImgList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mRvImgList.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<PreviewAdapter.PreviewBean> urls = new ArrayList();
                for (int i = 0; i < list.size(); i++) {
                    urls.add(new PreviewAdapter.PreviewBean(list.get(i).toString()));

                }
                Bundle bundle = new Bundle();
                bundle.putSerializable("PreviewBean", (Serializable) urls);
                gotoActivity(PreviewActivity.class, bundle);

            }
        });

        if (mTaskDetailBean.getStatus_str().equals("未安排")) {
            mTvProgress.setVisibility(View.GONE);
            mLlAssign.setVisibility(View.GONE);

            mTvDiscuss.setVisibility(View.GONE);
            mTvPlanEdit.setVisibility(View.GONE);
            mTvAssign.setText("派工");
        } else {
            mLlAssign.setVisibility(View.VISIBLE);
            mTvProgress.setVisibility(View.VISIBLE);
            mTvDiscuss.setVisibility(View.VISIBLE);
            mTvPlanEdit.setVisibility(View.VISIBLE);


            String startTime = mTaskDetailBean.getStart_date();
            String endTime = mTaskDetailBean.getEnd_date();
            long betweenDays = 0;
            if (!TextUtils.isEmpty(startTime) && !TextUtils.isEmpty(endTime)) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date startDate = format.parse(startTime);
                    Date endDate = format.parse(endTime);
                    betweenDays = ((endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }


            mTvPlanStart.setText("计划开始:" + mTaskDetailBean.getStart_date());
            mTvPlanEnd.setText("计划结束:" + mTaskDetailBean.getEnd_date());
            mTvPlanDay.setText("计划工日:" + "" + betweenDays);


            String esTime = mTaskDetailBean.getStart_date_real().equals("1970-01-01") ? "暂无" : mTaskDetailBean.getStart_date_real();
            String eeTime = mTaskDetailBean.getEnd_date_real().equals("1970-01-01") ? "暂无" : mTaskDetailBean.getEnd_date_real();


            long edays = 0;
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date startDate = format.parse(mTaskDetailBean.getStart_date_real());
                Date endDate = format.parse(mTaskDetailBean.getStart_date_real());
                edays = ((endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24));
            } catch (ParseException e) {
                e.printStackTrace();
            }


            mTvExecuteStart.setText("执行开始::" + esTime);
            mTvExecuteEnd.setText("执行结束:" + eeTime);
            mTvExecuteDay.setText("执行工日:" + edays);


            mTvLevel.setText("" + mTaskDetailBean.getLevel());


            mTvTeamName.setText("" + mTaskDetailBean.getTeam_name());
            mTvTeamCount.setText("(" + mTaskDetailBean.getTeam_num() + ")");
            mTvTeamProgress.setText("");

            mTrendsBeans.clear();
            mTrendsBeans.addAll(mTaskDetailBean.getTrends());
            mTrendsAdapter.notifyDataSetChanged();
        }


    }
    private int getStateColor(String status){

        int color = ResourceUtil.getColor("#FFF4A304");
        if (status.equals("未开始")){
            color = ResourceUtil.getColor("#FFF4A304");
        }else  if (status.equals("进行中")){
            color = ResourceUtil.getColor("#FF3291F8");
        }else  if (status.equals("作业中")){
            color = ResourceUtil.getColor("#FFB90BB9");
        }else  if (status.equals("进行中")){
            color = ResourceUtil.getColor("#FFF4A304");
        }else  if (status.equals("已逾期")||status.equals("已超期")){
            color = ResourceUtil.getColor("#FFF90C0C");
        }else  if (status.equals("已完成")){
            color = ResourceUtil.getColor("#FF6F6D6D");
        }
        return  color;
    }

    @OnClick({R.id.ll_header_back, R.id.ll_header_right, R.id.iv_team_scene, R.id.tv_scene, R.id.tv_discuss,
            R.id.tv_plan_edit, R.id.tv_assign, R.id.tv_action, R.id.iv_address})
    public void onViewClicked(View view) {
        Bundle bundle;
        switch (view.getId()) {
            case R.id.ll_header_back:
                finish();
                break;
            case R.id.iv_address:
                showMapApp(mIvAddress);
                break;
            case R.id.ll_header_right:
                showMenuDialog();
                break;
            case R.id.iv_team_scene:
            case R.id.tv_scene:
                bundle = new Bundle();
                bundle.putString("task_id", task_id + "");
                bundle.putString("project_id", "" + project_id);
                if (mTaskDetailBean != null) {
                    bundle.putString("task_name", "" + mTaskDetailBean.getName());
                } else {
                    bundle.putString("task_name", "");
                }
                bundle.putString("team_id",mTaskDetailBean.getTeam_id() + "");
                gotoActivity(TaskDetailActivity.class, bundle, 0);
                break;
            case R.id.tv_discuss:
                bundle = new Bundle();
                bundle.putString("task_id", "" + task_id);
                gotoActivity(ProjectTaskDiscussActivity.class, bundle, 0);
                break;
            case R.id.tv_plan_edit:
                showEditTimeDialog();
                break;
            case R.id.tv_assign:
                if (mTaskDetailBean.getTeam_id() == 0 &&TextUtils.isEmpty(mTaskDetailBean.getTeam_name())) {
                    showAssignTaskDialog();
                }else {
                    bundle = new Bundle();
                    bundle.putString("bean", JSON.toJSONString(mTaskDetailBean));
                    gotoActivity(ProjectTaskUpdateTeamActivity.class, bundle, 0);
                }
                break;
            case R.id.tv_action:
                showOperateDialog();
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
        if (eventTag == ApiConstants.EventTags.task_get) {
            iCommonRequestPresenter.requestPost(eventTag, this, ApiConstants.task_get, map);
        } else if (eventTag == ApiConstants.EventTags.task_del) {
            iCommonRequestPresenter.requestPost(eventTag, this, ApiConstants.task_del, map);
        } else if (eventTag == ApiConstants.EventTags.project_team) {
            map.put("project_id", "" + project_id);
            iCommonRequestPresenter.requestPost(eventTag, this, ApiConstants.project_team, map);
        } else if (eventTag == ApiConstants.EventTags.task_assign) {
            map.put("start_date", "" + assignStartTime);
            map.put("end_date", "" + assignEndTime);
            map.put("level", "" + assignLevel);
            map.put("team_id", "" + assignTeamId);
//            map.put("address", "" + assignAddress);
//            map.put("longitue", "" + assignLongitude);
//            map.put("latitude", "" + assignLatitude);
//            map.put("scope", "" + assignRange);
            iCommonRequestPresenter.requestPost(eventTag, this, ApiConstants.task_assign, map);
        } else if (eventTag == ApiConstants.EventTags.task_update) {
            if (operate) {
                map.put("status", "" + operateType);
            } else {
                map.put("start_date", "" + updateStartTime);
                map.put("end_date", "" + updateEndTime);
                map.put("level", "" + updateLevel);
            }
            operate = false;
            iCommonRequestPresenter.requestPost(eventTag, this, ApiConstants.task_update, map);
        }
    }

    @Override
    public void getRequestData(int eventTag, String result) {
        super.getRequestData(eventTag, result);
        dismissPopupLoading();
        ResultBean bean = JSON.parseObject(result, ResultBean.class);
        if (bean != null && bean.getCode() == 0) {
            if (eventTag == ApiConstants.EventTags.task_get) {
                mTaskDetailBean = JSON.parseObject(bean.getData(), ProjectTaskDetailBean.class);
                setViewData();
            } else if (eventTag == ApiConstants.EventTags.project_team) {
                List<ProjectTeamListBean> beanList = JSON.parseArray(bean.getData(), ProjectTeamListBean.class);
                mTeamListBeans.clear();
                mTeamListBeans.addAll(beanList);
            } else if (eventTag == ApiConstants.EventTags.task_assign) {
                ToastUtil.showToast(bean != null ? bean.getMsg() : "操作成功");
                toRequest(ApiConstants.EventTags.task_get);
            } else if (eventTag == ApiConstants.EventTags.task_update) {
                toRequest(ApiConstants.EventTags.task_get);
            } else if (eventTag == ApiConstants.EventTags.task_del) {
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

    private TextView mTvAssignDays;
    private TextView mTvAssignStartTime;
    private TextView mTvAssignEndTime;

    private String assignStartTime;
    private String assignEndTime;
    private String assignLevel;
    private String assignTeamId;

    public void showAssignTaskDialog() {
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.setContentView(R.layout.dialog_project_assign_task);
        dialog.getWindow().findViewById(R.id.design_bottom_sheet).setBackgroundResource(android.R.color.transparent);
        dialog.show();

        TextView title = dialog.findViewById(R.id.tv_title);
        mTvAssignDays = dialog.findViewById(R.id.tv_day);
        mTvAssignStartTime = dialog.findViewById(R.id.tv_start_time);
        mTvAssignEndTime = dialog.findViewById(R.id.tv_end_time);
        Spinner mSpLevel = dialog.findViewById(R.id.spinner_level);
        Spinner mSpTeam = dialog.findViewById(R.id.spinner_team);

        SpinnerTeamAdapter adapter = new SpinnerTeamAdapter(this, R.layout.item_task_spinner_team, mTeamListBeans);
        mSpTeam.setAdapter(adapter);


        title.setText("" + mTaskDetailBean.getName());
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
                if (!TextUtils.isEmpty(assignStartTime)
                        && !TextUtils.isEmpty(assignStartTime)
                        && !TextUtils.isEmpty(assignStartTime)) {

                    toRequest(ApiConstants.EventTags.task_assign);
                    dialog.dismiss();
                }
            }
        });
    }


    private TextView mTvUpdateStartTime;
    private TextView mTvUpdateEndTime;
    private TextView mTvUpdateDays;
    private Spinner mUpdateSpinnerLevel;
    private String updateStartTime;
    private String updateEndTime;
    private String updateLevel;

    public void showEditTimeDialog() {
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.setContentView(R.layout.dialog_project_edit_task);
        dialog.getWindow().findViewById(R.id.design_bottom_sheet).setBackgroundResource(android.R.color.transparent);
        dialog.show();

        mTvUpdateStartTime = dialog.findViewById(R.id.tv_start_time);
        mTvUpdateEndTime = dialog.findViewById(R.id.tv_end_time);
        mTvUpdateDays = dialog.findViewById(R.id.tv_day);
        mUpdateSpinnerLevel = dialog.findViewById(R.id.spinner_level);


        mTvUpdateStartTime.setText(mTaskDetailBean.getStart_date());
        mTvUpdateEndTime.setText(mTaskDetailBean.getEnd_date());


        String startTime = mTaskDetailBean.getStart_date();
        String endTime = mTaskDetailBean.getEnd_date();
        long betweenDays = 0;
        if (!TextUtils.isEmpty(startTime) && !TextUtils.isEmpty(endTime)) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = null;
            try {
                startDate = format.parse(startTime);
                Date endDate = format.parse(endTime);
                betweenDays = ((endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24));
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        }
        mTvUpdateDays.setText("" + betweenDays);

        dialog.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.tv_start_time).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker(3);
            }
        });
        dialog.findViewById(R.id.tv_end_time).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker(4);
            }
        });

        dialog.findViewById(R.id.tv_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(updateStartTime)) {
                    ToastUtil.showToast("请选择开始时间");
                    return;
                }
                if (TextUtils.isEmpty(updateEndTime)) {
                    ToastUtil.showToast("请选择结束时间");
                    return;
                }
                updateLevel = (String) mUpdateSpinnerLevel.getSelectedItem();
                toRequest(ApiConstants.EventTags.task_update);
                dialog.dismiss();
            }
        });

    }

    public void showMenuDialog() {
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.setContentView(R.layout.dialog_project_task_right_menu);
        dialog.getWindow().findViewById(R.id.design_bottom_sheet).setBackgroundResource(android.R.color.transparent);
        dialog.show();

        TextView title = dialog.findViewById(R.id.tv_title);
        title.setText("" + mTaskDetailBean.getName());

        dialog.findViewById(R.id.tv_copy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("mTaskDetailBean", "" + JSON.toJSONString(mTaskDetailBean));
                bundle.putBoolean("edit", false);
                gotoActivity(ProjectTaskEditActivity.class, bundle, 0);
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.tv_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("mTaskDetailBean", "" + JSON.toJSONString(mTaskDetailBean));
                bundle.putBoolean("edit", true);
                gotoActivity(ProjectTaskEditActivity.class, bundle, 0);
                dialog.dismiss();
            }
        });


        dialog.findViewById(R.id.tv_del).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toRequest(ApiConstants.EventTags.task_del);
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }


    private boolean operate;
    private String operateType;

    public void showOperateDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_project_task_operate);
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        lp.width = ViewUtil.dp2px(this, 250);
        lp.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);
        dialog.show();
        dialog.findViewById(R.id.tv_complete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operate = true;
                operateType = "3";
                toRequest(ApiConstants.EventTags.task_update);
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.tv_pause).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operate = true;
                operateType = "2";
                toRequest(ApiConstants.EventTags.task_update);
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.tv_copy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("mTaskDetailBean", "" + JSON.toJSONString(mTaskDetailBean));
                bundle.putBoolean("edit", false);
                gotoActivity(ProjectTaskEditActivity.class, bundle, 0);
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.tv_normal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operate = true;
                operateType = "1";
                toRequest(ApiConstants.EventTags.task_update);
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    public void showTimePicker(int type) {
        TimePickerView pickerView = new TimePickerBuilder(this, new OnTimeSelectListener() {
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

                } else if (type == 3) {
                    mTvUpdateStartTime.setText(dateFormat.format(date));
                    updateStartTime = dateFormat.format(date);
                    String startTime = mTvUpdateStartTime.getText().toString();
                    String endTime = mTvUpdateEndTime.getText().toString();

                    if (!TextUtils.isEmpty(startTime) && !TextUtils.isEmpty(endTime)) {
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        try {
                            Date startDate = format.parse(startTime);
                            Date endDate = format.parse(endTime);
                            long betweenDays = ((endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24));
                            mTvUpdateDays.setText("" + betweenDays);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                } else if (type == 4) {
                    mTvUpdateEndTime.setText(dateFormat.format(date));
                    updateEndTime = dateFormat.format(date);
                    String startTime = mTvUpdateStartTime.getText().toString();
                    String endTime = mTvUpdateEndTime.getText().toString();
                    if (!TextUtils.isEmpty(startTime) && !TextUtils.isEmpty(endTime)) {
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        try {
                            Date startDate = format.parse(startTime);
                            Date endDate = format.parse(endTime);
                            Logcat.d("" + startTime + "/" + endTime);
                            long betweenDays = ((endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24));
                            mTvUpdateDays.setText("" + betweenDays);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        })
                .setLineSpacingMultiplier(2)
                .isDialog(true)
                .setDecorView(getRootView())
                .build();
        pickerView.show();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            toRequest(ApiConstants.EventTags.task_get);
        }
        if (data == null) {
            return;
        }
        if (requestCode == 2) {
            Bundle bundle = data.getExtras();
            String  assignLongitude = bundle.getString("longitude");
            String  assignLatitude = bundle.getString("latitude");
            String assignAddress = bundle.getString("address");
            String assignRange = bundle.getString("range");

        }
    }

    public void showMapApp(View view) {
        final List<PopMenuBean> menuItems = new ArrayList<>();

        if (LbsUtil.isAmapMapInstalled()) {
            menuItems.add(new PopMenuBean(R.mipmap.ic_a_map, "高德地图"));
        }
        if (LbsUtil.isBaiduMapInstalled()) {
            menuItems.add(new PopMenuBean(R.mipmap.ic_baidu_map, "百度地图"));
        }
        if (LbsUtil.isTencentInstalled()) {
            menuItems.add(new PopMenuBean(R.mipmap.ic_qq_map, "腾讯地图"));
        }
        if (menuItems.size() == 0) {
            menuItems.add(new PopMenuBean(R.mipmap.ic_baidu_map, "百度地图（网页版）"));
        }

        PopContextMenu contextMenu = new PopContextMenu(this)
                .addMenuList(menuItems)
                .setWidth(0)
                .dimBackground(false)
                .setOnItemSelectListener(new PopContextMenu.OnItemSelectListener() {
                    @Override
                    public void onItemSelect(int position) {
                        Logcat.i("点击了菜单：" + menuItems.get(position));
                        mapMenuClicked(menuItems.get(position));
                    }
                });
        contextMenu.showMenu(view, +150, 0);
    }

    private void mapMenuClicked(PopMenuBean yogaMenuBean) {
        Logcat.i("latitude:" + mTaskDetailBean.getLatitude() + " longitude :" + mTaskDetailBean.getLongitue());
        switch (yogaMenuBean.getText()) {
            case "高德地图":
                LbsUtil.openAmap(this, mTaskDetailBean.getLatitude(), mTaskDetailBean.getLongitue(), mTaskDetailBean.getAddress());
                break;
            case "百度地图":
                LbsUtil.openBaiduMap(this, mTaskDetailBean.getLatitude(), mTaskDetailBean.getLongitue(), mTaskDetailBean.getAddress());
                break;
            case "腾讯地图":
                LbsUtil.openQQMap(this, mTaskDetailBean.getLatitude(), mTaskDetailBean.getLongitue(), mTaskDetailBean.getAddress());
                break;
            case "百度地图（网页版）":
                LbsUtil.openBaiduWebMap(this, mTaskDetailBean.getLatitude(), mTaskDetailBean.getLongitue(), mTaskDetailBean.getAddress());
                break;
        }
    }

}
