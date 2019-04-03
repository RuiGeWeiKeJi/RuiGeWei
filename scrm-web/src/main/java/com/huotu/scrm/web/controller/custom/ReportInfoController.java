package com.huotu.scrm.web.controller.custom;

import com.huotu.scrm.common.utils.Constant;
import com.huotu.scrm.service.model.customtrans;
import com.huotu.scrm.service.service.ReportInfo.ReportInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.huotu.scrm.web.GetUserInfo.GetUserLoginInfo.queryRoleForUser;


@Controller
@RequestMapping("/")
public class ReportInfoController {

    @Autowired
    private ReportInfoService reportInfoService;

    /**
     * 进入统计报表页面
     * @return
     */
    @RequestMapping(value = "/report")
    public  ModelAndView findPage() {
        ModelAndView model = new ModelAndView();
        model.setViewName("ReportInfo");
        return model;
    }

    /**
     * 根据登录人，获取沟通记录查询的权限人员信息
     * @return
     */
    @RequestMapping(value = "/linkUp")
    @ResponseBody
    public ModelAndView getListForBus(
    ){
        //获取cookie中的用户名和密码进行登录
        String username = Constant.userName;
Constant.Position="业务";
        ModelAndView modelAndView=new ModelAndView();

        List<String> getSales=new ArrayList<>();

        modelAndView= queryRoleForUser(username,modelAndView,getSales,reportInfoService);
        modelAndView.setViewName("ReportInfoQuery");
        return modelAndView;
}

    /**
     * 根据业务员和联系日期  查询近期此业务员的所有联系客户
     * @param CUS012
     * @param BRS003
     * @return
     */
    @RequestMapping(value = "/customtrans")
    @ResponseBody
    public Object getListForBusCustom(
            @RequestParam ("CUS012") String CUS012,
            @RequestParam ("BRS003") String BRS003,
            @RequestParam ("BRS0031") String BRS0031,
            @RequestParam("page") Integer pageIndex,
            @RequestParam("limit") Integer pageSize
    ){

        BRS003=BRS003+" 00:00:00";
        BRS0031=BRS0031+" 23:59:59";

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date timeOne=new Date(),timeTwo=new Date();
        try {
             timeOne=sdf.parse(BRS003);
             timeTwo=sdf.parse(BRS0031);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        List<Object> customtrans=reportInfoService.getInfoForsale(CUS012,timeOne,timeTwo,(pageIndex-1)*pageSize,pageSize);

        List<customtrans> customtransList=new ArrayList<>();

        for (int i=0;i<customtrans.size();i++){
            com.huotu.scrm.service.model.customtrans cus=new customtrans();
            Object[] objects =(Object[])customtrans.get(i);
            cus.setCUS001(objects[0].toString());
            cus.setCUS002(objects[1].toString());
            cus.setCUS004(objects[2].toString());
            cus.setBRS005(objects[3].toString());
            cus.setBRS006(objects[4].toString());
            cus.setBRS007(objects[5].toString());
            customtransList.add(cus);
        }

        Integer count=reportInfoService.getCountForsalePage(CUS012,timeOne,timeTwo);

        Map<String, Object> datasource = new LinkedHashMap<String, Object>();
        datasource.put("code", 0);
        datasource.put("count", count);
        datasource.put("data", customtransList);

        return datasource;
    }

}
