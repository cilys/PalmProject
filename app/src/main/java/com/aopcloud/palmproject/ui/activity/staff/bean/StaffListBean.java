package com.aopcloud.palmproject.ui.activity.staff.bean;

import android.text.TextUtils;

import com.mcxtzhang.indexlib.IndexBar.bean.BaseIndexPinyinBean;

import java.io.Serializable;
import java.util.List;

/**
 * @PackageName : com.aopcloud.palmproject.ui.activity.staff.bean
 * @File : StaffListBean.java
 * @Date : 2020/4/23 16:35
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @VersionCode : V 1.0
 * @Describe ：
 */
public class StaffListBean extends BaseIndexPinyinBean implements Serializable {




    private boolean notToPinyin; //是否不需要被转化成拼音 为true时不转化
    private boolean notShowSuspension;//是否不需要显示标签title 为true时不显示
    /**
     * user_id : 7
     * name : 米大爷
     * avatar : /upload/20200513/vUVMWpnjFC0gZGzRKquPbHVeQP2zs9oMPuzzIpNM.jpeg
     * status : 1
     * come_from :
     * reason :
     * make_time : 1588169862
     * deal_time : 0
     * company_user_id : 15
     * code : CP4B4A4C202004290015
     * user_status : 2
     * tel : 13500000001
     * company_name : 测试一下3编辑公司
     * roleTags : [{"department_id":9,"role_tag_id":9,"name":"经理"},{"department_id":53,"role_tag_id":10,"name":"总监"}]
     * role : 企业负责人
     */

    private int user_id;
    private String name;
    private String avatar;
    private int status;
    private String come_from;
    private String reason;
    private int make_time;
    private int deal_time;
    private int company_user_id;
    private String code;
    private int user_status;
    private long tel;
    private String company_name;
    private String role;
    private List<RoleTagsBean> roleTags;


    public StaffListBean setNotToPinyin(boolean notToPinyin) {
        this.notToPinyin = notToPinyin;
        return this;
    }

    public boolean isNotShowSuspension() {
        return notShowSuspension;
    }

    public StaffListBean setNotShowSuspension(boolean notShowSuspension) {
        this.notShowSuspension = notShowSuspension;
        return this;
    }

    @Override
    public boolean isNeedToPinyin() {
        return !notToPinyin;
    }

    @Override
    public String getTarget() {
        if (TextUtils.isEmpty(name)) {
            return "#";
        }
        return name;
    }


    @Override
    public boolean isShowSuspension() {
        return !notShowSuspension;
    }

    @Override
    public String getSuspensionTag() {
        if (notToPinyin && !notShowSuspension) {
            return "星标";
        } else {
            return super.getSuspensionTag();
        }
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCome_from() {
        return come_from;
    }

    public void setCome_from(String come_from) {
        this.come_from = come_from;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getMake_time() {
        return make_time;
    }

    public void setMake_time(int make_time) {
        this.make_time = make_time;
    }

    public int getDeal_time() {
        return deal_time;
    }

    public void setDeal_time(int deal_time) {
        this.deal_time = deal_time;
    }

    public int getCompany_user_id() {
        return company_user_id;
    }

    public void setCompany_user_id(int company_user_id) {
        this.company_user_id = company_user_id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getUser_status() {
        return user_status;
    }

    public void setUser_status(int user_status) {
        this.user_status = user_status;
    }

    public long getTel() {
        return tel;
    }

    public void setTel(long tel) {
        this.tel = tel;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
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
