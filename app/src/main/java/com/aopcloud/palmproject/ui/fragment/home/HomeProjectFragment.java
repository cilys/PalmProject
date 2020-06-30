package com.aopcloud.palmproject.ui.fragment.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
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
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.aopcloud.base.base.BaseFragment;
import com.aopcloud.base.common.BaseEvent;
import com.aopcloud.base.log.Logcat;
import com.aopcloud.base.util.ResourceUtil;
import com.aopcloud.base.util.ToastUtil;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.api.ApiConstants;
import com.aopcloud.palmproject.common.MassageEvent;
import com.aopcloud.palmproject.common.ResultBean;
import com.aopcloud.palmproject.ui.activity.project.ProjectDetailActivity;
import com.aopcloud.palmproject.ui.activity.project.bean.ProjectListBean;
import com.aopcloud.palmproject.ui.adapter.home.HomeProjectAdapter;
import com.aopcloud.palmproject.utils.LoginUserUtil;
import com.aopcloud.palmproject.view.decoration.DividerItemDecoration;
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
public class HomeProjectFragment extends BaseFragment implements LocationSource
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
    @BindView(R.id.spinner_type)
    Spinner mSpinnerType;
    @BindView(R.id.checkbox_state_all)
    CheckBox mCheckboxStateAll;             //状态：全部
    @BindView(R.id.checkbox_state_design)
    CheckBox mCheckboxStateDesign;          //状态：勘察设计
    @BindView(R.id.checkbox_state_ready)
    CheckBox mCheckboxStateReady;           //状态：开工预备
    @BindView(R.id.checkbox_state_build)
    CheckBox mCheckboxStateBuild;           //状态：在建
    @BindView(R.id.checkbox_state_completed)
    CheckBox mCheckboxStateCompleted;       //状态：竣工验收
    @BindView(R.id.checkbox_state_finish)
    CheckBox mCheckboxStateFinish;          //状态：完结维保
    @BindView(R.id.checkbox_state_termination)
    CheckBox mCheckboxStateTermination;     //状态：已终止
    @BindView(R.id.checkbox_state_stop)
    CheckBox mCheckboxStateStop;            //状态：已停工
    @BindView(R.id.checkbox_follow_all)
    CheckBox mCheckboxFollowAll;            //关注：全部
    @BindView(R.id.checkbox_follow_s)
    CheckBox mCheckboxFollowS;              //关注：已关注
    @BindView(R.id.checkbox_follow_n)
    CheckBox mCheckboxFollowN;              //关注：未关注
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
    private List<ProjectListBean> mProjectListBeans = new ArrayList<>();

    @Override
    protected boolean isImmersionBarEnabled() {
        return false;
    }

    public static HomeProjectFragment getInstance() {
        HomeProjectFragment fragment = new HomeProjectFragment();
        return fragment;
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_home_project;
    }

    @Override
    protected void initData() {
        super.initData();
        toRequest(ApiConstants.EventTags.project_all);
    }

    @Override
    protected void initView(View view) {
        super.initView(view);

        initMap();
        initLocation();
    }

    @Override
    protected void onVisible() {
        super.onVisible();
        toRequest(ApiConstants.EventTags.project_all);
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


    private void setViewData(List<ProjectListBean> beanList) {
        mAMap.clear();
        mProjectListBeans.clear();
        mProjectListBeans.addAll(beanList);

        mTvCount.setText("" + beanList.size());

        for (int i = 0; i < beanList.size(); i++) {
            //icon_task_progress  icon_project_home
            addMarker(beanList.get(i), R.mipmap.icon_task_progress);
        }
    }

    public void addMarker(ProjectListBean bean, int resId) {
        LatLng latLng = new LatLng(Double.valueOf(bean.getLatitude()), Double.valueOf(bean.getLongitue()));
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.icon(BitmapDescriptorFactory.fromResource(resId));
        markerOptions.title(bean.getName());
        markerOptions.period(bean.getProject_id());
        mAMap.addMarker(markerOptions);
    }

    @Override
    protected void setListener() {
        super.setListener();
        mCheckboxStateAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mCheckboxStateDesign.setChecked(!isChecked);
                    mCheckboxStateReady.setChecked(!isChecked);
                    mCheckboxStateBuild.setChecked(!isChecked);
                    mCheckboxStateCompleted.setChecked(!isChecked);
                    mCheckboxStateFinish.setChecked(!isChecked);
                    mCheckboxStateTermination.setChecked(!isChecked);
                    mCheckboxStateStop.setChecked(!isChecked);
                }
            }
        });
        mCheckboxStateDesign.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mCheckboxStateAll.setChecked(!isChecked);
                    mCheckboxStateReady.setChecked(!isChecked);
                    mCheckboxStateBuild.setChecked(!isChecked);
                    mCheckboxStateCompleted.setChecked(!isChecked);
                    mCheckboxStateFinish.setChecked(!isChecked);
                    mCheckboxStateTermination.setChecked(!isChecked);
                    mCheckboxStateStop.setChecked(!isChecked);
                }
            }
        });
        mCheckboxStateReady.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mCheckboxStateAll.setChecked(!isChecked);
                    mCheckboxStateDesign.setChecked(!isChecked);
                    mCheckboxStateBuild.setChecked(!isChecked);
                    mCheckboxStateCompleted.setChecked(!isChecked);
                    mCheckboxStateFinish.setChecked(!isChecked);
                    mCheckboxStateTermination.setChecked(!isChecked);
                    mCheckboxStateStop.setChecked(!isChecked);
                }
            }
        });
        mCheckboxStateBuild.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mCheckboxStateAll.setChecked(!isChecked);
                    mCheckboxStateDesign.setChecked(!isChecked);
                    mCheckboxStateReady.setChecked(!isChecked);
                    mCheckboxStateCompleted.setChecked(!isChecked);
                    mCheckboxStateFinish.setChecked(!isChecked);
                    mCheckboxStateTermination.setChecked(!isChecked);
                    mCheckboxStateStop.setChecked(!isChecked);
                }
            }
        });
        mCheckboxStateCompleted.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mCheckboxStateAll.setChecked(!isChecked);
                    mCheckboxStateDesign.setChecked(!isChecked);
                    mCheckboxStateReady.setChecked(!isChecked);
                    mCheckboxStateBuild.setChecked(!isChecked);
                    mCheckboxStateFinish.setChecked(!isChecked);
                    mCheckboxStateTermination.setChecked(!isChecked);
                    mCheckboxStateStop.setChecked(!isChecked);
                }
            }
        });
        mCheckboxStateFinish.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mCheckboxStateAll.setChecked(!isChecked);
                    mCheckboxStateDesign.setChecked(!isChecked);
                    mCheckboxStateReady.setChecked(!isChecked);
                    mCheckboxStateBuild.setChecked(!isChecked);
                    mCheckboxStateCompleted.setChecked(!isChecked);
                    mCheckboxStateTermination.setChecked(!isChecked);
                    mCheckboxStateStop.setChecked(!isChecked);
                }
            }
        });
        mCheckboxStateTermination.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mCheckboxStateAll.setChecked(!isChecked);
                    mCheckboxStateDesign.setChecked(!isChecked);
                    mCheckboxStateReady.setChecked(!isChecked);
                    mCheckboxStateBuild.setChecked(!isChecked);
                    mCheckboxStateCompleted.setChecked(!isChecked);
                    mCheckboxStateFinish.setChecked(!isChecked);
                    mCheckboxStateStop.setChecked(!isChecked);
                }
            }
        });
        mCheckboxStateStop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mCheckboxStateAll.setChecked(!isChecked);
                    mCheckboxStateDesign.setChecked(!isChecked);
                    mCheckboxStateReady.setChecked(!isChecked);
                    mCheckboxStateBuild.setChecked(!isChecked);
                    mCheckboxStateCompleted.setChecked(!isChecked);
                    mCheckboxStateFinish.setChecked(!isChecked);
                    mCheckboxStateTermination.setChecked(!isChecked);
                }
            }
        });

        mCheckboxFollowAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mCheckboxFollowN.setChecked(!isChecked);
                    mCheckboxFollowS.setChecked(!isChecked);
                }
            }
        });
        mCheckboxFollowN.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mCheckboxFollowAll.setChecked(!isChecked);
                    mCheckboxFollowS.setChecked(!isChecked);
                }
            }
        });
        mCheckboxFollowS.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mCheckboxFollowAll.setChecked(!isChecked);
                    mCheckboxFollowN.setChecked(!isChecked);
                }
            }
        });
    }

    @OnClick({R.id.tv_filter, R.id.tv_count, R.id.iv_refresh, R.id.tv_reset, R.id.tv_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_filter:
                mDrawerLayout.openDrawer(Gravity.START);
                break;
            case R.id.tv_count:
                showTaskList();
                break;
            case R.id.iv_refresh:
                if (mAMapLocation != null) {
                    LatLng latLng = new LatLng(mAMapLocation.getLatitude(), mAMapLocation.getLongitude());//构造一个位置
                    mAMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));
                } else {
                }
                break;
            case R.id.tv_reset:
                mCheckboxFollowAll.setChecked(false);
                mCheckboxStateDesign.setChecked(false);
                mCheckboxStateReady.setChecked(false);
                mCheckboxStateBuild.setChecked(false);
                mCheckboxStateCompleted.setChecked(false);
                mCheckboxStateFinish.setChecked(false);
                mCheckboxStateTermination.setChecked(false);
                mCheckboxStateStop.setChecked(false);


                mCheckboxStateAll.setChecked(false);
                mCheckboxFollowN.setChecked(false);
                mCheckboxFollowS.setChecked(false);

                break;
            case R.id.tv_sure:
                mDrawerLayout.closeDrawers();
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

        for (int i = 0; i < mProjectListBeans.size(); i++) {
            if (mProjectListBeans.get(i).getProject_id() == marker.getPeriod()) {
                showProjectInfo(mProjectListBeans.get(i));
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
                mAMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20));
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
        if (mlocationClient == null) {
            //初始化定位
            mlocationClient = new AMapLocationClient(mActivity);
            //初始化定位参数
            mLocationOption = new AMapLocationClientOption();
            //设置定位回调监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);

//            mLocationOption.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.SignIn);

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
        if (eventTag == ApiConstants.EventTags.project_all) {
            map.put("type", "" + 2);
            Logcat.i("------------" + eventTag + "/" + JSON.toJSONString(map));
            iCommonRequestPresenter.requestPost(eventTag, mActivity, ApiConstants.project_all, map);
        }
    }

    @Override
    public void getRequestData(int eventTag, String result) {
        super.getRequestData(eventTag, result);
        Logcat.i("------------" + eventTag + "/" + result);
        ResultBean bean = JSON.parseObject(result, ResultBean.class);
        if (bean != null && bean.getCode() == 0) {
            if (eventTag == ApiConstants.EventTags.project_all) {
                List<ProjectListBean> beanList = JSON.parseArray(bean.getData(), ProjectListBean.class);
                setViewData(beanList);
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


    public void showProjectInfo(ProjectListBean projectListBean) {
        BottomSheetDialog dialog = new BottomSheetDialog(mActivity);
        dialog.setContentView(R.layout.dialog_home_project_info);
        dialog.getWindow().findViewById(R.id.design_bottom_sheet)
                .setBackgroundResource(android.R.color.transparent);
        dialog.show();

        TextView mTvName = dialog.findViewById(R.id.tv_name);
        TextView mTvLeaderName = dialog.findViewById(R.id.tv_leader_name);
        TextView mTvTime = dialog.findViewById(R.id.tv_time);
        TextView mTvProgress = dialog.findViewById(R.id.tv_progress);
        TextView mTvState = dialog.findViewById(R.id.tv_state);
        TextView mTvAddress = dialog.findViewById(R.id.tv_address);
        TextView mTvDistance = dialog.findViewById(R.id.tv_distance);


        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date current = new Date();
        Date eDate = null;
        try {
            eDate = dateFormat.parse(projectListBean.getEnd_date());
        } catch (ParseException e) {
            eDate = new Date();
        }

        long betweenDays = ((eDate.getTime() - current.getTime()) / (1000 * 60 * 60 * 24));

        Logcat.i("--------" + betweenDays);
        String days = "";
        if (betweenDays > 0) {
            days = "剩余" + betweenDays + "天";
        } else {
            days = "逾期" + (-betweenDays) + "天";
        }


        LatLng latLng1 = new LatLng(Double.valueOf(projectListBean.getLatitude()), Double.valueOf(projectListBean.getLongitue()));
        LatLng latLng2 = new LatLng(latitude, longitude);
        int distance = (int) AMapUtils.calculateLineDistance(latLng1, latLng2);

        mTvName.setText("" + projectListBean.getName());
        mTvLeaderName.setText("" + projectListBean.getLeader_name());
        mTvTime.setText("" + projectListBean.getStart_date() + "-" + projectListBean.getEnd_date());
        mTvProgress.setText("" + projectListBean.getProgress() + "%");
        mTvState.setText("在建");
        mTvAddress.setText("" + projectListBean.getAddress());
        mTvDistance.setText(distance > 1000 ? "距你" + (distance / 1000) + "km" : "距你" + distance + "米");


    }

    public void showTaskList() {
        BottomSheetDialog dialog = new BottomSheetDialog(mActivity);
        dialog.setContentView(R.layout.dialog_home_project_list);
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
        textView.setText("共搜索到" + mProjectListBeans.size() + "条项目");
        dialog.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        HomeProjectAdapter adapter = new HomeProjectAdapter(R.layout.item_home_project, mProjectListBeans);
        adapter.setTask_latitude(latitude);
        adapter.setTask_longitude(longitude);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("project_id", mProjectListBeans.get(position).getProject_id() + "");
                gotoActivity(ProjectDetailActivity.class, bundle, 0);
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
            toRequest(ApiConstants.EventTags.project_all);
        }
    }
}