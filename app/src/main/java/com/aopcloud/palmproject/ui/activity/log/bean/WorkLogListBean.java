package com.aopcloud.palmproject.ui.activity.log.bean;

/**
 * @PackageName : com.aopcloud.palmproject.ui.activity.log.bean
 * @File : WorkLogListBean.java
 * @Date : 2020/4/21 2020/4/21
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class WorkLogListBean {

    /**
     * attach : null
     * make_time : 1588083290
     * type : 3
     * tasks_finished : 5
     * tasks_overtime : 1
     * tasks_doing : 1
     * summary : 健健康康
     * plan : 来咯来咯
     * user_id : 7
     * avatar : /img/avatar.jpg
     * name :
     */

    private String attach;
    private int make_time;
    private int type;
    private int tasks_finished;
    private int tasks_overtime;
    private int tasks_doing;
    private String summary;
    private String plan;
    private int user_id;
    private String avatar;
    private String name;

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public int getMake_time() {
        return make_time;
    }

    public void setMake_time(int make_time) {
        this.make_time = make_time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getTasks_finished() {
        return tasks_finished;
    }

    public void setTasks_finished(int tasks_finished) {
        this.tasks_finished = tasks_finished;
    }

    public int getTasks_overtime() {
        return tasks_overtime;
    }

    public void setTasks_overtime(int tasks_overtime) {
        this.tasks_overtime = tasks_overtime;
    }

    public int getTasks_doing() {
        return tasks_doing;
    }

    public void setTasks_doing(int tasks_doing) {
        this.tasks_doing = tasks_doing;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
