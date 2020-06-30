package com.aopcloud.palmproject.ui.activity.task.bean;

/**
 * @PackageName : com.aopcloud.palmproject.ui.activity.task.bean
 * @File : TaskScenesBean.java
 * @Date : 2020/5/21 2020/5/21
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class TaskScenesBean {
    /**
     * project_id : 15
     * task_name : 恶魔之眼
     * attach : /upload/20200515/yZpbgrJqvGLNgh4s3k6vxk6lplv1Fp4qvjtgJ8Cq.jpeg,/upload/20200515/Le3vQ6vRGqLe6v5qXjrmYTn0dP26zFPzP3FSjVxJ.jpeg
     * type : 进度
     * make_time : 1589521336
     * tags : 标签
     * user_id : 7
     * user_avatar : /upload/20200513/vUVMWpnjFC0gZGzRKquPbHVeQP2zs9oMPuzzIpNM.jpeg
     * user_name : 米大爷
     * project_name : 广州水电工程3期
     */

    private String project_id;
    private String task_name;
    private String attach;
    private String type;
    private long make_time;
    private String tags;
    private int user_id;
    private String user_avatar;
    private String user_name;
    private String project_name;

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    public String getTask_name() {
        return task_name;
    }

    public void setTask_name(String task_name) {
        this.task_name = task_name;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getMake_time() {
        return make_time;
    }

    public void setMake_time(long make_time) {
        this.make_time = make_time;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
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

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }
}

