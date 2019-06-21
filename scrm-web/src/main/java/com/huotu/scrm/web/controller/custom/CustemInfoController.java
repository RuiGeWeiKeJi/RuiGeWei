package com.huotu.scrm.web.controller.custom;

import com.google.common.collect.Sets;
import com.huotu.scrm.common.utils.Constant;
import com.huotu.scrm.service.entity.custom.Custom;
import com.huotu.scrm.service.service.ReportInfo.ReportInfoService;
import com.huotu.scrm.service.service.cutom.CustemInfoService;
import com.huotu.scrm.web.GetUserInfo.GetUserLoginInfo;
import com.huotu.scrm.web.GetUserInfo.StringSplitAndCom;
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
import javax.servlet.http.HttpServletRequest;
import java.util.*;

//import  org.springside.modules.persistence.DynamicSpecifications;


@Controller
@RequestMapping("/")
public class CustemInfoController {

    @Autowired
    private CustemInfoService custemInfoService;

    @Autowired
    private ReportInfoService reportInfoService;

    Boolean roleUser=false;

    @RequestMapping("/main")
    @ResponseBody
    public ModelAndView findCustom() {
        //List<String> user=custemInfoService.findAllBy();
        ModelAndView model = new ModelAndView();
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
            @RequestParam("limit") Integer pageSize,
            HttpServletRequest request
    ) {

        String userName= GetUserLoginInfo.getUserInfo(request).getUSE002();
        roleUser=false;
        Constant.Position = "业务";
        if (GetUserLoginInfo.getUserListForRole(userName, reportInfoService) )
            roleUser=true;

        Specification<Custom> specification = new Specification<Custom>() {
            @Override
            public Predicate toPredicate(Root<Custom> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {

                //定义条件对象列表
                List<Predicate> plist = new ArrayList<>();
                //单表条件对象构建(定区编码和地区关键字在分区表中)
                String[] strSplit = {};
                if (!StringUtils.isEmpty(CUS002)) {
                    Predicate p1 = cb.equal(root.get("CUS002"), CUS002);
                    plist.add(p1);
                }
                if (!StringUtils.isEmpty(CUS004)  && !CUS004.equals("null")) {
                    strSplit= StringSplitAndCom.getStrSplit(CUS004);
                    if(strSplit!=null) {
                        CriteriaBuilder.In<String> in=cb.in(root.get("CUS004").as(String.class));
                        for(String i :strSplit){
                            in.value(i);
                        }
                        plist.add(in);
                    }
                }
                if (!StringUtils.isEmpty(industry) && !industry.equals("null")) {
                    strSplit= StringSplitAndCom.getStrSplit(industry);
                    if(strSplit!=null) {
                        CriteriaBuilder.In<String> in=cb.in(root.get("industry").as(String.class));
                        for(String i :strSplit){
                            in.value(i);
                        }
                        plist.add(in);
                    }
                }
                if (!StringUtils.isEmpty(salesman) && !salesman.equals("null")) {
                    strSplit= StringSplitAndCom.getStrSplit(salesman);
                    if(strSplit!=null) {
                        CriteriaBuilder.In<String> in=cb.in(root.get("salesman").as(String.class));
                        for(String i :strSplit){
                            in.value(i);
                        }
                        plist.add(in);
                    }
                }
                if (!StringUtils.isEmpty(CUS013) && !CUS013.equals("null")) {
                    strSplit= StringSplitAndCom.getStrSplit(CUS013);
                    if(strSplit!=null) {
                        CriteriaBuilder.In<String> in=cb.in(root.get("CUS013").as(String.class));
                        for(String i :strSplit){
                            in.value(i);
                        }
                        plist.add(in);
                    }
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
                if (!StringUtils.isEmpty(userName) && !userName.equals("系统管理员") && roleUser==false && !salesman.equals(("无"))) {
                    Predicate p2 = cb.equal(root.get("salesman").as(String.class), userName);
                    plist.add(p2);
                }
                if("1".equals(other)){
                    List<Integer> stringList = custemInfoService.findBy();
                    if(stringList!=null && stringList.size()>0){
                      CriteriaBuilder.In<Integer> in=cb.in(root.get("id").as(Integer.class));
                      for(Integer i :stringList){
                          in.value(i);
                      }
                      plist.add(in);
                    }
                }

                //pList转换为具体类型的数组
                Predicate[] predicate = new Predicate[plist.size()];
                //将条件进行汇总并返回
                return cb.and(plist.toArray(predicate));
            }

        };

        Sort sort = new Sort(Sort.Direction.DESC, "CUS001");
        Pageable pageable = new PageRequest(pageIndex - 1, pageSize, sort);

        Page<Custom> customPage=custemInfoService.findAllByWhere(specification, pageable);

        Map<String, Object> datasource = new LinkedHashMap<String, Object>();
        datasource.put("code", 0);
        datasource.put("count", customPage.getTotalElements());
        datasource.put("data", customPage.getContent());
        return datasource;
    }

}