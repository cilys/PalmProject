package com.aopcloud.palmproject.ui.activity.staff.bean;

/**
 * @PackageName : com.aopcloud.palmproject.ui.activity.staff.bean
 * @File : RefuseApplyDetailBean.java
 * @Date : 2020/5/12 2020/5/12
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class RefuseApplyDetailBean {

    /**
     * id : 5
     * tel : 18676632715
     * make_time : 1587972740
     * status : 2
     * avatar : /upload/20200430/BvhK8FO4dqqkrRbs2HfNpH2hOxMa051gkbYZayHO.jpeg
     * nickname : 用户18676632715
     * name : 用户18676632715
     * id_no :
     * sex : 男
     * nation : 汉族
     * district : 广东省东莞市莞城
     * sinature : 哈哈哈
     * email :
     * address :
     * account_type : 中国银行
     * account_cardno :
     * education :
     * school :
     * skills :
     * company : {"user_code":"CP4B4A4C202004290015"}
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

    public static class CompanyBean {
        /**
         * user_code : CP4B4A4C202004290015
         */

        private String user_code;

        public String getUser_code() {
            return user_code;
        }

        public void setUser_code(String user_code) {
            this.user_code = user_code;
        }
    }
}
