package com.huotu.scrm.service.service.ReportInfo.impl;

import com.huotu.scrm.service.model.customtrans;
import com.huotu.scrm.service.repository.CustomBrs.CustomBrsRepository;
import com.huotu.scrm.service.repository.ReportInfo.ReportInfoRepository;
import com.huotu.scrm.service.service.ReportInfo.ReportInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
@Transactional
public class ReportInfoServiceImpl implements ReportInfoService {

    @Autowired
    private ReportInfoRepository reportInfoRepository;

    /**
     * 获取登录人角色权限
     * @param username
     * @return
     */
    @Override
    public List<String> getAllByROL002(String username) {
        return reportInfoRepository.getAllByROL002(username);
    }

    /**
     * 获取所有有客户信息的业务员
     * @return
     */
    @Override
    public List<String> getSalesManForUser() {
        return reportInfoRepository.getSalesManForUser();
    }

    /**
     * 获取所有行业
     * @return
     */
    @Override
    public List<String> getAllBy() {
        return reportInfoRepository.getAllBy();
    }

}
