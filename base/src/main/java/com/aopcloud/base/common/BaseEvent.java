package com.aopcloud.base.common;

/**
 * @PackageName : com.aopcloud.base.common
 * @File : BaseEvent.java
 * @Date : 2019/12/26 11:44
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @VersionCode : V 1.0
 * @Describe ：
 */
public class BaseEvent {

    public static String EVENT_LOGIN ="1";
    public static String EVENT_LOGOUT ="2";


    public String type;
    public Object result;
    public BaseEvent(String type){
        this.type = type;
    }
    public BaseEvent(String type, Object result) {
        this.type = type;
        this.result = result;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "BaseEvent{" +
                "type=" + type +
                ", result=" + result +
                '}';
    }
}
