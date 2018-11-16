package com.huotu.scrm.service.service.user.impl;

import com.huotu.scrm.service.entity.Power.Authority;
import com.huotu.scrm.service.entity.User.User;
import com.huotu.scrm.service.repository.user.LoginRepository;
import com.huotu.scrm.service.service.user.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class LoginServiceImpl implements LoginService {

    @Autowired
    private LoginRepository loginRepository;

    @Override
    public User findLogin(Specification specification) {
        return loginRepository.findOne(specification);
    }

    @Override
    public List<Authority> getPower(String username) {
        return loginRepository.getPower(username);
    }

    @Override
    public Integer findBy(String username) {
        return loginRepository.findBy(username);
    }
}
