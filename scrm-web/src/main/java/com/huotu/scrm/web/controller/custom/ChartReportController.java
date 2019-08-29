package com.huotu.scrm.web.controller.custom;

import com.huotu.scrm.common.utils.Constant;
import com.huotu.scrm.service.entity.Chart.ChartAvg;
import com.huotu.scrm.service.entity.baseset.Reachflt;
import com.huotu.scrm.service.entity.custom.Custom;
import com.huotu.scrm.service.service.ChartInfo.ChartInfoService;
import com.huotu.scrm.service.service.ReportInfo.ReportInfoService;
import com.huotu.scrm.service.service.cutom.CustomService;
import com.huotu.scrm.service.service.user.LoginService;
import com.huotu.scrm.web.GetUserInfo.DateFormat;
import com.huotu.scrm.web.GetUserInfo.GetUserLoginInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.huotu.scrm.web.GetUserInfo.GetUserLoginInfo.queryRoleForUser;


@Controller
@RequestMapping
public class ChartReportController {

    @Autowired
    private ChartInfoService chartInfoService;

    @Autowired
    private ReportInfoService reportInfoService;

    @Autowired
    private CustomService customService;

    @Autowired
    private LoginService loginService;

    @PersistenceContext
    private EntityManager entityManager;

    @RequestMapping(value = "/chart")
    @ResponseBody
    public ModelAndView findChartInfo(HttpServletRequest request) {

        ModelAndView model = new ModelAndView();
        //获取cookie中的用户名和密码进行登录
        String username = GetUserLoginInfo.getUserInfo(request).getUSE002();
        Constant.Position = "业务";
        List<String> getSales = new ArrayList<>();

        model = queryRoleForUser(username, model, getSales, reportInfoService);

        getSales = (List<String>) model.getModel().get("userSales");

        if (getSales.size() > 0) {
            if (!getSales.contains(username))
                username = "全体";
        }
        getResult(model, username);
        model.setViewName("ChartRepot");
        return model;
    }



    String flt001Forcus004 = "";
    String flt001Forbrs006 = "";
    String flt001Forbrs0061 = "";

    /**
     * 查找本月电话
     *
     * @param username
     * @return
     */
    @RequestMapping(value = "/chartuser")
    @ResponseBody
    public Object fincChartInfoForUser(@RequestParam("username") String username) {
        ModelAndView model = new ModelAndView();
        Map<Object, Object> map = getResult(model, username);
        return map;
    }

    Map<Object, Object> getResult(ModelAndView model, String username) {
        Map<Object, Object> map = new LinkedHashMap<>();
        Date date = chartInfoService.getDate();
        Date date1=date;
        Integer days = 0;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        days = calendar.get(Calendar.MONTH);
        if (days == 3)
            days = -92;
        else
            days = -93;
        calendar.add(Calendar.DAY_OF_MONTH, days);
        Date dateStart = calendar.getTime();
        List<Object> getIncreasebrs = chartInfoService.getincreasebrs(username);
        if(username.equals("全体")) {
            getIncreasebrs = chartInfoService.getincreasebrs();

        }

        Integer count = loginService.findBy(username);
        if(username.equals("全体")) {
            count = loginService.findAllBy();
        }


        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        StringBuilder stringBuilder = new StringBuilder("select id,AVG001,DATE_FORMAT(AVG003,'%m-%d') AVG003,AVG002,AVG004 from rgwavg ");
        stringBuilder.append("where AVG001='" + username + "' ");
        stringBuilder.append("and AVG003 BETWEEN '" + format.format(dateStart) + "' and '" + format.format(date) + "' ");
        stringBuilder.append(" union all ");
        if(username.equals("全体")) {
            stringBuilder.append("select @rownum:=@rownum+1 ,'全体',a.days,ifnull(b.coun,0)-ifnull(d.coun,0) conn,10 from ( ");
            stringBuilder.append("SELECT DISTINCT date_format(DATE_ADD(date_format(DATE_ADD('" + format.format(date) + "',INTERVAL " + days + " day),'%Y-%m-%d'),INTERVAL id DAY),'%m-%d') days,");
            stringBuilder.append("date_format(DATE_ADD(date_format(DATE_ADD('" + format.format(date) + "',INTERVAL " + days + " day),'%Y-%m-%d'),INTERVAL id DAY),'%Y%m%d') AS `TIME` FROM rgwcus ");
            stringBuilder.append("WHERE DATE_ADD('" + format.format(date) + "',INTERVAL id DAY) <= DATE_ADD(DATE_ADD('" + format.format(date) + "',INTERVAL 1 day),INTERVAL 3 month) ORDER BY time) a ");
            stringBuilder.append("left join ( ");
            stringBuilder.append("select date_format(BRS003,'%Y%m%d') days,count(1) coun from rgwbrs where date_format(BRS003,'%Y%m%d')>=" +
                    "date_format(DATE_ADD('" + format.format(date) + "',INTERVAL " +
                    "-90 day),'%Y%m%d') group by date_format" +
                    "(BRS003,'%Y%m%d') ) b on a.TIME=b.days ");
        }
        else {
            stringBuilder.append("select @rownum:=@rownum+1 ,'" + username + "',a.days,ifnull(b.coun,0)-ifnull(d.coun,0) conn,10 from ( ");
            stringBuilder.append("SELECT DISTINCT date_format(DATE_ADD(date_format(DATE_ADD('" + format.format(date) + "',INTERVAL " + days + " day),'%Y-%m-%d'),INTERVAL id DAY),'%m-%d') days,");
            stringBuilder.append("date_format(DATE_ADD(date_format(DATE_ADD('" + format.format(date) + "',INTERVAL " + days + " day),'%Y-%m-%d'),INTERVAL id DAY),'%Y%m%d') AS `TIME` FROM rgwcus ");
            stringBuilder.append("WHERE DATE_ADD('" + format.format(date) + "',INTERVAL id DAY) <= DATE_ADD(DATE_ADD('" + format.format(date) + "',INTERVAL 1 day),INTERVAL 3 month) ORDER BY time) a ");
            stringBuilder.append("left join ( ");
//            stringBuilder.append("select date_format(BRS003,'%Y%m%d') days,count(1) coun from rgwbrs a inner join rgwcus b on a.BRS002=b.CUS001 where date_format(BRS003,'%Y%m%d')>=" +
//                    "date_format(DATE_ADD('" + format.format(date) + "',INTERVAL " +
//                    "-90 day),'%Y%m%d')  and CUS012='" + username + "' group by date_format" +
//                    "(BRS003,'%Y%m%d') ) b on a.TIME=b.days ");
            stringBuilder.append("select date_format(BRS003,'%Y%m%d') days,count(1) coun from rgwbrs  where date_format(BRS003,'%Y%m%d')>=" +
                    "date_format(DATE_ADD('" + format.format(date) + "',INTERVAL " +
                    "-90 day),'%Y%m%d')  and BRS008='" + username + "' group by date_format" +
                    "(BRS003,'%Y%m%d') ) b on a.TIME=b.days ");
        }

        List<Reachflt> reachflts=chartInfoService.getAllBy();
        if(reachflts!=null) {

            String result = "";

            flt001Forcus004 = "";
            flt001Forbrs006 = "";
            flt001Forbrs0061 = "";

            if (reachflts != null) {
                for (Reachflt r : reachflts) {
                    if (r.getFLT004().equals("and")) {
                        getFlt(r);
                    }
                }
            }

            if (StringUtils.isEmpty(result))
                result = flt001Forcus004;
            else if (!StringUtils.isEmpty(flt001Forcus004))
                result = result + " and " + flt001Forcus004;

            if (StringUtils.isEmpty(result))
                result = flt001Forbrs006;
            else if (!StringUtils.isEmpty(flt001Forbrs006))
                result = result + " and " + flt001Forbrs006;

            if (StringUtils.isEmpty(result))
                result = flt001Forbrs0061;
            else if (!StringUtils.isEmpty(flt001Forbrs0061))
                result = result + " and " + flt001Forbrs0061;

            flt001Forcus004 = "";
            flt001Forbrs006 = "";
            flt001Forbrs0061 = "";


            if (reachflts != null) {
                for (Reachflt r : reachflts) {
                    if (r.getFLT004().equals("or")) {
                        getFlt(r);
                    }
                }
            }

            String resultOne = "";


            if (StringUtils.isEmpty(resultOne))
                resultOne = flt001Forcus004;
            else if (!StringUtils.isEmpty(flt001Forcus004))
                resultOne = resultOne + " or " + flt001Forcus004;

            if (StringUtils.isEmpty(resultOne))
                resultOne = flt001Forbrs006;
            else if (!StringUtils.isEmpty(flt001Forbrs006))
                resultOne = resultOne + " or " + flt001Forbrs006;

            if (StringUtils.isEmpty(resultOne))
                resultOne = flt001Forbrs0061;
            else if (!StringUtils.isEmpty(flt001Forbrs0061))
                resultOne = resultOne + " or " + flt001Forbrs0061;


            if (StringUtils.isEmpty(result))
                result = resultOne;
            else
                result = result + " or (" + resultOne + ")";


            if (!StringUtils.isEmpty(result)) {
                if(username.equals("全体")){

                    stringBuilder.append("left join ( ");
                    stringBuilder.append("select date_format(BRS003,'%Y%m%d') days,count(1) coun from rgwbrs a inner join rgwcus b on a.BRS002=b.CUS001 where date_format(BRS003,'%Y%m%d')>=" +
                            "date_format(DATE_ADD('" + format.format(date) + "',INTERVAL " +
                            "-90 day),'%Y%m%d') and (" + result + ") group by date_format" +
                            "(BRS003,'%Y%m%d') ) d on a.TIME=d.days,(SELECT @rowNum:=0) c ");

                    stringBuilder.append(" union all ");

                    stringBuilder.append("select @rownum:=@rownum+1 ,'全体',a.days,ifnull(b.coun,0) conn,20 from ( ");
                    stringBuilder.append("SELECT DISTINCT date_format(DATE_ADD(date_format(DATE_ADD('" + format.format(date) + "',INTERVAL " + days + " day),'%Y-%m-%d'),INTERVAL id DAY),'%m-%d') days,");
                    stringBuilder.append("date_format(DATE_ADD(date_format(DATE_ADD('" + format.format(date) + "',INTERVAL " + days + " day),'%Y-%m-%d'),INTERVAL id DAY),'%Y%m%d') AS `TIME` FROM rgwcus ");
                    stringBuilder.append("WHERE DATE_ADD('" + format.format(date) + "',INTERVAL id DAY) <= DATE_ADD(DATE_ADD('" + format.format(date) + "',INTERVAL 1 day),INTERVAL 3 month) ORDER BY time) a ");
                    stringBuilder.append("left join ( ");
                    stringBuilder.append("select date_format(BRS003,'%Y%m%d') days,count(1) coun from rgwbrs a inner join rgwcus b on a.BRS002=b.CUS001 where date_format(BRS003,'%Y%m%d')>=" +
                            "date_format(DATE_ADD('" + format.format(date) + "',INTERVAL " +
                            "-90 day),'%Y%m%d') and (" + result + ") group by date_format" +
                            "(BRS003,'%Y%m%d') ) b on a.TIME=b.days order by AVG003 ");
                }else {

                    stringBuilder.append("left join ( ");
//                    stringBuilder.append("select date_format(BRS003,'%Y%m%d') days,count(1) coun from rgwbrs a inner join rgwcus b on a.BRS002=b.CUS001 where date_format(BRS003,'%Y%m%d')>=" +
//                            "date_format(DATE_ADD('" + format.format(date) + "',INTERVAL " +
//                            "-90 day),'%Y%m%d')  and CUS012='" + username + "' and (" + result + ") group by date_format" +
//                            "(BRS003,'%Y%m%d') ) d on a.TIME=d.days ,(SELECT @rowNum:=0) c");

                    stringBuilder.append("select date_format(BRS003,'%Y%m%d') days,count(1) coun from rgwbrs a inner join rgwcus b on a.BRS002=b.CUS001 where date_format(BRS003,'%Y%m%d')>=" +
                            "date_format(DATE_ADD('" + format.format(date) + "',INTERVAL " +
                            "-90 day),'%Y%m%d')  and BRS008='" + username + "' and (" + result + ") group by date_format" +
                            "(BRS003,'%Y%m%d') ) d on a.TIME=d.days ,(SELECT @rowNum:=0) c");

                    stringBuilder.append(" union all ");

                    stringBuilder.append("select @rownum:=@rownum+1 ,'" + username + "',a.days,ifnull(b.coun,0) conn,20 from ( ");
                    stringBuilder.append("SELECT DISTINCT date_format(DATE_ADD(date_format(DATE_ADD('" + format.format(date) + "',INTERVAL " + days + " day),'%Y-%m-%d'),INTERVAL id DAY),'%m-%d') days,");
                    stringBuilder.append("date_format(DATE_ADD(date_format(DATE_ADD('" + format.format(date) + "',INTERVAL " + days + " day),'%Y-%m-%d'),INTERVAL id DAY),'%Y%m%d') AS `TIME` FROM rgwcus ");
                    stringBuilder.append("WHERE DATE_ADD('" + format.format(date) + "',INTERVAL id DAY) <= DATE_ADD(DATE_ADD('" + format.format(date) + "',INTERVAL 1 day),INTERVAL 3 month) ORDER BY time) a ");
                    stringBuilder.append("left join ( ");
//                    stringBuilder.append("select date_format(BRS003,'%Y%m%d') days,count(1) coun from rgwbrs a inner join rgwcus b on a.BRS002=b.CUS001 where date_format(BRS003,'%Y%m%d')>=" +
//                            "date_format(DATE_ADD('" + format.format(date) + "',INTERVAL " +
//                            "-90 day),'%Y%m%d')  and CUS012='" + username + "' and (" + result + ") group by date_format" +
//                            "(BRS003,'%Y%m%d') ) b on a.TIME=b.days order by AVG003");
                    stringBuilder.append("select date_format(BRS003,'%Y%m%d') days,count(1) coun from rgwbrs a inner join rgwcus b on a.BRS002=b.CUS001 where date_format(BRS003,'%Y%m%d')>=" +
                            "date_format(DATE_ADD('" + format.format(date) + "',INTERVAL " +
                            "-90 day),'%Y%m%d')  and BRS008='" + username + "' and (" + result + ") group by date_format" +
                            "(BRS003,'%Y%m%d') ) b on a.TIME=b.days order by AVG003");


                }
            } else {
                stringBuilder.append("left join (select date_format(BRS003,'%Y%m%d') days,0 coun from rgwbrs) d on a.TIME=d.days ,(SELECT @rowNum:=0) c ");
                stringBuilder.append(" order by AVG003 ");
            }
        }

        Query dataQquery=entityManager.createNativeQuery(stringBuilder.toString(),ChartAvg.class);
        List<ChartAvg> chartAvgs =dataQquery.getResultList();

        Specification<Custom> specification=new Specification<Custom>() {
            @Override
            public Predicate toPredicate(Root<Custom> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                predicates.add(cb.like(root.get("CUS018"), "%"+DateFormat.getFormatForDateyyyyMM(date1)+"%"));
                if(!username.equals("全体")){
                    predicates.add(cb.equal(root.get("CUS019"),username));
                }
                //pList转换为具体类型的数组
                Predicate[] predicate = predicates.toArray(new Predicate[0]);
                //将条件进行汇总并返回
                return cb.and(predicate);
            }
        };

        Long allAdd=customService.getCount(specification);

//        stringBuilder = new StringBuilder("SELECT count(1) FROM rgwcus where DATE_FORMAT(CUS018,'%Y%m')=DATE_FORMAT(NOW(),'%Y%m') ");
//        if(!username.equals("全体")) {
//            stringBuilder.append(" and CUS019='" + username + "'");
//        }
//
//        dataQquery=entityManager.createNativeQuery(stringBuilder.toString(),Object.class);
//        Object allAdd=dataQquery.getResultList().get(0);

        String ALev = "", BLev = "", CLev = "" , DLev = "", ELev = ""  ,addAll="本月新增["+allAdd+"]";
        for (int i = 0; i < getIncreasebrs.size(); i++) {
            Object[] objects = (Object[]) getIncreasebrs.get(i);

            if (objects[0].toString().equals("A")) {
                ALev = "A级[上月:" + objects[1].toString();
                ALev = ALev + " 新增:" + objects[2].toString() + "]";
            } else if (objects[0].toString().equals("B")) {
                BLev = "B级[上月:" + objects[1].toString();
                BLev = BLev + " 新增:" + objects[2].toString() + "]";
            } else if (objects[0].toString().equals("C")) {
                CLev = "C级[上月:" + objects[1].toString();
                CLev = CLev + " 新增:" + objects[2].toString() + "]";
            }else if (objects[0].toString().equals("D")) {
                DLev = "D级[上月:" + objects[1].toString();
                DLev = DLev + " 新增:" + objects[2].toString() + "]";
            }else if (objects[0].toString().equals("E")) {
                ELev = "E级[上月:" + objects[1].toString();
                ELev = ELev + " 新增:" + objects[2].toString() + "]";
            }
        }

        if (StringUtils.isEmpty(ALev))
            ALev = "A级[上月:0 新增:0]";
        if (StringUtils.isEmpty(BLev))
            BLev = "B级[上月:0 新增:0]";
        if (StringUtils.isEmpty(CLev))
            CLev = "C级[上月:0 新增:0]";
        if (StringUtils.isEmpty(DLev))
            DLev = "D级[上月:0 新增:0]";
        if (StringUtils.isEmpty(ELev))
            ELev = "E级[上月:0 新增:0]";

        String countResult = "";

        if (count > 0)
            countResult = "超期:" + count;
        else
            countResult = "超期:0";

        map.put("ALev", ALev);
        map.put("BLev", BLev);
        map.put("CLev", CLev);
        map.put("DLev", DLev);
        map.put("ELev", ELev);
        map.put("addAll", addAll);
        map.put("count", countResult);
        map.put("chartAvgs", chartAvgs);

        model.addObject("ALev", ALev);
        model.addObject("BLev", BLev);
        model.addObject("CLev", CLev);
        model.addObject("DLev", DLev);
        model.addObject("ELev", ELev);
        model.addObject("addAll", addAll);
        model.addObject("count", countResult);
        model.addObject("chartAvgs", chartAvgs);
        model.addObject("username", username);

        return map;
    }

    void getFlt(Reachflt r ) {

        if (r.getFLT001().equals("CUS004") || (r.getFLT001().equals("BRS006") && !r.getFLT002().equals("like"))) {
            flt001Forcus004 = getFlt(r, flt001Forcus004);
        } else if (r.getFLT001().equals("BRS0061")) {
            flt001Forbrs0061 = getFlt1(r, flt001Forbrs0061);
        } else if (r.getFLT001().equals("BRS006") && r.getFLT002().equals("like")) {
            flt001Forbrs006 = getFlt2(r, flt001Forbrs006);
        }

    }

    String getFlt(Reachflt r,String str){
        if (StringUtils.isEmpty(str)) {
            if (r.getFLT002().equals("like"))
                str = "(" + r.getFLT001() + " " + r.getFLT002() + " " + "'%" + r.getFLT003() + "%')";
            else
                str = "(" + r.getFLT001() + " " + r.getFLT002() + " " + "'" + r.getFLT003() + "')";
        }
        else {
            if (r.getFLT002().equals("like"))
                str = str + " " + r.getFLT004() + " " + "(" + r.getFLT001() + ' ' + r.getFLT002() + ' ' + "%'" + r.getFLT003() + "%'";
            else
                str = str + " " + r.getFLT004() + " " + "(" + r.getFLT001() + ' ' + r.getFLT002() + ' ' + "'" + r.getFLT003() + "'";
        }
        return str;
    }

    String getFlt1(Reachflt r,String str){
        if (StringUtils.isEmpty(str)) {
            if (r.getFLT002().equals("like"))
                str = "(LENGTH(BRS006)/3" + " " + r.getFLT002() + " '%" + r.getFLT003() + "%')";
            else
                str = "(LENGTH(BRS006)/3" + " " + r.getFLT002() + " " + r.getFLT003() + ")";
        }
        else {
            if (r.getFLT002().equals("like"))
                str = str + " " + r.getFLT004() + " " + "(LENGTH(BRS006)/3" + " " + r.getFLT002() + " '%" + r.getFLT003() + "%')";
            else
                str = str + " " + r.getFLT004() + " " + "(LENGTH(BRS006)/3" + " " + r.getFLT002() + " " + r.getFLT003() + ")";
        }
        return str;
    }

    String getFlt2(Reachflt r,String str){

        if (StringUtils.isEmpty(str))
            str = "(" + r.getFLT001() + " " + r.getFLT002() + " " + "'%" + r.getFLT003() + "%')";
        else
            str = str + " " + r.getFLT004() + " " + "(" + r.getFLT001() + ' ' + r.getFLT002() + ' ' + "'%" + r.getFLT003() + "%')";

        return str;
    }

}
