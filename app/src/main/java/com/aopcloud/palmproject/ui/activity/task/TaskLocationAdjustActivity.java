package com.aopcloud.palmproject.ui.activity.task;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MyLocationStyle;
import com.aopcloud.base.annotation.Layout;
import com.aopcloud.base.util.ResourceUtil;
import com.aopcloud.base.util.ToastUtil;
import com.aopcloud.palmproject.Conf;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.api.ApiConstants;
import com.aopcloud.palmproject.common.ResultBean;
import com.aopcloud.palmproject.ui.activity.BaseAc;
import com.aopcloud.palmproject.ui.activity.map.MapLocationConfirmActivity;
import com.aopcloud.palmproject.ui.activity.project.bean.ProjectTaskDetailBean;
import com.aopcloud.palmproject.ui.adapter.file.FileListAdapter;
import com.aopcloud.palmproject.utils.JsonUtil;
import com.aopcloud.palmproject.utils.LoginUserUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.guoxiaoxing.phoenix.core.PhoenixOption;
import com.guoxiaoxing.phoenix.core.model.MediaEntity;
import com.guoxiaoxing.phoenix.core.model.MimeType;
import com.guoxiaoxing.phoenix.picker.Phoenix;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * @PackageName : com.aopcloud.palmproject.ui.activity.task
 * @File : TaskLocationAdjustActivity.java
 * @Date : 2020/5/25 15:03
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @VersionCode : V 1.0
 * @Describe ：
 */
@Layout(R.layout.activity_task_location_adjust)
public class TaskLocationAdjustActivity extends BaseAc implements
        LocationSource
        , AMapLocationListener
        , FileListAdapter.OnItemChildClickListener
        , FileListAdapter.OnItemClickListener {
    @BindView(R.id.map_view)
    MapView mMapView;
    @BindView(R.id.tv_address)
    TextView mTvAddress;
    @BindView(R.id.iv_adjust_location)
    ImageView mIvAdjustLocation;
    @BindView(R.id.tv_adjust_location)
    TextView mTvAdjustLocation;
    @BindView(R.id.tv_description)
    TextView mTvDescription;
    @BindView(R.id.et_msg)
    EditText mEtMsg;
    @BindView(R.id.rv_list)
    RecyclerView mRvList;
    @BindView(R.id.ll_ui_layout)
    LinearLayout mLlUiLayout;
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
    @BindView(R.id.tv_task_name)
    TextView mTvTaskName;
    @BindView(R.id.tv_task_time)
    TextView mTvTaskTime;
    @BindView(R.id.tv_task_leader)
    TextView mTvTaskLeader;
    @BindView(R.id.tv_task_state)
    TextView mTvTaskState;
    @BindView(R.id.tv_enterprise)
    TextView mTvEnterprise;
    @BindView(R.id.tv_task_address)
    TextView mTvTaskAddress;
    @BindView(R.id.tv_progress)
    TextView mTvProgress;
    @BindView(R.id.rl_task_info)
    RelativeLayout mRlTaskInfo;
    private AMap mAMap;
    private AMapLocation mAMapLocation;
    private UiSettings mUiSettings;
    private MyLocationStyle myLocationStyle;
    private OnLocationChangedListener mListener;
    private AMapLocationClient mLocationClient;
    private AMapLocationClientOption mLocationOption;


    private String cityCode;
    private double latitude;
    private double longitude;
    private String address;


    private MediaEntity mAddMediaEntity;
    private FileListAdapter mFileListAdapter;
    private List<MediaEntity> mMediaEntities = new ArrayList<>();


    private String attach;
    private String message;


    private String task_id;
    private ProjectTaskDetailBean mTaskDetailBean;


    @Override
    protected void initData() {
        super.initData();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            task_id = bundle.getString("task_id");
            String taskBeanJson = bundle.getString("taskBean");
            mTaskDetailBean = JSON.parseObject(taskBeanJson, ProjectTaskDetailBean.class);
        }
    }

    @Override
    protected void initView() {
        mTvHeaderTitle.setText("上报现场位置较准");
        mTvHeaderRight.setText("确认");

        if (mTaskDetailBean != null) {
            mRlTaskInfo.setVisibility(View.VISIBLE);
            mTvTaskTime.setText(mTaskDetailBean.getStart_date());
            mTvTaskState.setText(mTaskDetailBean.getStatus_str());
            mTvTaskLeader.setText(mTaskDetailBean.getLeader_name());
            mTvTaskAddress.setText(mTaskDetailBean.getAddress());
            mTvProgress.setText("" + mTaskDetailBean.getProgress() + "%");
            mTvEnterprise.setText("");
        } else {
            mRlTaskInfo.setVisibility(View.GONE);
            mTvTaskTime.setText("");
            mTvTaskState.setText("");
            mTvTaskLeader.setText("");
            mTvTaskAddress.setText("");
            mTvProgress.setText("0%");
            mTvEnterprise.setText("");
        }

        initMap();
        initLocation();

        mAddMediaEntity = new MediaEntity();
        mMediaEntities.add(mAddMediaEntity);
        mFileListAdapter = new FileListAdapter(R.layout.item_default_file, mMediaEntities);
        mFileListAdapter.setEdit(true);
        mFileListAdapter.setOnItemClickListener(this);
        mFileListAdapter.setOnItemChildClickListener(this);
        mRvList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mRvList.setAdapter(mFileListAdapter);
    }

    private void initMap() {
        mAMap = mMapView.getMap();
        mUiSettings = mAMap.getUiSettings();
        mUiSettings.setZoomControlsEnabled(false);
        mUiSettings.setAllGesturesEnabled(true);
        mUiSettings.setMyLocationButtonEnabled(false);
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

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (position == mMediaEntities.size() - 1) {
            List list = new ArrayList();
            list.addAll(mMediaEntities);
            list.remove(mMediaEntities.size() - 1);
            Phoenix.with()
                    .theme(PhoenixOption.THEME_DEFAULT)// 主题
                    .fileType(MimeType.ofAll())//显示的文件类型图片、视频、图片和视频
                    .maxPickNumber(10)// 最大选择数量
                    .minPickNumber(1)// 最小选择数量
                    .spanCount(4)// 每行显示个数
                    .enablePreview(true)// 是否开启预览
                    .enableCamera(true)// 是否开启拍照
                    .enableAnimation(true)// 选择界面图片点击效果
                    .enableCompress(true)// 是否开启压缩
                    .compressPictureFilterSize(1024)//多少kb以下的图片不压缩
                    .compressVideoFilterSize(2018)//多少kb以下的视频不压缩
                    .thumbnailHeight(160)// 选择界面图片高度
                    .thumbnailWidth(160)// 选择界面图片宽度
                    .enableClickSound(false)// 是否开启点击声音
                    .pickedMediaList(list)// 已选图片数据
                    .videoFilterTime(500)//显示多少秒以内的视频
                    .mediaFilterSize(10000)//显示多少kb以下的图片/视频，默认为0，表示不限制
                    //如果是在Activity里使用就传Activity，如果是在Fragment里使用就传Fragment
                    .start(TaskLocationAdjustActivity.this, PhoenixOption.TYPE_PICK_MEDIA, 2);
        } else {

        }
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        if (view.getId() == R.id.iv_del) {
            List<MediaEntity> entities = new ArrayList<>();
            entities.addAll(mMediaEntities);
            entities.remove(position);
            mMediaEntities.clear();
            mMediaEntities.addAll(entities);
            Log.w(TAG, "-1--" + JSON.toJSONString(entities));
            Log.d(TAG, "--2-" + JSON.toJSONString(mMediaEntities));
            mFileListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        if (null != mLocationClient) {
            mLocationClient.onDestroy();
        }
    }

    @OnClick({R.id.ll_header_back, R.id.ll_header_right, R.id.iv_adjust_location, R.id.tv_adjust_location})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_header_back:
                finish();
                break;
            case R.id.ll_header_right:
                checkParams();
                break;
            case R.id.iv_adjust_location:
            case R.id.tv_adjust_location:
                gotoActivity(MapLocationConfirmActivity.class, 1);
                break;
        }
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

                mTvAddress.setText("" + amapLocation.getProvince() + amapLocation.getCity() + amapLocation.getDistrict() + amapLocation.getStreet() + amapLocation.getStreetNum());
                mTvDescription.setText("" + amapLocation.getDescription());

                LatLng latLng = new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude());//构造一个位置
                mAMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20));
                Log.i(TAG, "onLocationChanged:" + JSON.toJSONString(amapLocation));
                mLocationClient.startLocation();
            } else {
                String errText = "定位失败," + amapLocation.getErrorCode() + ": " + amapLocation.getErrorInfo();
                Log.w(TAG, "AmapErr：" + errText);
            }
        }
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
        if (mLocationClient == null) {
            //初始化定位
            mLocationClient = new AMapLocationClient(this);
            //初始化定位参数
            mLocationOption = new AMapLocationClientOption();
            //设置定位回调监听
            mLocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);

            mLocationOption.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.SignIn);


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

    private void checkParams() {
        List<MediaEntity> list = new ArrayList();
        list.addAll(mMediaEntities);
        if (list.contains(mAddMediaEntity)) {
            list.remove(mAddMediaEntity);
        }
        uploadFile(list);
    }

    @Override
    public void toRequest(int eventTag) {
        super.toRequest(eventTag);
        Map map = new HashMap();
        map.put("token", "" + LoginUserUtil.getToken(this));
        map.put("code", "" + LoginUserUtil.getCurrentEnterpriseNo(this));
        map.put("task_id", "" + task_id);//项目名称
        if (eventTag == ApiConstants.EventTags.trajectory_add) {
            map.put("longitue", "" + longitude);
            map.put("longitude", "" + longitude);
            map.put("longitude", "" + longitude);
            map.put("latitude", "" + latitude);
//            map.put("longitude", "" + longitude);
            Log.i(TAG, "------------" + eventTag + "/" + JSON.toJSONString(map));
            iCommonRequestPresenter.requestPost(eventTag, this, ApiConstants.trajectory_add, map);
        }
    }

    @Override
    public void getRequestData(int eventTag, String result) {
        super.getRequestData(eventTag, result);
        Log.i(TAG, "------------" + eventTag + "/" + result);
        ResultBean bean = JSON.parseObject(result, ResultBean.class);
        if (bean != null && bean.getCode() == 0) {
            if (eventTag == ApiConstants.EventTags.trajectory_add) {
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
        Log.i(TAG, "------------" + eventTag + "/" + msg);
        ToastUtil.showToast("网络错误");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            List<MediaEntity> result = Phoenix.result(data);
            Log.i(TAG, "------------" + JSON.toJSONString(result));
            mMediaEntities.clear();
            if (result != null) {
                mMediaEntities.addAll(result);
            }
            mMediaEntities.add(mAddMediaEntity);
            mFileListAdapter.notifyDataSetChanged();
        } else if (requestCode == 1) {
            if (resultCode != RESULT_OK){
                return;
            }
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                longitude = bundle.getDouble("longitude");
                latitude = bundle.getDouble("latitude");
                Log.i(TAG, "------------" + latitude + "/" + longitude);
                LatLng latLng = new LatLng(latitude, longitude);//构造一个位置
                mAMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, Conf.MAP_ZOOM_LEVEL));
                if (mLocationClient != null) {
                    mLocationClient.stopLocation();
                }
            }
        }
    }

    private void uploadFile(List<MediaEntity> result) {
        if (result.size() > 0) {
            MediaEntity entity = result.get(result.size() - 1);
            if (!TextUtils.isEmpty(entity.getMediaName()) && entity.getMediaName().equals("img_add_network")) {
                if (TextUtils.isEmpty(attach)) {
                    attach = entity.getLocalPath();
                } else {
                    attach = attach + "," + entity.getLocalPath();
                }
                result.remove(entity);
                uploadFile(result);
            } else {
                Log.i(TAG, "------------" + getPictureSuffix(entity.getLocalPath()));
                OkHttpUtils.post().url(ApiConstants.file_upload)
                        .addParams("token", "" + LoginUserUtil.getToken(this))
                        .addFile("file", getPictureSuffix(entity.getLocalPath()), new File(entity.getLocalPath()))
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                dismissPopupLoading();
                                Log.w(TAG, "add live video exception :" + e + "/");
                                ToastUtil.showToast("文件上传失败，请重试");
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                dismissPopupLoading();
                                Log.i(TAG, "add Serices Course  response :" + response);
                                ResultBean bean = JSON.parseObject(response, ResultBean.class);
                                if (bean != null && bean.getCode() == 0) {
                                    if (TextUtils.isEmpty(attach)) {
                                        attach = JsonUtil.parserField(bean.getData(), "path");
                                    } else {
                                        attach = attach + "," + JsonUtil.parserField(bean.getData(), "path");
                                    }
                                    result.remove(result.size() - 1);
                                    uploadFile(result);
                                } else {
                                    ToastUtil.showToast(bean != null ? bean.getMsg() : "上传失败，请重试");
                                }
                            }
                        });
            }
        } else {
            Log.i(TAG, "-------" + attach);
            toRequest(ApiConstants.EventTags.trajectory_add);
        }
    }

    private String getPictureSuffix(String path) {
        if (path.contains(".")) {
            return "live_" + System.currentTimeMillis() + path.substring(path.lastIndexOf("."), path.length());
        }
        return "live_" + System.currentTimeMillis() + ".jpg";
    }
}