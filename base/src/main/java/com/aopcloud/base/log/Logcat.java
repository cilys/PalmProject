package com.aopcloud.base.log;

/**
 * Package    Name: vip.devkit.debugkit.util
 * File       Name: Logcat.java
 * Create     Date: 2019/7/1 11:51
 * Author         : K
 * E-mail         : vip@devkit.vip
 * VersionCode    : V 1.0
 * Describe       ：
 * Code Update    :（author - time）
 * Update Describe：
 */
public class Logcat {

    private static  IPrinter sPrinter = new LogPrinter();


    private Logcat() {
    }

    /**
     * @return {@link LogConfig} config
     */
    public static LogConfig getLogConfig() {
        return sPrinter.getLogConfig();
    }

    /**
     * default
     * @return {@link LogConfig} log config
     */
    public static LogConfig init() {
        return sPrinter.init();
    }

    /**
     * @param logConfig config
     */
    public static void init(final LogConfig logConfig) {
        sPrinter.init(logConfig);
    }



    public static void d(String msg) {
        sPrinter.d(msg);
    }

    public static void i(Object... msg) {
        sPrinter.i(msg);
    }

    public static void w(String msg) {
        sPrinter.w(msg);
    }

    public static void e(Object... msg) {
        sPrinter.e(msg);
    }

    public static void e(String msg) {
        sPrinter.e(msg);
    }

    public static void e(Throwable  msg) {
        sPrinter.e(msg);
    }

    public static void a(String msg) {
        sPrinter.a(msg);
    }


    public static void d(String tag, String msg) {
        sPrinter.d(tag,msg);
    }
    public static void i(String tag, String msg) {
        sPrinter.i(tag,msg);
    }
    public static void w(String tag, String msg) {
        sPrinter.w(tag,msg);
    }
    public static void e(String tag, Object... msg) {
        sPrinter.e(tag,msg);
    }
    public static void e(String tag, String msg) {
        sPrinter.e(tag,msg);
    }
    public static void e(String tag,Throwable  msg) {
        sPrinter.e(tag,msg);
    }
    public static void a(String tag, String msg) {
        sPrinter.a(tag,msg);
    }





}
