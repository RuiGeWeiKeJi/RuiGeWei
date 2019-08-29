package com.huotu.scrm.web.controller.custom;

import com.huotu.scrm.common.utils.Constant;
import com.huotu.scrm.common.utils.GenerateUid;
import com.huotu.scrm.service.entity.Maintain.DevelopMain;
import com.huotu.scrm.service.repository.CustomBrs.CustomBrsRepository;
import com.huotu.scrm.service.service.Maintain.DevelopMainService;
import com.huotu.scrm.service.service.ReportInfo.ReportInfoService;
import com.huotu.scrm.web.GetUserInfo.GetUserLoginInfo;
import com.huotu.scrm.web.GetUserInfo.MainUtils;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/")
public class DevelopMainController {


    @Autowired
    private DevelopMainService developMainService;

    @Autowired
    private ReportInfoService reportInfoService;

    @Autowired
    private CustomBrsRepository customBrsRepository;

    @RequestMapping(value = "/devmain")
    public ModelAndView leftCustom() {
        ModelAndView model = new ModelAndView();
        model.setViewName("DevelopmentMain");
        return model;
    }

    /**
     * 自动查询待处理事项
     */
    @RequestMapping(value = "/readDevMaintain")
    @ResponseBody
    public Object readQuestion(HttpServletRequest request) {
        Map<Object, Object> map = new LinkedHashMap<>();
        readDate(map, 1, 10,request);
        return map;
    }

    /**
     * 新增事项
     */
    @RequestMapping(value = "/addDevMaintain")
    @ResponseBody
    public ModelAndView addQuestion(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("MaintainAdd");
        String uid = developMainService.getMaxDEV001();
        Date date = customBrsRepository.findBy();
        uid = GenerateUid.getOddNum(uid, date);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String datenow = dateFormat.format(date);
        Constant.Position="开发";
        String userName= GetUserLoginInfo.getUserInfo(request).getUSE002();
        List<String> mainList = GetUserLoginInfo.queryRoleForMainList(userName, modelAndView, reportInfoService, developMainService);
        modelAndView.addObject("uid", uid);
        modelAndView.addObject("datenow", datenow);
        modelAndView.addObject("mainList", mainList);
        return modelAndView;
    }


    @RequestMapping(value = "/saveDevMain")
    @ResponseBody
    public Object saveMaintain(
            @RequestParam("MAI001") String DEV001,
            @RequestParam("MAI002") String DEV002,
            @RequestParam("MAI003") String DEV003,
            @RequestParam("MAI004") String DEV004,
            @RequestParam("MAI005") String DEV005,
            @RequestParam("MAI006") String DEV006,
            @RequestParam("MAI007") String DEV007,
            @RequestParam("MAI008") String DEV008,
            @RequestParam("MAI009") String DEV009,
            @RequestParam("MAI010") String DEV010,
            @RequestParam("MAI011") String DEV011,
            @RequestParam("MAI012") String DEV012,
            @RequestParam("MAI013") String DEV013,
            @RequestParam("id") Integer id,
            @RequestParam("pageIndex") Integer pageIndex,
            @RequestParam("pageSize") Integer pageSize,
            HttpServletRequest request
    ) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        DevelopMain developMain = new DevelopMain();
        developMain.setId(id);
        developMain.setDEV001(DEV001);
        developMain.setDEV002(DEV002);
        developMain.setDEV003(DEV003);
        developMain.setDEV004(DEV004);
        developMain.setDEV005(DEV005);
        try {
            developMain.setDEV006(format.parse(DEV006));
            developMain.setDEV010(format.parse(DEV010));
            developMain.setDEV011(format.parse(DEV011));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        developMain.setDEV007(DEV007);
        developMain.setDEV008(DEV008);
        developMain.setDEV009(DEV009);
        developMain.setDEV012(DEV012);
        developMain.setDEV013(DEV013);

        developMainService.saveAndRefresh(developMain);
        Map<Object, Object> map = new LinkedHashMap<>();
        readDate(map, pageIndex, pageSize,request);
        return map;
    }

    /**
     * 编辑问题
     */
    @RequestMapping("/editDevMaintain")
    @ResponseBody
    public ModelAndView editpow(   @RequestParam("data") String data,
                                   HttpServletRequest request
    ) {
        ModelAndView modelAndView=  modelAndView = MainUtils.getEdit(data, request, reportInfoService, developMainService);
        return modelAndView;
    }

    /**
     * 查询问题
     */
    @RequestMapping("/queryDevMain")
    @ResponseBody
    public ModelAndView queryMain(HttpServletRequest request){
        ModelAndView modelAndView=MainUtils.getQuery(request,reportInfoService,developMainService);
        return modelAndView;
    }

    /**
     * 查询
     */
    @RequestMapping("/findDevMain")
    @ResponseBody
    public Object findMain(
            @RequestParam("mai002") String dev002,
            @RequestParam("mai008") String dev008,
            @RequestParam("mai005") String dev005,
            @RequestParam("mai009") String dev009,
            @RequestParam("mai006") String dev006,
            @RequestParam("mai0061") String dev0061,
            @RequestParam("mai010") String dev010,
            @RequestParam("mai0101") String dev0101,
            @RequestParam("mai011") String dev011,
            @RequestParam("mai0111") String dev0111,
            @RequestParam("mai007") String dev007,
            @RequestParam("mai012") String dev012,
            @RequestParam("mai013") String dev013,
            @RequestParam("limit") Integer pageSize,
            @RequestParam("page") Integer pageIndex
    ){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Specification<DevelopMain> specification=new Specification<DevelopMain>() {
            @Override
            public Predicate toPredicate(Root<DevelopMain> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> plist = new ArrayList<>();
                if (!StringUtils.isEmpty(dev002)) {
                    Predicate p1 = cb.equal(root.get("DEV002"), dev002);
                    plist.add(p1);
                }
                String[] strings={};
                if (!StringUtils.isEmpty(dev008) && !dev008.equals("null")) {
                    strings= StringSplitAndCom.getStrSplit(dev008);
                    if(strings!=null) {
                        CriteriaBuilder.In<String> in=cb.in(root.get("DEV008").as(String.class));
                        for(String i :strings){
                            in.value(i);
                        }
                        plist.add(in);
                    }
                }
                if (!StringUtils.isEmpty(dev005) && !dev005.equals("null")) {
                    strings= StringSplitAndCom.getStrSplit(dev005);
                    if(strings!=null) {
                        CriteriaBuilder.In<String> in=cb.in(root.get("DEV005").as(String.class));
                        for(String i :strings){
                            in.value(i);
                        }
                        plist.add(in);
                    }
                }
                if (!StringUtils.isEmpty(dev009) && !dev009.equals("null")) {
                    strings= StringSplitAndCom.getStrSplit(dev009);
                    if(strings!=null) {
                        CriteriaBuilder.In<String> in=cb.in(root.get("DEV009").as(String.class));
                        for(String i :strings){
                            in.value(i);
                        }
                        plist.add(in);
                    }
                }
                try {
                    if (!StringUtils.isEmpty(dev006)) {
                        Predicate p1  = cb.greaterThan(root.get("DEV006"), format.parse(dev006));
                        plist.add(p1);
                    }
                    if (!StringUtils.isEmpty(dev0061)) {
                        Predicate p1 = cb.lessThan(root.get("DEV006"), format.parse(dev0061));
                        plist.add(p1);
                    }
                    if (!StringUtils.isEmpty(dev010)) {
                        Predicate p1 = cb.greaterThan(root.get("DEV010"), format.parse(dev010));
                        plist.add(p1);
                    }
                    if (!StringUtils.isEmpty(dev0101)) {
                        Predicate p1 = cb.lessThan(root.get("DEV010"), format.parse(dev0101));
                        plist.add(p1);
                    }
                    if (!StringUtils.isEmpty(dev011)) {
                        Predicate p1 = cb.greaterThan(root.get("DEV011"), format.parse(dev011));
                        plist.add(p1);
                    }
                    if (!StringUtils.isEmpty(dev0111)) {
                        Predicate p1 = cb.lessThan(root.get("DEV011"), format.parse(dev0111));
                        plist.add(p1);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (!StringUtils.isEmpty(dev007)) {
                    Predicate p1 = cb.like(root.get("DEV007"), "%" + dev007 + "%");
                    plist.add(p1);
                }
                if (!StringUtils.isEmpty(dev012)) {
                    Predicate p1 = cb.like(root.get("DEV012"), "%" + dev012 + "%");
                    plist.add(p1);
                }
                if (!StringUtils.isEmpty(dev013) && !dev013.equals("null")) {
                    Predicate p1 = cb.equal(root.get("DEV013"),  dev013 );
                    plist.add(p1);
                }
                //pList转换为具体类型的数组
                Predicate[] predicate = plist.toArray(new Predicate[0]);
                //将条件进行汇总并返回
                return cb.and(predicate);
            }
        };
        Sort sort = new Sort(Sort.Direction.DESC, "DEV002", "DEV006");
        Pageable pageable = new PageRequest(pageIndex-1, pageSize, sort);
        Page<DevelopMain> maintainPage = developMainService.getAllBy(specification, pageable);
        Map<Object, Object> map=new LinkedHashMap<>();
        map.put("code", 0);
        map.put("count", maintainPage.getTotalElements());
        map.put("data", maintainPage.getContent());
        return map;
    }


    void readDate(Map<Object, Object> map, Integer pageIndex, Integer pageSize,HttpServletRequest request) {
        String username = GetUserLoginInfo.getUserInfo(request).getUSE002();
        boolean result = GetUserLoginInfo.getUserListForRole(username, reportInfoService);
        Specification<DevelopMain> specification = new Specification<DevelopMain>() {
            @Override
            public Predicate toPredicate(Root<DevelopMain> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                ///定义条件对象列表
                List<Predicate> plist = new ArrayList<>();
                if (result == false) {
                    Predicate p1 = cb.equal(root.get("DEV008"), username);
                    plist.add(p1);
                }
                CriteriaBuilder.In<String> in = cb.in(root.get("DEV009").as(String.class));
                in.value("未开始");
                in.value("进行中");
                plist.add(in);

                //pList转换为具体类型的数组
                Predicate[] predicate = plist.toArray(new Predicate[0]);
                //将条件进行汇总并返回
                return cb.and(predicate);
            }
        };
        Sort sort = new Sort(Sort.Direction.DESC, "DEV002", "DEV006");
        Pageable pageable = new PageRequest(pageIndex - 1, pageSize, sort);
        Page<DevelopMain> maintainPage = developMainService.getAllBy(specification, pageable);

        map.put("code", 0);
        map.put("count", maintainPage.getTotalElements());
        map.put("data", maintainPage.getContent());
    }

}
