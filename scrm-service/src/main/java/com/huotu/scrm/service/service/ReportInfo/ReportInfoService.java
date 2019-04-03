package com.huotu.scrm.service.service.ReportInfo;

import com.huotu.scrm.service.model.customtrans;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
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
    List<Object> getInfoForsale(String name, Date timeOne, Date timeTwo, int pageIndex, int pageSize);

    Page<customtrans> getInfoForsalePage(String name, int year, int month, Pageable pageable);

    Integer getCountForsalePage(String name, Date timeOne, Date timeTwo);

}
