package com.huotu.scrm.web.controller.custom;

import com.huotu.scrm.common.utils.Constant;
import com.huotu.scrm.service.model.customtrans;
import com.huotu.scrm.service.repository.CustomBrs.CustomBrsRepository;
import com.huotu.scrm.service.service.ReportInfo.ReportInfoService;
import com.huotu.scrm.web.GetUserInfo.DateFormat;
import com.huotu.scrm.web.GetUserInfo.GetUserLoginInfo;
import com.huotu.scrm.web.GetUserInfo.StringSplitAndCom;
import org.eclipse.persistence.internal.sessions.DirectCollectionChangeRecord;
import org.omg.CosNaming.BindingIterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
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
import javax.persistence.TemporalType;
import javax.persistence.criteria.*;
import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.huotu.scrm.web.GetUserInfo.GetUserLoginInfo.queryRoleForUser;


@Controller
@RequestMapping("/")
public class ReportInfoController {

    @Autowired
    private ReportInfoService reportInfoService;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private CustomBrsRepository customBrsRepository;

    /**
     * 进入统计报表页面
     *
     * @return
     */
    @RequestMapping(value = "/report")
    @ResponseBody
    public ModelAndView findPage() {
        ModelAndView model = new ModelAndView();
        model.setViewName("ReportInfo");
        return model;
    }

    /**
     * 根据登录人，获取沟通记录查询的权限人员信息
     *
     * @return
     */
    @RequestMapping(value = "/linkUp")
    @ResponseBody
    public ModelAndView getListForBus(
            HttpServletRequest request
    ) {
        String userName = GetUserLoginInfo.getUserInfo(request).getUSE002();
        Constant.Position = "业务";
        ModelAndView modelAndView = new ModelAndView();

        List<String> getSales = new ArrayList<>();

        modelAndView = queryRoleForUser(userName, modelAndView, getSales, reportInfoService);
        List<String> getInd=reportInfoService.getAllBy();
        modelAndView.addObject("ind",getInd);
        Date date = customBrsRepository.findBy();
        modelAndView.addObject("end", DateFormat.getFormatForDate(date));
        modelAndView.addObject("start",DateFormat.getFormatForAdd(date,-30));
        modelAndView.setViewName("ReportInfoQuery");

        return modelAndView;
    }


    Date timeOne = new Date(), timeTwo = new Date();

    /**
     * 根据业务员和联系日期  查询近期此业务员的所有联系客户
     *
     * @param CUS012
     * @param BRS003
     * @return
     */
    @RequestMapping(value = "/customtrans")
    @ResponseBody
    public Object getListForBusCustom(
            @RequestParam("BRS008") String BRS008,
            @RequestParam("BRS003") String BRS003,
            @RequestParam("BRS0031") String BRS0031,
            @RequestParam ("CUS004") String CUS004,
            @RequestParam ("CUS010") String CUS010,
            @RequestParam ("CUS002") String CUS002,
            @RequestParam("page") Integer pageIndex,
            @RequestParam("limit") Integer pageSize
    ) {

        BRS003 = BRS003 + " 00:00:00";
        BRS0031 = BRS0031 + " 23:59:59";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            timeOne = sdf.parse(BRS003);
            timeTwo = sdf.parse(BRS0031);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        CUS004= StringSplitAndCom.getStrs(CUS004);
        CUS010= StringSplitAndCom.getStrs(CUS010);
        BRS008= StringSplitAndCom.getStrs(BRS008);

        Sort sort = new Sort(Sort.Direction.DESC, "BRS001");
        Pageable pageable = new PageRequest(pageIndex - 1, pageSize, sort);

        StringBuilder stringBuilder = new StringBuilder("select CUS001,CUS002,CUS004,CUS010,CUS012,BRS001,BRS005,BRS006,BRS007,BRS008 from rgwcus a inner join rgwbrs b on a.CUS001=b.BRS002 ");
        StringBuilder countBuilder=new StringBuilder("select count(*) from rgwcus a inner join rgwbrs b on a.CUS001=b.BRS002 ");

        StringBuilder whereSql=new StringBuilder(" WHERE 1=1");

        if(!BRS008.equals("null")) {
            whereSql.append(" AND BRS008 IN (" + BRS008 + ")");
        }
        whereSql.append(" AND BRS003>= '"+BRS003+"'");
        whereSql.append(" AND BRS003<= '"+BRS0031+"'");
        if(!StringUtils.isEmpty(CUS004) && !CUS004.equals("null"))
        {
            whereSql.append(" AND CUS004 IN ("+CUS004+")");
        }
        if(!StringUtils.isEmpty(CUS010)  && !CUS010.equals("null"))
        {
            whereSql.append(" AND CUS010 IN ("+CUS010+")");
        }
        if(!StringUtils.isEmpty(CUS002))
        {
            whereSql.append(" AND CUS002 = '"+CUS002+"' ");
        }

        stringBuilder.append(whereSql);
        countBuilder.append(whereSql);
        Query  dataQquery=entityManager.createNativeQuery(stringBuilder.toString());
        Query  countQuery=entityManager.createNativeQuery(countBuilder.toString());

        dataQquery.setFirstResult(pageable.getOffset());
        dataQquery.setMaxResults(pageable.getPageSize());
        Long total=(long) countQuery.getSingleResult();
        List<Object> list=dataQquery.getResultList();
        List<customtrans> customtransList = new ArrayList<>();
                for (int i=0;i<list.size();i++) {
                    com.huotu.scrm.service.model.customtrans cus = new customtrans();
                    Object[] objects = (Object[]) list.get(i);
                    cus.setCUS001(objects[0].toString());
                    cus.setCUS002(objects[1].toString());
                    cus.setCUS004(objects[2].toString());
                    cus.setCUS010(objects[3].toString());
                    cus.setCUS012(objects[4].toString());
                    cus.setBRS001(objects[5].toString());
                    cus.setBRS005(objects[6].toString());
                    cus.setBRS006(objects[7].toString());
                    cus.setBRS007(objects[8].toString());
                    cus.setBRS008(objects[9].toString());
                    customtransList.add(cus);
                }
        Page<customtrans> customtransPage = new PageImpl<>(customtransList,pageable,total);

        Map<String, Object> datasource = new LinkedHashMap<String, Object>();
        datasource.put("code", 0);
        datasource.put("count", customtransPage.getTotalElements());
        datasource.put("data", customtransPage.getContent());

        return datasource;
    }

}
