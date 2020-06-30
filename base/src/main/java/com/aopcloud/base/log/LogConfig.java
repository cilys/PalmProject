package com.aopcloud.base.log;

/**
 * Package    Name: vip.devkit.debugkit.kit.log
 * File       Name: LogConfig.java
 * Create     Date: 2019/7/8 15:00
 * @Author         : K
 * E-mail         : vip@devkit.vip
 * VersionCode    : V 1.0
 * Describe       ：
 * Code Update    :（author - time）
 * Update Describe：
 */
public class LogConfig {

    public static final int VERBOSE = 2;
    public static final int DEBUG = 3;
    public static final int INFO = 4;
    public static final int WARN = 5;
    public static final int ERROR = 6;
    public static final int ASSERT = 7;
    public static final int JSON = 8;
    public static final int XML = 9;

    public static final int JSON_INDENT = 4;

    public static final String LINE_SEPARATOR = System.getProperty("line.separator");


    public static String LOG_TAG = "dk_log";

    public static LogLevel DEFAULT_LOG_LEVEL = LogLevel.DEBUG;


    public static LogLevel logLevel=DEFAULT_LOG_LEVEL;

    public static void setLogTag(String logTag) {
        LOG_TAG = logTag;
    }

    public void setLogLevel(LogLevel level) {
        logLevel = level;
    }
}

