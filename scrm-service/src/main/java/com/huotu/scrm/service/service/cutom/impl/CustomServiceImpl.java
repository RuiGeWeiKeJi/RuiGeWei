package com.huotu.scrm.service.service.cutom.impl;

import com.huotu.scrm.service.entity.custom.Custom;
import com.huotu.scrm.service.repository.custom.CustomRepository;
import com.huotu.scrm.service.service.cutom.CustomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.huotu.scrm.common.utils.GenerateUid;

import java.util.List;

@Service
@Transactional
public class CustomServiceImpl implements CustomService {

    @Autowired
    private CustomRepository customRepository;

    /**
     * 分页获取数据
     * @param pageable
     * @return
     */
    @Override
    public Page<Custom> findAllBy(Pageable pageable) {

        return customRepository.findAllBy(pageable);
    }

    /**
     * 保存数据
     * @param custom
     * @return
     */
    public Custom insertCustom(Custom custom){
        return customRepository.saveAndFlush(custom);
    }

    /**
     * 获取客户编号
     * @return
     */
    @Override
    public String findOneByCode() {
        String uid = customRepository.findOneByCode()+"";
        uid = GenerateUid.getUidForCus(uid);
        return uid;
    }

    @Override
    public List<Custom> findName(Specification specification) {
        return customRepository.findAll(specification);
    }

    /**
     * 是否存在客户
     * @param customName
     * @return
     */
    @Override
    public String existsCustomInfo(String customName) {
        return customRepository.findByCode(customName);
    }

    /**
     * 是否存在同名称的客户
     * @param customName
     * @param customId
     * @return
     */
    @Override
    public boolean existsCustomNameAndId(String customName, String customId) {
        Integer result=customRepository.findByName(customName,customId);
        return result>0?true:false;
    }

}
