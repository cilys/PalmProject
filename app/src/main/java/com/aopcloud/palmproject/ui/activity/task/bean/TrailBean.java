package com.aopcloud.palmproject.ui.activity.task.bean;

/**
 * @PackageName : com.aopcloud.palmproject.ui.activity.task.bean
 * @File : TrailBean.java
 * @Date : 2020/5/27 10:23
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @VersionCode : V 1.0
 * @Describe ：
 */
public class TrailBean {

    /**
     * longitue : 128.3214
     * latitude : 24.1235
     * make_time : 1587451578
     */

    private String longitue;
    private String latitude;
    private int make_time;

    public String getLongitue() {
        return longitue;
    }

    public void setLongitue(String longitue) {
        this.longitue = longitue;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public int getMake_time() {
        return make_time;
    }

    public void setMake_time(int make_time) {
        this.make_time = make_time;
    }
}
