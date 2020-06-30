package com.aopcloud.palmproject.ui.activity.project.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @PackageName : com.aopcloud.palmproject.ui.activity.project.bean
 * @File : ProjectMemberDetailBean.java
 * @Date : 2020/5/13 2020/5/13
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class ProjectMemberDetailBean {


    /**
     * id : 7
     * tel : 13500000001
     * make_time : 1587998473
     * status : 2
     * avatar : /upload/20200513/vUVMWpnjFC0gZGzRKquPbHVeQP2zs9oMPuzzIpNM.jpeg
     * nickname : 野猪佩奇1号
     * name : 米大爷
     * id_no :
     * sex : 男
     * nation : 汉族
     * district : 广州市天河区
     * sinature : 我是你大爷
     * email : 520@qq.com
     * address : 广东省广州市天河区
     * account_type : 中国银行广州分行
     * account_cardno : 009
     * education : 博士
     * school : 广州大学
     * skills : 莫莫莫
     * projects : [{"project_id":15,"project_name":"广州水电工程3期","status":"勘查设计"}]
     * team : {"user_id":7,"company_code":"CP4B4A4C","make_time":1589618969,"majors":"安卓","salary":800,"team_id":6,"type":1,"team_name":"测试编辑班组"}
     * company : {"user_code":"CP4B4A4999","time":1588169862}
     * roleTags : [{"department_id":9,"role_tag_id":9,"name":"经理","department_name":"总经办"},{"department_id":53,"role_tag_id":10,"name":"总监","department_name":"研究院"}]
     * trends : [{"id":32,"make_time":1589618969,"user_id":7,"company_code":"CP4B4A4C","content":"加入团队测试编辑班组","extra":"{\"team_id\":\"6\",\"deal_user_id\":7,\"deal_user_name\":\"\\u7c73\\u5927\\u7237\"}"},{"id":36,"make_time":1589766620,"user_id":7,"company_code":"CP4B4A4C","content":"被移除项目广州水电工程3期的施工员","extra":"{\"project_id\":15,\"deal_user_id\":7,\"deal_user_name\":\"\\u7c73\\u5927\\u7237\"}"},{"id":39,"make_time":1589911752,"user_id":7,"company_code":"CP4B4A4C","content":"被设置为项目广州水电工程3期的施工员","extra":"{\"project_id\":16,\"deal_user_id\":7,\"deal_user_name\":\"\\u7c73\\u5927\\u7237\"}"},{"id":42,"make_time":1589911780,"user_id":7,"company_code":"CP4B4A4C","content":"被移除项目广州水电工程3期的施工员","extra":"{\"project_id\":16,\"deal_user_id\":7,\"deal_user_name\":\"\\u7c73\\u5927\\u7237\"}"},{"id":43,"make_time":1589911789,"user_id":7,"company_code":"CP4B4A4C","content":"被设置为项目广州水电工程3期的管理员","extra":"{\"project_id\":16,\"deal_user_id\":7,\"deal_user_name\":\"\\u7c73\\u5927\\u7237\"}"}]
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
    private List<RoleTagsBean> roleTags;
    private List<TrendsBean> trends;

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
        return avatar;
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
        return projects==null?new ArrayList<>():projects;
    }

    public void setProjects(List<ProjectsBean> projects) {
        this.projects = projects;
    }

    public List<RoleTagsBean> getRoleTags() {
        return roleTags;
    }

    public void setRoleTags(List<RoleTagsBean> roleTags) {
        this.roleTags = roleTags;
    }

    public List<TrendsBean> getTrends() {
        return trends;
    }

    public void setTrends(List<TrendsBean> trends) {
        this.trends = trends;
    }

    public static class TeamBean {
        /**
         * user_id : 7
         * company_code : CP4B4A4C
         * make_time : 1589618969
         * majors : 安卓
         * salary : 800
         * team_id : 6
         * type : 1
         * team_name : 测试编辑班组
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
         * user_code : CP4B4A4999
         * time : 1588169862
         */

        private String user_code;
        private int time;

        public String getUser_code() {
            return user_code;
        }

        public void setUser_code(String user_code) {
            this.user_code = user_code;
        }

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }
    }

    public static class ProjectsBean {
        /**
         * project_id : 15
         * project_name : 广州水电工程3期
         * status : 勘查设计
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

    public static class RoleTagsBean {
        /**
         * department_id : 9
         * role_tag_id : 9
         * name : 经理
         * department_name : 总经办
         */

        private int department_id;
        private int role_tag_id;
        private String name;
        private String department_name;

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

        public String getDepartment_name() {
            return department_name;
        }

        public void setDepartment_name(String department_name) {
            this.department_name = department_name;
        }
    }

    public static class TrendsBean {
        /**
         * id : 32
         * make_time : 1589618969
         * user_id : 7
         * company_code : CP4B4A4C
         * content : 加入团队测试编辑班组
         * extra : {"team_id":"6","deal_user_id":7,"deal_user_name":"\u7c73\u5927\u7237"}
         */

        private int id;
        private long make_time;
        private int user_id;
        private String company_code;
        private String content;
        private String extra;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public long getMake_time() {
            return make_time;
        }

        public void setMake_time(long make_time) {
            this.make_time = make_time;
        }

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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getExtra() {
            return extra;
        }

        public void setExtra(String extra) {
            this.extra = extra;
        }
    }
}