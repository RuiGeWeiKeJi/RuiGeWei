package com.huotu.scrm.service.service.Maintain;

import com.huotu.scrm.service.entity.Maintain.MaintainOrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface MaintainOrderService {

    /**
     * 分页获取数据列表
     * @param specification
     * @param pageable
     * @return
     */
    Page<MaintainOrderEntity> findAllBy(Specification specification, Pageable pageable);

    /**
     * 获取单号
     * @return
     */
    String getByDER001Max();

    /**
     * 保存数据
     * @param maintainOrderEntity
     */
    void saveAndRefresh(MaintainOrderEntity maintainOrderEntity);

}
