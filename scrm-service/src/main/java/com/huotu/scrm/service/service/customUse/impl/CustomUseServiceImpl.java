package com.huotu.scrm.service.service.customUse.impl;

import com.huotu.scrm.common.utils.GenerateUid;
import com.huotu.scrm.service.entity.customUse.CustomUse;
import com.huotu.scrm.service.repository.customUser.CustomUseRepository;
import com.huotu.scrm.service.service.customUse.CustomUseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class CustomUseServiceImpl implements CustomUseService {

    @Autowired
    private CustomUseRepository customUseRepository;

    /**
     * 分页获取数据
     *
     * @return
     */
    @Override
    public Page<CustomUse> findBy(Specification specification, Pageable pageable) {
        return customUseRepository.findAll(specification,pageable);
    }

    /**
     * 获取客户对应联系人id
     * @param customId
     * @return
     */
    @Override
    public String getUserId(String customId) {
        String uid = customUseRepository.findOneByCode()+"";
        uid = GenerateUid.getUid(uid);
        return uid;
    }

    /**
     * 保存数据
     * @param customUse
     */
    @Override
    public void insertCustomUse(CustomUse customUse) {
        customUseRepository.saveAndFlush(customUse);
    }

    /**
     * 删除联系人
     * @return
     */
    @Override
    public void deleteByCustomIdAndUserId(CustomUse customUse) {
        customUseRepository.delete(customUse);
    }

    /**
     * 查询
     * @return
     */
    @Override
    public CustomUse findOneByCustomIdAndUserId(Specification specification) {
        return  customUseRepository.findOne(specification);
    }


}
