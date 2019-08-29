package com.huotu.scrm.service.service.cutom.impl;

import com.huotu.scrm.service.entity.custom.Custom;
import com.huotu.scrm.service.entity.custom.CustomQuery;
import com.huotu.scrm.service.repository.custom.CustemInfoRepository;
import com.huotu.scrm.service.service.cutom.CustemInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

import static org.springframework.data.jpa.domain.Specifications.not;
import static org.springframework.data.jpa.domain.Specifications.where;


@Service
@Transactional
public class CustemInfoServiceImpl implements CustemInfoService {

    @Autowired
    private CustemInfoRepository custemInfoRepository;

    @Autowired
   private EntityManager entityManager;

    /**
     * 根据条件查询客户资料
     * @param specification
     * @return
     */
    @Override
    public Page<Custom> findAllByWhere(Specification specification, Pageable pageable) {
        //return custemInfoRepository.findAll(where(specification).or(specification1),pageable);
        return custemInfoRepository.findAll(specification,pageable);
    }


    /**
     * 根据条件查询客户资料
     * @param customQuery
     * @return
     */
    @Override
    public List<Custom> queryAllInfo(CustomQuery customQuery) {
        StringBuilder strSql = new StringBuilder();
        strSql.append("select t from rgwcus t  ");//where 1=1
        if (!StringUtils.isEmpty(customQuery.getCUS002()))
            strSql.append(" and t.CUS002= :CUS002 ");
        if (!StringUtils.isEmpty(customQuery.getCUS004()))
            strSql.append(" and t.CUS004= :CUS004 ");
        if (!StringUtils.isEmpty(customQuery.getIndustry()))
            strSql.append(" and t.CUS010= :CUS010 ");
        if (!StringUtils.isEmpty(customQuery.getSalesman()))
            strSql.append(" and t.CUS012= :CUS012 ");
        if (!StringUtils.isEmpty(customQuery.getCUS013()))
            strSql.append(" and t.CUS013= :CUS013 ");
        if (!StringUtils.isEmpty(customQuery.getCUS009start()))
            strSql.append(" and t.CUS009>= :CUS009 ");
        if (!StringUtils.isEmpty(customQuery.getCUS009end()))
            strSql.append(" and t.CUS009<= :CUS0091 ");
        if (!StringUtils.isEmpty(customQuery.getCUS011start()))
            strSql.append(" and t.CUS011>= :CUS011 ");
        if (!StringUtils.isEmpty(customQuery.getCUS011end()))
            strSql.append(" and t.CUS011<= :CUS0111 ");
        if (!StringUtils.isEmpty(customQuery.getOther()))
            strSql.append(" and t.id in (select id from rgwcus where CUS011<NOW()) order by t.CUS001 ");
//        strSql.append("limit :page");
//        strSql.append(", :limit");

        Query query = entityManager.createQuery(strSql.toString());
        if (!StringUtils.isEmpty(customQuery.getCUS002()))
            query.setParameter("CUS002", customQuery.getCUS002());
        if (!StringUtils.isEmpty(customQuery.getCUS004()))
            query.setParameter("CUS004", customQuery.getCUS004());
        if (!StringUtils.isEmpty(customQuery.getIndustry()))
            query.setParameter("CUS010", customQuery.getIndustry());
        if (!StringUtils.isEmpty(customQuery.getSalesman()))
            query.setParameter("CUS012", customQuery.getSalesman());
        if (!StringUtils.isEmpty(customQuery.getCUS013()))
            query.setParameter("CUS013", customQuery.getCUS013());
        if (!StringUtils.isEmpty(customQuery.getCUS009start()))
            query.setParameter("CUS009", customQuery.getCUS009start());
        if (!StringUtils.isEmpty(customQuery.getCUS009end()))
            query.setParameter("CUS0091", customQuery.getCUS009end());
        if (!StringUtils.isEmpty(customQuery.getCUS011start()))
            query.setParameter("CUS011", customQuery.getCUS011start());
        if (!StringUtils.isEmpty(customQuery.getCUS011end()))
            query.setParameter("CUS0111", customQuery.getCUS011end());
//        query.setParameter("page", customQuery.getPageIndex());
//        query.setParameter("limit", customQuery.getPageSize());

        return  query.getResultList();
    }

    /**
     * 查询超期客户
     * @return
     */
    @Override
    public List<Integer> findBy() {
        return custemInfoRepository.findBy();
    }

    /**
     * 依据客户编号查询该客户所有联系人
     * @return
     */
    @Override
    public List<String> findAllBy() {
        return custemInfoRepository.findAllBy();
    }

    /**
     * 分页获取数据列表
     * @param strWhere
     * @param pageable
     * @return
     */
    @Override
    public Page<Custom> findAllByString(String strWhere, Pageable pageable) {
        return custemInfoRepository.findAllByString(strWhere,pageable);
    }

    @Override
    public List<String> findByCUS001(Specification specification) {
        return custemInfoRepository.findByCUS001(specification);
    }


}
