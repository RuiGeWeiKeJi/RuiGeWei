package com.huotu.scrm.web.controller.custom;

import com.google.gson.Gson;
import com.huotu.scrm.service.entity.CustomBrs.CustomBRS;
import com.huotu.scrm.service.entity.custom.Custom;
import com.huotu.scrm.service.service.customBrs.CustomBrsService;
import com.huotu.scrm.web.GetUserInfo.DateFormat;
import com.huotu.scrm.web.GetUserInfo.GetUserLoginInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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
public class CustomBrsController {

    @Autowired
    private CustomBrsService customBrsService;

    /**
     * 根据客户ID，获取单号
     *
     * @return
     */
    @RequestMapping(value = "/customGetId")
    public ModelAndView getId(
            @RequestParam(value = "customId") String customId
    ) {
        String uid = customBrsService.getId();
        Date date = customBrsService.getDateNow();
        List<String> allUse = customBrsService.findAllByCustomId(customId);
        ModelAndView model = new ModelAndView();
        model.addObject("data", uid);
        model.addObject("date", date);
        model.addObject("all", allUse);
        model.setViewName("ConAdd");
        return model;
    }

    /**
     * 刷新记录单数据
     *
     * @return
     */
    @Transactional(rollbackFor=Exception.class)
    @RequestMapping(value = "/customBrsFind", produces = {"text/html;charset=UTF-8;", "application/json;"})
    @ResponseBody  //返回的数据类型是json
    public Object findCon(
            @RequestParam("customId") String customId,
            @RequestParam("oddNum") String oddNum,
            @RequestParam("BRS007") String BRS007,
            @RequestParam("BRS005") String BRS005,
            @RequestParam("BRS003") String BRS003,
            @RequestParam("BRS004") String BRS004,
            @RequestParam("BRS006") String BRS006,
            @RequestParam("id") Integer id,
            @RequestParam("page") Integer pageIndex,
            @RequestParam("limit") Integer pageSize,
            HttpServletRequest request
    ) {
        try {
            URLDecoder.decode(BRS007,"UTF-8");
            URLDecoder.decode(BRS005,"UTF-8");
            URLDecoder.decode(BRS006,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        CustomBRS customBRS = new CustomBRS();
        customBRS.setId(id);
        customBRS.setOddNum(oddNum);
        customBRS.setCustomId(customId);
        customBRS.setBRS003(DateFormat.getFormatForString(BRS003));
        customBRS.setBRS004(DateFormat.getFormatForString(BRS004));
        customBRS.setBRS005(BRS005);
        customBRS.setBRS006(BRS006);
        customBRS.setBRS007(BRS007);
        String username = GetUserLoginInfo.getUserInfo(request).getUSE002();

        customBRS.setBRS008(username);
        Custom custom=new Custom();
        custom.setCUS001(customId);

        custom.setCUS009(BRS003);
        custom.setCUS011(BRS004);

        customBrsService.insertCustomUse(customBRS);
        customBrsService.updateCustom(custom);

        Specification specification=  addCondition(customId);

        Sort sort = new Sort(Sort.Direction.DESC, "oddNum");
        Pageable pageable = new PageRequest(pageIndex-1, pageSize, sort);
        Page<CustomBRS> customBRSPage = customBrsService.findByCustomId(specification, pageable);
        Map<String, Object> datasource = new LinkedHashMap<String, Object>();
        datasource.put("code", 0);
        datasource.put("count", customBRSPage.getTotalElements());
        datasource.put("data", customBRSPage.getContent());
        return datasource;
    }

    /**
     * 保存联系记录单数据
     *
     * @param customBRS
     * @return
     */
    @RequestMapping(value = "/custombrsave")
    @ResponseBody
    public String saveCon(CustomBRS customBRS) {
        customBrsService.insertCustomUse(customBRS);
        Gson gson = new Gson();
        String s = gson.toJson(customBRS);
        return s;
    }


    /**
     * 获取客户资料联系单数据
     *
     * @param customId
     * @return
     */
    @RequestMapping(value = "/readcuscon")
    @ResponseBody
    public Object readCusInfoByContact(
            @RequestParam("customId") String customId,
            @RequestParam("page") Integer pageIndex,
            @RequestParam("limit") Integer pageSize
    ) {
        Specification specification=  addCondition(customId);
        Sort sort = new Sort(Sort.Direction.DESC, "oddNum");
        Pageable pageable = new PageRequest(pageIndex - 1, pageSize, sort);
        Page<CustomBRS> customBRSList = customBrsService.readCusInfoByCon(specification, pageable);
        Map<String, Object> datasource = new LinkedHashMap<String, Object>();
        datasource.put("code", 0);
        datasource.put("count", customBRSList.getTotalElements());
        datasource.put("data", customBRSList.getContent());
        return datasource;
    }

    /**
     * 删除一条客户联系单记录
     *
     * @param oddNum
     * @return
     */
    @RequestMapping(value = "/cusbrsDelete")
    @ResponseBody
    public String deleteOne(
            @RequestParam("oddNum") String oddNum
    ) {
        customBrsService.deleteOneByOddNum(oddNum);
        String result = "success";
        return result;
    }

    Specification addCondition(String customId){
        Specification<CustomBRS> specification = new Specification<CustomBRS>() {
            @Override
            public Predicate toPredicate(Root<CustomBRS> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {

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

}





