package com.aopcloud.palmproject.ui.fragment.task;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.aopcloud.base.base.BaseFragment;
import com.aopcloud.base.log.Logcat;
import com.aopcloud.base.util.ResourceUtil;
import com.aopcloud.base.util.ToastUtil;
import com.aopcloud.base.util.ViewUtil;
import com.aopcloud.palmproject.BuildConfig;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.api.ApiConstants;
import com.aopcloud.palmproject.common.GlideRoundTransform;
import com.aopcloud.palmproject.common.ResultBean;
import com.aopcloud.palmproject.loader.AppImageLoader;
import com.aopcloud.palmproject.ui.activity.camera.PictureOrVideoActivity;
import com.aopcloud.palmproject.ui.activity.project.bean.DashboardAttendanceBean;
import com.aopcloud.palmproject.ui.activity.project.bean.ProjectTaskBean;
import com.aopcloud.palmproject.ui.activity.project.bean.ProjectTaskDetailBean;
import com.aopcloud.palmproject.ui.activity.task.ReplaceMemberSignActivity;
import com.aopcloud.palmproject.ui.activity.task.TaskLocationAdjustActivity;
import com.aopcloud.palmproject.ui.activity.task.TaskReportLocationActivity;
import com.aopcloud.palmproject.ui.activity.task.TaskScenesActivity;
import com.aopcloud.palmproject.ui.activity.task.TaskTrendsActivity;
import com.aopcloud.palmproject.ui.activity.task.TaskUpdateProgressActivity;
import com.aopcloud.palmproject.ui.activity.task.bean.TrailBean;
import com.aopcloud.palmproject.utils.ListUtil;
import com.aopcloud.palmproject.utils.LoginUserUtil;
import com.aopcloud.palmproject.view.CircleImageView;
import com.aopcloud.palmproject.view.TipsDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
public class TaskExecuteFragment extends BaseFragment implements
        LocationSource
        , AMapLocationListener
        , AMap.OnMarkerClickListener {


    @BindView(R.id.tv_count_unit)
    TextView mTvCountUnit;
    @BindView(R.id.tv_state)
    TextView mTvState;
    @BindView(R.id.tv_progress)
    TextView mTvProgress;
    @BindView(R.id.iv_edit)
    ImageView mIvEdit;
    @BindView(R.id.tv_current_time)
    TextView mTvCurrentTime;
    @BindView(R.id.map_view)
    MapView mMapView;
    @BindView(R.id.ll_demand)
    LinearLayout mLlDemand;
    @BindView(R.id.ll_trends)
    LinearLayout mLlTrends;
    @BindView(R.id.ll_top_menu)
    LinearLayout mLlTopMenu;
    @BindView(R.id.ll_task_img)
    LinearLayout mLlTaskImg;
    @BindView(R.id.ll_calibration)
    LinearLayout mLlCalibration;
    @BindView(R.id.iv_location)
    ImageView mIvLocation;
    @BindView(R.id.tv_start_time)
    TextView mTvStartTime;
    @BindView(R.id.tv_days)
    TextView mTvDays;
    @BindView(R.id.tv_end_time)
    TextView mTvEndTime;
    @BindView(R.id.tv_skip)
    TextView mTvSkip;
    @BindView(R.id.tv_report)
    TextView mTvReport;
    @BindView(R.id.ll_report)
    LinearLayout mLlAttendance;
    @BindView(R.id.tv_sign)
    TextView mTvSign;
    @BindView(R.id.ll_finish)
    LinearLayout mLlFinish;
    @BindView(R.id.ll_pause)
    LinearLayout mLlPause;
    @BindView(R.id.ll_record)
    LinearLayout mLlRecord;
    @BindView(R.id.ll_execute_menu)
    LinearLayout mLlExecuteMenu;
    @BindView(R.id.tv_continue)
    TextView mTvContinue;
    @BindView(R.id.ll_execute)
    LinearLayout mLlExecute;
    @BindView(R.id.ll_replace)
    LinearLayout mLlReplace;
    private String task_id;
    private String project_id;


    private int scope;
    private double task_latitude;
    private double task_longitude;


    private String cityCode;
    private double latitude;
    private double longitude;
    private String address;

    private boolean pause;

    private AMap mAMap;
    private AMapLocation mAMapLocation;
    private UiSettings mUiSettings;
    private MyLocationStyle myLocationStyle;
    private OnLocationChangedListener mListener;
    private AMapLocationClient mLocationClient;
    private AMapLocationClientOption mLocationOption;
    private ProjectTaskDetailBean mTaskDetailBean;

    private List<TrailBean> mTrailBeans = new ArrayList<>();


    public static TaskExecuteFragment getInstance(String task_id, String project_id) {
        Bundle bundle = new Bundle();
        bundle.putString("task_id", task_id);
        bundle.putString("project_id", project_id);
        TaskExecuteFragment fragment = new TaskExecuteFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initData() {
        super.initData();
        Bundle bundle = getArguments();
        if (bundle != null) {
            task_id = bundle.getString("task_id");
            project_id = bundle.getString("project_id");
        }
        toRequest(ApiConstants.EventTags.task_get);
        toRequest(ApiConstants.EventTags.trajectory_all);
        toRequest(ApiConstants.EventTags.attendance_all);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_task_execute;

    }

    @Override
    protected void initView(View view) {
        super.initView(view);

        initMap();
        initLocation();
    }


    private void initMap() {
        mAMap = mMapView.getMap();
        mUiSettings = mAMap.getUiSettings();
        mUiSettings.setZoomControlsEnabled(true);
        mAMap.setOnMarkerClickListener(this);

    }

    private void initLocation() {
        mAMap.setLocationSource(this);
        mAMap.setMyLocationEnabled(true);
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);//定位一次，且将视角移动到地图中心点。
        myLocationStyle.strokeColor(ResourceUtil.getColor("#FF108CF7"));
        myLocationStyle.radiusFillColor(ResourceUtil.getColor("#30108CF7"));
        myLocationStyle.strokeWidth(2);
        mAMap.setMyLocationStyle(myLocationStyle);
    }

    private void setTaskData(ProjectTaskDetailBean detailBean) {
        mTaskDetailBean = detailBean;

        scope = mTaskDetailBean.getScope();
        task_latitude = Double.valueOf(detailBean.getLatitude());
        task_longitude = Double.valueOf(detailBean.getLongitue());

        mTvCountUnit.setText(mTaskDetailBean.getWork_value_finished() + "/" + mTaskDetailBean.getWork_value() + mTaskDetailBean.getWork_unit());
        mTvState.setText(mTaskDetailBean.getStatus_str());
        mTvProgress.setText(mTaskDetailBean.getProgress() + "%");
        mTvStartTime.setText(mTaskDetailBean.getStart_date());
        mTvEndTime.setText(mTaskDetailBean.getEnd_date());


        String startTime = mTaskDetailBean.getStart_date();
        String endTime = mTaskDetailBean.getEnd_date();
        long betweenDays = 0;
        long cDays = 0;
        if (!TextUtils.isEmpty(startTime) && !TextUtils.isEmpty(endTime)) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date startDate = format.parse(startTime);
                Date endDate = format.parse(endTime);

              long s=  System.currentTimeMillis();
                betweenDays = ((endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24));
                cDays = ((s - endDate.getTime()) / (1000 * 60 * 60 * 24));

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        mTvDays.setText(betweenDays + "天");
        if (cDays>0){
            mTvCurrentTime.setText("已逾期"+cDays+"天");
        }
        if (mTaskDetailBean.getLeader_id() == LoginUserUtil.getLoginUserBean(mActivity).getId()) {
            mIvEdit.setVisibility(View.VISIBLE);
            mLlReplace.setVisibility(View.VISIBLE);
        } else {
            mLlReplace.setVisibility(View.GONE);
            mIvEdit.setVisibility(View.GONE);
        }

        if (mTaskDetailBean.getStatus_str().equals("未开始")) {
            addMarker(mTaskDetailBean, R.mipmap.icon_task_no_started);
        } else if (mTaskDetailBean.getStatus_str().equals("进行中")) {
            addMarker(mTaskDetailBean, R.mipmap.icon_task_progress);
        } else if (mTaskDetailBean.getStatus_str().equals("作业中")) {
            addMarker(mTaskDetailBean, R.mipmap.icon_task_progress);
        } else if (mTaskDetailBean.getStatus_str().equals("进行中")) {
            addMarker(mTaskDetailBean, R.mipmap.icon_task_progress);
        } else if (mTaskDetailBean.getStatus_str().equals("已逾期")) {
            addMarker(mTaskDetailBean, R.mipmap.icon_task_incomplete);
        } else if (mTaskDetailBean.getStatus_str().equals("已完成")) {
            addMarker(mTaskDetailBean, R.mipmap.icon_task_complete);
        }


    }


    public void addMarker(ProjectTaskDetailBean bean, int resId) {
        if (TextUtils.isEmpty(bean.getLongitue())) {
            return;
        }
        LatLng latLng = new LatLng(Double.valueOf(bean.getLatitude()), Double.valueOf(bean.getLongitue()));
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.icon(BitmapDescriptorFactory.fromResource(resId));
        markerOptions.title(bean.getName());
        markerOptions.period(bean.getTask_id());
        mAMap.addMarker(markerOptions);
    }


    /**
     * @Method
     * @Description 描述一下方法的作用
     * @Date: 2020/6/14 15:00
     * @Author: k
     * @Param
     * @Return 0   0:签到，1:签退 2.出勤
     */

    private void setBottomView(int i) {
        Logcat.i("----------" + i);
        if (i == 0) {
            mLlAttendance.setVisibility(View.GONE);
            mTvSign.setVisibility(View.GONE);
            mLlExecute.setVisibility(View.VISIBLE);
        } else if (i == 1) {
            mLlAttendance.setVisibility(View.VISIBLE);
            mTvSign.setVisibility(View.GONE);
            mLlExecute.setVisibility(View.GONE);
        } else if (i == 2) {
            mLlAttendance.setVisibility(View.GONE);
            mTvSign.setVisibility(View.VISIBLE);
            mLlExecute.setVisibility(View.GONE);
        }
    }


    private void setAttendanceView(List<DashboardAttendanceBean> beanList) {

        Logcat.e("--------setAttendanceView-----------" + JSON.toJSONString(beanList));

        if (ListUtil.isEmpty(beanList)) {
            setBottomView(1);
        }
        for (int i = 0; i < beanList.size(); i++) {
            if (beanList.get(i).getUser_id() == LoginUserUtil.getLoginUserBean(mActivity).getId()) {
                setBottomView(beanList.get(i).getType());
            }


            int finalI = i;
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .override(ViewUtil.dp2px(mActivity, 30))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.HIGH)
                    .transforms(new CenterCrop(), new GlideRoundTransform(30));
            Glide.with(mActivity).asBitmap()
                    .load(BuildConfig.BASE_URL + beanList.get(i).getUser_avatar())
                    .apply(options)
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            BitmapDescriptor icon = BitmapDescriptorFactory.fromBitmap(resource);
                            addMarker(beanList.get(finalI), icon);
                        }
                    });


        }
    }

    public void addMarker(DashboardAttendanceBean bean, BitmapDescriptor icon) {
        LatLng latLng = new LatLng(Double.valueOf(bean.getLatitude()), Double.valueOf(bean.getLongitue()));
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.icon(icon);
        mAMap.addMarker(markerOptions);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mMapView.onCreate(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @OnClick({R.id.iv_edit, R.id.ll_demand, R.id.ll_trends, R.id.ll_task_img, R.id.ll_calibration, R.id.iv_location
            , R.id.tv_skip, R.id.tv_report, R.id.tv_sign, R.id.ll_finish, R.id.ll_pause, R.id.ll_record, R.id.tv_continue,
            R.id.ll_replace})
    public void onViewClicked(View view) {
        Bundle bundle;
        switch (view.getId()) {
            case R.id.iv_edit:
                bundle = new Bundle();
                bundle.putString("task_id", task_id);
                bundle.putString("work_unit", mTaskDetailBean.getWork_unit() + "");
                bundle.putString("work_value", mTaskDetailBean.getWork_value() + "");
                bundle.putString("task_name", "" + mTaskDetailBean.getName());
                gotoActivity(TaskUpdateProgressActivity.class, bundle, 0);
                break;
            case R.id.ll_demand:
                showAskDialog();
                break;
            case R.id.ll_trends:
                bundle = new Bundle();
                bundle.putString("task_id", task_id);
                gotoActivity(TaskTrendsActivity.class, bundle, 0);
                break;
            case R.id.ll_task_img:
                bundle = new Bundle();
                bundle.putString("task_id", task_id);
                gotoActivity(TaskScenesActivity.class, bundle, 0);
                break;
            case R.id.ll_calibration:
                if (mTaskDetailBean == null) {
                    return;
                }
                bundle = new Bundle();
                bundle.putString("task_id", task_id);
                bundle.putInt("scope", scope);
                bundle.putString("taskBean", JSON.toJSONString(mTaskDetailBean));
                gotoActivity(TaskLocationAdjustActivity.class, bundle, 3);
                break;
            case R.id.iv_location:
                if (mAMapLocation != null) {
                    LatLng latLng = new LatLng(mAMapLocation.getLatitude(), mAMapLocation.getLongitude());//构造一个位置
                    mAMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));
                } else {
                    mAMap.moveCamera(CameraUpdateFactory.zoomIn());
                }
                break;
            case R.id.tv_skip:
                TipsDialog.wrap(mActivity)
                        .setTitle("跳过上报出工地点")
                        .setShowCancel(true)
                        .setMsg("跳过上报出工地点后，系统便无法获取您的出勤轨迹，您将不能为企业人事考勤提供凭证。")
                        .setMsgColor(ResourceUtil.getColor("#FFE51C23"))
                        .setOnActionClickListener(new TipsDialog.onActionClickListener() {
                            @Override
                            public void onSure(Dialog dialog) {
                                mTvSkip.setVisibility(View.GONE);
                                dialog.dismiss();
                            }
                        }).show();
                break;
            case R.id.tv_report:
                if (mTaskDetailBean == null) {
                    return;
                }
                TipsDialog.wrap(mActivity).setShowCancel(true)
                        .setTitle("确认出勤")
                        .setMsg("开始出勤后将通过定位实时记录您的位置，作为人事考勤的凭证。")
                        .setMsgColor(ResourceUtil.getColor("#FFE51C23"))
                        .setOnActionClickListener(new TipsDialog.onActionClickListener() {
                            @Override
                            public void onSure(Dialog dialog) {
                                Bundle bundle = new Bundle();
                                bundle.putString("type", "2");
                                bundle.putInt("scope", scope);
                                bundle.putDouble("longitude", Double.valueOf(mTaskDetailBean.getLongitue()));
                                bundle.putDouble("latitude", Double.valueOf(mTaskDetailBean.getLatitude()));
                                bundle.putString("task_id", task_id);
                                gotoActivity(TaskReportLocationActivity.class, bundle, 0);
                                dialog.dismiss();
                            }
                        }).show();
                break;
            case R.id.tv_sign:
                if (mTaskDetailBean == null) {
                    return;
                }
                bundle = new Bundle();
                bundle.putString("type", "0");
                bundle.putInt("scope", scope);
                bundle.putDouble("longitude", Double.valueOf(mTaskDetailBean.getLongitue()));
                bundle.putDouble("latitude", Double.valueOf(mTaskDetailBean.getLatitude()));
                bundle.putString("task_id", task_id);
                gotoActivity(TaskReportLocationActivity.class, bundle, 0);
                break;
            case R.id.ll_finish:
                if (mTaskDetailBean == null) {
                    return;
                }
                TipsDialog.wrap(mActivity).setShowCancel(true)
                        .setTitle("确认结束")
                        .setMsg("确认结束之后将停止记录您的工时，并未你签退离场，确定结束吗？")
                        .setMsgColor(ResourceUtil.getColor("#FFE51C23"))
                        .setOnActionClickListener(new TipsDialog.onActionClickListener() {
                            @Override
                            public void onSure(Dialog dialog) {
                                if (0 == 1) {
                                    s();
                                } else {
                                    Bundle bundle = new Bundle();
                                    bundle.putString("type", "1");
                                    bundle.putInt("scope", scope);
                                    bundle.putDouble("longitude", Double.valueOf(mTaskDetailBean.getLongitue()));
                                    bundle.putDouble("latitude", Double.valueOf(mTaskDetailBean.getLatitude()));
                                    bundle.putString("task_id", task_id);
                                    gotoActivity(TaskReportLocationActivity.class, bundle, 0);
                                }
                                dialog.dismiss();
                            }
                        }).show();
                break;
            case R.id.ll_pause:
                TipsDialog.wrap(mActivity).setShowCancel(true)
                        .setTitle("确认暂停")
                        .setMsg("暂停后将暂停记录您的工时，以便于中途休息，确认暂停？")
                        .setMsgColor(ResourceUtil.getColor("#FFE51C23"))
                        .setOnActionClickListener(new TipsDialog.onActionClickListener() {
                            @Override
                            public void onSure(Dialog dialog) {
                                pause = true;
                                toRequest(ApiConstants.EventTags.attendance_status);
                                dialog.dismiss();
                            }
                        }).show();
                break;
            case R.id.ll_record:
                bundle = new Bundle();
                bundle.putString("task_id", task_id);
                bundle.putString("project_id", project_id);
                gotoActivity(PictureOrVideoActivity.class, bundle, 0);
                break;
            case R.id.tv_continue:
                toRequest(ApiConstants.EventTags.attendance_status);
                break;
            case R.id.ll_replace:
                bundle = new Bundle();
                bundle.putString("task_id", task_id);
                bundle.putString("project_id", project_id);
                bundle.putInt("scope", scope);
                bundle.putDouble("longitude", Double.valueOf(mTaskDetailBean.getLongitue()));
                bundle.putDouble("latitude", Double.valueOf(mTaskDetailBean.getLatitude()));
                gotoActivity(ReplaceMemberSignActivity.class, bundle, 0);
                break;
        }
    }


    public void s() {
        TipsDialog.wrap(mActivity).setShowCancel(true)
                .setMsg("现场仍有5个未离场的人员，是否为他们代签退？")
                .setMsgColor(ResourceUtil.getColor("#FFE51C23"))
                .setOnActionClickListener(new TipsDialog.onActionClickListener() {
                    @Override
                    public void onSure(Dialog dialog) {
                        Bundle bundle = new Bundle();
                        bundle.putString("type", "2");
                        bundle.putInt("scope", scope);
                        bundle.putString("task_id", task_id);
                        gotoActivity(ReplaceMemberSignActivity.class, bundle, 0);
                        dialog.dismiss();
                    }

                    @Override
                    public void onCancel(Dialog dialog) {
                        super.onCancel(dialog);
                        Bundle bundle = new Bundle();
                        bundle.putString("type", "2");
                        bundle.putInt("scope", scope);
                        bundle.putString("task_id", task_id);
                        gotoActivity(TaskReportLocationActivity.class, bundle, 0);
                        dialog.dismiss();
                    }
                }).show();
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
        } else if (eventTag == ApiConstants.EventTags.attendance_all) {
            map.put("project_id", "" + project_id);
            map.put("start_time", "" + getStartTime(Calendar.getInstance()));
            map.put("end_time", "" + getEndTime(Calendar.getInstance()));
            map.put("page", "" + 1);
            map.put("page_size", "" + 1000);
            Logcat.i("------------" + eventTag + "/" + JSON.toJSONString(map));
            iCommonRequestPresenter.requestPost(eventTag, mActivity, ApiConstants.attendance_all, map);
        } else if (eventTag == ApiConstants.EventTags.trajectory_all) {
            map.put("start_time", "" + getStartTime(Calendar.getInstance()));
            map.put("end_time", "" + getEndTime(Calendar.getInstance()));
            map.put("page", "" + 1);
            map.put("page_size", "" + 10);
            Logcat.i("------------" + eventTag + "/" + JSON.toJSONString(map));
            iCommonRequestPresenter.requestPost(eventTag, mActivity, ApiConstants.trajectory_all, map);
        } else if (eventTag == ApiConstants.EventTags.attendance_status) {
            map.put("status", pause ? "1" : "2");
            Logcat.i("------------" + eventTag + "/" + JSON.toJSONString(map));
            iCommonRequestPresenter.requestPost(eventTag, mActivity, ApiConstants.attendance_status, map);
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
                setTaskData(mTaskDetailBean);
            } else if (eventTag == ApiConstants.EventTags.attendance_all) {
                List<DashboardAttendanceBean> beanList = JSON.parseArray(bean.getData(), DashboardAttendanceBean.class);
                setAttendanceView(beanList);
            } else if (eventTag == ApiConstants.EventTags.trajectory_all) {
                mTrailBeans = JSON.parseArray(bean.getData(), TrailBean.class);
            } else if (eventTag == ApiConstants.EventTags.attendance_status) {
                if (pause) {
                    mTvContinue.setVisibility(View.VISIBLE);
                    mLlExecuteMenu.setVisibility(View.GONE);
                } else {
                    mTvContinue.setVisibility(View.GONE);
                    mLlExecuteMenu.setVisibility(View.VISIBLE);
                }
                pause = !pause;
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


    public void showAskDialog() {
        Dialog dialog = new Dialog(mActivity);
        dialog.setContentView(R.layout.dialog_project_task_ask);
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        lp.width = ViewUtil.getScreenWidth(mActivity) - ViewUtil.dp2px(mActivity, 40);
        lp.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);
        dialog.show();

        TextView mTvLeader = dialog.findViewById(R.id.tv_leader_name);
        TextView mTvTaskName = dialog.findViewById(R.id.tv_task_name);
        TextView mTvProjectName = dialog.findViewById(R.id.tv_project_name);
        TextView mTvLeaderTime = dialog.findViewById(R.id.tv_leader_time);
        EditText mEditText = dialog.findViewById(R.id.et_msg);
        CircleImageView mIvImg = dialog.findViewById(R.id.iv_img);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        Date date = new Date();
        try {
            SimpleDateFormat dateFormat1 = new SimpleDateFormat();
            date.setTime(dateFormat1.parse(mTaskDetailBean.getEnd_date()).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        AppImageLoader.loadCircleImage(mActivity, BuildConfig.BASE_URL + mTaskDetailBean.getLeader_avatar(), mIvImg);
        mTvLeader.setText("" + mTaskDetailBean.getLeader_name());
        mTvProjectName.setText("" + mTaskDetailBean.getProject_name());
        mTvTaskName.setText("" + mTaskDetailBean.getName());
        mTvLeaderTime.setText("负责" + mTaskDetailBean.getLeader_name() + "| 截止" + dateFormat.format(date));
        mEditText.setText("" + mTaskDetailBean.getWork_des());


        dialog.findViewById(R.id.tv_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }


    @Override
    public void onResume() {
        mMapView.onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
        if (null != mLocationClient) {
            mLocationClient.onDestroy();
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    /**
     * 定位成功后回调函数
     */
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (mListener != null && amapLocation != null) {
            if (amapLocation != null && amapLocation.getErrorCode() == 0) {
                mAMapLocation = amapLocation;
                mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
//                mAMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,20));
                mAMap.setMinZoomLevel(18);
                cityCode = amapLocation.getCityCode();
                longitude = amapLocation.getLongitude();
                latitude = amapLocation.getLatitude();
                address = mAMapLocation.getAddress();


//                mAMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,20));


                LatLng latLng1 = new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude());//构造一个位置
                LatLng latLng2 = new LatLng(task_latitude, task_longitude);//构造一个位置
                int distance = (int) AMapUtils.calculateLineDistance(latLng2, latLng1);
                if (distance <= scope) {
                    mTvSign.setText("立即签到");
                    mTvSign.setBackgroundColor(ResourceUtil.getColor("#FF108CF7"));
                } else {
                    mTvSign.setText("距离" + distance + "米可以签到");
                    mTvSign.setBackgroundColor(ResourceUtil.getColor("#7F108CF7"));
                }
//                Logcat.i("onLocationChanged:" + JSON.toJSONString(amapLocation));
            } else {
                String errText = "定位失败," + amapLocation.getErrorCode() + ": " + amapLocation.getErrorInfo();
                Logcat.e("AmapErr", errText);
            }
        }
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
        if (mLocationClient == null) {
            //初始化定位
            mLocationClient = new AMapLocationClient(mActivity);
            //初始化定位参数
            mLocationOption = new AMapLocationClientOption();
            //设置定位回调监听
            mLocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mLocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mLocationClient.startLocation();//启动定位
        }
    }

    @Override
    public void deactivate() {
        mListener = null;
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();
        }
        mLocationClient = null;
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


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            toRequest(ApiConstants.EventTags.task_get);
            toRequest(ApiConstants.EventTags.trajectory_all);
            toRequest(ApiConstants.EventTags.attendance_all);
        }
        if (data == null) {
            return;
        }
        if (requestCode == 3) {
            Bundle bundle = data.getExtras();
            Logcat.i("--------" + JSON.toJSONString(bundle.toString()));
        }
    }


}
