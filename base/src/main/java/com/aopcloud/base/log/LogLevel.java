package com.aopcloud.base.log;

/**
 * Package    Name: vip.devkit.debugkit.kit.log
 * File       Name: LogLevel.java
 * Create     Date: 2019/7/8 14:59
 * Author         : K
 * E-mail         : vip@devkit.vip
 * VersionCode    : V 1.0
 * Describe       ：
 * Code Update    :（author - time）
 * Update Describe：
 */
public enum LogLevel {
    /**
     * 全部不打印
     */
    NONE,

    /**
     * 调试级别 v, d - 全部打印
     */
    DEBUG,

    /**
     * 正常级别 i
     */
    INFO,

    /**
     * 警告级别 w
     */
    WARN,

    /**
     * 异常级别 e, wtf
     */
    ERROR
}