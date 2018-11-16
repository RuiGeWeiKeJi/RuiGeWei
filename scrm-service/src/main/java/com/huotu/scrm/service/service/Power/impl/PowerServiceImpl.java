package com.huotu.scrm.service.service.Power.impl;

import com.huotu.scrm.common.utils.GenerateUid;
import com.huotu.scrm.service.entity.Power.PowerRole;
import com.huotu.scrm.service.entity.Power.UserRole;
import com.huotu.scrm.service.entity.User.User;
import com.huotu.scrm.service.repository.Power.PowerRepository;
import com.huotu.scrm.service.repository.Power.RoleRepository;
import com.huotu.scrm.service.repository.user.UseRepository;
import com.huotu.scrm.service.service.Power.PowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class PowerServiceImpl implements PowerService {

    @Autowired
    private PowerRepository powerRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UseRepository useRepository;

    @Override
    public String findByCode() {
        String uid = powerRepository.findByCode();
        uid = GenerateUid.getUid(uid);
        return uid;
    }

    @Override
    public String findByCodeRole() {
        String uid = roleRepository.findByCode();
        uid = GenerateUid.getUid(uid);
        return uid;
    }

    @Override
    public List<User> findAllUseRole() {
        return useRepository.findAll();
    }

    @Override
    public void insertPowerRole(PowerRole powerRole) {
        powerRepository.saveAndFlush(powerRole);
    }

    @Override
    public void insertUseRole(UserRole userRole) {
        roleRepository.saveAndFlush(userRole);
    }

    @Override
    public UserRole existsRole(Specification specification) {
        return roleRepository.findOne(specification);
    }

    /**
     * 查询权限信息
     *
     * @param pageable
     * @return
     */
    @Override
    public Page<PowerRole> findAllBy(Pageable pageable) {
        return powerRepository.findAll(pageable);
    }

    /**
     * 查询角色信息
     *
     * @param pageable
     * @return
     */
    @Override
    public Page<UserRole> findRoleBy(Pageable pageable) {
        return roleRepository.findAll(pageable);
    }


    /**
     * 删除权限
     *
     * @param oddNum
     */
    @Override
    public void deleteByOddNum(String oddNum) {
        powerRepository.deleteByOddNum(oddNum);
    }

    /**
     * 删除角色
     *
     * @param oddNum
     */
    @Override
    public void deleteByOddNumRole(String oddNum) {
        roleRepository.deleteByOddNum(oddNum);
    }


}
