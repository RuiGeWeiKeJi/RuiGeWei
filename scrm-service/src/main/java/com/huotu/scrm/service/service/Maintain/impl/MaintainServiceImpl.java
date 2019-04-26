package com.huotu.scrm.service.service.Maintain.impl;

import com.huotu.scrm.service.entity.Maintain.Maintain;
import com.huotu.scrm.service.entity.custom.Custom;
import com.huotu.scrm.service.entity.custom.CustomCon;
import com.huotu.scrm.service.repository.Maintain.MaintainRepository;
import com.huotu.scrm.service.repository.customCon.CustomConRepository;
import com.huotu.scrm.service.service.Maintain.MaintainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MaintainServiceImpl implements MaintainService {

    @Autowired
    private MaintainRepository maintainRepository;

    @Autowired
    private CustomConRepository customConRepository;

    /**
     * 根据条件获取问题处理数据
     * @param specification
     * @param pageable
     * @return
     */
    @Override
    public Page<Maintain> getAllBy(Specification specification, Pageable pageable) {
        return maintainRepository.findAll(specification,pageable);
    }

    /**
     * 获取最大值
     * @return
     */
    @Override
    public String getMaxMAI001() {
        return maintainRepository.getMaxMAI001();
    }

    /**
     * 依据客户名称获取联系人姓名
     * @param data
     * @return
     */
    @Override
    public List<String> getUserInfo(String data) {
        return maintainRepository.getUserInfo(data);
    }

    /**
     * 获取所有的技术
     * @return
     */
    @Override
    public List<String> getMainList() {
        return maintainRepository.getMainList();
    }

    /**
     * 保存数据
     * @param maintain
     */
    @Override
    public  void saveAndRefresh(Maintain maintain) {
        maintainRepository.saveAndFlush(maintain);
    }

    /**
     * 根据供应商名称，获取历史签合同明细
     * @param con001
     * @return
     */
    @Override
    public List<CustomCon> findAllByCON001(String con001) {
        return customConRepository.findAllByCON001(con001);
    }

    /**
     * 保存供应商签订合同等信息
     * @param customCon
     */
    @Override
    public void saveAndRefresh(CustomCon customCon) {
        customConRepository.saveAndFlush(customCon);
    }

    /**
     * 依据合同条件  签订所有合同
     * @param specification
     * @param pageable
     * @return
     */
    @Override
    public Page<CustomCon> findAll(Specification specification, Pageable pageable) {
        return customConRepository.findAll(specification,pageable);
    }


}
