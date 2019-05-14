package com.huotu.scrm.web.controller.custom;

import com.huotu.scrm.common.utils.Constant;
import com.huotu.scrm.common.utils.GenerateUid;
import com.huotu.scrm.service.entity.Maintain.Maintain;
import com.huotu.scrm.service.entity.custom.CustomCon;
import com.huotu.scrm.service.repository.CustomBrs.CustomBrsRepository;
import com.huotu.scrm.service.service.Maintain.MaintainService;
import com.huotu.scrm.service.service.ReportInfo.ReportInfoService;
import com.huotu.scrm.web.GetUserInfo.GetUserLoginInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Controller
@RequestMapping("/")
public class MaintainController {

    @Autowired
    private MaintainService maintainService;

    @Autowired
    private ReportInfoService reportInfoService;

    @Autowired
    private CustomBrsRepository customBrsRepository;

    @RequestMapping(value = "/maintain")
    public ModelAndView leftCustom() {
        ModelAndView model = new ModelAndView();
        model.setViewName("Maintain");
        return model;
    }

    /**
     * 自动查询待处理事项
     */
    @RequestMapping(value = "/readMaintain")
    @ResponseBody
    public Object readQuestion(HttpServletRequest request) {
        Map<Object, Object> map = new LinkedHashMap<>();
        readDate(map, 1, 10,request);
        return map;
    }

    /**
     * 新增事项
     */
    @RequestMapping(value = "/addMaintain")
    @ResponseBody
    public ModelAndView addQuestion(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("MaintainAdd");
        String uid = maintainService.getMaxMAI001();
        Date date = customBrsRepository.findBy();
        uid = GenerateUid.getOddNum(uid, date);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String datenow = dateFormat.format(date);
        Constant.Position="技术";
        String userName= GetUserLoginInfo.getUserInfo(request).getUSE002();
        List<String> mainList = GetUserLoginInfo.queryRoleForMainList(userName, modelAndView, reportInfoService, maintainService);
        modelAndView.addObject("uid", uid);
        modelAndView.addObject("datenow", datenow);
        modelAndView.addObject("mainList", mainList);
        return modelAndView;
    }

    /**
     * 依据客户名称获取联系人
     */
    @RequestMapping(value = "/findUserForCus")
    @ResponseBody
    public Object findUserForCustom(
            @RequestParam("data") String data) {
        try {
            URLDecoder.decode(data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        List<String> getUserInfo = maintainService.getUserInfo(data);
        return getUserInfo;
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
    }

    @RequestMapping(value = "/saveMain")
    @ResponseBody
    public Object saveMaintain(
            @RequestParam("MAI001") String MAI001,
            @RequestParam("MAI002") String MAI002,
            @RequestParam("MAI003") String MAI003,
            @RequestParam("MAI004") String MAI004,
            @RequestParam("MAI005") String MAI005,
            @RequestParam("MAI006") String MAI006,
            @RequestParam("MAI007") String MAI007,
            @RequestParam("MAI008") String MAI008,
            @RequestParam("MAI009") String MAI009,
            @RequestParam("MAI010") String MAI010,
            @RequestParam("MAI011") String MAI011,
            @RequestParam("MAI012") String MAI012,
            @RequestParam("id") Integer id,
            @RequestParam("pageIndex") Integer pageIndex,
            @RequestParam("pageSize") Integer pageSize,
            HttpServletRequest request
    ) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        Maintain maintain = new Maintain();
        maintain.setId(id);
        maintain.setMAI001(MAI001);
        maintain.setMAI002(MAI002);
        maintain.setMAI003(MAI003);
        maintain.setMAI004(MAI004);
        maintain.setMAI005(MAI005);
        try {
            maintain.setMAI006(format.parse(MAI006));
            maintain.setMAI010(format.parse(MAI010));
            maintain.setMAI011(format.parse(MAI011));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        maintain.setMAI007(MAI007);
        maintain.setMAI008(MAI008);
        maintain.setMAI009(MAI009);
        maintain.setMAI012(MAI012);

        maintainService.saveAndRefresh(maintain);
        Map<Object, Object> map = new LinkedHashMap<>();
        readDate(map, pageIndex, pageSize,request);
        return map;
    }

    /**
     * 编辑问题
     */
    @RequestMapping("/editMaintain")
    @ResponseBody
    public ModelAndView editpow(   @RequestParam("data") String data,
                                   HttpServletRequest request
    ) {
        try {
            URLDecoder.decode(data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        ModelAndView modelAndView=new ModelAndView();
        String userName= GetUserLoginInfo.getUserInfo(request).getUSE002();
        List<String> mainList = GetUserLoginInfo.queryRoleForMainList(userName, modelAndView, reportInfoService, maintainService);
        modelAndView.setViewName("MaintainAdd");
        modelAndView.addObject("mainList", mainList);
        List<String> getUserInfo = maintainService.getUserInfo(data);
        modelAndView.addObject("getsernfo", getUserInfo);
        return modelAndView;
    }

    /**
     * 查询问题
     */
    @RequestMapping("/queryMain")
    @ResponseBody
    public ModelAndView queryMain(HttpServletRequest request){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("MaintainQuery");
       String userName= GetUserLoginInfo.getUserInfo(request).getUSE002();
        List<String> mainList = GetUserLoginInfo.queryRoleForMainList(userName, modelAndView, reportInfoService, maintainService);
        modelAndView.addObject("mainList", mainList);
        return modelAndView;
    }

    /**
     * 查询
     */
    @RequestMapping("/findMain")
    @ResponseBody
    public Object findMain(
            @RequestParam("mai002") String mai002,
            @RequestParam("mai008") String mai008,
            @RequestParam("mai005") String mai005,
            @RequestParam("mai009") String mai009,
            @RequestParam("mai006") String mai006,
            @RequestParam("mai0061") String mai0061,
            @RequestParam("mai010") String mai010,
            @RequestParam("mai0101") String mai0101,
            @RequestParam("mai011") String mai011,
            @RequestParam("mai0111") String mai0111,
            @RequestParam("mai007") String mai007,
            @RequestParam("mai012") String mai012,
            @RequestParam("limit") Integer pageSize,
            @RequestParam("page") Integer pageIndex
    ){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Specification<Maintain> specification=new Specification<Maintain>() {
            @Override
            public Predicate toPredicate(Root<Maintain> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> plist = new ArrayList<>();
                if (!StringUtils.isEmpty(mai002)) {
                    Predicate p1 = cb.equal(root.get("MAI002"), mai002);
                    plist.add(p1);
                }
                if (!StringUtils.isEmpty(mai008)) {
                    Predicate p1 = cb.equal(root.get("MAI008"), mai008);
                    plist.add(p1);
                }
                if (!StringUtils.isEmpty(mai005)) {
                    Predicate p1 = cb.equal(root.get("MAI005"), mai005);
                    plist.add(p1);
                }
                if (!StringUtils.isEmpty(mai009)) {
                    Predicate p1 = cb.equal(root.get("MAI009"), mai009);
                    plist.add(p1);
                }
                try {
                    if (!StringUtils.isEmpty(mai006)) {
                        Predicate p1  = cb.greaterThan(root.get("MAI006"), format.parse(mai006));
                        plist.add(p1);
                    }
                    if (!StringUtils.isEmpty(mai0061)) {
                        Predicate p1 = cb.lessThan(root.get("MAI006"), format.parse(mai0061));
                        plist.add(p1);
                    }
                    if (!StringUtils.isEmpty(mai010)) {
                        Predicate p1 = cb.greaterThan(root.get("MAI010"), format.parse(mai010));
                        plist.add(p1);
                    }
                    if (!StringUtils.isEmpty(mai0101)) {
                        Predicate p1 = cb.lessThan(root.get("MAI010"), format.parse(mai0101));
                        plist.add(p1);
                    }
                    if (!StringUtils.isEmpty(mai011)) {
                        Predicate p1 = cb.greaterThan(root.get("MAI011"), format.parse(mai011));
                        plist.add(p1);
                    }
                    if (!StringUtils.isEmpty(mai0111)) {
                        Predicate p1 = cb.lessThan(root.get("MAI011"), format.parse(mai0111));
                        plist.add(p1);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (!StringUtils.isEmpty(mai007)) {
                    Predicate p1 = cb.like(root.get("MAI007"), "%" + mai007 + "%");
                    plist.add(p1);
                }
                if (!StringUtils.isEmpty(mai012)) {
                    Predicate p1 = cb.like(root.get("MAI012"), "%" + mai012 + "%");
                    plist.add(p1);
                }
                //pList转换为具体类型的数组
                Predicate[] predicate = plist.toArray(new Predicate[0]);
                //将条件进行汇总并返回
                return cb.and(predicate);
            }
        };
        Sort sort = new Sort(Sort.Direction.DESC, "MAI002", "MAI006");
        Pageable pageable = new PageRequest(pageIndex-1, pageSize, sort);
        Page<Maintain> maintainPage = maintainService.getAllBy(specification, pageable);
        Map<Object, Object> map=new LinkedHashMap<>();
        map.put("code", 0);
        map.put("count", maintainPage.getTotalElements());
        map.put("data", maintainPage.getContent());
        return map;
    }

    /**
     * 跳转到新页面
     */
    @RequestMapping(value = "/findContract")
    @ResponseBody
    public ModelAndView findContract(){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("SignContract");
        return modelAndView;
    }

    /**
     * 跳转到合同签订页面
     */
    @RequestMapping(value = "/signadd")
    @ResponseBody
    public ModelAndView singAdd(){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("MaintainContract");
        return modelAndView;
    }

    /**
     * 根据供应商名称，获取历史签合同明细
     */
    @RequestMapping(value = "/queryContract")
    @ResponseBody
    public Object findContractInfo(
            @RequestParam("data") String data
    ){
        List<CustomCon> customConList=maintainService.findAllByCON001(data);
        return  customConList;
    }

    /**
     * 保存签订合同等信息
     */
    @RequestMapping(value = "/saveAndEditCon")
    @ResponseBody
    public Object saveAndEditCon(
            @RequestParam("CON002") String con002,
            @RequestParam("CON003") String con003,
            @RequestParam("CON004") String con004,
            @RequestParam("CON005") String con005,
            @RequestParam("id") Integer id,
            @RequestParam("pageIndex") Integer pageIndex,
            @RequestParam("pageSize") Integer pageSize
    ){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        CustomCon customCon=new CustomCon();
        customCon.setCON002(con002);
        try {
            customCon.setCON003(format.parse( con003));
            customCon.setCON004(format.parse(con004));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        customCon.setCON005(con005);
        customCon.setId(id);
        maintainService.saveAndRefresh(customCon);
        Map<Object,Object> map=new LinkedHashMap<>();
        readDateForSign(map,pageIndex,pageSize,con002);
        return  map;
    }

    /**
     * 查询签订合同等信息
     */
    @RequestMapping(value = "/queryCon")
    @ResponseBody
    public Object findAllSignBycon002(
            @RequestParam("CON002") String con002
    ){
        Map<Object,Object> map=new LinkedHashMap<>();
        Integer pageIndex=1;
        Integer pageSize=50;
        readDateForSign(map,pageIndex,pageSize,con002);
        return  map;
    }


    void readDate(Map<Object, Object> map, Integer pageIndex, Integer pageSize,HttpServletRequest request) {
        String username = GetUserLoginInfo.getUserInfo(request).getUSE002();
        boolean result = GetUserLoginInfo.getUserListForRole(username, reportInfoService);
        Specification<Maintain> specification = new Specification<Maintain>() {
            @Override
            public Predicate toPredicate(Root<Maintain> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                ///定义条件对象列表
                List<Predicate> plist = new ArrayList<>();
                if (result == false) {
                    Predicate p1 = cb.equal(root.get("MAI008"), username);
                    plist.add(p1);
                }
                CriteriaBuilder.In<String> in = cb.in(root.get("MAI009").as(String.class));
                in.value("未开始");
                in.value("进行中");
                plist.add(in);

                //pList转换为具体类型的数组
                Predicate[] predicate = plist.toArray(new Predicate[0]);
                //将条件进行汇总并返回
                return cb.and(predicate);
            }
        };
        Sort sort = new Sort(Sort.Direction.DESC, "MAI002", "MAI006");
        Pageable pageable = new PageRequest(pageIndex - 1, pageSize, sort);
        Page<Maintain> maintainPage = maintainService.getAllBy(specification, pageable);

        map.put("code", 0);
        map.put("count", maintainPage.getTotalElements());
        map.put("data", maintainPage.getContent());
    }

    void readDateForSign(Map<Object, Object> map, Integer pageIndex, Integer pageSize,String con002){
        Specification<CustomCon> specification=new Specification<CustomCon>() {
            @Override
            public Predicate toPredicate(Root<CustomCon> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> plist = new ArrayList<>();
                if (!StringUtils.isEmpty(con002)) {
                    Predicate p1 = cb.equal(root.get("CON002"), con002);
                    plist.add(p1);
                }
                //pList转换为具体类型的数组
                Predicate[] predicate = plist.toArray(new Predicate[0]);
                //将条件进行汇总并返回
                return cb.and(predicate);
            }
        };
        Sort sort = new Sort(Sort.Direction.DESC, "CON002", "CON003");
        Pageable pageable = new PageRequest(pageIndex - 1, pageSize, sort);
        Page<CustomCon> customConList=maintainService.findAll(specification,pageable);
        map.put("code",0);
        map.put("count", customConList.getTotalElements());
        map.put("data", customConList.getContent());
    }

}



