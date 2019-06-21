package com.huotu.scrm.service.service.ReportInfo;


import com.huotu.scrm.service.entity.custom.Custom;

import java.util.List;

public interface ReportInfoService {

    /**
     * 获取登录人角色权限
     *
     * @param username
     * @return
     */
    List<String> getAllByROL002(String username);

    /**
     * 获取所有有客户信息的业务员
     *
     * @return
     */
    List<String> getSalesManForUser();

    /**
     * 获取所有行业
     *
     * @return
     */
    List<String> getAllBy();

}
