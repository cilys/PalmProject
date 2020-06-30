package com.aopcloud.palmproject.ui.activity.department.bean;

/**
 * @PackageName : com.aopcloud.palmproject.ui.activity.department.bean
 * @File : DepartmentStaffBean.java
 * @Date : 2020/5/1 2020/5/1
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class DepartmentStaffBean  {

    /**
     * user_id : 10
     * user_name : 张三
     * avatar : /1.jpg
     * tag : 总经理
     */

    private int user_id;
    private String user_name;
    private String avatar;
    private String tag;

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

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
