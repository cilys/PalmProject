package com.aopcloud.palmproject.ui.activity.map.bean;

/**
 * @PackageName : com.aopcloud.palmproject.ui.activity.map.bean
 * @File : LocationRangeBean.java
 * @Date : 2020/4/18 2020/4/18
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class LocationRangeBean {
    private int position;
    private int value;
    private boolean select;

    public LocationRangeBean(int position, int value, boolean select) {
        this.position = position;
        this.value = value;
        this.select = select;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }
}
