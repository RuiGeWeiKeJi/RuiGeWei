package com.huotu.scrm.web.GetUserInfo;

import org.checkerframework.checker.units.qual.C;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateFormat {

    /**
     * 得到yyyy-MM-dd的日期字符串
     * @param date
     * @return
     */
    public static String getFormatForDate(Date date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = format.format(date);
        return dateStr;
    }

    /**
     * 增加多少天
     * @param date
     * @return
     */
    public static  String getFormatForAdd(Date date,Integer days){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar= Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,days);
        return format.format(calendar.getTime());
    }


}
