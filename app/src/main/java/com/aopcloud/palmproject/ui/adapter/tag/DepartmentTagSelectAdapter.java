package com.aopcloud.palmproject.ui.adapter.tag;

import android.support.annotation.Nullable;

import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.bean.PermissionBean;
import com.aopcloud.palmproject.common.Constants;
import com.aopcloud.palmproject.ui.activity.tag.bean.DepartmentTagBean;
import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;
import java.util.Map;

/**
 * @PackageName : com.aopcloud.palmproject.ui.adapter.post
 * @File :DepartmentTagSelectAdapter.java
 * @Date : 2020/4/22 2020/4/22
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class DepartmentTagSelectAdapter extends BaseQuickAdapter<DepartmentTagBean, BaseViewHolder> {


    public DepartmentTagSelectAdapter(int layoutResId, @Nullable List<DepartmentTagBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DepartmentTagBean item) {
        String[] permissions = item.getRights().split(",");

        Map map = Constants.getPermissionMaps();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < permissions.length; i++) {
            if (map.containsKey(permissions[i])) {
                if (stringBuffer.length() > 0) {
                    stringBuffer.append(",");
                }
                stringBuffer.append(map.get(permissions[i]));
            }
        }
        helper.setText(R.id.tv_no, "" + (helper.getAdapterPosition() + 1))
                .setText(R.id.tv_name, item.getName())
                .setText(R.id.tv_permission_count, permissions.length + "个权限")
                .setText(R.id.tv_permission, stringBuffer.toString())
                .setChecked(R.id.checkbox,item.isSelect());



    }
}
