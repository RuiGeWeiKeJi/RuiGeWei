package com.huotu.scrm.web.controller.custom;

import com.huotu.scrm.service.entity.User.User;
import com.huotu.scrm.service.entity.custom.Custom;
import com.huotu.scrm.service.service.bar.barService;
import com.huotu.scrm.service.service.user.UseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
public class BarController {

    @Autowired
    private UseService useService;

    @Autowired
    private barService barService;

    @RequestMapping(value = "/left")
    public ModelAndView leftCustom() {
        List<String> industrylist=barService.findAllindustry();
        List<String> salesmanlist=barService.dindAllsalesman();
        ModelAndView model=new ModelAndView();
        model.addObject("industry",industrylist);
        model.addObject("salesman",salesmanlist);
        model.setViewName("CustemInfo");
        return model;
    }


    /**
     * 查找所有人员信息
     * @return
     */
    @RequestMapping(value = "/user")
    public String useInfo(){
        return "UserInfo";
    }

    /**
     * 查找所有人员信息
     * @return
     */
    @RequestMapping(value = "/readUse")
    @ResponseBody
    public Object readUseInfo(){
        List<User> userList=useService.findAllBy();
        Map<String, Object> datasource = new LinkedHashMap<String, Object>();
        datasource.put("code", 0);
        datasource.put("count", userList.size());
        datasource.put("data", userList);
        return datasource;
    }

}
