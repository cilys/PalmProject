package com.aopcloud.palmproject.ui.activity;

import com.alibaba.fastjson.JSON;
import com.aopcloud.base.base.BaseActivity;
import com.aopcloud.palmproject.api.ApiConstants;
import com.aopcloud.palmproject.bean.WeatherBean;
import com.aopcloud.palmproject.net.BaseRequestListener;
import com.aopcloud.palmproject.net.HttpUtils;
import com.aopcloud.palmproject.utils.LoginUserUtil;
import com.aopcloud.palmproject.utils.WeatherUtils;
import com.cily.utils.base.StrUtils;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseAc extends BaseActivity {

    protected String getUserNickName(){
        return LoginUserUtil.getLoginUserBean(this).getNickname();
    }

    protected void getWeather(String cityName){
        String cityCode = WeatherUtils.getCityCode(cityName);
        if (StrUtils.isEmpty(cityCode)){
            return;
        }
        HttpUtils.post(String.valueOf(ApiConstants.EventTags.weather),
                String.format(ApiConstants.weather, cityCode), null, null, new BaseRequestListener() {
            @Override
            public void onSuccess(String tag, String response) {
                super.onSuccess(tag, response);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        requestSuccess(tag, response);
                    }
                });
            }

            @Override
            public void onError(String tag, Exception e) {
                super.onError(tag, e);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        requestFailed(tag, e);
                    }
                });
            }
        });
    }

    protected String getWeatherInfo(){
        return WeatherBean.getCurrentWeatherInfo();
    }
    protected String getAddress(){
        return WeatherBean.getAddress();
    }

    protected void requestSuccess(String tag, String response){
        if (String.valueOf(ApiConstants.EventTags.weather).equals(tag)) {
            getWeatherSuccess(response);
        }
    }
    protected void requestFailed(String tag, Exception e){

    }

    @Override
    protected void initData() {
        super.initData();
        if (StrUtils.isEmpty(WeatherBean.getCurrentWeatherInfo())) {
            getWeather(WeatherBean.getCityName());
        }
    }

    protected void getWeatherSuccess(String response){
        if (!StrUtils.isEmpty(response)) {
            try{
                WeatherBean weatherBean = JSON.parseObject(response, WeatherBean.class);
                if (weatherBean != null && weatherBean.getWeatherinfo() != null) {
                    String s = weatherBean.getWeatherinfo().getWeather()
                            + " " + weatherBean.getWeatherinfo().getTemp1();

                    WeatherBean.setCurrentWeatherInfo(s);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        HttpUtils.cancel(String.valueOf(ApiConstants.EventTags.weather));
    }

    protected String getCompanyId(){
        return LoginUserUtil.getCurrentEnterpriseNo(this);
    }

    protected Map<String, String> baseParamMap(){
        Map<String, String> map = new HashMap<>();
        String token =  LoginUserUtil.getToken(this);
        if (!StrUtils.isEmpty(token)) {
            map.put("token", token);
        }
        String companyId = getCompanyId();
        if (!StrUtils.isEmpty(companyId)) {
            map.put("code", companyId);
        }
        return map;
    }
}