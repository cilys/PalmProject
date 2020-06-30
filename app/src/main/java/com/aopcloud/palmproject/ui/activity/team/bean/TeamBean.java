package com.aopcloud.palmproject.ui.activity.team.bean;

import java.util.List;

/**
 * @PackageName : com.aopcloud.palmproject.ui.activity.team
 * @File : TeamBean.java
 * @Date : 2020/5/16 2020/5/16
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class TeamBean {

    /**
     * team_id : 6
     * team_name : 测试添加班组
     * project_id : 15
     * code : TM9CBF
     * industry : 吊顶
     * district : 广州市
     * member : []
     */

    private int team_id;
    private String team_name;
    private int project_id;
    private String code;
    private String industry;
    private String district;
    private List<?> member;

    public int getTeam_id() {
        return team_id;
    }

    public void setTeam_id(int team_id) {
        this.team_id = team_id;
    }

    public String getTeam_name() {
        return team_name;
    }

    public void setTeam_name(String team_name) {
        this.team_name = team_name;
    }

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public List<?> getMember() {
        return member;
    }

    public void setMember(List<?> member) {
        this.member = member;
    }
}
