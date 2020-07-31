package com.aopcloud.palmproject.utils;

import android.content.Context;

import java.util.LinkedHashSet;
import java.util.Set;

public class SpUtils {

    public static void saveRecentlyUsedProjects(Context context, LinkedHashSet<String> sets){
        SharedPreferencesUtils.saveSet(context, "sp_recently_used_projects", sets);
    }

    public static Set<String> getRecentlyUsedProjects(Context context){
        return SharedPreferencesUtils.getSet(context, "sp_recently_used_projects", null);
    }

    public static void saveRecentlyUsedTasks(Context context, LinkedHashSet<String> sets){
        SharedPreferencesUtils.saveSet(context, "sp_recently_used_tasks", sets);
    }

    public static Set<String> getRecentlyUsedTasks(Context context){
        return SharedPreferencesUtils.getSet(context, "sp_recently_used_tasks", null);
    }

}
