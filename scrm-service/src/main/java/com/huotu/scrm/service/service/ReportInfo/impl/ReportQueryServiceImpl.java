package com.huotu.scrm.service.service.ReportInfo.impl;

import com.huotu.scrm.service.entity.baseset.QueryCondition;
import com.huotu.scrm.service.repository.ReportInfo.ReportQueryRepository;
import com.huotu.scrm.service.service.ReportInfo.ReportQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class ReportQueryServiceImpl implements ReportQueryService {


    @Autowired
    private ReportQueryRepository reportQueryRepository;


    @Override
    public void saveAndEdit(QueryCondition queryCondition) {
        reportQueryRepository.saveAndFlush(queryCondition);
    }

    @Override
    public Page<QueryCondition> findAll(Pageable pageable) {
        return reportQueryRepository.findAll(pageable);
    }

    @Override
    public void deleteQquery(QueryCondition queryCondition) {
        reportQueryRepository.delete(queryCondition);
    }

    @Override
    public QueryCondition getByFLT001AndFLT003(Specification specification) {
        return reportQueryRepository.findOne(specification);
    }

}
