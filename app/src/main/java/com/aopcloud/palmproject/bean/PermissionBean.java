package com.aopcloud.palmproject.bean;

/**
 * @PackageName : com.aopcloud.palmproject.bean
 * @File : PermissionBean.java
 * @Date : 2020/4/30 9:16
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @VersionCode : V 1.0
 * @Describe ：
 */
public class PermissionBean {

    private String name;
    private String permission;
    private boolean select;

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public PermissionBean(){}
    public PermissionBean(String name, String permission) {
        this.name = name;
        this.permission = permission;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }
}
