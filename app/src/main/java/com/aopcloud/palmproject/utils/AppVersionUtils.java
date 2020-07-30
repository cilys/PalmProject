package com.aopcloud.palmproject.utils;

import com.aopcloud.palmproject.BuildConfig;
import com.cily.utils.base.StrUtils;
import com.cily.utils.base.log.Logs;

public class AppVersionUtils {

    public static boolean needUpdate(String remoteAppVersion){
        try{
            if (BuildConfig.VERSION_NAME.equals(remoteAppVersion)) {
                return false;
            }

            if (StrUtils.isEmpty(remoteAppVersion)) {
                return false;
            }

            String[] strs = remoteAppVersion.split("\\.");
            if (strs == null || strs.length < 1){
                return false;
            }
            String[] currentVersions = BuildConfig.VERSION_NAME.split("\\.");
            int i0 = Integer.valueOf(strs[0]);
            int c0 = Integer.valueOf(currentVersions[0]);
            if (i0 < c0) {
                return false;
            }
            if (i0 > c0) {
                return true;
            }
            //i0 == i1
            int i1 = Integer.valueOf(strs[1]);
            int c1 = Integer.valueOf(currentVersions[1]);

            if (i1 < c1) {
                return false;
            }
            if (i1 > c1){
                return true;
            }

            int i2 = Integer.valueOf(strs[2]);
            int c2 = Integer.valueOf(currentVersions[2]);
            if (i2 < c2){
                return false;
            }
            if (i2 > c2){
                return true;
            }
        }catch (Exception e){
            Logs.printException(e);
        }

        return false;
    }

}
