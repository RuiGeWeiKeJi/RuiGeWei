package com.huotu.scrm.web.controller.custom;

import com.huotu.scrm.service.entity.customSRS.CustomSRS;
import com.huotu.scrm.service.service.customSrs.CustomSrsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/")
public class CustomSrsController {

    @Autowired
    private CustomSrsService customSrsService;

    /**
     * 跳转到客户维护记录单
     *
     * @return
     */
    @RequestMapping(value = "/mainfind")
    public ModelAndView findMaincustom(
            @RequestParam("customId") String customId
    ) {
        String uid = customSrsService.findByCode();
        Date date = customSrsService.getDateNow();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<String> allUse = customSrsService.findByCustomId(customId);
        ModelAndView model = new ModelAndView();
        model.setViewName("MainCustom");
        model.addObject("uid", uid);
        model.addObject("date", format.format(date));
        model.addObject("allUse", allUse);
        model.addObject("customId", customId);
        return model;
    }

    /**
     * 根据客户资料获取客户维护记录单
     *
     * @param customId
     * @return
     */
    @RequestMapping(value = "/maintable")
    @ResponseBody
    public Object getTable(
            @RequestParam("page") Integer pageIndex,
            @RequestParam("limit") Integer pageSize,
            @RequestParam("customId") String customId
    ) {
        Specification specification = addCondition(customId);
        return datasource(pageIndex, pageSize, specification);
    }

    /**
     * 保存数据
     *
     * @return
     */
    @RequestMapping("/mainsave")
    @ResponseBody
    public Object insertCusSrs(
            @RequestParam("id") Integer id,
            @RequestParam("customId") String customId,
            @RequestParam("oddNum") String oddNum,
            @RequestParam("SRS004") String SRS004,
            @RequestParam("SRS005") String SRS005,
            @RequestParam("SRS006") String SRS006,
            @RequestParam("SRS007") String SRS007,
            @RequestParam("conDate") String conDate,
            @RequestParam("page") Integer pageIndex,
            @RequestParam("limit") Integer pageSize
    ) {
        try {
            URLDecoder.decode(SRS004,"UTF-8");
            URLDecoder.decode(SRS005,"UTF-8");
            URLDecoder.decode(SRS006,"UTF-8");
            URLDecoder.decode(SRS007,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        CustomSRS customSRS=new CustomSRS();
        customSRS.setId(id);
        customSRS.setCustomId(customId);
        customSRS.setOddNum(oddNum);
        customSRS.setSRS004(SRS004);
        customSRS.setSRS005(SRS005);
        customSRS.setSRS006(SRS006);
        customSRS.setSRS007(SRS007);
        customSRS.setConDate(conDate);
        customSrsService.insertCusSrs(customSRS);
        Specification specification = addCondition(customId);
        return datasource(pageIndex, pageSize, specification);
    }

    /**
     * 删除维护记录单数据
     * @return
     */
    @RequestMapping(value = "/deletesrs")
    @ResponseBody
    public Object deleteCustomsrs(
            @RequestParam("customId") String customId,
            @RequestParam("oddNum") String oddNum,
            @RequestParam("page") Integer pageIndex,
            @RequestParam("limit") Integer pageSize
    ) {
        Specification specification=addCondition(customId,oddNum);
        CustomSRS customSRSList=customSrsService.findAllCustomsrs(specification);
        customSrsService.deleteCustomSrs(customSRSList);
        specification = addCondition(customId);
        return datasource(pageIndex, pageSize, specification);
    }

    /**
     * 根据客户资料id获取所有维护单记录
     *
     * @return
     */
    @RequestMapping(value = "/mainadd")
    @ResponseBody
    public CustomSRS addNew() {
        String uid = customSrsService.findByCode();
        Date date = customSrsService.getDateNow();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CustomSRS customSRS = new CustomSRS();
        customSRS.setOddNum(uid);
        customSRS.setConDate(format.format(date));
        return customSRS;
    }

    Specification addCondition(String customId) {
        Specification<CustomSRS> specification = new Specification<CustomSRS>() {
            @Override
            public Predicate toPredicate(Root<CustomSRS> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {

                ///定义条件对象列表
                List<Predicate> plist = new ArrayList<>();
                //单表条件对象构建(定区编码和地区关键字在分区表中)
                if (!StringUtils.isEmpty(customId)) {
                    Predicate p1 = cb.equal(root.get("customId"), customId);
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

    Specification addCondition(String customId,String oddNum) {
        Specification<CustomSRS> specification = new Specification<CustomSRS>() {
            @Override
            public Predicate toPredicate(Root<CustomSRS> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {

                ///定义条件对象列表
                List<Predicate> plist = new ArrayList<>();
                //单表条件对象构建(定区编码和地区关键字在分区表中)
                if (!StringUtils.isEmpty(customId)) {
                    Predicate p1 = cb.equal(root.get("customId"), customId);
                    plist.add(p1);
                }
                if (!StringUtils.isEmpty(oddNum)) {
                    Predicate p1 = cb.equal(root.get("oddNum"), oddNum);
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

    Map<String, Object> datasource(int pageIndex, int pageSize, Specification specification) {
        Sort sort = new Sort(Sort.Direction.ASC, "oddNum");
        Pageable pageable = new PageRequest(pageIndex - 1, pageSize, sort);
        Page<CustomSRS> customUsePage = customSrsService.findAllByCustomId(specification, pageable);
        Map<String, Object> datasource = new LinkedHashMap<String, Object>();
        datasource.put("code", 0);
        datasource.put("count", customUsePage.getTotalElements());
        datasource.put("data", customUsePage.getContent());
        return datasource;
    }


}
