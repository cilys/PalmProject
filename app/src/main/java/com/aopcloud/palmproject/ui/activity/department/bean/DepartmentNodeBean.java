package com.aopcloud.palmproject.ui.activity.department.bean;

import java.util.List;

/**
 * @PackageName : com.aopcloud.palmproject.ui.activity.department.bean
 * @File : DepartmentNodeBean.java
 * @Date : 2020/5/9 11:20
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @VersionCode : V 1.0
 * @Describe ：
 */
public class DepartmentNodeBean {


    /**
     * name : 总经办
     * department_id : 9
     * childen : []
     */

    private String name;
    private int department_id;
    private boolean select;
    private List<DepartmentNodeBean> childen;
    /**
     * status : 1
     * leader_id : 1
     * leader_name : 曾大大
     * leader_avatar : /upload/2123/1.jpg
     */

    private int status;
    private int leader_id;
    private String leader_name;
    private String leader_avatar;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(int department_id) {
        this.department_id = department_id;
    }

    public List<DepartmentNodeBean> getChilden() {
        return childen;
    }

    public void setChilden(List<DepartmentNodeBean> childen) {
        this.childen = childen;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getLeader_id() {
        return leader_id;
    }

    public void setLeader_id(int leader_id) {
        this.leader_id = leader_id;
    }

    public String getLeader_name() {
        return leader_name;
    }

    public void setLeader_name(String leader_name) {
        this.leader_name = leader_name;
    }

    public String getLeader_avatar() {
        return leader_avatar;
    }

    public void setLeader_avatar(String leader_avatar) {
        this.leader_avatar = leader_avatar;
    }
}
