package com.aopcloud.palmproject.ui.activity.project.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProjectDetailBean {

    /**
     * company_code : CP4B4A4C
     * leader_id : 1
     * pid : 7
     * name : 广州水电工程4.1期
     * short_name : 水电1期
     * make_time : 1588576728
     * address : 新县城
     * longitue : 103.2932
     * latitude : 24.3234
     * status : 勘查设计
     * progress : 0
     * rights : 2
     * district :
     * code : aaxx0011
     * type : 土建工程
     * price : 100
     * area : 150
     * summary : 项目简介
     * leader_avatar : /upload/2123/1.jpg
     * leader_name : 曾大大
     * project_id : 9
     * start_date : 2020-12-01
     * end_date : 2022-12-01
     * start_date_real : 1970-01-01
     * end_date_real : 1970-01-01
     * trends : [{"extra":"","make_time":1588576728,"type":2,"user_id":7,"content":"用户13500000001 创建项目","message":"","attach":""}]
     */

    private String company_code;
    private int leader_id;
    private int pid;
    private String name;
    private String short_name;
    private long make_time;
    private String address;
    private String longitue;
    private String latitude;
    private String status;
    private int progress;
    private int rights;
    private String district;
    private String code;
    private String type;
    private String price;
    private String area;
    private String summary;
    private String leader_avatar;
    private String leader_name;
    private int project_id;
    private String start_date;
    private String end_date;
    private String start_date_real;
    private String end_date_real;
    private List<TrendsBean> trends;

    public String getCompany_code() {
        return company_code;
    }

    public void setCompany_code(String company_code) {
        this.company_code = company_code;
    }

    public int getLeader_id() {
        return leader_id;
    }

    public void setLeader_id(int leader_id) {
        this.leader_id = leader_id;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShort_name() {
        return short_name;
    }

    public void setShort_name(String short_name) {
        this.short_name = short_name;
    }

    public long getMake_time() {
        return make_time;
    }

    public void setMake_time(long make_time) {
        this.make_time = make_time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLongitue() {
        return longitue;
    }

    public void setLongitue(String longitue) {
        this.longitue = longitue;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getRights() {
        return rights;
    }

    public void setRights(int rights) {
        this.rights = rights;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getLeader_avatar() {
        return leader_avatar;
    }

    public void setLeader_avatar(String leader_avatar) {
        this.leader_avatar = leader_avatar;
    }

    public String getLeader_name() {
        return leader_name;
    }

    public void setLeader_name(String leader_name) {
        this.leader_name = leader_name;
    }

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getStart_date_real() {
        return start_date_real;
    }

    public void setStart_date_real(String start_date_real) {
        this.start_date_real = start_date_real;
    }

    public String getEnd_date_real() {
        return end_date_real;
    }

    public void setEnd_date_real(String end_date_real) {
        this.end_date_real = end_date_real;
    }

    public List<TrendsBean> getTrends() {
        return trends;
    }

    public void setTrends(List<TrendsBean> trends) {
        this.trends = trends;
    }

    public static class TrendsBean {
        /**
         * extra :
         * make_time : 1588576728
         * type : 2
         * user_id : 7
         * content : 用户13500000001 创建项目
         * message :
         * attach :
         */

        private String extra;
        private long make_time;
        private int type;
        private int user_id;
        private String content;
        private String message;
        private String attach;
        /**
         * make_time : 1589515761
         * user_avatar : /upload/20200513/vUVMWpnjFC0gZGzRKquPbHVeQP2zs9oMPuzzIpNM.jpeg
         */

        private String user_avatar;

        public String getExtra() {
            return extra;
        }

        public void setExtra(String extra) {
            this.extra = extra;
        }

        public long getMake_time() {
            return make_time;
        }

        public void setMake_time(long make_time) {
            this.make_time = make_time;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getAttach() {
            return attach;
        }

        public void setAttach(String attach) {
            this.attach = attach;
        }

        public String getUser_avatar() {
            return user_avatar;
        }

        public void setUser_avatar(String user_avatar) {
            this.user_avatar = user_avatar;
        }
    }
}