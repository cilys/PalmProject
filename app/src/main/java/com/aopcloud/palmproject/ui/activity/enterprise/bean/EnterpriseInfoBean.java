package com.aopcloud.palmproject.ui.activity.enterprise.bean;

/**
 * @PackageName : com.aopcloud.palmproject.ui.activity.enterprise.bean
 * @File : EnterpriseInfoBean.java
 * @Date : 2020/4/28 2020/4/28
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class EnterpriseInfoBean {

    /**
     * code : AA990011
     * logo : /upload/20201231/123123.jpg
     * name : 广州xxx有限公司
     * district : 广东省广州市
     * industry : 建筑工程
     * leader_id : 101
     * leader_name : 曾大大
     * avatar : /avatar.jpg
     * registered_capital : 500
     * account_name : 曾大大
     * account_type : 建设银行
     * account_cardno : 621300033232123323
     * make_time : 123123123
     * id_photo : /upload/20201231/1.jpg,/upload/20201231/2.jpg
     */

    private String code;
    private String logo;
    private String name;
    private String district;
    private String industry;
    private String leader_id;
    private String leader_name;
    private String avatar;
    private int registered_capital;
    private String account_name;
    private String account_type;
    private String account_cardno;
    private int make_time;
    private String id_photo;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getLeader_id() {
        return leader_id;
    }

    public void setLeader_id(String leader_id) {
        this.leader_id = leader_id;
    }

    public String getLeader_name() {
        return leader_name;
    }

    public void setLeader_name(String leader_name) {
        this.leader_name = leader_name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getRegistered_capital() {
        return registered_capital;
    }

    public void setRegistered_capital(int registered_capital) {
        this.registered_capital = registered_capital;
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public String getAccount_type() {
        return account_type;
    }

    public void setAccount_type(String account_type) {
        this.account_type = account_type;
    }

    public String getAccount_cardno() {
        return account_cardno;
    }

    public void setAccount_cardno(String account_cardno) {
        this.account_cardno = account_cardno;
    }

    public int getMake_time() {
        return make_time;
    }

    public void setMake_time(int make_time) {
        this.make_time = make_time;
    }

    public String getId_photo() {
        return id_photo;
    }

    public void setId_photo(String id_photo) {
        this.id_photo = id_photo;
    }
}
