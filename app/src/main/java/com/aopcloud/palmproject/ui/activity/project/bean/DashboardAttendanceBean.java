package com.aopcloud.palmproject.ui.activity.project.bean;

/**
 * @PackageName : com.aopcloud.palmproject.ui.activity.project.bean
 * @File : DashboardAttendanceBean.java
 * @Date : 2020/5/23 2020/5/23
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class DashboardAttendanceBean {

    /**
     * task_name : 测试创建工单
     * user_id : 7
     * user_name : 米大爷
     * user_avatar : /upload/20200513/vUVMWpnjFC0gZGzRKquPbHVeQP2zs9oMPuzzIpNM.jpeg
     * make_time : 1590230412
     * other_id : 0
     * address : 广东省广州市天河区棠东丰乐路17号靠近棠潮女人街
     * longitue : 113.383068
     * latitude : 23.126627
     * type : 0
     * attach : /upload/20200523/30EH1b44dgOV0LESHkMoV1octBD1d6BDRicMXhuo.jpeg
     */

    private String task_name;
    private int user_id;
    private String user_name;
    private String user_avatar;
    private long make_time;
    private int other_id;
    private String address;
    private String longitue;
    private String latitude;
    private int type;
    private String attach;
    private boolean select;

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public String getTask_name() {
        return task_name;
    }

    public void setTask_name(String task_name) {
        this.task_name = task_name;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_avatar() {
        return user_avatar;
    }

    public void setUser_avatar(String user_avatar) {
        this.user_avatar = user_avatar;
    }

    public long getMake_time() {
        return make_time;
    }

    public void setMake_time(long make_time) {
        this.make_time = make_time;
    }

    public int getOther_id() {
        return other_id;
    }

    public void setOther_id(int other_id) {
        this.other_id = other_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }
}
