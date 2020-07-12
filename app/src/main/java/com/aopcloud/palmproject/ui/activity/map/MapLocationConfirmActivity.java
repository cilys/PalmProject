package com.aopcloud.palmproject.ui.activity.map;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.aopcloud.base.annotation.Layout;
import com.aopcloud.base.base.BaseActivity;
import com.aopcloud.base.util.ToastUtil;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.ui.activity.map.adapter.SelectLocationConfirmAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @PackageName : com.aopcloud.palmproject.ui.activity.map
 * @File : MapLocationConfirmActivity.java
 * @Date : 2020/5/25 9:43
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @VersionCode : V 1.0
 * @Describe ：
 */
@Layout(R.layout.activity_rv)
public class MapLocationConfirmActivity extends BaseActivity
        implements AMapLocationListener
        , PoiSearch.OnPoiSearchListener {
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
    @BindView(R.id.rv_list)
    RecyclerView mRvList;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mRefreshLayout;


    private PoiSearch.Query mPoiSearchQuery;
    private PoiSearch mPoiSearch;

    private AMapLocationClient mLocationClient;
    private AMapLocationClientOption mLocationOption;

    private int currentPage = 0;
    private String cityCode;
    private double latitude;
    private double longitude;
    private String address;


    private SelectLocationConfirmAdapter mSearchAdapter;

    @Override
    protected void initView() {
        mRefreshLayout.setEnableLoadMore(false);
        mRefreshLayout.setEnableRefresh(false);
        mTvHeaderTitle.setText("地点微调");
        mTvHeaderRight.setText("确认");
        mSearchAdapter = new SelectLocationConfirmAdapter(R.layout.item_location_confirm, new ArrayList<>());
        mRvList.setLayoutManager(new LinearLayoutManager(this));
        mRvList.setAdapter(mSearchAdapter);
        initLocation();
    }

    @Override
    protected void setListener() {
        super.setListener();
        mSearchAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                address = mSearchAdapter.getItem(position).getSnippet();
                longitude = mSearchAdapter.getItem(position).getLatLonPoint().getLongitude();
                latitude = mSearchAdapter.getItem(position).getLatLonPoint().getLatitude();

                mSearchAdapter.setSelectPosition(position);
                mSearchAdapter.notifyDataSetChanged();
            }
        });
    }

    @OnClick({R.id.ll_header_back, R.id.ll_header_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_header_back:
                finish();
                break;
            case R.id.ll_header_right:
                if (mSearchAdapter.getSelectPosition() == -1) {
                    ToastUtil.showToast("请选择地址");
                    return;
                }

                Bundle bundle = new Bundle();
                bundle.putDouble("longitude", longitude);
                bundle.putDouble("latitude", latitude);
                bundle.putString("address", "" + address);
                bundle.putString("address", "" + address);
                Intent intent = new Intent();
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }

    private void initLocation() {

        mLocationClient = new AMapLocationClient(this);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位监听
        mLocationClient.setLocationListener(this);
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        //设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        mLocationClient.startLocation();
    }

    /**
     * 定位成功后回调函数
     */
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                cityCode = amapLocation.getCityCode();
                longitude = amapLocation.getLongitude();
                latitude = amapLocation.getLatitude();
                address = amapLocation.getAddress();
                doSearchPOI();
                Log.i(TAG, "onLocationChanged:" + JSON.toJSONString(amapLocation));
            } else {
                String errText = "定位失败," + amapLocation.getErrorCode() + ": " + amapLocation.getErrorInfo();
                Log.e(TAG, errText);
            }
        }
    }


    /**
     * 开始进行poi搜索
     */
    protected void doSearchPOI() {
        currentPage = 0;
        mPoiSearchQuery = new PoiSearch.Query("", "", "");// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        mPoiSearchQuery.setPageSize(100);// 设置每页最多返回多少条poiitem
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
    public void onDestroy() {
        super.onDestroy();
        if (null != mLocationClient) {
            mLocationClient.onDestroy();
        }
    }
}
