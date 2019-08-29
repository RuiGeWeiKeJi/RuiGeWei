package com.huotu.scrm.service.service.Maintain.impl;

import com.huotu.scrm.service.entity.Maintain.Maintain;
import com.huotu.scrm.service.repository.Maintain.MaintainRepository;
import com.huotu.scrm.service.service.Maintain.MaintainReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaintainReportServiceImpl implements MaintainReportService {

    @Autowired
    private MaintainRepository maintainRepository;


    @Override
    public Page<Maintain> getAllBy(Specification specification, Pageable pageable) {
        return null;
    }

    @Override
    public List<String> getByDev002(String customName) {
        return maintainRepository.getByDev002(customName);
    }

    @Override
    public String getWeek() {
        return maintainRepository.getWeek();
    }

    @Override
    public List<String> getDEV002() {
        return maintainRepository.getDEV002();
    }
}
