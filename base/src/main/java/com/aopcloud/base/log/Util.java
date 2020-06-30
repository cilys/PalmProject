package com.aopcloud.base.log;

import android.util.Log;


/**
 * Package    Name: vip.devkit.debugkit.kit.log
 * File       Name: Util.java
 * Create     Date: 2019/7/8 15:38
 * Author         : K
 * E-mail         : vip@devkit.vip
 * VersionCode    : V 1.0
 * Describe       ：
 * Code Update    :（author - time）
 * Update Describe：
 */
public class Util {



    public static String getObjectsString(Object... objects) {

        if (objects.length > 1) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("\n");
            for (int i = 0; i < objects.length; i++) {
                Object object = objects[i];
                if (object == null) {
                    stringBuilder.append("param").append("[").append(i).append("]").append(" = ").append("null").append("\n");
                } else {
                    stringBuilder.append("param").append("[").append(i).append("]").append(" = ").append(object.toString()).append("\n");
                }
            }
            return stringBuilder.toString();
        } else {
            Object object = objects[0];
            return object == null ? "null" : object.toString();
        }
    }

    public static void printLine(String tag, boolean isTop) {
        if (isTop) {
            Log.d(tag, "╔═══════════════════════════════════════════════════════════════════════════════════════");
        } else {
            Log.d(tag, "╚═══════════════════════════════════════════════════════════════════════════════════════");
        }
    }

    /**
     * 是否打印日志
     * @param logConfig 日志配置
     * @param logType   日志类型
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isPrintLog(final LogConfig logConfig, final int logType) {
        boolean isPrint = false;
        LogLevel logLevel = logConfig.logLevel;
        switch (logLevel) {
            case NONE: //
                break;
            case DEBUG: //  v, d - all
                isPrint = true;
                break;
            case INFO: //  i
            case WARN: //  w
            case ERROR: //  e, wtf
                isPrint = checkLogLevel(logLevel, logType);
                break;
            default:
                break;
        }
        return isPrint;
    }
    /**
     * 判断日志级别是否允许输出
     * @param logLevel 日志级别
     * @param logType  日志类型
     * @return {@code true} yes, {@code false} no
     */
    private static boolean checkLogLevel(final LogLevel logLevel, final int logType) {
        switch (logLevel) {
            case INFO: //  i
                if (logType != Log.VERBOSE && logType != Log.DEBUG) {
                    return true;
                }
                break;
            case WARN: //  w
                if (logType != Log.VERBOSE && logType != Log.DEBUG && logType != Log.INFO) {
                    return true;
                }
                break;
            case ERROR: //  e, wtf
                if (logType == Log.ERROR || logType == Log.ASSERT) {
                    return true;
                }
                break;
            default:
                break;
        }
        return false;
    }

}
