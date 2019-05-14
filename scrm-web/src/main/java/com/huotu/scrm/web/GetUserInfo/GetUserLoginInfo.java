package com.huotu.scrm.web.GetUserInfo;

import com.huotu.scrm.common.utils.Constant;
import com.huotu.scrm.service.entity.User.User;
import com.huotu.scrm.service.service.Maintain.MaintainService;
import com.huotu.scrm.service.service.ReportInfo.ReportInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class GetUserLoginInfo {

    /**
     * 从cookies中获取登录用户名和密码
     * @param cookies
     */
    public static String getUserLoginInfo( Cookie[] cookies,ModelAndView model) {

        String pass = "";
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("username".equals(cookie.getName())) {
                    model.addObject("username", cookie.getValue());
                }
                if ("password".equals(cookie.getName())) {
                    pass = cookie.getValue();
                    model.addObject("password", pass);
                }
            }
        }
        return pass;
    }

    /**
     * 获取登录人职位及权限  业务员
     * @param username
     * @param model
     * @param getSales
     */
    public static ModelAndView  queryRoleForUser(String username , ModelAndView model, List<String> getSales,ReportInfoService reportInfoService){
        if(username=="")
            model.addObject("fail","fail");
        else {
            if(getUserListForRole(username,reportInfoService))
                getSales=reportInfoService.getSalesManForUser();
            else
                getSales.add(username);
            model.addObject("userSales", getSales);
        }
        return model;
    }

    /**
     * 获取登录人角色权限 是否是经理角色
     * @param username
     * @param reportInfoService
     * @return
     */
    public static boolean getUserListForRole(String username,ReportInfoService reportInfoService){
        List<String> getRoleForUser=reportInfoService.getAllByROL002(username);
        if(getRoleForUser==null)
            return false;
        else {
            if(getRoleForUser.contains(Constant.Position+"经理"))
                return true;
            else  if(getRoleForUser.contains(Constant.Position))
                return false;
            else
                return false;
        }
    }

    /**
     * 获取技术角色权限
     * @param username
     * @param model
     * @param reportInfoService
     * @param maintainService
     * @return
     */
    public static List<String>  queryRoleForMainList(String username , ModelAndView model,ReportInfoService reportInfoService,MaintainService maintainService){
        List<String> mainList=new ArrayList<>();
        if(StringUtils.isEmpty(username))
            return null;
        else {
            if(getUserListForRole(username,reportInfoService))
                mainList=maintainService.getMainList();
            else
                mainList.add(username);
        }
        return mainList;
    }

    /**
     * 获取用户名等信息从session中
     * @param request
     * @return
     */
    public static User getUserInfo( HttpServletRequest request ){
        User user=(User)request.getSession().getAttribute("user");
        return user;
    }

}
