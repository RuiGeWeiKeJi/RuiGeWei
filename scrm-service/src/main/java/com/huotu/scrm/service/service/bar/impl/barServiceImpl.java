package com.huotu.scrm.service.service.bar.impl;

import com.huotu.scrm.service.repository.bar.BarRepository;
import com.huotu.scrm.service.service.bar.barService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class barServiceImpl implements barService {

    @Autowired
    private BarRepository barRepository;

    /**
     * 查询所有行业
     * @return
     */
    @Override
    public List<String> findAllindustry() {
        return barRepository.findAllindustry();
    }

    @Override
    public List<String> dindAllsalesman() {
        return barRepository.dindAllsalesman();
    }
}
