package com.aopcloud.palmproject.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.cily.utils.base.StrUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * @PackageName : com.aopcloud.palmproject.utils
 * @File : SharedPreferencesUtils.java
 * @Date : 2020/4/27 2020/4/27
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class SharedPreferencesUtils {
    /**
     * SharedPreferences存储在sd卡中的文件名字
     * @param context
     * @return
     */
    private static String getSpName(Context context) {
        return context.getPackageName() + "_preferences";
    }

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     * @param context
     * @param key
     * @param object
     */
    public static void setSP(Context context, String key, Object object) {
        SharedPreferences sp = context.getSharedPreferences(getSpName(context), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            editor.putString(key, object.toString());
        }

        SharedPreferencesCompat.apply(editor);
    }

    public static void saveSet(Context context, String key, LinkedHashSet<String> sets){
        if (context == null || StrUtils.isEmpty(key) || sets == null) {
            return;
        }
        SharedPreferences sp = context.getSharedPreferences(getSpName(context), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putStringSet(key, sets);
        SharedPreferencesCompat.apply(editor);
    }

    public static Set<String> getSet(Context context, String key, Set<String> defValue){
        if (context == null || StrUtils.isEmpty(key)) {
            return defValue;
        }
        SharedPreferences sp = context.getSharedPreferences(getSpName(context), Context.MODE_PRIVATE);
        return sp.getStringSet(key, defValue);
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     * @param context
     * @param key
     * @param defaultObject
     * @return
     */
    public static Object getSP(Context context, String key, Object defaultObject) {
        SharedPreferences sp = context.getSharedPreferences(getSpName(context), Context.MODE_PRIVATE);

        if (defaultObject instanceof String) {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sp.getLong(key, (Long) defaultObject);
        } else {
            return null;
        }
    }

    /**
     * 移除某个key值已经对应的值
     * @param context
     * @param key
     */
    public static void removeSP(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(getSpName(context), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 清除所有数据
     * @param context
     */
    public static void clearAllSP(Context context) {
        SharedPreferences sp = context.getSharedPreferences(getSpName(context), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 查询某个key是否已经存在
     * @param context
     * @param key
     * @return
     */
    public static boolean contains(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(getSpName(context), Context.MODE_PRIVATE);
        return sp.contains(key);
    }

    /**
     * 返回所有的键值对
     * @param context
     * @return
     */
    public static Map<String, ?> getAllKeyValue(Context context) {
        SharedPreferences sp = context.getSharedPreferences(getSpName(context), Context.MODE_PRIVATE);
        return sp.getAll();
    }

    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     *
     */
    private static class SharedPreferencesCompat {

        private static final Method sApplyMethod = findApplyMethod();

        /**
         * 反射查找apply的方法
         * @return
         */
        @SuppressWarnings({"unchecked", "rawtypes"})
        private static Method findApplyMethod() {
            try {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         * @param editor
         */
        public static void apply(SharedPreferences.Editor editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (IllegalArgumentException expected) {
                expected.printStackTrace();
            } catch (IllegalAccessException expected) {
                expected.printStackTrace();
            } catch (InvocationTargetException expected) {
                expected.printStackTrace();
            }
            editor.commit();
        }
    }


}
