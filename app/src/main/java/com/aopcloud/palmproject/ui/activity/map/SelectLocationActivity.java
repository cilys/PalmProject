package com.aopcloud.palmproject.ui.activity.map;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.Poi;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.aopcloud.base.annotation.Layout;
import com.aopcloud.base.util.KeyboardUtil;
import com.aopcloud.base.util.ResourceUtil;
import com.aopcloud.base.util.ToastUtil;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.bean.WeatherBean;
import com.aopcloud.palmproject.ui.activity.BaseAc;
import com.aopcloud.palmproject.ui.activity.map.adapter.SelectLocationRangeAdapter;
import com.aopcloud.palmproject.ui.activity.map.adapter.SelectLocationSearchAdapter;
import com.aopcloud.palmproject.ui.activity.map.bean.LocationRangeBean;
import com.aopcloud.palmproject.view.CircleImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cily.utils.base.StrUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @PackageName : com.aopcloud.palmproject.ui.activity.map
 * @File : SelectLocationActivity.java
 * @Date : 2020/4/14 2020/4/14
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
@Layout(R.layout.activity_map_select_location)
public class SelectLocationActivity extends BaseAc implements LocationSource
        , AMapLocationListener, TextView.OnEditorActionListener
        , PoiSearch.OnPoiSearchListener, AMap.OnPOIClickListener, AMap.OnMarkerClickListener {
    @BindView(R.id.tv_cancel)
    TextView mTvCancel;
    @BindView(R.id.tv_sure)
    TextView mTvSure;
    @BindView(R.id.et_search)
    EditText mEtSearch;
    @BindView(R.id.map_view)
    MapView mMapView;
    @BindView(R.id.iv_refresh)
    CircleImageView mIvRefresh;
    @BindView(R.id.rv_list)
    RecyclerView mRvList;
    @BindView(R.id.ll_location_menu)
    LinearLayout mLlLocationMenu;
    @BindView(R.id.rv_range)
    RecyclerView mRvRange;
    @BindView(R.id.tv_range)
    TextView mTvRange;

    private AMap mAMap;
    private AMapLocation mAMapLocation;
    private UiSettings mUiSettings;
    private PoiSearch.Query mPoiSearchQuery;
    private PoiSearch mPoiSearch;
    private MyLocationStyle myLocationStyle;
    LocationSource.OnLocationChangedListener mListener;
    AMapLocationClient mlocationClient;
    AMapLocationClientOption mLocationOption;
    private int currentPage = 0;
    private String cityCode;
    private double latitude;
    private double longitude;
    private String address;

    private SelectLocationSearchAdapter mSearchAdapter;

    private List<LocationRangeBean> mRangeList = new ArrayList();
    private SelectLocationRangeAdapter mLocationRangeAdapter;

    @Override
    protected void initData() {
        super.initData();
        mRangeList.clear();
        LocationRangeBean rangeBean;
        rangeBean = new LocationRangeBean(0, 100, false);
        mRangeList.add(rangeBean);
        rangeBean = new LocationRangeBean(1, 200, false);
        mRangeList.add(rangeBean);
        rangeBean = new LocationRangeBean(2, 300, true);
        mRangeList.add(rangeBean);
        rangeBean = new LocationRangeBean(3, 400, false);
        mRangeList.add(rangeBean);
        rangeBean = new LocationRangeBean(4, 500, false);
        mRangeList.add(rangeBean);
        rangeBean = new LocationRangeBean(5, 800, false);
        mRangeList.add(rangeBean);
        rangeBean = new LocationRangeBean(6, 1000, false);
        mRangeList.add(rangeBean);

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
    protected void initView() {

        mEtSearch.setOnEditorActionListener(this::onEditorAction);
        initSearch();
        initRange();
        initMap();
        initLocation();

        doSearchQuery("");
    }

    private void initSearch() {
        mSearchAdapter = new SelectLocationSearchAdapter(R.layout.item_location_search_address, new ArrayList<>());
        mRvList.setLayoutManager(new LinearLayoutManager(this));
        mRvList.setAdapter(mSearchAdapter);
        mSearchAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                PoiItem poiItem = mSearchAdapter.getItem(position);
                LatLng latLng = new LatLng(poiItem.getLatLonPoint().getLatitude(), poiItem.getLatLonPoint().getLongitude());//构造一个位置
                mAMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

                mAMap.clear();
                Log.i(TAG, poiItem.getPoiId() + poiItem.getAdName());
                MarkerOptions markOptiopns = new MarkerOptions();
                markOptiopns.position(latLng);
                TextView textView = new TextView(getApplicationContext());
                textView.setText("" + poiItem.getTitle() + "");
                textView.setGravity(Gravity.CENTER);
                textView.setTextColor(Color.BLACK);
                textView.setBackgroundResource(R.drawable.custom_info_bubble);
                markOptiopns.icon(BitmapDescriptorFactory.fromView(textView));
                mAMap.addMarker(markOptiopns);

                mAMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));


                longitude = poiItem.getLatLonPoint().getLongitude();
                latitude = poiItem.getLatLonPoint().getLatitude();
                address = mAMapLocation.getProvince() + mAMapLocation.getCity() + mAMapLocation.getDistrict() + mAMapLocation.getStreet() + poiItem.getTitle();
            }
        });
    }

    private void initRange() {

        mLocationRangeAdapter = new SelectLocationRangeAdapter(R.layout.item_location_range, mRangeList);
        mLocationRangeAdapter.setSelectPosition(2);
        mRvRange.setLayoutManager(new GridLayoutManager(this, 7));
        mRvRange.setAdapter(mLocationRangeAdapter);
        mLocationRangeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                for (int i = 0; i < mRangeList.size(); i++) {
                    mRangeList.get(i).setSelect(false);
                }
                mRangeList.get(position).setSelect(true);
                mLocationRangeAdapter.setSelectPosition(position);
                mLocationRangeAdapter.notifyDataSetChanged();
                mTvRange.setText("（距以上地点" + mRangeList.get(position).getValue() + "米内签到有效）");
            }
        });
    }

    private void initMap() {
        mAMap = mMapView.getMap();
        mUiSettings = mAMap.getUiSettings();
        mUiSettings.setZoomControlsEnabled(false);
        mAMap.setOnPOIClickListener(this);
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

    @OnClick({R.id.tv_cancel, R.id.tv_sure, R.id.et_search, R.id.iv_refresh})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                finish();
                break;
            case R.id.tv_sure:

                Bundle bundle = new Bundle();
                bundle.putString("longitude", "" + longitude);
                bundle.putString("latitude", "" + latitude);
                bundle.putString("address", "" + address);
                bundle.putString("range", "" + mRangeList.get(mLocationRangeAdapter.getSelectPosition()).getValue());
                Intent intent = new Intent();
                intent.putExtras(bundle);
                setResult(0, intent);
                finish();
                break;
            case R.id.iv_refresh:
                break;
        }
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

    /**
     * 定位成功后回调函数
     */
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (mListener != null && amapLocation != null) {
            if (amapLocation != null && amapLocation.getErrorCode() == 0) {
                mAMapLocation = amapLocation;
                mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
                LatLng latLng = new LatLng(amapLocation.getLongitude(), amapLocation.getLatitude());//构造一个位置
//                mAMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,20));
                mAMap.setMinZoomLevel(18);
                cityCode = amapLocation.getCityCode();
                longitude = amapLocation.getLongitude();
                latitude = amapLocation.getLatitude();
                address = mAMapLocation.getAddress();
                doSearchPOI();
                if (!StrUtils.isEmpty(amapLocation.getCity())) {
                    if (!amapLocation.getCity().equals(WeatherBean.getCityName())){
                        WeatherBean.setCityName(amapLocation.getCity());
                    }
                }
                WeatherBean.setAddress(amapLocation.getAddress());

                Log.i(TAG, "onLocationChanged:" + JSON.toJSONString(amapLocation));
            } else {
                String errText = "定位失败," + amapLocation.getErrorCode() + ": " + amapLocation.getErrorInfo();
                Log.e(TAG, errText);
            }
        }
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
        if (mlocationClient == null) {
            //初始化定位
            mlocationClient = new AMapLocationClient(this);
            //初始化定位参数
            mLocationOption = new AMapLocationClientOption();
            //设置定位回调监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
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
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        String keyWord = v.getText().toString();
        if (TextUtils.isEmpty(keyWord)) {
            ToastUtil.showToast("请输入搜索内容");
        } else {
            KeyboardUtil.closeKeyboard(this);
            mSearchAdapter.getData().clear();
            doSearchQuery(keyWord);
        }
        return true;
    }

    /**
     * 开始进行poi搜索
     */
    protected void doSearchQuery(String keyWord) {
        currentPage = 0;
        mPoiSearchQuery = new PoiSearch.Query(keyWord, "", "" + cityCode);
        mPoiSearchQuery.setPageSize(50);// 设置每页最多返回多少条poiitem
        mPoiSearchQuery.setPageNum(currentPage);// 设置查第一页

        mPoiSearch = new PoiSearch(this, mPoiSearchQuery);
        mPoiSearch.setOnPoiSearchListener(this);
        //设置周边搜索的中心点以及半径
        mPoiSearch.setBound(new PoiSearch.SearchBound(new LatLonPoint(latitude, longitude), 1000));
        // 异步搜索
        mPoiSearch.searchPOIAsyn();
    }


    /**
     * 开始进行poi搜索
     */
    protected void doSearchPOI() {
        currentPage = 0;
        mPoiSearchQuery = new PoiSearch.Query("", "", "");// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        mPoiSearchQuery.setPageSize(50);// 设置每页最多返回多少条poiitem
        mPoiSearchQuery.setPageNum(currentPage);// 设置查第一页

        mPoiSearch = new PoiSearch(this, mPoiSearchQuery);
        mPoiSearch.setOnPoiSearchListener(this);
        mPoiSearch.setBound(new PoiSearch.SearchBound(new LatLonPoint(latitude, longitude), 1000, true));
        // 设置搜索区域为以lp点为圆心，其周围5000米范围
        mPoiSearch.searchPOIAsyn();// 异步搜索
    }


    private PoiResult poiResult; // poi返回的结果

    @Override
    public void onPoiSearched(PoiResult result, int rCode) {
        Log.i(TAG, "onPoiSearched:" + JSON.toJSONString(result));
        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getQuery() != null) {// 搜索poi的结果
                if (result.getQuery().equals(mPoiSearchQuery)) {// 是否是同一条
                    poiResult = result;
                    List<PoiItem> poiItems = poiResult.getPois();// 取得第一页的poiitem数据，页数从数字0开始
                    mSearchAdapter.setNewData(poiItems);
                }
            } else {
            }
        } else {
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {
        Log.i(TAG, "onPoiItemSearched:" + JSON.toJSONString(poiItem));
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onPOIClick(Poi poi) {
        mAMap.clear();
        Log.i("MY", poi.getPoiId() + poi.getName());
        MarkerOptions markOptiopns = new MarkerOptions();
        markOptiopns.position(poi.getCoordinate());
        TextView textView = new TextView(getApplicationContext());
        textView.setText("" + poi.getName() + "");
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.BLACK);
        textView.setBackgroundResource(R.drawable.custom_info_bubble);
        markOptiopns.icon(BitmapDescriptorFactory.fromView(textView));
        mAMap.addMarker(markOptiopns);
        longitude = poi.getCoordinate().longitude;
        latitude = poi.getCoordinate().latitude;

        address = mAMapLocation.getProvince() + mAMapLocation.getCity() + mAMapLocation.getDistrict() + mAMapLocation.getStreet() + poi.getName();
    }
}