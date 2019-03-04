package com.huotu.scrm.web.GetUserInfo;

import com.huotu.scrm.common.utils.Constant;

import javax.servlet.http.Cookie;

public class GetUserLoginInfo {

    /**
     * 从cookies中获取登录用户名和密码
     * @param cookies
     * @param username
     * @param password
     */
    public static void getUserLoginInfo( Cookie[] cookies,String username,String password){

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("username".equals(cookie.getName())) {
                    username = cookie.getValue();
                    Constant.userName=username;
                }
                if ("password".equals(cookie.getName())) {
                    password = cookie.getValue();
                }
            }
        }

    }

}
