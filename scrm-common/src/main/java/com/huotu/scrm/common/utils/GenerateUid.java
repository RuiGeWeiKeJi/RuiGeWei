package com.huotu.scrm.common.utils;

import org.springframework.util.StringUtils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GenerateUid {

    private static String STR_FORMAT = "0000";
    private static String STR_FORMAT_CUS = "000000";

    /**
     * 获取编号
     *
     * @param uid
     * @return
     */
    public static String getUidForCus(String uid) {
        return getUidAll(uid, STR_FORMAT_CUS);
    }


    /**
     * 获取编号
     *
     * @param uid
     * @return
     */
    public static String getUid(String uid) {
        return getUidAll(uid, STR_FORMAT);
    }

   static String getUidAll(String uid, String speci) {
        if ("null".equals(uid) || StringUtils.isEmpty(uid)) {
            uid = 0 + "";
        }
        Integer uuid = Integer.parseInt(uid.trim());
        uuid++;
        DecimalFormat df = new DecimalFormat(speci);
        return df.format(uuid);
    }

    /**
     * 获取记录单单号
     *
     * @param uid
     * @param date
     * @return
     */
    public static String getOddNum(String uid, Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Long uuid = 0L;
        String uidResult = "";
        if ("null".equals(uid) || StringUtils.isEmpty(uid)) {
            uidResult = format.format(date) + "001";
        } else {
            if (uid.substring(0, 8).equals(format.format(date))) {
                uuid = Long.parseLong(uid.trim());
                uuid++;
                uidResult = uuid + "";
            } else {
                uidResult = format.format(date) + "001";
            }
        }
        return uidResult;
    }

}
