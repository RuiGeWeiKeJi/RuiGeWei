package com.huotu.scrm.web.controller.custom;

import com.huotu.scrm.common.utils.Constant;
import com.huotu.scrm.service.entity.custom.Custom;
import com.huotu.scrm.service.repository.ReportInfo.ReportInfoRepository;
import com.huotu.scrm.service.service.ReportInfo.ReportInfoService;
import com.huotu.scrm.service.service.bar.barService;
import com.huotu.scrm.service.service.customBrs.CustomBrsService;
import com.huotu.scrm.service.service.cutom.CustemInfoService;
import com.huotu.scrm.service.service.cutom.CustomService;
import com.huotu.scrm.web.GetUserInfo.GetUserLoginInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

@Controller
@RequestMapping("/")
public class CustomController {

    @Autowired
    private CustomService customService;

    @Autowired
    private barService barService;

    @Autowired
    private CustemInfoService custemInfoService;

    @Autowired
    private CustomBrsService customBrsService;

    @Autowired
    private ReportInfoService reportInfoService;

    /**
     * 分页获取客户资料
     *
     * @param pageNumber
     * @return
     */
    @RequestMapping(value = "/CusOp")
    public ModelAndView findAllByPagetable(
            @RequestParam(value = "pagenumber") Integer pageNumber
    ) {
        ModelAndView model = new ModelAndView();
        Pageable pageable = new PageRequest(pageNumber, 20);
        Page<Custom> custom = customService.findAllBy(pageable);


        model.addObject("total", custom.getTotalPages());
        model.addObject("totalCount", custom.getTotalElements());
        model.addObject("data", custom.getContent());
        model.setViewName("CusOp");
        return model;
    }

    /**
     * 客户资料查询
     *
     * @return
     */
    @RequestMapping(value = "/custemQuery")
    public ModelAndView findCustemQuery(
            HttpServletRequest request
    ) {
        List<String> industrylist = barService.findAllindustry();

        List<String> salesmanlist = new ArrayList<>();
        Constant.Position = "业务";
        String userName= GetUserLoginInfo.getUserInfo(request).getUSE002();
        if (GetUserLoginInfo.getUserListForRole(userName, reportInfoService))
            salesmanlist = barService.dindAllsalesman();
        else
            salesmanlist.add(userName);
        ModelAndView model = new ModelAndView();
        model.addObject("industry", industrylist);
        model.addObject("salesman", salesmanlist);
        model.setViewName("custemQuery");
        return model;
    }

    /**
     * 保存数据
     *
     * @param custom
     * @return
     */
    @RequestMapping(value = "/addcustom")
    @ResponseBody
    public boolean insertCustom(Custom custom) {
        if (custom.getCUS009() == "")
            custom.setCUS009(null);
        if (custom.getCUS011() == "")
            custom.setCUS011(null);
        custom.setCUS001(custom.getCUS001().trim());
        custom.setCUS002(custom.getCUS002().trim());
        custom.setCUS003(custom.getCUS003().trim());
        custom.setCUS004(custom.getCUS004().trim());
        custom.setCUS005(custom.getCUS005().trim());
        custom.setSalesman(custom.getSalesman().trim());
        custom.setIndustry(custom.getIndustry().trim());
        custom.setCUS013(custom.getCUS013().trim());
        custom.setCUS014(custom.getCUS014().trim());
        custom.setCUS016(custom.getCUS016().trim());
        custom.setCUS017(custom.getCUS017().trim());
        custom.setCUS018(custom.getCUS018().trim());
        boolean result = customService.existsCustomNameAndId(custom.getCUS002(), custom.getCUS001());
        if (result == false) {
            custom.setCUS018(customBrsService.getDateNow().toString());
            customService.insertCustom(custom);
        }
        return result;
    }

    /**
     * 获取客户资料编号
     *
     * @return
     */
    @RequestMapping(value = "/custemfindByCode")
    @ResponseBody //必须要有这个,如果没有,ajax异步请求不能就收到数据
    public Object findOneByCode() {
        Map<String,Object> map=new LinkedHashMap<>();
        String uid = customService.findOneByCode();
        Date date=customBrsService.getDateNow();
        map.put("uid",uid);
        map.put("date",date);
//        List<String> user=custemInfoService.findAllBy();
//        map.put("user",user);
        return map;
    }

    /**
     * 读取业务人员
     * @return
     */
    @RequestMapping(value = "/readcustomuser")
    @ResponseBody
    public Object readCustomUser(
            HttpServletRequest request
    ) {
//        List<String> user=custemInfoService.findAllBy();
        List<String> salesmanlist = new ArrayList<>();
        Constant.Position = "业务";
        String userName = GetUserLoginInfo.getUserInfo(request).getUSE002();
        if (GetUserLoginInfo.getUserListForRole(userName, reportInfoService))
            salesmanlist = barService.dindAllsalesman();
        else
            salesmanlist.add(userName);
        Map<Object, Object> map = new LinkedHashMap<>();
        map.put("salesman", salesmanlist);
        List<String> industrylist=barService.findAllindustry();
        map.put("industry",industrylist);
        return map;
    }

    /**
     * 模糊查询客户名称
     *
     * @param data
     * @return
     */
    @RequestMapping(value = "/findlike")
    @ResponseBody
    public Object findLike(
            @RequestParam("data") String data,
            @RequestParam("condition") String condition
    ) {
        try {
            URLDecoder.decode(data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Specification specification = addCondition(data,condition);
        List<Custom> names = customService.findName(specification);
        return names;
    }

    /**
     * 是否存在该客户
     * @param customName
     * @return
     */
    @RequestMapping(value = "/existscustom")
    @ResponseBody
    public Object existsCusInfo(
            @RequestParam("customName") String customName
    ){
        String customInfo=customService.existsCustomInfo(customName);
        Date date=customBrsService.getDateNow();
        Map<Object,Object> map=new LinkedHashMap<>();
        map.put("custominfo",customInfo);
        map.put("date",date);
        return map;
    }

    Specification addCondition(String data,String condition) {

        Specification<Custom> specification = new Specification<Custom>() {
            @Override
            public Predicate toPredicate(Root<Custom> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {

                ///定义条件对象列表
                List<Predicate> plist = new ArrayList<>();
                //单表条件对象构建(定区编码和地区关键字在分区表中)
                if (!StringUtils.isEmpty(data)) {
                    Predicate p1 = cb.like(root.get("CUS002"), "%" + data + "%");
                    plist.add(p1);
                }
                if (!StringUtils.isEmpty(condition)) {
                    Predicate p1 = cb.equal(root.get("CUS004"), condition);
                    plist.add(p1);
                }

                //pList转换为具体类型的数组
                Predicate[] predicate = plist.toArray(new Predicate[0]);
                //将条件进行汇总并返回
                return cb.and(predicate);
            }
        };

        return specification;
    }

}
