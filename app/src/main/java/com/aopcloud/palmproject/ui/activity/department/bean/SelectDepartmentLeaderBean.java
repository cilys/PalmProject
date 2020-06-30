package com.aopcloud.palmproject.ui.activity.department.bean;

/**
 * @PackageName : com.aopcloud.palmproject.ui.activity.department.bean
 * @File : SelectDepartmentLeaderBean.java
 * @Date : 2020/5/11 2020/5/11
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class SelectDepartmentLeaderBean {


    /**
     * user_id : 7
     * name : 用户13500000001
     * avatar : /img/avatar.jpg
     * status : 1
     * reason :
     * make_time : 1588169862
     * deal_time : 0
     * company_user_id : 15
     * code : CP4B4A4C202004290015
     */

    private int user_id;
    private String name;
    private String avatar;
    private int status;
    private String reason;
    private int make_time;
    private int deal_time;
    private int company_user_id;
    private String code;
    private boolean select;


    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getMake_time() {
        return make_time;
    }

    public void setMake_time(int make_time) {
        this.make_time = make_time;
    }

    public int getDeal_time() {
        return deal_time;
    }

    public void setDeal_time(int deal_time) {
        this.deal_time = deal_time;
    }

    public int getCompany_user_id() {
        return company_user_id;
    }

    public void setCompany_user_id(int company_user_id) {
        this.company_user_id = company_user_id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
