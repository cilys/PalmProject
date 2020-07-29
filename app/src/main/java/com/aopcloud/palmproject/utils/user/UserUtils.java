package com.aopcloud.palmproject.utils.user;

import com.cily.utils.base.StrUtils;

public class UserUtils {

    /**
     * 是否可以新建工单（项目负责人、项目管理员、项目施工员可以创建工单）
     * @param userRole
     * @return
     */
    public static boolean canAddOrder(String userRole){
        if (StrUtils.isEmpty(userRole)) {
            return false;
        }

        return userRole.contains("负责人") || userRole.contains("管理员")
                || userRole.contains("施工员");
    }

}
