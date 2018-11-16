package com.huotu.scrm.service.service.customUse;

import com.huotu.scrm.service.entity.customUse.CustomUse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface CustomUseService {

    /**
     * 根据客户编号获取所有联系人
     *
     * @return
     */

    Page<CustomUse> findBy(Specification specification, Pageable pageable);

    /**
     * 获取客户联系人编号
     * @param customId
     * @return
     */
    String getUserId(String customId);

    /**
     * 保存数据
     * @param customUse
     */
    void insertCustomUse(CustomUse customUse);

    /**
     * 根据客户编号和联系人编号,删除联系人信息
     * @return
     */
    void deleteByCustomIdAndUserId(CustomUse customUse);

    /**
     * 根据编号查询数据
     * @return
     */
    CustomUse findOneByCustomIdAndUserId(Specification specification);

}
