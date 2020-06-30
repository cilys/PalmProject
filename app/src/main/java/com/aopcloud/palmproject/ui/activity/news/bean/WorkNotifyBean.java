package com.aopcloud.palmproject.ui.activity.news.bean;

/**
 * @PackageName : com.aopcloud.basic.ui.activity.news.bean
 * @File : WorkNotifyBean.java
 * @Date : 2020/4/11 2020/4/11
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class WorkNotifyBean {

    /**
     * msg_id : 1
     * title : 企业邀请
     * content : 企业邀请
     * make_time : 1586914403
     * data : {"deal_time":1586914403,"deal_user_id":2,"company_name":"广州xxx有限公司1","msg_type":"t4"}
     */

    private int msg_id;
    private String title;
    private String content;
    private long make_time;
    private int type;

    private DataBean data;

    public void setType(int type) {
        this.type = type;
    }

    public int getMsg_id() {
        return msg_id;
    }

    public void setMsg_id(int msg_id) {
        this.msg_id = msg_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getMake_time() {
        return make_time;
    }

    public void setMake_time(long make_time) {
        this.make_time = make_time;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }


    public static class DataBean {
        /**
         * deal_time : 1586914403
         * deal_user_id : 2
         * company_name : 广州xxx有限公司1
         * msg_type : t4
         */

        private int deal_time;
        private int deal_user_id;
        private String company_name;
        private String msg_type;

        public int getDeal_time() {
            return deal_time;
        }

        public void setDeal_time(int deal_time) {
            this.deal_time = deal_time;
        }

        public int getDeal_user_id() {
            return deal_user_id;
        }

        public void setDeal_user_id(int deal_user_id) {
            this.deal_user_id = deal_user_id;
        }

        public String getCompany_name() {
            return company_name;
        }

        public void setCompany_name(String company_name) {
            this.company_name = company_name;
        }

        public String getMsg_type() {
            return msg_type;
        }

        public void setMsg_type(String msg_type) {
            this.msg_type = msg_type;
        }
    }
}
