package com.aopcloud.palmproject.test;

import com.aopcloud.palmproject.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @PackageName : com.aopcloud.palmproject.test
 * @File : NodeBean.java
 * @Date : 2020/4/22 10:06
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @VersionCode : V 1.0
 * @Describe ：
 */
public abstract class NodeBean<T> {

    private int _level = -1;
    protected List<T> _childrenList = new ArrayList<>();
    private NodeBean _parent;
    private int _icon;
    private boolean isExpand = false;




    public int get_level() {
        if (_level == -1){//如果是 -1 的话就递归获取
            //因为是树形结构，所以此处想要得到当前节点的层级
            //，必须递归调用，但是递归效率低下，如果每次都递归获取会严重影响性能，所以我们把第一次
            //得到的结果保存起来避免每次递归获取
            int level = _parent == null ? 1 : _parent.get_level()+1;
            _level = level;
            return _level;
        }
        return _level;
    }

    public void set_level(int _level) {
        this._level = _level;
    }

    public List<T> get_childrenList() {
        return _childrenList;
    }

    public void set_childrenList(List<T> _childrenList) {
        this._childrenList = _childrenList;
    }

    public NodeBean get_parent() {
        return _parent;
    }

    public void set_parent(NodeBean _parent) {
        this._parent = _parent;
    }

    public int get_icon() {
        return _icon;
    }

    public void set_icon(int _icon) {
        this._icon = _icon;
    }

    public boolean isExpand() {
        return isExpand;
    }

    public void setIsExpand(boolean isExpand) {
        this.isExpand = isExpand;
        if (isExpand){
        }else{
        }
    }

    public boolean isRoot(){
        return _parent == null;
    }

    public boolean isLeaf(){
        return _childrenList.size() <= 0;
    }

}