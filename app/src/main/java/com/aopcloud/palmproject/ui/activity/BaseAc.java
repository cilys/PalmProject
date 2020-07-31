package com.aopcloud.palmproject.ui.activity;

import com.alibaba.fastjson.JSON;
import com.aopcloud.base.base.BaseActivity;
import com.aopcloud.base.common.BaseEvent;
import com.aopcloud.palmproject.api.ApiConstants;
import com.aopcloud.palmproject.bean.UserBean;
import com.aopcloud.palmproject.bean.WeatherBean;
import com.aopcloud.palmproject.common.ResultBean;
import com.aopcloud.palmproject.net.BaseRequestListener;
import com.aopcloud.palmproject.net.HttpUtils;
import com.aopcloud.palmproject.ui.activity.enterprise.bean.EnterpriseManagerBean;
import com.aopcloud.palmproject.utils.LoginUserUtil;
import com.aopcloud.palmproject.utils.SpUtils;
import com.aopcloud.palmproject.utils.WeatherUtils;
import com.aopcloud.palmproject.utils.user.UserUtils;
import com.cily.utils.base.StrUtils;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.greenrobot.event.EventBus;

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

    protected String getUserId(){
        UserBean ub = LoginUserUtil.getLoginUserBean(this);
        return ub == null ? "" : "" + ub.getId();
    }

    /**
     * 是否可以创建工单
     * @return
     */
    protected boolean canAddProjectOrder(){
        String type = LoginUserUtil.getUserRole(this);
        return UserUtils.canAddOrder(type);
    }

    /**
     * 获取企业的管理员
     */
    protected void getManageAll(){
        String token =  LoginUserUtil.getToken(this);
        if (StrUtils.isEmpty(token)) {
            return;
        }
        String companyId = getCompanyId();
        if (StrUtils.isEmpty(companyId)) {
            return;
        }
        int uId  = -1;
        UserBean ub = LoginUserUtil.getLoginUserBean(this);
        if (ub != null) {
            uId = ub.getId();
        }
        final int userId = uId;

        HttpUtils.post(String.valueOf(ApiConstants.EventTags.manage_all),
                ApiConstants.manage_all, null, baseParamMap(), new BaseRequestListener() {
                    @Override
                    public void onError(String tag, Exception e) {
                        super.onError(tag, e);
                    }

                    @Override
                    public void onSuccess(String tag, String response) {
                        super.onSuccess(tag, response);
                        ResultBean bean = JSON.parseObject(response, ResultBean.class);
                        if (bean != null && bean.getCode() == 0) {
                            if (StrUtils.isEmpty(bean.getData())){
                                return;
                            }

                            List<EnterpriseManagerBean> ls = JSON.parseArray(bean.getData(), EnterpriseManagerBean.class);
                            if (ls != null) {
                                for (EnterpriseManagerBean b : ls) {
                                    if (b.getUser_id() == userId) {
                                        LoginUserUtil.saveUserRole(BaseAc.this, b.getType());
                                    }
                                }
                            }
                        }
                    }
                });
    }

    protected void saveRecentlyUsedProjects(String projectId){
        if (StrUtils.isEmpty(projectId)) {
            return;
        }
        String[] strs = getRecentlyUsedProjects();
        LinkedHashSet<String> sets = getSaveSets(strs, projectId);
        if (sets != null && sets.size() > 0) {
            SpUtils.saveRecentlyUsedProjects(this, sets);
        }
    }
    protected void saveRecentlyUsedTasks(String projectId){
        if (StrUtils.isEmpty(projectId)) {
            return;
        }
        String[] strs = getRecentlyUsedTasks();
        LinkedHashSet<String> sets = getSaveSets(strs, projectId);
        if (sets != null && sets.size() > 0) {
            SpUtils.saveRecentlyUsedTasks(this, sets);
        }
    }

    protected String[] getRecentlyUsedTasks(){
        Set<String> sets = SpUtils.getRecentlyUsedTasks(this);
        if (sets != null) {
            return sets.toArray(new String[sets.size()]);
        }
        return null;
    }

    protected String[] getRecentlyUsedProjects(){
        Set<String> sets = SpUtils.getRecentlyUsedProjects(this);
        if (sets != null) {
            return sets.toArray(new String[sets.size()]);
        }
        return null;
    }

    private LinkedHashSet<String> getSaveSets(String[] strs, String key){
        if (StrUtils.isEmpty(key)) {
            return null;
        }

        LinkedHashSet<String> set = new LinkedHashSet<>();

        if (strs == null) {
            set.add(key);
        } else {
            if (strs.length == 0) {
                set.add(key);
            }else if (strs.length == 1) {
                String s = strs[0];
                if (key.equals(s)) {
                    set.add(s);
                } else {
                    set.add(key);
                    set.add(s);
                }
            } else if (strs.length == 2) {
                String s0 = strs[0];
                String s1 = strs[1];
                if (key.equals(s0)) {
                    set.add(s0);
                    set.add(s1);
                } else if (key.equals(s1)) {
                    set.add(s1);
                    set.add(s0);
                } else {
                    set.add(key);
                    set.add(s0);
                    set.add(s1);
                }
            } else if (strs.length == 3) {
                String s0 = strs[0];
                String s1 = strs[1];
                String s2 = strs[1];
                if (key.equals(s0)) {
                    set.add(s0);
                    set.add(s1);
                    set.add(s2);
                } else if (key.equals(s1)) {
                    set.add(s1);
                    set.add(s0);
                    set.add(s2);
                } else if (key.equals(s2)) {
                    set.add(s2);
                    set.add(s0);
                    set.add(s1);
                } else {
                    set.add(key);
                    set.add(s0);
                    set.add(s1);
                }
            }
        }
        return set;
    }
}