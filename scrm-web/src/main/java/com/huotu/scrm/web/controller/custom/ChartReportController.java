package com.huotu.scrm.web.controller.custom;

import com.huotu.scrm.common.utils.Constant;
import com.huotu.scrm.service.model.avgbrs;
import com.huotu.scrm.service.model.everybrs;
import com.huotu.scrm.service.model.increasebrs;
import com.huotu.scrm.service.service.ChartInfo.ChartInfoService;
import com.huotu.scrm.service.service.ReportInfo.ReportInfoService;
import com.huotu.scrm.web.GetUserInfo.GetUserLoginInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static com.huotu.scrm.web.GetUserInfo.GetUserLoginInfo.queryRoleForUser;


@Controller
@RequestMapping
public class ChartReportController {

    @Autowired
    ChartInfoService chartInfoService;

    @Autowired
    private ReportInfoService reportInfoService;

    @RequestMapping(value = "/chart")
    @ResponseBody
    public ModelAndView findChartInfo(HttpServletRequest request) {

        ModelAndView model = new ModelAndView();
        //获取cookie中的用户名和密码进行登录
        String username = GetUserLoginInfo.getUserInfo(request).getUSE002();
        Constant.Position="业务";
        List<String> getSales = new ArrayList<>();

        model = queryRoleForUser(username, model, getSales, reportInfoService);

        getSales=( List<String> )model.getModel().get("userSales");

        if(getSales.size()>0) {
            if(!getSales.contains(username))
                username=getSales.get(0);
        }
        getResult(model,username);
        model.setViewName("ChartRepot");
        return model;
    }

    /**
     * 查找本月电话
     * @param username
     * @return
     */
    @RequestMapping(value = "/chartuser")
    @ResponseBody
    public Object fincChartInfoForUser(@RequestParam("username") String username){
        ModelAndView model = new ModelAndView();
        Map<Object,Object> map= getResult(model,username);
        return  map;
    }

     Map<Object,Object>  getResult(ModelAndView model,String username){
        Map<Object,Object> map=new LinkedHashMap<>();
        Date date = chartInfoService.getDate();
        List<avgbrs> getCount = chartInfoService.getCount(date,username);
        List<everybrs> getCountEvery = chartInfoService.getCountEveryDay(date,username);
        List<Object> getIncreasebrs=chartInfoService.getincreasebrs();

        List<increasebrs> increasebrs=new ArrayList<>();
        for(int i=0;i<getIncreasebrs.size();i++) {
            increasebrs increasebrs1 = new increasebrs();
            Object[] objects = (Object[]) getIncreasebrs.get(i);
            increasebrs1.setUsername(objects[0].toString());
            increasebrs1.setLev(objects[1].toString());
            increasebrs1.setPremonth(Integer.parseInt(objects[2].toString()));
            increasebrs1.setMonthadd(Integer.parseInt(objects[3].toString()));
            increasebrs1.setPreweek(Integer.parseInt(objects[4].toString()));
            increasebrs1.setWeekadd(Integer.parseInt(objects[5].toString()));
            increasebrs1.setPreday(Integer.parseInt(objects[6].toString()));
            increasebrs1.setDayadd(Integer.parseInt(objects[7].toString()));
            increasebrs.add(increasebrs1);
        }

        map.put("avg",getCount);
        map.put("every",getCountEvery);
        map.put("code",0);
        map.put("count",increasebrs.size());
        map.put("data",increasebrs);
        model.addObject("avg", getCount);
        model.addObject("every", getCountEvery);
        model.addObject("increase", getIncreasebrs);
        return map;
    }

}
