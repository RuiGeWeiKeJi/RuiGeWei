package com.huotu.scrm.service.service.customBrs.impl;


import com.huotu.scrm.common.utils.GenerateUid;
import com.huotu.scrm.service.entity.CustomBrs.CustomBRS;
import com.huotu.scrm.service.entity.custom.Custom;
import com.huotu.scrm.service.entity.customUse.CustomUse;
import com.huotu.scrm.service.repository.CustomBrs.CustomBrsRepository;
import com.huotu.scrm.service.service.customBrs.CustomBrsService;
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
public class CustomBrsServiceImpl implements CustomBrsService {

    @Autowired
    private CustomBrsRepository customBrsRepository;

    /**
     * 根据客户编号获取记录单单号
     * @return
     */
    @Override
    public String getId() {
        String uid= customBrsRepository.findOneByCode();
        Date date=customBrsRepository.findBy();
        uid= GenerateUid.getOddNum(uid,date);
        return  uid;
    }

    /**
     * 获取当前日期
     * @return
     */
    @Override
    public Date getDateNow() {
        return customBrsRepository.findBy();
    }

    /**
     * 依据客户编号查询该客户所有联系人
     * @param customId
     * @return
     */
    @Override
    public List<String> findAllByCustomId(String customId) {
        return customBrsRepository.findAllByCustomId(customId);
    }

    /**
     * 保存数据
     * @param customBRS
     */
    @Override
    public void insertCustomUse(CustomBRS customBRS) {
         customBrsRepository.saveAndFlush(customBRS);
    }

    /**
     * 修改客户资料的最近联系日期和下次联系日期
     * @param custom
     */
    @Override
    public void updateCustom(Custom custom) {
         customBrsRepository.updateCustom(custom.getCUS009(),custom.getCUS011(),custom.getCUS001());
    }

    @Override
    public void updateCustomDate() {
        customBrsRepository.updateCustomDate();
    }

    /**
     * 分页读取客户记录单数据
     * @param pageable
     * @return
     */
    @Override
    public Page<CustomBRS> findByCustomId(Specification specification, Pageable pageable) {
        return customBrsRepository.findAll(specification,pageable);
    }

    /**
     * 读取客户资料联系单数据
     * @param specification
     * @return
     */
    @Override
    public Page<CustomBRS> readCusInfoByCon(Specification specification,Pageable pageable) {
        return customBrsRepository.findAll(specification,pageable);
    }

    @Override
    public void deleteOneByOddNum(String oddNum) {
         customBrsRepository.deleteByOddNum(oddNum);
    }


}
