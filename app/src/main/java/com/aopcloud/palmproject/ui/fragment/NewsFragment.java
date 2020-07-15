package com.aopcloud.palmproject.ui.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.amap.api.services.weather.LocalWeatherForecastResult;
import com.amap.api.services.weather.LocalWeatherLive;
import com.amap.api.services.weather.LocalWeatherLiveResult;
import com.amap.api.services.weather.WeatherSearch;
import com.amap.api.services.weather.WeatherSearchQuery;
import com.aopcloud.base.base.BaseFragment;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.loader.BannerGlideImageLoader;
import com.aopcloud.palmproject.ui.fragment.adapter.NewsPageAdapter;
import com.aopcloud.palmproject.ui.fragment.news.NewsNotifyFragment;
import com.aopcloud.palmproject.ui.fragment.news.NewsTodoFragment;
import com.flyco.tablayout.SlidingTabLayout;
import com.gyf.immersionbar.ImmersionBar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;
import vip.devkit.view.common.banner.BannerConfig;
import vip.devkit.view.common.banner.BannerV;
import vip.devkit.view.common.banner.listener.OnBannerListener;

/**
 * @PackageName : com.aopcloud.basic.ui.fragment
 * @File : NewsFragment.java
 * @Date : 2020/4/10 2020/4/10
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class NewsFragment extends BaseFragment implements WeatherSearch.OnWeatherSearchListener {
    @BindView(R.id.banner_view)
    BannerV mBannerView;
    @BindView(R.id.tab_view)
    SlidingTabLayout mTabView;
    @BindView(R.id.page_view)
    ViewPager mPageView;
    Unbinder unbinder;
    @BindView(R.id.tv_date)
    TextView mTvDate;
    @BindView(R.id.tv_weather)
    TextView mTvWeather;
    Unbinder unbinder1;

    private List mDatas = new ArrayList();
    private List<String> mList;
    private List<Fragment> mFragments;
    private String[] weeks = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_news;
    }

    @Override
    protected void initImmersionBar() {
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.reset().fitsSystemWindows(true)
                .statusBarDarkFont(true)
                .navigationBarEnable(true)
                .autoNavigationBarDarkModeEnable(true, 0.2f)
                .navigationBarColor("#111111")
                .statusBarColor(R.color.theme_white)
                .init();
    }

    @Override
    protected void onFirstVisible() {
        super.onFirstVisible();
        initImmersionBar();
    }

    @Override
    protected void onVisible() {
        super.onVisible();
        initImmersionBar();
        initWeather();
    }

    @Override
    protected void initData() {
        super.initData();
        mDatas.clear();
        mDatas.add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=345866336,2046716460&fm=26&gp=0.jpg");
        mDatas.add("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=657430869,2065016904&fm=26&gp=0.jpg");
        mDatas.add("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1580270077,889210988&fm=26&gp=0.jpg");
        mDatas.add("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3221016909,4152370360&fm=26&gp=0.jpg");

        mList = new ArrayList<>();
        mList.add("待办(0)");
        mList.add("通知(0)");
        mFragments = new ArrayList<>();
        NewsTodoFragment todoFragment = new NewsTodoFragment();
        todoFragment.setOnLoadCountListener(new NewsTodoFragment.OnLoadCountListener() {
            @Override
            public void onLoadCount(int size) {
                mTabView.getTitleView(0).setText("待办(" + size + ")");
                mTabView.postInvalidate();
            }
        });
        mFragments.add(todoFragment);
        mFragments.add(new NewsNotifyFragment());
    }


    @Override
    protected void initView(View view) {
        super.initView(view);
        initBannerView();
        Calendar calendar = Calendar.getInstance();


        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        mTvDate.setText(weeks[calendar.get(Calendar.DAY_OF_WEEK) - 1] + " " + dateFormat.format(calendar.getTime()));


        NewsPageAdapter pageAdapter = new NewsPageAdapter(getChildFragmentManager(), mFragments, mList);
        mPageView.setAdapter(pageAdapter);
        mTabView.setViewPager(mPageView);

        initWeather();
    }

    private void initWeather() {
        //检索参数为城市和天气类型，实况天气为WEATHER_TYPE_LIVE、天气预报为WEATHER_TYPE_FORECAST
        WeatherSearchQuery mquery = new WeatherSearchQuery("广州", WeatherSearchQuery.WEATHER_TYPE_LIVE);
        WeatherSearch mweathersearch = new WeatherSearch(mActivity);
        mweathersearch.setOnWeatherSearchListener(this);
        mweathersearch.setQuery(mquery);
        mweathersearch.searchWeatherAsyn(); //异步搜索
    }

    private void initBannerView() {
        mBannerView.setBannerStyle(1);
        mBannerView.setImageLoader(new BannerGlideImageLoader(4));
        mBannerView.setImages(mDatas);
        mBannerView.isAutoPlay(true);
        mBannerView.setDelayTime(3000);
        mBannerView.setIndicatorGravity(BannerConfig.CIRCLE_INDICATOR);
        mBannerView.start();
        mBannerView.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
            }
        });
    }

    @Override
    public void onWeatherLiveSearched(LocalWeatherLiveResult weatherLiveResult, int rCode) {
        if (rCode == 1000) {
            if (weatherLiveResult != null && weatherLiveResult.getLiveResult() != null) {
                LocalWeatherLive weatherlive = weatherLiveResult.getLiveResult();
                mTvWeather.setText("" + weatherlive.getWeather() + " " + weatherlive.getTemperature() + "°C " + weatherlive.getWindDirection() + "风" + weatherlive.getWindPower() + "级");
            } else {
            }
        }
    }

    @Override
    public void onWeatherForecastSearched(LocalWeatherForecastResult localWeatherForecastResult, int i) {

    }
}