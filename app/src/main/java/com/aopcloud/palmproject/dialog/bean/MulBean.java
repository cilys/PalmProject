package com.aopcloud.palmproject.dialog.bean;

import java.io.Serializable;

public class MulBean implements Serializable {
    private String id;
    private String name;
    private boolean selected;

    public MulBean(){

    }

    public MulBean(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public MulBean(String id, String name, boolean selected) {
        this.id = id;
        this.name = name;
        this.selected = selected;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
