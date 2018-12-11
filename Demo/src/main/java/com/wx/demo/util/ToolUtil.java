package com.wx.demo.util;

import java.util.Calendar;

public class ToolUtil {

    /**
     * 计算两个日期时间戳相差的天数
     *
     * @param firTime
     * @param secTime
     * @return
     */
    public static boolean isSameDay(long firTime, long secTime) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTimeInMillis(firTime);
        cal2.setTimeInMillis(secTime);
        return cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR) &&
                cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
    }
}
