package com.huotu.scrm.web.controller.custom;

import com.huotu.scrm.common.utils.Constant;
import com.huotu.scrm.service.model.MaintainChart;
import com.huotu.scrm.service.model.MaintainReport;
import com.huotu.scrm.service.service.Maintain.DevelopMainService;
import com.huotu.scrm.service.service.Maintain.MaintainReportService;
import com.huotu.scrm.service.service.ReportInfo.ReportInfoService;
import com.huotu.scrm.web.GetUserInfo.GetUserLoginInfo;
import com.mysql.jdbc.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
public class MaintainReportController {

    @Autowired
    private DevelopMainService developMainService;

    @Autowired
    private ReportInfoService reportInfoService;

    @Autowired
    private MaintainReportService maintainReportService;

    @PersistenceContext
    private EntityManager entityManager;


    ModelAndView getModelAndView(String viewName){
        ModelAndView modelAndView= new ModelAndView();
        modelAndView.setViewName(viewName);
        return modelAndView;
    }

    List<String> getMainList(HttpServletRequest request,ModelAndView modelAndView){
        Constant.Position="开发";
        String userName= GetUserLoginInfo.getUserInfo(request).getUSE002();
        List<String> mainList = GetUserLoginInfo.queryRoleForMainList(userName, modelAndView, reportInfoService, developMainService);
        return mainList;
    }

    @RequestMapping("/maintainQuery")
    public ModelAndView GetModelAndView(HttpServletRequest request) {
        ModelAndView modelAndView=getModelAndView("MaintainReport");
        List<String> mainList = getMainList(request,modelAndView);
        modelAndView.addObject("mainList", mainList);
        return modelAndView;
    }

    String strWhere="1=1";

    @RequestMapping("/findMaintainData")
    @ResponseBody
    public Object GetDataForMainReport(
            @RequestParam("dev002") String dev002,
            @RequestParam("dev009") String dev009,
            @RequestParam("dev008") String dev008,
            @RequestParam("mai006") String mai006,
            @RequestParam("mai0061") String mai0061,
            @RequestParam("limit") Integer limit,
            @RequestParam("page") Integer page
    ) {
        strWhere="1=1";
        if(!StringUtils.isNullOrEmpty(dev002))
            strWhere=strWhere+" AND DEV002='"+dev002+"'";
        if(!StringUtils.isNullOrEmpty(dev009))
            strWhere=strWhere+" AND DEV009='"+dev009+"'";
        if(!StringUtils.isNullOrEmpty(dev008))
            strWhere=strWhere+" AND DEV008='"+dev008+"'";
        if(!StringUtils.isNullOrEmpty(mai006))
            strWhere=strWhere+" AND DEV006>='"+mai006+"'";
        if(!StringUtils.isNullOrEmpty(mai0061))
            strWhere=strWhere+" AND DEV006<='"+mai0061+"'";

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT DEV002,DEV008,DEV009,MAX(CASE DEV013 WHEN '新增' THEN coun ELSE 0 END) 'add',");
        stringBuilder.append("MAX(CASE DEV013 WHEN '修改' THEN coun ELSE 0 END) 'edit',MAX(CASE DEV013 WHEN '追加' THEN coun ELSE 0 END) 'ad',");
        stringBuilder.append("MAX(CASE DEV013 WHEN 'bug' THEN coun ELSE 0 END) 'bug',MAX(CASE DEV013 WHEN '其它' THEN coun ELSE 0 END) 'other'  FROM (");
        stringBuilder.append("SELECT DEV002,DEV008,DEV009,DEV013,count(1) coun FROM rgwdev where "+strWhere+" ");
        stringBuilder.append( "group by DEV002,DEV008,DEV009,DEV013) A GROUP BY DEV002,DEV008,DEV009 limit "+ (page-1)+","+limit+"");

        Query  dataQquery=entityManager.createNativeQuery(stringBuilder.toString());
        List<MaintainReport> chartAvgs =dataQquery.getResultList();
        Map<Object,Object> map=new HashMap<>();
        map.put("code",0);
        map.put("count", chartAvgs.size());
        map.put("data", chartAvgs);
        return map;
    }

    @RequestMapping("/maintainChart")
    public ModelAndView GetModelAndViewChart(HttpServletRequest request){
        ModelAndView modelAndView=getModelAndView("DevelopmentChart");
        List<String> mainList = getMainList(request,modelAndView);
        List<String> customs =maintainReportService.getDEV002();
        modelAndView.addObject("mainList", mainList);
        modelAndView.addObject("customs", customs);
        return modelAndView;
    }


    @RequestMapping("/chartData")
    @ResponseBody
    public Object GetChartData(
            @RequestParam("username") String username,
            @RequestParam("opera") String opera,
            @RequestParam("speed") String speed,
            @RequestParam("custom") String custom,
            @RequestParam("dateStart") String dateStart,
            @RequestParam("dateEnd") String dateEnd,
            @RequestParam("monthOrWeek") String monthOrWeek
    ) {

        Map<Object, Object> map = new HashMap<>();

        strWhere = "1=1";

        StringBuilder stringBuilder = new StringBuilder();

        if (StringUtils.isNullOrEmpty(monthOrWeek))
            monthOrWeek = "月";

        if (!"全体".equals(username))
            strWhere = strWhere + " AND DEV008='" + username + "'";
        if (!StringUtils.isNullOrEmpty(opera))
            strWhere = strWhere + " AND DEV013='" + opera + "'";
        if (!StringUtils.isNullOrEmpty(speed))
            strWhere = strWhere + " AND DEV009='" + speed + "'";
        if (!StringUtils.isNullOrEmpty(dateStart)) {
            strWhere = strWhere + " AND DEV006>='" + dateStart + "'";
            strWhere = strWhere + " AND YEAR(DEV006)='" + dateStart.substring(0, 4) + "'";
        }
        if (!StringUtils.isNullOrEmpty(dateEnd)) {
            strWhere = strWhere + " AND DEV006<='" + dateEnd + "'";
            strWhere = strWhere + " AND YEAR(DEV006)='" + dateStart.substring(0, 4) + "'";
        }
        if(!StringUtils.isNullOrEmpty(custom)){
            strWhere = strWhere + " AND DEV002='" + custom + "'";
        }

        String column = "";

        if ("月".equals(monthOrWeek)) {
            column="DATE_FORMAT(DEV006,'%Y%m')";
            if (StringUtils.isNullOrEmpty(dateStart) && StringUtils.isNullOrEmpty(dateEnd)) {
                strWhere = strWhere + " AND YEAR(DEV006)=YEAR(NOW())";
            }

        }else if("周".equals(monthOrWeek)){
            column="YEARWEEK(DEV006)";
            if (StringUtils.isNullOrEmpty(dateStart) && StringUtils.isNullOrEmpty(dateEnd)) {
                String week = maintainReportService.getWeek();
                strWhere=strWhere+" AND YEARWEEK(DEV006)>'"+week+"'";
            }
        }


        stringBuilder=new StringBuilder();
//        if (custom.equals("客户/时间")) {
//            stringBuilder.append("select DEV002 custom,"+column+" date,count(1) coun from rgwdev where " + strWhere);
//            stringBuilder.append(" group by DEV002,"+column+" order by "+column+";");
//        } else if (custom.equals("客户/性质")) {
//            stringBuilder.append("select DEV002 custom,DEV013 opera,count(1) coun from rgwdev where " + strWhere);
//            stringBuilder.append(" group by DEV002,DEV013 order by DEV013;");
//        } else if (custom.equals("客户/进度")) {
//            stringBuilder.append("select DEV002 custom,DEV009 speed,count(1) coun from rgwdev where " + strWhere);
//            stringBuilder.append(" group by DEV002,DEV009 order by DEV009;");
//        } else if (custom.equals("时间/性质")) {
//            stringBuilder.append("select DEV013 opera,"+column+" date,count(1) coun from rgwdev where " + strWhere);
//            stringBuilder.append(" group by DEV013,"+column+" order by "+column+";");
//        } else if (custom.equals("时间/进度")) {
//            stringBuilder.append("select DEV009 speed,"+column+" date,count(1) coun from rgwdev where " + strWhere);
//            stringBuilder.append(" group by DEV009,"+column+" order by "+column+";");
//        } else if (custom.equals("进度/性质")) {
//            stringBuilder.append("select DEV009 speed,DEV013 opera,count(1) coun from rgwdev where " + strWhere);
//            stringBuilder.append(" group by DEV009,DEV013 order by DEV013;");
//        }

            stringBuilder.append("select DEV013 opera,"+column+" date,count(1) coun from rgwdev  ");
            stringBuilder.append("where " + strWhere);
            stringBuilder.append(" group by DEV013,"+column+" order by "+column+"; ");

        Query query = entityManager.createNativeQuery(stringBuilder.toString());
        List<Object[]> chartList = query.getResultList();

        //x轴
        List<String> dateList=new ArrayList<>();

        for (Object[] obj : chartList){
                if(!dateList.contains(obj[1].toString()))
                    dateList.add(obj[1].toString());
        }

        //新增完成
        List<Integer> addList=new ArrayList<>();
        //新增未开始
//        List<Integer> addOverList=new ArrayList<>();
        //新增进行中
//        List<Integer> addListing=new ArrayList<>();
        //修改完成
        List<Integer> editList=new ArrayList<>();
        //修改未开始
//        List<Integer> editOverList=new ArrayList<>();
        //修改进行中
//        List<Integer> editListing=new ArrayList<>();
        //追加完成
        List<Integer> insertList=new ArrayList<>();
        //追加未开始
//        List<Integer> insertOverList=new ArrayList<>();
        //追加进行中
//        List<Integer> insertListing=new ArrayList<>();
        //bug完成
        List<Integer> bugList=new ArrayList<>();
        //bug未开始
//        List<Integer> bugOverList=new ArrayList<>();
        //bug进行中
//        List<Integer> bugListing=new ArrayList<>();
        //其它完成
        List<Integer> qtList=new ArrayList<>();
        //其它未开始
//        List<Integer> qtOverList=new ArrayList<>();
        //其它进行中
//        List<Integer> qtListing=new ArrayList<>();

        int i=0;
        String operas="", speeds="",dt="";
        boolean addOver = false;//,addNoOver=false,addOverIng=false
        boolean editOver = false;//,editNoOver=false,editOverIng=false
        boolean inOver = false;//,inNoOver=false,inOverIng=false
        boolean bugOver = false;//,bugNoOver=false,bugOverIng=false
        boolean qtOver = false;//,qtNoOver=false,qtOverIng=false

        for (String date:dateList) {
            i = 0;
            addOver =  false;//addNoOver = addOverIng =
            editOver = false;//editNoOver=editOverIng=
            inOver = false;//inNoOver=inOverIng=
            bugOver = false;//bugNoOver=bugOverIng=
            qtOver = false;//qtNoOver=qtOverIng=
            for (Object[] obj : chartList) {
                operas = obj[0].toString().trim();
                speeds = obj[1].toString().trim();
                dt = obj[2].toString();
                i++;

                if (addOver == false) {
                    if ("新增".equals(operas) && date.equals(speeds) ) { //&& "完成".equals(speed) && date.equals(dt)
                        addList.add(Integer.parseInt(dt));
                        addOver = true;
                    } else if (i == chartList.size())
                        addList.add(0);
                }

//                if (addNoOver == false) {
//                    if ("新增".equals(opera) && "未开始".equals(speed) && date.equals(dt)) {
//                        addOverList.add(Integer.parseInt(obj[3].toString()));
//                        addNoOver = true;
//                    } else if (i == chartList.size())
//                        addOverList.add(0);
//                }
//
//                if (addOverIng == false) {
//                    if ("新增".equals(opera) && "进行中".equals(speed) && date.equals(dt)) {
//                        addListing.add(Integer.parseInt(obj[3].toString()));
//                        addOverIng=true;
//                    } else if (i == chartList.size())
//                        addListing.add(0);
//                }

                if (editOver == false) {
                    if ("修改".equals(operas) && date.equals(speeds) ) {//&& "完成".equals(speed) && date.equals(dt)
                        editList.add(Integer.parseInt(dt));
                        editOver = true;
                    } else if (i == chartList.size())
                        editList.add(0);
                }

//                if (editNoOver == false) {
//                    if ("修改".equals(opera) && "未开始".equals(speed) && date.equals(dt)) {
//                        editOverList.add(Integer.parseInt(obj[3].toString()));
//                        editNoOver = true;
//                    } else if (i == chartList.size())
//                        editOverList.add(0);
//                }
//
//                if (editOverIng == false) {
//                    if ("修改".equals(opera) && "进行中".equals(speed) && date.equals(dt)) {
//                        editListing.add(Integer.parseInt(obj[3].toString()));
//                        editOverIng=true;
//                    } else if (i == chartList.size())
//                        editListing.add(0);
//                }

                if (inOver == false) {
                    if ("追加".equals(operas) && date.equals(speeds) ) {//&& "完成".equals(speed) && date.equals(dt)
                        insertList.add(Integer.parseInt(dt));
                        inOver = true;
                    } else if (i == chartList.size())
                        insertList.add(0);
                }

//                if (inNoOver == false) {
//                    if ("追加".equals(opera) && "未开始".equals(speed) && date.equals(dt)) {
//                        insertOverList.add(Integer.parseInt(obj[3].toString()));
//                        inNoOver = true;
//                    } else if (i == chartList.size())
//                        insertOverList.add(0);
//                }
//
//                if (inOverIng == false) {
//                    if ("追加".equals(opera) && "进行中".equals(speed) && date.equals(dt)) {
//                        insertListing.add(Integer.parseInt(obj[3].toString()));
//                        inOverIng=true;
//                    } else if (i == chartList.size())
//                        insertListing.add(0);
//                }

                if (bugOver == false) {
                    if ("bug".equals(operas)  && date.equals(speeds)) {//&& "完成".equals(speed) && date.equals(dt)
                        bugList.add(Integer.parseInt(dt));
                        bugOver = true;
                    } else if (i == chartList.size())
                        bugList.add(0);
                }

//                if (bugNoOver == false) {
//                    if ("bug".equals(opera) && "未开始".equals(speed) && date.equals(dt)) {
//                        bugOverList.add(Integer.parseInt(obj[3].toString()));
//                        bugNoOver = true;
//                    } else if (i == chartList.size())
//                        bugOverList.add(0);
//                }
//
//                if (bugOverIng == false) {
//                    if ("bug".equals(opera) && "进行中".equals(speed) && date.equals(dt)) {
//                        bugListing.add(Integer.parseInt(obj[3].toString()));
//                        bugOverIng=true;
//                    } else if (i == chartList.size())
//                        bugListing.add(0);
//                }

                if (qtOver == false) {
                    if ("其它".equals(operas)  && date.equals(speeds)) {//&& "完成".equals(speed) && date.equals(dt)
                        qtList.add(Integer.parseInt(dt));
                        qtOver = true;
                    } else if (i == chartList.size())
                        qtList.add(0);
                }

//                if (qtNoOver == false) {
//                    if ("其它".equals(opera) && "未开始".equals(speed) && date.equals(dt)) {
//                        qtOverList.add(Integer.parseInt(obj[3].toString()));
//                        qtNoOver = true;
//                    } else if (i == chartList.size())
//                        qtOverList.add(0);
//                }
//
//                if (qtOverIng == false) {
//                    if ("其它".equals(opera) && "进行中".equals(speed) && date.equals(dt)) {
//                        qtListing.add(Integer.parseInt(obj[3].toString()));
//                        qtOverIng=true;
//                    } else if (i == chartList.size())
//                        qtListing.add(0);
//                }
            }
        }

        map.put("dateList", dateList);
        map.put("addList", addList);
//        map.put("addOverList", addOverList);
//        map.put("addListing", addListing);
        map.put("editList", editList);
//        map.put("editOverList", editOverList);
//        map.put("editListing", editListing);
        map.put("insertList", insertList);
//        map.put("insertOverList", insertOverList);
//        map.put("insertListing", insertListing);
        map.put("bugList", bugList);
//        map.put("bugOverList", bugOverList);
//        map.put("bugListing", bugListing);
        map.put("qtList", qtList);
//        map.put("qtOverList", qtOverList);
//        map.put("qtListing", qtListing);

//        List<String> xValue = new ArrayList<>();
//        List<String> yValue = new ArrayList<>();
//
//        List<Object[]> result = new ArrayList<>();
//        List<Object[]> sertitle = new ArrayList<>();
//
//        for (Object[] obj : chartList) {
//            if (!xValue.contains(obj[0].toString()))
//                xValue.add(obj[0].toString());
//            if (!yValue.contains(obj[1].toString()))
//                yValue.add(obj[1].toString());
//        }
//
//        for(Object[] obj : chartList){
//            Object[] obj1=new Object[3];
//            obj1[0]=xValue.indexOf(obj[0].toString());
//            obj1[1]=yValue.indexOf(obj[1].toString());
//            obj1[2]=obj[2].toString();
//            result.add(obj1);
//
//            Object[] obj2=new Object[3];
//            obj2[0]=obj[0].toString();
//            obj2[1]=obj[1].toString();
//            obj2[2]=obj[2].toString();
//            sertitle.add(obj2);
//        }
//
//        map.put("xValue", xValue);
//        map.put("yValue", yValue);
//        map.put("result", result);
//        map.put("sertitle", sertitle);
        map.put("dateList",dateList);
        map.put("addList",addList);
        map.put("editList",editList);
        map.put("insertList",insertList);
        map.put("bugList",bugList);
        map.put("qtList",qtList);
        return map;
    }


}
