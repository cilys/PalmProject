package com.aopcloud.palmproject.bean;

import android.text.TextUtils;

import java.util.List;

/**
 * @PackageName : com.aopcloud.palmproject.bean
 * @File : UserBean.java
 * @Date : 2020/4/27 2020/4/27
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class UserBean {

    /**
     * id : 3
     * tel : 13912341236
     * make_time : 1586749953
     * status : 2
     * avatar : /upload/2123/1.jpg
     * nickname : 冰凡
     * name : 曾大大
     * id_no :
     * sex : 女
     * nation : 汉族
     * district : 广东省梅州市
     * sinature : 个性签名
     * email : 123@qq.com
     * address : 广东清远
     * account_type : 工商银行
     * account_cardno : 625200000232948
     * education : 本科
     * school : 嘉应大学
     * skills : 英文4级
     * projects : [{"project_id":1,"project_name":"广州水电工程1期","status":"开工预备"}]
     * team : {"user_id":3,"company_code":"CP4B4A44","make_time":1587032368,"majors":"木工","salary":100,"team_id":1,"type":1,"team_name":"水电一班1"}
     * company : {"user_code":"CP4B4A44202004140002"}
     */

    private int id;
    private long tel;
    private long make_time;
    private int status;
    private String avatar;
    private String nickname;
    private String name;
    private String id_no;
    private String sex;
    private String nation;
    private String district;
    private String sinature;
    private String email;
    private String address;
    private String account_type;
    private String account_cardno;
    private String education;
    private String school;
    private String skills;
    private TeamBean team;
    private CompanyBean company;
    private List<ProjectsBean> projects;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getTel() {
        return tel;
    }

    public void setTel(long tel) {
        this.tel = tel;
    }

    public long getMake_time() {
        return make_time;
    }

    public void setMake_time(long make_time) {
        this.make_time = make_time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getAvatar() {
        return TextUtils.isEmpty(avatar)?"":avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId_no() {
        return id_no;
    }

    public void setId_no(String id_no) {
        this.id_no = id_no;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getSinature() {
        return sinature;
    }

    public void setSinature(String sinature) {
        this.sinature = sinature;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAccount_type() {
        return account_type;
    }

    public void setAccount_type(String account_type) {
        this.account_type = account_type;
    }

    public String getAccount_cardno() {
        return account_cardno;
    }

    public void setAccount_cardno(String account_cardno) {
        this.account_cardno = account_cardno;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public TeamBean getTeam() {
        return team;
    }

    public void setTeam(TeamBean team) {
        this.team = team;
    }

    public CompanyBean getCompany() {
        return company;
    }

    public void setCompany(CompanyBean company) {
        this.company = company;
    }

    public List<ProjectsBean> getProjects() {
        return projects;
    }

    public void setProjects(List<ProjectsBean> projects) {
        this.projects = projects;
    }

    public static class TeamBean {
        /**
         * user_id : 3
         * company_code : CP4B4A44
         * make_time : 1587032368
         * majors : 木工
         * salary : 100
         * team_id : 1
         * type : 1
         * team_name : 水电一班1
         */

        private int user_id;
        private String company_code;
        private int make_time;
        private String majors;
        private int salary;
        private int team_id;
        private int type;
        private String team_name;

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getCompany_code() {
            return company_code;
        }

        public void setCompany_code(String company_code) {
            this.company_code = company_code;
        }

        public int getMake_time() {
            return make_time;
        }

        public void setMake_time(int make_time) {
            this.make_time = make_time;
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

        public int getTeam_id() {
            return team_id;
        }

        public void setTeam_id(int team_id) {
            this.team_id = team_id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getTeam_name() {
            return team_name;
        }

        public void setTeam_name(String team_name) {
            this.team_name = team_name;
        }
    }

    public static class CompanyBean {
        /**
         * user_code : CP4B4A44202004140002
         */

        private String user_code;

        public String getUser_code() {
            return user_code;
        }

        public void setUser_code(String user_code) {
            this.user_code = user_code;
        }
    }

    public static class ProjectsBean {
        /**
         * project_id : 1
         * project_name : 广州水电工程1期
         * status : 开工预备
         */

        private int project_id;
        private String project_name;
        private String status;

        public int getProject_id() {
            return project_id;
        }

        public void setProject_id(int project_id) {
            this.project_id = project_id;
        }

        public String getProject_name() {
            return project_name;
        }

        public void setProject_name(String project_name) {
            this.project_name = project_name;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
