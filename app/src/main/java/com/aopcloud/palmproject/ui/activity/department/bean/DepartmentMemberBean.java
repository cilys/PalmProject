package com.aopcloud.palmproject.ui.activity.department.bean;

import java.util.List;

/**
 * @PackageName : com.aopcloud.palmproject.ui.activity.department.bean
 * @File : DepartmentMemberBean.java
 * @Date : 2020/5/11 2020/5/11
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class DepartmentMemberBean {

    private int company_user_id;
    private boolean select;


    /**
     * user_id : 7
     * avatar : /upload/20200513/vUVMWpnjFC0gZGzRKquPbHVeQP2zs9oMPuzzIpNM.jpeg
     * user_name : 米大爷
     * tag : 经理
     * tel : 13500000001
     * user_status : 2
     * role : 企业负责人
     * roleTags : [{"department_id":9,"role_tag_id":9,"name":"经理"},{"department_id":53,"role_tag_id":10,"name":"总监"}]
     */

    private int user_id;
    private String avatar;
    private String user_name;
    private String tag;
    private long tel;
    private int user_status;
    private String role;
    private List<RoleTagsBean> roleTags;

    public int getCompany_user_id() {
        return company_user_id;
    }

    public void setCompany_user_id(int company_user_id) {
        this.company_user_id = company_user_id;
    }

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

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public long getTel() {
        return tel;
    }

    public void setTel(long tel) {
        this.tel = tel;
    }

    public int getUser_status() {
        return user_status;
    }

    public void setUser_status(int user_status) {
        this.user_status = user_status;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<RoleTagsBean> getRoleTags() {
        return roleTags;
    }

    public void setRoleTags(List<RoleTagsBean> roleTags) {
        this.roleTags = roleTags;
    }

    public static class RoleTagsBean {
        /**
         * department_id : 9
         * role_tag_id : 9
         * name : 经理
         */

        private int department_id;
        private int role_tag_id;
        private String name;

        public int getDepartment_id() {
            return department_id;
        }

        public void setDepartment_id(int department_id) {
            this.department_id = department_id;
        }

        public int getRole_tag_id() {
            return role_tag_id;
        }

        public void setRole_tag_id(int role_tag_id) {
            this.role_tag_id = role_tag_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
