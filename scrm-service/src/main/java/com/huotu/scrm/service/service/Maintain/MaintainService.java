package com.huotu.scrm.service.service.Maintain;

import com.huotu.scrm.service.entity.Maintain.Maintain;
import com.huotu.scrm.service.entity.custom.CustomCon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface MaintainService {

    /**
     * 根据条件获取问题处理数据
     * @param specification
     * @param pageable
     * @return
     */
    Page<Maintain> getAllBy(Specification specification, Pageable pageable);

    /**
     * 获取最大值
     * @return
     */
    String getMaxMAI001();

    /**
     * 依据客户名称获取联系人姓名
     * @param data
     * @return
     */
    List<String> getUserInfo(String data);

    /**
     * 获取所有的技术
     * @return
     */
    List<String> getMainList();

    /**
     * 保存数据
     * @param maintain
     */
    void saveAndRefresh(Maintain maintain);

    /**
     * 根据供应商名称，获取历史签合同明细
     * @param con001
     * @return
     */
    List<CustomCon> findAllByCON001(String con001);

    /**
     * 保存签订合同时间等信息
     * @param customCon
     */
    void saveAndRefresh(CustomCon customCon);

}
