package com.aopcloud.palmproject.ui.adapter.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @PackageName : com.aopcloud.palmproject.ui.adapter.base
 * @File : GCBaseExpandableListAdapter.java
 * @Date : 2020/5/21 14:24
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @VersionCode : V 1.0
 * @Describe ：
 */
public abstract class GCBaseExpandableListAdapter <Group, Child> extends BaseExpandableListAdapter {

    protected Map<Group, List<Child>> mGroup = new HashMap<>();
    protected LayoutInflater mInflater;
    protected Context mContext;
    protected int groupLayoutId;
    protected int childLayoutId;

    public GCBaseExpandableListAdapter(Context context, int groupLayoutId, int childLayoutId ,Map<Group, List<Child>> mGroup) {
        this.mContext = context;
        this.mGroup = mGroup;
        this.groupLayoutId = groupLayoutId;
        this.childLayoutId = childLayoutId;
        mInflater = LayoutInflater.from(context);
    }


    @Override
    public int getGroupCount() {
        return mGroup.size();
    }

    @Override
    public Group getGroup(int i) {
        return (Group) mGroup.keySet().toArray()[i];
    }

    @Override
    public int getChildrenCount(int i) {
        return isEmpty(mGroup.get(getGroup(i))) ? 0 : mGroup.get(getGroup(i)).size();
    }

    @Override
    public Child getChild(int i, int i1) {
        List<Child> childList = mGroup.get(getGroup(i));
        return childList.get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }


    @Override
    public View getGroupView(int position, boolean b, View view, ViewGroup viewGroup) {
        ViewHolder mGroupHolder = ViewHolder.get(mContext, view, viewGroup, groupLayoutId, position);
        convertGroup(mGroupHolder,b, getGroup(position), position);
        return mGroupHolder.getConvertView();
    }


    @Override
    public View getChildView(int groupPosition, int childPosition, boolean b, View view, ViewGroup viewGroup) {
        ViewHolder childHolder = ViewHolder.get(mContext, view, viewGroup, childLayoutId, childPosition);
        convertChild(childHolder,b, getGroup(groupPosition), getChild(groupPosition, childPosition), groupPosition, childPosition);
        return childHolder.getConvertView();
    }

    public abstract void convertGroup(ViewHolder holder,boolean isExpanded, Group group, int groupPosition);

    public abstract void convertChild(ViewHolder holder,boolean isExpanded ,Group group, Child child, int groupPosition, int childPosition);

    @Override
    public boolean hasStableIds() {
        return true;
    }

    /**
     * 子类点击事件
     *
     * @param i  groupPosition
     * @param i1 childPosition
     * @return
     */
    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    public static <V> boolean isEmpty(List<V> sourceList) {
        return (sourceList == null || sourceList.size() == 0);
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
    }
}
