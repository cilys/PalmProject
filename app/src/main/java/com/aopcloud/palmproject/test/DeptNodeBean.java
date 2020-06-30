package com.aopcloud.palmproject.test;

/**
 * @PackageName : com.aopcloud.palmproject.test
 * @File : DeptNodeBean.java
 * @Date : 2020/4/22 10:34
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @VersionCode : V 1.0
 * @Describe ：
 */
public class DeptNodeBean extends NodeBean<DeptNodeBean> {

    private int id;//部门ID
    private int parentId;//父亲节点ID
    private String name;//部门名称

    public DeptNodeBean() {
    }

    public DeptNodeBean(int id, int parentId, String name) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
    }




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}