package com.huotu.scrm.service.service.Power;


import com.huotu.scrm.service.entity.Power.PowerRole;
import com.huotu.scrm.service.entity.Power.UserRole;
import com.huotu.scrm.service.entity.User.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface PowerService {

    /**
     * 获取ID
     * @return
     */
    String findByCode();

    /**
     * 获取ID
     * @return
     */
    String findByCodeRole();

    /**
     * 获取所有员工
     * @return
     */
    List<User> findAllUseRole();

    /**
     * 保存权限
     * @param powerRole
     */
    void  insertPowerRole(PowerRole powerRole);

    /**
     * 保存用户
     * @param userRole
     */
    void insertUseRole(UserRole userRole);

    /**
     * 是否存在
     * @return
     */
    UserRole existsRole(Specification specification);

    /**
     * 查询权限信息
     * @param pageable
     * @return
     */
    Page<PowerRole> findAllBy( Pageable pageable);

    /**
     * 查询角色信息
     * @param pageable
     * @return
     */
    Page<UserRole> findRoleBy(Pageable pageable);

    /**
     * 删除权限
     * @param oddNum
     */
    void deleteByOddNum(String oddNum);

    /**
     * 删除角色
     * @param oddNum
     */
    void deleteByOddNumRole(String oddNum);

}
