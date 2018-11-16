package com.huotu.scrm.web.controller.custom;

import com.huotu.scrm.service.entity.Power.PowerRole;
import com.huotu.scrm.service.entity.Power.UserRole;
import com.huotu.scrm.service.entity.User.User;
import com.huotu.scrm.service.entity.customUse.CustomUse;
import com.huotu.scrm.service.service.Power.PowerService;
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
import javax.xml.parsers.SAXParser;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
public class PowerController {

    @Autowired
    private PowerService powerService;

    /**
     * 权限控制
     *
     * @return
     */
    @RequestMapping("/power")
    public String findPower() {
        return "Power";
    }

    /**
     * 查询权限
     *
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @RequestMapping("/powerfind")
    @ResponseBody
    public Object findAllPower(
            @RequestParam("page") Integer pageIndex,
            @RequestParam("limit") Integer pageSize
    ) {
        return datasource(pageIndex, pageSize);
    }

    /**
     * 查询角色
     *
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @RequestMapping("/powerrolefind")
    @ResponseBody
    public Object findAllRole(
            @RequestParam("page") Integer pageIndex,
            @RequestParam("limit") Integer pageSize) {

        return rolesource(pageIndex, pageSize);
    }

    /**
     * 权限新增
     *
     * @return
     */
    @RequestMapping("/addpow")
    @ResponseBody
    public ModelAndView addpow() {
        String uid = powerService.findByCode();
        ModelAndView model = new ModelAndView();
        model.addObject("uid", uid);
        model.setViewName("PowAdd");
        return model;
    }

    /**
     * 角色新增
     *
     * @return
     */
    @RequestMapping("/addrole")
    @ResponseBody
    public ModelAndView addrole() {
        String uid = powerService.findByCodeRole();
        List<User> userList = powerService.findAllUseRole();
        ModelAndView model = new ModelAndView();
        model.addObject("uid", uid);
        model.addObject("user", userList);
        model.setViewName("RoleAdd");
        return model;
    }


    /**
     * 编辑权限
     *
     * @return
     */
    @RequestMapping("/editpow")
    public String editpow() {
        return "PowAdd";
    }

    /**
     * 编辑角色
     *
     * @return
     */
    @RequestMapping("/editrole")
    @ResponseBody
    public ModelAndView editrole() {
        List<User> userList = powerService.findAllUseRole();
        ModelAndView model = new ModelAndView();
        model.addObject("user", userList);
        model.setViewName("RoleAdd");
        return model;
    }

    /**
     * 删除权限
     *
     * @param oddNum
     * @return
     */
    @RequestMapping("/deletepow")
    @ResponseBody
    public String deletepow(
            @RequestParam("oddNum") String oddNum

    ) {
        powerService.deleteByOddNum(oddNum);
        String result = "success";
        return result;
    }

    /**
     * 删除角色
     *
     * @param oddNum
     * @return
     */
    @RequestMapping("/deleterole")
    @ResponseBody
    public String deleterole(
            @RequestParam("oddNum") String oddNum

    ) {
        powerService.deleteByOddNumRole(oddNum);
        String result = "success";
        return result;
    }

    /**
     * 保存权限
     *
     * @param POW001
     * @param POW002
     * @param POW003
     * @param POW004
     * @param POW005
     * @param POW006
     * @param POW007
     * @param oddNum
     * @param id
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @RequestMapping("/savepow")
    @ResponseBody
    public Object savepow(
            @RequestParam("POW001") String POW001,
            @RequestParam("POW002") String POW002,
            @RequestParam("POW003") boolean POW003,
            @RequestParam("POW004") boolean POW004,
            @RequestParam("POW005") boolean POW005,
            @RequestParam("POW006") boolean POW006,
            @RequestParam("POW007") boolean POW007,
            @RequestParam("oddNum") String oddNum,
            @RequestParam("id") Integer id,
            @RequestParam("page") Integer pageIndex,
            @RequestParam("limit") Integer pageSize
    ) {
        try {
            URLDecoder.decode(POW001,"UTF-8");
            URLDecoder.decode(POW002,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        PowerRole powerRole = new PowerRole();
        powerRole.setId(id);
        powerRole.setPOW001(POW001);
        powerRole.setPOW002(POW002);
        powerRole.setPOW003(POW003);
        powerRole.setPOW004(POW004);
        powerRole.setPOW005(POW005);
        powerRole.setPOW006(POW006);
        powerRole.setPOW007(POW007);
        powerRole.setOddNum(oddNum);

        powerService.insertPowerRole(powerRole);

        return datasource(pageIndex, pageSize);
    }

    /**
     * 保存角色
     *
     * @param oddNum
     * @param userId
     * @param ROL002
     * @param id
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @RequestMapping("/saverole")
    @ResponseBody
    public Object saverole(
            @RequestParam("oddNum") String oddNum,
            @RequestParam("userId") String userId,
            @RequestParam("ROL002") String ROL002,
            @RequestParam("ROL003") String ROL003,
            @RequestParam("id") Integer id,
            @RequestParam("page") Integer pageIndex,
            @RequestParam("limit") Integer pageSize
    ) {
        try {
            URLDecoder.decode(ROL002,"UTF-8");
            URLDecoder.decode(ROL003,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        UserRole userRole = new UserRole();
        userRole.setId(id);
        userRole.setOddNum(oddNum);
        userRole.setUserId(userId);
        userRole.setROL002(ROL002);
        userRole.setROL003(ROL003);

        powerService.insertUseRole(userRole);
        return rolesource(pageIndex, pageSize);
    }

    /**
     * 是否存在角色
     *
     * @param userId
     * @param ROL003
     * @return
     */
    @RequestMapping("/exitrole")
    @ResponseBody
    public Object existrole(
            @RequestParam("userId") String userId,
            @RequestParam("ROL003") String ROL003
    ) {
        try {
            URLDecoder.decode(ROL003,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        UserRole userRole = new UserRole();
        userRole.setUserId(userId);
        userRole.setROL003(ROL003);

        Specification<UserRole> specification = new Specification<UserRole>() {
            @Override
            public Predicate toPredicate(Root<UserRole> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                ///定义条件对象列表
                List<Predicate> plist = new ArrayList<>();
                //单表条件对象构建(定区编码和地区关键字在分区表中)
                if (!StringUtils.isEmpty(userId)) {
                    Predicate p1 = cb.equal(root.get("userId"), userId);
                    plist.add(p1);
                }
                if (!StringUtils.isEmpty(ROL003)) {
                    Predicate p1 = cb.equal(root.get("ROL003"), ROL003);
                    plist.add(p1);
                }

                //pList转换为具体类型的数组
                Predicate[] predicate = plist.toArray(new Predicate[0]);
                //将条件进行汇总并返回
                return cb.and(predicate);
            }
        };

        UserRole userRole1 = powerService.existsRole(specification);
        if (userRole1 == null)
            //不存在
            return "1";
        else
            //存在
            return "0";
    }


    Map<String, Object> datasource(Integer pageIndex, Integer pageSize) {
        Sort sort = new Sort(Sort.Direction.ASC, "oddNum");
        Pageable pageable = new PageRequest(pageIndex - 1, pageSize, sort);
        Page<PowerRole> powerRolePage = powerService.findAllBy(pageable);
        Map<String, Object> datasource = new LinkedHashMap<String, Object>();
        datasource.put("code", 0);
        datasource.put("count", powerRolePage.getTotalElements());
        datasource.put("data", powerRolePage.getContent());
        return datasource;
    }

    Map<String, Object> rolesource(Integer pageIndex, Integer pageSize) {
        Sort sort = new Sort(Sort.Direction.ASC, "userId");
        Pageable pageable = new PageRequest(pageIndex - 1, pageSize, sort);
        Page<UserRole> userRolePage = powerService.findRoleBy(pageable);
        Map<String, Object> datasource = new LinkedHashMap<String, Object>();
        datasource.put("code", 0);
        datasource.put("count", userRolePage.getTotalElements());
        datasource.put("data", userRolePage.getContent());
        return datasource;
    }


}
