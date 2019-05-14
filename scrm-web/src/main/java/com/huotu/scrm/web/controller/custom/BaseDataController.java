package com.huotu.scrm.web.controller.custom;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huotu.scrm.service.entity.baseset.BaseSet;
import com.huotu.scrm.service.service.baseset.basedataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

@Controller
@RequestMapping("/")
public class BaseDataController {

    @Autowired
    private basedataService basedataService;
    
    @RequestMapping(value = "basedata")
    @ResponseBody
    public ModelAndView findBaseDataPage(){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("BaseData");
        return modelAndView;
    }

    @RequestMapping(value = "type")
    @ResponseBody
    public Object findTypeForQuery(
            @RequestParam("page") Integer pageIndex,
            @RequestParam("limit") Integer pageSize,
            @RequestParam("bas001") String bas001
    ) {
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        org.springframework.data.domain.Pageable pageable = new PageRequest(pageIndex - 1, pageSize, sort);
        Specification<BaseSet> baseSetSpecification=new Specification<BaseSet>() {
            @Override
            public Predicate toPredicate(Root<BaseSet> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> predicates=new ArrayList<>();
                predicates.add(cb.equal(root.get("BAS001"),bas001));
                //pList转换为具体类型的数组
                Predicate[] predicate = predicates.toArray(new Predicate[0]);
                //将条件进行汇总并返回
                return cb.and(predicate);
            }
        };
        Page<BaseSet> baseSetPage = basedataService.findAll(baseSetSpecification,pageable);
        Map<Object,Object> map=new LinkedHashMap<>();
        map.put("code",0);
        map.put("count",baseSetPage.getTotalElements());
        map.put("data",baseSetPage.getContent());
        return map;
    }


    @RequestMapping(value = "savebasedata")
    @ResponseBody
    public Object saveAndEdit(  @RequestParam("id") Integer id,
                                @RequestParam("BAS001") String BAS001,
                                @RequestParam("BAS002") String BAS002) {
        BaseSet baseSet = new BaseSet();
        baseSet.setId(id);
        baseSet.setBAS001(BAS001);
        baseSet.setBAS002(BAS002);

        Specification<BaseSet> baseSetSpecification = new Specification<BaseSet>() {
            @Override
            public Predicate toPredicate(Root<BaseSet> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                predicates.add(cb.equal(root.get("BAS001"), BAS001));
                predicates.add(cb.equal(root.get("BAS002"), BAS002));
                //pList转换为具体类型的数组
                //pList转换为具体类型的数组
                Predicate[] predicate = predicates.toArray(new Predicate[0]);
                //将条件进行汇总并返回
                return cb.and(predicate);
            }
        };

        BaseSet baseSet1 = basedataService.findOne(baseSetSpecification);
        if (baseSet1 == null) {
            basedataService.saveAndRefresh(baseSet);
            return "true";
        } else
            return "false";
    }

    @RequestMapping(value = "deletebasedata")
    @ResponseBody
    public Object deleteItem(@RequestParam("id") Integer id)
    {
        BaseSet baseSet=new BaseSet();
        baseSet.setId(id);
        basedataService.deleteItem(baseSet);
        return "true";
    }

}
