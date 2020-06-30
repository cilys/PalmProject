package com.aopcloud.palmproject.ui.activity.log.bean;

/**
 * @PackageName : com.aopcloud.palmproject.ui.activity.log.bean
 * @File : WorkLogStatisticsBean.java
 * @Date : 2020/4/28 2020/4/28
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class WorkLogStatisticsBean {

    /**
     * total : 2
     * report_day : 0
     * report_week : 0
     * report_month : 0
     */

    private int total;
    private int report_day;
    private int report_week;
    private int report_month;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getReport_day() {
        return report_day;
    }

    public void setReport_day(int report_day) {
        this.report_day = report_day;
    }

    public int getReport_week() {
        return report_week;
    }

    public void setReport_week(int report_week) {
        this.report_week = report_week;
    }

    public int getReport_month() {
        return report_month;
    }

    public void setReport_month(int report_month) {
        this.report_month = report_month;
    }
}
