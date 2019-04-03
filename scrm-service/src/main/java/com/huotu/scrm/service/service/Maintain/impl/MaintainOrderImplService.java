package com.huotu.scrm.service.service.Maintain.impl;

import com.huotu.scrm.service.entity.Maintain.MaintainOrderEntity;
import com.huotu.scrm.service.repository.Maintain.MaintainOrderRepository;
import com.huotu.scrm.service.service.Maintain.MaintainOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class MaintainOrderImplService implements MaintainOrderService {

    @Autowired
    private MaintainOrderRepository maintainOrderRepository;

    /**
     * 获取所有服务记录的信息
     * @param specification
     * @param pageable
     * @return
     */
    @Override
    public Page<MaintainOrderEntity> findAllBy(Specification specification, Pageable pageable) {
        return maintainOrderRepository.findAll(specification,pageable);
    }

    /**
     * 获取单号
     * @return
     */
    @Override
    public String getByDER001Max() {
        return maintainOrderRepository.getByDER001Max();
    }

    /**
     * 保存数据
     * @param maintainOrderEntity
     */
    @Override
    public void saveAndRefresh(MaintainOrderEntity maintainOrderEntity) {
        maintainOrderRepository.saveAndFlush(maintainOrderEntity);
    }
}
