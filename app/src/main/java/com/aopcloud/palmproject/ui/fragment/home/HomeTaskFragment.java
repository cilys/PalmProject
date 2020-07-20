package com.aopcloud.palmproject.ui.fragment.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
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
import com.aopcloud.base.common.BaseEvent;
import com.aopcloud.base.util.ResourceUtil;
import com.aopcloud.base.util.ToastUtil;
import com.aopcloud.palmproject.Conf;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.api.ApiConstants;
import com.aopcloud.palmproject.bean.WeatherBean;
import com.aopcloud.palmproject.common.MassageEvent;
import com.aopcloud.palmproject.common.ResultBean;
import com.aopcloud.palmproject.ui.activity.project.bean.ProjectTaskBean;
import com.aopcloud.palmproject.ui.activity.task.TaskDetailActivity;
import com.aopcloud.palmproject.ui.activity.task.TaskUpdateProgressActivity;
import com.aopcloud.palmproject.ui.adapter.home.HomeTaskAdapter;
import com.aopcloud.palmproject.utils.LoginUserUtil;
import com.aopcloud.palmproject.view.decoration.DividerItemDecoration;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cily.utils.base.StrUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @PackageName : com.aopcloud.palmproject.ui.fragment.home
 * @File : HomeProjectFragment.java
 * @Date : 2020/5/23 2020/5/23
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class HomeTaskFragment extends BaseFragment implements LocationSource
        , AMapLocationListener
        , AMap.OnMarkerClickListener {

    @BindView(R.id.map_view)
    MapView mMapView;
    @BindView(R.id.tv_filter)
    TextView mTvFilter;
    @BindView(R.id.tv_count)
    TextView mTvCount;
    @BindView(R.id.iv_refresh)
    ImageView mIvRefresh;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.ll_all)
    LinearLayout mLLAll;
    @BindView(R.id.spinner_type)
    Spinner mSpinnerType;
    @BindView(R.id.checkbox_state_all)
    CheckBox mCheckboxStateAll; //任务进行状态：全部
    @BindView(R.id.checkbox_state_progress)
    CheckBox mCheckboxStateProgress;    //任务进行状态：进行中
    @BindView(R.id.checkbox_state_expect)
    CheckBox mCheckboxStateExpect;      //任务进行状态：已超期
    @BindView(R.id.checkbox_state_complete)
    CheckBox mCheckboxStateComplete;    //任务进行状态：已完成
    @BindView(R.id.checkbox_state_no_start)
    CheckBox mCheckboxStateNoStart;     //任务进行状态：未开始
    @BindView(R.id.checkbox_state_cancel)
    CheckBox mCheckboxStateCancel;      //任务进行状态：已取消
    @BindView(R.id.checkbox_state_pause)
    CheckBox mCheckboxStatePause;       //任务进行状态：暂停中
    @BindView(R.id.checkbox_state_operation)
    CheckBox mCheckboxStateOperation;   //任务进行状态：作业中
    @BindView(R.id.checkbox_level_all)
    CheckBox mCheckboxLevelAll;         //优先级：全部
    @BindView(R.id.checkbox_level_ordinary)
    CheckBox mCheckboxLevelOrdinary;    //优先级：普通
    @BindView(R.id.checkbox_level_important)
    CheckBox mCheckboxLevelImportant;   //优先级：重要
    @BindView(R.id.checkbox_level_urgent)
    CheckBox mCheckboxLevelUrgent;      //优先级：紧急
    @BindView(R.id.spinner_company)
    Spinner mSpinnerCompany;
    @BindView(R.id.tv_reset)
    TextView mTvReset;
    @BindView(R.id.tv_sure)
    TextView mTvSure;
    Unbinder unbinder;


    private String cityCode;
    private double latitude;
    private double longitude;
    private String address;

    private AMap mAMap;
    private AMapLocation mAMapLocation;
    private UiSettings mUiSettings;
    private MyLocationStyle myLocationStyle;
    private OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;

    private List<ProjectTaskBean> mTaskBeans = new ArrayList<>();
    private List<ProjectTaskBean> mAllList = new ArrayList();


    private String task_type = "0";

    public final static String STATE_all = "全部";
    public final static String STATE_progress = "进行中";
    public final static String STATE_expect = "已超期";
    public final static String STATE_complete = "已完成";
    public final static String STATE_no_start = "未开始";
    public final static String STATE_cancel = "已取消";
    public final static String STATE_pause = "暂停中";
    public final static String STATE_operation = "作业中";

    /**
     * 未完成，统计数量用，包括：未开始、进行中、作业中、已暂停
     */
    public final static String STATE_UNDO = "STATE_UNDO";
    /**
     * 已完成，统计数量用，包括：如期完成，逾期完成
     */
    public final static String STATE_DONE = "STATE_DONE";
    public final static String STATE_DONE_IN_TIME = "如期完成";
    public final static String STATE_DONE_OUT_OF_TIME = "逾期完成";
    /**
     * 逾期，统计数量用，包括：未开始、进行中、作业中、已暂停、已完成
     */
    public final static String STATE_OUT_OF_TIME = "STATE_OUT_OF_TIME";
    public final static String STATE_ALL = "STATE_ALL";
    /**
     * 进行中，包括进行中、作业中
     */
    public final static String STATE_DOING = "STATE_DOING";
    public final static String STATE_DOING_IN = "进行中";
    public final static String STATE_DOING_ZUOYE = "作业中";

    public final static String LEVEL_all = "全部";
    public final static String LEVEL_ordinary = "普通";
    public final static String LEVEL_important = "重要";
    public final static String LEVEL_urgent = "紧急";

    private String state = STATE_all;
    private String level = LEVEL_all;

    @Override
    protected boolean isImmersionBarEnabled() {
        return false;
    }

    public static HomeTaskFragment getInstance() {
        HomeTaskFragment fragment = new HomeTaskFragment();
        return fragment;
    }

    @Override
    protected void initData() {
        super.initData();
        toRequest(ApiConstants.EventTags.task_all);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_home_task;
    }

    @Override
    protected void onVisible() {
        super.onVisible();
        toRequest(ApiConstants.EventTags.task_all);
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
//        mUiSettings.setScrollGesturesEnabled(false);
        mUiSettings.setRotateGesturesEnabled(false);
        mUiSettings.setTiltGesturesEnabled(false);
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
        myLocationStyle.strokeWidth(5);
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.dingwei);
        myLocationStyle.myLocationIcon(bitmapDescriptor);
        mAMap.setMyLocationStyle(myLocationStyle);
        mAMap.setMyLocationEnabled(true);
    }

    private void setViewData(List<ProjectTaskBean> beanList) {
        mAllList.clear();

        mAllList.addAll(beanList);

//        setFilter("全部");
        setFilter(null);

    }

    private void setFilter(String type) {
//        if (type.equals("全部")) {
//            mTaskBeans.addAll(mAllList);
//        } else {
//            for (int i = 0; i < mAllList.size(); i++) {
//                if (type.equals(mAllList.get(i).getStatus_str())) {
//                    mTaskBeans.add(mAllList.get(i));
//                }
//            }
//        }
        mTaskBeans.clear();
        if (mAllList == null || mAllList.size() < 1) {
            mTvCount.setText("0");
            return;
        }

        List<ProjectTaskBean> ls = new ArrayList<>();
        for (ProjectTaskBean b : mAllList) {
            String state_str = b.getStatus_str();
            String level_str = b.getLevel();

            if (state_str == null || state_str.equals("")) {
                state_str = STATE_all;
            }
            if (STATE_all.equals(state)) {
                state_str = state;
            }

            if (level_str == null || level_str.equals("")) {
                level_str = LEVEL_all;
            }
            if (LEVEL_all.equals(level)) {
                level_str = level;
            }

            if (state_str.equals(state) && level_str.equals(level)) {
                ls.add(b);
            }
        }
        mTaskBeans.addAll(ls);

        mTvCount.setText("" + ls.size());

        for (int i = 0; i < mAllList.size(); i++) {
            if (mAllList.get(i).getStatus_str().equals("未开始")) {
                addMarker(mAllList.get(i), R.mipmap.icon_task_no_started);
            } else if (mAllList.get(i).getStatus_str().equals("进行中")) {
                addMarker(mAllList.get(i), R.mipmap.icon_task_progress);
            } else if (mAllList.get(i).getStatus_str().equals("作业中")) {
                addMarker(mAllList.get(i), R.mipmap.icon_task_progress);
            } else if (mAllList.get(i).getStatus_str().equals("进行中")) {
                addMarker(mAllList.get(i), R.mipmap.icon_task_progress);
            } else if (mAllList.get(i).getStatus_str().equals("已逾期")) {
                addMarker(mAllList.get(i), R.mipmap.icon_task_incomplete);
            } else if (mAllList.get(i).getStatus_str().equals("已完成")) {
                addMarker(mAllList.get(i), R.mipmap.icon_task_complete);
            }
        }
    }

    public void addMarker(ProjectTaskBean bean, int resId) {
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

    @Override
    protected void setListener() {
        super.setListener();
        mCheckboxStateAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    state = STATE_all;

                    mCheckboxStateProgress.setChecked(!isChecked);
                    mCheckboxStateExpect.setChecked(!isChecked);
                    mCheckboxStateComplete.setChecked(!isChecked);
                    mCheckboxStateNoStart.setChecked(!isChecked);
                    mCheckboxStateCancel.setChecked(!isChecked);
                    mCheckboxStatePause.setChecked(!isChecked);
                    mCheckboxStateOperation.setChecked(!isChecked);
                }
            }
        });
        mCheckboxStateProgress.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    state = STATE_progress;

                    mCheckboxStateAll.setChecked(!isChecked);
                    mCheckboxStateExpect.setChecked(!isChecked);
                    mCheckboxStateComplete.setChecked(!isChecked);
                    mCheckboxStateNoStart.setChecked(!isChecked);
                    mCheckboxStateCancel.setChecked(!isChecked);
                    mCheckboxStatePause.setChecked(!isChecked);
                    mCheckboxStateOperation.setChecked(!isChecked);
                }
            }
        });
        mCheckboxStateExpect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    state = STATE_expect;

                    mCheckboxStateAll.setChecked(!isChecked);
                    mCheckboxStateProgress.setChecked(!isChecked);
                    mCheckboxStateComplete.setChecked(!isChecked);
                    mCheckboxStateNoStart.setChecked(!isChecked);
                    mCheckboxStateCancel.setChecked(!isChecked);
                    mCheckboxStatePause.setChecked(!isChecked);
                    mCheckboxStateOperation.setChecked(!isChecked);
                }
            }
        });
        mCheckboxStateComplete.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    state = STATE_complete;

                    mCheckboxStateAll.setChecked(!isChecked);
                    mCheckboxStateProgress.setChecked(!isChecked);
                    mCheckboxStateExpect.setChecked(!isChecked);
                    mCheckboxStateNoStart.setChecked(!isChecked);
                    mCheckboxStateCancel.setChecked(!isChecked);
                    mCheckboxStatePause.setChecked(!isChecked);
                    mCheckboxStateOperation.setChecked(!isChecked);
                }
            }
        });
        mCheckboxStateNoStart.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    state = STATE_no_start;

                    mCheckboxStateAll.setChecked(!isChecked);
                    mCheckboxStateProgress.setChecked(!isChecked);
                    mCheckboxStateExpect.setChecked(!isChecked);
                    mCheckboxStateComplete.setChecked(!isChecked);
                    mCheckboxStateCancel.setChecked(!isChecked);
                    mCheckboxStatePause.setChecked(!isChecked);
                    mCheckboxStateOperation.setChecked(!isChecked);
                }
            }
        });
        mCheckboxStateCancel.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    state = STATE_cancel;

                    mCheckboxStateAll.setChecked(!isChecked);
                    mCheckboxStateProgress.setChecked(!isChecked);
                    mCheckboxStateExpect.setChecked(!isChecked);
                    mCheckboxStateComplete.setChecked(!isChecked);
                    mCheckboxStateNoStart.setChecked(!isChecked);
                    mCheckboxStatePause.setChecked(!isChecked);
                    mCheckboxStateOperation.setChecked(!isChecked);
                }
            }
        });
        mCheckboxStatePause.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    state = STATE_pause;

                    mCheckboxStateAll.setChecked(!isChecked);
                    mCheckboxStateProgress.setChecked(!isChecked);
                    mCheckboxStateExpect.setChecked(!isChecked);
                    mCheckboxStateComplete.setChecked(!isChecked);
                    mCheckboxStateNoStart.setChecked(!isChecked);
                    mCheckboxStateCancel.setChecked(!isChecked);
                    mCheckboxStateOperation.setChecked(!isChecked);
                }
            }
        });
        mCheckboxStateOperation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    state = STATE_operation;

                    mCheckboxStateAll.setChecked(!isChecked);
                    mCheckboxStateProgress.setChecked(!isChecked);
                    mCheckboxStateExpect.setChecked(!isChecked);
                    mCheckboxStateComplete.setChecked(!isChecked);
                    mCheckboxStateNoStart.setChecked(!isChecked);
                    mCheckboxStateCancel.setChecked(!isChecked);
                    mCheckboxStatePause.setChecked(!isChecked);
                }
            }
        });


        mCheckboxLevelAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    level = LEVEL_all;

                    mCheckboxLevelImportant.setChecked(!isChecked);
                    mCheckboxLevelOrdinary.setChecked(!isChecked);
                    mCheckboxLevelUrgent.setChecked(!isChecked);
                }
            }
        });
        mCheckboxLevelImportant.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    level = LEVEL_important;

                    mCheckboxLevelAll.setChecked(!isChecked);
                    mCheckboxLevelOrdinary.setChecked(!isChecked);
                    mCheckboxLevelUrgent.setChecked(!isChecked);
                }
            }
        });
        mCheckboxLevelOrdinary.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    level = LEVEL_ordinary;

                    mCheckboxLevelAll.setChecked(!isChecked);
                    mCheckboxLevelImportant.setChecked(!isChecked);
                    mCheckboxLevelUrgent.setChecked(!isChecked);
                }
            }
        });
        mCheckboxLevelUrgent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    level = LEVEL_urgent;

                    mCheckboxLevelAll.setChecked(!isChecked);
                    mCheckboxLevelImportant.setChecked(!isChecked);
                    mCheckboxLevelOrdinary.setChecked(!isChecked);
                }
            }
        });
    }

    @OnClick({R.id.tv_filter, R.id.ll_all, R.id.iv_refresh, R.id.checkbox_state_all, R.id.checkbox_level_all, R.id.tv_reset, R.id.tv_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_filter:
                mDrawerLayout.openDrawer(Gravity.START);
                break;
            case R.id.ll_all:
                showTaskList();
                break;
            case R.id.iv_refresh:
                if (mAMapLocation != null) {
                    LatLng latLng = new LatLng(mAMapLocation.getLatitude(), mAMapLocation.getLongitude());//构造一个位置
                    mAMap.moveCamera(CameraUpdateFactory.changeLatLng(latLng));
                } else {
                }
                break;
            case R.id.tv_reset:
                mCheckboxStateAll.setChecked(true);
                state = STATE_all;


                mCheckboxStateProgress.setChecked(false);
                mCheckboxStateExpect.setChecked(false);
                mCheckboxStateComplete.setChecked(false);
                mCheckboxStateNoStart.setChecked(false);
                mCheckboxStateCancel.setChecked(false);
                mCheckboxStatePause.setChecked(false);
                mCheckboxStateOperation.setChecked(false);

                mCheckboxLevelAll.setChecked(true);
                level = LEVEL_all;

                mCheckboxLevelImportant.setChecked(false);
                mCheckboxLevelOrdinary.setChecked(false);
                mCheckboxLevelUrgent.setChecked(false);
                task_type = "0";
                break;
            case R.id.tv_sure:
                mDrawerLayout.closeDrawers();
                String type = (String) mSpinnerType.getSelectedItem();

                if (type.equals("我负责的任务")) {
                    task_type = "2";
                } else if (type.equals("我参与的任务")) {
                    task_type = "3";
                } else if (type.equals("我发起的任务")) {
                    task_type = "1";
                }
                toRequest(ApiConstants.EventTags.task_all);
                break;
        }
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
        if (null != mlocationClient) {
            mlocationClient.onDestroy();
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        for (int i = 0; i < mTaskBeans.size(); i++) {
            if (mTaskBeans.get(i).getTask_id() == marker.getPeriod()) {
                showTaskInfo(mTaskBeans.get(i));
            }
        }
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
                cityCode = amapLocation.getCityCode();
                longitude = amapLocation.getLongitude();
                latitude = amapLocation.getLatitude();
                address = mAMapLocation.getAddress();

                LatLng latLng = new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude());//构造一个位置
                mAMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, Conf.MAP_ZOOM_LEVEL));

                if (mlocationClient != null) {
                    mlocationClient.stopLocation();
                }
                if (!StrUtils.isEmpty(amapLocation.getCity())) {
                    if (!amapLocation.getCity().equals(WeatherBean.getCityName())){
                        WeatherBean.setCityName(amapLocation.getCity());
                    }
                }
                WeatherBean.setAddress(amapLocation.getAddress());
//
//                Logcat.i("onLocationChanged:" + JSON.toJSONString(amapLocation));
            } else {
                String errText = "定位失败," + amapLocation.getErrorCode() + ": " + amapLocation.getErrorInfo();
                Log.e("AmapErr", errText);
                if (amapLocation.getErrorCode() == 12) {
                    ToastUtil.showToast("请开启定位权限");
                }
            }
        }
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
        if (mlocationClient == null) {
            //初始化定位
            mlocationClient = new AMapLocationClient(mActivity);
            //初始化定位参数
            mLocationOption = new AMapLocationClientOption();
            //设置定位回调监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);

            mLocationOption.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.SignIn);

            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();//启动定位
        }
    }

    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }

    @Override
    public void toRequest(int eventTag) {
        super.toRequest(eventTag);
        Map map = new HashMap();
        map.put("token", "" + LoginUserUtil.getToken(mActivity));
        map.put("code", "" + LoginUserUtil.getCurrentEnterpriseNo(mActivity));
        if (eventTag == ApiConstants.EventTags.task_all) {
            map.put("type", "" + task_type);
            iCommonRequestPresenter.requestPost(eventTag, mActivity, ApiConstants.task_all, map);
        }
    }

    @Override
    public void getRequestData(int eventTag, String result) {
        super.getRequestData(eventTag, result);
        ResultBean bean = JSON.parseObject(result, ResultBean.class);
        if (bean != null && bean.getCode() == 0) {
            if (eventTag == ApiConstants.EventTags.task_all) {
                List<ProjectTaskBean> beanList = JSON.parseArray(bean.getData(), ProjectTaskBean.class);
                setViewData(beanList);
            }
        } else {
            ToastUtil.showToast(bean != null ? bean.getMsg() : "加载错误，请重试");
        }
    }

    @Override
    public void onRequestFailureException(int eventTag, String msg) {
        super.onRequestFailureException(eventTag, msg);
    }


    public void showTaskInfo(ProjectTaskBean taskBean) {
        BottomSheetDialog dialog = new BottomSheetDialog(mActivity);
        dialog.setContentView(R.layout.dialog_task_info);
        dialog.getWindow().findViewById(R.id.design_bottom_sheet)
                .setBackgroundResource(android.R.color.transparent);
        dialog.show();
        TextView mTvName = dialog.findViewById(R.id.tv_name);
        TextView mTvLeaderName = dialog.findViewById(R.id.tv_leader_name);
        TextView mTvTime = dialog.findViewById(R.id.tv_time);
        TextView mTvDays = dialog.findViewById(R.id.tv_days);
        TextView mTvCountUnit = dialog.findViewById(R.id.tv_count_unit);
        TextView mTvProgress = dialog.findViewById(R.id.tv_progress);
        TextView mTvState = dialog.findViewById(R.id.tv_state);
        TextView mTvAddress = dialog.findViewById(R.id.tv_address);
        TextView mTvDistance = dialog.findViewById(R.id.tv_distance);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date current = new Date();
        Date eDate = null;
        try {
            eDate = dateFormat.parse(taskBean.getEnd_date());
        } catch (ParseException e) {
            eDate = new Date();
        }

        long betweenDays = ((eDate.getTime() - current.getTime()) / (1000 * 60 * 60 * 24));

        String days = "";
        if (betweenDays > 0) {
            days = "剩余" + betweenDays + "天";
        } else {
            days = "逾期" + (-betweenDays) + "天";
        }

        LatLng latLng1 = new LatLng(Double.valueOf(taskBean.getLatitude()), Double.valueOf(taskBean.getLongitue()));
        LatLng latLng2 = new LatLng(latitude, longitude);
        int distance = (int) AMapUtils.calculateLineDistance(latLng1, latLng2);

        mTvName.setText("" + taskBean.getName());
        mTvLeaderName.setText("" + taskBean.getLeader_name());
        mTvTime.setText("" + taskBean.getStart_date() + "-" + taskBean.getEnd_date());
        mTvDays.setText("" + days);
        mTvCountUnit.setText("" + taskBean.getWork_value() + "/" + taskBean.getWork_unit());
        mTvProgress.setText("" + taskBean.getProgress() + "%");
        mTvState.setText("" + taskBean.getStatus_str());
        mTvAddress.setText("" + taskBean.getAddress());
        mTvDistance.setText(distance > 1000 ? "距你" + (distance / 1000) + "km" : "距你" + distance + "米");

        mTvDays.setTextColor(taskBean.getStatus_str().equals("已逾期") ? ResourceUtil.getColor("#FFF90C0C") : ResourceUtil.getColor("#FF3291F8"));
        mTvState.setTextColor(getStateColor(taskBean.getStatus_str()));
        mTvTime.setVisibility(!taskBean.getStatus_str().equals("未安排") ? View.VISIBLE : View.GONE);
        mTvDays.setVisibility(taskBean.getStatus_str().equals("已逾期") || taskBean.getStatus_str().equals("进行中") || taskBean.getStatus_str().equals("作业中") ? View.VISIBLE : View.GONE);
        mTvProgress.setVisibility(!taskBean.getStatus_str().equals("未安排") ? View.VISIBLE : View.GONE);

        dialog.findViewById(R.id.tv_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("task_id", "" + taskBean.getTask_id());
                bundle.putString("work_unit", taskBean.getWork_unit() + "");
                bundle.putString("work_value", taskBean.getWork_value() + "");
                bundle.putString("task_name", "" + taskBean.getName());
                bundle.putBoolean("child", taskBean.getPid() != 0);
                gotoActivity(TaskUpdateProgressActivity.class, bundle, 0);
            }
        });
        dialog.findViewById(R.id.tv_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("task_id", taskBean.getTask_id() + "");
                bundle.putString("project_id", taskBean.getProject_id() + "");
                bundle.putString("task_name", taskBean.getName() + "");
                bundle.putString("team_id", taskBean.getTeam_id() + "");
                bundle.putString("project_name", taskBean.getProject_name());
//                bundle.putString("project_tag", taskBean.get);
//                bundle.putString("project_type", taskBean.getProj);
                gotoActivity(TaskDetailActivity.class, bundle, 0);
            }
        });
    }


    private int getStateColor(String status) {

        int color = ResourceUtil.getColor("#FFF4A304");
        if (status.equals("未开始")) {
            color = ResourceUtil.getColor("#FFF4A304");
        } else if (status.equals("进行中")) {
            color = ResourceUtil.getColor("#FF3291F8");
        } else if (status.equals("作业中")) {
            color = ResourceUtil.getColor("#FFB90BB9");
        } else if (status.equals("进行中")) {
            color = ResourceUtil.getColor("#FFF4A304");
        } else if (status.equals("已逾期")) {
            color = ResourceUtil.getColor("#FFF90C0C");
        } else if (status.equals("已完成")) {
            color = ResourceUtil.getColor("#FF6F6D6D");
        }
        return color;
    }

    public void showTaskList() {
        BottomSheetDialog dialog = new BottomSheetDialog(mActivity);
        dialog.setContentView(R.layout.dialog_task_list);
        dialog.getWindow().findViewById(R.id.design_bottom_sheet)
                .setBackgroundResource(android.R.color.transparent);
        dialog.setCancelable(true);
        dialog.show();

        TextView textView = dialog.findViewById(R.id.tv_count);
        RecyclerView recyclerView = dialog.findViewById(R.id.rv_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        DividerItemDecoration itemDecoration = new DividerItemDecoration.Builder()
                .color(ResourceUtil.getColor(R.color.theme_background_f5))
                .height(20)
                .build();
        recyclerView.addItemDecoration(itemDecoration);
        textView.setText("共搜索到" + mTaskBeans.size() + "条任务");
        dialog.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        HomeTaskAdapter adapter = new HomeTaskAdapter(R.layout.item_home_task, mTaskBeans);
        adapter.setTask_latitude(latitude);
        adapter.setTask_longitude(longitude);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("task_id", mTaskBeans.get(position).getTask_id() + "");
                bundle.putString("project_id", mTaskBeans.get(position).getProject_id() + "");
                bundle.putString("task_name", mTaskBeans.get(position).getName() + "");
                bundle.putString("team_id", mTaskBeans.get(position).getTeam_id() + "");
                bundle.putString("project_name", mTaskBeans.get(position).getProject_name());
                gotoActivity(TaskDetailActivity.class, bundle, 0);
            }
        });
    }

    @Override
    public void onEvent(BaseEvent postResult) {
        super.onEvent(postResult);
        if (postResult.type.equals(BaseEvent.EVENT_LOGIN)) {
            toRequest(ApiConstants.EventTags.task_all);
        } else if (postResult.type.equals(BaseEvent.EVENT_LOGOUT)) {
        } else if (postResult.type.equals(MassageEvent.SWITCH_COMPANY)) {
            toRequest(ApiConstants.EventTags.task_all);
        }
    }

}

