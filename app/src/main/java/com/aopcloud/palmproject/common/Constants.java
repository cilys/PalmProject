package com.aopcloud.palmproject.common;

import android.os.Environment;

import com.aopcloud.palmproject.bean.PermissionBean;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @PackageName : com.aopcloud.palmproject.common
 * @File : Constants.java
 * @Date : 2020/4/28 2020/4/28
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class Constants {
    public static String STORE_FOLDER = "PalmProject";

    public static String STORE_FOLDER_CAMERA ="camera";



    public static String URL_ABOUT ="http://zhj.gzfy.tech:31004/about";
    public static String URL_FEEDBACK ="http://zhj.gzfy.tech:31004/feedback";
    /**
     * 权限列表
     *
     * @return
     */
    public static List getPermissions() {
        List list = new ArrayList();
        PermissionBean permissionBean;
        permissionBean = new PermissionBean("所有权限", "all");
        list.add(permissionBean);
        permissionBean = new PermissionBean("部门成员管理", "department:member");
        list.add(permissionBean);
        permissionBean = new PermissionBean("企业信息管理", "company:info");
        list.add(permissionBean);
        permissionBean = new PermissionBean("企业部门管理", "company:department");
        list.add(permissionBean);
        permissionBean = new PermissionBean("企业成员管理", "company:member");
        list.add(permissionBean);
        permissionBean = new PermissionBean("项目管理:编辑项目", "project:edit");
        list.add(permissionBean);
        permissionBean = new PermissionBean("项目管理:查看项目", "project:find");
        list.add(permissionBean);
        permissionBean = new PermissionBean("班组团队", "department:team");
        list.add(permissionBean);
        permissionBean = new PermissionBean("项目管理:添加项目", "project:add");
        list.add(permissionBean);
        return list;
    }


    /**
     * 权限列表
     *
     * @return
     */
    public static Map getPermissionMaps() {
        Map<String, String> map = new HashMap();
        map.put("all", "所有权限");
        map.put("department:member", "部门成员管理");
        map.put("company:info", "企业信息管理");
        map.put("company:department", "企业部门管理");
        map.put("company:member", "企业成员管理");
        map.put("project:edit", "项目管理:编辑项目");
        map.put("project:find", "项目管理:查看项目");
        map.put("department:team", "班组团队");
        map.put("project:add", "项目管理:添加项目");
        return map;
    }

}
