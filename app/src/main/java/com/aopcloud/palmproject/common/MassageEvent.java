package com.aopcloud.palmproject.common;

import com.aopcloud.base.common.BaseEvent;

/**
 * @PackageName : com.aopcloud.palmproject.common
 * @File : MassageEvent.java
 * @Date : 2020/5/5 2020/5/5
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class MassageEvent extends BaseEvent {

    public static final  String SWITCH_COMPANY="SWITCH_COMPANY";

    public MassageEvent(String type) {
        super(type);
    }
}
