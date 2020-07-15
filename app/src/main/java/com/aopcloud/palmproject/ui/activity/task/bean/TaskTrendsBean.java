package com.aopcloud.palmproject.ui.activity.task.bean;

import java.io.Serializable;

/**
 * @PackageName : com.aopcloud.palmproject.ui.activity.task.bean
 * @File : TaskTrendsBean.java
 * @Date : 2020/5/21 2020/5/21
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class TaskTrendsBean {


    /**
     * trends_id : 7
     * user_id : 1
     * user_avatar : /upload/2123/1.jpg
     * user_name : 曾大大
     * type : 4
     * make_time : 1587204599
     * message :
     * content : 曾大大 指派给团队 水电一班1
     * attach :
     * extra : {"team_id":"1"}
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

    public static class ExtraBean implements Serializable {
        private int work_value_done_before;
        private int work_value_done_after;
        private int progress_before;
        private int progress_after;

        public int getWork_value_done_before() {
            return work_value_done_before;
        }

        public void setWork_value_done_before(int work_value_done_before) {
            this.work_value_done_before = work_value_done_before;
        }

        public int getWork_value_done_after() {
            return work_value_done_after;
        }

        public void setWork_value_done_after(int work_value_done_after) {
            this.work_value_done_after = work_value_done_after;
        }

        public int getProgress_before() {
            return progress_before;
        }

        public void setProgress_before(int progress_before) {
            this.progress_before = progress_before;
        }

        public int getProgress_after() {
            return progress_after;
        }

        public void setProgress_after(int progress_after) {
            this.progress_after = progress_after;
        }
    }
}
