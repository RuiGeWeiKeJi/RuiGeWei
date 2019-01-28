package com.huotu.scrm.web.controller.custom;

import com.huotu.scrm.service.entity.custom.Custom;
import com.huotu.scrm.service.service.bar.barService;
import com.huotu.scrm.service.service.customBrs.CustomBrsService;
import com.huotu.scrm.service.service.cutom.CustemInfoService;
import com.huotu.scrm.service.service.cutom.CustomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
    public ModelAndView findCustemQuery() {
        List<String> industrylist = barService.findAllindustry();
        List<String> salesmanlist = barService.dindAllsalesman();
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
        map.put("uid",uid);
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
    public Object readCustomUser(){
        List<String> user=custemInfoService.findAllBy();
        return user;
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
            @RequestParam("data") String data
    ) {
        try {
            URLDecoder.decode(data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Specification specification = addCondition(data);
        List<Custom> names = customService.findName(specification);
        return names;
    }

    /**
     * 是否存在该客户
     * @param customId
     * @return
     */
    @RequestMapping(value = "/existscustom")
    @ResponseBody
    public Object existsCusInfo(
            @RequestParam("customId") String customId
    ){
        boolean result=customService.existsCustomInfo(customId);
        if(result)
            return "true";
        else
            return "false";
    }

    Specification addCondition(String data) {
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

                //pList转换为具体类型的数组
                Predicate[] predicate = plist.toArray(new Predicate[0]);
                //将条件进行汇总并返回
                return cb.and(predicate);
            }
        };

        return specification;
    }

}
