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

    public static String[] getStrSplit (String strs){
        String[] strings ={};
        if(!StringUtils.isEmpty(strs)) {
            if (strs.contains(",")) {
                strings = strs.split(",");
                return  strings;
            }
            else {
                strings=new String[1];
                strings[0]=strs;
                return strings;
            }
        }
        else
            return null;
    }


}
