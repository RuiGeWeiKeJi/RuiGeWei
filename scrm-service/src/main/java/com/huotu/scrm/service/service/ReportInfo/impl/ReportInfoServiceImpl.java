package com.huotu.scrm.service.service.ReportInfo.impl;

import com.huotu.scrm.service.model.customtrans;
import com.huotu.scrm.service.repository.ReportInfo.ReportInfoRepository;
import com.huotu.scrm.service.repository.ReportInfo.ReportInfoTransRepository;
import com.huotu.scrm.service.service.ReportInfo.ReportInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.HTMLDocument;
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
     * @return
     */
    @Override
    public Page<customtrans> getInfoForsale(String name,int year,int month, Pageable pageable) {
        //return reportInfoTransRepository.getAllByCUS012AndBRS003(specification,pageable);
//        return null;

        return reportInfoRepository.getInfoForsale(name,year,month,pageable);
    }
}
