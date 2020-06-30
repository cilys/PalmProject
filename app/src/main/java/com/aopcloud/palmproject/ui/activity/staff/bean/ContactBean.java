package com.aopcloud.palmproject.ui.activity.staff.bean;

/**
 * @PackageName : com.aopcloud.palmproject.ui.activity.staff.bean
 * @File : ContactBean.java
 * @Date : 2020/5/10 2020/5/10
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class ContactBean {
    private String name;
    private String phone;
    private String note;
    private boolean select;

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
