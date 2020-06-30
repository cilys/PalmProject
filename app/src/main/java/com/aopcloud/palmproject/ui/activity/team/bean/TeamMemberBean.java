package com.aopcloud.palmproject.ui.activity.team.bean;

/**
 * @PackageName : com.aopcloud.palmproject.ui.activity.team.bean
 * @File : TeamMemberBean.java
 * @Date : 2020/5/16 2020/5/16
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class TeamMemberBean {
    /**
     * user_id : 4
     * avatar : /upload/2123/1.jpg
     * user_name : 曾大大
     * majors : 木工
     * salary : 100
     * type : 1
     */

    private int user_id;
    private String avatar;
    private String user_name;
    private String majors;
    private int salary;
    private int type;
    boolean select;
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getMajors() {
        return majors;
    }

    public void setMajors(String majors) {
        this.majors = majors;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
