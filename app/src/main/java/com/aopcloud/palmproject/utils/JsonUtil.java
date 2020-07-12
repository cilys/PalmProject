package com.aopcloud.palmproject.utils;

import android.util.Log;

import com.alibaba.fastjson.JSON;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * @PackageName : com.aopcloud.palmproject.utils
 * @File : JsonUtil.java
 * @Date : 2020/4/27 2020/4/27
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class JsonUtil {
    protected static String TAG = JsonUtil.class.getSimpleName();
    /**
     * @param json
     * @param field
     * @return
     * @Describe  解析单个数据
     */
    public static String parserField(String json, String field) {
        try {
            JSONObject obj = new JSONObject(json);
            return obj.getString(field);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * @param o
     * @return
     */
    public String toJsonByF(Object o){
        if (null==o){
            Log.d(TAG, "metadata is empty");
            return null;
        }
        return JSON.toJSONString(o);
    }
    public static <T> List fromJsonArray(String  json, Class<T> clazz) {
        return JSON.parseArray(json,clazz);
    }


    public static <T> T fromJsonObject(String  json, Class<T> clazz) {
        return JSON.parseObject(json,clazz);
    }

}
