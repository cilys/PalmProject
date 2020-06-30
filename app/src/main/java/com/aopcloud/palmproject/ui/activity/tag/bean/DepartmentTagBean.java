package com.aopcloud.palmproject.ui.activity.tag.bean;

/**
 * @PackageName : com.aopcloud.palmproject.ui.activity.post.bean
 * @File : DepartmentTagBean.java
 * @Date : 2020/5/2 2020/5/2
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class DepartmentTagBean {


    /**
     * tag_id : 11
     * name : 财务主管
     * level : 100
     * rights : project:add,project:find
     */

    private int tag_id;
    private String name;
    private int level;
    private String rights;
    private boolean select;

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public int getTag_id() {
        return tag_id;
    }

    public void setTag_id(int tag_id) {
        this.tag_id = tag_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getRights() {
        return rights;
    }

    public void setRights(String rights) {
        this.rights = rights;
    }
}
