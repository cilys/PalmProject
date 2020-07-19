package com.aopcloud.palmproject.utils;

import com.cily.utils.base.StrUtils;
import com.cily.utils.base.time.TimeType;
import com.cily.utils.base.time.TimeUtils;

public class DateUtils {

    public static long betweenDay(String day1, String day2) {
        if (!StrUtils.isEmpty(day1) && !StrUtils.isEmpty(day2)) {
            long d1 = TimeUtils.strToMil(day1, TimeType.DAY_LINE, System.currentTimeMillis());
            long d2 = TimeUtils.strToMil(day2, TimeType.DAY_LINE, System.currentTimeMillis());

            return betweenDay(d1, d2);
        }
        return 0;
    }

    public static long betweenDay(long d1, long d2){
        return (d1 - d2) / (1000 * 60 * 60 * 24);
    }

    public static long betweenDay(String day1) {
        if (!StrUtils.isEmpty(day1)){
            long d1 = TimeUtils.strToMil(day1, TimeType.DAY_LINE, System.currentTimeMillis());
            return betweenDay(d1, System.currentTimeMillis());
        }
        return 0;
    }

    public static String calculateBetweenDay(String day1) {
        long betweenDay = betweenDay(day1);
        if (betweenDay >= 0) {
            return "剩余 " + betweenDay + " 天";
        }else {
            return "逾期 " + Math.abs(betweenDay) + " 天";
        }
    }
}