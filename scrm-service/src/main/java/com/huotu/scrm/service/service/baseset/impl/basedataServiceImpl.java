package com.huotu.scrm.service.service.baseset.impl;

import com.huotu.scrm.service.entity.baseset.BaseSet;
import com.huotu.scrm.service.repository.baseset.BasedataRepository;
import com.huotu.scrm.service.service.baseset.basedataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class basedataServiceImpl implements basedataService {

    @Autowired
    private BasedataRepository basedataRepository;


    @Override
    public Page<BaseSet> findAll(Specification<BaseSet> specification, Pageable pageable) {
        return basedataRepository.findAll(specification,pageable);
    }

    /**
     * 保存数据
     * @param baseSet
     */
    @Override
    public void saveAndRefresh(BaseSet baseSet) {
        basedataRepository.saveAndFlush(baseSet);
    }

    @Override
    public void deleteItem(BaseSet baseSet) {
         basedataRepository.delete(baseSet);
    }

    @Override
    public BaseSet findOne(Specification<BaseSet> specification) {
        return basedataRepository.findOne(specification);
    }
}
