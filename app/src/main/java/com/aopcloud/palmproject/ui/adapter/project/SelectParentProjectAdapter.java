package com.aopcloud.palmproject.ui.adapter.project;

import android.content.Context;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.ui.activity.project.bean.ProjectListBean;
import com.aopcloud.palmproject.ui.adapter.base.GCBaseExpandableListAdapter;
import com.aopcloud.palmproject.ui.adapter.base.ViewHolder;

import java.util.List;
import java.util.Map;

/**
 * @PackageName : com.aopcloud.palmproject.ui.adapter.project
 * @File : SelectParentProjectAdapter.java
 * @Date : 2020/5/21 14:26
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @VersionCode : V 1.0
 * @Describe ：
 */
public class SelectParentProjectAdapter extends GCBaseExpandableListAdapter<String, ProjectListBean> {
    int sGroupPosition = -1;
    int sChildPosition = -1;

    private OnPickerListener mOnPickerListener;

    public void setOnPickerListener(OnPickerListener onPickerListener) {
        mOnPickerListener = onPickerListener;
    }

    public SelectParentProjectAdapter(Context context, int groupLayoutId, int childLayoutId, Map<String, List<ProjectListBean>> mGroup) {
        super(context, groupLayoutId, childLayoutId, mGroup);
    }

    @Override
    public void convertGroup(ViewHolder holder, boolean isExpanded, String s, int groupPosition) {
        holder.setText(R.id.tv_name, s);
    }

    @Override
    public void convertChild(ViewHolder holder, boolean isExpanded, String s, ProjectListBean projectListBean, int groupPosition, int childPosition) {
        holder.setText(R.id.checkbox, projectListBean.getCompany_code() + "-" + projectListBean.getName());
        CheckBox checkBox = holder.getView(R.id.checkbox);
        if (sGroupPosition==groupPosition&&sChildPosition==childPosition){
            checkBox.setChecked(true);
        }else {
            checkBox.setChecked(false);
        }
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    if (mOnPickerListener != null) {
                        mOnPickerListener.onPickerItem(projectListBean);
                    }
                }else {
                    if (mOnPickerListener != null) {
                        mOnPickerListener.onPickerItem(null);
                    }
                }
            }
        });
    }


    /**
     * @param groupPosition
     * @param childPosition
     */
    public void setSelectedPosition(int groupPosition, int childPosition) {
        this.sGroupPosition = groupPosition;
        this.sChildPosition = childPosition;
    }



    public interface OnPickerListener{
        void  onPickerItem(ProjectListBean videoItem);
    }
}
