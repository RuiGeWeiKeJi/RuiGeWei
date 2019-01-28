package com.huotu.scrm.web.controller.custom;

import com.huotu.scrm.common.utils.Constant;
import com.huotu.scrm.service.entity.custom.Custom;
import com.huotu.scrm.service.service.cutom.CustemInfoService;
import org.eclipse.persistence.internal.oxm.schema.model.Content;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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

//import  org.springside.modules.persistence.DynamicSpecifications;


@Controller
@RequestMapping("/")
public class CustemInfoController {

    @Autowired
    private CustemInfoService custemInfoService;

    @RequestMapping("/main")
    @ResponseBody
    public ModelAndView findCustom() {
        //List<String> user=custemInfoService.findAllBy();
        ModelAndView model=new ModelAndView();
        model.setViewName("CusOp");
        //model.addObject("users",user);
        return model;
    }

    /**
     * 根据条件查询数据
     *
     * @param CUS004
     * @param industry
     * @param salesman
     * @param CUS013
     * @param CUS009start
     * @param CUS009end
     * @param CUS011start
     * @param CUS011end
     * @return
     */
    @RequestMapping(value = "/querycustom", method = RequestMethod.POST, produces = {"text/html;charset=UTF-8;", "application/json;"})
    @ResponseBody
    public Object queryCustomByWhere(
            @RequestParam("CUS002") String CUS002,
            @RequestParam("CUS004") String CUS004,
            @RequestParam("industry") String industry,
            @RequestParam("salesman") String salesman,
            @RequestParam("CUS013") String CUS013,
            @RequestParam("CUS009start") String CUS009start,
            @RequestParam("CUS009end") String CUS009end,
            @RequestParam("CUS011start") String CUS011start,
            @RequestParam("CUS011end") String CUS011end,
            @RequestParam("other") String other,
            @RequestParam("page") Integer pageIndex,
            @RequestParam("limit") Integer pageSize
    ) {

//        try {
//            URLDecoder.decode(CUS002,"UTF-8");
//            URLDecoder.decode(CUS004,"UTF-8");
//            URLDecoder.decode(industry,"UTF-8");
//            URLDecoder.decode(salesman,"UTF-8");
//            URLDecoder.decode(CUS013,"UTF-8");
//            URLDecoder.decode(CUS009start,"UTF-8");
//            URLDecoder.decode(CUS009end,"UTF-8");
//            URLDecoder.decode(CUS011start,"UTF-8");
//            URLDecoder.decode(CUS011end,"UTF-8");
//            URLDecoder.decode(other,"UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }

        
        Specification<Custom> specification = new Specification<Custom>() {
            @Override
            public Predicate toPredicate(Root<Custom> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {

                ///定义条件对象列表
                List<Predicate> plist = new ArrayList<>();
                //单表条件对象构建(定区编码和地区关键字在分区表中)
                if (!StringUtils.isEmpty(CUS002)) {
                    Predicate p1 = cb.equal(root.get("CUS002"), CUS002);
                    plist.add(p1);
                }
                if (!StringUtils.isEmpty(CUS004)) {
                    Predicate p1 = cb.equal(root.get("CUS004"), CUS004);
                    plist.add(p1);
                }
                if (!StringUtils.isEmpty(industry)) {
                    Predicate p1 = cb.equal(root.get("industry"), industry);
                    plist.add(p1);
                }
                if (!StringUtils.isEmpty(salesman)) {
                    Predicate p1 = cb.equal(root.get("salesman"), salesman);
                    plist.add(p1);
                }
                if (!StringUtils.isEmpty(CUS013)) {
                    Predicate p1 = cb.equal(root.get("CUS013"), CUS013);
                    plist.add(p1);
                }
                if (!StringUtils.isEmpty(CUS009start)) {
                    Predicate p2 = cb.greaterThanOrEqualTo(root.get("CUS009").as(String.class), CUS009start);
                    plist.add(p2);
                }
                if (!StringUtils.isEmpty(CUS009end)) {
                    Predicate p2 = cb.lessThanOrEqualTo(root.get("CUS009").as(String.class), CUS009end);
                    plist.add(p2);
                }
                if (!StringUtils.isEmpty(CUS011start)) {
                    Predicate p2 = cb.greaterThanOrEqualTo(root.get("CUS011").as(String.class), CUS011start);
                    plist.add(p2);
                }
                if (!StringUtils.isEmpty(CUS011end)) {
                    Predicate p2 = cb.lessThanOrEqualTo(root.get("CUS011").as(String.class), CUS011end);
                    plist.add(p2);
                }
                if(!StringUtils.isEmpty(Constant.userName) && !Constant.userName.equals("系统管理员")){
                    Predicate p2 = cb.equal(root.get("salesman").as(String.class), Constant.userName);
                    plist.add(p2);
                }

                //pList转换为具体类型的数组
                Predicate[] predicate = new Predicate[plist.size()];
                //将条件进行汇总并返回
                return cb.and(plist.toArray(predicate));
            }
        };

        Specification<Custom> specification1=new Specification<Custom>() {
            @Override
            public Predicate toPredicate(Root<Custom> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {

                ///定义条件对象列表
                List<Predicate> plist = new ArrayList<>();

                    CriteriaBuilder.In<Integer> in = cb.in(root.get("id"));
                    List<Integer> stringList = custemInfoService.findBy();
                    if (stringList != null && stringList.size() > 0) {
                        for (Integer s : stringList) {
                            if (s != null)
                                in.value(s);
                        }
                        plist.add(in);
                    }

                //pList转换为具体类型的数组
                Predicate[] predicate = new Predicate[plist.size()];
                //将条件进行汇总并返回
                return cb.and(plist.toArray(predicate));
            }
        };

        Sort sort = new Sort(Sort.Direction.DESC, "CUS001");
        Pageable pageable = new PageRequest(pageIndex - 1, pageSize, sort);
        Page<Custom> customPage = custemInfoService.findAllByWhere(  specification,specification1, pageable);

        Map<String, Object> datasource = new LinkedHashMap<String, Object>();
        datasource.put("code", 0);
        datasource.put("count", customPage.getTotalElements());
        datasource.put("data", customPage.getContent());
        return datasource;
    }

}