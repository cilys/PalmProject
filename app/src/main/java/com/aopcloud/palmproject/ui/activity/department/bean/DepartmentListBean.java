package com.aopcloud.palmproject.ui.activity.department.bean;

import java.util.List;

/**
 * @PackageName : com.aopcloud.palmproject.ui.activity.department.bean
 * @File : DepartmentStaffBean.java
 * @Date : 2020/5/1 2020/5/1
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class DepartmentListBean {

    /**
     * name : 服务中心
     * department_id : 14
     */

    private String name;
    private int department_id;

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
}
