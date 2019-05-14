package com.huotu.scrm.web.GetUserInfo;

import org.springframework.util.StringUtils;

public class StringSplitAndCom {

    /**
     * 拆分之后缝合
     * @param strs
     * @return
     */
    public static String getStrs(String strs){
        if(!StringUtils.isEmpty(strs)) {
            if (strs.contains(",")) {
                String resultStr = "";
                String[] strings = strs.split(",");
                for (String s : strings) {
                    if (StringUtils.isEmpty(resultStr))
                        resultStr = "'" + s + "'";
                    else
                        resultStr = resultStr + "," + "'" + s + "'";
                }
                return resultStr;
            } else if (strs.equals("null"))
                return strs;
            else
                return "'" + strs + "'";
        }
        else
            return strs;
    }



}
