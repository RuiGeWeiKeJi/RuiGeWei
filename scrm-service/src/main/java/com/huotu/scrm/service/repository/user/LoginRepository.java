package com.huotu.scrm.service.repository.user;

import com.huotu.scrm.service.entity.Power.Authority;
import com.huotu.scrm.service.entity.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface LoginRepository  extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    /**
     * 获取用户的权限信息
     * @param username
     * @return
     */

    @Query(value = "select b.USE001,b.USE002,b.POW002,b.POW003,b.POW004,b.POW005,b.POW006,b.POW007 " +
            "from powerinfo b where b.USE002=?1",nativeQuery = true)
    List<Authority> getPower(String username);

    /**
     * 查询超期客户
     * @return
     */
    @Query(value = "select count(1) from rgwcus where CUS011<NOW() and CUS012=?1",nativeQuery = true)
    Integer findBy(String username);

    /**
     * 查询所有人超期客户
     * @return
     */
    @Query(value = "select count(1) from rgwcus where CUS011<NOW()",nativeQuery = true)
    Integer findAllBy();

}
