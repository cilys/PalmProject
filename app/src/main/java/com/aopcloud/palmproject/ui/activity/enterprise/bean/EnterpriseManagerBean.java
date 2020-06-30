package com.aopcloud.palmproject.ui.activity.enterprise.bean;

/**
 * @PackageName : com.aopcloud.palmproject.ui.activity.enterprise.bean
 * @File : EnterpriseManagerBean.java
 * @Date : 2020/4/21 16:39
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @VersionCode : V 1.0
 * @Describe ：
 */
public class EnterpriseManagerBean {

    /**
     * rights : all
     * user_id : 1
     * user_name : 曾大大
     * avatar : /upload/2123/1.jpg
     * type : 负责人
     */

    private String rights;
    private int user_id;
    private String user_name;
    private String avatar;
    private String type;
    private boolean select;


    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }
    public String getRights() {
        return rights;
    }

    public void setRights(String rights) {
        this.rights = rights;
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
