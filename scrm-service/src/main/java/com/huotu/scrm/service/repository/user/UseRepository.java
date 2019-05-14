package com.huotu.scrm.service.repository.user;


import com.huotu.scrm.service.entity.User.User;
import com.huotu.scrm.service.entity.customUse.CustomUse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UseRepository  extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {


    /**
     * 获取员工ID
     * @return
     */
    @Query(value = "select USE001 from rgwuse where id=(select max(id) from rgwuse)",nativeQuery = true)
    String findByCode();

    /**
     * 根据客户编号和联系人编号查询数据
     * @return
     */
    User findOneByUserId(String userId);

    /**
     * 获取所有员工
     * @return
     */
    @Query(value = "select distinct USE002 from rgwuse",nativeQuery = true)
    List<String> getAllBy();


    @Query(value = "update rgwuse set USE006=?2 where USE002=?1 ",nativeQuery = true)
    @Modifying
    int updateUserPass(String username,String password);

}
