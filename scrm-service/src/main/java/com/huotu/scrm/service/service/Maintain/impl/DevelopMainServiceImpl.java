package com.huotu.scrm.service.service.Maintain.impl;

import com.huotu.scrm.service.entity.Maintain.DevelopMain;
import com.huotu.scrm.service.entity.Maintain.Maintain;
import com.huotu.scrm.service.repository.Maintain.DevelopMainRepository;
import com.huotu.scrm.service.repository.customCon.CustomConRepository;
import com.huotu.scrm.service.service.Maintain.DevelopMainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DevelopMainServiceImpl implements DevelopMainService {

    @Autowired
    private DevelopMainRepository developMainRepository;


    @Override
    public Page<DevelopMain> getAllBy(Specification specification, Pageable pageable) {
        return developMainRepository.findAll(specification,pageable);
    }

    @Override
    public String getMaxDEV001() {
        return developMainRepository.getMaxDEV001();
    }

    @Override
    public List<String> getUserInfo(String data) {
        return developMainRepository.getUserInfo(data);
    }

    @Override
    public List<String> getMainList() {
        return developMainRepository.getMainList();
    }

    @Override
    public void saveAndRefresh(DevelopMain developMain) {
        developMainRepository.saveAndFlush(developMain);
    }

}
