package com.huotu.scrm.web.GetUserInfo;

import com.huotu.scrm.service.service.Maintain.MainService;
import com.huotu.scrm.service.service.ReportInfo.ReportInfoService;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

public class MainUtils {

    public static ModelAndView getEdit(String data, HttpServletRequest request, ReportInfoService reportInfoService, MainService maintainService){
        try {
            URLDecoder.decode(data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        ModelAndView modelAndView=new ModelAndView();
        String userName= GetUserLoginInfo.getUserInfo(request).getUSE002();
        List<String> mainList = GetUserLoginInfo.queryRoleForMainList(userName, modelAndView, reportInfoService, maintainService);
        modelAndView.setViewName("MaintainAdd");
        modelAndView.addObject("mainList", mainList);
        List<String> getUserInfo = maintainService.getUserInfo(data);
        modelAndView.addObject("getsernfo", getUserInfo);
        return modelAndView;
    }

    public static ModelAndView getQuery( HttpServletRequest request, ReportInfoService reportInfoService, MainService maintainService){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("MaintainQuery");
        String userName= GetUserLoginInfo.getUserInfo(request).getUSE002();
        List<String> mainList = GetUserLoginInfo.queryRoleForMainList(userName, modelAndView, reportInfoService, maintainService);
        modelAndView.addObject("mainList", mainList);
        return modelAndView;
    }

}
