package com.huotu.scrm.service.service.customSrs.impl;

import com.huotu.scrm.common.utils.GenerateUid;
import com.huotu.scrm.service.entity.customSRS.CustomSRS;
import com.huotu.scrm.service.repository.CustomBrs.CustomBrsRepository;
import com.huotu.scrm.service.repository.customSrs.CustomSrsRepository;
import com.huotu.scrm.service.service.customSrs.CustomSrsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class CustomSrsServiceImpl implements CustomSrsService {


    @Autowired
    private CustomSrsRepository customSrsRepository;

    @Autowired
    private CustomBrsRepository customBrsRepository;

    /**
     * 获取联系单单号
     * @return
     */
    @Override
    public String findByCode() {
        String uid=customSrsRepository.findByCode();
        Date date=customBrsRepository.findBy();
        uid= GenerateUid.getOddNum(uid,date);
        return uid;
    }

    /**
     * 获取系统当前日期
     * @return
     */
    @Override
    public Date getDateNow() {
        return customBrsRepository.findBy();
    }

    /**
     * 获取联系人
     * @param customId
     * @return
     */
    @Override
    public List<String> findByCustomId(String customId) {
        return customBrsRepository.findAllByCustomId(customId);
    }

    /**
     * 分页获取所有数据
     * @param pageable
     * @return
     */
    @Override
    public Page<CustomSRS> findAllByCustomId(Specification specification, Pageable pageable) {
        return customSrsRepository.findAll(specification,pageable);
    }

    /**
     * 保存数据
     * @param customSRS
     * @return
     */
    @Override
    public CustomSRS insertCusSrs(CustomSRS customSRS) {
        return customSrsRepository.saveAndFlush(customSRS);
    }

    @Override
    public void deleteCustomSrs(CustomSRS customSRS) {
         customSrsRepository.delete(customSRS);
    }

    @Override
    public CustomSRS findAllCustomsrs(Specification specification) {
        return customSrsRepository.findOne(specification);
    }


}
