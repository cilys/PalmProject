package com.aopcloud.palmproject.ui.activity.project.bean;

/**
 * @PackageName : com.aopcloud.palmproject.ui.activity.project.bean
 * @File : ProjectLogBean.java
 * @Date : 2020/5/15 9:24
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @VersionCode : V 1.0
 * @Describe ：
 */
public class ProjectLogBean {


    /**
     * trends_id : 25
     * user_id : 1
     * user_avatar : /upload/2123/1.jpg
     * user_name : 曾大大
     * type : 0
     * make_time : 1589492471
     * message : 更新进度
     * content : 曾大大 反馈进度至 10
     * attach : /1.jpg,/2.jpg
     * extra : {"progress_before":0,"progress_after":10}
     * task_id : 13
     * task_name : 工单任务1.0
     * team_id : 4
     * team_name : 水电一班1
     */

    private int trends_id;
    private int user_id;
    private String user_avatar;
    private String user_name;
    private int type;
    private int make_time;
    private String message;
    private String content;
    private String attach;
    private String extra;
    private int task_id;
    private String task_name;
    private int team_id;
    private String team_name;
    public ProjectLogBean(){

    }
    public ProjectLogBean(int type,int id, String name) {
        if (type==0){
            this.task_id = id;
            this.task_name = name;
        }else {
            this.user_id = id;
            this.user_name = name;
        }
    }


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

    public int getMake_time() {
        return make_time;
    }

    public void setMake_time(int make_time) {
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

    public int getTask_id() {
        return task_id;
    }

    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }

    public String getTask_name() {
        return task_name;
    }

    public void setTask_name(String task_name) {
        this.task_name = task_name;
    }

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
}
