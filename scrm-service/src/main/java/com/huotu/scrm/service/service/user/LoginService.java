package com.huotu.scrm.service.service.user;

import com.huotu.scrm.service.entity.Power.Authority;
import com.huotu.scrm.service.entity.User.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LoginService {

    /**
     * 用户登录验证
     * @return
     */
    User findLogin(Specification specification);

    /**
     * 获取用户的权限信息
     * @param username
     * @return
     */
    List<Authority> getPower(String username);

    /**
     * 查询超期客户数量
     * @return
     */
    Integer findBy(String username);

}
