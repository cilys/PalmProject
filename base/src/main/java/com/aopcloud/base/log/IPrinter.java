package com.aopcloud.base.log;

/**
 * Package    Name: vip.devkit.debugkit.kit.log
 * File       Name: IPrinter.java
 * Create     Date: 2019/7/8 15:16
 * Author         : K
 * E-mail         : vip@devkit.vip
 * VersionCode    : V 1.0
 * Describe       ：
 * Code Update    :（author - time）
 * Update Describe：
 */
public interface IPrinter {

    /**
     * 获取日志配置信息
     *
     * @return {@link LogConfig} 日志配置
     */
    LogConfig getLogConfig();

    /**
     * 初始化日志配置信息 ( 使用默认配置 )
     *
     * @return {@link LogConfig} 日志配置
     */
    LogConfig init();


    /**
     * 自定义日志配置信息
     *
     * @param logConfig 日志配置
     */
    void init(LogConfig logConfig);


    void d(String log);

    void i(Object... log);

    void w(String log);

    void e(String log);

    void e(Object... log);

    void e(Throwable throwable);

    void a(String log);

    void json(String log);

    void xml(String log);



    void d(String tag, String log);

    void i(String tag, Object... log);

    void w(String tag, String log);

    void e(String tag, String log);

    void e(String tag, Throwable throwable);

    void e(String tag, Object... log);

    void a(String tag, String log);

    void json(String tag, String log);

    void xml(String tag, String log);

}