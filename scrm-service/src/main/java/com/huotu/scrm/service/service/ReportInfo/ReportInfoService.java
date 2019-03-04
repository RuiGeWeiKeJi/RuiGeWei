package com.huotu.scrm.service.service.ReportInfo;

import com.huotu.scrm.service.model.customtrans;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface ReportInfoService {

    /**
     * 获取登录人角色权限
     * @param username
     * @return
     */
    List<String> getAllByROL002(String username);

    /**
     * 获取所有有客户信息的业务员
     * @return
     */
    List<String> getSalesManForUser( );

    /**
     * 根据业务员和日期  查询该业务员本月内的联系客户
     * @return
     */
    Page<customtrans> getInfoForsale(String name,int year,int month, Pageable pageable);

}
