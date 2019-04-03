package com.huotu.scrm.web.controller.custom;

import com.huotu.scrm.common.utils.GenerateUid;
import com.huotu.scrm.service.entity.Maintain.MaintainOrderEntity;
import com.huotu.scrm.service.repository.CustomBrs.CustomBrsRepository;
import com.huotu.scrm.service.service.Maintain.MaintainOrderService;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/")
public class MaintainOrderController {

    @Autowired
    private MaintainOrderService maintainOrderService;
    @Autowired
    private CustomBrsRepository customBrsRepository;

    @RequestMapping(value = "/findOrderMaintain")
    public ModelAndView findOrder(){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("MaintainOrder");
        return modelAndView;
    }

    /**
     * 依据客户名称查询所有服务记录单
     * @param DER012
     * @return
     */
    @RequestMapping(value = "/findAllOrder")
    @ResponseBody
    public Object findAllInfo(
            @RequestParam("DER012") String DER012
    ){
        Specification<MaintainOrderEntity> specification=new Specification<MaintainOrderEntity>() {
            @Override
            public Predicate toPredicate(Root<MaintainOrderEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> predicates=new ArrayList<>();
                if(StringUtils.isEmpty(DER012)){
                    Predicate p1 = cb.equal(root.get("DER012"), DER012);
                    predicates.add(p1);
                }
                Predicate[] predicate = predicates.toArray(new Predicate[0]);
                return cb.and(predicate);
            }
        };
        Sort sort=new Sort(Sort.Direction.ASC,"DER001");
        Pageable pageable=new PageRequest(0,50,sort);
        Page<MaintainOrderEntity> maintainOrderEntityPage=maintainOrderService.findAllBy(specification,pageable);
        Map<Object,Object> map=new LinkedHashMap<>();
        map.put("code",0);
        map.put("count", maintainOrderEntityPage.getTotalElements());
        map.put("data", maintainOrderEntityPage.getContent());
        return map;
    }

    /**
     * 新增获取单号
     * @return
     */
    @RequestMapping(value = "/getCodeForOrder")
    @ResponseBody
    public Object getCodeForOrder(){
        String uid=maintainOrderService.getByDER001Max();
        Date date = customBrsRepository.findBy();
        uid = GenerateUid.getOddNum(uid, date);
        Map<Object,Object> map=new LinkedHashMap<>();
        map.put("uid",uid);
        map.put("date",date);
        return map;
    }

    /**
     * 保存数据
     * @return
     */
    @RequestMapping(value = "/saveAndEdit")
    @ResponseBody
    public boolean saveAndEditOrder( MaintainOrderEntity maintainOrderEntity
//            @RequestParam("der001") String der001,
//            @RequestParam("der002") boolean der002,
//            @RequestParam("der003") boolean der003,
//            @RequestParam("der004") boolean der004,
//            @RequestParam("der005") boolean der005,
//            @RequestParam("der006") boolean der006,
//            @RequestParam("der007") boolean der007,
//            @RequestParam("der008") boolean der008,
//            @RequestParam("der009") boolean der009,
//            @RequestParam("der010") boolean der010,
//            @RequestParam("der011") boolean der011,
//            @RequestParam("der012") String der012,
//            @RequestParam("der013") String der013,
//            @RequestParam("der014") String der014,
//            @RequestParam("der015") String der015,
//            @RequestParam("der016") String der016,
//            @RequestParam("der017") String der017,
//            @RequestParam("der018") String der018,
//            @RequestParam("der019") String der019,
//            @RequestParam("der020") double der020,
//            @RequestParam("der021") boolean der021,
//            @RequestParam("id") Integer id
    ){
//        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
//        MaintainOrderEntity maintainOrderEntity=new MaintainOrderEntity();
//        maintainOrderEntity.setDER001(der001);
//        maintainOrderEntity.setDER002(der002);
//        maintainOrderEntity.setDER003(der003);
//        maintainOrderEntity.setDER004(der004);
//        maintainOrderEntity.setDER005(der005);
//        maintainOrderEntity.setDER006(der006);
//        maintainOrderEntity.setDER007(der007);
//        maintainOrderEntity.setDER008(der008);
//        maintainOrderEntity.setDER009(der009);
//        maintainOrderEntity.setDER010(der010);
//        maintainOrderEntity.setDER011(der011);
//        maintainOrderEntity.setDER012(der012);
//        maintainOrderEntity.setDER013(der013);
//        maintainOrderEntity.setDER014(der014);
//        try {
//            maintainOrderEntity.setDER015(format.parse( der015));
//            maintainOrderEntity.setDER017(format.parse(der017));
//            maintainOrderEntity.setDER018(format.parse(der018));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        maintainOrderEntity.setDER016(der016);
//        maintainOrderEntity.setDER019(der019);
//        maintainOrderEntity.setDER020(der020);
//        maintainOrderEntity.setDER021(der021);
//        maintainOrderEntity.setId(id);
        maintainOrderService.saveAndRefresh(maintainOrderEntity);
        return true;
    }

}
