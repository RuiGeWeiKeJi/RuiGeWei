package com.huotu.scrm.web.GetUserInfo;

import java.text.ParsePosition;
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

    public static String getFormatForDate(String date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        long lt = new Long(date);
        Date da = new Date(lt);
        String dateStr = format.format(da);
        return dateStr;
    }

    public static  String getFormatForDateyyyyMM (Date date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
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

    /**
     * 字符串转为日期
     * @param str
     * @return
     */
    public static Date getFormatForString (String str){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(str, pos);
        return strtodate;
    }


}
