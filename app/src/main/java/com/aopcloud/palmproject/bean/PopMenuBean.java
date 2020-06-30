package com.aopcloud.palmproject.bean;

/**
 * @PackageName : com.aopcloud.palmproject.bean
 * @File : PopMenuBean.java
 * @Date : 2020/5/3 2020/5/3
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class PopMenuBean {

    private String id;
    private int icon=-1;
    private String text;

    public PopMenuBean() {

    }

    public PopMenuBean(String text) {
        this.text = text;
    }

    public PopMenuBean(int iconId, String text) {
        this.icon = iconId;
        this.text = text;
    }

    public PopMenuBean(String id, int iconId, String text) {
        this.id = id;
        this.icon = iconId;
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int iconId) {
        this.icon = iconId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}


