package com.huotu.scrm.service.service.bar;

import java.util.List;

public interface barService {

    /**
     * 查询所有行业
     * @return
     */
    List<String> findAllindustry();

    /**
     * 查询所有业务员
     * @return
     */
    List<String> dindAllsalesman();

}
