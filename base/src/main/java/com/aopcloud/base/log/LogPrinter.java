package com.aopcloud.base.log;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import static com.aopcloud.base.log.LogConfig.ASSERT;
import static com.aopcloud.base.log.LogConfig.DEBUG;
import static com.aopcloud.base.log.LogConfig.ERROR;
import static com.aopcloud.base.log.LogConfig.INFO;
import static com.aopcloud.base.log.LogConfig.JSON;
import static com.aopcloud.base.log.LogConfig.JSON_INDENT;
import static com.aopcloud.base.log.LogConfig.LINE_SEPARATOR;
import static com.aopcloud.base.log.LogConfig.LOG_TAG;
import static com.aopcloud.base.log.LogConfig.VERBOSE;
import static com.aopcloud.base.log.LogConfig.WARN;
import static com.aopcloud.base.log.LogConfig.XML;
import static com.aopcloud.base.log.Util.getObjectsString;
import static com.aopcloud.base.log.Util.printLine;


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
public class LogPrinter implements IPrinter {

    LogConfig mLogConfig;

    @Override
    public LogConfig getLogConfig() {
        return mLogConfig;
    }

    @Override
    public LogConfig init() {
        if (mLogConfig == null) {
            mLogConfig = new LogConfig();
        }
        return mLogConfig;
    }

    @Override
    public void init(LogConfig logConfig) {
        mLogConfig = logConfig;
    }


    @Override
    public void d(String log) {
        d(LOG_TAG, log);
    }

    @Override
    public void i(Object... log) {
        StringBuilder sb = new StringBuilder();
        for (Object obj : log) {
            sb.append(obj + ",");
        }
        log(INFO, LOG_TAG, String.valueOf(sb));
    }


    @Override
    public void w(String log) {
        log(WARN, LOG_TAG, log);
    }

    @Override
    public void e(String log) {
        log(ERROR, LOG_TAG, log);
    }

    @Override
    public void e(Object... log) {
        StringBuilder sb = new StringBuilder();
        for (Object obj : log) {
            sb.append(obj + ",");
        }
        log(ERROR, LOG_TAG, String.valueOf(sb));
    }

    @Override
    public void e(Throwable throwable) {
        log(ERROR, LOG_TAG, throwable.toString());
    }

    @Override
    public void a(String log) {
        log(ASSERT, LOG_TAG, log);
    }

    @Override
    public void json(String log) {
        log(JSON, LOG_TAG, log);
    }

    @Override
    public void xml(String log) {
        log(XML, LOG_TAG, log);
    }


    @Override
    public void d(String tag, String log) {
        log(DEBUG, tag, log);
    }


    @Override
    public void i(String tag, Object... log) {
        StringBuilder sb = new StringBuilder();
        for (Object obj : log) {
            sb.append(obj + ",");
        }
        log(INFO, tag, String.valueOf(sb));
    }

    @Override
    public void w(String tag, String log) {
        log(WARN, tag, log);
    }

    @Override
    public void e(String tag, String log) {
        log(ERROR, tag, log);
    }

    @Override
    public void e(String tag, Throwable throwable) {
        log(ERROR, tag, throwable.toString());
    }

    @Override
    public void e(String tag, Object... log) {
        StringBuilder sb = new StringBuilder();
        for (Object obj : log) {
            sb.append(obj + ",");
        }
        log(ERROR, tag, String.valueOf(sb));
    }

    @Override
    public void a(String tag, String log) {
        log(ASSERT, tag, log);
    }

    @Override
    public void json(String tag, String log) {
        log(JSON, tag, log);
    }

    @Override
    public void xml(String tag, String log) {
        log(XML, tag, log);
    }


    private void log(int logType, String tagStr, Object objects) {
        String[] contents = wrapperContent(tagStr, objects);
        String tag = contents[0];
        String msg = contents[1];
        String headString = contents[2];
        if (mLogConfig.logLevel != LogLevel.NONE) {
            switch (logType) {
                case VERBOSE:
                case DEBUG:
                case INFO:
                case WARN:
                case ERROR:
                case ASSERT:
                    printDefault(logType, tag, headString + msg);
                    break;
                case JSON:
                    printJson(tag, msg, headString);
                    break;
                case XML:
                    printXml(tag, msg, headString);
                    break;
                default:
                    break;
            }
        }
    }

    public String[] wrapperContent(String tag, Object... objects) {
        if (TextUtils.isEmpty(tag)) {
            tag = LOG_TAG;
        }
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        StackTraceElement targetElement = stackTrace[6];
        if (TextUtils.isEmpty(tag)) {
            targetElement = stackTrace[6];
        }
        String className = targetElement.getClassName();
        String[] classNameInfo = className.split("\\.");
        if (classNameInfo.length > 0) {
            className = classNameInfo[classNameInfo.length - 1] + ".java";
        }
        String methodName = targetElement.getMethodName();
        int lineNumber = targetElement.getLineNumber();
        if (lineNumber < 0) {
            lineNumber = 0;
        }
        String methodNameShort = methodName.substring(0, 1).toUpperCase() + methodName.substring(1);
        String msg = (objects == null) ? "Log with null object" : getObjectsString(objects);
        String headString = "[(" + className + ":" + lineNumber + ")#" + methodNameShort + " ] ";
        return new String[]{tag, msg, headString};
    }

    private void printDefault(int type, String tag, String msg) {
        if (!Util.isPrintLog(mLogConfig, type)) {
            return;
        }
        if (TextUtils.isEmpty(tag)) {
            tag = LOG_TAG;
        }
        int index = 0;
        int maxLength = 4000;
        int countOfSub = msg.length() / maxLength;

        if (countOfSub > 0) {  // The log is so long
            for (int i = 0; i < countOfSub; i++) {
                String sub = msg.substring(index, index + maxLength);
                printSub(type, tag, sub);
                index += maxLength;
            }
            //printSub(type, msg.substring(index, msg.length()));
        } else {
            printSub(type, tag, msg);
        }

    }

    private void printSub(int type, String tag, String sub) {
        if (tag == null) {
            tag = LOG_TAG;
        }
        switch (type) {
            case VERBOSE:
                Log.v(tag, sub);
                break;
            case DEBUG:
                Log.d(tag, sub);
                break;
            case INFO:
                Log.i(tag, sub);
                break;
            case WARN:
                Log.w(tag, sub);
                break;
            case ERROR:
                Log.e(tag, sub);
                break;
            case ASSERT:
                Log.wtf(tag, sub);
                break;
            default:
                break;
        }
    }


    private void printJson(String tag, String json, String headString) {
        if (TextUtils.isEmpty(json)) {
            d("Empty/Null json content");
            return;
        }
        if (TextUtils.isEmpty(tag)) {
            tag = LOG_TAG;
        }
        String message;

        try {
            if (json.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(json);
                message = jsonObject.toString(JSON_INDENT);
            } else if (json.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(json);
                message = jsonArray.toString(JSON_INDENT);
            } else {
                message = json;
            }
        } catch (JSONException e) {
            message = json;
        }

        printLine(tag, true);
        message = headString + LINE_SEPARATOR + message;
        String[] lines = message.split(LINE_SEPARATOR);
        for (String line : lines) {
            Log.d(tag, "|" + line);
        }
        printLine(tag, false);
    }

    private void printXml(String tag, String xml, String headString) {
        if (TextUtils.isEmpty(tag)) {
            tag = LOG_TAG;
        }
        if (xml != null) {
            try {
                Source xmlInput = new StreamSource(new StringReader(xml));
                StreamResult xmlOutput = new StreamResult(new StringWriter());
                Transformer transformer = TransformerFactory.newInstance().newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
                transformer.transform(xmlInput, xmlOutput);
                xml = xmlOutput.getWriter().toString().replaceFirst(">", ">\n");
            } catch (Exception e) {
                e.printStackTrace();
            }
            xml = headString + "\n" + xml;
        } else {
            xml = headString + "Log with null object";
        }

        printLine(tag, true);
        String[] lines = xml.split(LINE_SEPARATOR);
        for (String line : lines) {
            if (!TextUtils.isEmpty(line)) {
                Log.d(tag, "|" + line);
            }
        }
        printLine(tag, false);
    }


}