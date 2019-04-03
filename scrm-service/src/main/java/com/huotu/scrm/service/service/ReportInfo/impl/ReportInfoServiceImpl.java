package com.huotu.scrm.service.service.ReportInfo.impl;

import com.huotu.scrm.service.model.customtrans;
import com.huotu.scrm.service.repository.ReportInfo.ReportInfoRepository;
import com.huotu.scrm.service.repository.ReportInfo.ReportInfoTransRepository;
import com.huotu.scrm.service.service.ReportInfo.ReportInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ReportInfoServiceImpl implements ReportInfoService {

    @Autowired
    private ReportInfoRepository reportInfoRepository;

//    @Autowired
//    private ReportInfoTransRepository reportInfoTransRepository;

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
     * 根据业务员和日期  查询该业务员本月内的联系客户
     * @param name
     * @param timeOne
     * @param timeTwo
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @Override
    public List<Object> getInfoForsale(String name, Date timeOne, Date timeTwo, int pageIndex, int pageSize) {
        return reportInfoRepository.getInfoForsale(name,timeOne,timeTwo,pageIndex,pageSize);
    }


    @Override
    public Page<customtrans> getInfoForsalePage(String name, int year, int month, Pageable pageable) {
        return reportInfoRepository.getInfoForsalePage(name,year,month,pageable);
    }

    @Override
    public Integer getCountForsalePage(String name, Date timeOne, Date timeTwo) {
        return reportInfoRepository.getCountForsalePage(name,timeOne,timeTwo);
    }


}
