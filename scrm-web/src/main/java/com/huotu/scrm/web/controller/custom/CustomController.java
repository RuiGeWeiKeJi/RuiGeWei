package com.huotu.scrm.web.controller.custom;

import com.huotu.scrm.common.utils.Constant;
import com.huotu.scrm.service.entity.CustomBrs.CustomBRS;
import com.huotu.scrm.service.entity.custom.Custom;
import com.huotu.scrm.service.entity.customUse.CustomUse;
import com.huotu.scrm.service.repository.ReportInfo.ReportInfoRepository;
import com.huotu.scrm.service.service.ReportInfo.ReportInfoService;
import com.huotu.scrm.service.service.bar.barService;
import com.huotu.scrm.service.service.customBrs.CustomBrsService;
import com.huotu.scrm.service.service.customUse.CustomUseService;
import com.huotu.scrm.service.service.cutom.CustemInfoService;
import com.huotu.scrm.service.service.cutom.CustomService;
import com.huotu.scrm.web.GetUserInfo.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.internet.MimeUtility;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;

@Controller
@RequestMapping("/")
public class CustomController {

    @Autowired
    private CustomService customService;

    @Autowired
    private barService barService;

    @Autowired
    private CustemInfoService custemInfoService;

    @Autowired
    private CustomBrsService customBrsService;

    @Autowired
    private ReportInfoService reportInfoService;

    @Autowired
    private CustomUseService customUseService;

    /**
     * 分页获取客户资料
     *
     * @param pageNumber
     * @return
     */
    @RequestMapping(value = "/CusOp")
    public ModelAndView findAllByPagetable(
            @RequestParam(value = "pagenumber") Integer pageNumber
    ) {
        ModelAndView model = new ModelAndView();
        Pageable pageable = new PageRequest(pageNumber, 20);
        Page<Custom> custom = customService.findAllBy(pageable);


        model.addObject("total", custom.getTotalPages());
        model.addObject("totalCount", custom.getTotalElements());
        model.addObject("data", custom.getContent());
        model.setViewName("CusOp");
        return model;
    }

    /**
     * 客户资料查询
     *
     * @return
     */
    @RequestMapping(value = "/custemQuery")
    public ModelAndView findCustemQuery(
            HttpServletRequest request
    ) {
        List<String> industrylist = barService.findAllindustry();

        List<String> salesmanlist = new ArrayList<>();
        Constant.Position = "业务";
        String userName= GetUserLoginInfo.getUserInfo(request).getUSE002();
        if (GetUserLoginInfo.getUserListForRole(userName, reportInfoService))
            salesmanlist = barService.dindAllsalesman();
        else
            salesmanlist.add(userName);
        ModelAndView model = new ModelAndView();
        model.addObject("industry", industrylist);
        model.addObject("salesman", salesmanlist);
        model.setViewName("custemQuery");
        return model;
    }

    private String sss="";

    /**
     * 保存数据
     *
     * @param custom
     * @return
     */
    @RequestMapping(value = "/addcustom")
    @ResponseBody
    public boolean insertCustom(Custom custom,HttpServletRequest request) {
        if (custom.getCUS009() == "")
            custom.setCUS009(null);
        if (custom.getCUS011() == "")
            custom.setCUS011(null);
        custom.setCUS001(custom.getCUS001().trim());
        custom.setCUS002(custom.getCUS002().trim());
        custom.setCUS003(custom.getCUS003().trim());
        custom.setCUS004(custom.getCUS004().trim());
        custom.setCUS005(custom.getCUS005().trim());
        custom.setSalesman(custom.getSalesman().trim());
        custom.setIndustry(custom.getIndustry().trim());
        custom.setCUS013(custom.getCUS013().trim());
        custom.setCUS014(custom.getCUS014().trim());
        custom.setCUS016(custom.getCUS016().trim());
        custom.setCUS017(custom.getCUS017().trim());
        if (!custom.getCUS018().trim().contains("-"))
            custom.setCUS018(DateFormat.getFormatForDate(custom.getCUS018().trim()));
        else
            custom.setCUS018(custom.getCUS018().trim());
        if (custom.getCUS019().trim() == "")
            custom.setCUS019(GetUserLoginInfo.getUserInfo(request).getUSE002());
        else
            custom.setCUS019(custom.getCUS019().trim());
        if (StringUtils.isEmpty(custom.getCUS018()))
            custom.setCUS018(customBrsService.getDateNow().toString());
        boolean result = customService.existsCustomNameAndId(custom.getCUS002(), custom.getCUS001());
        if (result == false) {
            customService.insertCustom(custom);
        }
        return result;
    }

    /**
     * 获取客户资料编号
     *
     * @return
     */
    @RequestMapping(value = "/custemfindByCode")
    @ResponseBody //必须要有这个,如果没有,ajax异步请求不能就收到数据
    public Object findOneByCode() {
        Map<String,Object> map=new LinkedHashMap<>();
        String uid = customService.findOneByCode();
        Date date=customBrsService.getDateNow();
        map.put("uid",uid);
        map.put("date",date);
//        List<String> user=custemInfoService.findAllBy();
//        map.put("user",user);
        return map;
    }

    /**
     * 读取业务人员
     * @return
     */
    @RequestMapping(value = "/readcustomuser")
    @ResponseBody
    public Object readCustomUser(
            HttpServletRequest request
    ) {
//        List<String> user=custemInfoService.findAllBy();
        List<String> salesmanlist = new ArrayList<>();
        Constant.Position = "业务";
        String userName = GetUserLoginInfo.getUserInfo(request).getUSE002();
        if (GetUserLoginInfo.getUserListForRole(userName, reportInfoService))
            salesmanlist = barService.dindAllsalesman();
        else
            salesmanlist.add(userName);
        Map<Object, Object> map = new LinkedHashMap<>();
        map.put("salesman", salesmanlist);
        List<String> industrylist=barService.findAllindustry();
        map.put("industry",industrylist);
        return map;
    }

    /**
     * 模糊查询客户名称
     *
     * @param data
     * @return
     */
    @RequestMapping(value = "/findlike")
    @ResponseBody
    public Object findLike(
            @RequestParam("data") String data,
            @RequestParam("condition") String condition
    ) {
        try {
            URLDecoder.decode(data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Specification specification = addCondition(data,condition);
        List<Custom> names = customService.findName(specification);
        return names;
    }

    /**
     * 是否存在该客户
     * @param customName
     * @return
     */
    @RequestMapping(value = "/existscustom")
    @ResponseBody
    public Object existsCusInfo(
            @RequestParam("customName") String customName
    ){
        String customInfo=customService.existsCustomInfo(customName);
        Date date=customBrsService.getDateNow();
        Map<Object,Object> map=new LinkedHashMap<>();
        map.put("custominfo",customInfo);
        map.put("date",date);
        return map;
    }



    @RequestMapping(value = "/findexecl")
    @ResponseBody
    public ModelAndView getExeclReportHtml(){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("ExeclExport");
        return modelAndView;
    }

    private boolean result=false;
    private String info="";

    @RequestMapping(value = "/saveexecl")
    @ResponseBody
    public Object getExeclData(
            @RequestParam(value = "files",required = false) MultipartFile multipartFile
            ) {
        if (multipartFile.isEmpty()) {
            return "文件不存在";
        }
        List<String> levelList=new ArrayList<>();
        levelList.add("无");
        levelList.add("A");
        levelList.add("B");
        levelList.add("C");
        levelList.add("D");
        levelList.add("E");
        levelList.add("X");
        levelList.add("Y");
        info="导入成功";
        InputStream in = null;
        try {
            in = multipartFile.getInputStream();
            List<List<Object>> lists = ExeclUtils.getBankListByExecl(in, multipartFile.getOriginalFilename());
            List<Custom> customList = new ArrayList<Custom>();
            List<String> cusName=new ArrayList<>();
            result=true;
            for (int i = 0; i < lists.size(); i++) {
                List<Object> ob = lists.get(i);
                int size=ob.size();
                if (size <= 1)
                    break;
                Custom custom = new Custom();
                if(StringUtils.isEmpty( ob.get(0)) && StringUtils.isEmpty(ob.get(1)) )
                    break;
                if(ob.get(0).toString().equals("null") || ob.get(1).toString().equals("null"))
                    break;
                if(StringUtils.isEmpty( ob.get(0)) && !StringUtils.isEmpty(ob.get(1)) ){
                    info="第"+i+1+"行客户编号为空";
                    result=false;
                    break;
                }
                custom.setCUS001(String.valueOf(ob.get(0)).trim());
                if(custom.getCUS001().trim().length()<6){
                    info="客户编号少于6位,请核实后再导入";
                    result=false;
                    break;
                }
                if(StringUtils.isEmpty( ob.get(1) ) ){
                    info="客户编号:"+custom.getCUS001()+"的客户名称为空";
                    result=false;
                    break;
                }
                custom.setCUS002(String.valueOf(ob.get(1)).trim());
                if(!cusName.contains(custom.getCUS002()))
                    cusName.add(custom.getCUS002().trim());
                if(StringUtils.isEmpty( ob.get(2)) ){
                    info="客户编号:"+custom.getCUS001()+"的联系人为空";
                    result=false;
                    break;
                }
                custom.setCUS003(String.valueOf(ob.get(2)).trim());
                if(StringUtils.isEmpty( ob.get(3)) ){
                    info="客户编号:"+custom.getCUS001()+"的级别为空";
                    result=false;
                    break;
                }
                custom.setCUS004(String.valueOf(ob.get(3)).trim());
                if(!levelList.contains(custom.getCUS004())){
                    info="客户编号:"+custom.getCUS001()+"的级别不符合要求";
                    result=false;
                    break;
                }
                if(StringUtils.isEmpty( ob.get(4) ) ){
                    info="客户编号:"+custom.getCUS001()+"的地址为空";
                    result=false;
                    break;
                }
                custom.setCUS005(String.valueOf(ob.get(4)).trim());
                Double db = null;
                if (StringUtils.isEmpty(String.valueOf(ob.get(5))))
                    custom.setCUS006(0.0);
                else {
                    db = new Double(String.valueOf(ob.get(5)));
                    custom.setCUS006(db.doubleValue());
                }
                if (StringUtils.isEmpty(String.valueOf(ob.get(6))))
                    custom.setCUS007(0.0);
                else {
                    db = new Double(String.valueOf(ob.get(6)));
                    custom.setCUS007(db.doubleValue());
                }
                if (StringUtils.isEmpty(String.valueOf(ob.get(7))))
                    custom.setCUS008(0.0);
                else {
                    db = new Double(String.valueOf(ob.get(7)));
                    custom.setCUS008(db.doubleValue());
                }
                if(StringUtils.isEmpty( ob.get(8) ) ){
                    info="客户编号:"+custom.getCUS001()+"的行业为空";
                    result=false;
                    break;
                }
                custom.setIndustry(String.valueOf(ob.get(8)).trim());
                if(StringUtils.isEmpty( ob.get(9)) ){
                    info="客户编号:"+custom.getCUS001()+"的业务员为空";
                    result=false;
                    break;
                }
                custom.setSalesman(String.valueOf(ob.get(9)).trim());
                if(StringUtils.isEmpty( ob.get(10)) ){
                    info="客户编号:"+custom.getCUS001()+"的来源为空";
                    result=false;
                    break;
                }
                custom.setCUS013(String.valueOf(ob.get(10)).trim());

                custom.setCUS014(String.valueOf(ob.get(11)).trim());
                if (StringUtils.isEmpty(String.valueOf(ob.get(12))))
                    custom.setCUS015(0);
                else {
                    Integer bd = new Integer(String.valueOf(ob.get(12)));
                    custom.setCUS015(bd.intValue());
                }
                custom.setCUS016(String.valueOf(ob.get(13)).trim());
                custom.setCUS017(String.valueOf(ob.get(14)).trim());
                custom.setCUS018(String.valueOf(ob.get(15)).trim());
                custom.setCUS019(String.valueOf(ob.get(16)).trim());
                customList.add(custom);
            }

            if(result==false)
                return "";

            Specification<Custom> specification=new Specification<Custom>() {
                @Override
                public Predicate toPredicate(Root<Custom> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                   List<Predicate> list=new ArrayList<>();
                    if(list!=null && list.size()>0){
                        CriteriaBuilder.In<String> in=cb.in(root.get("CUS002").as(String.class));
                        for(String i :cusName){
                            in.value(i);
                        }
                        list.add(in);
                    }
                    //pList转换为具体类型的数组
                    Predicate[] predicate = new Predicate[list.size()];
                    //将条件进行汇总并返回
                    return cb.and(list.toArray(predicate));
                }
            };
            List<Custom> repearCus=new ArrayList<>();
            List<Custom> customList1=customService.findAll(specification);
            if(customList1!=null && customList1.size()>0) {
               for(int i=0;i<customList1.size();i++){
                   for(int j=0;j<customList.size();j++){
                       if(customList.get(j).getCUS002().trim().equals(customList1.get(i).getCUS002().trim())){
//                           customList.remove(j);
//                           j--;
                           repearCus.add(customList.get(j));
//                           info="客户:"+customList.get(j).getCUS002().trim()+"重复,请核实后再导入";
//                           result=false;
//                           break;
                       }
                   }
//                   if(result==false)
//                       break;
               }
            }

            if(repearCus.size()>0){
                info="部分客户重复,其它客户已导入成功";
                for(Custom cus:repearCus){
                    customList.remove(cus);
                }
            }

//            if(result==false)
//                return "";

            customList= ListUtils.removeDuplicate(customList);
            customService.saveAndRefresh(customList);
            result=true;
        } catch (Exception e) {
            info=e.getMessage()+"字段类型错误,请核实后重新导入" +"  "+e.getStackTrace();
            result=false;
        } finally {
            return getMap(in,info,result);
        }
    }

    @RequestMapping(value = "/saveexecluse")
    @ResponseBody
    public Object getExeclDataUse(
            @RequestParam(value = "files",required = false) MultipartFile multipartFile
    ){
        if (multipartFile.isEmpty()) {
            return "文件不存在";
        }
        result=true;
        info="导入成功";
        InputStream in = null;
        try {
            in = multipartFile.getInputStream();
            List<List<Object>> lists = ExeclUtils.getBankListByExecl(in, multipartFile.getOriginalFilename());
            List<CustomUse> customList = new ArrayList<CustomUse>();
            List<String> cusName=new ArrayList<>();
            List<String> customNum=new ArrayList<>();

            for (int i = 0; i < lists.size(); i++) {

                    List<Object> ob = lists.get(i);
                    if(ob==null)
                        break;
                    int size = ob.size();
                    if (size <= 1)
                        break;
                    CustomUse custom = new CustomUse();
                    custom.setUserId(String.valueOf(ob.get(0)).trim());
                    if (custom.getUserId().contains(".")) {
                        info = "人员编号数据格式错误,不应该包括小数";
                        result = false;
                        break;
                    }

                    if (!cusName.contains(custom.getUserId()))
                        cusName.add(custom.getUserId());

                    custom.setCUR002(String.valueOf(ob.get(1)).trim());
                    custom.setCUR003(String.valueOf(ob.get(2)).trim());
                    custom.setCUR004(String.valueOf(ob.get(3)).trim());
                    custom.setCUR005(String.valueOf(ob.get(4)).trim());
                    custom.setCUR006(String.valueOf(ob.get(5)).trim());
                    custom.setCUR007(String.valueOf(ob.get(6)).trim());
                    custom.setCUR008(null);
                    custom.setCUR009(String.valueOf(ob.get(8)).trim());
                    custom.setCustomId(String.valueOf(ob.get(9)));


                if(custom.getCustomId().contains("."))
                {
                    info="客户编号数据格式错误,不应该包括小数";
                    result=false;
                    break;
                }
                if(!StringUtils.isEmpty(custom.getCustomId()) && !customNum.contains(custom.getCustomId()))
                    customNum.add(custom.getCustomId());

                if(StringUtils.isEmpty(custom.getUserId()) && StringUtils.isEmpty(custom.getCustomId())){
                    break;
                }

                if(custom.getUserId().equals("null") && custom.getCustomId().equals("null")){
                    break;
                }
                if(custom.getUserId().trim().length()<6){
                    info="人员编号:"+custom.getUserId().trim()+"少于6位,请核实后重新导入";
                    result=false;
                    break;
                }
                if(custom.getCustomId().trim().length()<6){
                    info="人员编号:"+custom.getUserId().trim()+"的客户编号少于6位,请核实后重新导入";
                    result=false;
                    break;
                }

                if(StringUtils.isEmpty(custom.getUserId().trim())){
                    info="人员编号为空,请核实";
                    result=false;
                    break;
                }
                if( StringUtils.isEmpty(custom.getCUR002().trim()) ){
                    info="人员编号:"+custom.getCustomId().trim()+"的姓名为空";
                    result=false;
                    break;
                }
                if( StringUtils.isEmpty(custom.getCUR007()) ){
                    info="人员编号:"+custom.getCustomId().trim()+"的性别为空";
                    result=false;
                    break;
                }
                if(  StringUtils.isEmpty(custom.getCustomId())){
                    info="人员编号:"+custom.getCustomId().trim()+"的客户编号为空";
                    result=false;
                    break;
                }

                customList.add(custom);

            }

            if(result==false)
                return "";

            Specification<CustomUse> specification=new Specification<CustomUse>() {
                @Override
                public Predicate toPredicate(Root<CustomUse> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                    List<Predicate> list=new ArrayList<>();
                    if(list!=null && list.size()>0){
                        CriteriaBuilder.In<String> in=cb.in(root.get("userId").as(String.class));
                        for(String i :cusName){
                            in.value(i);
                        }
                        list.add(in);
                    }
                    //pList转换为具体类型的数组
                    Predicate[] predicate = new Predicate[list.size()];
                    //将条件进行汇总并返回
                    return cb.and(list.toArray(predicate));
                }
            };

            Specification<CustomUse> specification1=new Specification<CustomUse>() {
                @Override
                public Predicate toPredicate(Root<CustomUse> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                    List<Predicate> list=new ArrayList<>();
                    if(list!=null && list.size()>0){
                        CriteriaBuilder.In<String> in=cb.in(root.get("customId").as(String.class));
                        for(String i :customNum){
                            in.value(i);
                        }
                        list.add(in);
                    }
                    //pList转换为具体类型的数组
                    Predicate[] predicate = new Predicate[list.size()];
                    //将条件进行汇总并返回
                    return cb.and(list.toArray(predicate));
                }
            };

            List<CustomUse> customUses=new ArrayList<>();

            //获取已有联系人编号   如果导入的数据中有重复的，将不再重复导入
            List<CustomUse> customList1=customUseService.findAll(specification);
            //获取导入的客户编号,获取库中所有该客户编号的客户编号，取消库中客户编号中不存在的该客户编号的数据
            List<String> stringList =custemInfoService.findByCUS001(specification);

            if(customList1!=null && customList1.size()>0) {
                for (int j = 0; j < customList.size(); j++) {
                    for (int i = 0; i < customList1.size(); i++) {
                        if (customList.get(j).getUserId().trim().equals(customList1.get(i).getUserId().trim())) {
//                            customList.remove(j);
//                            j--;
                            customUses.add(customList.get(j));
                        }
                    }
                }
            }

            if(customUses.size()>0){
                info="联系人部分数据重复,其它数据已导入成功";
                for (CustomUse user:customUses) {
                    customList.remove(user);
                }
            }

            if(result==false)
                return "";

            customList= ListUtils.removeDuplicate(customList);
            customUseService.saveAndRefresh(customList);
            result=true;
        } catch (Exception e) {
            info="数据格式错误,请核实"+e.getMessage()+"  "+e.getStackTrace();
            result=false;
        } finally {
            return getMap(in,info,result);
        }
    }


    static Map<Object,Object> getMap(InputStream in , String info, boolean result) {
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<Object, Object> map = new LinkedHashMap<>();
        map.put("info", info);
        if (result)
            map.put("result", "true");
        else
            map.put("result", "false");
        return map;
    }

    @RequestMapping(value = "/download")
    public void downloadExecl(HttpServletRequest request,HttpServletResponse response){
        String fileName="客户信息.xlsx";
        DownLoad.DownLoadInfo(fileName,response,request);
    }

    @RequestMapping(value = "/downloaduse")
    public void downloadExeclUse(HttpServletRequest request,HttpServletResponse response){
        String fileName="联系人信息.xlsx";
        DownLoad.DownLoadInfo(fileName,response,request);
    }

    Specification addCondition(String data,String condition) {

        Specification<Custom> specification = new Specification<Custom>() {
            @Override
            public Predicate toPredicate(Root<Custom> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {

                ///定义条件对象列表
                List<Predicate> plist = new ArrayList<>();
                //单表条件对象构建(定区编码和地区关键字在分区表中)
                if (!StringUtils.isEmpty(data)) {
                    Predicate p1 = cb.like(root.get("CUS002"), "%" + data + "%");
                    plist.add(p1);
                }
                if (!StringUtils.isEmpty(condition)) {
                    Predicate p1 = cb.equal(root.get("CUS004"), condition);
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
