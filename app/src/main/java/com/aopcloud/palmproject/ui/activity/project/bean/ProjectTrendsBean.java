package com.aopcloud.palmproject.ui.activity.project.bean;

/**
 * @PackageName : com.aopcloud.palmproject.ui.activity.project.bean
 * @File : ProjectTrendsBean.java
 * @Date : 2020/5/31 2020/5/31
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class ProjectTrendsBean {

    /**
     * trends_id : 67
     * user_id : 7
     * user_avatar : /upload/20200513/vUVMWpnjFC0gZGzRKquPbHVeQP2zs9oMPuzzIpNM.jpeg
     * user_name : 米大爷
     * type : 0
     * make_time : 1590915146
     * message : 恶魔眼在真学在真学在真学在真学在真学在真转
     * content : 米大爷 反馈进度至 43
     * attach : /upload/20200531/DUoDxpYHEnibsCUI6clwvGaySFu1D7LHdoxeTxyw.jpeg
     * extra : {"progress_before":41,"progress_after":"43"}
     */

    private int trends_id;
    private int user_id;
    private String user_avatar;
    private String user_name;
    private int type;
    private long make_time;
    private String message;
    private String content;
    private String attach;
    private String extra;

    public int getTrends_id() {
        return trends_id;
    }

    public void setTrends_id(int trends_id) {
        this.trends_id = trends_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_avatar() {
        return user_avatar;
    }

    public void setUser_avatar(String user_avatar) {
        this.user_avatar = user_avatar;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getMake_time() {
        return make_time;
    }

    public void setMake_time(long make_time) {
        this.make_time = make_time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }
}
