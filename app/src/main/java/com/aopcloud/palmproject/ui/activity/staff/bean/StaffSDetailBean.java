package com.aopcloud.palmproject.ui.activity.staff.bean;

import java.util.List;

/**
 * @PackageName : com.aopcloud.palmproject.ui.activity.staff.bean
 * @File : StaffSDetailBean.java
 * @Date : 2020/5/10 2020/5/10
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class StaffSDetailBean {


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
     * company : {"user_code":"CP4B4A44202004140002","time":1586846183}
     * roleTags : [{"department_id":1,"role_tag_id":4,"name":"普通成员","department_name":"总经办"}]
     * trends : [{"id":6,"make_time":1589384563,"user_id":3,"company_code":"CP4B4A44","content":"通过加入企业申请","extra":null}]
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
    private CompanyBean company;
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

    public CompanyBean getCompany() {
        return company;
    }

    public void setCompany(CompanyBean company) {
        this.company = company;
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

    public static class CompanyBean {
        /**
         * user_code : CP4B4A44202004140002
         * time : 1586846183
         */

        private String user_code;
        private long time;

        public String getUser_code() {
            return user_code;
        }

        public void setUser_code(String user_code) {
            this.user_code = user_code;
        }

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }
    }

    public static class RoleTagsBean {
        /**
         * department_id : 1
         * role_tag_id : 4
         * name : 普通成员
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
         * id : 6
         * make_time : 1589384563
         * user_id : 3
         * company_code : CP4B4A44
         * content : 通过加入企业申请
         * extra : null
         */

        private int id;
        private long make_time;
        private int user_id;
        private String company_code;
        private String content;
        private Object extra;

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

        public Object getExtra() {
            return extra;
        }

        public void setExtra(Object extra) {
            this.extra = extra;
        }
    }
}
