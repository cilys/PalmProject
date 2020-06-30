package com.aopcloud.palmproject.ui.adapter.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * @PackageName : com.aopcloud.palmproject.ui.adapter.base
 * @File : CommonAdapter.java
 * @Date : 2020/4/21 2020/4/21
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public abstract class CommonAdapter<T> extends BaseAdapter {
    //这些属性都是每个适配器中都能用到的，访问控制符设置为protected，以便继承的子类都能访问
    protected LayoutInflater mInflater;
    protected List<T> mBeanList;//数据源
    protected Context mContext;
    protected int layoutId;//item布局文件

    public CommonAdapter(Context context, int layoutId,List<T> mBeanList) {
        this.mContext = context;
        this.mBeanList = mBeanList;
        mInflater = LayoutInflater.from(context);
        this.layoutId = layoutId;
    }


    public List<T> getBeanList() {
        return mBeanList;
    }

    @Override
    public int getCount() {
        return mBeanList.size();
    }

    @Override
    public T getItem(int position) {//这里的返回值类型是T，不是自动生成的Object
        return mBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount();

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        mContext = parent.getContext();
        ViewHolder holder = ViewHolder.get(mContext, convertView, parent, layoutId, position);
        convert(holder, getItem(position), position);
        return holder.getConvertView();
    }

    public abstract void convert(ViewHolder holder, T t, int position);


}
