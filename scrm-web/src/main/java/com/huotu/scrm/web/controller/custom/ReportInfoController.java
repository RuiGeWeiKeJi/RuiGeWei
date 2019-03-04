package com.huotu.scrm.web.controller.custom;

import com.huotu.scrm.common.utils.Constant;
import com.huotu.scrm.service.entity.custom.Custom;
import com.huotu.scrm.service.model.customtrans;
import com.huotu.scrm.service.service.ReportInfo.ReportInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


import javax.persistence.Convert;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.*;

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

        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("ReportInfoQuery");

        List<String> getSales=new ArrayList<>();

        if(username=="")
            modelAndView.addObject("fail","fail");
        else
        {
            List<String> getRoleForUser=reportInfoService.getAllByROL002(username);
            if(getRoleForUser==null)
                modelAndView.addObject("fail","fail");
            else {
                if(getRoleForUser.contains("业务经理"))
                    getSales=reportInfoService.getSalesManForUser();
                else  if(getRoleForUser.contains("业务"))
                    getSales.add(username);
            }
            modelAndView.addObject("userSales",getSales);
        }
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
            @RequestParam("page") Integer pageIndex,
            @RequestParam("limit") Integer pageSize
    ){

        //Sort sort = new Sort(Sort.Direction.DESC, "CUS001");
        Pageable pageable = new PageRequest(pageIndex - 1, pageSize);

        int year=Integer.parseInt (BRS003.substring(0,4));
        int month=Integer.parseInt(BRS003.substring(5,7));

        Page<customtrans> customtransPage=reportInfoService.getInfoForsale(CUS012,year,month,pageable);

        Map<String, Object> datasource = new LinkedHashMap<String, Object>();
        datasource.put("code", 0);
        datasource.put("count", customtransPage.getTotalElements());
        datasource.put("data", customtransPage.getContent());
        return datasource;
    }

}
