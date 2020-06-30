package com.aopcloud.palmproject.bean;

/**
 * @PackageName : com.aopcloud.palmproject.bean
 * @File : MsgTodoNotifyBean.java
 * @Date : 2020/6/8 2020/6/8
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class MsgTodoNotifyBean {
    private int type;
    private String typeName;
    private String title;
    private String msg;
    private long time;
    private int project_id;
    private int company_user_id;
    private int work_value;
    private int pid;
    private int task_id;
    private int user_id;
    private int user_status;
    private String work_unit;
    private String name;


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    public int getCompany_user_id() {
        return company_user_id;
    }

    public void setCompany_user_id(int company_user_id) {
        this.company_user_id = company_user_id;
    }

    public int getWork_value() {
        return work_value;
    }

    public void setWork_value(int work_value) {
        this.work_value = work_value;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getTask_id() {
        return task_id;
    }

    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getUser_status() {
        return user_status;
    }

    public void setUser_status(int user_status) {
        this.user_status = user_status;
    }

    public String getWork_unit() {
        return work_unit;
    }

    public void setWork_unit(String work_unit) {
        this.work_unit = work_unit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
