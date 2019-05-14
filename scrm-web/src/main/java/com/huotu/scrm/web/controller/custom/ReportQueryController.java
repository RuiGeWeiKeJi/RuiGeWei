package com.huotu.scrm.web.controller.custom;

import com.huotu.scrm.service.entity.baseset.QueryCondition;
import com.huotu.scrm.service.service.ReportInfo.ReportQueryService;
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

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/")
public class ReportQueryController {


    @Autowired
    private ReportQueryService reportQueryService;

    @RequestMapping(value = "savequery")
    @ResponseBody
    public Object saveAndEdit(@RequestParam("id") Integer id,
                              @RequestParam("flt001") String flt001,
                              @RequestParam("flt002") String flt002,
                              @RequestParam("flt003") String flt003,
                              @RequestParam("flt004") String flt004
                              ){

        QueryCondition queryCondition=new QueryCondition();
        queryCondition.setId(id);
        queryCondition.setFLT001(flt001);
        queryCondition.setFLT002(flt002);
        queryCondition.setFLT003(flt003);
        queryCondition.setFLT004(flt004);
        Specification<QueryCondition> specification=new Specification<QueryCondition>() {
            @Override
            public Predicate toPredicate(Root<QueryCondition> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                predicates.add(cb.equal(root.get("FLT001"), flt001));
                predicates.add(cb.equal(root.get("FLT002"), flt002));
                predicates.add(cb.equal(root.get("FLT003"), flt003));
                if(!StringUtils.isEmpty(id)) {
                    predicates.add(cb.notEqual(root.get("id"), id));
                }
                Predicate[] predicate = predicates.toArray(new Predicate[0]);
                //将条件进行汇总并返回
                return cb.and(predicate);
            }
        };
        QueryCondition queryCondition1 = reportQueryService.getByFLT001AndFLT003(specification);
        if(queryCondition1==null) {
            reportQueryService.saveAndEdit(queryCondition);
            return "true";
        }
        else
            return "false";
    }

    @RequestMapping(value = "findquery")
    @ResponseBody
    public Object queryResult(@RequestParam("page") Integer pageIndex,
                              @RequestParam("limit") Integer pageSize){

        Sort sort=new Sort("FLT001");
        Pageable pageable=new PageRequest(pageIndex-1,pageSize,sort);
        Page<QueryCondition> queryConditions=reportQueryService.findAll(pageable);
        Map<Object,Object> map=new LinkedHashMap<>();
        map.put("code",0);
        map.put("count",queryConditions.getTotalElements());
        map.put("data",queryConditions.getContent());
        return map;
    }

    @RequestMapping(value = "deletequery")
    @ResponseBody
    public Object deleteQuery(@RequestParam("id") Integer id){
        QueryCondition queryCondition=new QueryCondition();
        queryCondition.setId(id);
        reportQueryService.deleteQquery(queryCondition);
        return "true";
    }


}
