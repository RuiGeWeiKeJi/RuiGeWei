package com.huotu.scrm.web.controller.custom;

import com.huotu.scrm.service.entity.CustomBrs.CustomBRS;
import com.huotu.scrm.service.entity.customUse.CustomUse;
import com.huotu.scrm.service.service.customUse.CustomUseService;
import com.sun.org.apache.bcel.internal.generic.RETURN;
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
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/")
public class CustomUseController {

    @Autowired
    private CustomUseService customUseService;

    /**
     * 分页获取客户联系人
     *
     * @param customId
     * @return
     */
    @RequestMapping(value = "/Contacts")
    @ResponseBody
    public Object findAllByCustomCode(
            @RequestParam(value = "customId") String customId,
            @RequestParam(value = "page") Integer pageIndex,
            @RequestParam(value = "limit") Integer pageSize
    ) {
        Specification specification = addCondition(customId);
        return datasource(pageIndex, pageSize, specification);
    }

    /**
     * 跳转到联系人新增页面
     *
     * @return
     */
    @RequestMapping(value = "/findContacts")
    public ModelAndView findContacts(
            @RequestParam("customId") String customId
    ) {
        ModelAndView model=new ModelAndView();
        model.setViewName("Contacts");
        model.addObject("customId",customId);
        return model;
    }

    /**
     * 获取客户编号对应的人员编号
     *
     * @param customId
     * @return
     */
    @RequestMapping(value = "/customUsefindById")
    @ResponseBody //必须要有这个,如果没有,ajax异步请求不能就收到数据
    public String getUserId(
            @RequestParam(value = "customId") String customId
    ) {
        String userId = customUseService.getUserId(customId);
        return userId;
    }


    /**
     * 保存数据
     *
     * @return
     */
    @RequestMapping(value = "/customUseSave")
    @ResponseBody
    //@ResponseBody //必须要有这个,如果没有,ajax异步请求不能就收到数据
    public Object saveCustomUse(
            @RequestParam(value = "customId") String customId,
            @RequestParam(value = "userId") String userId,
            @RequestParam(value = "CUR002") String CUR002,
            @RequestParam(value = "CUR003") String CUR003,
            @RequestParam(value = "CUR004") String CUR004,
            @RequestParam(value = "CUR005") String CUR005,
            @RequestParam(value = "CUR006") String CUR006,
            @RequestParam(value = "CUR007") String CUR007,
            @RequestParam(value = "CUR008") Integer CUR008,
            @RequestParam(value = "CUR009") String CUR009,
            @RequestParam(value = "id") Integer id,
            @RequestParam(value = "page") Integer pageIndex,
            @RequestParam(value = "limit") Integer pageSize) {

        try {
            URLDecoder.decode(CUR002,"UTF-8");
            URLDecoder.decode(CUR003,"UTF-8");
            URLDecoder.decode(CUR004,"UTF-8");
            URLDecoder.decode(CUR005,"UTF-8");
            URLDecoder.decode(CUR006,"UTF-8");
            URLDecoder.decode(CUR007,"UTF-8");
            URLDecoder.decode(CUR009,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        CustomUse customUse = new CustomUse();
        customUse.setId(id);
        customUse.setCustomId(customId);
        customUse.setUserId(userId);
        customUse.setCUR002(CUR002);
        customUse.setCUR003(CUR003);
        customUse.setCUR004(CUR004);
        customUse.setCUR005(CUR005);
        customUse.setCUR006(CUR006);
        customUse.setCUR007(CUR007);
        customUse.setCUR008(CUR008);
        customUse.setCUR009(CUR009);
        customUseService.insertCustomUse(customUse);

        Specification specification = addCondition(customUse.getCustomId());
        return datasource(pageIndex, pageSize, specification);
    }

    /**
     * 删除联系人
     *
     * @param customId
     * @param userId
     * @return
     */
    @RequestMapping(value = "/customUseDelete")
    @ResponseBody //必须要有这个,如果没有,ajax异步请求不能就收到数据
    public Object deleteBycustomIdAnduserId(
            @RequestParam(value = "customId") String customId,
            @RequestParam(value = "userId") String userId,
            @RequestParam(value = "page") Integer pageIndex,
            @RequestParam(value = "limit") Integer pageSize
    ) {
        Specification specification = addCondition(customId,userId);
        CustomUse customUse = customUseService.findOneByCustomIdAndUserId(specification);
        customUseService.deleteByCustomIdAndUserId(customUse);

        specification = addCondition(customUse.getCustomId());
        return datasource(pageIndex, pageSize, specification);
    }

    Specification addCondition(String customId) {
        Specification<CustomUse> specification = new Specification<CustomUse>() {
            @Override
            public Predicate toPredicate(Root<CustomUse> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {

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

    Specification addCondition(String customId,String userId) {
        Specification<CustomUse> specification = new Specification<CustomUse>() {
            @Override
            public Predicate toPredicate(Root<CustomUse> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {

                ///定义条件对象列表
                List<Predicate> plist = new ArrayList<>();
                //单表条件对象构建(定区编码和地区关键字在分区表中)
                if (!StringUtils.isEmpty(customId)) {
                    Predicate p1 = cb.equal(root.get("customId"), customId);
                    plist.add(p1);
                }
                if (!StringUtils.isEmpty(userId)) {
                    Predicate p1 = cb.equal(root.get("userId"), userId);
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
        Sort sort = new Sort(Sort.Direction.ASC, "userId");
        Pageable pageable = new PageRequest(pageIndex - 1, pageSize, sort);
        Page<CustomUse> customUsePage = customUseService.findBy(specification, pageable);
        Map<String, Object> datasource = new LinkedHashMap<String, Object>();
        datasource.put("code", 0);
        datasource.put("count", customUsePage.getTotalElements());
        datasource.put("data", customUsePage.getContent());
        return datasource;
    }

}
